import java.util.Scanner;

public class Ui {
    private static final String LINES = "____________________________________________________________";
    private static final String BOTNAME = "Hunnie";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println(LINES);
        System.out.println("Hello! I'm " + BOTNAME);
        System.out.println("What can I do for you?");
        System.out.println(LINES);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINES);
    }

    public void showLine() {
        System.out.println(LINES);
    }

    public void showTaskList(TaskList tasks) throws HunnieException {
        if (tasks.size() == 0) {
            throw new HunnieException("No tasks found!");
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " task(s) in the list.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from file. Starting with empty task list.");
    }

    public void showSaveError(String message) {
        System.out.println("Warning: Could not save tasks to file: " + message);
    }
}
