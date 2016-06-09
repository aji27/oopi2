package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.Model;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.view.View;

import java.util.Date;

/**
 * Created by ajant on 03.05.2016.
 */
public class OscarAppPresenter implements Presenter {

    private final View _view;
    private final Model _repository;

    private Movie _selectedItem;

    public OscarAppPresenter(View view, Model repository) {

        if (view == null)
            throw new IllegalArgumentException("Argument view cannot be null.");

        if (repository == null)
            throw new IllegalArgumentException("Argument repository cannot be null.");

        _view = view;
        _repository = repository;

        initialize();
    }

    @Override
    public void onSelectedItemChanged(Integer movieId) {
        if (movieId > 0) {
            Movie item = _repository.getById(movieId);
            if (item != null) {
                _selectedItem = item;

                _view.setPoster(_selectedItem.getId());
                _view.setYear(_selectedItem.getYearOfAward());
                _view.setTitle(_selectedItem.getTitle());
                _view.setDirector(_selectedItem.getDirector());
                _view.setMainActor(_selectedItem.getMainActor());
                _view.setCountry(_selectedItem.getCountry());
                _view.setNumberOfOscars(_selectedItem.getNumberOfOscars());
                _view.setTitleEnglish(_selectedItem.getTitleEnglish());
                _view.setGenre(_selectedItem.getGenre());
                _view.setYearOfProduction(_selectedItem.getYearOfProduction());
                _view.setDuration(_selectedItem.getDuration());
                _view.setFsk(_selectedItem.getFsk());
                //_view.setStartDate(new Date(_selectedItem.getStartDate()));

            }
        } else {
            _selectedItem = null;
        }
    }

    @Override
    public void onSaveClicked() {

    }

    @Override
    public void onAddNewItemClicked() {

    }

    @Override
    public void onDeleteSelectedItemClicked() {

    }

    @Override
    public void onUndoClicked() {

    }

    @Override
    public void onRedoClicked() {

    }

    @Override
    public void onSearchTextChanged(String searchText) {

    }

    @Override
    public void onYearChanged(Integer year) {
        if (_selectedItem != null) {
            _selectedItem.setYearOfAward(year);
            _view.setYear(year);
        }
    }

    @Override
    public void onTitleChanged(String title) {
        if (_selectedItem != null) {
            _selectedItem.setTitle(title);
            _view.setTitle(title);
        }
    }

    @Override
    public void onDirectorChanged(String director) {
        if (_selectedItem != null) {
            _selectedItem.setDirector(director);
            _view.setDirector(director);
        }
    }

    @Override
    public void onMainActorChanged(String mainActor) {
        if (_selectedItem != null) {
            _selectedItem.setMainActor(mainActor);
            _view.setMainActor(mainActor);
        }
    }

    @Override
    public void onTitleEnglishChanged(String titleEnglish) {
        if (_selectedItem != null) {
            _selectedItem.setTitleEnglish(titleEnglish);
            _view.setTitleEnglish(titleEnglish);
        }
    }

    @Override
    public void onGenreChanged(String genre) {
        if (_selectedItem != null) {
            _selectedItem.setGenre(genre);
            _view.setGenre(genre);
        }
    }

    @Override
    public void onYearOfProductionChanged(Integer yearOfProduction) {
        if (_selectedItem != null) {
            _selectedItem.setYearOfProduction(yearOfProduction);
            _view.setYearOfProduction(yearOfProduction);
        }
    }

    @Override
    public void onCountryChanged(String country) {
        if (_selectedItem != null) {
            _selectedItem.setCountry(country);
            _view.setCountry(country);
        }
    }

    @Override
    public void onDurationChanged(Integer duration) {
        if (_selectedItem != null) {
            _selectedItem.setDuration(duration);
            _view.setDuration(duration);
        }
    }

    @Override
    public void onFskChanged(Integer fsk) {
        if (_selectedItem != null) {
            _selectedItem.setFsk(fsk);
            _view.setFsk(fsk);
        }
    }

    @Override
    public void onStartDateChanged(Date startDate) {
        if (_selectedItem != null) {
            _selectedItem.setStartDate(startDate.toString());
            _view.setStartDate(startDate);
        }
    }

    @Override
    public void onNumberOfOscarsChanged(Integer numberOfOscars) {
        if (_selectedItem != null) {
            _selectedItem.setNumberOfOscars(numberOfOscars);
            _view.setNumberOfOscars(numberOfOscars);
        }
    }

    private void initialize() {
        _view.setPresenter(this);
        _view.setItems(_repository.getAll());
    }
}
