package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.Model;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by ajant on 03.05.2016.
 */
public class OscarAppPresenter implements Presenter {

    private final View _view;
    private final Model _model;
    private final SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Movie _selectedItem;

    public OscarAppPresenter(View view, Model repository) {

        if (view == null)
            throw new IllegalArgumentException("Argument view cannot be null.");

        if (repository == null)
            throw new IllegalArgumentException("Argument repository cannot be null.");

        _view = view;
        _model = repository;

        initialize();
    }

    @Override
    public void onSelectedItemChanged(Integer movieId) {
        if (movieId > -1) {
            Movie item = _model.getById(movieId);
            if (item != null) {
                _selectedItem = item;

                _view.showMovieDetailPane();

                _view.setPoster(_selectedItem.getId());
                _view.setYear(_selectedItem.getYearOfAward());
                _view.setTitle(_selectedItem.getTitle());
                _view.setDirector(_selectedItem.getDirector());
                _view.setDirectorHeading(String.format("von %s", _selectedItem.getDirector()));
                _view.setMainActor(_selectedItem.getMainActor());
                _view.setMainActorHeading(String.format("mit %s",_selectedItem.getMainActor()));
                _view.setCountry(_selectedItem.getCountry());
                _view.setNumberOfOscars(_selectedItem.getNumberOfOscars());
                _view.setTitleEnglish(_selectedItem.getTitleEnglish());
                _view.setGenre(_selectedItem.getGenre());
                _view.setYearOfProduction(_selectedItem.getYearOfProduction());
                _view.setDuration(_selectedItem.getDuration());
                _view.setFsk(_selectedItem.getFsk());

                try {
                    _view.setStartDate(_dateFormat.parse(_selectedItem.getStartDate()));
                } catch (ParseException ex) {
                    // ToDo: Log exception and notify user
                }
            }
        } else {
            _selectedItem = null;

            _view.hideMovieDetailPane();
        }
    }

    @Override
    public void onSaveClicked() {
        _model.saveChanges();
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
        if (searchText == null || searchText.equals("")) {
            _view.setItems(_model
                            .getAll()
                            .stream()
                            .sorted((m1, m2) -> Integer.compare(m1.getId(), m2.getId()))
                            .collect(Collectors.toList()));
        }

        final String text = searchText.toLowerCase();

        _view.setItems(_model
                        .getAll()
                        .stream()
                        .filter(m -> m.getTitle().toLowerCase().contains(text)
                                  || m.getTitleEnglish().toLowerCase().contains(text)
                                  || m.getDirector().toLowerCase().contains(text)
                                  || m.getMainActor().toLowerCase().contains(text)
                                  || ((Integer)m.getYearOfAward()).toString().equals(text)
                        )
                        .sorted((m1, m2) -> Integer.compare(m1.getId(), m2.getId()))
                        .collect(Collectors.toList()));
    }

    @Override
    public void onYearChanged(Integer year) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setYearOfAward(year);
            _view.setYear(year);
        }
    }

    @Override
    public void onTitleChanged(String title) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setTitle(title);
            _view.setTitle(title);
        }
    }

    @Override
    public void onDirectorChanged(String director) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setDirector(director);
            _view.setDirector(_selectedItem.getDirector());
            _view.setDirectorHeading(String.format("von %s", _selectedItem.getDirector()));
        }
    }

    @Override
    public void onMainActorChanged(String mainActor) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setMainActor(mainActor);
            _view.setMainActor(_selectedItem.getMainActor());
            _view.setMainActorHeading(String.format("mit %s",_selectedItem.getMainActor()));
        }
    }

    @Override
    public void onTitleEnglishChanged(String titleEnglish) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setTitleEnglish(titleEnglish);
            _view.setTitleEnglish(titleEnglish);
        }
    }

    @Override
    public void onGenreChanged(String genre) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setGenre(genre);
            _view.setGenre(genre);
        }
    }

    @Override
    public void onYearOfProductionChanged(Integer yearOfProduction) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setYearOfProduction(yearOfProduction);
            _view.setYearOfProduction(yearOfProduction);
        }
    }

    @Override
    public void onCountryChanged(String country) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setCountry(country);
            _view.setCountry(country);
        }
    }

    @Override
    public void onDurationChanged(Integer duration) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setDuration(duration);
            _view.setDuration(duration);
        }
    }

    @Override
    public void onFskChanged(Integer fsk) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setFsk(fsk);
            _view.setFsk(fsk);
        }
    }

    @Override
    public void onStartDateChanged(Date startDate) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setStartDate(_dateFormat.format(startDate));

            try {
                _view.setStartDate(_dateFormat.parse(_selectedItem.getStartDate()));
            } catch (ParseException ex) {
                // ToDo: log exception and notify user
            }
        }
    }

    @Override
    public void onNumberOfOscarsChanged(Integer numberOfOscars) {
        if (_selectedItem != null) {
            // ToDo: sanity check

            _selectedItem.setNumberOfOscars(numberOfOscars);
            _view.setNumberOfOscars(numberOfOscars);
        }
    }

    private void initialize() {
        _view.setPresenter(this);
        _view.setItems(_model
                .getAll()
                .stream()
                .sorted((m1, m2) -> Integer.compare(m1.getId(), m2.getId()))
                .collect(Collectors.toList()));
    }
}
