package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskIdx;

    /**
     * Creates a new unmark command for the task at the specified index.
     *
     * @param taskIdx Zero-based index of the task to mark as not done.
     */
    public UnmarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    /**
     * Executes the unmark command by marking the specified task as not done.
     * The updated task list is saved to storage after unmarking.
     *
     * @param tasks Task list containing the task to unmark.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        tasks.unmark(this.taskIdx);
        ui.showTaskUnmarked(tasks.get(this.taskIdx));
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
