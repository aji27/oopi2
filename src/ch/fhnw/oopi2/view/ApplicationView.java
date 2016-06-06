package ch.fhnw.oopi2.view;

import ch.fhnw.oopi2.model.Movie;

import java.util.List;

/**
 * Created by ajant on 03.05.2016.
 */
public interface ApplicationView {

    void setItems(List<Movie> items);
}
