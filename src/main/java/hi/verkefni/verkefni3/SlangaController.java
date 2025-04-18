package hi.verkefni.verkefni3;

import hi.verkefni.verkefni3.vinnsla.Leikmadur;
import hi.verkefni.verkefni3.vinnsla.Leikur;
import hi.verkefni.verkefni3.vinnsla.LeikurObserver;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;

public class SlangaController implements LeikurObserver {
    @FXML
    private Button fxDice;
    @FXML
    private Button fxNewGame;
    @FXML
    private Label fxMessage1;
    @FXML
    private Label fxMessage2;
    @FXML
    private GridPane fxBoard;
    @FXML
    private List<Node> boxes;
    private Leikur game;
    private final int TOTAL_BOXES = 30;
    private final String[] TENINGS_MYNDIR = {"one", "two", "three", "four", "five", "six"};
    private final String[] BOX_COLOR = {"#1C1C1C", "#0B3D0A", "#2E8B57", "#6A8E22"};

    @Override
    public void onCurrentPlayerChanged(Leikmadur newPlayer) {
        fxMessage1.setText(newPlayer.getName());
        Leikmadur currentPlayer = game.getOtherPlayer();
        int currentPlayerCurrentBox = currentPlayer.getBox();
        fxMessage2.setText("Er á reit " + (currentPlayerCurrentBox));
    }

    /**
     * initialize aðferð sem keyrir þegar viðmótinu er keyrt
     * Býr til nýjan leik
     * Setur leikmenn á byrjunarreit
     * Bindir skilaboð [fxMessage1 og fxMessage2]
     * Setur liti af handahófi í spilið
     * Bindir myndir af tening
     * Bindir myndir af slöngum og stigum
     */
    @FXML
    public void initialize() {
        boxes = fxBoard.getChildren();

        for (int i = 0; i < boxes.size(); i++) {
            System.out.println("Box " + (i + 1) + " -> " + boxes.get(i));
        }
        game = new Leikur(TOTAL_BOXES);
        System.out.println("Game(Leikur) initialized with " + TOTAL_BOXES + " boxes.");

        // Byrja leik sjáfkrafa þegar application er startað
        game.nyrLeikur();
        System.out.println("Game strarted!");
        fxMessage1.setText("Velkominn í");
        fxMessage2.setText("Slöngur og stigar!");

        //Setja leikmenn á fyrsta reit
        updatePlayerImage(0, 1, 1);
        updatePlayerImage(1, 1, 1);

        //Disabela hnappa eftir stöðu á leiknum
        fxNewGame.disableProperty().bind(game.gameFinishProperty().not());
        fxDice.disableProperty().bind(fxNewGame.disableProperty().not());

        // Binda fxMessage1 og fxMessage2
        game.addObserver(this);

        //Setja randum liti á reiti
        applyRandomColors();

        //Default mynd þegar maður keyir appið
        fxDice.getStyleClass().add("default-dice");

        //Skipta um mynd á tening með listener
        game.dicePorperty().addListener((observable, oldValue ,newValue) -> {
                    System.out.println("This is the last number: " + oldValue);
                    System.out.println("This is the new number: " + newValue);
                    fxDice.getStyleClass().removeAll("one", "two", "three", "four", "five", "six", "default-dice");
                    fxDice.getStyleClass().add(TENINGS_MYNDIR[newValue.intValue() - 1]);
                }
        );

        //Skipta um mynd á reit þegar leikmenn færast
        //Færa leikmann 1
        game.getPlayer(0).boxProperty().addListener((observable, oldValue, newValue) -> {
            updatePlayerImage(0, oldValue.intValue(), newValue.intValue());
        });
        //Færa leikmann 2
        game.getPlayer(1).boxProperty().addListener((observable, oldValue, newValue) -> {
            updatePlayerImage(1, oldValue.intValue(), newValue.intValue());
        });

        //Setja upp myndir af slöngum og stigum
        updateSnakesAndLadders();

    }

