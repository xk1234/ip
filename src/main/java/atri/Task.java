package atri;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an abstract task.
 * This is the base class for different types of tasks in the Duke application.
 */
abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     * Sets the isDone status to true.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     * Sets the isDone status to false.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the type of the task.
     * This is an abstract method to be implemented by subclasses.
     *
     * @return A string representing the task type.
     */
    public abstract String getTaskType();

    /**
     * Returns a string representation of the task for display.
     * Includes the task type, status, and description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        String status = isDone ? "[X]" : "[ ]";
        return "[" + getTaskType() + "]" + status + " " + description;
    }

    /**
     * Returns a string representation of the task for file storage.
     * Formatted as "TaskType | isDone (1 or 0) | description".
     *
     * @return A formatted string for saving the task to a file.
     */
    public String toFileString() {
        return String.format("%s | %d | %s", getTaskType(), isDone ? 1 : 0, description);
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}

/**
 * Represents a To Do task.
 */
class ToDo extends Task {
    /**
     * Constructs a To Do task.
     *
     * @param description The description of the To Do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the task type for To Do tasks.
     * For To Do tasks, the type is "T".
     *
     * @return "T" indicating To Do task type.
     */
    @Override
    public String getTaskType() {
        return "T";
    }
}

/**
 * Represents a Deadline task.
 * A Deadline task has a specific deadline by which it needs to be completed.
 */
class Deadline extends Task {
    /** Deadline for the task. */
    private LocalDateTime by;

    /**
     * Constructs a Deadline task.
     *
     * @param description The description of the Deadline task.
     * @param by          The LocalDateTime representing the deadline.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the task type for Deadline tasks.
     * For Deadline tasks, the type is "D".
     *
     * @return "D" indicating Deadline task type.
     */
    @Override
    public String getTaskType() {
        return "D";
    }

    /**
     * Returns a string representation of the Deadline task for display.
     * Includes the task details and the deadline.
     *
     * @return A formatted string representing the Deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        String formattedDate = by.format(formatter);
        return super.toString() + " (by: " + formattedDate + ")";
    }

    /**
     * Returns a string representation of the Deadline task for file storage.
     * Includes the base file string and the deadline formatted for file storage.
     *
     * @return A formatted string for saving the Deadline task to a file.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return super.toFileString() + " | " + by.format(formatter);
    }

    public LocalDateTime getBy() {
        return by;
    }

    public void setBy(LocalDateTime by) {
        this.by = by;
    }
}

/**
 * Represents an Event task.
 * An Event task has a start and end time.
 */
class Event extends Task {
    /** Start time of the event. */
    private LocalDateTime from;
    /** End time of the event. */
    private LocalDateTime to;

    /**
     * Constructs an Event task.
     *
     * @param description The description of the Event task.
     * @param from        The LocalDateTime representing the start time of the event.
     * @param to          The LocalDateTime representing the end time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the task type for Event tasks.
     * For Event tasks, the type is "E".
     *
     * @return "E" indicating Event task type.
     */
    @Override
    public String getTaskType() {
        return "E";
    }

    /**
     * Returns a string representation of the Event task for display.
     * Includes the task details and the event's time range.
     *
     * @return A formatted string representing the Event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        String formattedFrom = from.format(formatter);
        String formattedTo = to.format(formatter);
        return super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    /**
     * Returns a string representation of the Event task for file storage.
     * Includes the base file string and the start and end times formatted for file storage.
     *
     * @return A formatted string for saving the Event task to a file.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return super.toFileString() + " | " + from.format(formatter) + " | " + to.format(formatter);
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
