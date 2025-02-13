package duke;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents the main Duke application.
 * This class initializes the necessary components (Ui, Storage, TaskList)
 * and processes commands by returning appropriate response strings.  Uses streams where appropriate.
 */
public class Duke {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private boolean isExit; // Tracks whether Duke should exit

    /**
     * Constructs a new Duke instance.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.loadTasks());
        isExit = false;
    }

    /**
     * Handles the "mark" command using streams.
     */
    private String handleMark(String arguments) {
        return handleTaskIndexOperation(arguments, "mark", taskList::markTaskDone, true);
    }

    /**
     * Handles the "unmark" command using streams.
     */
    private String handleUnmark(String arguments) {
        return handleTaskIndexOperation(arguments, "unmark", taskList::markTaskUndone, false);
    }

    /**
     * Handles task index operations (mark, unmark, delete) using a common method with streams.
     */
    private String handleTaskIndexOperation(String arguments, String command, TaskIndexOperation operation, boolean isMark) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                operation.perform(taskIndex); // Use the provided operation
                Task task = taskList.getTask(taskIndex);
                return isMark ? ui.getTaskMarkedMessage(task) : ui.getTaskUnmarkedMessage(task);
            } else {
                return ui.getInvalidInputError(arguments, command + " <task_number>");
            }
        } catch (NumberFormatException e) {
            return ui.getInvalidInputError(arguments, command + " <task_number>");
        }
    }


    /**
     * Handles the "todo" command.
     */
    private String handleTodo(String arguments, String commandLine) {
        String description = arguments.trim();
        if (description.isEmpty()) {
            return ui.getEmptyDescriptionError(commandLine);
        }
        Task newTask = new ToDo(description);
        taskList.addTask(newTask);
        return ui.getTaskAddedMessage(newTask, taskList.getSize());
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
     * Handles the "delete" command using streams.
     */
    private String handleDelete(String arguments) {
        return handleTaskIndexOperation(arguments, "delete", taskIndex -> {
            Task deletedTask = taskList.getTask(taskIndex); // Get task *before* deletion
            taskList.deleteTask(taskIndex); // Delete task.
        }, false); //dummy boolean
    }


    /**
     * Handles the "find" command using streams.
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
     * Processes the user input and returns a response.
     * @param input The user input string.
     * @return The response string.
     */
    public String getResponse(String input) {
        String[] parts = Parser.parseCommand(input);
        String command = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        String response;
        switch (command.toLowerCase()) {
        case "bye":
            isExit = true;
            response = ui.getGoodbyeMessage();
            break;
        case "list":
            response = IntStream.range(0, taskList.getSize())
                    .mapToObj(i -> ui.getTaskListItem(i, taskList.getTask(i)))
                    .collect(Collectors.joining("\n", ui.getTaskListMessage() + "\n", ""));
            break;
        case "mark":
            response = handleMark(arguments);
            break;
        case "unmark":
            response = handleUnmark(arguments);
            break;
        case "todo":
            response = handleTodo(arguments, input);
            break;
        case "deadline":
            response = handleDeadline(arguments, input);
            break;
        case "event":
            response = handleEvent(arguments, input);
            break;
        case "delete":
            response = handleDelete(arguments);
            break;
        case "find":
            response = handleFind(arguments);
            break;
        default:
            response = ui.getInvalidCommandError(input);
            break;
        }

        // Save after any command that might modify the task list.
        storage.saveTasks(taskList);
        return response;
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

    /**
     * Functional interface for task index operations.
     */
    @FunctionalInterface
    private interface TaskIndexOperation {
        void perform(int taskIndex);
    }
}
