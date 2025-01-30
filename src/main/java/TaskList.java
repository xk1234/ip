import java.util.ArrayList;

class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void deleteTask(int index) {
        this.tasks.remove(index);
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public void markTaskDone(int index) {
        this.tasks.get(index).markDone();
    }

    public void markTaskUndone(int index) {
        this.tasks.get(index).markUndone();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }
}