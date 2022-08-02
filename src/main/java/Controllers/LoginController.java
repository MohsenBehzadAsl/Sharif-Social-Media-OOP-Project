package Controllers;

import Controllers.ForgotPasswordController;
import Controllers.SignUpController;
import DataBase.DataBase;
import Manager.ManagerLoginPage;
import View.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.sql.SQLException;

public class LoginController {


    int indexNumberOfInvalidPassword=0;


    @FXML
    private TextField idTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private GridPane right;

    ManagerLoginPage managerLoginPage=new ManagerLoginPage();

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

    public void forgetPassword(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (idTextField.getText().isEmpty()){

            Controller.changeTextFieldColor(idTextField,"Please enter your id to show Forgot Password Page.","#ec1a1a",true,true);
        }else if(managerLoginPage.checkLogin(idTextField.getText(),"----")==0){
            Controller.changeTextFieldColor(idTextField,"there is no ID: "+idTextField.getText(),"#ec1a1a",true,true);
        }else {
            right.getChildren().clear();
            right.getRowConstraints().removeAll();
            right.getColumnConstraints().removeAll();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ForgotPassword.fxml"));
            GridPane gridPane = fxmlLoader.load();

            ForgotPasswordController forgotPasswordController = fxmlLoader.getController();
            forgotPasswordController.user=DataBase.getUserWithId(idTextField.getText());
            forgotPasswordController.start();
            forgotPasswordController.right = right;
            right.add(gridPane, 0, 0);




        }



    }

    public void exit(ActionEvent actionEvent) {
        Controller.stage.close();
    }

    public void logIn(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
//        System.out.println("_____");
//        System.out.println("Controller.stage.getWidth()= "+Controller.stage.getWidth());
//        System.out.println("Controller.stage.getHeight()= "+Controller.stage.getHeight());



        String id= idTextField.getText();
        String password=passwordTextField.getText();


        if(managerLoginPage.checkLogin(id,password)==1){
            Controller.showHomePage(id);
        }else if(managerLoginPage.checkLogin(id,password)==0){
            Controller.changeTextFieldColor(idTextField,"Desired id not found.", "#ec1a1a",true,true);
        }else if(managerLoginPage.checkLogin(id,password)==-1){
            //forgetPassword(id);
            //break;


            indexNumberOfInvalidPassword++;

            if (indexNumberOfInvalidPassword>=4){
                Controller.changeTextFieldColor(passwordTextField,"Hint: "+DataBase.getUserWithId(id).getPasswordHint(),"#da42ba",false,true);
            }else {
                Controller.changeTextFieldColor(passwordTextField,"Invalid Password", "#ec1a1a",true,true);
            }
        }

    }


}
