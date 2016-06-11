package ch.fhnw.oopi2;

import ch.fhnw.oopi2.model.FileBackendModel;
import ch.fhnw.oopi2.model.Model;
import ch.fhnw.oopi2.presenter.OscarAppPresenter;
import ch.fhnw.oopi2.view.OscarAppView;
import ch.fhnw.oopi2.view.View;
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
        //Locale.setDefault(Locale.FRENCH);

        FileBackendModel.initialize();

        OscarAppView oscarAppView = new OscarAppView();

        Model model = FileBackendModel.getCurrent();
        View view = oscarAppView;
        OscarAppPresenter presenter = new OscarAppPresenter(view, model);

        GridPane root = oscarAppView;
        root.setPrefSize(PREF_WINDOW_WIDTH, PREF_WINDOW_HEIGHT);
        root.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Oscars für den besten Film");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
