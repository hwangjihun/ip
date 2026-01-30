import java.io.IOException;
import java.util.Scanner;

public class Hunnie {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Hunnie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (HunnieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    private void saveToStorage() {
        try {
            storage.save(tasks.getAllTasks());
        } catch (IOException e) {
            ui.showSaveError(e.getMessage());
        }
    }

    private void handleMarkCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        int taskIndex = parsedCommand.getTaskNumber();
        tasks.mark(taskIndex);
        ui.showTaskMarked(tasks.get(taskIndex));
        saveToStorage();
    }

    private void handleUnmarkCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        int taskIndex = parsedCommand.getTaskNumber();
        tasks.unmark(taskIndex);
        ui.showTaskUnmarked(tasks.get(taskIndex));
        saveToStorage();
    }

    private void handleDeleteCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        int taskIndex = parsedCommand.getTaskNumber();
        Task taskToDelete = tasks.get(taskIndex);
        tasks.delete(taskIndex);
        ui.showTaskDeleted(taskToDelete, tasks.size());
        saveToStorage();
    }

    private void handleTodoCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        String description = parsedCommand.getArguments().trim();
        if (description.isEmpty()) {
            throw new HunnieException("Hey, the description of a todo task should not be empty!");
        }
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        saveToStorage();
    }

    private void handleDeadlineCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        String[] parts = Parser.parseDeadline(parsedCommand.getArguments());
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new HunnieException("Hey, the description of a deadline task should not be empty!");
        }

        Task newTask = new Deadline(description, by);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        saveToStorage();
    }

    private void handleEventCommand(Parser.ParsedCommand parsedCommand) throws HunnieException {
        String[] parts = Parser.parseEvent(parsedCommand.getArguments());
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new HunnieException("Hey, the description of an event task should not be empty!");
        }

        Task newTask = new Event(description, from, to);
        tasks.add(newTask);
        ui.showTaskAdded(newTask, tasks.size());
        saveToStorage();
    }

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String input = scanner.nextLine();
                ui.showLine();

                Parser.ParsedCommand parsedCommand = Parser.parse(input);

                switch (parsedCommand.getCommandType()) {
                case BYE:
                    ui.showGoodbye();
                    scanner.close();
                    return;
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK:
                    handleMarkCommand(parsedCommand);
                    break;
                case UNMARK:
                    handleUnmarkCommand(parsedCommand);
                    break;
                case DELETE:
                    handleDeleteCommand(parsedCommand);
                    break;
                case TODO:
                    handleTodoCommand(parsedCommand);
                    break;
                case DEADLINE:
                    handleDeadlineCommand(parsedCommand);
                    break;
                case EVENT:
                    handleEventCommand(parsedCommand);
                    break;
                case UNKNOWN:
                default:
                    throw new HunnieException("I am sorry. Idk what that means at the moment!");
                }
            } catch (HunnieException | IllegalArgumentException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Invalid command format! Please check your input again ^^");
            }

            ui.showLine();
        }
    }

    public static void main(String[] args) {
        new Hunnie("src/main/data/hunnie.txt").run();
    }
}