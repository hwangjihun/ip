package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to find tasks matching a keyword.
 */
public class FindCommand extends Command {
    private static final String NO_MATCHING_TASKS_MESSAGE = "Sorry, I cannot find any matching tasks!";
    private final String keyword;

    /**
     * Creates a new find command with the specified keyword.
     *
     * @param keyword Keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks containing the keyword.
     *
     * @param tasks Task list to search in.
     * @param ui UI to display matching tasks to the user.
     * @param storage Storage (not used in this command).
     * @throws HunnieException If no matching tasks are found.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        assert this.keyword != null : "Find keyword should not be null";
        TaskList matchingTasks = tasks.getFilteredTasks(this.keyword);
        if (matchingTasks.size() == 0) {
            throw new HunnieException(NO_MATCHING_TASKS_MESSAGE);
        }
        ui.showTaskList(matchingTasks);
    }
}
