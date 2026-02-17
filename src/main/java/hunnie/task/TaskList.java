package hunnie.task;

import java.util.ArrayList;

import hunnie.exception.HunnieException;

/**
 * Represents a list of tasks and provides operations to manage them.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates a new empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a new task list with the specified tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list should not be initialized with null storage";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        assert task != null : "Added task should not be null";
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index from the task list.
     *
     * @param index Zero-based index of the task to delete.
     * @throws HunnieException If the index is invalid.
     */
    public void delete(int index) throws HunnieException {
        if (index < 0 || index >= tasks.size()) {
            throw new HunnieException("Invalid task number! You only have " + tasks.size() + " task(s).");
        }
        tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Zero-based index of the task to retrieve.
     * @return Task at the specified index.
     * @throws HunnieException If the index is invalid.
     */
    public Task get(int index) throws HunnieException {
        if (index < 0 || index >= tasks.size()) {
            throw new HunnieException("Invalid task number! You only have " + tasks.size() + " task(s).");
        }
        Task task = tasks.get(index);
        assert task != null : "Stored tasks should not contain null entries";
        return task;
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index Zero-based index of the task to mark.
     * @throws HunnieException If the index is invalid.
     */
    public void mark(int index) throws HunnieException {
        Task task = get(index);
        task.mark();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index Zero-based index of the task to unmark.
     * @throws HunnieException If the index is invalid.
     */
    public void unmark(int index) throws HunnieException {
        Task task = get(index);
        task.unMark();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of all tasks.
     *
     * @return ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public TaskList getFilteredTasks(String keyword) {
        assert keyword != null : "Filter keyword should not be null";
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : this.tasks) {
            assert task != null : "Stored tasks should not contain null entries";
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return new TaskList(matchingTasks);
    }
}
