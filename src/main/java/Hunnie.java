import java.util.ArrayList;
import java.util.Scanner;

public class Hunnie {
    private static final String LINES = "____________________________________________________________";
    private static final String BOTNAME = "Hunnie";

    private ArrayList<Task> tasks;

    public Hunnie() {
        this.tasks = new ArrayList<>();
    }

    private void greet() {
        System.out.println(LINES);
        System.out.println("Hello! I'm " + BOTNAME);
        System.out.println("What can I do for you?");
        System.out.println(LINES);
    }

    private void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINES);
    }

    private void getList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + "." + this.tasks.get(i));
        }
    }

    private void markAsDone(int taskID) {
        this.tasks.get(taskID).mark();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(this.tasks.get(taskID));
    }

    private void unmark(int taskID) {
        this.tasks.get(taskID).unMark();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(this.tasks.get(taskID));
    }

    private void addTask(String taskType, String taskDesc) {
        Task newTask = null;

        if (taskType.equals("todo")) {
            newTask = new ToDo(taskDesc);
        } else if (taskType.equals("deadline")) {
            String[] deadline = taskDesc.split(" /by ");
            newTask = new Deadline(deadline[0], deadline[1]);
        } else if (taskType.equals("event")) {
            String[] timeSpan = taskDesc.split(" /from | /to ");
            newTask = new Event(timeSpan[0], timeSpan[1], timeSpan[2]);
        }
        this.tasks.add(newTask);
        System.out.println("Got it. I've added this task:");
        System.out.println(newTask);
        System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
    }

    public static void main(String[] args) {
        Hunnie bot = new Hunnie();
        Scanner scanner = new Scanner(System.in);

        bot.greet();

        while (true) {
            String command = scanner.nextLine();
            String[] cmd = command.split(" ", 2);

            System.out.println(LINES);
            if (cmd[0].equals("bye")) {
                bot.goodbye();
                break;
            }
            else if (cmd[0].equals("list")) {
                bot.getList();
            }
            else if (cmd[0].equals("mark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                bot.markAsDone(taskID);
            }
            else if (cmd[0].equals("unmark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                bot.unmark(taskID);
            }
            else {
                bot.addTask(cmd[0], cmd[1]);
            }
            System.out.println(LINES);
        }
    }
}
