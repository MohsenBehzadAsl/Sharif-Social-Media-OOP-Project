package Controllers;

import View.Controller;
import component.User;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class FollowerFollowingPageController {

    ArrayList<User> followings=new ArrayList<>();
    ArrayList<User> followers=new ArrayList<>();

    public Parent nowParent;
    @FXML
    private GridPane followerGridPane;
    @FXML
    private GridPane followingGridPane;
    @FXML
    private VBox middle;
    @FXML
    private VBox followerVbox;
    @FXML
    private VBox followingVbox;
    @FXML
    private ImageView followersArrow;
    @FXML
    private ImageView followingArrow;
    @FXML
    private ImageView plusFollower;
    @FXML
    private ImageView plusFollowing;
    @FXML
    private TextField searchFollower;
    @FXML
    private TextField searchFollowing;
    @FXML
    private GridPane searchUser;
    @FXML
    private GridPane searchUser2;
    @FXML
    private GridPane total;



    @FXML
    public void initialize() throws IOException {

    }
    public void updateScrollPanes() throws IOException {
        for (int i=0;i<Controller.user.getFollowers().size();i++){
            followers.add(Controller.user.getFollowers().get(i));
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/UserIconInFollowingFollowerPage.fxml"));
            Parent parent=fxmlLoader.load();
            UserIconInFollowingFollowerPageController userIconInFollowingFollowerPageController=fxmlLoader.getController();
            System.out.println(fxmlLoader.getController().getClass().toString());
            userIconInFollowingFollowerPageController.setFirst(Controller.user.getFollowers().get(i),true);
            userIconInFollowingFollowerPageController.backParent=nowParent;
            userIconInFollowingFollowerPageController.nowParent=parent;
            followerVbox.getChildren().add(parent);
        }
        for (int i=0;i<Controller.user.getFollowings().size();i++){
            followings.add(Controller.user.getFollowings().get(i));
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/UserIconInFollowingFollowerPage.fxml"));
            Parent parent=fxmlLoader.load();
            UserIconInFollowingFollowerPageController userIconInFollowingFollowerPageController=fxmlLoader.getController();
            userIconInFollowingFollowerPageController.setFirst(Controller.user.getFollowings().get(i),false);
            userIconInFollowingFollowerPageController.backParent=nowParent;
            userIconInFollowingFollowerPageController.nowParent=parent;
            followingVbox.getChildren().add(parent);
        }
    }

    @FXML
    void followerMaximize(MouseEvent event) {
        if (followersArrow.getRotate()==-90) {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setToAngle(90);
            rotateTransition.setNode(followersArrow);
            rotateTransition.play();
            total.getColumnConstraints().get(0).setPercentWidth(100);
            total.getColumnConstraints().get(1).setPercentWidth(0);
            total.getColumnConstraints().get(2).setPercentWidth(0);
            middle.setVisible(false);
            followingGridPane.setVisible(false);
            followerGridPane.setVisible(true);
        }else {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setToAngle(-90);
            rotateTransition.setNode(followersArrow);
            rotateTransition.play();
            total.getColumnConstraints().get(0).setPercentWidth(48);
            total.getColumnConstraints().get(1).setPercentWidth(4);
            total.getColumnConstraints().get(2).setPercentWidth(48);
            middle.setVisible(true);
            followerGridPane.setVisible(true);
            followingGridPane.setVisible(true);
        }
    }

    @FXML
    void followingMaximize(MouseEvent event) {
        if (followingArrow.getRotate()==90) {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setToAngle(-90);
            rotateTransition.setNode(followingArrow);
            rotateTransition.play();
            total.getColumnConstraints().get(0).setPercentWidth(0);
            total.getColumnConstraints().get(1).setPercentWidth(0);
            total.getColumnConstraints().get(2).setPercentWidth(100);
            middle.setVisible(false);
            followerGridPane.setVisible(false);
            followingGridPane.setVisible(true);
        }else {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setToAngle(90);
            rotateTransition.setNode(followingArrow);
            rotateTransition.play();
            total.getColumnConstraints().get(0).setPercentWidth(48);
            total.getColumnConstraints().get(1).setPercentWidth(4);
            total.getColumnConstraints().get(2).setPercentWidth(48);
            middle.setVisible(true);
            followerGridPane.setVisible(true);
            followingGridPane.setVisible(true);
        }
    }

    @FXML
    void newPv(MouseEvent event) {

    }

    @FXML
    void searchFollower(MouseEvent event) {

    }

    @FXML
    void searchFollowing(MouseEvent event) {

    }
    @FXML
    void back(MouseEvent event) {
        Controller.main.getChildren().clear();
    }
}
