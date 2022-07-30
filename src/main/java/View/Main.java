package View;

import Controllers.ForgotPasswordController;
import Controllers.SignUpController;
import DataBase.DataBase;
import DataBase.UserRepository;
import ShowClass.ShowStartPage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class Main extends Application {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private VBox right;


    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection= DriverManager.
//                getConnection("jdbc:mysql://localhost:3306/joel"
//                        , "root",
//                        DataBase.password);
//        UserRepository.getData().createTable(connection);
//        UserRepository.getData().initializeuser(connection);
//        Scanner in=new Scanner(System.in);
        //ShowStartPage showLoginPage=new ShowStartPage(in);
        //showLoginPage.processor();



       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();


        this.stage=stage;
        BorderPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("EDU");
        Controller.stage=stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        right.getChildren().clear();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
        System.out.println(getClass().getResource("/fxml/SignUp.fxml"));
        VBox vBox=fxmlLoader.load();
        SignUpController signUpController=fxmlLoader.getController();
        signUpController.right=right;
        right.getChildren().add(vBox);
    }

    public void forgetPassword(ActionEvent actionEvent) throws IOException {
        right.getChildren().clear();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ForgotPassword.fxml"));
        System.out.println(getClass().getResource("/fxml/ForgotPassword.fxml"));
        VBox vBox=fxmlLoader.load();
        ForgotPasswordController forgotPasswordController=fxmlLoader.getController();
        forgotPasswordController.right=right;
        right.getChildren().add(vBox);
    }

    public void exit(ActionEvent actionEvent) {
        Controller.stage.close();
    }

    public void logIn(ActionEvent actionEvent) {
    }
}
