package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Person;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Logic {

    public static final String separator = ";";
    /**
     * Проверяет можно ли добавлять в контакты с исходными данными.
     * @param name имя
     * @param surname фамиля
     * @param phone телефон
     * @param persons контакты
     * @param address адрес
     * @param comment комментарий
     * @return результат
     */
    public static boolean isCorrectToAdd(String name, String surname, String phone,
                                         ObservableList<Person> persons,
                                         String address, String comment) {
        boolean isCorrect = true;

        if (name == null || name.isEmpty() || name.isBlank()) {

            isCorrect = false;
        } else if (surname == null || surname.isEmpty() || surname.isBlank()) {

            isCorrect = false;
        } else if (persons.stream().anyMatch(p -> p.getName().equals(name)
                && p.getSurname().equals(surname))) {

            isCorrect = false;
        } else if (phone == null || phone.isEmpty() || phone.isBlank()) {

            isCorrect = false;
        }

        isCorrect =  isCorrect && isCorrectChars(name, surname, phone, address, comment);

        return isCorrect;
    }

    /**
     * Проверяет можно ли сохранить редактрованный контакт.
     * @param name имя
     * @param surname фамилия
     * @param phone телефон
     * @param person редактируемый контакт
     * @param persons контакты
     * @param address адрес
     * @param comment комментарий
     * @return руезльат проверки
     */
    public static boolean isCorrectToSave(String name, String surname, String phone,
                                          Person person, ObservableList<Person> persons,
                                          String address, String comment) {

        boolean isCorrect = true;


        if (persons.stream().anyMatch(p -> p != person && p.getName().equals(name)
                && p.getSurname().equals(surname))) {

            isCorrect = false;
        }
        if (name == null || name.isEmpty() || name.isBlank()) {

            isCorrect = false;
        }
        if (surname == null || surname.isEmpty() || surname.isBlank()) {

            isCorrect = false;
        }

        if (phone == null || phone.isEmpty() || phone.isBlank()) {

            isCorrect = false;
        }

        isCorrect =  isCorrect && isCorrectChars(name, surname, phone, address, comment);

        return isCorrect;
    }

    private static boolean isCorrectChars(String name, String surname, String phone, String address, String comment) {
        return name != null && !name.contains(";") && surname != null && !surname.contains(";")
                && phone != null && !phone.contains(";") && (address == null || !address.contains(";"))
                && (comment == null || !comment.contains(";"));
    }

    /**
     * Фильтрует контакты: имя или фамилия должны начинаться с введенной строки.
     * @param allPersons контакты
     * @param text введенная строка.
     * @return Элементы прошедщие фильрацию.
     */
    public static ObservableList<Person> filter(ObservableList<Person> allPersons, String text) {

        ObservableList<Person> filteredList = FXCollections.observableArrayList();


        for (var p : allPersons) {

            if (p.getSurname().startsWith(text) || p.getName().startsWith(text)) {

                filteredList.add(p);
            }
        }

        return filteredList;
    }

    /**
     * Сохарняет  в файлы контакты.
     * @param allPersons контакты
     * @param path путь файла
     */
    public static void saveToFile(ObservableList<Person> allPersons, String path) {

        var sb = new StringBuilder();

        for (var p : allPersons) {

            sb.append(p.toString()).append("\n");
        }


        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    /**
     * Читает данные из файла и добавляет в контакты, если соответсвует условию isCorrectToAdd.
     * @param path путь файла
     * @param allPersons контакты
     */
    public static void importPersons(String path, ObservableList<Person> allPersons) {

        var inputs = readPersons(path);

        for (var i : inputs) {


            if (isCorrectToAdd(i.getName(), i.getSurname(), i.getPhone(),
                    allPersons, i.getAddress(), i.getComment())) {

                allPersons.add(i);
                PhonebookDb.addPerson(i);
            }
        }
    }

    /**
     * Читает данные из файла в коллекцию
     * @param path путь файла
     * @return коллекция
     */
    public static ObservableList<Person> readPersons(String path) {
        ObservableList<Person> list = FXCollections.observableArrayList();

        try (FileReader reader = new FileReader(path)) {

            String line;

            Scanner sc = new Scanner(reader);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                try {

                    list.add(createPerson(line));

                } catch (Throwable ignored) {
                    System.err.println("Corrupted line: " + line);
                }

            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return list;
    }

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Создает из строки контакт
     * @param data строка
     * @return контакт
     */
    private static Person createPerson(String data) {
        var p = new Person();

        var mas = data.split(separator, -1);

        p.setName(mas[0])
                .setSurname(mas[1])
                .setPhone(mas[2])
                .setAddress(mas[3].equals("") ? null : mas[3])
                .setComment(mas[5].equals("") ? null : mas[5]);
        LocalDate date;
        try {
            date = LocalDate.parse(mas[4], formatter);
        } catch (Exception ex) {
            date = null;
        }

        p.setDate(date);

        return p;
    }


}
