package View;

import DataBase.DataBase;
import DataBase.UserRepository;
import component.User;
import javafx.application.Application;
import javafx.css.CssParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;


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
        ArrayList<String> s=new ArrayList<>();
        UserRepository.getData().createTable(connection);
        System.out.println(String.valueOf(getClass().getResource("/CSS/DARK.css")).replaceAll("^file:/",""));
        File file=new File(String.valueOf(getClass().getResource("/CSS/newCssMain.css")).replaceAll("^file:/",""));
        Scanner scanner=new Scanner(file);
        while (scanner.hasNextLine()){
            s.add(scanner.nextLine());
            if (s.get(s.size()-1).matches("(.*)-fx-border-style:(.*)")){
                s.set(s.size()-1,"-fx-border-style: "+"Blue");
            }
        }
        for (int i=0;i<s.size();i++){
            System.out.println(s.get(i));
        }
        System.out.println("******_________***");
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write("hello");

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
