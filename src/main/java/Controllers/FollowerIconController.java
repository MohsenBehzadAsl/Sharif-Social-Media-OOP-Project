package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class FollowerIconController {
    @FXML
    private Label myIdLabel;
    @FXML
    private Label myNameLabel;
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Label getMyIdLabel() {
        return myIdLabel;
    }

    public void setMyIdLabel(Label myIdLabel) {
        this.myIdLabel = myIdLabel;
    }

    public Label getMyNameLabel() {
        return myNameLabel;
    }

    public void setMyNameLabel(Label myNameLabel) {
        this.myNameLabel = myNameLabel;
    }

    public Circle getMyProfilePicture() {
        return myProfilePicture;
    }

    public void setMyProfilePicture(Circle myProfilePicture) {
        this.myProfilePicture = myProfilePicture;
    }

    @FXML
    private Circle myProfilePicture;
    public void set(User user){
        myIdLabel.setText(id);
        myNameLabel.setText(username);

    }
    @FXML
    void choose(MouseEvent event) throws IOException {
    //    FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowFollowers.fxml"));
      //  Parent parent=fxmlLoader.load();
       FollowersController followersController=Controller.followersController;
       //  followersController = fxmlLoader.getController();
        DataBase dataBase=new DataBase();
        User user=new User();
        user.setId(id);
        for (User user1 : DataBase.getUsers()) {
            if(user1.getId().equals(user.getId())){
                System.out.println("*************");
              //  followersController.getAll().getColumnConstraints().get(0).setPercentWidth(0);
                //followersController.setBio(user1.getBio());
              //  followersController.setId(user1.getId());
                //followersController.getMyUserNameLabel().setText(user1.getUserName());
                followersController.setUserName(user1.getUserName());
             //   int followers=0;
             //   for (User follower : user1.getFollowers()) {
             //       followers++;
             //   }
             //   int followingg=0;
             //   for (User following : user1.getFollowings()) {
             //       followingg++;
             //   }
              //  followersController.setFollowersNumber(String.valueOf(followers));
             //   followersController.setFollowingsNumber(String.valueOf(followingg));
                followersController.show();
            }
        }




    }
}
