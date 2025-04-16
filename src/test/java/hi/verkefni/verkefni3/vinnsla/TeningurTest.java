package hi.verkefni.verkefni3.vinnsla;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TeningurTest {
    @Test
    public void testRoll() {
        Teningur dice = new Teningur();
        dice.roll();
        int result = dice.getDiceNumber();
        assertTrue(result >= 1 && result <= 6);
    }


}