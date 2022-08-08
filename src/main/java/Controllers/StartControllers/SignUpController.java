package Controllers.StartControllers;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;
import Manager.ManagerLoginPage;
import View.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class SignUpController {

    @FXML
    private TextField UserName;

    @FXML
    private TextField ID;

    @FXML
    private TextField password1;

    @FXML
    private TextField checkPassword;

    @FXML
    private TextField passwordHint;

    @FXML
    private TextField question;

    @FXML
    private TextField answer;

    @FXML
    private ComboBox<String> comboboxUserType;

    @FXML
    public void initialize(){
        comboboxUserType.getItems().add("Normal");
        comboboxUserType.getItems().add("Business");
    };

    public GridPane right;

    ManagerLoginPage managerLoginPage=new ManagerLoginPage();

    public void Back(ActionEvent actionEvent) {
        right.getChildren().clear();
    }


    public void SignUp(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        boolean checked = checkAllIsValue();
        if (checked){
            DataBase.addUser(UserName.getText(),ID.getText(),password1.getText(),passwordHint.getText(),comboboxUserType.getSelectionModel().getSelectedItem(),question.getText(),answer.getText());
            Controller.showHomePage(ID.getText());
        }
    }

    private boolean checkAllIsValue() {

        boolean flag=true;
        if (UserName.getText().isEmpty()){
            Controller.changeTextFieldColor(UserName,"Please fill UserName.", "#ec1a1a",true,true);
            flag=false;
        }if (ID.getText().isEmpty()){
            Controller.changeTextFieldColor(ID,"Please fill ID.", "#ec1a1a",true,true);
            flag=false;
        }else {
            if (managerLoginPage.validID(ID.getText())){
                Controller.changeTextFieldColor(ID,"database have this id :(", "#ec1a1a",true,true);
                flag=false;
            }
        }if (password1.getText().isEmpty()){
                Controller.changeTextFieldColor(password1,"Please fill password.", "#ec1a1a",true,true);
                flag=false;
        }if (checkPassword.getText().isEmpty()){
            Controller.changeTextFieldColor(checkPassword,"Please fill check Password.", "#ec1a1a",true,true);
            flag=false;
        } if (passwordHint.getText().isEmpty()){
            Controller.changeTextFieldColor(passwordHint,"Please fill passwordHint.", "#ec1a1a",true,true);
            flag=false;
        }if (question.getText().isEmpty()){
            Controller.changeTextFieldColor(question,"Please fill question.", "#ec1a1a",true,true);
            flag=false;
        } if (answer.getText().isEmpty()){
            Controller.changeTextFieldColor(answer,"Please fill answer.", "#ec1a1a",true,true);
            flag=false;
        }if (comboboxUserType.getSelectionModel().getSelectedItem()==null){
            comboboxUserType.setStyle("-fx-background-color: #ec1a1a");
            comboboxUserType.setPromptText("Please select your type");
            comboboxUserType.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    comboboxUserType.setStyle("-fx-background-color: #efece6");
                    comboboxUserType.setStyle("-fx-background-radius: 50");
                }
            });
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    comboboxUserType.setStyle("-fx-background-color: #efece6");
                    comboboxUserType.setStyle("-fx-background-radius: 50");
                }
            }, 3000l);
            flag=false;
        }



        String newPassword=password1.getText();
        String check=checkPassword.getText();

        if (managerLoginPage.validPassword(newPassword)) {
            if (!newPassword.equals(check)){
                Controller.changeTextFieldColor(checkPassword,"Please enter your new password correctly ", "#ec1a1a",true,true);
                flag=false;
            }

        }else{
            Controller.changeTextFieldColor(password1,"Please enter valid Password.", "#ec1a1a",true,true);
            Controller.changeTextFieldColor(checkPassword," ", "#ec1a1a",true,true);
            flag=false;
        }


        return flag;
    }

}
