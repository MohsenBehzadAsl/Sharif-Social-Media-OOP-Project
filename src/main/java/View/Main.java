package View;

import Controllers.ForgotPasswordController;
import Controllers.SignUpController;
import DataBase.DataBase;
import DataBase.UserRepository;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main extends Application {

    @FXML
    private TextField idTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private GridPane right;


    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                DataBase.password);
        UserRepository.getData().createTable(connection);
        UserRepository.getData().initializeuser(connection);
//        Scanner in=new Scanner(System.in);
        //ShowStartPage showLoginPage=new ShowStartPage(in);
        //showLoginPage.processor();
       // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        this.stage=stage;
        stage.setMinHeight(636);
        stage.setMinWidth(1095);
        Parent pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(pane);
        Controller.startPage=scene;

        stage.setScene(scene);
        stage.setTitle("EDU");
        Controller.stage=stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
