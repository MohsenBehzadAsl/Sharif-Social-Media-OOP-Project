package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ForgotPasswordController {
    public VBox right=new VBox();
    @FXML
    private Label NewPasswordLabel;

    @FXML
    private Button SignInButton;

    @FXML
    private TextField answer;

    @FXML
    private Label checkPasswordLabel;

    @FXML
    private TextField checkPasswordTextField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private HBox questionTextArea;

    @FXML
    void Back(ActionEvent event) {
        right.getChildren().clear();
        right.setStyle("-fx-background-color: #000000");
    }

    @FXML
    void Check(ActionEvent event) {

    }

    @FXML
    void SignIn(ActionEvent event) {

    }
}
