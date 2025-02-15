package duke;

import java.util.ArrayList;

/**
 * UI class
 */
public class Ui {

    /**
     * Returns a message for a task that has been marked as done.
     *
     * @param task The task that was marked.
     * @return A confirmation message.
     */
    public String getTaskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a message for a task that has been marked as not done.
     *
     * @param task The task that was unmarked.
     * @return A confirmation message.
     */
    public String getTaskUnmarkedMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns a message for a task that has been deleted.
     *
     * @param task The task that was deleted.
     * @param remainingTasks The number of tasks remaining.
     * @return A confirmation message.
     */
    public String getTaskDeletedMessage(Task task, int remainingTasks) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + remainingTasks + (remainingTasks == 1 ? " task" : " tasks") + " in the list.";
    }

    /**
     * Returns a message for a task that has been added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks now.
     * @return A confirmation message.
     */
    public String getTaskAddedMessage(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + (taskCount == 1 ? " task" : " tasks") + " in the list.";
    }

    /**
     * Returns a string representation of a task list item.
     *
     * @param index The index of the task.
     * @param task The task to display.
     * @return A formatted list item.
     */
    public String getTaskListItem(int index, Task task) {
        return (index + 1) + ". " + task;
    }

    /**
     * Returns the header message for the task list.
     *
     * @return A header string.
     */
    public String getTaskListMessage() {
        return "Here are the tasks in your list:";
    }

    /**
     * Returns an error message when a command's description is empty.
     *
     * @param commandLine The command input.
     * @return An error message.
     */
    public String getEmptyDescriptionError(String commandLine) {
        return "The description of '" + commandLine + "' cannot be empty.";
    }

    /**
     * Returns an error message for invalid input.
     *
     * @param arguments The invalid argument string.
     * @param usage The correct usage of the command.
     * @return An error message.
     */
    public String getInvalidInputError(String arguments, String usage) {
        return "Invalid input: " + arguments + "\nUsage: " + usage;
    }

    /**
     * Returns an error message for invalid date input.
     *
     * @param commandLine The command that was entered.
     * @param usage The correct usage format.
     * @return An error message.
     */
    public String getInvalidDateError(String commandLine, String usage) {
        return "Invalid date format in '" + commandLine + "'.\nUsage: " + usage;
    }

    /**
     * Returns an error message for an unrecognized command.
     *
     * @param command The unrecognized command.
     * @return An error message.
     */
    public String getInvalidCommandError(String command) {
        return "I'm sorry, but I don't know what that means: " + command;
    }

    /**
     * Returns the goodbye message.
     *
     * @return A goodbye message.
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }
    /**
     * Returns the welcome message which includes instructions on how to use Duke.
     *
     * @return The welcome message.
     */
    public String getHelpMessage() {
        return """
               Hello! I'm Atri, your friendly task manager.
               I'm here to help you track your to-dos, deadlines, and events in a natural, conversational way.
               How to use me:
               - Add a task:
                 • todo <task description>
                 • deadline <task description> /by <yyyy-MM-dd HHmm>
                 • event <task description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
               - Manage tasks:
                 • list       : View all tasks.
                 • mark <n>   : Mark task number n as done.
                 • unmark <n> : Mark task number n as not done.
                 • delete <n> : Delete task number n.
               - Search for tasks:
                 • find <keyword> : Find tasks with a specific keyword.
               - Need a reminder? Just type 'help' to see all commands.
               I'm designed to be as warm and helpful as a friend. Let's get started!
               """;
    }

    /**
     * Returns a message listing all matching tasks.
     * @param tasks an ArrayList of matching tasks.
     * @return a formatted string of matching tasks.
     */
    public String getMatchingTasksMessage(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ooh! I found some tasks that match what you're looking for:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append((i + 1)).append(". ").append(tasks.get(i));
        }
        sb.append("\nHope that helps!");
        return sb.toString();
    }
}
