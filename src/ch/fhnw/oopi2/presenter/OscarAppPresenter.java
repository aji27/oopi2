package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.Model;
import ch.fhnw.oopi2.model.Movie;
import ch.fhnw.oopi2.model.MovieImpl;
import ch.fhnw.oopi2.util.Levenshtein;
import ch.fhnw.oopi2.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by ajant on 03.05.2016.
 */
public class OscarAppPresenter implements Presenter {

    private final SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    final int LEVENSHTEIN_MAX_DISTANCE = 1;

    private final View _view;
    private final Model _model;

    private final CommandController _controller;
    private Movie _selectedItem;

    public OscarAppPresenter(View view, Model model) {

        if (view == null)
            throw new IllegalArgumentException("Argument view cannot be null.");

        if (model == null)
            throw new IllegalArgumentException("Argument model cannot be null.");

        _view = view;
        _model = model;
        _controller = new CommandController();

        initialize();
    }

    @Override
    public void onSelectedItemChanged(Integer movieId) {
        if (movieId == -1 && _selectedItem == null)
            return;

        if (_selectedItem != null && _selectedItem.getId() == movieId)
            return;

        Command changeSelectedItemCommand = new Command() {
            private Movie _oldSelectedItem;

            @Override
            public void execute() {
                _oldSelectedItem = _selectedItem;

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
            public void undo() {
                _selectedItem = _oldSelectedItem;
                displaySelectedItem();
            }
        };

        execute(changeSelectedItemCommand);
    }

    @Override
    public void onSaveClicked() {
        _model.saveChanges();
    }

    @Override
    public void onAddNewItemClicked() {

        Command addNewItemCommand = new Command() {
            private Movie _oldSelectedItem;

            @Override
            public void execute() {
                _oldSelectedItem = _selectedItem;

                Movie newMovie = new MovieImpl();
                _model.add(newMovie);

                displayAllMoviesAndSelect(newMovie);
            }

            @Override
            public void undo() {
                _model.remove(_selectedItem);
                displayAllMoviesAndSelect(_oldSelectedItem);
            }
        };

        execute(addNewItemCommand);
    }

    @Override
    public void onDeleteSelectedItemClicked() {

        Command deleteSelectedItemCommand = new Command() {
            private Movie _oldSelectedItem;

            @Override
            public void execute() {
                _oldSelectedItem = _selectedItem;

                if (_selectedItem != null) {
                    List<Movie> items = getSortedMovies();
                    Integer size = items.size();
                    Integer idxOfNext = items.indexOf(_selectedItem) + 1;

                    _model.remove(_selectedItem);
                    displayAllMovies();

                    if (size > 1 && idxOfNext <= size - 1) {
                        _selectedItem = items.get(idxOfNext);
                        displaySelectedItem();
                    }
                }
            }

            @Override
            public void undo() {
                if (_oldSelectedItem != null) {
                    _model.add(_oldSelectedItem);
                    displayAllMoviesAndSelect(_oldSelectedItem);
                }
            }
        };

        execute(deleteSelectedItemCommand);
    }

    @Override
    public void onUndoClicked() {
        _controller.undo();
    }

    @Override
    public void onRedoClicked() {
        _controller.redo();
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
                        || Levenshtein.distance(m.getTitle().toLowerCase(), text) <= LEVENSHTEIN_MAX_DISTANCE
                        || Arrays.stream(m.getTitle().toLowerCase().split(" ")).anyMatch(s -> Levenshtein.distance(s, text) <= LEVENSHTEIN_MAX_DISTANCE)
                        || m.getTitleEnglish().toLowerCase().contains(text)
                        || Levenshtein.distance(m.getTitleEnglish().toLowerCase(), text) <= LEVENSHTEIN_MAX_DISTANCE
                        || Arrays.stream(m.getTitleEnglish().toLowerCase().split(" ")).anyMatch(s -> Levenshtein.distance(s, text) <= LEVENSHTEIN_MAX_DISTANCE)
                        || m.getDirector().toLowerCase().contains(text)
                        || Levenshtein.distance(m.getDirector().toLowerCase(), text) <= LEVENSHTEIN_MAX_DISTANCE
                        || Arrays.stream(m.getDirector().toLowerCase().split(" ")).anyMatch(s -> Levenshtein.distance(s, text) <= LEVENSHTEIN_MAX_DISTANCE)
                        || m.getMainActor().toLowerCase().contains(text)
                        || Levenshtein.distance(m.getMainActor().toLowerCase(), text) <= LEVENSHTEIN_MAX_DISTANCE
                        || Arrays.stream(m.getMainActor().replace(",", "").toLowerCase().split(" ")).anyMatch(s -> Levenshtein.distance(s, text) <= LEVENSHTEIN_MAX_DISTANCE)
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
        if (_selectedItem != null && Objects.equals(year, _selectedItem.getYearOfAward()))
            return;

        Command changeYearCommand = new Command() {

            private Integer oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getYearOfAward();
                    _selectedItem.setYearOfAward(year);
                    displayYear();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setYearOfAward(oldValue);
                    displayYear();
                }
            }
        };

