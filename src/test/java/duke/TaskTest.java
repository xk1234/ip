package duke;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void markDone_taskIsMarkedDone() {
        ToDo todo = new ToDo("Buy groceries");
        assertFalse(todo.isDone);
        todo.markDone();
        assertTrue(todo.isDone);
    }

    @Test
    void markUndone_taskIsMarkedUndone() {
        ToDo todo = new ToDo("Read a book");
        todo.markDone();
        assertTrue(todo.isDone);
        todo.markUndone();
        assertFalse(todo.isDone);
    }

    @Test
    void getTaskType_todoTask_returnsT() {
        ToDo todo = new ToDo("Learn Java");
        assertEquals("T", todo.getTaskType());
    }

    @Test
    void getTaskType_deadlineTask_returnsD() {
        LocalDateTime by = LocalDateTime.of(2024, 12, 31, 23, 59);
        Deadline deadline = new Deadline("Submit assignment", by);
        assertEquals("D", deadline.getTaskType());
    }

    @Test
    void getTaskType_eventTask_returnsE() {
        LocalDateTime from = LocalDateTime.of(2024, 12, 25, 10, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 25, 12, 0);
        Event event = new Event("Christmas Lunch", from, to);
        assertEquals("E", event.getTaskType());
    }

    @Test
    void toString_todoTaskNotDone_returnsCorrectString() {
        ToDo todo = new ToDo("Walk the dog");
        assertEquals("[T][ ] Walk the dog", todo.toString());
    }

    @Test
    void toString_todoTaskDone_returnsCorrectString() {
        ToDo todo = new ToDo("Pay bills");
        todo.markDone();
        assertEquals("[T][X] Pay bills", todo.toString());
    }

    @Test
    void toString_deadlineTask_returnsCorrectStringWithDate() {
        LocalDateTime by = LocalDateTime.of(2024, 8, 15, 18, 0);
        Deadline deadline = new Deadline("File taxes", by);
        assertEquals("[D][ ] File taxes (by: Aug 15 2024 1800)", deadline.toString());
    }

    @Test
    void toString_eventTask_returnsCorrectStringWithDateRange() {
        LocalDateTime from = LocalDateTime.of(2024, 9, 10, 9, 0);
        LocalDateTime to = LocalDateTime.of(2024, 9, 10, 17, 0);
        Event event = new Event("Team meeting", from, to);
        assertEquals("[E][ ] Team meeting (from: Sept 10 2024 0900 to: Sept 10 2024 1700)", event.toString()); // Changed "Sep" to "Sept"
    }

    @Test
    void toFileString_todoTaskNotDone_returnsCorrectFileString() {
        ToDo todo = new ToDo("Grocery shopping");
        assertEquals("T | 0 | Grocery shopping", todo.toFileString());
    }

    @Test
    void toFileString_todoTaskDone_returnsCorrectFileString() {
        ToDo todo = new ToDo("Book appointment");
        todo.markDone();
        assertEquals("T | 1 | Book appointment", todo.toFileString());
    }

    @Test
    void toFileString_deadlineTask_returnsCorrectFileStringWithDate() {
        LocalDateTime by = LocalDateTime.of(2025, 1, 1, 0, 0);
        Deadline deadline = new Deadline("New Year resolution", by);
        assertEquals("D | 0 | New Year resolution | 2025-01-01 0000", deadline.toFileString());
    }

    @Test
    void toFileString_eventTask_returnsCorrectFileStringWithDateRange() {
        LocalDateTime from = LocalDateTime.of(2024, 11, 28, 12, 30);
        LocalDateTime to = LocalDateTime.of(2024, 11, 28, 14, 0);
        Event event = new Event("Thanksgiving dinner", from, to);
        assertEquals("E | 0 | Thanksgiving dinner | 2024-11-28 1230 | 2024-11-28 1400", event.toFileString());
    }
}