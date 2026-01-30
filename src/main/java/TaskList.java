import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public void delete(int index) throws HunnieException {
        if (index < 0 || index >= tasks.size()) {
            throw new HunnieException("Invalid task number! You only have " + tasks.size() + " task(s).");
        }
        tasks.remove(index);
    }

    public Task get(int index) throws HunnieException {
        if (index < 0 || index >= tasks.size()) {
            throw new HunnieException("Invalid task number! You only have " + tasks.size() + " task(s).");
        }
        return tasks.get(index);
    }

    public void mark(int index) throws HunnieException {
        Task task = get(index);
        task.mark();
    }

    public void unmark(int index) throws HunnieException {
        Task task = get(index);
        task.unMark();
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}
