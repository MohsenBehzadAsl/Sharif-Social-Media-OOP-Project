package Controllers;

import View.Controller;
import View.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;

public class MainPageController {

    @FXML
    private HBox LogOutButton;

    @FXML
    private GridPane main;

    @FXML
    private GridPane total;



    @FXML
    public void initialize(){
        Controller.main=main;

        Controller.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            total.getColumnConstraints().get(0).setPercentWidth(16725/Controller.stage.getWidth());
        });
    }
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
    void Followers(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowFollowers.fxml"));
        Parent parent=fxmlLoader.load();
        main.getChildren().clear();
        main.getRowConstraints().removeAll();
        main.getColumnConstraints().removeAll();
        main.add(parent,0,0);
        FollowersController followersController=fxmlLoader.getController();
        followersController.update();
        Controller.followersController=followersController;
    }
    @FXML
    void Followings(MouseEvent event) {

    }
    @FXML
    void Group(MouseEvent event) {

    }
    @FXML
    void Home(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MyHomePostPage.fxml"));
        Parent parent=fxmlLoader.load();
        MyHomePostPageController myHomePostPageController=fxmlLoader.getController();
        setMain(parent);
        myHomePostPageController.startShowPost();
        myHomePostPageController.getUsername().setText(Controller.user.getUserName());
        myHomePostPageController.getId().setText("@"+Controller.user.getId());
        myHomePostPageController.getFollowers().setText("Num Of Followers: "+ Controller.user.getFollowers().size());
        myHomePostPageController.getFollowing().setText("Num Of Following: "+ Controller.user.getFollowings().size());
        myHomePostPageController.getImageProfile().setImage(new Image(Controller.user.getPhotoNameFromImageFolder()));
        myHomePostPageController.getAll().getRowConstraints().get(0).setPercentHeight(13.6);
        myHomePostPageController.getAll().getRowConstraints().get(1).setPercentHeight(86.4);
        myHomePostPageController.getAll().getRowConstraints().get(2).setPercentHeight(0);
        myHomePostPageController.getAll().getRowConstraints().get(3).setPercentHeight(0);

    }

    @FXML
    void MainPage(MouseEvent event) {

    }
    @FXML
    void Pv(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Parent parent=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        setMain(parent);
    }

    private void setMain(Parent parent) {
        main.getChildren().clear();
        main.getColumnConstraints().removeAll();
        main.getRowConstraints().removeAll();
        main.add(parent,0,0);
    }

    @FXML
    void SaveMessage(MouseEvent event) {

    }
    @FXML
    void Setting(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Setting.fxml"));
        Parent parent=fxmlLoader.load();
        main.getChildren().clear();
        main.getRowConstraints().removeAll();
        main.getColumnConstraints().removeAll();
        main.add(parent,0,0);

    }

}
