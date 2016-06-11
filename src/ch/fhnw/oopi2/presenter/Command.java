package ch.fhnw.oopi2.presenter;

/**
 * Created by Ajanth on 11.06.2016.
 */
public interface Command {
    void execute();
    void undo();
}
