import java.util.Scanner;

public class Hunnie {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String lines = "____________________________________________________________";
        String botName = "Hunnie";
        String[] tasks = new String[100];
        int numberOfTasks = 0;

        System.out.println(lines);
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println(lines);

        while (true) {
            String cmd = scanner.nextLine();
            System.out.println(lines);
            if (cmd.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(lines);
                break;
            }
            else if (cmd.equals("list")) {
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            }
            else {
                tasks[numberOfTasks] = cmd;
                System.out.println("added: "+ cmd);
                numberOfTasks++;
            }
            System.out.println(lines);
        }
    }
}
