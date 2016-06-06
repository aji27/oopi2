package ch.fhnw.oopi2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ajant on 03.05.2016.
 */
public class MovieImpl implements Movie {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty title = new SimpleStringProperty("");
    private final IntegerProperty yearOfAward = new SimpleIntegerProperty(0);
    private final StringProperty mainActor =  new SimpleStringProperty("");
    private final StringProperty director =  new SimpleStringProperty("");
    private final StringProperty titleEnglish = new SimpleStringProperty("");
    private final IntegerProperty yearOfProduction = new SimpleIntegerProperty(0);
    private final StringProperty country = new SimpleStringProperty("");
    private final IntegerProperty duration = new SimpleIntegerProperty(0);
    private final IntegerProperty fsk = new SimpleIntegerProperty(0);
    private final StringProperty genre =  new SimpleStringProperty("");
    private final StringProperty startDate = new SimpleStringProperty("");
    private final IntegerProperty numberOfOscars = new SimpleIntegerProperty(0);

    @Override
    public int getId() {
        return id.get();
    }

    @Override
    public IntegerProperty idProperty() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id.set(id);
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    @Override
    public StringProperty titleProperty() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public int getYearOfAward() {
        return yearOfAward.get();
    }

    @Override
    public IntegerProperty yearOfAwardProperty() {
        return yearOfAward;
    }

    @Override
    public void setYearOfAward(int yearOfAward) {
        this.yearOfAward.set(yearOfAward);
    }

    @Override
    public String getDirector() {
        return director.get();
    }

    @Override
    public StringProperty directorProperty() {
        return director;
    }

    @Override
    public void setDirector(String director) {
        this.director.set(director);
    }

    @Override
    public String getMainActor() {
        return mainActor.get();
    }

    @Override
    public StringProperty mainActorProperty() {
        return mainActor;
    }

    @Override
    public void setMainActor(String mainActor) {
        this.mainActor.set(mainActor);
    }

    @Override
    public String getTitleEnglish() {
        return titleEnglish.get();
    }

    @Override
    public StringProperty titleEnglishProperty() {
        return titleEnglish;
    }

    @Override
    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish.set(titleEnglish);
    }

    @Override
    public int getYearOfProduction() {
        return yearOfProduction.get();
    }

    @Override
    public IntegerProperty yearOfProductionProperty() {
        return yearOfProduction;
    }

    @Override
    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction.set(yearOfProduction);
    }

    @Override
    public String getCountry() {
        return country.get();
    }

    @Override
    public StringProperty countryProperty() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country.set(country);
    }

    @Override
    public int getDuration() {
        return duration.get();
    }

    @Override
    public IntegerProperty durationProperty() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    @Override
    public int getFsk() {
        return fsk.get();
    }

    @Override
    public IntegerProperty fskProperty() {
        return fsk;
    }

    @Override
    public void setFsk(int fsk) {
        this.fsk.set(fsk);
    }

    @Override
    public String getGenre() {
        return genre.get();
    }

    @Override
    public StringProperty genreProperty() {
        return genre;
    }

    @Override
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    @Override
    public String getStartDate() {
        return startDate.get();
    }

    @Override
    public StringProperty startDateProperty() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    @Override
    public int getNumberOfOscars() {
        return numberOfOscars.get();
    }

    @Override
    public IntegerProperty numberOfOscarsProperty() {
        return numberOfOscars;
    }

    @Override
    public void setNumberOfOscars(int numberOfOscars) {
        this.numberOfOscars.set(numberOfOscars);
    }
}