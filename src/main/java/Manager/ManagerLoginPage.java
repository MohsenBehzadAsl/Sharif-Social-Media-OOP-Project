package Manager;

import DataBase.DataBase;
import ShowClass.ShowHomepage;
import component.User;

import java.sql.SQLException;
import java.util.Scanner;

public class ManagerLoginPage {
    DataBase dataBase=new DataBase();
    Scanner in=new Scanner(System.in);
    public ManagerLoginPage(Scanner in){
        this.in=in;
    }
    public boolean validPassword(String password){
        if (!password.matches("^\\S{8,}$")){
            System.out.println("invalid password");
            return false;
        }
        else if (!password.matches("^(.*)(\\d+)(.*)$")){
            System.out.println("invalid password");
            return false;
        }
        else if (!password.matches("^(.*)([a-zA-Z]+)(.*)$")){
            System.out.println("invalid password");
            return false;
        }
        return true;
    }
    public boolean validID(String id){
        if (dataBase.getUserIDs().contains(id)){
            System.out.println("database have this id :(");
            return true;
        }
        return false;
    }
    public boolean checkBack(String back){
        if (back.equalsIgnoreCase("Back")){
            return true;
        }
        return false;
    }
    public int checkLogin(String id , String password) throws SQLException, ClassNotFoundException {  // 0 means id doesnt exist   // -1 means incorrect password  // 1 everthing is valid
        if( ! dataBase.getUserIDs().contains(id)){
            return 0;
        }
        User user= getUserWithID(id);
        if (user.getPassword().equals(password)){
            ShowHomepage showHomepage= new ShowHomepage(user,in);
            showHomepage.main();
            return 1;
        }
        return -1;
    }
    public boolean checkAnswer(String id,String answer){
        User user= getUserWithID(id);
        if (user.getAnsQuestion().equals(answer)){
            return true;
        }
        return false;
    }
    public String getPasswordHint(String id){
        User user= getUserWithID(id);
        return user.getPasswordHint();
    }
    public String getSecurityQuestion(String id){
        User user= getUserWithID(id);
        return user.getQuestion();
    }
    public User getUserWithID(String id){
        return dataBase.getUsers().get(dataBase.getUserIDs().indexOf(id));
    }
    public void setNewPassword(String id, String newPassword) throws SQLException, ClassNotFoundException {
        User user= getUserWithID(id);
        user.setPassword(newPassword);
        ShowHomepage showHomepage= new ShowHomepage(user,in);
        showHomepage.main();
    }
}
