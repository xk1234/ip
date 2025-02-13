package duke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loading and saving tasks to a persistent data file.
 */
class Storage {
    private static final String DATA_FILE_PATH = "./data/duke.txt";

    /**
     * Loads tasks from the data file.  Returns an empty list if the file does
     * not exist or if loading fails. Uses streams for file reading and parsing.
     *
     * @return An ArrayList of Task objects loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        Path filePath = Paths.get(DATA_FILE_PATH);
        if (!Files.exists(filePath)) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }

        try (Stream<String> lines = Files.lines(filePath)) {
            List<Task> tasks = lines.map(this::parseTaskLine)
                    .filter(Objects::nonNull)
                    .toList();
            System.out.println("Tasks loaded from " + DATA_FILE_PATH);
            return new ArrayList<>(tasks); // Convert to ArrayList
        } catch (IOException e) {
            System.out.println("Error loading tasks from file during startup: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error
        }
    }



    /**
     * Parses a single line from the data file into a Task object.
     * Handles different task types (T, D, E) and their respective formats.
     *
     * @param line The line from the file to parse.
     * @return The parsed Task object, or null if parsing fails.
     */
    private Task parseTaskLine(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String taskType = parts[0];
            int isDone = Integer.parseInt(parts[1]);
            String description = parts[2];

            Task task;
            switch (taskType) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                LocalDateTime by = Parser.parseDateTime(parts[3]);
                if (by == null) {
                    return null;
                }
                task = new Deadline(description, by);
                break;
            case "E":
                LocalDateTime from = Parser.parseDateTime(parts[3]);
                LocalDateTime to = Parser.parseDateTime(parts[4]);
                if (from == null || to == null) {
                    return null;
                }
                task = new Event(description, from, to);
                break;
            default:
                return null; // Invalid task type
            }

            if (isDone == 1) {
                task.markDone();
            }
            return task;

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing task line: " + line + " - " + e.getMessage());
            return null; // Return null on parsing error
        }
    }


    /**
     * Saves all tasks in the provided TaskList to the persistent data file.
     * Uses streams for writing the task data to the file.
     *
     * @param taskList TaskList containing the tasks to be saved.
     */
    public void saveTasks(TaskList taskList) {
        Path filePath = Paths.get(DATA_FILE_PATH);
        Path parentDir = filePath.getParent();

        try {
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            // Use Files.write to write the lines directly from the stream
            Files.write(filePath, taskList.getTasks().stream()
                    .map(Task::toFileString)
                    .collect(Collectors.toList()));

        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}