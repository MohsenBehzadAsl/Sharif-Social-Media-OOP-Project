package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Pv;
import component.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FollowersController {

    private String bio;
    private String userName;
    private String followersNumber;
    private String followingsNumber;
    private String id;


    private User user;
    @FXML
    private GridPane all;


    @FXML
    private VBox followersVBox;

    @FXML
    private Label myBioLabel;

    @FXML
    private Button myFollowButton;

    @FXML
    private Label myFollowersLabel;

    @FXML
    private Label myFollowingsLabel;

    @FXML
    private Label myIdLabel;


    @FXML
    private Circle myImageCircle;

    @FXML
    private Button mySendMessageButton;

    @FXML
    private Button myShowFollowersButton;

    @FXML
    private Button myShowFollowingButton;

    @FXML
    private Label myUserNameLabel;
    public void update() throws IOException {
        User user= Controller.user;
        ArrayList<User> followers=user.getFollowers();
        for (int i=0;i<followers.size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowUserIcon.fxml"));
            Parent parent=fxmlLoader.load();
            FollowerIconController followerIconController=fxmlLoader.getController();
        /*    Label label=new Label();
            System.out.println(followers.get(i).getId());
            System.out.println(followers.get(i).getUserName());
            label.setText(followers.get(i).getId());
            Label label1=new Label();
            label1.setText(followers.get(i).getUserName());
            followerIconController.setMyIdLabel(label);
            followerIconController.setMyNameLabel(label1);*/
            followerIconController.setId(followers.get(i).getId());
            followerIconController.setUsername(followers.get(i).getUserName());
            followerIconController.set(followers.get(i));
            addFollowerIcon(parent);
        }
    }
    public void addFollowerIcon(Parent follower){
        followersVBox.getChildren().add(follower);
    }
    @FXML
    void followOrUnfollow(ActionEvent event) {






    }
    public void show(){
        System.out.println("##############'");
     //   myBioLabel.setText(bio);
     //   myFollowersLabel.setText(followersNumber);
     //   myFollowingsLabel.setText(followingsNumber);
     //   myIdLabel.setText(id);
        System.out.println(userName);
        myUserNameLabel.setText(userName);
        System.out.println(myUserNameLabel.getText());
        Label label=new Label();
        label.setText("baz showww lamazhabbb");
        all.getColumnConstraints().get(0).setPercentWidth(0);

    }

    @FXML
    void sendMessage(ActionEvent event) {





    }

    @FXML
    void showFollowersOFUser(ActionEvent event) {





    }

    @FXML
    void showFollowingOfUser(ActionEvent event) {





    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(String followersNumber) {
        this.followersNumber = followersNumber;
    }

    public String getFollowingsNumber() {
        return followingsNumber;
    }

    public void setFollowingsNumber(String followingsNumber) {
        this.followingsNumber = followingsNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public VBox getFollowersVBox() {
        return followersVBox;
    }

    public void setFollowersVBox(VBox followersVBox) {
        this.followersVBox = followersVBox;
    }

    public Label getMyBioLabel() {
        return myBioLabel;
    }

    public void setMyBioLabel(Label myBioLabel) {
        this.myBioLabel = myBioLabel;
    }

    public Button getMyFollowButton() {
        return myFollowButton;
    }

    public void setMyFollowButton(Button myFollowButton) {
        this.myFollowButton = myFollowButton;
    }

    public Label getMyFollowersLabel() {
        return myFollowersLabel;
    }

    public void setMyFollowersLabel(Label myFollowersLabel) {
        this.myFollowersLabel = myFollowersLabel;
    }

    public Label getMyFollowingsLabel() {
        return myFollowingsLabel;
    }

    public void setMyFollowingsLabel(Label myFollowingsLabel) {
        this.myFollowingsLabel = myFollowingsLabel;
    }

    public Label getMyIdLabel() {
        return myIdLabel;
    }

    public void setMyIdLabel(Label myIdLabel) {
        this.myIdLabel = myIdLabel;
    }

    public Circle getMyImageCircle() {
        return myImageCircle;
    }

    public void setMyImageCircle(Circle myImageCircle) {
        this.myImageCircle = myImageCircle;
    }

    public Button getMySendMessageButton() {
        return mySendMessageButton;
    }

    public void setMySendMessageButton(Button mySendMessageButton) {
        this.mySendMessageButton = mySendMessageButton;
    }

    public Button getMyShowFollowersButton() {
        return myShowFollowersButton;
    }

    public void setMyShowFollowersButton(Button myShowFollowersButton) {
        this.myShowFollowersButton = myShowFollowersButton;
    }

    public Button getMyShowFollowingButton() {
        return myShowFollowingButton;
    }

    public void setMyShowFollowingButton(Button myShowFollowingButton) {
        this.myShowFollowingButton = myShowFollowingButton;
    }

    public Label getMyUserNameLabel() {
        return myUserNameLabel;
    }

    public void setMyUserNameLabel(Label myUserNameLabel) {
        this.myUserNameLabel = myUserNameLabel;
    }
    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }




}