        execute(changeYearCommand);
    }

    @Override
    public void onTitleChanged(String title) {
        if (_selectedItem != null && Objects.equals(title, _selectedItem.getTitle()))
            return;

        Command changeTitleCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getTitle();
                    _selectedItem.setTitle(title);
                    displayTitle();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setTitle(oldValue);
                    displayTitle();
                }
            }
        };

        execute(changeTitleCommand);
    }

    @Override
    public void onDirectorChanged(String director) {
        if (_selectedItem != null && Objects.equals(director, _selectedItem.getDirector()))
            return;

        Command changeDirectorCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getDirector();
                    _selectedItem.setDirector(director);
                    displayDirector();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setDirector(oldValue);
                    displayDirector();
                }
            }
        };

        execute(changeDirectorCommand);        
    }

    @Override
    public void onMainActorChanged(String mainActor) {
        if (_selectedItem != null && Objects.equals(mainActor, _selectedItem.getMainActor()))
            return;

        Command changeMainActorCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getMainActor();
                    _selectedItem.setMainActor(mainActor);
                    displayMainActor();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setMainActor(oldValue);
                    displayMainActor();
                }
            }
        };

        execute(changeMainActorCommand);
    }

    @Override
    public void onTitleEnglishChanged(String titleEnglish) {
        if (_selectedItem != null && Objects.equals(titleEnglish, _selectedItem.getTitleEnglish()))
            return;

        Command changeTitleEnglishCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getTitleEnglish();
                    _selectedItem.setTitleEnglish(titleEnglish);
                    displayTitleEnglish();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setTitleEnglish(oldValue);
                    displayTitleEnglish();
                }
            }
        };
        
        execute(changeTitleEnglishCommand);
    }

    @Override
    public void onGenreChanged(String genre) {
        if (_selectedItem != null && Objects.equals(genre, _selectedItem.getGenre()))
            return;

        Command changeGenreCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getGenre();
                    _selectedItem.setGenre(genre);
                    displayGenre();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setGenre(oldValue);
                    displayGenre();
                }
            }
        };

        execute(changeGenreCommand);
    }

    @Override
    public void onYearOfProductionChanged(Integer yearOfProduction) {
        if (_selectedItem != null && Objects.equals(yearOfProduction, _selectedItem.getYearOfProduction()))
            return;

        Command changeYearOfProductionCommand = new Command() {
            private Integer oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getYearOfProduction();
                    _selectedItem.setYearOfProduction(yearOfProduction);
                    displayYearOfProduction();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setYearOfProduction(oldValue);
                    displayYearOfProduction();
                }
            }
        };

        execute(changeYearOfProductionCommand);
    }

    @Override
    public void onCountryChanged(String country) {
        if (_selectedItem != null && Objects.equals(country, _selectedItem.getCountry()))
            return;

        Command changeCountryCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getCountry();
                    _selectedItem.setCountry(country);
                    displayCountry();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setCountry(oldValue);
                    displayCountry();
                }
            }
        };

        execute(changeCountryCommand);
    }

    @Override
    public void onDurationChanged(Integer duration) {
        if (_selectedItem != null && Objects.equals(duration, _selectedItem.getDuration()))
            return;

        Command changeDurationCommand = new Command() {
            private Integer oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getDuration();
                    _selectedItem.setDuration(duration);
                    displayDuration();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setDuration(oldValue);
                    displayDuration();
                }
            }
        };
        
        execute(changeDurationCommand);
    }

    @Override
    public void onFskChanged(Integer fsk) {
        if (_selectedItem != null && Objects.equals(fsk, _selectedItem.getFsk()))
            return;

        Command changeFskCommand = new Command() {
            private Integer oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getFsk();
                    _selectedItem.setFsk(fsk);
                    displayFsk();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setFsk(oldValue);
                    displayFsk();
                }
            }
        };

        execute(changeFskCommand);
    }

    @Override
    public void onStartDateChanged(Date startDate) {
        if (_selectedItem != null)
        {
            if (startDate == null && (_selectedItem.getStartDate() == null || _selectedItem.getStartDate().equals("")))
                return;

            if (_dateFormat.format(startDate).equals(_selectedItem.getStartDate()))
                return;
        }

        Command changeStartDateCommand = new Command() {
            private String oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getStartDate();
                    if (startDate != null) {
                        _selectedItem.setStartDate(_dateFormat.format(startDate));
                    } else {
                        _selectedItem.setStartDate(null);
                    }
                    displayStartDate();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    // ToDo: sanity check
                    _selectedItem.setStartDate(oldValue);
                    displayStartDate();
                }
            }
        };

        execute(changeStartDateCommand);
    }

    @Override
    public void onNumberOfOscarsChanged(Integer numberOfOscars) {
        if (_selectedItem != null && Objects.equals(numberOfOscars, _selectedItem.getNumberOfOscars()))
            return;

        Command changeNumberOfOscarsCommand = new Command() {
            private Integer oldValue;

            @Override
            public void execute() {
                if (_selectedItem != null) {
                    // ToDo: sanity check

                    oldValue = _selectedItem.getNumberOfOscars();
                    _selectedItem.setNumberOfOscars(numberOfOscars);
                    displayNumberOfOscars();
                }
            }

            @Override
            public void undo() {
                if (_selectedItem != null) {
                    _selectedItem.setNumberOfOscars(oldValue);
                    displayNumberOfOscars();
                }
            }
        };

        execute(changeNumberOfOscarsCommand);
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

    private void displayAllMoviesAndSelect(Movie item) {
        List<Movie> items = getSortedMovies();
        _view.setItems(items);
        _selectedItem = item;
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

    private void execute(Command command) {
        if (command == null)
            throw new IllegalArgumentException("Argument command cannot be null.");

        _controller.execute(command);

        // ToDo: update view undo / redo list
    }
}
