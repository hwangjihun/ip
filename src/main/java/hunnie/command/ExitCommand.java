package hunnie.command;

import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to exit the Hunnie application.
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by displaying a goodbye message.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Returns whether this command should cause the application to exit.
     *
     * @return True, as this is an exit command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
