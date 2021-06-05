package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToryTest {
    private Tory<String> tory;

    @BeforeEach
    void setUp() {
        var in = new Innovator<>("0");
        var v1_1 = new Innovator<>("1.1");
        in.addChild(v1_1);
        v1_1.addChild(new Innovator<>("1.1.1"));
        in.addChild(new Innovator<>("1.2"));

        tory = new Tory<>(in);
    }

    @AfterEach
    void tearDown() {
        tory = null;
    }

    @Test
    void getParent() {
        assertEquals(((Tory<String>) tory.getChildren().toArray()[0]).getParent().getCode(), tory.getCode());
    }

    @Test
    void getChildren() {
        assertTrue(tory.getChildren().stream().anyMatch(x -> x.getCode().equals("1.1")));
    }

    @Test
    void getDescendants() {
        assertTrue(tory.getDescendants().stream().anyMatch(x -> x.getCode().equals("1.2")));
        assertTrue(tory.getDescendants().stream().anyMatch(x -> x.getCode().equals("1.1.1")));
    }

    @Test
    void hasChildWithValue() {
        assertTrue(tory.hasChildWithValue("1.1"));
        assertFalse(tory.hasChildWithValue("1.1.1"));
    }

    @Test
    void hasDescendantWithValue() {
        assertTrue(tory.hasDescendantWithValue("1.1"));
        assertTrue(tory.hasDescendantWithValue("1.1.1"));
    }

    @Test
    void getCode() {
        assertEquals(tory.getCode(), "0");
    }
}