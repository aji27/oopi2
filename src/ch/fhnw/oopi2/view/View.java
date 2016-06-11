package ch.fhnw.oopi2.view;

import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.presenter.Presenter;

import java.util.Date;
import java.util.List;

/**
 * Created by ajant on 03.05.2016.
 */
public interface View {
    void setPresenter(Presenter presenter);
    void setItems(List<Movie> items);
    void hideMovieDetailPane();
    void showMovieDetailPane();
    void setPoster(Integer movieId);
    void setYear(Integer year);
    void setTitle(String title);
    void setDirector(String director);
    void setDirectorHeading(String directorHeading);
    void setMainActor(String mainActor);
    void setMainActorHeading(String mainActorHeading);
    void setTitleEnglish(String titleEnglish);
    void setGenre(String genre);
    void setYearOfProduction(Integer yearOfProduction);
    void setCountry(String country);
    void setDuration(Integer duration);
    void setFsk(Integer fsk);
    void setStartDate(Date startDate);
    void setNumberOfOscars(Integer numberOfOscars);
    void setSelectedItem(Movie selectedItem);
    void scrollTo(Movie item);
    void enableBtnDelete(boolean enable);
    void enableBtnUndo(boolean enable);
    void enableBtnRedo(boolean enable);
}
