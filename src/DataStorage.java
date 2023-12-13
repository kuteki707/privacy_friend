import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataStorage {
    private static final ArrayList<User> users;

    static {
        users = loadUsers();
    }

    public static void saveUsers(ArrayList<User> userList) {
        try (PrintWriter writer = new PrintWriter("db.xml")) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<users>");

            for (User user : userList) {
                writer.println("    <user>");
                writer.println("        <username>" + user.getUsername() + "</username>");
                writer.println("        <password>" + user.getPassword() + "</password>");
                writer.println("    </user>");
            }

            writer.println("</users>");
        } catch (IOException e) {
            throw new FileWriteException("Error while saving users");
        }
    }

    public static void saveAccounts(ArrayList<UserAccount> accounts, User user){
        String fileName = String.format("%s.xml", user.getUsername());
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<accounts>");

            for (UserAccount account : accounts) {
                writer.println("    <account>");
                writer.println("        <accountName>" + account.getAccountName() + "</accountName>");
                writer.println("        <username>" + account.getUsername() + "</username>");
                writer.println("        <password>" + account.getPassword() + "</password>");
                writer.println("        <email>" + account.getEmail() + "</email>");
                writer.println("        <website>" + account.getWebsite() + "</website>");
                writer.println("    </account>");
            }

            writer.println("</accounts>");
        } catch (IOException e) {
            throw new FileWriteException("Error while saving accounts");
        }
        String encryptedFileName = String.format("%s.enc", user.getUsername());
        byte[] file = InputDevice.fileByteInput(fileName);
        byte[] key = Encryptor.make256Bit(user.getPassword().getBytes());
        byte[] encryptedFile = Encryptor.encryptAES(file, key);
        OutputDevice.createFileFromBytes(encryptedFile, encryptedFileName);
        deleteFile(fileName);
    }

    public static ArrayList<User> loadUsers() {
        String filePath = "db.xml";
        ArrayList<User> loadedUsers = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createEmptyFile(filePath);
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip the XML declaration line
            reader.readLine();

            // Skip the opening <users> tag
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null && !line.equals("</users>")) {
                if (line.trim().equals("<user>")) {
                    String username = reader.readLine().trim().replace("<username>", "").replace("</username>", "");
                    String password = reader.readLine().trim().replace("<password>", "").replace("</password>", "");

                    loadedUsers.add(new User(username, password));

                    // Skip the closing </user> tag
                    reader.readLine();
                }
            }
        } catch (IOException e) {
            throw new FileReadException("Error while loading users");
        }
        return loadedUsers;
    }

    public static ArrayList<UserAccount> loadAccounts(User user) {
        try {
            String encryptedFilePath = String.format("%s.enc", user.getUsername());
            byte[] encrypted_file = InputDevice.fileByteInput(encryptedFilePath);
            byte[] key = Encryptor.make256Bit(user.getPassword().getBytes());
            byte[] decrypted_file = Decryptor.decryptAESNoOutput(encrypted_file, key);
            OutputDevice.createFileFromBytes(decrypted_file, user.getUsername() + ".xml");
        } catch (Exception e) {
            OutputDevice.display("");
        }

        String filePath = String.format("%s.xml", user.getUsername());
        ArrayList<UserAccount> loadedUsers = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            createEmptyFile(filePath);
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip the XML declaration line
            reader.readLine();

            // Skip the opening <accounts> tag
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null && !line.equals("</account>")) {
                if (line.trim().equals("<account>")) {
                    String accountName = reader.readLine().trim().replace("<accountName>", "").replace("</accountName>", "");
                    String username = reader.readLine().trim().replace("<username>", "").replace("</username>", "");
                    String password = reader.readLine().trim().replace("<password>", "").replace("</password>", "");
                    String email = reader.readLine().trim().replace("<email>", "").replace("</email>", "");
                    String website = reader.readLine().trim().replace("<website>", "").replace("</website>", "");

                    loadedUsers.add(new UserAccount(accountName,username, password, email, website));

                    // Skip the closing </account> tag
                    reader.readLine();
                }
            }
        } catch (IOException e) {
            throw new FileReadException("Error while loading accounts");
        }
        return loadedUsers;
    }


    public static void createEmptyFile(String filePath) {
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
            throw new FileWriteException("Error creating empty file "+ e.getMessage());
        }
    }
    static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
        }catch (IOException e) {
            throw new FileWriteException("Error deleting file " + e.getMessage());
        }

    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}