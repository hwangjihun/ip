package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.task.ToDo;
import hunnie.ui.Ui;

import java.io.IOException;

public class ToDoCommand extends Command {
    private final String description;

    public ToDoCommand(String description) {
        this.description = description;
    }

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
