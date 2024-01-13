import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class DataStorage {
    private static final ArrayList<User> users;
    private static final String DB_URL = "jdbc:sqlite:db.sqlite";

    static {
        users = loadUsers();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void saveUsers(ArrayList<User> userList) {
        String sql = "INSERT OR IGNORE INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (User user : userList) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving users: " + e.getMessage());
        }
    }

    public static void saveAccounts(ArrayList<UserAccount> accounts, User user) {


        String insertSql = String.format("INSERT OR REPLACE INTO %s (account_name, account_username, account_password, account_email, account_website)\n" +
                "VALUES (?, ?, ?, ?, ?);", user.getUsername());

        try (Connection connection = getConnection();
             Statement tableStatement = connection.createStatement()) {
            createTable(user);
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                byte[] key = Encryptor.make256Bit(user.getPassword().getBytes());
                for (UserAccount account : accounts) {
                    String accountName = Base64.getEncoder().encodeToString(Encryptor.encryptAES(account.getAccountName().getBytes(),key));
                    String username = Base64.getEncoder().encodeToString(Encryptor.encryptAES(account.getUsername().getBytes(),key));
                    String password = Base64.getEncoder().encodeToString(Encryptor.encryptAES(account.getPassword().getBytes(),key));
                    String email = Base64.getEncoder().encodeToString(Encryptor.encryptAES(account.getEmail().getBytes(),key));
                    String website = Base64.getEncoder().encodeToString(Encryptor.encryptAES(account.getWebsite().getBytes(),key));

                    insertStatement.setString(1,accountName);
                    insertStatement.setString(2,username);
                    insertStatement.setString(3,password);
                    insertStatement.setString(4,email);
                    insertStatement.setString(5,website);

                    insertStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while saving accounts: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadUsers() {
        String sql = "SELECT username, password FROM users";
        ArrayList<User> loadedUsers = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                loadedUsers.add(new User(username, password));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while loading users: " + e.getMessage());
        }

        return loadedUsers;
    }



    public static ArrayList<UserAccount> loadAccounts(User user) {
        createTable(user);
        String sql = String.format("SELECT account_name, account_username, account_password, account_email, account_website FROM %s", user.getUsername());
        ArrayList<UserAccount> loadedAccounts = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            byte[] key = Encryptor.make256Bit(user.getPassword().getBytes());

            while (resultSet.next()) {
                String accountName = resultSet.getString("account_name");
                String username = resultSet.getString("account_username");
                String password = resultSet.getString("account_password");
                String email = resultSet.getString("account_email");
                String website = resultSet.getString("account_website");

                String decryptedAccountName = new String(Objects.requireNonNull(Decryptor.decryptAES(Base64.getDecoder().decode(accountName), key)));
                String decryptedUsername = new String(Objects.requireNonNull(Decryptor.decryptAES(Base64.getDecoder().decode(username), key)));
                String decryptedPassword = new String(Objects.requireNonNull(Decryptor.decryptAES(Base64.getDecoder().decode(password), key)));
                String decryptedEmail = new String(Objects.requireNonNull(Decryptor.decryptAES(Base64.getDecoder().decode(email), key)));
                String decryptedWebsite = new String(Objects.requireNonNull(Decryptor.decryptAES(Base64.getDecoder().decode(website), key)));


                loadedAccounts.add(new UserAccount(decryptedAccountName, decryptedUsername, decryptedPassword, decryptedEmail, decryptedWebsite));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while loading accounts: " + e.getMessage());
        }

        return loadedAccounts;
    }

    public static void createTable(User user) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (\n" +
                "    account_name TEXT PRIMARY KEY,\n" +
                "    account_username TEXT,\n" +
                "    account_password TEXT,\n" +
                "    account_email TEXT,\n" +
                "    account_website TEXT\n" +
                ");", user.getUsername());

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Error while creating table: " + e.getMessage());
        }
    }

    public static void deleteFile(String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Error while deleting file: " + e.getMessage());
        }
    }


    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}