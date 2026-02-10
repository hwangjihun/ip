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
    private static final String DEFAULT_FILE_PATH = "src/main/data/hunnie.txt";
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private boolean isExit;

    /**
     * Creates a new Hunnie instance with the specified file path for data storage.
     * Attempts to load existing tasks from the file, or starts with an empty task list if loading fails.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Hunnie(String filePath) {
        this(filePath, false);
    }

    /**
     * Creates a new Hunnie instance with the specified file path for data storage.
     * Allows suppressing console output for GUI usage.
     *
     * @param filePath Path to the file where tasks are stored.
     * @param isGui Whether this instance is used by a GUI.
     */
    public Hunnie(String filePath, boolean isGui) {
        ui = new Ui(isGui);
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (HunnieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Creates a new Hunnie instance with the default file path for data storage.
     */
    public Hunnie() {
        this(DEFAULT_FILE_PATH, false);
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
        new Hunnie(DEFAULT_FILE_PATH, false).run();
    }

    /**
     * Returns the welcome message for display in the GUI.
     *
     * @return Welcome message.
     */
    public String getWelcomeMessage() {
        ui.clearOutput();
        ui.showWelcome();
        return ui.getOutput();
    }

    /**
     * Handles user input and returns the chatbot's response.
     *
     * @param input User input string.
     * @return Response string to display.
     */
    public String getResponse(String input) {
        ui.clearOutput();
        isExit = false;
        try {
            Command command = Parser.parse(input);
            command.execute(this.tasks, this.ui, this.storage);
            isExit = command.isExit();
        } catch (HunnieException e) {
            ui.showError(e.getMessage());
        }
        return ui.getOutput();
    }

    /**
     * Indicates whether the last command was an exit command.
     *
     * @return true if the last command was an exit command.
     */
    public boolean isExit() {
        return isExit;
    }
}
