package hunnie.command;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command that is not recognized by the parser.
 * This command always throws an exception when executed.
 */
public class UnknownCommand extends Command {

    /**
     * Executes the unknown command by throwing an exception.
     *
     * @param tasks Task list (not used).
     * @param ui UI (not used).
     * @param storage Storage (not used).
     * @throws HunnieException Always thrown to indicate an unknown command.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        throw new HunnieException("I am sorry. Idk what that means at the moment!");
    }
}
