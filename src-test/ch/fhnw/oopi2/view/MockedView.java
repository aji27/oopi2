package ch.fhnw.oopi2.view;

import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.presenter.Presenter;

import java.util.Date;
import java.util.List;

/**
 * Created by Ajanth on 11.06.2016.
 */
public class MockedView implements View {

    private List<Movie> _items;
    private Movie _selectedItem;

    public List<Movie> getItems(){
        return _items;
    }

    public Movie getSelectedItem() {
        return _selectedItem;
    }

    @Override
    public void setPresenter(Presenter presenter) {

    }

    @Override
    public void setItems(List<Movie> items) {
        _items = items;
    }

    @Override
    public void hideMovieDetailPane() {

    }

    @Override
    public void showMovieDetailPane() {

    }

    @Override
    public void setPoster(Integer movieId) {

    }

    @Override
    public void setYear(Integer year) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDirector(String director) {

    }

    @Override
    public void setDirectorHeading(String directorHeading) {

    }

    @Override
    public void setMainActor(String mainActor) {

    }

    @Override
    public void setMainActorHeading(String mainActorHeading) {

    }

    @Override
    public void setTitleEnglish(String titleEnglish) {

    }

    @Override
    public void setGenre(String genre) {

    }

    @Override
    public void setYearOfProduction(Integer yearOfProduction) {

    }

    @Override
    public void setCountry(String country) {

    }

    @Override
    public void setDuration(Integer duration) {

    }

    @Override
    public void setFsk(Integer fsk) {

    }

    @Override
    public void setStartDate(Date startDate) {

    }

    @Override
    public void setNumberOfOscars(Integer numberOfOscars) {

    }

    @Override
    public void setSelectedItem(Movie selectedItem) {
        _selectedItem = selectedItem;
    }

    @Override
    public void scrollTo(Movie item) {

    }

    @Override
    public void enableBtnDelete(boolean enable) {

    }

    @Override
    public void enableBtnUndo(boolean enable) {

    }

    @Override
    public void enableBtnRedo(boolean enable) {

    }
}
