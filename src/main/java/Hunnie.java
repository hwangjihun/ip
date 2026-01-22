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
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
                }
            }
            else if (cmd[0].equals("mark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                tasks[taskID].isDone = true;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[" +  tasks[taskID].getStatusIcon() + "] " + tasks[taskID].description);
            }
            else if (cmd[0].equals("unmark")) {
                int taskID = Integer.parseInt(cmd[1]) - 1;
                tasks[taskID].isDone = false;
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[" +  tasks[taskID].getStatusIcon() + "] " + tasks[taskID].description);
            }
            else {
                tasks[numberOfTasks] = new Task(command);
                System.out.println("added: " + command);
                numberOfTasks++;
            }
            System.out.println(lines);
        }
    }
}
