package hunnie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hunnie.task.ToDo;

public class ToDoTest {

    @Test
    public void constructor_validDescription_success() {
        ToDo todo = new ToDo("read harry potter");
        assertEquals("read harry potter", todo.getDescription());
    }

    @Test
    public void getIsDone_newTask_returnsFalse() {
        ToDo todo = new ToDo("enjoy life");
        assertFalse(todo.getIsDone());
    }

    @Test
    public void getStatusIcon_notDone_returnsSpace() {
        ToDo todo = new ToDo("exercise");
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void getStatusIcon_done_returnsX() {
        ToDo todo = new ToDo("exercise");
        todo.mark();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void mark_task_setsIsDoneTrue() {
        ToDo todo = new ToDo("finish homework");
        todo.mark();
        assertTrue(todo.getIsDone());
    }

    @Test
    public void unMark_markedTask_setsIsDoneFalse() {
        ToDo todo = new ToDo("clean room");
        todo.mark();
        todo.unMark();
        assertFalse(todo.getIsDone());
    }

    @Test
    public void toString_notDone_correctFormat() {
        ToDo todo = new ToDo("esports watch party with friends");
        assertEquals("[T][ ] esports watch party with friends", todo.toString());
    }

    @Test
    public void toString_done_correctFormat() {
        ToDo todo = new ToDo("read book");
        todo.mark();
        assertEquals("[T][X] read book", todo.toString());
    }

    @Test
    public void mark_multipleSequence_correctState() {
        ToDo todo = new ToDo("task");

        assertFalse(todo.getIsDone());

        todo.mark();
        assertTrue(todo.getIsDone());

        todo.unMark();
        assertFalse(todo.getIsDone());

        todo.mark();
        assertTrue(todo.getIsDone());
    }

    @Test
    public void toString_anyTask_startsWithT() {
        ToDo todo = new ToDo("sample task");
        assertTrue(todo.toString().startsWith("[T]"));
    }
}
