package Controllers.GroupControllers;

import Controllers.GroupControllers.FollowerMakeNewGroupController;
import Controllers.GroupControllers.GroupPageController;
import View.Controller;
import component.Group;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class MakeNewGroupController {

    public GroupPageController groupPageController;
    public String photo;
    public ArrayList<User> members=new ArrayList<>();

    @FXML
    private VBox FollowersList;

    @FXML
    private TextField idTextField;

    @FXML
    private Circle image;

    @FXML
    private TextField nameTextField;


    public void start() throws IOException {
        for (int i=0;i< Controller.user.getFollowers().size();i++){
            if (Controller.user.getFollowers().get(i).getAddToGroupAbility()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FollowerMakeNewGroup.fxml"));
                Parent parent=fxmlLoader.load();
                FollowerMakeNewGroupController followerMakeNewGroupController=fxmlLoader.getController();
                followerMakeNewGroupController.user=Controller.user.getFollowers().get(i);
                followerMakeNewGroupController.backParent= groupPageController.nowParent;
                followerMakeNewGroupController.setFirst(Controller.user.getFollowers().get(i));
                FollowersList.getChildren().add(parent);
                followerMakeNewGroupController.members=members;
            }
        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        groupPageController.showMakeNewGroup(false);
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
            photo=file.toURI().toString();
            image.setFill(new ImagePattern(new Image(photo)));
        }
    }
    @FXML
    void makeGroup(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {
        String id=idTextField.getText();
        String name=nameTextField.getText();
        boolean flag=true;
        String photo=this.photo;
        if (photo==null){
            photo=new String();
            int index= ((int) (Math.random()*10))%7;
            photo="sampleProfilePhoto"+index;
            photo= String.valueOf(Controller.class.getResource("/images/"+photo+".png"));
        }
        if(id.isEmpty()){
            Controller.changeTextFieldColor(idTextField,"it's empty","#ec1a1a",true,true);
            flag=false;
        }if (name.isEmpty()){
            Controller.changeTextFieldColor(nameTextField,"it's empty","#ec1a1a",true,true);
            flag=false;
        }
        if (!flag){
            return;
        }else {
            Group group=new Group(Controller.user,name,id,photo);
            for (int i=0;i<members.size();i++){
                group.addMember(members.get(i));
            }
            back(null);
        }
    }

}
