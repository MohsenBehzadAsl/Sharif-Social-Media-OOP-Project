package Controllers.AnalysePageControllers;

import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import View.Controller;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ViewUserInAnalyzePageController {

    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private GridPane total;

    @FXML
    private Circle image;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label followings;

    @FXML
    private Label followers;

    @FXML
    private Label type;

    @FXML
    private Label viewDate;

    @FXML
    void visitPage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
        Parent parent=fxmlLoader.load();
        ShowAnotherUserPageController showAnotherUserPageController=fxmlLoader.getController();
        Controller.mainPageController.setMain(parent);
        showAnotherUserPageController.user=user;
        showAnotherUserPageController.start(user);
        user.getViewsFromPage().put(Controller.user, LocalDateTime.now());
    }

    public GridPane getTotal() {
        return total;
    }

    public void setTotal(GridPane total) {
        this.total = total;
    }

    public Circle getImage() {
        return image;
    }

    public void setImage(Circle image) {
        this.image = image;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getId() {
        return id;
    }

    public void setId(Label id) {
        this.id = id;
    }

    public Label getFollowings() {
        return followings;
    }

    public void setFollowings(Label followings) {
        this.followings = followings;
    }

    public Label getFollowers() {
        return followers;
    }

    public void setFollowers(Label followers) {
        this.followers = followers;
    }

    public Label getType() {
        return type;
    }

    public void setType(Label type) {
        this.type = type;
    }

    public Label getViewDate() {
        return viewDate;
    }

    public void setViewDate(Label viewDate) {
        this.viewDate = viewDate;
    }
}
