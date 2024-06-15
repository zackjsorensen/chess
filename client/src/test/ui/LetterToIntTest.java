package ui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterToIntTest {
    static LetterToInt lti;


    @BeforeAll
    public static void setup() {
        lti = new LetterToInt();
    }

    @Test
    void testA(){
        assertEquals(1, LetterToInt.convert('A'));
        assertEquals(1, LetterToInt.convert('a'));
    }

    @Test
    void testB(){
        assertEquals(2, LetterToInt.convert('B'));
        assertEquals(2, LetterToInt.convert('b'));
    }

}