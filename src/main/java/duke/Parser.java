package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user commands and date/time strings.
 * This class provides utility methods for parsing user input into commands and for converting date/time strings
 * into LocalDateTime objects.
 */
class Parser {

    /**
     * Parses the user command into command and arguments.
     * Splits the input line by space to separate the command keyword from the arguments.
     *
     * @param inputLine The full input line from the user.
     * @return An array of strings, where the first element is the command \
     *          and the second element is the arguments (can be null if no arguments).
     */
    public static String[] parseCommand(String inputLine) {
        return inputLine.split(" ", 2);
    }

    /**
     * Parses a date and time string into a LocalDateTime object.
     * Uses the format "yyyy-MM-dd HHmm" to parse the date and time string.
     * Returns null if parsing fails due to invalid format.
     *
     * @param dateTimeString The date and time string to parse.
     * @return LocalDateTime object if parsing is successful, null otherwise.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses the arguments for a deadline command.
     * Splits the arguments string by " /by " to separate the description from the deadline time string.
     *
     * @param arguments The arguments string for the deadline command.
     * @return An array of strings, where the first element is the description and the second \
     *          element is the deadline time string.
     */
    public static String[] parseDeadlineArguments(String arguments) {
        return arguments.split(" /by ", 2);
    }

    /**
     * Parses the arguments for an event command.
     * Splits the arguments string by " /from " and " /to " to separate the description, \
     *          from time string, and to time string.
     *
     * @param arguments The arguments string for the event command.
     * @return An array of strings, where the first element is the description, \
     *          the second is the from time string, and the third is the to time string.
     */
    public static String[] parseEventArguments(String arguments) {
        return arguments.split(" /from | /to ");
    }
}
