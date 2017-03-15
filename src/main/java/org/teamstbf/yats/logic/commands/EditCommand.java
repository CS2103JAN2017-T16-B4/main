package org.teamstbf.yats.logic.commands;

import java.util.List;
import java.util.Optional;

import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.commons.util.CollectionUtil;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Date;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * Edits the details of an existing task in the task scheduler.
 */
public class EditCommand extends Command {

	/**
	 * Stores the details to edit the person with. Each non-empty field value
	 * will replace the corresponding field value of the person.
	 */
	public static class EditTaskDescriptor {
		private Optional<Title> name = Optional.empty();
		private Optional<Date> deadline = Optional.empty();
		private Optional<Schedule> startTime = Optional.empty();
		private Optional<Schedule> endTime = Optional.empty();
		private Optional<Description> description = Optional.empty();
		private Optional<Periodic> periodic = Optional.empty();
		private Optional<UniqueTagList> tags = Optional.empty();

		public EditTaskDescriptor() {
		}

		public EditTaskDescriptor(EditTaskDescriptor toCopy) {
			this.name = toCopy.getName();
			this.deadline = toCopy.getDeadline();
			this.startTime = toCopy.getStartTime();
			this.endTime = toCopy.getStartTime();
			this.description = toCopy.getDescription();
			this.periodic = toCopy.getPeriodic();
			this.tags = toCopy.getTags();
		}

		public Optional<Date> getDeadline() {
			return deadline;
		}

		public Optional<Description> getDescription() {
			return description;
		}

		public Optional<Schedule> getEndTime() {
			return endTime;
		}

		public Optional<Title> getName() {
			return name;
		}

		public Optional<Periodic> getPeriodic() {
			return periodic;
		}

		public Optional<Schedule> getStartTime() {
			return startTime;
		}

		public Optional<UniqueTagList> getTags() {
			return tags;
		}

		/**
		 * Returns true if at least one field is edited.
		 */
		public boolean isAnyFieldEdited() {
			return CollectionUtil.isAnyPresent(this.name, this.deadline, this.startTime, this.description,
					this.periodic, this.tags);
		}

		public void setDeadline(Optional<Date> deadline) {
			assert deadline != null;
			this.deadline = deadline;
		}

		public void setDescription(Optional<Description> description) {
			assert description != null;
			this.description = description;
		}

		public void setEndTime(Optional<Schedule> schedule) {
			assert schedule != null;
			this.endTime = schedule;
		}

		public void setName(Optional<Title> name) {
			assert name != null;
			this.name = name;
		}

		public void setPeriodic(Optional<Periodic> periodic) {
			assert periodic != null;
			this.periodic = periodic;
		}

		public void setStartTime(Optional<Schedule> schedule) {
			assert schedule != null;
			this.startTime = schedule;
		}

		public void setTags(Optional<UniqueTagList> tags) {
			assert tags != null;
			this.tags = tags;
		}
	}

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
			+ "by the index number used in the last task listing. "
			+ "Existing values will be overwritten by the input values.\n"
			+ "Parameters: INDEX (must be a positive integer) [b/DEADLINE] [s/TIME] [d/DESCRIPTION] [t/TAGS]...\n"
			+ "Example: " + COMMAND_WORD + " 1 b/02-02-2017 t/school";
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
	public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

	/**
	 * Creates and returns a {@code Task} with the details of {@code taskToEdit}
	 * edited with {@code editTaskDescriptor}.
	 */
	private static Event createEditedTask(ReadOnlyEvent taskToEdit, EditTaskDescriptor editTaskDescriptor) {
		assert taskToEdit != null;

		Title updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getTitle);
		// Deadline updatedPhone =
		// editPersonDescriptor.getPhone().orElseGet(personToEdit::getDeadline);
		// Timing updatedEmail =
		// editPersonDescriptor.getEmail().orElseGet(personToEdit::getTiming);
		Schedule updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
		Schedule updatedEndTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getEndTime);
		Date updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
		Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
		UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

		return new Event();
	}

	private final int filteredTaskListIndex;

	private final EditTaskDescriptor editTaskDescriptor;

	/**
	 * @param filteredTaskListIndex
	 *            the index of the task in the filtered task list to edit
	 * @param editTaskDescriptor
	 *            details to edit the task
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
			model.updateEvent(filteredTaskListIndex, editedTask);
		} catch (UniqueEventList.DuplicateEventException dpe) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
		model.updateFilteredListToShowAll();
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}
}
