package hunnie.task;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types in the Hunnie application.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description should not be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean getIsDone() {
        return isDone();
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if the task is done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Marks this task as not done.
     */
    public void unMark() {
        unmark();
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
