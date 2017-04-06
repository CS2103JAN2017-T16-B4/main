package org.teamstbf.yats.logic.parser;

import java.util.regex.Pattern;

import org.teamstbf.yats.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_PERIOD = new Prefix("p/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("b/");

    /* NLP Prefix definitions */
    public static final Prefix PREFIX_NLP_TIME = new Prefix(",");
    public static final Prefix PREFIX_NLP_LOCATION = new Prefix("@");
    public static final Prefix PREFIX_NLP_TAG = new Prefix("#");
    public static final Prefix PREFIX_NLP_HOURS = new Prefix("=");
    public static final Prefix PREFIX_NLP_DESCRIPTION = new Prefix("//");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
													  // or
													  // more
													  // keywords
													  // separated
													  // by
													  // whitespace

}
