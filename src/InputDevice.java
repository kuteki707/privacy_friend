import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public interface InputDevice {
    static String keyboardTextInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static boolean SQLSafe(String input){
        return input.matches("[a-zA-Z0-9]+");
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
