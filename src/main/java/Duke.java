import java.util.ArrayList;
import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        String name = "Duke";

        System.out.println("Hello! I'm " + name + "\n");
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Boolean> done = new ArrayList<>();

        while (true) {
            System.out.println("What can I do for you?\n");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    String status = done.get(i) ? "[X]" : "[ ]";
                    System.out.println((i + 1) + ". " + status + " " + list.get(i));
                }
            } else if (input.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (taskNumber >= 0 && taskNumber < list.size()) {
                        done.set(taskNumber, true);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  [X] " + list.get(taskNumber));
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Use: mark <task_number>");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (taskNumber >= 0 && taskNumber < list.size()) {
                        done.set(taskNumber, false);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  [ ] " + list.get(taskNumber));
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Use: unmark <task_number>");
                }
            } else {
                list.add(input);
                done.add(false);
            }
            System.out.println();
        }

        System.out.println("Bye. Hope to see you again soon!");

    }
}
