package validator.checkers.classesfortest;

import validator.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Constrained
public class TestClass {
    @NotNull
    String s = "A";

    @NotNull
    String ss;

    @NotNull
    List<@NotNull List<@NotNull @Size(min = 2, max = 10) List<@AnyOf({"A", "B", "C"}) String>>> list = new ArrayList<>() {
        {
            add(new ArrayList<>() {
                {
                    add(new ArrayList<>() {
                        {
                            add("A"); add(null); add("B"); add("C"); add("F");
                        }
                    });

                    add(new ArrayList<>() {
                        {
                            add("X");
                        }
                    });

                    add(null);
                }
            });
        }
    };

    @NotNull
    List<@NotNull List<@NotNull String>> list22;

    @Negative
    int i = -1;

    @Negative
    int ii = 1;

    @Positive
    int j = 1;

    @Positive
    int jj = -1;

    @InRange(min = 0, max = 10)
    int k = 5;

    @InRange(min = 0, max = 10)
    int kk = -5;

    @NotBlank
    String v = "a";

    @NotBlank
    String vv = "   ";

    @NotEmpty
    String a = "a";

    @NotEmpty
    String aa = "";

    @AnyOf({"a", "v"})
    String x = "a";

    @AnyOf({"a", "v"})
    String xx = "k";


    @Size(min = 1, max = 5)
    ArrayList<Integer> l = new ArrayList<>() {
        {
            add(5);
            add(5);
            add(5);
        }
    };

    @Size(min = 1, max = 2)
    ArrayList<Integer> ll = new ArrayList<>() {
        {
            add(5);
            add(5);
            add(5);
        }
    };
}
