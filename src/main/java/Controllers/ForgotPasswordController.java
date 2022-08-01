package Controllers;

import DataBase.UpdateSqlTable;
import Manager.ManagerLoginPage;
import View.Controller;
import component.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class ForgotPasswordController {

    public User user;
    public void start(){
        question.setText(user.getQuestion());
        question.setDisable(true);
        visible(false);
    }
    private void visible (Boolean visible){
        NewPasswordLabel.setVisible(visible);
        checkPasswordLabel.setVisible(visible);
        newPasswordTextField.setVisible(visible);
        checkPasswordTextField.setVisible(visible);
        SignInButton.setVisible(visible);
        changePasswordLabel.setVisible(visible);
    }

    @FXML
    private TextArea question;

    @FXML
    private TextField answer;

    @FXML
    private Label NewPasswordLabel;

    @FXML
    private Label checkPasswordLabel;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField checkPasswordTextField;

    @FXML
    private Button SignInButton;

    @FXML
    private Label changePasswordLabel;

    public GridPane right;


    ManagerLoginPage managerLoginPage=new ManagerLoginPage();


    public void Check(ActionEvent actionEvent) {
        String answerString= answer.getText();
        if (answerString.isEmpty()){
            visible(false);
        }else if (answerString.equals(user.getAnsQuestion())){
            answer.setStyle("-fx-background-color: #1df105");
            visible(true);
        }else{
            visible(false);
            Controller.changeTextFieldColor(answer,"Please enter valid answer.", "#ec1a1a",true,true);
        }
    }

    public void SignIn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String newPassword=newPasswordTextField.getText();
        String check=checkPasswordTextField.getText();
        Boolean flag=false;
        if (newPassword.isEmpty()){
            flag=true;
        }if (check.isEmpty()){
            flag=true;
        }if (flag){
            return;
        }
        if (managerLoginPage.validPassword(newPassword)) {

            if (newPassword.equals(check)){
                UpdateSqlTable.setPasswordToTable(user,newPassword);
                user.setPassword(newPassword);
                Controller.showHomePage(user.getId());

            }else{
                Controller.changeTextFieldColor(checkPasswordTextField,"Please enter your new password correctly ", "#ec1a1a",true,true);
            }


        }else{
            Controller.changeTextFieldColor(newPasswordTextField,"Please enter valid Password.", "#ec1a1a",true,true);
            Controller.changeTextFieldColor(checkPasswordTextField," ", "#ec1a1a",true,true);
        }
    }

    public void Back(ActionEvent actionEvent) {
        right.getChildren().clear();
    }




}
