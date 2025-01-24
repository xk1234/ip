import java.util.ArrayList;
import java.util.Scanner;
public class Duke {
    static abstract class Task {
        String description;
        boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public void markDone() {
            this.isDone = true;
        }

        public void markUndone() {
            this.isDone = false;
        }

        public abstract String getTaskType();

        @Override
        public String toString() {
            String status = isDone ? "[X]" : "[ ]";
            return "[" + getTaskType() + "]" + status + " " + description;
        }
    }

    static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String getTaskType() {
            return "T";
        }
    }

    static class Deadline extends Task {
        String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String getTaskType() {
            return "D";
        }

        @Override
        public String toString() {
            return super.toString() + " (by: " + by + ")";
        }
    }

    static class Event extends Task {
        String from;
        String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String getTaskType() {
            return "E";
        }

        @Override
        public String toString() {
            return super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

    public static void main(String[] args) {
        String logo = """
                 ____        _       \s
                |  _ \\ _   _| | _____\s
                | | | | | | | |/ / _ \\
                | |_| | |_| |   <  __/
                |____/ \\__,_|_|\\_\\___|
                """;
        System.out.println("Hello from\n" + logo);
        String name = "Duke";

        System.out.println("Hello! I'm " + name + "\n");
        ArrayList<Task> list = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("What can I do for you?");
        while (scan.hasNextLine()) {
            String input = scan.nextLine();

            System.out.println("____________________________________________________________");

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
            } else if (input.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (taskNumber >= 0 && taskNumber < list.size()) {
                        list.get(taskNumber).markDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + list.get(taskNumber));
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid input. Use: mark <task_number>");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (taskNumber >= 0 && taskNumber < list.size()) {
                        list.get(taskNumber).markUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + list.get(taskNumber));
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid input. Use: unmark <task_number>");
                }
            } else if (input.startsWith("todo")) {
                try {
                    String description = input.substring(5).trim();
                    list.add(new ToDo(description));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [T][ ] " + description);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid description of a todo.");
                }
            } else if (input.startsWith("deadline")) {
                try {
                    String[] parts = input.substring(9).split(" /by ");
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    list.add(new Deadline(description, by));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [D][ ] " + description + " (by: " + by + ")");
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid format. Use: deadline <description> /by <time>");
                }
            } else if (input.startsWith("event")) {
                try {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    list.add(new Event(description, from, to));
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  [E][ ] " + description + " (from: " + from + " to: " + to + ")");
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid format. Use: event <description> /from <time> /to <time>");
                }
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            System.out.println("____________________________________________________________");
            System.out.println("What can I do for you?");
        }

        System.out.println("Bye. Hope to see you again soon!");

    }
}
