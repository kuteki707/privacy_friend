import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class MainSceneController {
    @FXML
    private Label test_label;
    @FXML
    private ListView<String> list_view;
    @FXML
    private void initialize() {
        try {
            updateLabelText(test_label, "Hello " + MainFX.logged_in_user.getUsername() + "!");
        }catch (NullPointerException e) {
            OutputDevice.display("Error loading main scene");
        }
        ArrayList<UserAccount> accounts = MainFX.logged_in_user.getAccounts();
        list_view.getItems().add("********** Accounts **********");
        for(UserAccount account: accounts){
            list_view.getItems().add("Name: " +account.getAccountName());
            if(!Objects.equals(account.getUsername(), "")){
                list_view.getItems().add("Username: " + account.getUsername());
            }
            list_view.getItems().add("Password: " + account.getPassword());
            if(!Objects.equals(account.getEmail(), "")){
                list_view.getItems().add("Email: " + account.getEmail());
            }
            if(!Objects.equals(account.getWebsite(), "")){
                list_view.getItems().add("Website: " + account.getWebsite());
            }
            list_view.getItems().add("************************************");
        }

    }

    private void updateLabelText(Label label, String text) {
        label.setText(text);
    }

}
