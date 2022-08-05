package Controllers.OtherUserPageControllers;

import DataBase.DataBase;
import View.Controller;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class FollowingFollowerIconInPopUpController{

    public Parent backParent;
    public User user;
    public Stage popUp;

    @FXML
    private Button followButton;

    @FXML
    private Label id;

    @FXML
    private Circle image;

    @FXML
    private Label name;

    @FXML
    private GridPane total;

    @FXML
    void follow(MouseEvent event) {
        if (Controller.user.getFollowings().contains(user)){
           Controller.user.getFollowings().remove(user);
           user.getFollowers().remove(Controller.user);
        }else {
            Controller.user.getFollowings().add(user);
            user.getFollowers().add(Controller.user);
        }

        if (Controller.user.getFollowings().contains(user)){
            String style="";
            style+="-fx-background-color: Red;";
            style+="-fx-background-radius: 50;";
            followButton.setStyle(style);
            followButton.setText("UnFollow");
        }else {
            String style="";
            style+="-fx-background-color: Green;";
            style+="-fx-background-radius: 50;";
            followButton.setStyle(style);
            followButton.setText("Follow");
        }
    }

    @FXML
    void visitPage(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
        Parent parent=fxmlLoader.load();
        ShowAnotherUserPageController showAnotherUserPageController=fxmlLoader.getController();
        Controller.main.getChildren().clear();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.add(parent,0,0);
        showAnotherUserPageController.nowParent=parent;
        showAnotherUserPageController.backParent=backParent;
        showAnotherUserPageController.set(DataBase.getUserWithId(id.getText().replaceAll("Id :@","")));
        popUp.close();
    }

    public void setFirst(User user){
        if (Controller.user==user){
            followButton.setVisible(false);
        }
        this.user=user;
        if (Controller.user.getFollowings().contains(user)){
            String style="";
            style+="-fx-background-color: Red;";
            style+="-fx-background-radius: 50;";
            followButton.setStyle(style);
            followButton.setText("UnFollow");
        }else {
            String style="";
            style+="-fx-background-color: Green;";
            style+="-fx-background-radius: 50;";
            followButton.setStyle(style);
            followButton.setText("Follow");

        }
        id.setText(id.getText()+"@"+user.getId());
        name.setText(user.getUserName());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
    }


}
