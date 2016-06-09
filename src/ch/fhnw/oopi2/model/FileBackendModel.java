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
    public boolean saveChanges() {
        return false;
    }

    private void loadData() throws UnsupportedEncodingException {
        InputStream stream = getClass().getResourceAsStream(FILENAME_BACKEND);

        if (stream == null) {
            throw new IllegalArgumentException(String.format("Resource '%s' not found.", FILENAME_BACKEND));
        }

        synchronized (_syncStreamAccess) {
            Reader inputStreamReader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines().skip(1).forEach(s -> {
                if (s != null) {
                    String[] values = s.split(";");

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
                    movie.setStartDate(values[11]);
                    movie.setNumberOfOscars(Integer.parseInt(values[12]));

                    synchronized (_syncCollectionAccess) {
                        _movies.add(movie);
                    }
                }
            });
        }
    }
}
