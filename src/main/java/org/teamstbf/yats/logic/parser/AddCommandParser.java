package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_ADD_DESCRIPTION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_ADD_TIME;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_ADD_LOCATION;
import static org.teamstbf.yats.logic.parser.CliSyntax.PREFIX_ADD_TAG;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;

import com.joestelmach.natty.DateGroup;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_ADD_LOCATION, PREFIX_ADD_TIME, PREFIX_ADD_DESCRIPTION, PREFIX_ADD_TAG);
        argsTokenizer.tokenize(args);
        try {
            HashMap<String, Object> addParam = new HashMap<>();
            addParam.put("name", argsTokenizer.getPreamble().get());
            addParam.put("location", argsTokenizer.getValue(PREFIX_ADD_LOCATION).orElse(null));
            addParam.put("time", 
                    getDateTimefromWords(argsTokenizer.getValue(PREFIX_ADD_TIME).orElse(null)));
            addParam.put("description", argsTokenizer.getValue(PREFIX_ADD_DESCRIPTION).orElse(null));
            addParam.put("tag", ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_ADD_TAG)));
            return new AddCommand(addParam);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private List<Date> getDateTimefromWords(String words) throws IllegalValueException {
        //Date referenceDate = new Date();
        if (words == null) {
            return null;
        }
        com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
        //dateGroup contains isRecurring() and getRecursUntil() methods that can be used later
        List<DateGroup> dateGroup = dateParser.parse(words);
        List<Date> dateList = dateGroup.isEmpty() ? new ArrayList<Date>() : dateGroup.get(0).getDates();
        return dateList;
    }

}
