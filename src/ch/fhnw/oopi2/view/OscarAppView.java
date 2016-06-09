package ch.fhnw.oopi2.view;

import ch.fhnw.oopi2.model.FSK;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.presenter.Presenter;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ajant on 03.05.2016.
 */
public class OscarAppView extends GridPane implements View {

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
    private HBox _hbCountry;
    private ImageView _ivPoster;
    private HBox _hbOscars;
    private Spinner<Integer> _spYear;
    private TextField _tfTitle;
    private TextField _tfDirector;
    private TextField _tfMainActor;
    private TextField _tfTitleEnglish;
    private TextField _tfGenre;
    private Spinner<Integer> _spYearOfProduction;
    private TextField _tfCountry;
    private Spinner<Integer> _spDuration;
    private ComboBox<Integer> _cbFSK;
    private DatePicker _dpStartDate;
    private Spinner<Integer> _spNumberOfOscars;

    private Presenter _presenter;

    public OscarAppView(){
        initialize();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }

    @Override
    public void setItems(List<Movie> items) {
        _tableView.getItems().clear();
        _tableView.getItems().addAll(items);

        _tableView.getSelectionModel().selectFirst();
    }

    @Override
    public void hideMovieDetailPane() {
        _gpMovie.setVisible(false);
    }

    @Override
    public void showMovieDetailPane() {
        _gpMovie.setVisible(true);
    }

    @Override
    public void setPoster(Integer movieId) {
        final InputStream posterResourceStream = getClass().getResourceAsStream(String.format("../res/view/javafx/posters/%s.jpg", movieId));
        if (posterResourceStream != null) {
            _ivPoster.setImage(new Image(posterResourceStream));
        }
    }

    @Override
    public void setYear(Integer year) {
        _lblYear.setText(year.toString());
        _spYear.getValueFactory().setValue(year);
    }

    @Override
    public void setTitle(String title) {
        _lblTitle.setText(title);
        _tfTitle.setText(title);
    }

    @Override
    public void setDirector(String director) {
        _tfDirector.setText(director);
    }

    @Override
    public void setDirectorHeading(String directorHeading) {
        _lblDirector.setText(directorHeading);
    }

    @Override
    public void setMainActor(String mainActor) {
        _tfMainActor.setText(mainActor);
    }

    @Override
    public void setMainActorHeading(String mainActorHeading) {
        _lblMainActor.setText(mainActorHeading);
    }

    @Override
    public void setTitleEnglish(String titleEnglish) {
        _tfTitleEnglish.setText(titleEnglish);
    }

    @Override
    public void setGenre(String genre) {
        _tfGenre.setText(genre);
    }

    @Override
    public void setYearOfProduction(Integer yearOfProduction) {
        _spYearOfProduction.getValueFactory().setValue(yearOfProduction);
    }

    @Override
    public void setCountry(String country) {
        _tfCountry.setText(country);

        _hbCountry.getChildren().clear();

        if (country != null && !country.equals("")) {
            for (String c : country.split("/")) {
                if (c != null) {
                    final InputStream countryResourceStream = getClass().getResourceAsStream(String.format("../res/view/javafx/flags/%s.png", c.trim()));
                    if (countryResourceStream != null) {
                        _hbCountry.getChildren().add(new ImageView(new Image(countryResourceStream)));
                    }
                }
            }
        }
    }

    @Override
    public void setDuration(Integer duration) {
        _spDuration.getValueFactory().setValue(duration);
    }

    @Override
    public void setFsk(Integer fsk) {
        _cbFSK.setValue(fsk);
    }

