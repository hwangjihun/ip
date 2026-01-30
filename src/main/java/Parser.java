public class Parser {

    public enum Command {
        BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN
    }

    public static class ParsedCommand {
        private final Command commandType;
        private final String arguments;

        public ParsedCommand(Command commandType, String arguments) {
            this.commandType = commandType;
            this.arguments = arguments;
        }

        public Command getCommandType() {
            return commandType;
        }

        public String getArguments() {
            return arguments;
        }

        public int getTaskNumber() throws HunnieException {
            try {
                return Integer.parseInt(arguments.trim()) - 1;
            } catch (NumberFormatException e) {
                throw new HunnieException("Invalid task number format!");
            }
        }
    }

    public static ParsedCommand parse(String input) throws HunnieException {
        if (input == null || input.trim().isEmpty()) {
            throw new HunnieException("Please enter a command!");
        }

        String[] parts = input.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        Command command;
        switch (commandWord) {
        case "bye":
            command = Command.BYE;
            break;
        case "list":
            command = Command.LIST;
            break;
        case "mark":
            command = Command.MARK;
            break;
        case "unmark":
            command = Command.UNMARK;
            break;
        case "delete":
            command = Command.DELETE;
            break;
        case "todo":
            command = Command.TODO;
            break;
        case "deadline":
            command = Command.DEADLINE;
            break;
        case "event":
            command = Command.EVENT;
            break;
        default:
            command = Command.UNKNOWN;
            break;
        }

        return new ParsedCommand(command, arguments);
    }

    public static String[] parseDeadline(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /by ");
        if (parts.length != 2) {
            throw new HunnieException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        return parts;
    }

    public static String[] parseEvent(String arguments) throws HunnieException {
        String[] parts = arguments.split(" /from | /to ");
        if (parts.length != 3) {
            throw new HunnieException("Invalid event format! Use: event <description> /from <date> /to <date>");
        }
        return parts;
    }
}
