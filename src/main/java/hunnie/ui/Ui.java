package hunnie.ui;

import java.util.Scanner;

import hunnie.exception.HunnieException;
import hunnie.task.Task;
import hunnie.task.TaskList;

/**
 * Handles user interface operations including reading user input and displaying messages.
 */
public class Ui {
    private static final String LINES = "____________________________________________________________";
    private static final String BOT_NAME = "Hunnie";
    private final Scanner scanner;
    private final StringBuilder output;
    private final boolean isGui;

    /**
     * Creates a new UI instance with a scanner for reading user input.
     */
    public Ui() {
        this(false);
    }

    /**
     * Creates a new UI instance.
     *
     * @param isGui Whether this UI is used by the GUI (suppresses console output).
     */
    public Ui(boolean isGui) {
        this.scanner = new Scanner(System.in);
        this.output = new StringBuilder();
        this.isGui = isGui;
    }

    /**
     * Clears the buffered output.
     */
    public void clearOutput() {
        output.setLength(0);
    }

    /**
     * Returns the buffered output as a string.
     *
     * @return Buffered output.
     */
    public String getOutput() {
        return output.toString();
    }

    /**
     * Reads a command from the user.
     *
     * @return User input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    private void appendLine(String message) {
        output.append(message).append(System.lineSeparator());
        if (!isGui) {
            System.out.println(message);
        }
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        appendLine(LINES);
        appendLine("Hello! I'm " + BOT_NAME);
        appendLine("What can I do for you?");
        appendLine(LINES);
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        appendLine("Bye. Hope to see you again soon!");
        appendLine(LINES);
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        appendLine(LINES);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Task list to display.
     * @throws HunnieException If the task list is empty.
     */
    public void showTaskList(TaskList tasks) throws HunnieException {
        int taskCount = tasks.size();
        if (taskCount == 0) {
            throw new HunnieException("No tasks found!");
        }
        appendLine("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            appendLine((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task Task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        showTaskWithPrefix("Nice! I've marked this task as done:", task);
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task Task that was marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        showTaskWithPrefix("OK, I've marked this task as not done yet:", task);
    }

    /**
     * Displays a message indicating that a task has been added to the list.
     *
     * @param task Task that was added.
     * @param taskCount Current number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        showTaskWithPrefix("Got it. I've added this task:", task);
        showTaskCount(taskCount);
    }

    /**
     * Displays a message indicating that a task has been deleted from the list.
     *
     * @param task Task that was deleted.
     * @param taskCount Current number of tasks in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        showTaskWithPrefix("Noted. I've removed this task:", task);
        showTaskCount(taskCount);
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        appendLine(message);
    }

    /**
     * Displays a warning message when tasks cannot be loaded from the file.
     */
    public void showLoadingError() {
        appendLine("Warning: Could not load tasks from file. Starting with empty task list.");
    }

    /**
     * Displays a warning message when tasks cannot be saved to the file.
     *
     * @param message Specific error message from the IOException.
     */
    public void showSaveError(String message) {
        appendLine("Warning: Could not save tasks to file: " + message);
    }

    private void showTaskWithPrefix(String prefix, Task task) {
        appendLine(prefix);
        appendLine(task.toString());
    }

    private void showTaskCount(int taskCount) {
        appendLine("Now you have " + taskCount + " task(s) in the list.");
    }
}
