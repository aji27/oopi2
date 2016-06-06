package ch.fhnw.oopi2.presenter;

import ch.fhnw.oopi2.model.MovieRepository;
import ch.fhnw.oopi2.view.ApplicationView;

/**
 * Created by ajant on 03.05.2016.
 */
public class Presenter {

    private final ApplicationView _view;
    private final MovieRepository _repository;

    public Presenter(ApplicationView view, MovieRepository repository) {
        _view = view;
        _repository = repository;
    }

    public void initialize() {
        _view.setItems(_repository.getAll());
    }
}
