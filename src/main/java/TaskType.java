public enum TaskType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event");

    private final String cmd;

    private TaskType(String cmd) {
        this.cmd = cmd;
    }
    public String toString() {
        return cmd;
    }

    public static TaskType fromString(String cmd) {
        for (TaskType type : TaskType.values()) {
            if (type.cmd.equals(cmd)) {
                return type;
            }
        }
        return null;
    }
}
