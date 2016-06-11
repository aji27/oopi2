package ch.fhnw.oopi2.model;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ajanth on 06.06.2016.
 */
public class FileBackendModel implements Model {

    public static final String FILENAME_BACKEND = "../res/model/filebackend/movies.csv";
    public static final String CSV_HEADER_LINE = "#_id;Title;_yearOfAward;_director;_mainActor;_titleEnglish;_yearOfProduction;_country;_duration;_fsk;_genre;_startDate;_numberOfOscars";
    public static final String CSV_DELIMITER = ";";
    private static FileBackendModel _instance;

    private final HashSet<Movie> _movies = new HashSet<>();

    private final Object _syncStreamAccess = new Object();
    private final Object _syncCollectionAccess = new Object();

    private FileBackendModel() {
    }

    public static Model getCurrent() {
        return _instance;
    }

    public static void initialize() throws UnsupportedEncodingException {
        if (_instance == null) {
            _instance = new FileBackendModel();
            _instance.loadData();
        }
    }

    @Override
    public Movie getById(int id) {
        return _movies.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Movie> getAll() {
        return _movies.stream().collect(Collectors.toList());
    }

    @Override
    public void add(Movie item) {
        if (item == null)
            throw new IllegalArgumentException("Argument item cannot be null.");

        if (_movies.contains(item))
            throw new IllegalArgumentException("Item is already present in internal collection.");

        if (item.getId() == 0) {
            Integer newMovieId = 0;
            Movie m = _movies.stream().max((m1, m2) -> Integer.compare(m1.getId(), m2.getId())).orElse(null);
            if (m != null) {
                newMovieId = m.getId();
            }
            newMovieId++;
            item.setId(newMovieId);
        }

        _movies.add(item);
    }

    @Override
    public void remove(Movie item) {
        if (item == null)
            throw new IllegalArgumentException("Argument item cannot be null.");

        if (!_movies.contains(item))
            throw new IllegalArgumentException("Item could not be found.");

        _movies.remove(item);
    }

    @Override
    public boolean saveChanges() {
        boolean hasStreamAccessError = false;
        try {
            synchronized (_syncStreamAccess) {
                String fileName = getClass().getResource(FILENAME_BACKEND).getFile();
                OutputStream stream = new FileOutputStream(fileName, false);
                PrintWriter writer = new PrintWriter(stream);
                writer.println(CSV_HEADER_LINE);
                for (Movie m : _movies) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(m.getId());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getTitle());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getYearOfAward());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getDirector());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getMainActor());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getTitleEnglish());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getYearOfProduction());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getCountry());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getDuration());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getFsk());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getGenre());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getStartDate());
                    builder.append(CSV_DELIMITER);
                    builder.append(m.getNumberOfOscars());
                    builder.append(CSV_DELIMITER);
                    writer.println(builder.toString());
                }
                writer.flush();
                stream.close();
            }
        } catch (FileNotFoundException ex1) {
            // ToDo: log exception and notify user
            hasStreamAccessError = true;
        } catch (IOException ex2) {
            // ToDo: log exception and notify user
            hasStreamAccessError = true;
        }
        if (hasStreamAccessError) {
            throw new IllegalStateException(String.format("Error while accessing file '%s'.", FILENAME_BACKEND));
        }
        return false;
    }

    private void loadData() throws UnsupportedEncodingException {
        boolean hasStreamAccessError = false;
        try {
            synchronized (_syncStreamAccess) {
                String fileName = getClass().getResource(FILENAME_BACKEND).getFile();
                InputStream stream = new FileInputStream(fileName);
                Reader inputStreamReader = new InputStreamReader(stream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                bufferedReader.lines().skip(1).forEach(s -> {
                    if (s != null) {
                        String[] values = s.split(CSV_DELIMITER);
                        MovieImpl movie = new MovieImpl();
                        movie.setId(Integer.parseInt(values[0]));
                        movie.setTitle(values[1]);
                        movie.setYearOfAward(Integer.parseInt(values[2]));
                        movie.setDirector(values[3]);
                        movie.setMainActor(values[4]);
                        movie.setTitleEnglish(values[5]);
                        movie.setYearOfProduction(Integer.parseInt(values[6]));
                        movie.setCountry(values[7]);
                        movie.setDuration(Integer.parseInt(values[8]));
                        movie.setFsk(Integer.parseInt(values[9]));
                        movie.setGenre(values[10]);
                        movie.setNumberOfOscars(Integer.parseInt(values[12]));

                        if (!values[11].equals("-")) {
                            movie.setStartDate(values[11]);
                        }

                        synchronized (_syncCollectionAccess) {
                            _movies.add(movie);
                        }
                    }
                });
                bufferedReader.close();
            }
        } catch (FileNotFoundException ex1) {
            // ToDo: log exception and notify user
            hasStreamAccessError = true;
        } catch (IOException ex2) {
            // ToDo: log exception and notify user
            hasStreamAccessError = true;
        }
        if (hasStreamAccessError) {
            throw new IllegalStateException(String.format("Error while accessing file '%s'.", FILENAME_BACKEND));
        }
    }
}
