package hunnie.command;

import java.io.IOException;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Event;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to add an event task to the task list.
 * An event task has a description, start date, and end date.
 */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Creates a new event command with the specified description, start date, and end date.
     *
     * @param description Description of the event task.
     * @param from Start date of the event in yyyy-MM-dd format.
     * @param to End date of the event in yyyy-MM-dd format.
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the event command by adding a new event task to the task list.
     * The task is saved to storage after being added.
     *
     * @param tasks Task list to add the event task to.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the description is empty or if the date format is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        if (this.description.trim().isEmpty()) {
            throw new HunnieException("Hey, the description of an event task should not be empty!");
        }
        Task newTask = new Event(this.description, this.from, this.to);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
