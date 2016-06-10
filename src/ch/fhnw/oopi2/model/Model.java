package ch.fhnw.oopi2.model;

import java.util.List;

/**
 * Created by ajant on 03.05.2016.
 */
public interface Model {
    Movie getById(int id);
    List<Movie> getAll();
    void add(Movie item);
    void remove(Movie item);
    boolean saveChanges();
}