    @Override
    public void setStartDate(Date startDate) {
        _dpStartDate.setValue(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Override
    public void setNumberOfOscars(Integer numberOfOscars) {
        _spNumberOfOscars.getValueFactory().setValue(numberOfOscars);

        _hbOscars.getChildren().clear();

        final InputStream oscarResourceStream = getClass().getResourceAsStream("../res/view/javafx/Oscar-logo.png");
        if (oscarResourceStream != null) {
            final Image oscarImage = new Image(oscarResourceStream, 30, 76, true, true);
            for (int i = 1; i <= numberOfOscars; i++) {
                ImageView ivOscar = new ImageView(oscarImage);
                StackPane spOscar = new StackPane(ivOscar);
                spOscar.setPadding(new Insets(0,10,0,0));
                _hbOscars.getChildren().add(spOscar);
            }
        }
    }

    private void initialize() {
        initializeComponent();
        addEventHandlers();
        addValueChangeListeners();
    }

    private void initializeComponent() {

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
                return p.getValue().yearOfAwardProperty().asObject();
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
        _gpMovie.setMaxWidth(Double.MAX_VALUE);

        final HBox hbMovie = new HBox(_gpMovie);
        HBox.setHgrow(_gpMovie, Priority.ALWAYS);

        final ScrollPane spMovie = new ScrollPane(hbMovie);
        spMovie.setMaxWidth(Double.MAX_VALUE);
        spMovie.setFitToHeight(true);
        spMovie.setFitToWidth(true);

        ColumnConstraints _gpCol = new ColumnConstraints();
        _gpCol.setHgrow(Priority.ALWAYS);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);
        _gpMovie.getColumnConstraints().add(_gpCol);

        _lblYear = new Label();
        _lblYear.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-wrap-text: true;");
        _gpMovie.add(_lblYear, 0, 0, 2, 1);

        _hbCountry = new HBox();
        final StackPane spCountry = new StackPane(_hbCountry);
        spCountry.setPadding(new Insets(0,10,0,0));
        spCountry.setAlignment(Pos.TOP_CENTER);
        _ivPoster = new ImageView();
        final HBox hbPoster = new HBox(spCountry, _ivPoster);
        hbPoster.setAlignment(Pos.TOP_RIGHT);
        _gpMovie.add(hbPoster, 3, 0, 1, 5);

        _lblTitle = new Label();
        _lblTitle.setStyle("-fx-font-size: 36px;-fx-font-weight: bold;-fx-wrap-text: true;");
        _gpMovie.add(_lblTitle, 0, 1, 3, 1);

        _lblDirector = new Label();
        _lblDirector.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-wrap-text: true;");
        _gpMovie.add(_lblDirector, 0, 2, 3, 1);

        _lblMainActor = new Label();
        _lblMainActor.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-wrap-text: true;");
        _gpMovie.add(_lblMainActor, 0, 3, 3, 1);

        _hbOscars = new HBox();
        _gpMovie.add(_hbOscars, 0, 4, 3, 1);
        GridPane.setHgrow(_hbOscars, Priority.ALWAYS);

        final GridPane gpMovieEditable = new GridPane();
        gpMovieEditable.setHgap(10);
        gpMovieEditable.setVgap(10);
        gpMovieEditable.setMaxWidth(Double.MAX_VALUE);
        gpMovieEditable.getColumnConstraints().add(_gpCol);
        gpMovieEditable.getColumnConstraints().add(_gpCol);
        gpMovieEditable.getColumnConstraints().add(_gpCol);
        gpMovieEditable.getColumnConstraints().add(_gpCol);
        _gpMovie.add(gpMovieEditable, 0, 5, 4, 1);

        _spYear = new Spinner<>();
        _spYear
            .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1929, LocalDateTime.now().getYear()));
        gpMovieEditable.add(new Label("Jahr"), 0, 5); // Anmerkung des Entwicklers: Mir ist bewusst, dass der Row-Index ab hier 'eigentlich' falsch ist, aber Java stellt es korrekt dar :-).
        gpMovieEditable.add(_spYear, 1, 5);

        _tfTitle = new TextField();
        gpMovieEditable.add(new Label("Titel"), 0, 6);
        gpMovieEditable.add(_tfTitle, 1, 6, 3, 1);

        _tfDirector = new TextField();
        gpMovieEditable.add(new Label("Regisseur"), 0, 7);
        gpMovieEditable.add(_tfDirector, 1, 7, 3, 1);

        _tfMainActor = new TextField();
        gpMovieEditable.add(new Label("Hauptdarsteller"), 0, 8);
        gpMovieEditable.add(_tfMainActor, 1, 8, 3, 1);

        _tfTitleEnglish = new TextField();
        gpMovieEditable.add(new Label("englischer Titel"), 0, 9);
        gpMovieEditable.add(_tfTitleEnglish, 1, 9, 3, 1);

