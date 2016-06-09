package ch.fhnw.oopi2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ajant on 03.05.2016.
 */
public class MovieImpl implements Movie {
    private final IntegerProperty _id = new SimpleIntegerProperty(0);
    private final StringProperty _title = new SimpleStringProperty("");
    private final IntegerProperty _yearOfAward = new SimpleIntegerProperty(0);
    private final StringProperty _mainActor =  new SimpleStringProperty("");
    private final StringProperty _director =  new SimpleStringProperty("");
    private final StringProperty _titleEnglish = new SimpleStringProperty("");
    private final IntegerProperty _yearOfProduction = new SimpleIntegerProperty(0);
    private final StringProperty _country = new SimpleStringProperty("");
    private final IntegerProperty _duration = new SimpleIntegerProperty(0);
    private final IntegerProperty _fsk = new SimpleIntegerProperty(0);
    private final StringProperty _genre =  new SimpleStringProperty("");
    private final StringProperty _startDate = new SimpleStringProperty("");
    private final IntegerProperty _numberOfOscars = new SimpleIntegerProperty(0);

    public int getId() {
        return _id.get();
    }

    public IntegerProperty idProperty() {
        return _id;
    }

    public void setId(int id) {
        this._id.set(id);
    }

    public String getTitle() {
        return _title.get();
    }

    public StringProperty titleProperty() {
        return _title;
    }

    public void setTitle(String title) {
        this._title.set(title);
    }

    public int getYearOfAward() {
        return _yearOfAward.get();
    }

    public IntegerProperty yearOfAwardProperty() {
        return _yearOfAward;
    }

    public void setYearOfAward(int yearOfAward) {
        this._yearOfAward.set(yearOfAward);
    }

    public String getDirector() {
        return _director.get();
    }

    public StringProperty directorProperty() {
        return _director;
    }

    public void setDirector(String director) {
        this._director.set(director);
    }

    public String getMainActor() {
        return _mainActor.get();
    }

    public StringProperty mainActorProperty() {
        return _mainActor;
    }

    public void setMainActor(String mainActor) {
        this._mainActor.set(mainActor);
    }

    public String getTitleEnglish() {
        return _titleEnglish.get();
    }

    public StringProperty titleEnglishProperty() {
        return _titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this._titleEnglish.set(titleEnglish);
    }

    public int getYearOfProduction() {
        return _yearOfProduction.get();
    }

    public IntegerProperty yearOfProductionProperty() {
        return _yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this._yearOfProduction.set(yearOfProduction);
    }

    public String getCountry() {
        return _country.get();
    }

    public StringProperty countryProperty() {
        return _country;
    }

    public void setCountry(String country) {
        this._country.set(country);
    }

    public int getDuration() {
        return _duration.get();
    }

    public IntegerProperty durationProperty() {
        return _duration;
    }

    public void setDuration(int duration) {
        this._duration.set(duration);
    }

    public int getFsk() {
        return _fsk.get();
    }

    public IntegerProperty fskProperty() {
        return _fsk;
    }

    public void setFsk(int fsk) {
        this._fsk.set(fsk);
    }

    public String getGenre() {
        return _genre.get();
    }

    public StringProperty genreProperty() {
        return _genre;
    }

    public void setGenre(String genre) {
        this._genre.set(genre);
    }

    public String getStartDate() {
        return _startDate.get();
    }

    public StringProperty startDateProperty() {
        return _startDate;
    }

    public void setStartDate(String startDate) {
        this._startDate.set(startDate);
    }

    public int getNumberOfOscars() {
        return _numberOfOscars.get();
    }

    public IntegerProperty numberOfOscarsProperty() {
        return _numberOfOscars;
    }

    public void setNumberOfOscars(int numberOfOscars) {
        this._numberOfOscars.set(numberOfOscars);
    }
}