package View;

import DataBase.DataBase;
import DataBase.UserRepository;
import ShowClass.ShowStartPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class Main extends Application {

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
        Scanner in=new Scanner(System.in);
        //ShowStartPage showLoginPage=new ShowStartPage(in);
        //showLoginPage.processor();



//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml\\Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();


        this.stage=stage;
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("EDU");
        Controller.stage=stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
