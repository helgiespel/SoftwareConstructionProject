package hi.verkefni.verkefni3.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Leikmadur {
    private final StringProperty name; //Geymir nafn á likmanni.
    private final IntegerProperty box; //Geymir reit sem leikmaður er á.
    private final SlongurStigar snakesLadders;

    /**
     * Builder fyrir Leikmaður
     * @param name nafn leikmanns
     * @param snakesLadders slongurStigar hlutur
     */
    public Leikmadur(String name, SlongurStigar snakesLadders) {
        this.name = new SimpleStringProperty(name);
        this.box = new SimpleIntegerProperty(1); //Byrjar á reit 1.
        this.snakesLadders = snakesLadders;
    }

    /**
     * Færir leikmann á nýjan reit
     * @param box reitur sem leikmapur færist til
     * @param max samtals reitir í spilinu
     */
    public void move(int box, int max) {
        if (box > max) {
            System.out.println(getName() + " tried moving to " + box + " but the max is " + max);
        } else {
            int destination = snakesLadders.getDestinationBox(box);
            this.box.set(destination);
        }
    }

    /**
     * Get aðferð fyrir nafn leikamanns, skilar nafn leikmanns
     * @return nafn leikmanns
     */
    public String getName() {
        return name.get();
    }

    /**
     * Get aðferð sem skilar reit
     * @return reitur sem int tala
     */
    public int getBox() {
        return box.get();
    }

    /**
     * Get aðferð fyir reit, skilar reit sem IntegerProperty
     * @return reitur sem IntegerProperty
     */
    public IntegerProperty boxProperty() {
        return box;
    }
}