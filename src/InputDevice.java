import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public interface InputDevice {
    static String keyboardTextInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static String SQLSafeInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.matches("^[a-zA-Z0-9_]+$")) {
            OutputDevice.display("Invalid input. Please enter alphanumeric characters and underscores only.");
            input = scanner.nextLine();
        }

        return input;
    }

    static byte[] keyboardByteInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().getBytes();
    }
    static byte[] fileByteInput(String filePath){
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new FileWriteException("Error while reading file");
        }
    }
    static int keyboardIntInput(){
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextInt();
        }catch (Exception e){
            OutputDevice.display("Invalid input");
            return keyboardIntInput();
        }
    }
}
