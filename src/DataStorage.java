import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataStorage {
    private static ArrayList<User> users;

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
        return loadedUsers;
    }

    public static ArrayList<UserAccount> loadAccounts(User user) {
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
            e.printStackTrace();
        }
        return loadedUsers;
    }


    public static void createEmptyFile(String filePath) {
        try {
            new File(filePath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}