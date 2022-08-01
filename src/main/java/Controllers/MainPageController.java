package Controllers;

import View.Controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainPageController {
    @FXML
    private HBox LogOutButton;

    @FXML
    private GridPane main;

    @FXML
    public void initialize(){
        LogOutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Controller.stage.setScene(Controller.startPage);
            }
        });
    }
}