        _tfGenre = new TextField();
        gpMovieEditable.add(new Label("Genre"), 0, 10);
        gpMovieEditable.add(_tfGenre, 1, 10);

        _spYearOfProduction = new Spinner();
        _spYearOfProduction
                .setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1929, LocalDateTime.now().getYear()));
        gpMovieEditable.add(new Label("Produktionsjahr"), 2, 10);
        gpMovieEditable.add(_spYearOfProduction, 3, 10);

        _tfCountry = new TextField();
        gpMovieEditable.add(new Label("Land"), 0, 11);
        gpMovieEditable.add(_tfCountry, 1, 11);

        _spDuration = new Spinner();
        _spDuration.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 240));
        gpMovieEditable.add(new Label("Länge (Minuten)"), 2, 11);
        gpMovieEditable.add(_spDuration, 3, 11);

        _cbFSK = new ComboBox<>();
        _cbFSK.setItems(FXCollections.observableArrayList(Arrays.stream(FSK.values()).map(f -> f.getValue()).collect(Collectors.toList())));
        class FskCell extends ListCell<Integer> {
            FskCell() {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (Arrays.stream(FSK.values()).anyMatch(f -> f.getValue() == item)) {
                    final InputStream posterResourceStream = getClass().getResourceAsStream(String.format("../res/view/javafx/fsk_labels/FSK_ab_%s_logo_Dec_2008.svg.png", item));
                    if (posterResourceStream != null) {
                        setGraphic(new ImageView(new Image(posterResourceStream)));
                        return;
                    }
                }

                setGraphic(null);
            }
        }
        _cbFSK.setCellFactory(param -> new FskCell());
        _cbFSK.setButtonCell(new FskCell());
        gpMovieEditable.add(new Label("FSK-Altersfreigabe"), 0, 12);
        gpMovieEditable.add(_cbFSK, 1, 12);

        _dpStartDate = new DatePicker();
        gpMovieEditable.add(new Label("Kinostart"), 2, 12);
        gpMovieEditable.add(_dpStartDate, 3, 12);

        _spNumberOfOscars = new Spinner<>();
        _spNumberOfOscars.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11));
        gpMovieEditable.add(new Label("Oscars"), 0, 13);
        gpMovieEditable.add(_spNumberOfOscars, 1, 13);

        _splitPane.getItems().add(spMovie);
    }

    private void addEventHandlers() {
        _tableView.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            int selectedMovieId = -1;

            if (newValue != null && newValue instanceof Movie) {
                selectedMovieId = ((Movie)newValue).getId();
            }
            _presenter.onSelectedItemChanged(selectedMovieId);
        });
    }

    private void addValueChangeListeners(){
        _spYear.getValueFactory().valueProperty()
                .addListener((observable, oldValue, newValue) -> _presenter.onYearChanged(newValue));
        _tfTitle.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onTitleChanged(newValue));
        _tfDirector.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onDirectorChanged(newValue));
        _tfMainActor.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onMainActorChanged(newValue));
        _tfTitleEnglish.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onTitleEnglishChanged(newValue));
        _tfGenre.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onGenreChanged(newValue));
        _spYearOfProduction.getValueFactory().valueProperty()
                .addListener((observable, oldValue, newValue) -> _presenter.onYearOfProductionChanged(newValue));
        _tfCountry.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onCountryChanged(newValue));
        _spDuration.getValueFactory().valueProperty()
                .addListener((observable, oldValue, newValue) -> _presenter.onDurationChanged(newValue));
        _cbFSK.valueProperty().addListener((observable, oldValue, newValue) -> _presenter.onFskChanged(newValue));
        _dpStartDate.valueProperty()
                .addListener((observable, oldValue, newValue) -> _presenter.onStartDateChanged(Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        _spNumberOfOscars.getValueFactory().valueProperty()
                .addListener((observable, oldValue, newValue) -> _presenter.onNumberOfOscarsChanged(newValue));
        _tfSearch.textProperty().addListener((observable, oldValue, newValue) -> _presenter.onSearchTextChanged(newValue));
    }
}
