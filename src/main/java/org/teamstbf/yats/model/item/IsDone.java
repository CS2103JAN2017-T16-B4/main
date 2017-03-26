package org.teamstbf.yats.model.item;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completeness in the Task Manager.
 * is valid as declared in {@link #isValidPeriod(String)}
 */
//@author A0139448U
public class IsDone {

	public final static String ISDONE_NOTDONE = "No";
	public final static String ISDONE_DONE = "Yes";

	private String value;

	public IsDone() {
		this.value = ISDONE_NOTDONE;
	}

	public IsDone(String done) throws IllegalValueException{
		assert done != null;
		this.value = done;
		if (this.value.equals(ISDONE_DONE)) {
		}
		if (this.value.equals(ISDONE_NOTDONE)) {
		}
	}

	public String getIsDone() {
		return this.value;
	}

	public void markDone() {
		this.value = ISDONE_DONE;
	}
}
