package atri;

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
        return "Great! I've marked this task as complete:\n  " + task;
    }

    /**
     * Returns a message for a task that has been marked as not done.
     *
     * @param task The task that was unmarked.
     * @return A confirmation message.
     */
    public String getTaskUnmarkedMessage(Task task) {
        return "Okay, I've reset this task to not done:\n  " + task;
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
                + "\nNow there are " + remainingTasks + (remainingTasks == 1 ? " task" : " tasks") + " remaining.";
    }

    /**
     * Returns a message for a task that has been added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks now.
     * @return A confirmation message.
     */
    public String getTaskAddedMessage(Task task, int taskCount) {
        return "Task added! I've stored it in our list:\n  " + task
                + "\nWe're now tracking " + taskCount + (taskCount == 1 ? " task" : " tasks") + ".";
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
        return "Oops! The description for '" + commandLine + "' can't be empty. Please provide more details.";
    }

    /**
     * Returns an error message for invalid input.
     *
     * @param arguments The invalid argument string.
     * @param usage The correct usage of the command.
     * @return An error message.
     */
    public String getInvalidInputError(String arguments, String usage) {
        return "Hmm, I didn't understand '" + arguments + "'.\nPlease use the following format: " + usage;
    }

    /**
     * Returns an error message for invalid date input.
     *
     * @param commandLine The command that was entered.
     * @param usage The correct usage format.
     * @return An error message.
     */
    public String getInvalidDateError(String commandLine, String usage) {
        return "The date in '" + commandLine + "' doesn't seem right.\nTry this format: " + usage;
    }

    /**
     * Returns an error message for an unrecognized command.
     *
     * @param command The unrecognized command.
     * @return An error message.
     */
    public String getInvalidCommandError(String command) {
        return "I'm sorry, I don't recognize the command: '" + command + "'. Could you try something else?";
    }

    /**
     * Returns the goodbye message.
     *
     * @return A goodbye message.
     */
    public String getGoodbyeMessage() {
        return "Goodbye! Looking forward to our next productive session.";
    }

    /**
     * Returns the help message which includes instructions on how to use Atri.
     *
     * @return The help message.
     */
    public String getHelpMessage() {
        return """
               Hello! I'm Atri, your high-performance assistant.
               I'm here to help you manage your tasks with efficiency and care.
               Here's how we can work together:
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
                 • find <keyword> : Locate tasks by keyword.
               Need a reminder? Just type 'help' anytime.
               Let's get started!
               """;
    }

    /**
     * Returns a message listing all matching tasks.
     * @param tasks an ArrayList of matching tasks.
     * @return a formatted string of matching tasks.
     */
    public String getMatchingTasksMessage(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("I found some tasks that match your query:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append((i + 1)).append(". ").append(tasks.get(i));
        }
        sb.append("\nHope this helps!");
        return sb.toString();
    }
}
