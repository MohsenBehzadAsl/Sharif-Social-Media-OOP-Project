package Controllers.PvControllers;

import DataBase.DataBase;
import View.Controller;
import component.Message;
import component.Post;
import component.Pv;
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
import DataBase.UpdateSqlTable;

import java.io.IOException;
import java.sql.SQLException;
import DataBase.UpdateSqlTable;
import javafx.stage.Stage;

public class PvIconController {

    public boolean isInPv=true;
    public Post selectPost; //For Forward PopUp
    public Message selectMessage;
    public Stage popUp;
    public Pv pv;
    public boolean isPost=false;

    public PvPageController pvPageController;
    public boolean isPvForward;
    @FXML
    private GridPane totalGridPane;
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

    public void set(Pv pv){
        this.pv=pv;
        id.setText("@"+Controller.user.getLinkedPvs().get(pv).getId());
        name.setText(Controller.user.getLinkedPvs().get(pv).getUserName());
        photo.setFill(new ImagePattern(new Image(Controller.user.getLinkedPvs().get(pv).getPhotoNameFromImageFolder())));
        if (pv.getMessages().size()!=0) {
            lastContent.setText(pv.getMessages().get(pv.getMessages().size() - 1).getContent().split("\n")[0]);
        }else {
            lastContent.setText("No Message");
        }
        notread.setText(String.valueOf(pv.getMessages().size()- Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv))));
        if (isInPv) {
            MenuItem markAsRead = new MenuItem("mark As Read");
            markAsRead.setOnAction(e -> {
                Controller.user.getReadMessagePv().set(Controller.user.getPvs().indexOf(pv), pv.getMessages().size());
                try {
                    UpdateSqlTable.setReadMessagePv(pv, Controller.user);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pvPageController.updatePvs();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            MenuItem deleteMenu = new MenuItem("Delete");
            deleteMenu.setOnAction(e -> {
                try {
                    Controller.user.removePv(pv);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                try {
                    pvPageController.updatePvs();
                    pvPageController.visiblePv(false);
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
    @FXML
    void selectPv(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (isInPv) {
            if (event.getClickCount() == 1) {
                pvPageController.showPv(DataBase.getUserWithId(id.getText().replaceAll("@", "")));
            }
        }else {
            Message message;
            User forwarder;
            if (isPost) {
                message = new Message(Controller.user, selectPost.getFormat(), selectPost.getContent(), false, true, false);
                forwarder=selectPost.getSender();
            }else {
                message = new Message(Controller.user, selectMessage.getFormat(), selectMessage.getContent(), false, true, false);
                if (!selectMessage.getForward()) {
                    forwarder=selectMessage.getSender();
                }else {
                    forwarder=selectMessage.getForwardFrom();
                }
            }
            message.setForwardFrom(forwarder);
            pv.addMessage(message);

            if (isPvForward){
                pvPageController.updatePvs();

                if (pv.getUser1()==Controller.user) {
                    pvPageController.showPv(pv.getUser2());
                }else {
                    pvPageController.showPv(pv.getUser1());
                }
            }

        }
    }

}
