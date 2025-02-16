package atri;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
/**
 * Represents the main Duke application.
 * This class initializes the necessary components (Ui, Storage, TaskList)
 * and processes commands by returning appropriate response strings.
 * Uses streams where appropriate.
 */
public class Atri {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a new Atri instance.
     */
    public Atri() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.loadTasks());
    }

    /**
     * Handles the "mark" command using streams.
     */
    private String handleMark(String arguments) {
        return handleTaskIndexOperation(arguments, "mark", taskIndex -> {
            taskList.markTaskDone(taskIndex);
            Task task = taskList.getTask(taskIndex);
            return ui.getTaskMarkedMessage(task);
        });
    }

    /**
     * Handles the "unmark" command using streams.
     */
    private String handleUnmark(String arguments) {
        return handleTaskIndexOperation(arguments, "unmark", taskIndex -> {
            taskList.markTaskUndone(taskIndex);
            Task task = taskList.getTask(taskIndex);
            return ui.getTaskUnmarkedMessage(task);
        });
    }

    /**
     * Handles the "delete" command using streams.
     */
    private String handleDelete(String arguments) {
        return handleTaskIndexOperation(arguments, "delete", taskIndex -> {
            Task taskToDelete = taskList.getTask(taskIndex); // get task before deletion
            taskList.deleteTask(taskIndex); // delete task
            return ui.getTaskDeletedMessage(taskToDelete, taskList.getSize());
        });
    }

    /**
     * Handles task index operations (mark, unmark, delete) using a common method with streams.
     * The lambda provided returns a string message describing the outcome.
     *
     * @param arguments The argument string representing the task number.
     * @param command   The command name (e.g., "mark", "unmark", "delete").
     * @param operation The lambda operation that performs the task index operation.
     * @return A message indicating success or an error message if input is invalid.
     */
    private String handleTaskIndexOperation(String arguments, String command,
                                            TaskIndexOperation operation) {
        try {
            int taskIndex = Integer.parseInt(arguments.trim()) - 1;
            if (isValidTaskIndex(taskIndex)) {
                return operation.perform(taskIndex);
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
     * Handles the "find" command using streams.
     */
    private String handleFind(String keyword) {
        ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
        return ui.getMatchingTasksMessage(matchingTasks);
    }

    /**
     * Handles the "help" command.
     */
    private String handleHelp() {
        return ui.getHelpMessage();
    }

    /**
     * Checks if the given task index is valid.
     */
    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < taskList.getSize();
    }

    /**
     * Processes the user input and returns a response.
     *
     * @param input The user input string.
     * @return The response string.
     */
    public String getResponse(String input) {
        String[] parts = Parser.parseCommand(input);
        String command = parts[0];
        String arguments = (parts.length > 1) ? parts[1] : "";

        String response = switch (command.toLowerCase()) {
        case "bye" -> {
            Platform.exit();
            yield ui.getGoodbyeMessage();
        }
        case "list" -> IntStream.range(0, taskList.getSize())
                .mapToObj(i -> ui.getTaskListItem(i, taskList.getTask(i)))
                .collect(Collectors.joining("\n", ui.getTaskListMessage() + "\n", ""));
        case "mark" -> handleMark(arguments);
        case "unmark" -> handleUnmark(arguments);
        case "todo" -> handleTodo(arguments, input);
        case "deadline" -> handleDeadline(arguments, input);
        case "event" -> handleEvent(arguments, input);
        case "delete" -> handleDelete(arguments);
        case "find" -> handleFind(arguments);
        case "help" -> handleHelp();
        default -> ui.getInvalidCommandError(input);
        };

        // Save after any command that might modify the task list.
        storage.saveTasks(taskList);
        return response;
    }

    /**
     * Returns the welcome message.
     */
    public String getWelcome() {
        return """
                As a high performance robot, this is what I can do:
                ✔ Add tasks:
                   - todo <task description>
                   - deadline <task description> /by <yyyy-MM-dd HHmm>
                   - event <task description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
                ✔ View tasks: list
                ✔ Mark tasks as done: mark <task number>
                ✔ Unmark tasks: unmark <task number>
                ✔ Delete tasks: delete <task number>
                ✔ Find tasks: find <keyword>
                ✔ Help: help
                ✔ Exit: bye
                Now what do you need me to do? :)
                """;
    }

    /**
     * Functional interface for task index operations.
     * The operation returns a String message indicating the result.
     */
    @FunctionalInterface
    private interface TaskIndexOperation {
        String perform(int taskIndex);
    }
}
