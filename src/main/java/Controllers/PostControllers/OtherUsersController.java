package Controllers.PostControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class OtherUsersController {


    @FXML
    private ImageView UserProfile;

    @FXML
    private Label userName;

    public void showOthersPage(MouseEvent mouseEvent) {

    }

    public ImageView getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(ImageView userProfile) {
        UserProfile = userProfile;
    }

    public Label getUserName() {
        return userName;
    }

    public void setUserName(Label userName) {
        this.userName = userName;
    }
}
