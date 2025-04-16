package hi.verkefni.verkefni3.vinnsla;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SlongurStigarTest {
    private SlongurStigar board;

    @Before
    public void setUp() {
        board = new SlongurStigar();
    }

    @Test
    public void testDesstinationBox() {
        board.addSnakeLadder(10, 2);
        board.addSnakeLadder(3, 15);

        assertEquals(2, board.getDestinationBox(10));
        assertEquals(15, board.getDestinationBox(3));
    }

    @Test
    public void testBoxWithNoSnakeOrLadder() {
        int box = 5;
        assertEquals(box, board.getDestinationBox(box));
    }


}
