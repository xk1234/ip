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
        System.out.println("What can I do for you?\n");
        while (true) {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else {
                System.out.println(input);
            }
        }
        System.out.println("Bye. Hope to see you again soon!\n");

    }
}
