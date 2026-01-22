import java.util.Scanner;

public class Hunnie {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String lines = "____________________________________________________________";
        String botName = "Hunnie";
        Task[] tasks = new Task[100];
        int numberOfTasks = 0;

        System.out.println(lines);
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println(lines);

        while (true) {
            String command = scanner.nextLine();
            String[] cmd = command.split(" ", 2);

            System.out.println(lines);
            if (cmd[0].equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(lines);
                break;
            }
            else if (cmd[0].equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            }
            else if (cmd[0].equals("mark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                tasks[taskID].mark();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[taskID]);
            }
            else if (cmd[0].equals("unmark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                tasks[taskID].unMark();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[taskID]);
            }
            else {
                if (cmd[0].equals("todo")) {
                    tasks[numberOfTasks] = new ToDo(cmd[1]);
                } else if (cmd[0].equals("deadline")) {
                    String[] deadline = cmd[1].split(" /by ");
                    tasks[numberOfTasks] = new Deadline(deadline[0], deadline[1]);
                } else if (cmd[0].equals("event")) {
                    String[] timeSpan = cmd[1].split(" /from | /to ");
                    tasks[numberOfTasks] = new Event(timeSpan[0], timeSpan[1], timeSpan[2]);
                }
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[numberOfTasks]);
                numberOfTasks++;
                System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
            }
            System.out.println(lines);
        }
    }
}
