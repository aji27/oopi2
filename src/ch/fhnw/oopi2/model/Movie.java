package ch.fhnw.oopi2.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Ajanth on 06.06.2016.
 */
public interface Movie {
    int getId();
    IntegerProperty idProperty();
    void setId(int id);
    String getTitle();
    StringProperty titleProperty();
    void setTitle(String title);
    int getYearOfAward();
    IntegerProperty yearOfAwardProperty();
    void setYearOfAward(int yearOfAward);
    String getDirector();
    StringProperty directorProperty();
    void setDirector(String director);
    String getMainActor();
    StringProperty mainActorProperty();
    void setMainActor(String mainActor);
    String getTitleEnglish();
    StringProperty titleEnglishProperty();
    void setTitleEnglish(String titleEnglish);
    int getYearOfProduction();
    IntegerProperty yearOfProductionProperty();
    void setYearOfProduction(int yearOfProduction);
    String getCountry();
    StringProperty countryProperty();
    void setCountry(String country);
    int getDuration();
    IntegerProperty durationProperty();
    void setDuration(int duration);
    int getFsk();
    IntegerProperty fskProperty();
    void setFsk(int fsk);
    String getGenre();
    StringProperty genreProperty();
    void setGenre(String genre);
    String getStartDate();
    StringProperty startDateProperty();
    void setStartDate(String startDate);
    int getNumberOfOscars();
    IntegerProperty numberOfOscarsProperty();
    void setNumberOfOscars(int numberOfOscars);
}
