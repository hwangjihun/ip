package hunnie.parser;

import hunnie.command.Command;
import hunnie.command.DeadlineCommand;
import hunnie.command.DeleteCommand;
import hunnie.command.EventCommand;
import hunnie.command.ExitCommand;
import hunnie.command.FindCommand;
import hunnie.command.ListCommand;
import hunnie.command.MarkCommand;
import hunnie.command.ToDoCommand;
import hunnie.command.UnknownCommand;
import hunnie.command.UnmarkCommand;
import hunnie.exception.HunnieException;

/**
 * Parses user input and creates the appropriate command objects.
 */
public class Parser {
    private static final int COMMAND_PARTS_LIMIT = 2;
    private static final String COMMAND_SEPARATOR = " ";
    private static final String EMPTY_ARGUMENTS = "";
    private static final int USER_TASK_NUMBER_OFFSET = 1;
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final String EMPTY_COMMAND_MESSAGE = "Empty commands are not allowed!!!";
    private static final String INVALID_TASK_NUMBER_MESSAGE = "Invalid task number format!";
    private static final String INVALID_DEADLINE_FORMAT_MESSAGE =
            "Invalid deadline format! Use: deadline <description> /by <date>";
    private static final String INVALID_EVENT_FORMAT_MESSAGE =
            "Invalid event format! Use: event <description> /from <date> /to <date>";
    private static final String EMPTY_FIND_KEYWORD_MESSAGE = "Please provide a keyword to search for!";

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
            throw new HunnieException(EMPTY_COMMAND_MESSAGE);
        }

        String[] cmdParts = fullCommand.trim().split(COMMAND_SEPARATOR, COMMAND_PARTS_LIMIT);
        String cmd = cmdParts[0].toLowerCase();
        String args = cmdParts.length > 1 ? cmdParts[1] : EMPTY_ARGUMENTS;

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
        case "find":
            return new FindCommand(parseFindKeyword(args));
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
        assert arguments != null : "Task-number arguments should not be null";
        try {
            return Integer.parseInt(arguments.trim()) - USER_TASK_NUMBER_OFFSET;
        } catch (NumberFormatException e) {
            throw new HunnieException(INVALID_TASK_NUMBER_MESSAGE);
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
        assert arguments != null : "Deadline arguments should not be null";
        int byIndex = arguments.indexOf(DEADLINE_SEPARATOR);
        boolean hasExactlyOneBySeparator = byIndex != -1 && byIndex == arguments.lastIndexOf(DEADLINE_SEPARATOR);
        if (!hasExactlyOneBySeparator) {
            throw new HunnieException(INVALID_DEADLINE_FORMAT_MESSAGE);
        }
        String description = arguments.substring(0, byIndex);
        String by = arguments.substring(byIndex + DEADLINE_SEPARATOR.length());
        return new String[] {description, by};
    }

    /**
     * Parses the event command arguments to extract description, start date, and end date.
     *
     * @param arguments String containing the event description, start date, and end date.
     * @return Array containing the description at index 0, start date at index 1, and end date at index 2.
     * @throws HunnieException If the event format is invalid.
     */
    private static String[] parseEvent(String arguments) throws HunnieException {
        assert arguments != null : "Event arguments should not be null";
        String[] parts = arguments.split(" /from | /to ");
        if (parts.length != 3) {
            throw new HunnieException(INVALID_EVENT_FORMAT_MESSAGE);
        }
        return parts;
    }

    /**
     * Parses and validates the keyword for the find command.
     *
     * @param arguments String containing the keyword to search for.
     * @return Trimmed keyword string.
     * @throws HunnieException If the keyword is empty.
     */
    private static String parseFindKeyword(String arguments) throws HunnieException {
        assert arguments != null : "Find arguments should not be null";
        if (arguments.trim().isEmpty()) {
            throw new HunnieException(EMPTY_FIND_KEYWORD_MESSAGE);
        }
        return arguments.trim();
    }
}
