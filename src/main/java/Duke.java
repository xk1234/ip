import java.time.LocalDateTime;

public class Duke {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        taskList = new TaskList(storage.loadTasks());
    }

    public void run() {
        ui.showWelcomeMessage("Duke");
        if (taskList.getSize() == 0) {
            ui.showDataLoadMessage();
        }
        boolean isExit = false;
        while (!isExit) {
            String commandLine = ui.readCommand(); // This now includes lines around "What can I do for you?"
            String[] parts = Parser.parseCommand(commandLine);
            String command = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (command.toLowerCase()) {
                case "bye":
                    isExit = true;
                    break;
                case "list":
                    ui.showTaskList();
                    for (int i = 0; i < taskList.getSize(); i++) { // Moved list item printing to Ui
                        ui.showTaskListItem(i, taskList.getTask(i));
                    }
                    break;
                case "mark":
                    handleMark(arguments);
                    break;
                case "unmark":
                    handleUnmark(arguments);
                    break;
                case "todo":
                    handleTodo(arguments, commandLine);
                    break;
                case "deadline":
                    handleDeadline(arguments, commandLine);
                    break;
                case "event":
                    handleEvent(arguments, commandLine);
                    break;
                case "delete":
                    handleDelete(arguments, commandLine);
                    break;
                default:
                    ui.showInvalidCommandError(commandLine);
                    break;
            }
            storage.saveTasks(taskList);
        }
        ui.showGoodbyeMessage();
    }

    private void handleMark(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                taskList.markTaskDone(taskIndex);
                ui.showTaskMarked(taskList.getTask(taskIndex));
            } else {
                ui.showInvalidInputError(arguments, "mark <task_number>");
            }
        } catch (NumberFormatException e) {
            ui.showInvalidInputError(arguments, "mark <task_number>");
        }
    }

    private void handleUnmark(String arguments) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                taskList.markTaskUndone(taskIndex);
                ui.showTaskUnmarked(taskList.getTask(taskIndex));
            } else {
                ui.showInvalidInputError(arguments, "unmark <task_number>");
            }
        } catch (NumberFormatException e) {
            ui.showInvalidInputError(arguments, "unmark <task_number>");
        }
    }

    private void handleTodo(String arguments, String commandLine) {
        String description = arguments.trim();
        if (description.isEmpty()) {
            ui.showEmptyDescriptionError(commandLine);
        } else {
            Task newTask = new ToDo(description);
            taskList.addTask(newTask);
            ui.showTaskAdded(newTask, taskList.getSize());
        }
    }

    private void handleDeadline(String arguments, String commandLine) {
        String[] parts = Parser.parseDeadlineArguments(arguments);
        if (parts.length < 2) {
            ui.showInvalidInputError(commandLine, "deadline <description> /by <yyyy-MM-dd HHmm>");
            return;
        }
        String description = parts[0].trim();
        String byString = parts[1].trim();
        LocalDateTime by = Parser.parseDateTime(byString);
        if (by == null) {
            ui.showInvalidDateError(commandLine, "deadline <description> /by <yyyy-MM-dd HHmm>");
            return;
        }
        Task newTask = new Deadline(description, by);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getSize());
    }

    private void handleEvent(String arguments, String commandLine) {
        String[] parts = Parser.parseEventArguments(arguments);
        if (parts.length < 3) {
            ui.showInvalidInputError(commandLine, "event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
            return;
        }
        String description = parts[0].trim();
        String fromString = parts[1].trim();
        String toString = parts[2].trim();
        LocalDateTime from = Parser.parseDateTime(fromString);
        LocalDateTime to = Parser.parseDateTime(toString);
        if (from == null || to == null) {
            ui.showInvalidDateError(commandLine, "event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
            return;
        }
        Task newTask = new Event(description, from, to);
        taskList.addTask(newTask);
        ui.showTaskAdded(newTask, taskList.getSize());
    }

    private void handleDelete(String arguments, String commandLine) {
        try {
            int taskIndex = Integer.parseInt(arguments) - 1;
            if (isValidTaskIndex(taskIndex)) {
                Task deletedTask = taskList.getTask(taskIndex);
                taskList.deleteTask(taskIndex);
                ui.showTaskDeleted(deletedTask, taskList.getSize());
            } else {
                ui.showInvalidInputError(arguments, "delete <task_number>");
            }
        } catch (NumberFormatException e) {
            ui.showInvalidInputError(arguments, "delete <task_number>");
        }
    }


    private boolean isValidTaskIndex(int index) {
        return index >= 0 && index < taskList.getSize();
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}