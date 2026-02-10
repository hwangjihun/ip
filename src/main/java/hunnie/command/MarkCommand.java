package hunnie.command;

import java.io.IOException;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskIdx;

    /**
     * Creates a new mark command for the task at the specified index.
     *
     * @param taskIdx Zero-based index of the task to mark as done.
     */
    public MarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    /**
     * Executes the mark command by marking the specified task as done.
     * The updated task list is saved to storage after marking.
     *
     * @param tasks Task list containing the task to mark.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        tasks.mark(this.taskIdx);
        ui.showTaskMarked(tasks.get(this.taskIdx));
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
