package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_END_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_LOCATION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_PERIOD;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_START_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.EditCommand.EditTaskDescriptor;
import org.teamstbf.yats.logic.commands.IncorrectCommand;
import org.teamstbf.yats.logic.commands.MarkDoneCommand;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new MarkDoneCommand object
 */
//@@author A0139448U
public class MarkDoneCommandParser extends EditCommandParser{
	/**
	 * Parses the given {@code String} of arguments in the context of the MarkDoneCommand
	 * and returns an MarkDoneCommand object for execution.
	 */
	@Override
	public Command parse(String args) {
		assert args != null;
		ArgumentTokenizer argsTokenizer =
				new ArgumentTokenizer(PREFIX_LOCATION, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_DESCRIPTION, PREFIX_PERIOD, PREFIX_TAG);
		argsTokenizer.tokenize(args);
		List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

		Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
		}

		EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
		try {
		    // THIS PART NEEDS MORE EDITING
		    // NLP NOT APPLIED YET
			editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
			editTaskDescriptor.setLocation(ParserUtil.parseLocation(argsTokenizer.getValue(PREFIX_LOCATION)));
			editTaskDescriptor.setTime(ParserUtil.parseSchedule(argsTokenizer.getValue(PREFIX_START_TIME)));
			editTaskDescriptor.setDescription(ParserUtil.parseDescription(argsTokenizer.getValue(PREFIX_DESCRIPTION)));
			editTaskDescriptor.setPeriodic(ParserUtil.parsePeriodic(argsTokenizer.getValue(PREFIX_PERIOD)));
			editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
			editTaskDescriptor.markDone();
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}

		return new MarkDoneCommand(index.get(), editTaskDescriptor);
	}

	/**
	 * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
	 * If {@code tags} contain only one element which is an empty string, it will be parsed into a
	 * {@code Optional<UniqueTagList>} containing zero tags.
	 */
	private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
		assert tags != null;

		if (tags.isEmpty()) {
			return Optional.empty();
		}
		Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
		return Optional.of(ParserUtil.parseTags(tagSet));
	}

}

