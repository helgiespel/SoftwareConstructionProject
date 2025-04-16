package hi.verkefni.verkefni3.vinnsla;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LeikurTest {
    private Leikur game;

    @Before
    public void gameStarts() {
        game = new Leikur(100);
        game.nyrLeikur();
    }

    @Test
    public void testPlayerExist() {
        Leikmadur playerOne = game.getPlayer(0);
        Leikmadur playerTwo = game.getPlayer(1);
        assertTrue(playerOne != null && playerTwo != null);
    }

    @Test
    public void testPlayersMove() {
        Leikmadur player = game.getPlayer(0);
        int initialPosition = player.getBox();
        game.leikaLeik();
        int finalPosition = player.getBox();
        assertFalse(initialPosition == finalPosition);
    }

    @Test
    public void testSwitchTurn() {
        Leikmadur playerOne = game.getPlayer(0);
        game.leikaLeik();
        Leikmadur playerTwo = game.getCurrentPlayer();
        assertFalse(playerOne == playerTwo);
    }

    @Test
    public void testNoPlayersWhenGameFinishes() {
        while(!game.gameFinishProperty().get()) {
            game.leikaLeik();
        }
        assertTrue(game.gameFinishProperty().get());
        assertFalse(game.isPlayersEmpty());
    }
}
