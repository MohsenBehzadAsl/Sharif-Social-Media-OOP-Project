package Controllers;

import DataBase.UpdateSqlTable;
import View.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UserInformationController {
    public Stage stage;
    private String information;
    private String newInformation;

    @FXML
    private Button myButton;

    @FXML
    private Label myLabel;

    @FXML
    private TextField myTextField;

    @FXML
    void change(ActionEvent event) throws SQLException, ClassNotFoundException {
        newInformation=myTextField.getText();

        if(information.equals("USERNAME")){
          //  myLabel.setText("LAST "+information+"="+Controller.user.getUserName());
            UpdateSqlTable.setUserNameToTable(Controller.user,newInformation);
            Controller.user.setUserName(newInformation);
        }
        if(information.equals("ID")){
       //     myLabel.setText("LAST "+information+"="+Controller.user.getId());
            //UpdateSqlTable.setIdToTable();
            Controller.user.setId(newInformation);
        }
        if(information.equals("PASSWORD")){
        //    myLabel.setText("LAST "+information+"="+Controller.user.getPassword());
            UpdateSqlTable.setPasswordToTable(Controller.user,newInformation);
            Controller.user.setPassword(newInformation);
        }
        if(information.equals("PASSWORD HINT")){
          //  myLabel.setText("LAST "+information+"="+Controller.user.getPasswordHint());
            UpdateSqlTable.setPasswordHintToTable(Controller.user,newInformation);
            Controller.user.setPasswordHint(newInformation);
        }
        if(information.equals("PASSWORD QUESTION")){
        //    myLabel.setText("LAST "+information+"="+Controller.user.getQuestion());
            UpdateSqlTable.setPasswordQuestionToTable(Controller.user,newInformation);
            Controller.user.setQuestion(newInformation);
        }
        if(information.equals("QUESTION ANSWER")){
       //     myLabel.setText("LAST "+information+"="+Controller.user.getAnsQuestion());
            Controller.user.setAnsQuestion(newInformation);
            UpdateSqlTable.setAnswerOfPasswordQuestionToTable(Controller.user,newInformation);
        }if(information.equals("BIO")){
        //    myLabel.setText("LAST "+information+"="+Controller.user.getBio());
            UpdateSqlTable.setBioUserToTable(Controller.user,newInformation);
            Controller.user.setBio(newInformation);
        }if(information.equals("TYPE")){
       //     myLabel.setText("LAST "+information+"="+Controller.user.getType());
            UpdateSqlTable.setGenderToTable(Controller.user,newInformation);
            Controller.user.setType(newInformation);
        }if(information.equals("ADD TO GROUP ABILITY")){
         //   myLabel.setText("LAST "+information+"="+Controller.user.getAddToGroupAbility());
            UpdateSqlTable.setAddToGroupAbilityToTable(Controller.user, Boolean.parseBoolean(newInformation));
            Controller.user.setAddToGroupAbility(Boolean.valueOf(newInformation));
        }
        stage.close();
    }

    @FXML
    void getInformation(ActionEvent event) {
        newInformation=myTextField.getText();
    }
    public void lable(){
        if(information.equals("USERNAME")){
            myLabel.setText("LAST "+information+"="+Controller.user.getUserName());
        }
        if(information.equals("ID")){
            myLabel.setText("LAST "+information+"="+Controller.user.getId());


        }
        if(information.equals("PASSWORD")){
            myLabel.setText("LAST "+information+"="+Controller.user.getPassword());


        }
        if(information.equals("PASSWORD HINT")){
            myLabel.setText("LAST "+information+"="+Controller.user.getPasswordHint());


        }
        if(information.equals("PASSWORD QUESTION")){
            myLabel.setText("LAST "+information+"="+Controller.user.getQuestion());



        }
        if(information.equals("QUESTION ANSWER")){
            myLabel.setText("LAST "+information+"="+Controller.user.getAnsQuestion());


        }if(information.equals("BIO")){
            myLabel.setText("LAST "+information+"="+Controller.user.getBio());


        }if(information.equals("TYPE")){
            myLabel.setText("LAST "+information+"="+Controller.user.getType());


        }if(information.equals("ADD TO GROUP ABILITY")){
            myLabel.setText("LAST "+information+"="+Controller.user.getAddToGroupAbility());
        }

    }
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}
