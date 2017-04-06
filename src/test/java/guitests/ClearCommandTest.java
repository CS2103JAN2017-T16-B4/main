package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClearCommandTest extends TaskManagerGuiTest {

	@Test
	public void clear() {

		//verify a non-empty list can be cleared
		assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
		assertClearCommandSuccess();

		//verify other commands can work after a clear command
		commandBox.runCommand("add Act like a craven"); //td.cower.getAddCommand()
		assertTrue(taskListPanel.isListMatching(td.cower));
		commandBox.runCommand("delete 1");
		assertListSize(0);

		//verify clear command works when the list is empty
		assertClearCommandSuccess();
	}

	private void assertClearCommandSuccess() {
		commandBox.runCommand("reset");
		assertListSize(0);
		assertResultMessage("Task Manager has been reset!");
	}
}
