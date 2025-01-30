package duke;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Storage {
    private static final String DATA_FILE_PATH = "./data/duke.txt";

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> list = new ArrayList<>();
        java.nio.file.Path filePath = java.nio.file.Paths.get(DATA_FILE_PATH);

        if (!java.nio.file.Files.exists(filePath)) {
            return list; // Return empty list, don't print message here
        }

        try {
            List<String> lines = java.nio.file.Files.readAllLines(filePath);
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
                        String byString = parts[3];
                        LocalDateTime by = Parser.parseDateTime(byString);
                        if (by != null) {
                            task = new Deadline(description, by);
                        }
                        break;
                    case "E":
                        String fromString = parts[3];
                        String toString = parts[4];
                        LocalDateTime from = Parser.parseDateTime(fromString);
                        LocalDateTime to = Parser.parseDateTime(toString);
                        if (from != null && to != null) {
                            task = new Event(description, from, to);
                        }
                        break;
                    default:
                        // No-op (unrecognized task type)
                }

                if (task != null) {
                    if (isDone == 1) {
                        task.markDone();
                    }
                    list.add(task);
                }
            }
            System.out.println("Tasks loaded from " + DATA_FILE_PATH);
        } catch (java.io.IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error loading tasks from file during startup: " + e.getMessage());
        }
        return list;
    }

    public void saveTasks(TaskList taskList) {
        java.nio.file.Path filePath = java.nio.file.Paths.get(DATA_FILE_PATH);
        java.nio.file.Path parentDir = filePath.getParent();

        try {
            if (parentDir != null && !java.nio.file.Files.exists(parentDir)) {
                java.nio.file.Files.createDirectories(parentDir);
            }

            try (java.io.FileWriter writer = new java.io.FileWriter(filePath.toFile())) {
                for (int i = 0; i < taskList.getSize(); i++) {
                    writer.write(taskList.getTask(i).toFileString() + System.lineSeparator());
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}
