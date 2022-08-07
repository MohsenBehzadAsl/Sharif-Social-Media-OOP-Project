package Controllers.GroupControllers;

import DataBase.UpdateSqlTable;
import View.Controller;
import component.Group;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupInfoController {

    public Group group;
    public GroupPageController groupPageController;

    @FXML
    private VBox MembersVbox;
    @FXML
    private Button banGroupButton;
    @FXML
    private TextArea bioTextArea;
    @FXML
    private VBox followersAddMember;
    @FXML
    private TextField idTextField;
    @FXML
    private Circle image;
    @FXML
    private Label nMembers;
    @FXML
    private Label nMessages;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField owner;
    @FXML
    private Button changePhoto;

    @FXML
    void back(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        updateInfoOfGroup();
        groupPageController.closeInfo();
        groupPageController.updateGroups();
        groupPageController.beckFromSetting();
    }

    private void updateInfoOfGroup() {
        group.setBio(bioTextArea.getText());
        group.setName(nameTextField.getText());
        group.setGroupId(idTextField.getText());
    }

    @FXML
    void banGroupClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(Controller.user==group.getOwner()) {
            if (group.getBanGroup()) {
                String style = "";
                style += "-fx-background-color: Green;";
                style += "-fx-background-radius: 50;";
                banGroupButton.setStyle(style);
                banGroupButton.setText("UnBan Group");
                group.setBanGroup(false);
            } else {
                String style = "";
                style += "-fx-background-color: Red;";
                style += "-fx-background-radius: 50;";
                banGroupButton.setStyle(style);
                banGroupButton.setText("Ban Group");
                group.setBanGroup(true);
            }
        }
    }

    @FXML
    void changePhoto(MouseEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage stage=(Stage) nameTextField.getScene().getWindow();
        File file=  fileChooser.showOpenDialog(stage);
        if(file!=null){
            group.setPhoto(file.toURI().toString());
            image.setFill(new ImagePattern(new Image(group.getPhoto())));
        }
    }

    public void setFirst() throws IOException {
        if (!group.getBanGroup()){
            String style="";
            style+="-fx-background-color: Green;";
            style+="-fx-background-radius: 50;";
            banGroupButton.setStyle(style);
            banGroupButton.setText("UnBan Group");
        }else {
            String style="";
            style+="-fx-background-color: Red;";
            style+="-fx-background-radius: 50;";
            banGroupButton.setStyle(style);
            banGroupButton.setText("Ban Group");
        }
        updateMembers();
        updateFollowers();
        System.out.println(group.getOwner().getUserName());
        image.setFill(new ImagePattern(new Image(group.getPhoto())));
        owner.setText(group.getOwner().getUserName());
        nMembers.setText(nMembers.getText()+" "+group.getMembers().size());
        nMessages.setText(nMessages.getText()+" "+group.getMessages().size());
        if (!group.getAdmins().contains(Controller.user)&&group.getOwner()!=Controller.user){
            idTextField.setEditable(false);
            nameTextField.setEditable(false);
            bioTextArea.setEditable(false);
            changePhoto.setVisible(false);
        }
        idTextField.setText(group.getGroupId());
        nameTextField.setText(group.getName());
        bioTextArea.setText(group.getBio());
    }
    public void updateFollowers() throws IOException {
        if (Controller.user==group.getOwner()||group.getAdmins().contains(Controller.user)) {
            followersAddMember.getChildren().clear();
            followersAddMember.getChildren().removeAll();
            for (int i = 0; i < Controller.user.getFollowers().size(); i++) {
                if ((!group.getMembers().contains(Controller.user.getFollowers().get(i))) && (Controller.user.getFollowers().get(i).getAddToGroupAbility())) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FollowerIconInAddMember.fxml"));
                    Parent parent = fxmlLoader.load();
                    followersAddMember.getChildren().add(parent);
                    FollowerIconInAddMemberController followerIconInAddMemberController = fxmlLoader.getController();
                    followerIconInAddMemberController.user = Controller.user.getFollowers().get(i);
                    followerIconInAddMemberController.members = group.getMembers();
                    followerIconInAddMemberController.groupInfoController = this;
                    followerIconInAddMemberController.setFirst(Controller.user.getFollowers().get(i));
                }
            }
        }
    }
    public void updateMembers() throws IOException {
        MembersVbox.getChildren().clear();
        MembersVbox.getChildren().removeAll();
        for (int i=0;i<group.getMembers().size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MemberIcon.fxml"));
            Parent parent=fxmlLoader.load();
            MembersVbox.getChildren().add(parent);
            MemberIconController memberIconController=fxmlLoader.getController();
            memberIconController.backParent=groupPageController.nowParent;
            memberIconController.member=group.getMembers().get(i);
            memberIconController.group=group;
            memberIconController.groupInfoController=this;
            memberIconController.setFirst();
        }
    }
}
