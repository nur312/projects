package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    private Innovator<String> in;

    @BeforeEach
    void setUp() {
        in = new Innovator<>("0");
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));
        in.addChild(new Innovator<>("1.2"));

    }

    @AfterEach
    void tearDown() {
        in = null;
    }

    @Test
    void toStringTree() {

        var tory = new Tory<>(in);
        String str = Tree.ToStringTree(tory);

        System.out.println(str);
    }

    @Test
    void readTree() {

        String str = Tree.ToStringTree(in);

        var i = (Innovator<String>)Tree.ReadTree(str);

        assertTrue(i.getCode().equals(in.getCode()));
    }
}