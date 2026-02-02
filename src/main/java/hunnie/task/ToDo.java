package hunnie.task;

/**
 * Represents a todo task with only a description and no date/time information.
 */
public class ToDo extends Task {

    /**
     * Creates a new todo task with the specified description.
     *
     * @param description Description of the todo task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
