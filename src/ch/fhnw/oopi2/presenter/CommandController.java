package ch.fhnw.oopi2.presenter;

import java.util.LinkedList;

/**
 * Created by Ajanth on 11.06.2016.
 */
public class CommandController {

    private final LinkedList<Command> _undoList = new LinkedList<>();
    private final LinkedList<Command> _redoList = new LinkedList<>();

    private final Object _syncListAccess = new Object();

    public void execute(Command command) {
        if (command == null)
            throw new IllegalArgumentException("Argument command cannot be null.");

        synchronized (_syncListAccess) {
            _undoList.add(command);
            command.execute();
        }
    }

    public void undo() {
        popExecuteAndPush(_undoList, _redoList, "undo");
    }

    public void redo() {
        popExecuteAndPush(_redoList, _undoList, "redo");
    }

    private void popExecuteAndPush(LinkedList<Command> source, LinkedList<Command> destination, String command) {
        synchronized (_syncListAccess) {
            if (source.size() > 0) {
                Command last = source.getLast();
                if (last != null) {
                    source.remove(last);
                    switch (command) {
                        case "undo":
                            last.undo();
                            break;
                        case "redo":
                            last.execute();
                            break;
                    }
                    destination.add(last);
                }
            }
        }
    }
}
