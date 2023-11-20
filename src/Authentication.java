import java.util.ArrayList;

public class Authentication {
    static String init(){
        DataStorage.loadUsers();
        OutputDevice.display("Do you have an account?(y/n)");
        String input = InputDevice.keyboardTextInput();
        while (!input.equals("y") && !input.equals("n")) {
            OutputDevice.display("Please enter y or n");
            input = InputDevice.keyboardTextInput();
        }
        return input;
    }
    static User Login() {
        OutputDevice.display("Please enter your username:");
        String username = InputDevice.keyboardTextInput();
        OutputDevice.display("Please enter your password:");
        String password = Hasher.hashWithSHA256(InputDevice.keyboardByteInput());
        User user = new User(username, password);
        ArrayList<User> users= DataStorage.getUsers();
        for(User tmp: users){
            if(tmp.getUsername().equals(user.getUsername()) && tmp.getPassword().equals(user.getPassword())){
                OutputDevice.display("Welcome " + user.getUsername());
                return user;
            }
        }
        return null;

    }
    static User Register() {
        OutputDevice.display("Let's create an account for you!");
        OutputDevice.display("Please enter your username:");
        String username = InputDevice.keyboardTextInput();
        OutputDevice.display("Please enter your password:");
        String password = Hasher.hashWithSHA256(InputDevice.keyboardByteInput());
        String fileName = String.format("%s.xml", username);
        User user = new User(username, password);
        ArrayList<User> users= DataStorage.getUsers();
        for(User tmp: users) {
            if (tmp.getUsername().equals(user.getUsername())) {
                OutputDevice.display("You already have an account");
                return null;
            }
        }
        OutputDevice.display("Welcome " + user.getUsername());
        OutputDevice.display("You can do a lot of things with this app:");
        OutputDevice.display("Store your passwords");
        OutputDevice.display("Hash text and files with different algorithms");
        OutputDevice.display("Encrypt text and files with different algorithms");
        DataStorage.addUser(user);
        DataStorage.createEmptyFile(fileName);
        return user;
    }
}
