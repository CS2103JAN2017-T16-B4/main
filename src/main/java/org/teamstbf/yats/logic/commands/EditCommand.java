package t16b4.yats.logic.commands;

import java.util.List;
import java.util.Optional;

import t16b4.yats.commons.core.Messages;
import t16b4.yats.commons.util.CollectionUtil;
import t16b4.yats.logic.commands.exceptions.CommandException;
import t16b4.yats.model.item.Description;
import t16b4.yats.model.item.Event;
import t16b4.yats.model.item.Periodic;
import t16b4.yats.model.item.ReadOnlyEvent;
import t16b4.yats.model.item.Timing;
import t16b4.yats.model.item.Title;
import t16b4.yats.model.item.Task;
import t16b4.yats.model.item.Deadline;
import t16b4.yats.model.item.ReadOnlyItem;
import t16b4.yats.model.item.UniqueItemList;
import t16b4.yats.model.tag.UniqueTagList;

/**
 * Edits the details of an existing task in the task scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [b/DEADLINE] [s/TIME] [d/DESCRIPTION] [t/TAGS]...\n"
            + "Example: " + COMMAND_WORD + " 1 b/02-02-2017 t/school";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyEvent taskToEdit = lastShownList.get(filteredTaskListIndex);
        Event editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updatePerson(filteredTaskListIndex, editedTask);
        } catch (UniqueItemList.DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Event createEditedTask(ReadOnlyEvent personToEdit,
                                             EditTaskDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Title updatedName = editPersonDescriptor.getName().orElseGet(personToEdit::getTitle);
        // Deadline updatedPhone = editPersonDescriptor.getPhone().orElseGet(personToEdit::getDeadline);
        // Timing updatedEmail = editPersonDescriptor.getEmail().orElseGet(personToEdit::getTiming);
        Description updatedAddress = editPersonDescriptor.getDescription().orElseGet(personToEdit::getDescription);
        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Event();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Title> name = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<Timing> timing = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<Periodic> periodic = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.deadline = toCopy.getDeadline();
            this.timing = toCopy.getTiming();
            this.description = toCopy.getDescription();
            this.periodic = toCopy.getPeriodic();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.deadline, this.timing, this.description, this.periodic, this.tags);
        }

        public void setName(Optional<Title> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Title> getName() {
            return name;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setTiming(Optional<Timing> timing) {
            assert timing != null;
            this.timing = timing;
        }

        public Optional<Timing> getTiming() {
            return timing;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }
        
        public void setPeriodic(Optional<Periodic> periodic) {
        	assert periodic != null;
        	this.periodic = periodic;
        }
        
        public Optional<Periodic> getPeriodic() {
        	return periodic;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
