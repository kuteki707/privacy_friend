import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface OutputDevice {
    static void display(String output){
        System.out.println(output);
    }
    public static void writeBytesToFile(byte[] data, String filePath) throws Exception {
        Path path = Path.of(filePath);
        Files.write(path, data, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
}
