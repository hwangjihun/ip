package hunnie.command;

import java.io.IOException;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Deadline;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to add a deadline task to the task list.
 * A deadline task has a description and a due date.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Creates a new deadline command with the specified description and due date.
     *
     * @param description Description of the deadline task.
     * @param by Due date of the deadline task in yyyy-MM-dd format.
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the deadline command by adding a new deadline task to the task list.
     * The task is saved to storage after being added.
     *
     * @param tasks Task list to add the deadline task to.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the description is empty or if the date format is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        if (this.description.trim().isEmpty()) {
            throw new HunnieException("Hey, the description of a deadline task should not be empty!");
        }
        Task newTask = new Deadline(description, by);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
