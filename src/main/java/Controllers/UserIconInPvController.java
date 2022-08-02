package Controllers;

import DataBase.DataBase;
import component.User;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;

public class UserIconInPvController {
    @FXML
    private Label Id;

    @FXML
    private Label Name;

    @FXML
    private Circle image;
    public PvPageController pvPageController;

    public void set(User user){

        Id.setText(Id.getText()+"@"+user.getId());
        Name.setText(user.getUserName());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
    }

    public void newPv(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        if (mouseEvent.getClickCount()==2){
            System.out.println(Id.getText());
            pvPageController.showPv(DataBase.getUserWithId(Id.getText().replaceAll("Id :@","")));
        }
    }
}
