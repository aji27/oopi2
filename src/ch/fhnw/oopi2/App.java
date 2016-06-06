package ch.fhnw.oopi2;

import ch.fhnw.oopi2.model.MovieRepositoryImpl;
import ch.fhnw.oopi2.presenter.Presenter;
import ch.fhnw.oopi2.view.ApplicationUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static final double PREF_WINDOW_WIDTH = 800.0;
    public static final double PREF_WINDOW_HEIGHT = 600.0;

    @Override
    public void start(Stage primaryStage) throws UnsupportedEncodingException {
        MovieRepositoryImpl.initialize();

        ApplicationUI ui = new ApplicationUI();
        Presenter presenter = new Presenter(ui, MovieRepositoryImpl.getCurrent());

        GridPane root = ui;
        root.setPrefSize(PREF_WINDOW_WIDTH, PREF_WINDOW_HEIGHT);
        root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Oscars für den besten Film");
        primaryStage.setScene(scene);
        primaryStage.show();

        presenter.initialize();
    }
}
