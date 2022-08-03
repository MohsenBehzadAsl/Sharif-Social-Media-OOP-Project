package Controllers;

import View.Controller;
import component.Pv;
import component.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PvIconController {

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
            lastContent.setText(pv.getMessages().get(pv.getMessages().size() - 1).getContent());
        }else {
            lastContent.setText("No Message");
        }
        notread.setText(String.valueOf(pv.getMessages().size()- Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv))));
    }

    @FXML
    void selectPv(MouseEvent event) {

    }

}
