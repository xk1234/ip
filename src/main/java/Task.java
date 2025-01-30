import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract class Task {
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

class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}

class Deadline extends Task {
    LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        String formattedDate = by.format(formatter);
        return super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return super.toFileString() + " | " + by.format(formatter);
    }
}

class Event extends Task {
    LocalDateTime from;
    LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        String formattedFrom = from.format(formatter);
        String formattedTo = to.format(formatter);
        return super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }


    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return super.toFileString() + " | " + from.format(formatter) + " | " + to.format(formatter);
    }
}