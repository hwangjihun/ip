package hunnie;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hunnie.command.FindCommand;
import hunnie.exception.HunnieException;
import hunnie.storage.Storage;
import hunnie.task.TaskList;
import hunnie.task.ToDo;
import hunnie.ui.Ui;

public class FindCommandTest {

    @Test
    public void execute_noMatchingTasks_showsFindSpecificErrorMessage() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("finish assignment"));
        FindCommand findCommand = new FindCommand("meeting");

        HunnieException exception = assertThrows(HunnieException.class, () -> {
            findCommand.execute(tasks, new Ui(true), new Storage());
        });

        assertTrue(exception.getMessage().contains("Sorry, I cannot find any matching tasks!"));
    }
}
