package Controllers.GroupControllers;

import View.Controller;
import component.Group;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;

public class GroupIconInGroupPageController {
    public GroupPageController groupPageController;
    public Group group;

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
        if (event.getClickCount()==1){
            groupPageController.showGroup(group);
        }
    }

    public void set(Group group) {
        this.group=group;
        id.setText("@"+group.getGroupId());
        name.setText(group.getName());
        photo.setFill(new ImagePattern(new Image(group.getPhoto())));
        if (group.getMessages().size()!=0) {
            lastContent.setText(group.getMessages().get(group.getMessages().size() - 1).getContent().split("\n")[0]);
        }else {
            lastContent.setText("No Message");
        }
        notread.setText(String.valueOf(group.getMessages().size()- Controller.user.getReadMessageGroup().get(Controller.user.getGroups().indexOf(group))));

        MenuItem markAsRead = new MenuItem("mark As Read");
        markAsRead.setOnAction(e -> {
            Controller.user.getReadMessageGroup().set(Controller.user.getGroups().indexOf(group),group.getMessages().size());
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
