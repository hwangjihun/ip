public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        ui.showTaskList(tasks);
    }
}
