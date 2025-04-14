package hi.verkefni.verkefni3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SlangaApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hi/verkefni/verkefni3/slanga-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/hi/verkefni/verkefni3/css/style.css")).toExternalForm());

        stage.setTitle("Snakes&Ladders!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}