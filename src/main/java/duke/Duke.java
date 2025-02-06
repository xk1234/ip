package duke;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents the main Duke application.
 * This class initializes the necessary components (Ui, Storage, TaskList)
 * and processes commands by returning appropriate response strings.
 */
public class Duke {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExit; // Tracks whether Duke should exit

    /**
     * Constructs a new Duke instance.
     * Initializes the user interface, storage, and task list.
     * Loads tasks from storage into the task list.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.loadTasks());
        isExit = false;
    }

    /**
     * Handles the "mark" command.
     */
    private String handleMark(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                taskList.markTaskDone(taskIndex);
                return ui.getTaskMarkedMessage(taskList.getTask(taskIndex));
            } else {
                return ui.getInvalidInputError(arguments, "mark <task_number>");
            }
        } catch (NumberFormatException e) {
            return ui.getInvalidInputError(arguments, "mark <task_number>");
        }
    }

    /**
     * Handles the "unmark" command.
     */
    private String handleUnmark(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                taskList.markTaskUndone(taskIndex);
                return ui.getTaskUnmarkedMessage(taskList.getTask(taskIndex));
            } else {
                return ui.getInvalidInputError(arguments, "unmark <task_number>");
            }
        } catch (NumberFormatException e) {
            return ui.getInvalidInputError(arguments, "unmark <task_number>");
        }
    }

    /**
     * Handles the "todo" command.
     */
    private String handleTodo(String arguments, String commandLine) {
        String description = arguments.trim();
        if (description.isEmpty()) {
            return ui.getEmptyDescriptionError(commandLine);
        } else {
            Task newTask = new ToDo(description);
            taskList.addTask(newTask);
            return ui.getTaskAddedMessage(newTask, taskList.getSize());
        }
    }

    /**
     * Handles the "deadline" command.
     */
    private String handleDeadline(String arguments, String commandLine) {
        String[] parts = Parser.parseDeadlineArguments(arguments);
        if (parts.length < 2) {
            return ui.getInvalidInputError(commandLine, "deadline <description> /by <yyyy-MM-dd HHmm>");
        }
        String description = parts[0].trim();
        String byString = parts[1].trim();
        LocalDateTime by = Parser.parseDateTime(byString);
        if (by == null) {
            return ui.getInvalidDateError(commandLine, "deadline <description> /by <yyyy-MM-dd HHmm>");
        }
        Task newTask = new Deadline(description, by);
        taskList.addTask(newTask);
        return ui.getTaskAddedMessage(newTask, taskList.getSize());
    }

    /**
     * Handles the "event" command.
     */
    private String handleEvent(String arguments, String commandLine) {
        String[] parts = Parser.parseEventArguments(arguments);
        if (parts.length < 3) {
            return ui.getInvalidInputError(commandLine, "event <description> "
                    + "/from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }
        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();
        LocalDateTime from = Parser.parseDateTime(fromString);
        LocalDateTime to = Parser.parseDateTime(toString);
        if (from == null || to == null) {
            return ui.getInvalidDateError(commandLine, "event <description> /from <yyyy-MM-dd HHmm>"
                    + " /to <yyyy-MM-dd HHmm>");
        }
        Task newTask = new Event(description, from, to);
        taskList.addTask(newTask);
        return ui.getTaskAddedMessage(newTask, taskList.getSize());
    }

    /**
     * Handles the "delete" command.
     */
    private String handleDelete(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                Task deletedTask = taskList.getTask(taskIndex);
                taskList.deleteTask(taskIndex);
                return ui.getTaskDeletedMessage(deletedTask, taskList.getSize());
            } else {
                return ui.getInvalidInputError(arguments, "delete <task_number>");
            }
        } catch (NumberFormatException e) {
            return ui.getInvalidInputError(arguments, "delete <task_number>");
        }
    }

    /**
     * Handles the "find" command.
     */
    private String handleFind(String keyword) {
        ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
        return ui.getMatchingTasksMessage(matchingTasks);
    }

    /**
     * Checks if the given task index is valid.
     */
    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < taskList.getSize();
    }

    /**
     * Processes the given input command and returns Duke's response as a String.
     * This is the method used by the GUI's event handler.
     *
     * @param input The user's command input.
     * @return A string response containing the result of executing the command.
     */
    public String getResponse(String input) {
        StringBuilder response = new StringBuilder();
        String[] parts = Parser.parseCommand(input);
        String command = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        switch (command.toLowerCase()) {
        case "bye":
            isExit = true;
            response.append(ui.getGoodbyeMessage());
            break;
        case "list":
            response.append(ui.getTaskListMessage());
            for (int i = 0; i < taskList.getSize(); i++) {
                response.append("\n").append(ui.getTaskListItem(i, taskList.getTask(i)));
            }
            break;
        case "mark":
            response.append(handleMark(arguments));
            break;
        case "unmark":
            response.append(handleUnmark(arguments));
            break;
        case "todo":
            response.append(handleTodo(arguments, input));
            break;
        case "deadline":
            response.append(handleDeadline(arguments, input));
            break;
        case "event":
            response.append(handleEvent(arguments, input));
            break;
        case "delete":
            response.append(handleDelete(arguments));
            break;
        case "find":
            response.append(handleFind(arguments));
            break;
        default:
            response.append(ui.getInvalidCommandError(input));
            break;
        }

        // Save the tasks after processing the command.
        storage.saveTasks(taskList);
        return response.toString();
    }

    public String getWelcome() {
        return "As a high performance robot, this is what I can do: \n"
                + "✔ Add tasks: \n"
                + "   - todo <task description>\n"
                + "   - deadline <task description> /by <yyyy-MM-dd HHmm>\n"
                + "   - event <task description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>\n"
                + "✔ View tasks: list\n"
                + "✔ Mark tasks as done: mark <task number>\n"
                + "✔ Unmark tasks: unmark <task number>\n"
                + "✔ Delete tasks: delete <task number>\n"
                + "✔ Find tasks: find <keyword>\n"
                + "✔ Exit: bye\n\n"
                + "Now what do you need me to do?\uD83D\uDE0A\n";
    }
}
