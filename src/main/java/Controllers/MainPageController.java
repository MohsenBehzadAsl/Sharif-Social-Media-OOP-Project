package Controllers;

import View.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainPageController {

    @FXML
    private HBox LogOutButton;

    @FXML
    private GridPane main;

    @FXML
    void AnalysePage(MouseEvent event) {

    }

    @FXML
    void Exit(MouseEvent event) {
        Controller.stage.setScene(Controller.startPage);
    }
    @FXML
    void Explore(MouseEvent event) {

    }
    @FXML
    void Followers(MouseEvent event) {

    }
    @FXML
    void Followings(MouseEvent event) {

    }
    @FXML
    void Group(MouseEvent event) {

    }
    @FXML
    void Home(MouseEvent event) {

    }
    @FXML
    void MainPage(MouseEvent event) {

    }
    @FXML
    void Pv(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Scene scene=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        Controller.stage.setScene(scene);
    }
    @FXML
    void SaveMessage(MouseEvent event) {

    }
    @FXML
    void Setting(MouseEvent event) {

    }

}
