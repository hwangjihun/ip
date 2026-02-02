package hunnie.parser;

import hunnie.command.Command;
import hunnie.command.ExitCommand;
import hunnie.command.ListCommand;
import hunnie.command.MarkCommand;
import hunnie.command.UnmarkCommand;
import hunnie.command.DeleteCommand;
import hunnie.command.ToDoCommand;
import hunnie.command.DeadlineCommand;
import hunnie.command.EventCommand;
import hunnie.command.UnknownCommand;
import hunnie.exception.HunnieException;

/**
 * Parses user input and creates the appropriate command objects.
 */
public class Parser {

    /**
     * Parses the full command string and returns the corresponding Command object.
     * Supported commands include: bye, list, mark, unmark, delete, todo, deadline, and event.
     *
     * @param fullCommand Full command string entered by the user.
     * @return Command object corresponding to the user input.
     * @throws HunnieException If the command is empty or if required arguments are invalid.
     */
    public static Command parse(String fullCommand) throws HunnieException {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            throw new HunnieException("Empty commands are not allowed!!!");
        }

        String[] cmdParts = fullCommand.trim().split(" ", 2);
        String cmd = cmdParts[0].toLowerCase();
        String args = cmdParts.length > 1 ? cmdParts[1] : "";

        switch (cmd) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(parseTaskNumber(args));
        case "unmark":
            return new UnmarkCommand(parseTaskNumber(args));
        case "delete":
            return new DeleteCommand(parseTaskNumber(args));
        case "todo":
            return new ToDoCommand(args);
        case "deadline":
            String[] deadlineParts = parseDeadline(args);
            return new DeadlineCommand(deadlineParts[0].trim(), deadlineParts[1].trim());
        case "event":
            String[] eventParts = parseEvent(args);
            return new EventCommand(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
        default:
            return new UnknownCommand();
        }
    }

    /**
     * Parses the task number from the argument string and converts it to a zero-based index.
     *
     * @param arguments String containing the task number.
     * @return Zero-based index of the task.
     * @throws HunnieException If the task number format is invalid.
     */
    private static int parseTaskNumber(String arguments) throws HunnieException {
        try {
            return Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new HunnieException("Invalid task number format!");
        }
    }

    /**
     * Parses the deadline command arguments to extract description and due date.
     *
     * @param arguments String containing the deadline description and due date.
     * @return Array containing the description at index 0 and due date at index 1.
     * @throws HunnieException If the deadline format is invalid.
     */
    private static String[] parseDeadline(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /by ");
        if (parts.length != 2) {
            throw new HunnieException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        return parts;
    }

    /**
     * Parses the event command arguments to extract description, start date, and end date.
     *
     * @param arguments String containing the event description, start date, and end date.
     * @return Array containing the description at index 0, start date at index 1, and end date at index 2.
     * @throws HunnieException If the event format is invalid.
     */
    private static String[] parseEvent(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /from | /to ");
        if (parts.length != 3) {
            throw new HunnieException("Invalid event format! Use: event <description> /from <date> /to <date>");
        }
        return parts;
    }
}
