package ch.fhnw.oopi2.model;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ajanth on 11.06.2016.
 */
public class MockedModel implements Model {

    private final HashSet<Movie> _movies = new HashSet<>();

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
        _movies.add(item);
    }

    @Override
    public void remove(Movie item) {
        _movies.remove(item);
    }

    @Override
    public boolean saveChanges() {
        return true;
    }
}
