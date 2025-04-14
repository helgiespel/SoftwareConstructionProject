package hi.verkefni.verkefni3.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import  javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Set;

public class SlongurStigar {
    private HashMap<Integer, Integer> slongurStigar;
    private final StringProperty message;

    /**
     * Builder fyrir slongur og stigar byr til n´ytt HashMap
     */
    public SlongurStigar() {
        slongurStigar = new HashMap<>();
        message = new SimpleStringProperty("");
    }

    /**
     * Bætir við slöngu eða stiga.
     * Setur hvaðan og hvert slangan/stígin fer.
     * @param start Byrjun á slöngu/stiga.
     * @param end Endir á slöngu/stiga.
     */
    public void addSnakeLadder(int start, int end){
        slongurStigar.put(start, end);
    }

    /**
     * get aðferð fyrir reit sem slanga/stigi sendir leikmann til
     * ef það er ekki slanga/stigi á reit birtast skilaboð um það
     * ef það er ekki slanga/stígi á reit skilar aðferðin sama reit og leikmapur er í
     * @param box reitur sem leikmaður er í
     * @return skilar reit sem slanga/stígi sendir leikmann til (destination)
     */
    public int getDestinationBox(int box) {
        if (slongurStigar.containsKey(box)) {
            int destination = slongurStigar.get(box);
            message.set("You moved from box " + box + " to box " + destination);
            return destination;
        }
        message.set("There are no ladders in this box: " + box);
        return box;
    }

    /**
     * get aðferð sem skilar byrjunarreit slongu/stiga.
     * @return Integer listi með alla byrjunarreiti
     */
    public Set<Integer> getAllKeys() {
        return slongurStigar.keySet();
    }
}
