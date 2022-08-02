package View;

import DataBase.DataBase;
import component.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public final static String Block_Photo= String.valueOf(Controller.class.getResource("/images/block.png"));
    public static User user=new User();
    public static Stage stage=new Stage();
    public static Scene startPage;
    public static GridPane main;

    public static void showHomePage(String id) throws IOException {
        Controller.user= DataBase.getUserWithId(id);

        Parent pane = FXMLLoader.load(Controller.class.getResource("/fxml/MainPage.fxml"));
        Scene scene = new Scene(pane);
        Controller.stage.setScene(scene);
    }
    public static boolean find(String sentence, String key) {
        if (key.matches("(^\")(.+)(\"$)")) { //exact search
            key=key.replaceAll("\"","");
            if (sentence.matches("(.*)" + "(\\s+)" + key + "(\\s+)" + "(.*)") ||
                    sentence.matches("^" + key + "(\\s+)" + "(.*)") ||
                    sentence.matches("(.*)" + "(\\s+)" + key + "$") ||
                    sentence.matches("^"+key+"$")) {
                return true;
            }
            return false;
        }else{
            if (sentence.matches("(.*)"+ key +"(.*)")){
                return true;
            }
            return false;
        }
    }



    public static void changeTextFieldColor(TextField textField, String promptText, String color, Boolean timer,Boolean clicked){
        textField.setStyle("-fx-background-color:"+color);
        textField.setText("");
        textField.setPromptText(promptText);

        if (clicked) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    textField.setStyle("-fx-background-color: #efece6");
                    textField.setStyle("-fx-background-radius: 50");
                }
            });
        }

        if (timer) {
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    textField.setStyle("-fx-background-color: #efece6");
                    textField.setStyle("-fx-background-radius: 50");
                }
            }, 3000l);
        }
    }



}
