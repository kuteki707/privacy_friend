import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainFX extends Application {
public static User logged_in_user = null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/loginfx.fxml"));

        Image icon = new Image("/icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Privacy Friend");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            if(logged_in_user != null){
                DataStorage.saveAccounts(logged_in_user.getAccounts(),logged_in_user);
                DataStorage.saveUsers(DataStorage.getUsers());
            }
            primaryStage.close();
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}