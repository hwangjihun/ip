package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to display all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks to the user.
     *
     * @param tasks Task list to display.
     * @param ui UI to display the task list to the user.
     * @param storage Storage (not used in this command).
     * @throws HunnieException If the task list is empty.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        ui.showTaskList(tasks);
    }
}
