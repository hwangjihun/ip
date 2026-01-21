import java.util.Scanner;

public class Hunnie {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String lines = "____________________________________________________________";
        String botName = "Hunnie";
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
            } else {
                System.out.println(cmd);
            }
            System.out.println(lines);
        }
    }
}
