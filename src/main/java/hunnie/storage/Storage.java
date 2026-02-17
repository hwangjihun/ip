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
    private static final String DEFAULT_FILE_PATH = "src/main/data/hunnie.txt";
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";
    private static final String DONE_FLAG = "1";
    private static final String NOT_DONE_FLAG = "0";
    private static final String FIELD_SEPARATOR = " | ";
    private static final String FIELD_SPLIT_REGEX = " \\| ";
    private static final int TODO_FIELD_COUNT = 3;
    private static final int DEADLINE_FIELD_COUNT = 4;
    private static final int EVENT_FIELD_COUNT = 5;
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String filePath;

    /**
     * Creates a new Storage instance with the default file path.
     */
    public Storage() {
        this.filePath = DEFAULT_FILE_PATH;
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
     * Creates the parent directory if it does not exist.
     *
     * @return List of tasks loaded from the file.
     * @throws HunnieException If an error occurs during file operations.
     */
    public ArrayList<Task> load() throws HunnieException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(this.filePath);
        try {
            ensureParentDirectoryExists(file);
            if (!file.exists()) {
                return loadedTasks;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Task task = parseTask(line);
                    if (task != null) {
                        loadedTasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            throw new HunnieException("Could not load tasks from file: " + e.getMessage());
        }

        return loadedTasks;
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
        ensureParentDirectoryExists(file);
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(encodeTask(task) + System.lineSeparator());
            }
        }
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
        String doneFlag = task.isDone() ? DONE_FLAG : NOT_DONE_FLAG;
        String isDone = task.getIsDone() ? "1" : "0";

        if (task instanceof ToDo todo) {
            return TASK_TYPE_TODO + FIELD_SEPARATOR + doneFlag + FIELD_SEPARATOR + todo.getDescription();
        }
        if (task instanceof Deadline deadline) {
            String by = deadline.getBy().format(STORAGE_FORMAT);
            return TASK_TYPE_DEADLINE + FIELD_SEPARATOR + doneFlag + FIELD_SEPARATOR
                    + deadline.getDescription() + FIELD_SEPARATOR + by;
        }
        if (task instanceof Event event) {
            String from = event.getFrom().format(STORAGE_FORMAT);
            String to = event.getTo().format(STORAGE_FORMAT);
            return TASK_TYPE_EVENT + FIELD_SEPARATOR + doneFlag + FIELD_SEPARATOR + event.getDescription()
                    + FIELD_SEPARATOR + from + FIELD_SEPARATOR + to;
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
        String[] parts = line.split(FIELD_SPLIT_REGEX);

        if (parts.length < TODO_FIELD_COUNT) {
            return null;
        }

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals(DONE_FLAG);
        String description = parts[2].trim();

        try {
            Task task = decodeTask(taskType, description, parts);
            markTaskAsDoneIfNeeded(task, isDone);
            return task;
        } catch (HunnieException e) {
            System.out.println("Warning: Skipping invalid task in file: " + e.getMessage());
            return null;
        }
    }

    private Task decodeTask(String taskType, String description, String[] parts) throws HunnieException {
        switch (taskType) {
        case TASK_TYPE_TODO:
            return parts.length == TODO_FIELD_COUNT ? new ToDo(description) : null;
        case TASK_TYPE_DEADLINE:
            return decodeDeadline(description, parts);
        case TASK_TYPE_EVENT:
            return decodeEvent(description, parts);
        default:
            return null;
        }
    }

    private Task decodeDeadline(String description, String[] parts) throws HunnieException {
        if (parts.length != DEADLINE_FIELD_COUNT) {
            return null;
        }
        return new Deadline(description, parts[3].trim());
    }

    private Task decodeEvent(String description, String[] parts) throws HunnieException {
        if (parts.length != EVENT_FIELD_COUNT) {
            return null;
        }
        return new Event(description, parts[3].trim(), parts[4].trim());
    }

    private void markTaskAsDoneIfNeeded(Task task, boolean isDone) {
        if (task != null && isDone) {
            task.mark();
        }
    }

    private void ensureParentDirectoryExists(File file) throws IOException {
        File parentDirectory = file.getParentFile();
        if (parentDirectory == null || parentDirectory.exists()) {
            return;
        }

        if (!parentDirectory.mkdirs()) {
            throw new IOException("Could not create directory: " + parentDirectory.getPath());
        }
    }
}
