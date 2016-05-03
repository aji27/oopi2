package ch.fhnw.oopi2;

import ch.fhnw.oopi2.view.ApplicationUI;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {



        Parent root = new ApplicationUI();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Oscars für den besten Film");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
