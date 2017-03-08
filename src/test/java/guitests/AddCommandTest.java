package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.testutil.TestPerson;
import org.teamstbf.yats.testutil.TestUtil;

import guitests.guihandles.PersonCardHandle;
<<<<<<< HEAD
=======
import t16b4.yats.testutil.TestEvent;
import t16b4.yats.testutil.TestUtil;
>>>>>>> origin/v0.1.2-Class-Variable-Refactor-Update

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestEvent[] currentList = td.getTypicalPersons();
        TestEvent personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestEvent personToAdd, TestEvent... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getTitle().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestEvent[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
