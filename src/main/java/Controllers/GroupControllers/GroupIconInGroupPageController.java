package Controllers.GroupControllers;

import View.Controller;
import component.Group;
import component.Message;
import component.Post;
import component.User;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GroupIconInGroupPageController {
    public GroupPageController groupPageController;
    public Group group;
    public boolean isPost;
    public boolean isInGroup=true;
    public Stage popUp;
    public Post selectPost;
    public Message selectMessage;
    public boolean isGroupForward;

    @FXML
    private Label id;

    @FXML
    private Label lastContent;

    @FXML
    private Label name;

    @FXML
    private Label notread;

    @FXML
    private Circle photo;

    @FXML
    private GridPane totalGridPane;

    @FXML
    void selectGroup(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (isInGroup) {
            if (event.getClickCount() == 1) {
                groupPageController.showGroup(group);
            }
        }else {
            Message message;
            User forwarder;
            if (isPost) {
                message = new Message(Controller.user, selectPost.getFormat(), selectPost.getContent(), false, true, false);
                message.setPhotoAddress(selectPost.getPhotoAddress());
                forwarder=selectPost.getSender();
            }else {
                message = new Message(Controller.user, selectMessage.getFormat(), selectMessage.getContent(), false, true, false);
                if (!selectMessage.getForward()) {
                    forwarder=selectMessage.getSender();
                }else {
                    forwarder=selectMessage.getForwardFrom();
                }
                message.setPhotoAddress(selectMessage.getPhotoAddress());
            }
            message.setForwardFrom(forwarder);
            group.addMessage(message);
            if (isGroupForward) {
                groupPageController.updateGroups();
                groupPageController.showGroup(group);
            }
            message.setIsPvOrGroup("group",group.getSqlId());
            message.addMessageToTable();
            popUp.close();
        }
    }

    public void set(Group group) {
        this.group=group;
        id.setText("@"+group.getGroupId());
        name.setText(group.getName());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$4");
        System.out.println(group.getPhoto());
        photo.setFill(new ImagePattern(new Image(group.getPhoto())));
        if (group.getMessages().size()!=0) {
            lastContent.setText(group.getMessages().get(group.getMessages().size() - 1).getContent().split("\n")[0]);
        }else {
            lastContent.setText("No Message");
        }
        notread.setText(String.valueOf(group.getMessages().size()- Controller.user.getReadMessageGroup().get(Controller.user.getGroups().indexOf(group))));

        if (isInGroup) {
            MenuItem markAsRead = new MenuItem("mark As Read");
            markAsRead.setOnAction(e -> {
                Controller.user.getReadMessageGroup().set(Controller.user.getGroups().indexOf(group), group.getMessages().size());
                try {
                    groupPageController.updateGroups();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            MenuItem deleteMenu = new MenuItem("Delete");
            deleteMenu.setOnAction(e -> {
                try {
                    group.removeMember(Controller.user);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                try {
                    groupPageController.updateGroups();
                    groupPageController.visibleGroup(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            ContextMenu menu = new ContextMenu(markAsRead, deleteMenu);
            totalGridPane.setOnContextMenuRequested(e -> {
                menu.show(totalGridPane.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });
        }
    }



}
