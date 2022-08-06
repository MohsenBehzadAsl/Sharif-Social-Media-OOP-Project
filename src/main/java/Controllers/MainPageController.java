package Controllers;

import Controllers.GroupControllers.GroupPageController;
import Controllers.PvControllers.PvPageController;
import View.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;
import java.sql.SQLException;

public class MainPageController {

    @FXML
    private HBox LogOutButton;

    @FXML
    private GridPane main;

    public GridPane getMain() {
        return main;
    }

//    public void setMain(GridPane main) {
//        this.main = main;
//    }

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
    void AnalysePage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/AnalyzePage.fxml"));
        Parent parent=fxmlLoader.load();
        AnalyzePageController analyzePageController=fxmlLoader.getController();
        setMain(parent);
        analyzePageController.getWhere().setText("Analyze Page");
        analyzePageController.startShowPost();
    }
    @FXML
    void Exit(MouseEvent event) {
        Controller.stage.setScene(Controller.startPage);
    }
    @FXML
    void Explore(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Explore.fxml"));
        Parent parent=fxmlLoader.load();
        ExploreController exploreController=fxmlLoader.getController();
        setMain(parent);exploreController.getWhere().setText("Explore");
        exploreController.startShowPost();

    }
    @FXML
    void Group(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/GroupPage.fxml"));
        Parent parent=fxmlLoader.load();
        GroupPageController groupPageController=fxmlLoader.getController();
        setMain(parent);
        groupPageController.nowParent=parent;
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
        myHomePostPageController.getImageProfile().setFill(new ImagePattern(new Image(Controller.user.getPhotoNameFromImageFolder())));
        myHomePostPageController.getAll().getRowConstraints().get(0).setPercentHeight(13.6);
        myHomePostPageController.getAll().getRowConstraints().get(1).setPercentHeight(80);
        myHomePostPageController.getAll().getRowConstraints().get(2).setPercentHeight(6.4);
        myHomePostPageController.getAll().getRowConstraints().get(3).setPercentHeight(0);
        myHomePostPageController.getAll().getRowConstraints().get(4).setPercentHeight(0);
    }

    @FXML
    void MainPage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Explore.fxml"));
        Parent parent=fxmlLoader.load();
        ExploreController exploreController=fxmlLoader.getController();
        setMain(parent);
        exploreController.startShowPost();
        exploreController.getWhere().setText("Main Page");
    }
    @FXML
    void Pv(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Parent parent=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        pvPageController.nowParent=parent;
        setMain(parent);
    }

    public void setMain(Parent parent) {
        main.getChildren().clear();
        main.getColumnConstraints().removeAll();
        main.getRowConstraints().removeAll();
        main.add(parent,0,0);
    }

    @FXML
    void SaveMessage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Parent parent=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        pvPageController.nowParent=parent;
        setMain(parent);
        pvPageController.showPv(Controller.user);
        pvPageController.maximize(null);
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

    public void followerFollowingPage(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/FolloweFollowingPage.fxml"));
        Parent parent=fxmlLoader.load();
        FollowerFollowingPageController followerFollowingPageController=fxmlLoader.getController();
        main.getChildren().clear();
        main.getRowConstraints().removeAll();
        main.getColumnConstraints().removeAll();
        main.add(parent,0,0);
        followerFollowingPageController.nowParent=parent;
        followerFollowingPageController.updateScrollPanes();
    }
}
