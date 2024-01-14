
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginController {

    private Stage stage;
    private Scene scene;

    public void switchToSignup(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/signupfx.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private Button login_button;
    @FXML
    private Button signup_button;
    @FXML
    private Label error_label;
    public void login_function(ActionEvent event) {
        String username = username_field.getText();
        String password = password_field.getText();
        if (username.length() < 3) {
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
        ArrayList<User> users= DataStorage.getUsers();
        for(User tmp: users){
            if(tmp.getUsername().equals(user.getUsername()) && tmp.getPassword().equals(user.getPassword())){
                try {
                    MainFX.logged_in_user = user;
                    Parent root = FXMLLoader.load(getClass().getResource("/main_scenefx.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        error_label.setText("Wrong username or password");
    }
}