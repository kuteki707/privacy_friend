import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public interface InputDevice {
    static String keyboardTextInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static byte[] keyboardByteInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().getBytes();
    }
    static byte[] fileByteInput(String filePath){
        try {
            // Read all bytes from the file into a byte array
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
