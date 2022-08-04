package Controllers;

import DataBase.UpdateSqlTable;
import View.Controller;
import component.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private ImageView myImageView;

    @FXML
    private Button myButton;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private Label myLabel1;

    @FXML
    private Label myLabel2;
    @FXML
    private TextField myTextField;
    @FXML
    private GridPane myGridPane;
    @FXML
    void change(ActionEvent event) throws SQLException, ClassNotFoundException {
        value=myTextField.getText();
        myLabel1.setText("ENTER NEW .....");
      //  myLabel2.setText("..... CHANGED SUCCESSFULLY");
        System.out.println(myChoiceBox.getValue());
        if(myChoiceBox.getValue().equals("USERNAME")){
            System.out.println("88888888888888888");
            user.setUserName(value);
            myLabel2.setText("USERNAME CHANGED SUCCESSFULLY");
            UpdateSqlTable.setUserNameToTable(user,value);
        }
        if(myChoiceBox.getValue().equals("ID")){
            user.setId(value);
            myLabel2.setText("ID CHANGED SUCCESSFULLY");
            //UpdateSqlTable.setIdToTable();
        }
        if(myChoiceBox.getValue().equals("PASSWORD")){
            user.setPassword(value);
            myLabel2.setText("PASSWORD CHANGED SUCCESSFULLY");
            UpdateSqlTable.setPasswordToTable(user,value);
        }if(myChoiceBox.getValue().equals("PASSWORD HINT")){
            user.setPasswordHint(value);
            myLabel2.setText("PASSWORD HINT CHANGED SUCCESSFULLY");
            UpdateSqlTable.setPasswordHintToTable(user,value);
        }if(myChoiceBox.getValue().equals("PASSWORD QUESTION")){
            user.setQuestion(value);
            myLabel2.setText("PASSWORD QUESTION CHANGED SUCCESSFULLY");
            UpdateSqlTable.setPasswordQuestionToTable(user,value);
        }if(myChoiceBox.getValue().equals("QUESTION ANSWER")){
            user.setAnsQuestion(value);
            myLabel2.setText("QUESTION ANSWER CHANGED SUCCESSFULLY");
            UpdateSqlTable.setAnswerOfPasswordQuestionToTable(user,value);
        }if(myChoiceBox.getValue().equals("BIO")){

            user.setBio(value);
            myLabel2.setText("BIO CHANGED SUCCESSFULLY");
            UpdateSqlTable.setBioUserToTable(user,value);
        }if(myChoiceBox.getValue().equals("TYPE")){

            user.setGender(value);
            UpdateSqlTable.setGenderToTable(user,value);
            myLabel2.setText("TYPE CHANGED SUCCESSFULLY");
        }
        if(myChoiceBox.getValue().equals("ADD TO GROUP ABILITY")){
            user.setAddToGroupAbility(Boolean.valueOf(value));
            UpdateSqlTable.setAddToGroupAbilityToTable(user, Boolean.parseBoolean(value));
            myLabel2.setText("ADD TO GROUP ABILITY CHANGED SUCCESSFULLY");
        }
        if(myChoiceBox.getValue().equals("CHANGE PROFILE PHOTO")){
            user.setPhotoNameFromImageFolder(value);
            UpdateSqlTable.setProfilePhotoToTable(user,value);
            myLabel2.setText("PROFILE PHOTO CHANGED SUCCESSFULLY");
        }
    }
    private User user= Controller.user;
    private String value;
    @FXML
    void getText(ActionEvent event) throws SQLException, ClassNotFoundException {
         value=myTextField.getText();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().add("USERNAME");
        myChoiceBox.getItems().add("ID");
        myChoiceBox.getItems().add("PASSWORD");
        myChoiceBox.getItems().add("PASSWORD HINT");
        myChoiceBox.getItems().add("PASSWORD QUESTION");
        myChoiceBox.getItems().add("QUESTION ANSWER");
        myChoiceBox.getItems().add("BIO");
        myChoiceBox.getItems().add("TYPE");
        myChoiceBox.getItems().add("ADD TO GROUP ABILITY");
        myChoiceBox.getItems().add("CHANGE PROFILE PHOTO");
        myChoiceBox.setOnAction(this::getValue);
    }
    private void getValue(Event event) {
        String temp=myChoiceBox.getValue();
        if(temp.equals("CHANGE PROFILE PHOTO")){
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            //final DirectoryChooser directoryChooser=new DirectoryChooser();
            Stage stage=(Stage) myGridPane.getScene().getWindow();
            File file=  fileChooser.showOpenDialog(stage);
            if(file!=null){
                myTextField.setText(file.getAbsolutePath());
                Image image = new Image(file.toURI().toString());
                user.setPhotoNameFromImageFolder(file.toURI().toString());
                myImageView.setImage(image);
            }
            myLabel1.setText("ADDRESS OF NEW PHOTO");
        }
        else {
            myLabel1.setText("ENTER NEW "+temp);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
