package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SignUpController {
    public VBox right=new VBox();
    @FXML
    private TextField ID;

    @FXML
    private TextField UserName;

    @FXML
    private TextField answer;

    @FXML
    private TextField checkPassword;

    @FXML
    private TextField password1;

    @FXML
    private TextField passwordHint;

    @FXML
    private TextField question;

    @FXML
    void Back(ActionEvent event) {
        right.getChildren().clear();
        right.setStyle("-fx-background-color: #000000");
    }

    @FXML
    void SignUp(ActionEvent event) {

    }

}
