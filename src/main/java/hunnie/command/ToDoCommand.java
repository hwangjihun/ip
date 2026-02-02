package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.task.ToDo;
import hunnie.ui.Ui;

import java.io.IOException;

/**
 * Represents a command to add a todo task to the task list.
 * A todo task has only a description without any date/time information.
 */
public class ToDoCommand extends Command {
    private final String description;

    /**
     * Creates a new todo command with the specified description.
     *
     * @param description Description of the todo task.
     */
    public ToDoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the todo command by adding a new todo task to the task list.
     * The task is saved to storage after being added.
     *
     * @param tasks Task list to add the todo task to.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the description is empty.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        if (this.description.trim().isEmpty()) {
            throw new HunnieException("Hey, the description of a todo task should not be empty!");
        }
        Task newTask = new ToDo(this.description);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
