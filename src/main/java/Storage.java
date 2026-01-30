import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String PATH = "src/main/data/hunnie.txt";
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String filePath;

    public Storage() {
        this.filePath = PATH;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);

        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(this.filePath);
        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(encodeTask(task) + System.lineSeparator());
        }
        writer.close();
    }

    private String encodeTask(Task task) {
        String isDone = task.getIsDone() ? "1" : "0";

        if (task instanceof ToDo todo) {
            return "T | " + isDone + " | " + todo.getDescription();
        } else if (task instanceof Deadline deadline) {
            String by = deadline.getBy().format(STORAGE_FORMAT);
            return "D | " + isDone + " | " + deadline.getDescription() + " | " + by;
        } else if (task instanceof Event event) {
            String from = event.getFrom().format(STORAGE_FORMAT);
            String to = event.getTo().format(STORAGE_FORMAT);
            return "E | " + isDone + " | " + event.getDescription() + " | " + from + " | " + to;
        }

        return "";
    }
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task = null;

        switch (taskType) {
        case "T":
            if (parts.length == 3) {
                task = new ToDo(description);
            }
            break;
        case "D":
            if (parts.length == 4) {
                String by = parts[3].trim();
                task = new Deadline(description, by);
            }
            break;
        case "E":
            if (parts.length == 5) {
                String from = parts[3].trim();
                String to = parts[4].trim();
                task = new Event(description, from, to);
            }
            break;
        }

        if (task != null && isDone) {
            task.mark();
        }

        return task;
    }

    // test code
    public static void main(String[] args) {
        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.load();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
