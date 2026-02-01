package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Deadline;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

import java.io.IOException;

public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

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
