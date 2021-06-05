package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovatorTest {
    private Innovator<String> in;

    @BeforeEach
    void setUp() {
        in = new Innovator<>("0");
    }

    @AfterEach
    void tearDown() {
        in = null;
    }

    @Test
    void addChild() {
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));
        in.addChild(new Innovator<>("1.2"));
        assertTrue(in.hasChildWithValue("1.1"));
        assertTrue(in.hasDescendantWithValue("1.1.1"));
    }

    @Test
    void removeChild() {
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));
        in.addChild(new Innovator<>("1.2"));
        var j = new Innovator<>("1.1.2");
        v1_1.addChild(j);
        v1_1.removeChild(j);
        assertFalse(v1_1.hasChildWithValue("1.1.2"));
        assertFalse(in.hasDescendantWithValue("1.1.2"));
        assertTrue(v1_1.addChild(j));
    }

    @Test
    void setCode() {
        in.setCode("1");
        assertSame("1", in.getCode());
    }

    @Test
    void setParent() {
        var v1_1 = new Innovator<>("1.1");
        v1_1.setParent(in);
        assertTrue(in.hasChildWithValue(v1_1.getCode()));
        assertEquals(v1_1.getParent().getCode(), in.getCode());
    }

    @Test
    void getParent() {
        var v1_1 = new Innovator<>("1.1");
        v1_1.setParent(in);
        assertEquals(v1_1.getParent().getCode(), in.getCode());
    }

    @Test
    void getChildren() {
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));
        var v1_2 = new Innovator<>("1.2");
        in.addChild(v1_2);
        var c = in.getChildren();
        assertTrue(c.contains(v1_1));
        assertTrue(c.contains(v1_2));
    }

    @Test
    void getDescendants() {
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        var v111 = new Innovator<>("1.1.1");
        v1_1.addChild(v111);
        var v1_2 = new Innovator<>("1.2");
        in.addChild(v1_2);
        var c = in.getDescendants();
        assertTrue(c.contains(v1_1));
        assertTrue(c.contains(v1_2));
        assertTrue(c.contains(v111));
    }

    @Test
    void hasChildWithValue() {
        in.addChild(new Innovator<>("1.2"));

        assertTrue(in.hasChildWithValue("1.2"));
        assertFalse(in.hasChildWithValue("1.2.3"));
    }

    @Test
    void hasDescendantWithValue() {
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));

        assertTrue(in.hasDescendantWithValue("1.1"));
        assertTrue(in.hasDescendantWithValue("1.1.1"));
        assertFalse(in.hasDescendantWithValue("1.1.1.1"));
    }

    @Test
    void getCode() {
        assertEquals(in.getCode(), "0");
    }
}