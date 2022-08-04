package Controllers;

import View.Controller;
import component.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;

public class ShowAnotherUserPageController {

    public User user;
    public Parent backParent=null;
    public Parent nowParent=null;

    @FXML
    private Button followOrUnFollow;
    @FXML
    private Label bio;
    @FXML
    private Label id;
    @FXML
    private Circle image;
    @FXML
    private Label myFollowersLabel;
    @FXML
    private Label myFollowingsLabel;
    @FXML
    private Label name;


    @FXML
    void back(MouseEvent event) {
        Controller.main.getChildren().clear();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.add(backParent,0,0);
    }

    @FXML
    void followOrUnfollow(ActionEvent event) {
        if (Controller.user.getFollowings().contains(user)){
            Controller.user.getFollowings().remove(user);
            user.getFollowers().remove(Controller.user);
        }else {
            Controller.user.getFollowings().add(user);
            user.getFollowers().add(Controller.user);
        }
        if (Controller.user.getFollowings().contains(user)){
            followOrUnFollow.setText("UnFollow");
        }else {
            followOrUnFollow.setText("Follow");
        }
        myFollowersLabel.setText(""+user.getFollowers().size());
        myFollowingsLabel.setText(""+user.getFollowings().size());
    }

    @FXML
    void sendMessage(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Parent parent=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        Controller.main.getChildren().clear();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.add(parent,0,0);
        pvPageController.showPv(user);
    }

    @FXML
    void showFollowersOFUser(ActionEvent event) {

    }

    @FXML
    void showFollowingOfUser(ActionEvent event) {

    }

    public void set(User userWithId) {
        this.user=userWithId;
        System.out.println("hello");
        bio.setText(user.getBio());
        id.setText(user.getId());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
        myFollowersLabel.setText(""+user.getFollowers().size());
        myFollowingsLabel.setText(""+user.getFollowings().size());
        name.setText(user.getUserName());
        if (Controller.user.getFollowings().contains(user)){
            followOrUnFollow.setText("UnFollow");
        }else {
            followOrUnFollow.setText("Follow");
        }

    }
}
