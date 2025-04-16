package hi.verkefni.verkefni3.vinnsla;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TeningurTest {
    @Test
    public void testRoll() {
        Teningur dice = new Teningur();
        dice.roll();
        int diceNumber = dice.getDiceNumber();
        assertTrue(diceNumber >= 1 && diceNumber <= 6);
    }

    @Test
    public void testPropertyMatchesNumber() {
        Teningur dice = new Teningur();
        dice.roll();
        int diceNumber = dice.diceNumber;
        int diceProperty = dice.diceProperty().get();
        assertEquals(diceNumber, diceProperty);
    }


}