    /**
     * Handler fyrir nýr leikur hnapp
     * Býr til nýjan leik
     * Setur leikmenn á byrjunarreit
     */
    @FXML
    private void newGameHandler() {
        System.out.println("New Game button was pressed!");
        game.nyrLeikur();
        fxMessage1.setText("Leikur í gangi!");

        //Setja default mynd af tening
        fxDice.getStyleClass().removeAll("one", "two", "three", "four", "five", "six");
        fxDice.getStyleClass().add("default-dice");

        clearPlayerImages();
        newPlayerListener();
        updatePlayerImage(0, game.getPlayer(0).getBox(), 1);
        updatePlayerImage(1, game.getPlayer(1).getBox(), 1);
    }

    /**
     * Handler fyrir tening
     * Athugar hvort að leikur sé búinn, ef ekki
     * Lætur fxMessage1 birta skilaboð um að leikur sé búinn
     */
    @FXML
    private void diceHandler() {
        System.out.println("Dice was pressed!");
        boolean gameFinished = game.leikaLeik();
        if(gameFinished) {
            System.out.println("Game is finished!");
            fxMessage1.setText("Leikur búinn!");
        }
    }

    /**
     * Hjálparaðferð til að setja styleclass á reiti þar sem leikmaður lendir
     * Tekur styclass af gamla reit
     * Setur Styleclass á nýja reit
     * @param playerIndex tala fyir leikmann (0 ef leikmaður 1, 1 ef leikmapur 2)
     * @param oldBox gamli reitur sem leikmapur lenti á
     * @param newBox nýji reitur sem leikmaður lendir á
     */
    private void updatePlayerImage(int playerIndex, int oldBox, int newBox) {
        Platform.runLater(() -> {
            if (oldBox > 0 && oldBox <= boxes.size()) {
                Node oldNode = boxes.get(oldBox - 1);
                if (oldNode instanceof Label) {
                    oldNode.getStyleClass().remove("player-" + playerIndex);
                }
            }
            if (newBox > 0 && newBox <= boxes.size()) {
                Node newNode = boxes.get(newBox - 1);
                if (newNode instanceof Label) {
                    newNode.getStyleClass().add("player-" + playerIndex);
                }
            }
        });
    }

    /**
     * Hjálparaðferð til að setja upp myndir af snákum og stigum á rétta reiti.
     */
    private void updateSnakesAndLadders() {
        Platform.runLater(() -> {
            for (int start : game.getSnakesLadders().getAllKeys()) {
                Node startNode = boxes.get(start - 1);
                if (startNode instanceof Label) {
                    if (game.getSnakesLadders().getDestinationBox(start) < start) {
                        startNode.getStyleClass().add("snake");
                    } else {
                        startNode.getStyleClass().add("ladder");
                    }
                }
            }
        });
    }

    /**
     * Hjálparaðferð til að velja lit af handahófi fyrir reiti
     */
    private void applyRandomColors() {
        Random random = new Random();
        for (Node node: fxBoard.getChildren()) {
            if (node instanceof Label label) {
                String randomColor = BOX_COLOR[random.nextInt(BOX_COLOR.length)];
                label.setStyle("-fx-background-color: " + randomColor + ";");
            }
        }
    }

    /**
     * Hjálparaðferð sem tekur gamlar myndir af leikmönnum þegar stofanð er nýjan leik.
     */
    private void clearPlayerImages() {

            for (Node node : boxes) {
                if (node instanceof Label) {
                    node.getStyleClass().removeAll("player-0", "player-1");
                }
            }
    }

    /**
     * Hjálparaðferð sem býr til nýja listener þegar það er búið til nýjan leik.
     * Listener til að vakta hvernig leikmenn færast í leiknum
     */
    private void newPlayerListener() {
        game.getPlayer(0).boxProperty().removeListener((observable, oldValue, newValue) -> {});
        game.getPlayer(1).boxProperty().removeListener((observable, oldValue, newValue) -> {});

        game.getPlayer(0).boxProperty().addListener((observable, oldValue, newValue) -> {
            updatePlayerImage(0, oldValue.intValue(), newValue.intValue());
        });

        game.getPlayer(1).boxProperty().addListener((observable, oldValue, newValue) -> {
            updatePlayerImage(1, oldValue.intValue(), newValue.intValue());
        });
    }
}