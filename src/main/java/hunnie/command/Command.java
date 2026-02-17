package hunnie.command;

import java.io.IOException;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents an abstract command that can be executed in the Hunnie application.
 * All specific command types should extend this class and implement the execute method.
 */
public abstract class Command {

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks Task list to operate on.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save/load tasks.
     * @throws HunnieException If an error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException;

    protected final void saveTasks(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }

    /**
     * Returns whether this command should cause the application to exit.
     *
     * @return True if the application should exit after this command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
