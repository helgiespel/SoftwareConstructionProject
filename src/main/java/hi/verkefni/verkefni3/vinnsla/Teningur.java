package hi.verkefni.verkefni3.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;

public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty numberPorperty = new SimpleIntegerProperty(1);

    int diceNumber;

    /**
     * Kastar tening þannig að fundinn sé tala af handahófi á bilinu 1 til MAX+1.
     */
    public void roll() {
        Random random = new Random();
        diceNumber = random.nextInt(MAX) + 1;
        numberPorperty.set(diceNumber);
    }

    /**
     * get aðferð sem skilar teningstölu.
     * @return teningstala
     */
    public int getDice() {
        return numberPorperty.get();
    }

    /**
     * get aðferð sem skilar teningstölu sem Integer Property
     * @return teningstala sem integer property
     */
    public IntegerProperty diceProperty() {
        return numberPorperty;
    }
}