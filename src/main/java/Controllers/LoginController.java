package Controllers;

import Controllers.ForgotPasswordController;
import Controllers.SignUpController;
import View.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class LoginController {
    @FXML
    private TextField idTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private GridPane right;


    public void signUp(ActionEvent actionEvent) throws IOException {
        right.getChildren().clear();
        right.getRowConstraints().removeAll();
        right.getColumnConstraints().removeAll();

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
        GridPane gridPane=fxmlLoader.load();

        SignUpController signUpController=fxmlLoader.getController();
        signUpController.right=right;
        right.add(gridPane,0,0);

    }

    public void forgetPassword(ActionEvent actionEvent) throws IOException {

        right.getChildren().clear();
        right.getRowConstraints().removeAll();
        right.getColumnConstraints().removeAll();

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ForgotPassword.fxml"));
        GridPane gridPane=fxmlLoader.load();

        ForgotPasswordController forgotPasswordController=fxmlLoader.getController();
        forgotPasswordController.right=right;
        right.add(gridPane,0,0);
    }

    public void exit(ActionEvent actionEvent) {
        Controller.stage.close();
    }

    public void logIn(ActionEvent actionEvent) {
        System.out.println("_____");
        System.out.println("Controller.stage.getWidth()= "+Controller.stage.getWidth());
        System.out.println("Controller.stage.getHeight()= "+Controller.stage.getHeight());

    }
}
