public class Parser {

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

    private static int parseTaskNumber(String arguments) throws HunnieException {
        try {
            return Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new HunnieException("Invalid task number format!");
        }
    }

    private static String[] parseDeadline(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /by ");
        if (parts.length != 2) {
            throw new HunnieException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        return parts;
    }

    private static String[] parseEvent(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /from | /to ");
        if (parts.length != 3) {
            throw new HunnieException("Invalid event format! Use: event <description> /from <date> /to <date>");
        }
        return parts;
    }
}
