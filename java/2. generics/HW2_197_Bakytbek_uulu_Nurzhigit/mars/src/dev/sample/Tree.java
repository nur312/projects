package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Tree {
    public static ArrayList<IMartian>  roots = new ArrayList<>();
    /**
     * Предоставляет родственное дерево в виде текстового отчета
     *
     * @param martian объект, которму нужно составить отчет.
     * @param <T>     тип генетического типа.
     * @return отчет.
     */
    public static <T> String ToStringTree(IMartian<T> martian) {
        StringBuilder sb = new StringBuilder(martian.toString() + "\n");

        for (var i : martian.getChildren()) {
            WriteToStringTree((IMartian<T>) i, sb, 1);
        }
        return sb.toString();
    }

    /**
     * проходимся по графу, записывая узлы в в иде строк.
     */
    private static <T> void WriteToStringTree(IMartian<T> martian, StringBuilder sb, int deep) {
        sb.append(" ".repeat(4 * deep)).append(martian.toString()).append("\n");

        for (var i : martian.getChildren()) {
            WriteToStringTree(i, sb, deep + 1);
        }
    }

    /**
     * Переводит отчет в объект класса генеалогического дерева
     *
     * @param data отчет
     * @param <T>  тип генетического кода.
     * @return объект, соответсвующий отчету
     * @throws IllegalArgumentException если неверный формат отчета.
     */
    public static <T> IMartian ReadTree(final String data) throws IllegalArgumentException {

        List<String> lines = Arrays.asList(data.split("\n"));

        String type = lines.get(0).substring(lines.get(0).indexOf('('), lines.get(0).indexOf(':'));

        if (type.contains("Str")) {
            var root = CreateInnovatorStr(lines.get(0));
            Convert(root, lines, Tree::CreateInnovatorStr);
            return lines.get(0).contains("Innovator") ? root : new Tory<>(root);
        } else if (type.contains("Int")) {
            var root = CreateInnovatorInt(lines.get(0));
            Convert(root, lines, Tree::CreateInnovatorInt);
            return lines.get(0).contains("Innovator") ? root : new Tory<>(root);
        } else {
            var root = CreateInnovatorDouble(lines.get(0));
            Convert(root, lines, Tree::CreateInnovatorDouble);
            return lines.get(0).contains("Innovator") ? root : new Tory<>(root);
        }

    }

    /**
     * Чтение и запись детей из отчета.
     */
    private static <T> void Convert(Innovator<T> root, List<String> lines, Function<String, Innovator<T>> creator) {
        for (int i = 1; i < lines.size(); ++i) {
            if (IsDeep(lines.get(i), 1)) {
                var child = creator.apply(lines.get(i));
                root.addChild(child);
                Convert(child, lines, creator, 2, i);

            }
        }
    }

    /**
     * Чтение и запись детей из отчета, рекурсивно проходимся.
     */
    private static <T> void Convert(Innovator<T> root, List<String> lines, Function<String,
            Innovator<T>> creator, int deep, int index) {

        for (int i = index + 1; i < lines.size(); ++i) {

            if (IsDeep(lines.get(i), deep)) {
                var child = creator.apply(lines.get(i));
                root.addChild(child);
                Convert(child, lines, creator, deep + 1, i);
            } else {
                break;
            }
        }
    }

    private static Innovator<String> CreateInnovatorStr(String s) {
        return new Innovator<>(GetValue(s));
    }

    private static Innovator<Integer> CreateInnovatorInt(String s) {
        return new Innovator<>(Integer.parseInt(GetValue(s)));
    }

    private static Innovator<Double> CreateInnovatorDouble(String s) {
        return new Innovator<>(Double.parseDouble(GetValue(s)));
    }

    /**
     * Из строки отчета возвращает значение ген. кода
     *
     * @param s строка отчета.
     * @return значение кода.
     */
    private static String GetValue(String s) {
        return s.substring(s.indexOf(':') + 1, s.length() - 1);
    }

    /**
     * Возращает имеет ли колено deep строка.
     *
     * @param line строка из отчета.
     * @param deep колено.
     * @return результат проверки.
     */
    private static boolean IsDeep(String line, int deep) {
        if (line.length() < 4 * deep + 1) {
            return false;
        }
        for (int i = 0; i < 4 * deep; ++i) {
            if (line.charAt(i) != ' ') {
                return false;
            }
        }

        return line.charAt(4 * deep) != ' ';
    }
}
