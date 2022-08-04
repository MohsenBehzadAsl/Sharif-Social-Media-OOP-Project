package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class UserIconInFollowingFollowerPageController {

    public Parent backParent;
    public Parent nowParent;
    boolean isFollower=true;

    @FXML
    private Label followers;

    @FXML
    private Label followings;

    @FXML
    private Label id;

    @FXML
    private Circle image;

    @FXML
    private Label name;

    @FXML
    private GridPane total;

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
    }

    public void setFirst(User user,boolean isFollower){
        this.isFollower=isFollower;
        id.setText(id.getText()+"@"+user.getId());
        name.setText(user.getUserName());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
        followers.setText(followers.getText()+" "+user.getFollowers().size());
        followings.setText(followings.getText()+" "+user.getFollowers().size());
        MenuItem item;
        if (isFollower) { //remove Follower
            item = new MenuItem("remove");
            item.setOnAction(e -> {
                Controller.user.getFollowers().remove(user);
                user.getFollowings().remove(Controller.user);
            });
        }else {
            item = new MenuItem("UnFollow");
            item.setOnAction(e -> {
                user.getFollowers().remove(Controller.user);
                Controller.user.getFollowings().remove(user);
            });
        }
        ContextMenu menu = new ContextMenu(item);
        total.setOnContextMenuRequested(e -> {
            menu.show(total.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });
    }

}
