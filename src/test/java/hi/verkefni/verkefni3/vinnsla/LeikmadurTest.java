package hi.verkefni.verkefni3.vinnsla;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LeikmadurTest {
    private Leikmadur player;
    private SlongurStigar testBoard;

    @Before
    public void setUp() {
        testBoard = new SlongurStigar();
        player = new Leikmadur("Helgi", testBoard);
    }

    @Test
    public void testInitialPosition() {
        assertEquals(1, player.getBox());
    }
    @Test
    public void testPlayerMovesToRightPosition() {
        int destination = 5;
        player.move(5, 100);
        assertEquals(destination, player.getBox());
    }

    @Test
    public void testPlayerMovesWithLadder() {
        int ladderStarts = 10;
        int ladderEnds = 20;
        int max = 100;
        testBoard.addSnakeLadder(ladderStarts, ladderEnds);
        player.move(ladderStarts, max);
        assertEquals(ladderEnds, player.getBox());
    }

    @Test
    public void testPlayerMovesWithSnake() {
        int snakeStart = 20;
        int snakeEnds = 10;
        int max = 100;
        testBoard.addSnakeLadder(snakeStart, snakeEnds);
        player.move(snakeStart, max);
        assertEquals(snakeEnds, player.getBox());
    }

    @Test
    public void testMoveBeyondBoardMax() {
        player.move(110, 100);
        assertEquals(1, player.getBox());
    }
}
