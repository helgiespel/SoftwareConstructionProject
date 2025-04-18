package hi.verkefni.verkefni3.vinnsla;

import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;


public class Leikur {

    //Observers fyrir observer pattern
    private List<LeikurObserver> observers = new ArrayList<>();

    //Tilviksbreytur
    private List<Leikmadur> players;
    private Teningur dice;
    private SlongurStigar snakesLadders;
    private  int currentTurn;

    //Tilviksbreytur vaktaðar af notendaviðmótinu
    private final BooleanProperty gameFinish;
    private final SimpleStringProperty winner;
    private final SimpleStringProperty nextPlayer;
    private final ObjectProperty<Leikmadur> currentPlayer = new SimpleObjectProperty<>();
    private final IntegerProperty diceValue;

    //Tilvikbreyta fyrir damtals reitir
    private int totalBox;

    //Final tilviksbreytur fyrir leikmennina
    private final String PLAYER_1 = "Leikmaður 1";
    private final String PLAYER_2 = "Leikmaður 2";

    /**
     * Smiður fyrir Leikur
     * @param totalBox fjöldi reita á spilinu
     */
    public Leikur(int totalBox){
        this.totalBox = totalBox;
        this.players = new ArrayList<>();
        this.dice = new Teningur();
        this.snakesLadders = new SlongurStigar();
        this.currentTurn = 0;

        this.gameFinish = new SimpleBooleanProperty(true);
        this.winner = new SimpleStringProperty("");
        this.nextPlayer = new SimpleStringProperty("");
        this.diceValue = new SimpleIntegerProperty();
    }

    /**
     * Aðferð sem er kölluð í hvert skipti notandi kastar tening.
     * Aðferð kastar tening, færir leikmann, skiptir á næsta leikmann
     * @return skilar true ef leik er lokið
     */
    public boolean leikaLeik() {
        //Kastar tening
        dice.roll();
        int diceResult = dice.getDice();
        System.out.println("The dice result is " + diceResult + "!");

        //Tékka á snákum og stiga, tékka ef likurinn er búinn, færa leikmann
        Leikmadur currentPlayer = players.get(currentTurn);
        int newBox = currentPlayer.getBox() + diceResult;

         if(newBox >= totalBox) {
             currentPlayer.move(totalBox, totalBox);
             gameFinish.set(true);
             System.out.println(currentPlayer.getName() + " won the game!");
             nextPlayer.set("None");
             return true;

         } else {
             newBox = snakesLadders.getDestinationBox(newBox);
             currentPlayer.move(newBox, totalBox);

             System.out.println(currentPlayer.getName() + " moved to moved to " + currentPlayer.getBox());
         }
        //Uppfæra nextPlayer
        switchTurn();
        //Skilar true ef likurinn er búinn
        return false;
    }

    /**
     * Byrjar nýjan leik. Leikmenn settir á reit eitt.
     */
    public void nyrLeikur() {
        gameFinish.set(false);
        winner.set("");
        players.clear();

        players.add(new Leikmadur(PLAYER_1, snakesLadders));
        players.add(new Leikmadur(PLAYER_2, snakesLadders));

        for (Leikmadur player : players) {
            player.move(1, totalBox);
        }

        currentTurn = 0;
        currentPlayer.set(players.get(currentTurn));

        if (!players.isEmpty()) {
            nextPlayer.set(players.get(currentTurn).getName());
        } else {
            nextPlayer.set("There are no players");
        }

        setUpSnakesLadders();
        System.out.println("Snakes and ladders added.");
    }

    /**
     * Hjálparaðferð sem skiptir um leikmann sem á að gera næst.
     */
    private void switchTurn() {
        currentTurn = (currentTurn + 1) % players.size();
        nextPlayer.set(players.get(currentTurn).getName());
        notifyCurrentPlayerChanged(); //lætur vita að leikmaður afi breyst
    }

