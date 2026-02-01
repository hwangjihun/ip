package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

import java.io.IOException;

public class MarkCommand extends Command {
    private final int taskIdx;

    public MarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

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
