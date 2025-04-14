module hi.verkefni.verkefni3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hi.verkefni.verkefni3 to javafx.fxml;
    exports hi.verkefni.verkefni3;
    exports hi.verkefni.verkefni3.vinnsla;
    opens hi.verkefni.verkefni3.vinnsla to javafx.fxml;
}