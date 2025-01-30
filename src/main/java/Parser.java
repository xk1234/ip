import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Parser {

    public static String[] parseCommand(String inputLine) {
        return inputLine.split(" ", 2);
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static String[] parseDeadlineArguments(String arguments) {
        return arguments.split(" /by ", 2);
    }

    public static String[] parseEventArguments(String arguments) {
        return arguments.split(" /from | /to ");
    }
}