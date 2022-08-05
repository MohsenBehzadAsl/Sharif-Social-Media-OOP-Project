package View;

import DataBase.DataBase;
import DataBase.UserRepository;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

//        for (int i=0;i<DataBase.getUsers().size();i++){
//            for (int j=i+1;j<DataBase.getUsers().size();j++){
//                User.addFollowerAndFollowingToTable(DataBase.getUsers().get(i),DataBase.getUsers().get(j));
//            }
//        }
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
