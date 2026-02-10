package hunnie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hunnie.exception.HunnieException;
import hunnie.task.Task;
import hunnie.task.TaskList;
import hunnie.task.ToDo;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void size_emptyList_returnsZero() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void add_singleTask_sizeIncreases() {
        Task task = new ToDo("enjoy life");
        taskList.add(task);
        assertEquals(1, taskList.size());
    }

    @Test
    public void add_multipleTasks_correctSize() {
        taskList.add(new ToDo("task 1"));
        taskList.add(new ToDo("task 2"));
        taskList.add(new ToDo("task 3"));
        assertEquals(3, taskList.size());
    }

    @Test
    public void get_validIndex_returnsTask() throws HunnieException {
        Task task = new ToDo("buy Harry Potter book Globet of the Fire");
        taskList.add(task);
        Task retrieved = taskList.get(0);
        assertEquals(task, retrieved);
    }

    @Test
    public void get_indexOutOfRange_exceptionThrown() {
        taskList.add(new ToDo("task"));
        assertThrows(HunnieException.class, () -> {
            taskList.get(5);
        });
    }

    @Test
    public void get_emptyList_exceptionThrown() {
        assertThrows(HunnieException.class, () -> {
            taskList.get(0);
        });
    }

    @Test
    public void delete_validIndex_taskRemoved() throws HunnieException {
        taskList.add(new ToDo("task 1"));
        taskList.add(new ToDo("task 2"));

        taskList.delete(0);

        assertEquals(1, taskList.size());
        assertEquals("task 2", taskList.get(0).getDescription());
    }

    @Test
    public void delete_indexOutOfRange_exceptionThrown() {
        taskList.add(new ToDo("task"));
        assertThrows(HunnieException.class, () -> {
            taskList.delete(5);
        });
    }

}
