package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIdx;

    /**
     * Creates a new delete command for the task at the specified index.
     *
     * @param taskIdx Zero-based index of the task to delete.
     */
    public DeleteCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    /**
     * Executes the delete command by removing the specified task from the task list.
     * The updated task list is saved to storage after deletion.
     *
     * @param tasks Task list to delete the task from.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        Task taskToDelete = tasks.get(this.taskIdx);
        tasks.delete(this.taskIdx);
        ui.showTaskDeleted(taskToDelete, tasks.size());
        saveTasks(tasks, storage, ui);
    }
}
