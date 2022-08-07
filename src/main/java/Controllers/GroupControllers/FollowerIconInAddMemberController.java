package Controllers.GroupControllers;

import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowerIconInAddMemberController {
    public Parent backParent;
    public User user;
    public boolean select=false;
    public ArrayList<User> members=new ArrayList<>();
    GroupInfoController groupInfoController;


    @FXML
    private Label id;

    @FXML
    private Circle image;

    @FXML
    private Label name;




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

    public void setFirst(User user){
        this.user=user;
        id.setText(id.getText()+"@"+user.getId());
        name.setText(user.getUserName());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
    }

    public void addMember(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        groupInfoController.group.addMember(user);
        groupInfoController.updateMembers();
        groupInfoController.updateFollowers();
    }
}
