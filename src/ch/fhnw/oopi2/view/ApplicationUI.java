package ch.fhnw.oopi2.view;

import ch.fhnw.oopi2.model.Movie;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by ajant on 03.05.2016.
 */
public class ApplicationUI extends GridPane implements ApplicationView {

    private ToolBar _toolBar;
    private Button _btnSave;
    private Button _btnAdd;
    private Button _btnRemove;
    private Button _btnUndo;
    private Button _btnRedo;
    private TextField _tfSearch;
    private SplitPane _splitPane;
    private TableView _tableView;
    private GridPane _gpMovie;

    public ApplicationUI(){
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangeListeners();
        addBindings();
    }

    @Override
    public void setItems(List<Movie> items) {
        _tableView.getItems().addAll(items);
        _tableView.requestLayout();
    }

    private void initializeControls() {

        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        getColumnConstraints().add(column);

        RowConstraints row1 = new RowConstraints();
        getRowConstraints().add(row1);

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.ALWAYS);
        getRowConstraints().add(row2);

        _toolBar = new ToolBar();
        _toolBar.setPadding(new Insets(10.0));
        add(_toolBar, 0, 0);

        _btnSave = new Button();
        _btnSave.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../res/view/javafx/icons/save.svg.png"))));
        _toolBar.getItems().add(_btnSave);
        _toolBar.getItems().add(new Separator());

        _btnAdd = new Button();
        _btnAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../res/view/javafx/icons/add.svg.png"))));
        _toolBar.getItems().add(_btnAdd);

        _btnRemove = new Button();
        _btnRemove.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../res/view/javafx/icons/remove.svg.png"))));
        _toolBar.getItems().add(_btnRemove);
        _toolBar.getItems().add(new Separator());

        _btnUndo = new Button();
        _btnUndo.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../res/view/javafx/icons/undo.svg.png"))));
        _toolBar.getItems().add(_btnUndo);

        _btnRedo = new Button();
        _btnRedo.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../res/view/javafx/icons/redo.svg.png"))));
        _toolBar.getItems().add(_btnRedo);

        final Pane rightSpacer = new Pane();
        HBox.setHgrow(
                rightSpacer,
                Priority.SOMETIMES
        );
        _toolBar.getItems().add(rightSpacer);

        _tfSearch = new TextField();
        _tfSearch.setPromptText("Suche...");
        _toolBar.getItems().add(_tfSearch);

        _splitPane = new SplitPane();
        setFillHeight(_splitPane, true);
        add(_splitPane, 0, 1);

        _tableView = new TableView();
        _tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        final TableColumn tcRowState = new TableColumn<Movie, Image>();
        _tableView.getColumns().add(tcRowState);

        final TableColumn tcYear = new TableColumn<Movie, Integer>("Jahr");
        tcYear.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Movie, Integer> p) {
                return p.getValue().yearOfProductionProperty().asObject();
            }
        });
        _tableView.getColumns().add(tcYear);

        final TableColumn tcTitle = new TableColumn<Movie, String>("Titel");
        tcTitle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movie, String> p) {
                return p.getValue().titleProperty();
            }
        });
        _tableView.getColumns().add(tcTitle);

        final TableColumn tcDirector = new TableColumn<Movie, String>("Regisseur");
        tcDirector.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movie, String> p) {
                return p.getValue().directorProperty();
            }
        });
        _tableView.getColumns().add(tcDirector);

        final TableColumn tcMainActor = new TableColumn<Movie, String>("Hauptdarsteller");
        tcMainActor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movie, String> p) {
                return p.getValue().mainActorProperty();
            }
        });
        _tableView.getColumns().add(tcMainActor);

        _splitPane.getItems().add(_tableView);

        _gpMovie = new GridPane();
        _splitPane.getItems().add(_gpMovie);
    }

    private void layoutControls() {

    }

    private void addEventHandlers() {

    }

    private void addValueChangeListeners(){

    }

    private void addBindings() {

    }
}
