package hi.verkefni.verkefni3.vinnsla;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class TeningurTest {

    private Teningur dice;

    @Before
    public void setUp() {
        dice = new Teningur();
    }
    @Test
    public void testRoll() {
        dice.roll();
        int diceNumber = dice.getDiceNumber();
        assertTrue(diceNumber >= 1 && diceNumber <= 6);
    }

    @Test
    public void testPropertyMatchesNumber() {
        dice.roll();
        int diceNumber = dice.diceNumber;
        int diceProperty = dice.diceProperty().get();
        assertEquals(diceNumber, diceProperty);
    }



}