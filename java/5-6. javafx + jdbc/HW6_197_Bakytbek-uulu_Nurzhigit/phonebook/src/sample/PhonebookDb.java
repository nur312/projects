package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Person;

import java.sql.*;

/**
 * Класс для работы с бд
 */
public class PhonebookDb {

    private static Connection conn;

    private static final String dbName = "phonebookDB";

    private static final String createStringSQL = "CREATE TABLE PERSONS  "
            + "(PERSON_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
            + "   CONSTRAINT WISH_PK PRIMARY KEY, "
            + "NAME VARCHAR(32) NOT NULL, "
            + "SURNAME VARCHAR(32) NOT NULL, "
            + "PHONE VARCHAR(32)  NOT NULL, "
            + "ADDRESS VARCHAR(32), "
            + "BIRTHDAY TIMESTAMP, "
            + "COMMENT VARCHAR(32)"
            + ")";

    private static final String insertPersonSQL = "INSERT INTO PERSONS(NAME, SURNAME, PHONE, " +
            "ADDRESS, BIRTHDAY, COMMENT) VALUES(?, ?, ?, ?, ?, ?)";

    private static final String deletePersonSQL = "DELETE FROM PERSONS WHERE PERSON_ID = ?";

    private static final String selectAllSQL = "SELECT PERSON_ID, NAME, SURNAME, PHONE, ADDRESS, " +
            "BIRTHDAY, COMMENT from PERSONS";

    private static final String updatePersonSQL = "UPDATE PERSONS "
            + "SET NAME = ?"
            + ", SURNAME = ? "
            + ", PHONE = ? "
            + ", ADDRESS = ? "
            + ", BIRTHDAY = ? "
            + ", COMMENT = ? "
            + "WHERE PERSON_ID = ?";

    /**
     * Обновляет контакт
     * @param p контакт
     */
    public static void updatePerson(Person p) {

        int affectedRows;

        try (PreparedStatement statement = getConnection().prepareStatement(updatePersonSQL)) {

            statement.setString(1, p.getName());
            statement.setString(2, p.getSurname());
            statement.setString(3, p.getPhone());
            statement.setString(4, p.getAddress());
            statement.setDate(5, p.getDate() != null ? Date.valueOf(p.getDate()) : null);
            statement.setString(6, p.getComment());
            statement.setLong(7, p.getPersonId());

            affectedRows = statement.executeUpdate();

            if (affectedRows == 1) {

                System.out.println("Updated " + affectedRows + " person with id = " + p.getPersonId());
            } else {

                System.err.println("Update error");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Читает из базы данных все контакты и возращает их.
     * Используется один раз - при загрузке приложения.
     * @return все контакты
     */
    public static ObservableList<Person> getAllPerson() {

        ObservableList<Person> list = FXCollections.observableArrayList();

        try (Statement statement = getConnection().createStatement()) {

            ResultSet persons = statement.executeQuery(selectAllSQL);

            while (persons.next()) {

                var p = new Person();

                p.setPersonId(persons.getLong(1))
                        .setName(persons.getString(2))
                        .setSurname(persons.getString(3))
                        .setPhone(persons.getString(4))
                        .setAddress((persons.getString(5) == null || persons.getString(5).equals(""))
                                ? null : persons.getString(5))
                        .setDate(persons.getDate(6) == null ? null : persons.getDate(6).toLocalDate())
                        .setComment((persons.getString(5) == null || persons.getString(7).equals(""))
                                ? null : persons.getString(7));

                list.add(p);
            }

            persons.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }

    /**
     * Уаляет контакт из бд
     * @param p контакт
     */
    public static void deletePerson(Person p) {

        try (PreparedStatement statement = getConnection().prepareStatement(deletePersonSQL)) {
            statement.setLong(1, p.getPersonId());

            System.out.println("Deleted " + statement.executeUpdate() + " person with id = " + p.getPersonId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**
     * Добавялет контакт в бд
     * @param p контакт
     */
    public static void addPerson(Person p) {

        try (PreparedStatement statement = getConnection().prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, p.getName());
            statement.setString(2, p.getSurname());
            statement.setString(3, p.getPhone());
            statement.setString(4, p.getAddress());
            statement.setDate(5, p.getDate() != null ? Date.valueOf(p.getDate()) : null);
            statement.setString(6, p.getComment());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setPersonId(generatedKeys.getLong(1));

                    System.out.println("Added " + affectedRows + " person" + " with id = " + p.getPersonId());
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Создает соединение с бд, если его нет.
     * Было решено держать соединение открытым во время работы приложения, так как
     * при проверке будут выполнятся много операций с бд, но при промышленной
     * разработке нужно было открывать и закрывать при каждой операции, так как
     * контакты обновяются и добавляются очень редко.
     * @return соединение с бд.
     */
    public static Connection getConnection() {


        // define the Derby connection URL to use
        String connectionURL = "jdbc:derby:" + dbName + ";create=true";


        try {
            if (conn != null) {
                return conn;
            }

            conn = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);


            // Call utility method to check if table exists.
            //      Create the table if needed
            if (!isExistsPersonsTable(conn)) {

                System.out.println(" . . . . creating table PERSONS");

                Statement s = conn.createStatement();
                s.execute(createStringSQL);
                s.close();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conn;

    }
    /**
     * Закрывает соединение с бд.
     */
    public static void shutdownDb() {

        boolean gotSQLExc = false;
        try {

            conn.close();

            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException se) {
            if (se.getSQLState().equals("XJ015")) {

                gotSQLExc = true;
            }
        }
        if (!gotSQLExc) {

            System.out.println("Database did not shut down normally");
        } else {
            System.out.println("Database shut down normally");

        }
    }

    /**
     * Проверяет существет ли таблица в бд.
     * @param conTst соединение
     * @return результат проверки (true, если таблица сущетсвует).
     */
    public static boolean isExistsPersonsTable(Connection conTst) throws SQLException {

        try {
            Statement s = conTst.createStatement();
            s.execute("UPDATE PERSONS SET NAME = 'TEST', SURNAME = 'TEST', PHONE = 'TEST' WHERE 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();

            if (theError.equals("42X05"))   // Table does not exist
            {
                return false;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("PERSONS: Incorrect table definition. Drop table PERSONS and rerun this program");
                throw sqle;
            } else {
                System.out.println("PERSONS: Unhandled SQLException");
                throw sqle;
            }
        }
        return true;
    }



    ////////////////////////////TESTING//////////////////////////////


    /**
     * Соединение для теста (in-memory)
     * @return соединение с бд.
     */
    public static Connection getTestConnection() {


        // define the Derby connection URL to use
        String connectionURL = "jdbc:derby:memory:" + dbName + ";create=true";

        // jdbc:derby:memory:myDB;drop=true


        try {

            conn = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to in-memory database " + dbName);


            // Call utility method to check if table exists.
            //      Create the table if needed
            if (!isExistsPersonsTable(conn)) {

                System.out.println(" . . . . creating table PERSONS");

                Statement s = conn.createStatement();
                s.execute(createStringSQL);
                s.close();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return conn;

    }

    /**
     * Закрывает соединение с бд.
     */
    public static void dropTestDb() {

        try {
            conn.close();

            String connectionURL = "jdbc:derby:memory:" + dbName + ";drop=true";

            DriverManager.getConnection(connectionURL);


        } catch (SQLException ignored) {
        }
    }

}
