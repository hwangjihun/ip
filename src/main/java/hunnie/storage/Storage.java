package hunnie.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import hunnie.exception.HunnieException;
import hunnie.task.Deadline;
import hunnie.task.Event;
import hunnie.task.Task;
import hunnie.task.ToDo;

/**
 * Handles the loading and saving of tasks to a file.
 * Tasks are stored in a human-readable format with one task per line.
 */
public class Storage {
    private static final String PATH = "src/main/data/hunnie.txt";
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String filePath;

    /**
     * Creates a new Storage instance with the default file path.
     */
    public Storage() {
        this.filePath = PATH;
    }

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks will be stored.
     */
    public Storage(String filePath) {
        assert filePath != null : "Storage file path should not be null";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * Creates the parent directory and file if they do not exist.
     *
     * @return List of tasks loaded from the file.
     * @throws HunnieException If an error occurs during file operations.
     */
    public ArrayList<Task> load() throws HunnieException {
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

    /**
     * Saves the list of tasks to the storage file.
     * Each task is encoded as a single line in the file.
     *
     * @param tasks List of tasks to save.
     * @throws IOException If an error occurs during file writing.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "Task collection to save should not be null";
        File file = new File(this.filePath);
        FileWriter writer = new FileWriter(file);
        for (Task task : tasks) {
            writer.write(encodeTask(task) + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Encodes a task into a string format suitable for storage.
     * Format: {@code <type> | <isDone> | <description> | [additional fields]}
     *
     * @param task Task to encode.
     * @return String representation of the task for storage.
     */
    private String encodeTask(Task task) {
        assert task != null : "Task to encode should not be null";
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

        assert false : "Unsupported task type encountered during storage encoding: " + task.getClass().getName();
        return "";
    }

    /**
     * Parses a line from the storage file and creates the corresponding Task object.
     *
     * @param line Line from the storage file.
     * @return Task object parsed from the line, or null if the line is invalid.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task = null;
        try {
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
            default:
                break;
            }

            if (task != null && isDone) {
                task.mark();
            }
        } catch (HunnieException e) {
            System.out.println("Warning: Skipping invalid task in file: " + e.getMessage());
            return null;
        }
        return task;
    }
}
