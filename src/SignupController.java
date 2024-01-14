import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SignupController {
    private Stage stage;
    private Scene scene;

    public void switchToLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/loginfx.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private TextField password_confirmation_field;
    @FXML
    private Label error_label;

    public void signupButtonClicked(ActionEvent event) {
        String username = username_field.getText();
        String password = password_field.getText();
        String password_confirmation = password_confirmation_field.getText();


        if(!password.equals(password_confirmation)){
            error_label.setText("Passwords do not match");
            return;
        }else if (username.length() < 3) {
            error_label.setText("Username must be at least 3 characters long");
            return;
        } else if (!InputDevice.SQLSafe(username)) {
            error_label.setText("Username must not contain SQL injection");
            return;
        } else if (password.length() < 8) {
            error_label.setText("Password must be at least 8 characters long");
            return;
        }
        String hashed_password = Hasher.hashWithSHA512(password.getBytes());
        password = null;
        User user = new User(username, hashed_password);
        try {
            // Sleep for 3 seconds (3000 milliseconds)
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Handle the exception if the sleep is interrupted
            e.printStackTrace();
        }
        ArrayList<User> users= DataStorage.getUsers();
        for(User tmp: users) {
            if (tmp.getUsername().equals(user.getUsername())) {
                error_label.setText("You already have an account");
                return;
            }
        }

        try {
            MainFX.logged_in_user = user;
            DataStorage.addUser(user);
            Parent root = FXMLLoader.load(getClass().getResource("/main_scenefx.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            OutputDevice.display("Error loading main scene");
        }
    }
}
