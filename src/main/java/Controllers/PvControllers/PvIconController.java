package Controllers.PvControllers;

import DataBase.DataBase;
import View.Controller;
import component.Pv;
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

public class PvIconController {

    public PvPageController pvPageController;
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
        id.setText("@"+Controller.user.getLinkedPvs().get(pv).getId());
        name.setText(Controller.user.getLinkedPvs().get(pv).getUserName());
        photo.setFill(new ImagePattern(new Image(Controller.user.getLinkedPvs().get(pv).getPhotoNameFromImageFolder())));
        if (pv.getMessages().size()!=0) {
            lastContent.setText(pv.getMessages().get(pv.getMessages().size() - 1).getContent().split("\n")[0]);
        }else {
            lastContent.setText("No Message");
        }
        notread.setText(String.valueOf(pv.getMessages().size()- Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv))));
        //ContextMenu contextMenu=new ContextMenu();
        MenuItem markAsRead = new MenuItem("mark As Read");
        markAsRead.setOnAction(e -> {
            Controller.user.getReadMessagePv().set(Controller.user.getPvs().indexOf(pv),pv.getMessages().size());
            try {
                pvPageController.updatePvs();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem deleteMenu = new MenuItem("Delete");
        deleteMenu.setOnAction(e -> {
            Controller.user.removePv(pv);
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

    @FXML
    void selectPv(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (event.getClickCount()==1){
            pvPageController.showPv(DataBase.getUserWithId(id.getText().replaceAll("@","")));
        }
    }

}
