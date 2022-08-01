package Controllers;

import Controllers.ForgotPasswordController;
import Controllers.SignUpController;
import View.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField idTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private VBox right;


    public void signUp(ActionEvent actionEvent) throws IOException {
        right.getChildren().clear();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
        VBox vBox=fxmlLoader.load();
        SignUpController signUpController=fxmlLoader.getController();

        right.getChildren().add(vBox);
    }

    public void forgetPassword(ActionEvent actionEvent) throws IOException {
        right.getChildren().clear();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ForgotPassword.fxml"));
        VBox vBox=fxmlLoader.load();
        ForgotPasswordController forgotPasswordController=fxmlLoader.getController();
        right.getChildren().add(vBox);
    }

    public void exit(ActionEvent actionEvent) {
        Controller.stage.close();
    }

    public void logIn(ActionEvent actionEvent) {
    }
}
