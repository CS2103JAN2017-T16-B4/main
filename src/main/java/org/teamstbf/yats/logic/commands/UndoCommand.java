package org.teamstbf.yats.logic.commands;

import org.teamstbf.yats.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {
	
	public static final String MESSAGE_SUCCESS = "Undo command success";

	@Override
	public CommandResult execute() throws CommandException {
		// TODO undo command algorithm
		return new CommandResult(MESSAGE_SUCCESS);
	}

}
