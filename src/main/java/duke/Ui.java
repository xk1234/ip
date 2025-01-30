package duke;
class Ui {
    private final java.util.Scanner scanner;

    public Ui() {
        this.scanner = new java.util.Scanner(System.in);
    }

    public void showWelcomeMessage(String name) {
        System.out.println("Hello! I'm " + name + "\n");
    }

    public void showDataLoadMessage() {
        System.out.println("No existing data file found. Starting with an empty task list.");
    }

    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showTaskList() {
        System.out.println("Here are the tasks in your list:");
    }

    public void showTaskListItem(int index, Task task) {
        System.out.println((index + 1) + ". " + task);
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showInvalidCommandError(String command) {
        System.out.println("OOPS!!! I'm sorry, but I don't know what command: " + command + " means :-(");
    }

    public void showInvalidInputError(String input, String usage) {
        System.out.println("OOPS!!! Invalid input for command: " + input + ". Use: " + usage);
    }

    public void showEmptyDescriptionError(String input) {
        System.out.println("OOPS!!! Invalid description for command: " + input + ". Description cannot be empty.");
    }

    public void showInvalidDateError(String input, String usage) {
        System.out.println("OOPS!!! Invalid date/time format for command: " + input + ". Use: " + usage);
    }

    public String readCommand() {
        showLine(); // Line before prompt
        System.out.println("What can I do for you?");
        showLine(); // Line after prompt
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showMatchingTasks(java.util.ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}
