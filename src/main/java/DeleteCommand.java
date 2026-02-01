import java.io.IOException;

public class DeleteCommand extends Command {
    private final int taskIdx;

    public DeleteCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        Task taskToDelete = tasks.get(this.taskIdx);
        tasks.delete(this.taskIdx);
        ui.showTaskDeleted(taskToDelete, tasks.size());
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }
}
