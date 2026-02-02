package hunnie;

import hunnie.command.Command;
import hunnie.exception.HunnieException;
import hunnie.parser.Parser;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents the main class of the Hunnie application.
 * Hunnie is a task management chatbot that helps users track their todos, deadlines, and events.
 */
public class Hunnie {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new Hunnie instance with the specified file path for data storage.
     * Attempts to load existing tasks from the file, or starts with an empty task list if loading fails.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Hunnie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (HunnieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Hunnie application.
     * Continuously reads user commands, parses them, executes them, and displays results
     * until an exit command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (HunnieException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Starts the Hunnie application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Hunnie("src/main/data/hunnie.txt").run();
    }
}