import java.util.Objects;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        try {
            if(Objects.equals(args[0], "--noaccount")) {
                app.runNoAccount();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            app.run();
        }
    }
}