    /**
     * hlustar hvort að það sé búið að skipta um leikman sem á að gera næst
     */
    private void notifyCurrentPlayerChanged() {
        for (LeikurObserver observer : observers) {
            observer.onCurrentPlayerChanged(currentPlayer.get());
        }
    }

    //Get aðferðir
    /**
     * Get aðferð sem skilar true ef liekurinn er búinn
     * @return boolean breyat sem segir tiil hvort að leikurinn sé búinn
     */
    public BooleanProperty gameFinishProperty() {
        return gameFinish;
    }

    /**
     * Get aðferð fyrir núverandi leikmann, skilar núverandi leikmann sem ObjectProperty
     * @return núverandi leikmaður sem ObjectProperty<Leikmadur>
     */
    public ObjectProperty<Leikmadur> currentPlayerProperty() {
        return currentPlayer;
    }

    /**
     * Get að ferð fyrir leikmann sem á að gera næast
     * @return næsti leikmaður sem StringProperty
     */
    public StringProperty nextPlayerProperty() {
        return nextPlayer;
    }

    /**
     * Get aðferð fyrir teningstölu
     * @return teningstala
     */
    public int getDiceValue() {
        return diceValue.get();
    }

    /**
     * Get aðferð fyrir teningstölu í IntegerProperty
     * @return teningstala í IntegerProperty
     */
    public IntegerProperty dicePorperty() {
        return dice.diceProperty();
    }

    /**
     * Get aðferð fyir leikmann sem á að gera núna.
     * @return skilar leikmann af klasa Leikmadur
     */
    public Leikmadur getCurrentPlayer() {
        return players.get(currentTurn);
    }

    /**
     * Get aðferðs sem skilar leikmann sem á ekki að gera
     * @return Leikmapur sem á ekki að gera
     */
    public Leikmadur getOtherPlayer() {
        if (getCurrentPlayer() == players.get(0)) {
            return players.get(1);
        } else {
            return players.get(0);
        }
    }

    /**
     * Get aðferð fyrir snáka/stiga
     * @return skilar hlut af klasanum SlongurStigar
     */
    public SlongurStigar getSnakesLadders() {
        return snakesLadders;
    }

    /**
     * Skilar leikmann eftir havaða staki hann er í lisytaum
     * 0 skilar leikmann í fyrsta staki
     * 1 skilar leikmann í öðru staki
     * ef kallað er á fall með tölu sem er stærri en players-listi (þ.e. 2) skilar fallið null.
     * @param index stakið í players-list (0 eða 1)
     * @return skilar hlut af klasa Leikmann.
     */
    public Leikmadur getPlayer(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        }
        return null;
    }

    /**
     * skilar hvort að það séu leikmenn í leiknum
     * @return
     */
    public boolean isPlayersEmpty(){
        return players.isEmpty();
    }

    /**
     * hjálparaðferð fyrir aðra klasa til að bæta við listener
     * @param observer
     */
    public void addObserver(LeikurObserver observer) {
        observers.add(observer);
    }

    /**
     * hjálapraðferp fyrir aðra klasa til að eyða litenera
     * @param observer
     */
    public void removeObserver(LeikurObserver observer) {
        observers.remove(observer);
    }

    /**
     * Aðferð sem setur upp snáka og stiga
     */
    public void setUpSnakesLadders() {
        //Bæta stigum
        snakesLadders.addSnakeLadder(3, 11);
        snakesLadders.addSnakeLadder(6, 17);
        snakesLadders.addSnakeLadder(9, 18);
        snakesLadders.addSnakeLadder(11, 20);

        //Bæta snákum
        snakesLadders.addSnakeLadder(14, 4);
        snakesLadders.addSnakeLadder(19, 8);
        snakesLadders.addSnakeLadder(22, 20);
        snakesLadders.addSnakeLadder(27, 17);
        snakesLadders.addSnakeLadder(25, 7);
    }
}
