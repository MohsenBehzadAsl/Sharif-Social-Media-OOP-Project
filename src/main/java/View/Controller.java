package View;

import Controllers.ForwardPopUpControllers;
import Controllers.MainPageController;
import DataBase.DataBase;
import component.Post;
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
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public final static String Block_Photo= String.valueOf(Controller.class.getResource("/images/block.png"));
    public static User user=new User();
    public static Stage stage=new Stage();
    public static Scene startPage;
    public static GridPane main;
    public static Scene scene;
    public static MainPageController mainPageController;
    public static void showHomePage(String id) throws IOException, SQLException, ClassNotFoundException {
        Controller.user= DataBase.getUserWithId(id);

         FXMLLoader fxmlLoader=new FXMLLoader(Controller.class.getResource("/fxml/MainPage.fxml"));
         Parent parent=fxmlLoader.load();
         mainPageController=fxmlLoader.getController();
         mainPageController.update();
         if (user.getType().equalsIgnoreCase("Normal")){
             mainPageController.getAnalyzeHBox().setVisible(false);
         }
         mainPageController.Home(null);
         scene = new Scene(parent);
         String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
         scene.getStylesheets().add(css);

       // AquaFx.style();
        Controller.stage.setScene(scene);



    }
    public static boolean find(String sentence, String key) {
        String finder[]=sentence.split("\n");
        for (int i=0;i<finder.length;i++) {
            if (key.matches("(^\")(.+)(\"$)")) { //exact search
                key = key.replaceAll("\"", "");
                if (finder[i].matches("(.*)" + "(\\s+)" + key + "(\\s+)" + "(.*)") ||
                        sentence.matches("^" + key + "(\\s+)" + "(.*)") ||
                        sentence.matches("(.*)" + "(\\s+)" + key + "$") ||
                        sentence.matches("^" + key + "$")) {
                    return true;
                }
                return false;
            } else {
                if (finder[i].matches("(.*)" + key + "(.*)")) {
                    return true;
                }
            }
        }
        return false;
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
    public static void forwardMessageSimple(Post post) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(Controller.class.getResource("/fxml/ForwardPopUp.fxml"));
        Parent parent=fxmlLoader.load();
        ForwardPopUpControllers forwardPopUpControllers=fxmlLoader.getController();
        forwardPopUpControllers.isPost=true;
        forwardPopUpControllers.post=post;
        Scene scene = new Scene(parent, 520, 550);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setX(Controller.stage.getX()+Controller.stage.getWidth()/2-520/2);
        stage.setY(Controller.stage.getY()+Controller.stage.getHeight()/2-550/2);
        stage.setResizable(false);
        forwardPopUpControllers.popUp=stage;
        forwardPopUpControllers.set();
        stage.show();
    }

}
