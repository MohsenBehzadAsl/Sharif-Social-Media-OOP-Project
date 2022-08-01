package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SignUpController {

    public GridPane right;

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

    public void Back(ActionEvent actionEvent) {
        right.getChildren().clear();
    }


    public void SignUp(ActionEvent actionEvent) {
    }
}
