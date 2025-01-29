import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {

    private static final String DATA_FILE_PATH = "./data/duke.txt";

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

        public String toFileString() {
            return String.format("%s | %d | %s", getTaskType(), isDone ? 1 : 0, description);
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

        @Override
        public String toFileString() {
            return super.toFileString() + " | " + by;
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

        @Override
        public String toFileString() {
            return super.toFileString() + " | " + from + " | " + to;
        }
    }

    public static void main(String[] args) {
        String name = "Duke";

        System.out.println("Hello! I'm " + name + "\n");
        ArrayList<Task> list = new ArrayList<>();

        // Load tasks from file
        loadTasksFromFile(list);

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
                        saveTasksToFile(list); // Save changes
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
                        saveTasksToFile(list); // Save changes
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid input. Use: unmark <task_number>");
                }
            } else if (input.startsWith("todo")) {
                try {
                    String description = input.substring(5).trim();
                    Task newTask = new ToDo(description);
                    list.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    saveTasksToFile(list); // Save changes
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid description of a todo.");
                }
            } else if (input.startsWith("deadline")) {
                try {
                    String[] parts = input.substring(9).split(" /by ");
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    Task newTask = new Deadline(description, by);
                    list.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    saveTasksToFile(list); // Save changes
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid format. Use: deadline <description> /by <time>");
                }
            } else if (input.startsWith("event")) {
                try {
                    String[] parts = input.substring(6).split(" /from | /to ");
                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    Task newTask = new Event(description, from, to);
                    list.add(newTask);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    saveTasksToFile(list); // Save changes
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid format. Use: event <description> /from <time> /to <time>");
                }
            } else if (input.startsWith("delete")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (taskNumber >= 0 && taskNumber < list.size()) {
                        Task removedTask = list.remove(taskNumber);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        saveTasksToFile(list); // Save changes
                    } else {
                        System.out.println("Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println("OOPS!!! Invalid input. Use: delete <task_number>");
                }
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            System.out.println("____________________________________________________________");
            System.out.println("What can I do for you?");
        }

        System.out.println("Bye. Hope to see you again soon!");

    }

    private static void loadTasksFromFile(ArrayList<Task> list) {
        Path filePath = Paths.get(DATA_FILE_PATH);
        if (!Files.exists(filePath)) {
            System.out.println("No existing data file found. Starting with an empty task list.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                String taskType = parts[0];
                int isDone = Integer.parseInt(parts[1]);
                String description = parts[2];

                Task task = null;
                switch (taskType) {
                    case "T":
                        task = new ToDo(description);
                        break;
                    case "D":
                        String by = parts[3];
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
                        task = new Event(description, from, to);
                        break;
                }

                if (task != null) {
                    if (isDone == 1) {
                        task.markDone();
                    }
                    list.add(task);
                }

            }
            System.out.println("Tasks loaded from " + DATA_FILE_PATH);
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }


    private static void saveTasksToFile(ArrayList<Task> list) {
        Path filePath = Paths.get(DATA_FILE_PATH);
        Path parentDir = filePath.getParent();

        try {
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                for (Task task : list) {
                    writer.write(task.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}