package Controllers.SettingControllers;

import Controllers.UserInformationController;
import DataBase.UpdateSqlTable;
import View.Controller;
import component.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static View.Controller.mainPageController;

public class SettingController implements Initializable {
    @FXML
    private ChoiceBox<String> myChangeInformationChoiceBox;
    @FXML
    private Button myChangeProfileButton;
    @FXML
    private ImageView myImageView;
    @FXML
    private Label myLabel;
    @FXML
    private Button mySubmitButton;
    @FXML
    private TextField myTextField;
    @FXML
    private GridPane myGridPane;
    @FXML
    private ToggleGroup background;
    @FXML
    private ToggleGroup item;
    @FXML
    private ColorPicker myColorPicker;
    @FXML
    private RadioButton myNoneRadioButton;
    @FXML
    private RadioButton myPinkRadioButton;
    @FXML
    private RadioButton mySilverRadioButton;
    @FXML
    private RadioButton myCustomRadioButton;
    @FXML
    private RadioButton mySkyRadioButton;
    @FXML
    private RadioButton myDarkRadioButton;
    @FXML
    void submit(ActionEvent event) throws SQLException, ClassNotFoundException {
        value=myTextField.getText();
        //  myLabel2.setText("..... CHANGED SUCCESSFULLY");
        //  System.out.println(myChoiceBox.getValue());
        if(myChangeInformationChoiceBox.getValue().equals("USERNAME")){
            // user.setUserName(value);

            //    UpdateSqlTable.setUserNameToTable(user,value);
        }
        if(myChangeInformationChoiceBox.getValue().equals("ID")){
            //    user.setId(value);

            //UpdateSqlTable.setIdToTable();
        }
        if(myChangeInformationChoiceBox.getValue().equals("PASSWORD")){
            //  user.setPassword(value);

            //   UpdateSqlTable.setPasswordToTable(user,value);
        }if(myChangeInformationChoiceBox.getValue().equals("PASSWORD HINT")){
            //    user.setPasswordHint(value);

            //   UpdateSqlTable.setPasswordHintToTable(user,value);
        }if(myChangeInformationChoiceBox.getValue().equals("PASSWORD QUESTION")){
            //    user.setQuestion(value);

            //   UpdateSqlTable.setPasswordQuestionToTable(user,value);
        }if(myChangeInformationChoiceBox.getValue().equals("QUESTION ANSWER")){
            //    user.setAnsQuestion(value);

            //   UpdateSqlTable.setAnswerOfPasswordQuestionToTable(user,value);
        }if(myChangeInformationChoiceBox.getValue().equals("BIO")){

            user.setBio(value);

            //     UpdateSqlTable.setBioUserToTable(user,value);
        }if(myChangeInformationChoiceBox.getValue().equals("TYPE")){
            user.setGender(value);
            //     UpdateSqlTable.setGenderToTable(user,value);

        }
        if(myChangeInformationChoiceBox.getValue().equals("ADD TO GROUP ABILITY")){
            user.setAddToGroupAbility(Boolean.valueOf(value));
            //    UpdateSqlTable.setAddToGroupAbilityToTable(user, Boolean.parseBoolean(value));

        }
        if(myChangeInformationChoiceBox.getValue().equals("CHANGE PROFILE PHOTO")){
            user.setPhotoNameFromImageFolder(value);
            //     UpdateSqlTable.setProfilePhotoToTable(user,value);

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
        myChangeInformationChoiceBox.getItems().add("USERNAME");
        myChangeInformationChoiceBox.getItems().add("ID");
        myChangeInformationChoiceBox.getItems().add("PASSWORD");
        myChangeInformationChoiceBox.getItems().add("PASSWORD HINT");
        myChangeInformationChoiceBox.getItems().add("PASSWORD QUESTION");
        myChangeInformationChoiceBox.getItems().add("QUESTION ANSWER");
        myChangeInformationChoiceBox.getItems().add("BIO");
        myChangeInformationChoiceBox.getItems().add("TYPE");
        myChangeInformationChoiceBox.getItems().add("ADD TO GROUP ABILITY");
        //  myChangeInformationChoiceBox.getItems().add("CHANGE PROFILE PHOTO");
       /* myChangeItemChoiceBox.getItems().add("SILVER");
        myChangeItemChoiceBox.getItems().add("PINK");
        myChangeBackgroundChoiceBox.getItems().add("SKY");
        myChangeBackgroundChoiceBox.getItems().add("DARK");
        myChangeBackgroundChoiceBox.setOnAction(this::background);*/
        myChangeInformationChoiceBox.setOnAction(this::information);
        /*myChangeItemChoiceBox.setOnAction(this::item);*/
    }
    public void information(Event event)  {
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/UserInformationSetting.fxml"));
            Parent parent = fxmlLoader.load();
            //   value=myTextField.getText();
            UserInformationController userInformationController = fxmlLoader.getController();
            //  myLabel2.setText("..... CHANGED SUCCESSFULLY");
            //  System.out.println(myChoiceBox.getValue());
            if(myChangeInformationChoiceBox.getValue().equals("USERNAME")){
                userInformationController.setInformation("USERNAME");
                // user.setUserName(value);

                //    UpdateSqlTable.setUserNameToTable(user,value);
            }
            if(myChangeInformationChoiceBox.getValue().equals("ID")){
                //    user.setId(value);
                userInformationController.setInformation("ID");
                //UpdateSqlTable.setIdToTable();
            }
            if(myChangeInformationChoiceBox.getValue().equals("PASSWORD")){
                //  user.setPassword(value);
                userInformationController.setInformation("PASSWORD");
                //   UpdateSqlTable.setPasswordToTable(user,value);
            }if(myChangeInformationChoiceBox.getValue().equals("PASSWORD HINT")){
                //    user.setPasswordHint(value);
                userInformationController.setInformation("PASSWORD HINT");
                //   UpdateSqlTable.setPasswordHintToTable(user,value);
            }if(myChangeInformationChoiceBox.getValue().equals("PASSWORD QUESTION")){
                //    user.setQuestion(value);
                userInformationController.setInformation("PASSWORD QUESTION");
                //   UpdateSqlTable.setPasswordQuestionToTable(user,value);
            }if(myChangeInformationChoiceBox.getValue().equals("QUESTION ANSWER")){
                //    user.setAnsQuestion(value);
                userInformationController.setInformation("QUESTION ANSWER");
                //   UpdateSqlTable.setAnswerOfPasswordQuestionToTable(user,value);
            }if(myChangeInformationChoiceBox.getValue().equals("BIO")){

                // user.setBio(value);
                userInformationController.setInformation("BIO");
                //     UpdateSqlTable.setBioUserToTable(user,value);
            }if(myChangeInformationChoiceBox.getValue().equals("TYPE")){
                //    user.setGender(value);
                //     UpdateSqlTable.setGenderToTable(user,value);
                userInformationController.setInformation("TYPE");
            }
            if(myChangeInformationChoiceBox.getValue().equals("ADD TO GROUP ABILITY")){
                //  user.setAddToGroupAbility(Boolean.valueOf(value));
                //    UpdateSqlTable.setAddToGroupAbilityToTable(user, Boolean.parseBoolean(value));
                userInformationController.setInformation("ADD TO GROUP ABILITY");
            }
            Scene scene = new Scene(parent, 328, 400);
            String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(Controller.stage.getX()+Controller.stage.getWidth()/2-328/2);
            stage.setY(Controller.stage.getY()+Controller.stage.getHeight()/2-400/2);
            stage.setResizable(false);
            userInformationController.lable();
            stage.show();
            userInformationController.stage=stage;
        }
        catch (IOException ioException){
            System.out.println(ioException);
        }
    }
    public void setMyChangeProfileButton(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage stage=(Stage) myGridPane.getScene().getWindow();
        File file=  fileChooser.showOpenDialog(stage);
        if(file!=null){
            myTextField.setText(file.getAbsolutePath());
            Image image = new Image(file.toURI().toString().replace("^file:/",""));
            user.setPhotoNameFromImageFolder(file.toURI().toString().replace("^file:/",""));
            myImageView.setImage(image);
            myLabel.setText("PROFILE PHOTO CHANGED SUCCESSFULLY");
            mainPageController.update();
        }
    }
    public void getBackground(ActionEvent actionEvent) throws IOException {
        if(myDarkRadioButton.isSelected()){
            Controller.scene.getStylesheets().clear();
            Controller.scene.getStylesheets().removeAll();
            if(myNoneRadioButton.isSelected()){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
            } else if (mySilverRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            } else if (myPinkRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/PINK.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            }
        } else if (mySkyRadioButton.isSelected()) {
            Controller.scene.getStylesheets().clear();
            Controller.scene.getStylesheets().removeAll();
            if(myNoneRadioButton.isSelected()){
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
            } else if (mySilverRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            } else if (myPinkRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                Controller.scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            }
        }
        /*else if(myCustomRadioButton.isSelected()){

            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ColorPicker.fxml"));
            Parent parent = fxmlLoader.load();
            ColorPickerController colorPickerController = fxmlLoader.getController();
            Scene scene = new Scene(parent, 328, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(Controller.stage.getX()+Controller.stage.getWidth()/2-328/2);
            stage.setY(Controller.stage.getY()+Controller.stage.getHeight()/2-400/2);
            stage.setResizable(false);
            stage.show();
            colorPickerController.stage=stage;
        }*/
    }
    public void custom(ActionEvent actionEvent){
        Color color=myColorPicker.getValue();
        String   base=String.valueOf(color).replaceAll("0x","#");
        base=base.replaceAll("ff$","");
        String cssBase=Controller.class.getResource("/CSS/CUSTOM.css").toExternalForm();
        //String CSSButtonHober=Controller.class.getResource("/CSS/CSSButtonHober.css").toExternalForm();
        //  String css=Controller.class.getResource("/CSS/CUSTOM.css").toExternalForm();
        Controller.scene.getStylesheets().clear();
        Controller.scene.getStylesheets().removeAll();
        Controller.scene.getStylesheets().add(cssBase);
        Controller.scene.getRoot().setStyle("-root-fill:" + base + " ;");
    }

