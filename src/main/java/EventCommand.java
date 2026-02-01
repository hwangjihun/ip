import java.io.IOException;

public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

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
