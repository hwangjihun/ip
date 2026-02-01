public class UnknownCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        throw new HunnieException("I am sorry. Idk what that means at the moment!");
    }
}