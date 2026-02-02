package hunnie.ui;

import hunnie.exception.HunnieException;
import hunnie.task.Task;
import hunnie.task.TaskList;

import java.util.Scanner;

/**
 * Handles user interface operations including reading user input and displaying messages.
 */
public class Ui {
    private static final String LINES = "____________________________________________________________";
    private static final String BOTNAME = "Hunnie";
    private final Scanner scanner;

    /**
     * Creates a new UI instance with a scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the user.
     *
     * @return User input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println(LINES);
        System.out.println("Hello! I'm " + BOTNAME);
        System.out.println("What can I do for you?");
        System.out.println(LINES);
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINES);
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINES);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Task list to display.
     * @throws HunnieException If the task list is empty.
     */
    public void showTaskList(TaskList tasks) throws HunnieException {
        if (tasks.size() == 0) {
            throw new HunnieException("No tasks found!");
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task Task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task Task that was marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Displays a message indicating that a task has been added to the list.
     *
     * @param task Task that was added.
     * @param taskCount Current number of tasks in the list.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
    }

    /**
     * Displays a message indicating that a task has been deleted from the list.
     *
     * @param task Task that was deleted.
     * @param taskCount Current number of tasks in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a warning message when tasks cannot be loaded from the file.
     */
    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from file. Starting with empty task list.");
    }

    /**
     * Displays a warning message when tasks cannot be saved to the file.
     *
     * @param message Specific error message from the IOException.
     */
    public void showSaveError(String message) {
        System.out.println("Warning: Could not save tasks to file: " + message);
    }
}