    public void getItem(ActionEvent actionEvent){
        if(myNoneRadioButton.isSelected()){
            Controller.scene.getStylesheets().clear();
            Controller.scene.getStylesheets().removeAll();
            if(myDarkRadioButton.isSelected()){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
            } else if (mySkyRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
            }
        } else if(mySilverRadioButton.isSelected()){
            Controller.scene.getStylesheets().clear();
            Controller.scene.getStylesheets().removeAll();
            if(myDarkRadioButton.isSelected()){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            } else if (mySkyRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
                Controller. scene.getStylesheets().add(css1);
            }
        } else if (myPinkRadioButton.isSelected()) {
            Controller.scene.getStylesheets().clear();
            Controller.scene.getStylesheets().removeAll();
            if(myDarkRadioButton.isSelected()){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/PINK.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
                Controller.scene.getStylesheets().add(css1);
            } else if (mySkyRadioButton.isSelected()) {
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                String css1=Controller.class.getResource("/CSS/PINK.css").toExternalForm();
                Controller. scene.getStylesheets().add(css);
                Controller. scene.getStylesheets().add(css1);
            }
        }







    }



    /*  public void background(Event event)  {
          if(myChangeBackgroundChoiceBox.getValue().equals("SKY")){
              String css=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
              if(myChangeItemChoiceBox.getValue()==null){
              }
              else if(!(myChangeItemChoiceBox.getValue().equals("SILVER"))){
                  Controller.scene.getStylesheets().clear();
                  Controller.scene.getStylesheets().removeAll();
                  String css1=Controller.class.getResource("/CSS/PINK.css").toExternalForm();
                 Controller. scene.getStylesheets().add(css);
                 Controller. scene.getStylesheets().add(css1);
              }
              else if(!(myChangeItemChoiceBox.getValue().equals("PINK"))){
                  Controller.scene.getStylesheets().clear();
                  Controller.scene.getStylesheets().removeAll();

                  String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                  Controller.  scene.getStylesheets().add(css);
                  Controller.scene.getStylesheets().add(css1);
              }
          }
          else if(myChangeBackgroundChoiceBox.getValue().equals("DARK")){
              String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
              if(myChangeItemChoiceBox.getValue()==null){
              }
              else if(!(myChangeItemChoiceBox.getValue().equals("SILVER"))){
                  Controller.scene.getStylesheets().clear();
                  Controller. scene.getStylesheets().removeAll();
                  String css1=Controller.class.getResource("/CSS/PINK.css").toExternalForm();
                  Controller.scene.getStylesheets().add(css);
                  Controller.scene.getStylesheets().add(css1);
              }
              else if(!(myChangeItemChoiceBox.getValue().equals("PINK"))){
                  Controller.scene.getStylesheets().clear();
                  Controller. scene.getStylesheets().removeAll();
                  String css1=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
                  Controller.scene.getStylesheets().add(css);
                  Controller.scene.getStylesheets().add(css1);
              }
          }
      }*/
  /*  public void item(Event event)  {
        if(myChangeItemChoiceBox.getValue().equals("SILVER")){
            String css=Controller.class.getResource("/CSS/SILVER.css").toExternalForm();
            if(myChangeBackgroundChoiceBox.getValue()==null){
            }
           else if(myChangeBackgroundChoiceBox.getValue().equals("SKY")){
                Controller.scene.getStylesheets().clear();
                Controller. scene.getStylesheets().removeAll();
                String css1=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                Controller.scene.getStylesheets().add(css1);
                Controller.scene.getStylesheets().add(css);
            }
            else if(myChangeBackgroundChoiceBox.getValue().equals("DARK")){
                Controller.scene.getStylesheets().clear();
                Controller. scene.getStylesheets().removeAll();
                String css1=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                Controller.scene.getStylesheets().add(css1);
                Controller.scene.getStylesheets().add(css);
            }
        }
        else if(myChangeItemChoiceBox.getValue().equals("PINK")){

            String css=Controller.class.getResource("/CSS/DARK.css").toExternalForm();

            if(myChangeBackgroundChoiceBox.getValue()==null){

            }
           else if(myChangeBackgroundChoiceBox.getValue().equals("SKY")){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();

                String css1=Controller.class.getResource("/CSS/SKY.css").toExternalForm();
                Controller.scene.getStylesheets().add(css1);
                Controller.scene.getStylesheets().add(css);
            }
            else if(myChangeBackgroundChoiceBox.getValue().equals("DARK")){
                Controller.scene.getStylesheets().clear();
                Controller.scene.getStylesheets().removeAll();
                String css1=Controller.class.getResource("/CSS/DARK.css").toExternalForm();
                Controller.scene.getStylesheets().add(css1);
                Controller.scene.getStylesheets().add(css);
            }
        }
    }
*/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
