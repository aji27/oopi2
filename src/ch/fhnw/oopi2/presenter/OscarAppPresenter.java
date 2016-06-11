package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.Model;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.model.MovieImpl;
import ch.fhnw.oopi2.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ajant on 03.05.2016.
 */
public class OscarAppPresenter implements Presenter {

    private final View _view;
    private final Model _model;
    private final SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Movie _selectedItem;

    public OscarAppPresenter(View view, Model model) {

        if (view == null)
            throw new IllegalArgumentException("Argument view cannot be null.");

        if (model == null)
            throw new IllegalArgumentException("Argument model cannot be null.");

        _view = view;
        _model = model;

        initialize();
    }

    @Override
    public void onSelectedItemChanged(Integer movieId) {
        if (movieId > -1) {
            Movie item = _model.getById(movieId);
            if (item != null) {
                _selectedItem = item;
            }
        } else {
            _selectedItem = null;
        }

        displaySelectedItem();
    }

    @Override
    public void onSaveClicked() {
        _model.saveChanges();
    }

    @Override
    public void onAddNewItemClicked() {
        Movie newMovie = new MovieImpl();
        _model.add(newMovie);
        _model.saveChanges();
        List<Movie> items = getSortedMovies();
        _view.setItems(items);
        _selectedItem = newMovie;
        displaySelectedItem();
        _view.scrollToItem(_selectedItem);
    }

    @Override
    public void onDeleteSelectedItemClicked() {
        if (_selectedItem != null) {
            List<Movie> items = getSortedMovies();
            Integer size = items.size();
            Integer idxOfNext = items.indexOf(_selectedItem) + 1;

            _model.remove(_selectedItem);
            _model.saveChanges();

            displayAllMovies();

            if (size > 1 && idxOfNext <= size - 1) {
                _selectedItem = items.get(idxOfNext);
                displaySelectedItem();
            }
        }
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
            displayAllMovies();
            return;
        }

        final String text = searchText.toLowerCase();

        List<Movie> filteredItems = _model
                .getAll()
                .stream()
                .filter(m -> m.getTitle().toLowerCase().contains(text)
                        || m.getTitleEnglish().toLowerCase().contains(text)
                        || m.getDirector().toLowerCase().contains(text)
                        || m.getMainActor().toLowerCase().contains(text)
                        || ((Integer)m.getYearOfAward()).toString().equals(text)
                )
                .sorted((m1, m2) -> Integer.compare(m1.getId(), m2.getId()))
                .collect(Collectors.toList());
        Movie firstOrDefault = filteredItems.stream().findFirst().orElse(null);
        _view.setItems(filteredItems);
        _selectedItem = firstOrDefault;
        displaySelectedItem();
    }

    @Override
    public void onYearChanged(Integer year) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setYearOfAward(year);
            displayYear();
        }
    }

    @Override
    public void onTitleChanged(String title) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setTitle(title);
            displayTitle();
        }
    }

    @Override
    public void onDirectorChanged(String director) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setDirector(director);
            displayDirector();
        }
    }

    @Override
    public void onMainActorChanged(String mainActor) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setMainActor(mainActor);
            displayMainActor();
        }
    }

    @Override
    public void onTitleEnglishChanged(String titleEnglish) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setTitleEnglish(titleEnglish);
            displayTitleEnglish();
        }
    }

    @Override
    public void onGenreChanged(String genre) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setGenre(genre);
            displayGenre();
        }
    }

    @Override
    public void onYearOfProductionChanged(Integer yearOfProduction) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setYearOfProduction(yearOfProduction);
            displayYearOfProduction();
        }
    }

    @Override
    public void onCountryChanged(String country) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setCountry(country);
            displayCountry();
        }
    }

    @Override
    public void onDurationChanged(Integer duration) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setDuration(duration);
            displayDuration();
        }
    }

    @Override
    public void onFskChanged(Integer fsk) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setFsk(fsk);
            displayFsk();
        }
    }

    @Override
    public void onStartDateChanged(Date startDate) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            if (startDate != null) {
                _selectedItem.setStartDate(_dateFormat.format(startDate));
            } else {
                _selectedItem.setStartDate(null);
            }
            displayStartDate();
        }
    }

    @Override
    public void onNumberOfOscarsChanged(Integer numberOfOscars) {
        if (_selectedItem != null) {
            // ToDo: sanity check
            _selectedItem.setNumberOfOscars(numberOfOscars);
            displayNumberOfOscars();
        }
    }

    private void initialize() {
        _view.setPresenter(this);
        displayAllMovies();
    }

    private List<Movie> getSortedMovies() {
        List<Movie> items = _model
                .getAll()
                .stream()
                .sorted((m1, m2) -> Integer.compare(m1.getId(), m2.getId()))
                .collect(Collectors.toList());
        return items;
    }

    private void displayAllMovies() {
        List<Movie> items = getSortedMovies();
        Movie firstOrDefault = items.stream().findFirst().orElse(null);
        _view.setItems(items);
        _selectedItem = firstOrDefault;
        displaySelectedItem();
    }

    private void displaySelectedItem() {
        _view.setSelectedItem(_selectedItem);
        if (_selectedItem != null) {
            _view.showMovieDetailPane();
            displayPoster();
            displayYear();
            displayTitle();
            displayDirector();
            displayMainActor();
            displayTitleEnglish();
            displayGenre();
            displayYearOfProduction();
            displayCountry();
            displayDuration();
            displayFsk();
            displayStartDate();
            displayNumberOfOscars();
        } else {
            _view.hideMovieDetailPane();
        }
    }

    private void displayPoster() {
        _view.setPoster(_selectedItem.getId());
    }

    private void displayYear() {
        _view.setYear(_selectedItem.getYearOfAward());
    }

    private void displayTitle() {
        _view.setTitle(_selectedItem.getTitle());
    }

    private void displayDirector() {
        _view.setDirector(_selectedItem.getDirector());
        _view.setDirectorHeading(String.format("von %s", _selectedItem.getDirector()));
    }

    private void displayMainActor() {
        _view.setMainActor(_selectedItem.getMainActor());
        _view.setMainActorHeading(String.format("mit %s",_selectedItem.getMainActor()));
    }

    private void displayTitleEnglish() {
        _view.setTitleEnglish(_selectedItem.getTitleEnglish());
    }

    private void displayGenre() {
        _view.setGenre(_selectedItem.getGenre());
    }

    private void displayYearOfProduction() {
        _view.setYearOfProduction(_selectedItem.getYearOfProduction());
    }

    private void displayCountry() {
        _view.setCountry(_selectedItem.getCountry());
    }

    private void displayDuration() {
        _view.setDuration(_selectedItem.getDuration());
    }

    private void displayFsk() {
        _view.setFsk(_selectedItem.getFsk());
    }

    private void displayStartDate() {
        try {
            String date = _selectedItem.getStartDate();
            if (date != null && !date.equals("")) {
                _view.setStartDate(_dateFormat.parse(_selectedItem.getStartDate()));
            } else {
                _view.setStartDate(null);
            }
        } catch (ParseException ex) {
            _view.setStartDate(null);
            // ToDo: Log exception and notify user
        }
    }

    private void displayNumberOfOscars() {
        _view.setNumberOfOscars(_selectedItem.getNumberOfOscars());
    }
}
