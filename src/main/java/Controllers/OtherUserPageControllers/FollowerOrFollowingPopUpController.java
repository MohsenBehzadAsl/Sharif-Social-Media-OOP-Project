package Controllers.OtherUserPageControllers;

import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class FollowerOrFollowingPopUpController {

    public User user;
    public boolean isFollowing;
    public Parent backParent;
    public Stage popUp;

    @FXML
    private Label followingFollowersLabel;

    @FXML
    private VBox vBox;

    public void update() throws IOException {
        if (isFollowing){
            followingFollowersLabel.setText("Followings");
            updateVbox(user.getFollowings());
        }else {
            followingFollowersLabel.setText("Followers");
            updateVbox(user.getFollowers());
        }
    }
    public void updateVbox(ArrayList<User> users) throws IOException {
        for (int i=0;i<users.size();i++){
            loadUserInVbox(users.get(i));
        }
    }
    private void loadUserInVbox(User user) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/FollowingFollowerIconInPopUp.fxml"));
        Parent parent=fxmlLoader.load();
        FollowingFollowerIconInPopUpController followingFollowerIconInPopUpController=fxmlLoader.getController();
        vBox.getChildren().add(parent);
        followingFollowerIconInPopUpController.popUp=popUp;
        followingFollowerIconInPopUpController.backParent=backParent;
        followingFollowerIconInPopUpController.setFirst(user);
    }

}
