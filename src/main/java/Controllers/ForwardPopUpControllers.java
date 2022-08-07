package Controllers;

import Controllers.GroupControllers.GroupIconInGroupPageController;
import Controllers.GroupControllers.GroupPageController;
import Controllers.PvControllers.PvIconController;
import Controllers.PvControllers.PvPageController;
import View.Controller;
import component.Message;
import component.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ForwardPopUpControllers {
    public Message message;
    public Post post;
    public boolean isPost=false;
    public Stage popUp;
    public PvPageController pvPageController;
    public GroupPageController groupPageController;
    public boolean isInGroup=false;
    public boolean isInPv=false;



    @FXML
    private VBox groups;

    @FXML
    private VBox pvs;

    public void set() throws IOException {
        for (int i=0;i<Controller.user.getGroups().size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/GroupIconInGroupPage.fxml"));
            Parent parent=fxmlLoader.load();
            GroupIconInGroupPageController groupIconInGroupPageController=fxmlLoader.getController();
            groupIconInGroupPageController.isPost=isPost;
            groupIconInGroupPageController.group=Controller.user.getGroups().get(i);
            groupIconInGroupPageController.isInGroup=false;
            groupIconInGroupPageController.popUp=popUp;
            groupIconInGroupPageController.groupPageController=groupPageController;
            groupIconInGroupPageController.isGroupForward=isInGroup;
            if (isPost) {
                groupIconInGroupPageController.selectPost=post;
            }else {
                groupIconInGroupPageController.selectMessage=message;
            }
            groupIconInGroupPageController.set(Controller.user.getGroups().get(i));
            groups.getChildren().add(parent);
        }
        for (int i=0;i<Controller.user.getPvs().size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvIcon.fxml"));
            Parent parent=fxmlLoader.load();
            PvIconController pvIconController=fxmlLoader.getController();
            pvIconController.isPost=isPost;
            pvIconController.pv=Controller.user.getPvs().get(i);
            pvIconController.isInPv=false;
            pvIconController.popUp=popUp;
            pvIconController.pvPageController=pvPageController;
            pvIconController.isPvForward=isInPv;
            if (isPost) {
                pvIconController.selectPost=post;
            }else {
                pvIconController.selectMessage=message;
            }
            System.out.println(message.getContent());
            pvIconController.set(Controller.user.getPvs().get(i));
            pvs.getChildren().add(parent);
        }
    }
}
