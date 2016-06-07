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
    private Label _lblYear;
    private Label _lblTitle;
    private Label _lblDirector;
    private Label _lblMainActor;
    private ImageView _ivCountry;
    private ImageView _ivPoster;
    private ImageView _ivOscars;
    private Spinner<Integer> _spYear;
    private TextField _tfTitle;
    private TextField _tfDirector;
    private TextField _tfMainActor;
    private TextField _tfTitleEN;
    private TextField _tfGenre;
    private Spinner<Integer> _spProductionYear;
    private TextField _tfCountry;
    private Spinner<Integer> _spDuration;
    private ComboBox<Integer> _cbFSK;
    private DatePicker _dpStartDate;
    private Spinner<Integer> _spNumberOfOscars;

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
        _gpMovie.setPadding(new Insets(10.0));
        _gpMovie.setHgap(10);
        _gpMovie.setVgap(10);

        ColumnConstraints _gpCol = new ColumnConstraints();
        _gpCol.setHgrow(Priority.SOMETIMES);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);

        _lblYear = new Label();
        _gpMovie.add(_lblYear, 0, 0, 2, 1);

        _ivCountry = new ImageView();
        _gpMovie.add(_ivCountry, 2, 0);

        _ivPoster = new ImageView();
        _gpMovie.add(_ivPoster, 3, 1, 1, 4);

        _lblTitle = new Label();
        _gpMovie.add(_lblTitle, 0, 1, 3, 1);

        _lblDirector = new Label();
        _gpMovie.add(_lblDirector, 0, 2, 3, 1);

        _lblMainActor = new Label();
        _gpMovie.add(_lblMainActor, 0, 3, 3, 1);

        _ivOscars = new ImageView();
        _gpMovie.add(_ivOscars, 0, 4, 3, 1);

        _spYear = new Spinner<>();
        _gpMovie.add(new Label("Jahr"), 0, 5);
        _gpMovie.add(_spYear, 1, 5);

        _tfTitle = new TextField();
        _gpMovie.add(new Label("Titel"), 0, 6);
        _gpMovie.add(_tfTitle, 1, 6, 3, 1);

        _tfDirector = new TextField();
        _gpMovie.add(new Label("Regisseur"), 0, 7);
        _gpMovie.add(_tfDirector, 1, 7, 3, 1);

        _tfMainActor = new TextField();
        _gpMovie.add(new Label("Hauptdarsteller"), 0, 8);
        _gpMovie.add(_tfMainActor, 1, 8, 3, 1);

        _tfTitleEN = new TextField();
        _gpMovie.add(new Label("englischer Titel"), 0, 9);
        _gpMovie.add(_tfTitleEN, 1, 9, 3, 1);

        _tfGenre = new TextField();
        _gpMovie.add(new Label("Genre"), 0, 10);
        _gpMovie.add(_tfGenre, 1, 10);

        _spProductionYear = new Spinner();
        _gpMovie.add(new Label("Produktionsjahr"), 2, 10);
        _gpMovie.add(_spProductionYear, 3, 10);

        _tfCountry = new TextField();
        _gpMovie.add(new Label("Land"), 0, 11);
        _gpMovie.add(_tfCountry, 1, 11);

        _spDuration = new Spinner();
        _gpMovie.add(new Label("Länge (Minuten)"), 2, 11);
        _gpMovie.add(_spDuration, 3, 11);

        _cbFSK = new ComboBox<>();
        _gpMovie.add(new Label("FSK-Altersfreigabe"), 0, 12);
        _gpMovie.add(_cbFSK, 1, 12);

        _dpStartDate = new DatePicker();
        _gpMovie.add(new Label("Kinostart"), 2, 12);
        _gpMovie.add(_dpStartDate, 3, 12);

        _spNumberOfOscars = new Spinner<>();
        _gpMovie.add(new Label("Oscars"), 0, 13);
        _gpMovie.add(_spNumberOfOscars, 1, 13);

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
