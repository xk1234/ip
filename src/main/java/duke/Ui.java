package duke;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Command Handler
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the welcome message.
     * @param name the name of the chatbot.
     * @return a welcome string.
     */
    public String getWelcomeMessage(String name) {
        return "Hello! I'm " + name + "\n";
    }

    /**
     * Returns a message to indicate that no previous data was found.
     * @return a data load message.
     */
    public String getDataLoadMessage() {
        return "No existing data file found. Starting with an empty task list.";
    }

    /**
     * Returns the goodbye message.
     * @return a goodbye string.
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a header for displaying the task list.
     * @return a task list header.
     */
    public String getTaskListMessage() {
        return "Here are the tasks in your list:";
    }

    /**
     * Returns a string representation of a single task list item.
     * @param index the index of the task.
     * @param task the task to be displayed.
     * @return a formatted string for the task.
     */
    public String getTaskListItem(int index, Task task) {
        return (index + 1) + ". " + task;
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     * @param task the task that was marked.
     * @return a confirmation string.
     */
    public String getTaskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a message indicating that a task has been unmarked.
     * @param task the task that was unmarked.
     * @return a confirmation string.
     */
    public String getTaskUnmarkedMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns a message indicating that a task has been added.
     * @param task the task that was added.
     * @param taskCount the current number of tasks.
     * @return an added task message.
     */
    public String getTaskAddedMessage(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have "
                + taskCount + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a task has been deleted.
     * @param task the task that was removed.
     * @param taskCount the current number of tasks.
     * @return a deleted task message.
     */
    public String getTaskDeletedMessage(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have "
                + taskCount + " tasks in the list.";
    }

    /**
     * Returns an error message for an invalid command.
     * @param command the command input that was not recognized.
     * @return an error string.
     */
    public String getInvalidCommandError(String command) {
        return "Hmm... I don’t recognize the command: \"" + command + "\". "
                + "Did you mean something else? Let me know, and I'll try my best to understand!";
    }

    /**
     * Returns an error message for invalid input.
     * @param input the input that was invalid.
     * @param usage a string describing the proper usage.
     * @return an error string.
     */
    public String getInvalidInputError(String input, String usage) {
        return "Oh no! That input doesn't seem right: \"" + input + "\". "
                + "Maybe try using it like this: " + usage + "? I believe in you!";
    }

    /**
     * Returns an error message when a command's description is empty.
     * @param input the command with the empty description.
     * @return an error string.
     */
    public String getEmptyDescriptionError(String input) {
        return "Ah! You forgot to include a description for \"" + input + "\". "
                + "Can you tell me a little more? I'd love to help!";
    }

    /**
     * Returns an error message for an invalid date/time format.
     * @param input the command that contains the date/time.
     * @param usage a string describing the proper format.
     * @return an error string.
     */
    public String getInvalidDateError(String input, String usage) {
        return "Hmm... I think there's something off with the date/time format in \"" + input + "\". "
                + "Maybe try it like this: " + usage + "? That should work!";
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
