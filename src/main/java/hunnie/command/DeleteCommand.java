package hunnie.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final List<Integer> taskIndices;

    /**
     * Creates a new delete command for the task at the specified index.
     *
     * @param taskIdx Zero-based index of the task to delete.
     */
    public DeleteCommand(int taskIdx) {
        this(List.of(taskIdx));
    }

    /**
     * Creates a new delete command for the tasks at the specified indexes.
     *
     * @param taskIndices Zero-based indexes of the tasks to delete.
     */
    public DeleteCommand(List<Integer> taskIndices) {
        this.taskIndices = new ArrayList<>(taskIndices);
    }

    /**
     * Executes the delete command by removing the specified tasks from the task list.
     * The updated task list is saved to storage after deletion.
     *
     * @param tasks Task list to delete the task from.
     * @param ui UI to display messages to the user.
     * @param storage Storage to save the updated task list.
     * @throws HunnieException If the task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws HunnieException {
        ArrayList<Integer> uniqueTaskIndices = new ArrayList<>(new LinkedHashSet<>(this.taskIndices));
        ArrayList<Task> tasksToDelete = new ArrayList<>();
        for (int taskIndex : uniqueTaskIndices) {
            tasksToDelete.add(tasks.get(taskIndex));
        }

        ArrayList<Integer> deletionOrder = new ArrayList<>(uniqueTaskIndices);
        deletionOrder.sort(Comparator.reverseOrder());
        for (int taskIndex : deletionOrder) {
            tasks.delete(taskIndex);
        }

        if (tasksToDelete.size() == 1) {
            ui.showTaskDeleted(tasksToDelete.get(0), tasks.size());
        } else {
            ui.showTasksDeleted(tasksToDelete, tasks.size());
        }
        saveTasks(tasks, storage, ui);
    }
}
