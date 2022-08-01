package ShowClass;

import DataBase.DataBase;
import Manager.ManagerLoginPage;

import java.sql.SQLException;
import java.util.Scanner;

public class ShowStartPage {
    String input=new String();
    DataBase dataBase=new DataBase();
    Scanner in;
    ManagerLoginPage managerLoginPage;

    public ShowStartPage(Scanner in) throws SQLException, ClassNotFoundException {
        this.in=in;
        managerLoginPage=new ManagerLoginPage();
     //   initialize();
    }
/*    public void initialize() throws SQLException, ClassNotFoundException {
            for (int k = 1; k <= 20; k++) {
                if (k <= 10) {
                    dataBase.initializeAddUser("hamid" + k, "hamid" + k, "400sharif", "BiaDaneshgah", in, "Normal", "biamDaneshgah?", "baleee");
                    dataBase.initializeAddUser("mohsen" + k, "mohsen" + k, "400sharif", "BiaDaneshgah", in, "Normal", "biamDaneshgah?", "baleee");
                    dataBase.initializeAddUser("mohammad" + k, "mohammad" + k, "400sharif", "BiaDaneshgah", in, "Normal", "biamDaneshgah?", "baleee");
                } else {
                    dataBase.initializeAddUser("hamid" + k, "hamid" + k, "400sharif", "BiaDaneshgah", in, "Business", "biamDaneshgah?", "baleee");
                    dataBase.initializeAddUser("mohsen" + k, "mohsen" + k, "400sharif", "BiaDaneshgah", in, "Business", "biamDaneshgah?", "baleee");
                    dataBase.initializeAddUser("mohammad" + k, "mohammad" + k, "400sharif", "BiaDaneshgah", in, "Business", "biamDaneshgah?", "baleee");
                }
            }

        if (dataBase.getPosts().isEmpty()) {
            for (int k = 0; k < 60; k++) {
                Post post = new Post(dataBase.getUsers().get(k), "text", "Hello,I'm " + dataBase.getUsers().get(k).getUserName(), true);
                dataBase.getUsers().get(k).getPosts().add(post);
                dataBase.getPosts().add(post);
            }
        }
    }*/
    public void processor() throws SQLException, ClassNotFoundException {
        boolean exit=false;
        while (!exit){
            showLoginMenu();
            input=in.nextLine();
            if (!input.matches("\\w{1}")){
                System.out.println("invalid input");
            }
            else if (input.equals("1")){
                showSignIn();
               
            }
            else if (input.equals("2")){
                showLogIn();
                
            }
            else if (input.equals("3")){
                System.out.println("exit :)");
                exit=true;
            }
            else {
                System.out.println("invalid input");
            }
        }
        System.exit(0);
    }
    public void showLoginMenu(){
        System.out.print("|||||||||||||||||||||||||||||\n" +
                "|      login page           |\n" +
                "|                           |\n" +
                "|                           |\n" +
                "|pls select your choice :   |\n" +
                "|1-signup                   |\n" +
                "|2-login                    |\n" +
                "|3-exit                     |\n" +
                "|___________________________|\n"+
                "input: ");
    }
    public void showSignIn() throws SQLException, ClassNotFoundException {
        boolean running=true;
        String username=new String();
        String id=new String();
        String password=new String();
        String password2=new String();
        String passwordHint=new String();
        String SecurityQuestion=new String();
        String Answer=new String();
        main: while (running) {
            System.out.println("||||||||||||||||||||||||||||||\n" +
                               "|           Signup           |\n" +
                               "|                            |\n" +
                               "|Username:                   |\n" +
                               "|Id:                         |\n" +
                               "|Password:                   |\n" +
                               "|Password Hint:              |\n" +
                               "|Security Question:          |\n" +
                               "|Answer:                     |\n" +
                               "|                            |\n" +
                               "|if you want Back type Back  |\n" +
                               "|____________________________|");
            System.out.print("Username: ");username=in.nextLine();
            if (managerLoginPage.checkBack(username)){
                break;
            }
            while (true) {
                System.out.print("Id: ");
                id = in.nextLine();
                if (managerLoginPage.checkBack(id)){
                    break;
                }
                if (!managerLoginPage.validID(id)){
                    break;
                }
            }
            if (managerLoginPage.checkBack(id)){
                break;
            }

            while (true) {
                while (true) {
                    System.out.print("Password: ");
                    password = in.nextLine();
                    if (managerLoginPage.checkBack(password)) {
                        break main;
                    }
                    if (managerLoginPage.validPassword(password)) {
                        break;
                    }
                }
                System.out.print("Enter Password again: ");
                password2 = in.nextLine();
                if (managerLoginPage.checkBack(password2)) {
                    break main;
                }
                if (password2.equals(password)){
                    break;
                }
                System.out.println("you have entered your check password incorrectly.\n please enter your password");
            }
            if (managerLoginPage.checkBack(password)){
                break;
            }
            System.out.print("Password Hint: ");passwordHint=in.nextLine();
            if (managerLoginPage.checkBack(passwordHint)){
                break;
            }
            System.out.print("Security Question: ");SecurityQuestion=in.nextLine();
            if (managerLoginPage.checkBack(SecurityQuestion)){
                break;
            }
            System.out.print("Answer: ");Answer=in.nextLine();
            if (managerLoginPage.checkBack(Answer)){
                break;
            }
            running=false;
            showSelectUserType(username,id,password,passwordHint,SecurityQuestion,Answer);
        }
    }
    public void showLogIn() throws SQLException, ClassNotFoundException {
        boolean running=true;

        String id=new String();
        String password=new String();
        String Answer=new String();


        while (running) {
            System.out.println("||||||||||||||||||||||||||||||\n" +
                    "|           Login            |\n" +
                    "|                            |\n" +
                    "|Id:                         |\n" +
                    "|Password:                   |\n" +
                    "|Forget Password?(Enter -1)  |\n" +
                    "|                            |\n" +
                    "|if you want Back type Back  |\n" +
                    "|____________________________|");
            System.out.print("Id: ");id=in.nextLine();
            if (managerLoginPage.checkBack(id)){
                break;
            }
            if (id.equals("-1")){
                getIdtoForgetPassword();
                break;
            }
            System.out.print("Password: ");password=in.nextLine();
            if (managerLoginPage.checkBack(password)){
                break;
            }
            if (id.equals("-1")){
                forgetPassword(id);
                break;
            }
            if(managerLoginPage.checkLogin(id,password)==1){
                running=false;
            }else if(managerLoginPage.checkLogin(id,password)==0){
                System.out.println("Desired id not found.");
            }else if(managerLoginPage.checkLogin(id,password)==-1){
                forgetPassword(id);
                break;
            }

        }
    }
    private void getIdtoForgetPassword() throws SQLException, ClassNotFoundException {
        System.out.println("Warning: At first,please enter your id.");
        String id=new String();
        boolean running=true;
        while (running){
            System.out.print("Id: ");id=in.nextLine();
            if (managerLoginPage.checkBack(id)){
                showLogIn();
                break;
            }
            if(managerLoginPage.checkLogin(id,"111")==0){
                System.out.print("Desired id not found.");
            }else{
                forgetPassword(id);
                break;
            }
        }

    }
    public void forgetPassword(String id) throws SQLException, ClassNotFoundException {
        boolean running=true;

        String password=new String();
        String Answer=new String();


        while (running) {
            System.out.print("||||||||||||||||||||||||||||||\n" +
                    "|       Forget Password      |\n" +
                    "|                            |\n" +
                    "| ");
            System.out.print("id: "+id);
            for (int i=1;i<30-5-id.length()-1;i++){
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print(
                    "|                            |\n" +
                    "|pls select your choice:     |\n" +
                    "|1-PasswordHint              |\n" +
                    "|2-SecurityQuestion          |\n" +
                    "|3-back                      |\n" +
                    "|                            |\n"
            );
            System.out.println("|____________________________|\n"+
                    "input: ");

            input=in.nextLine();
            if (!input.matches("\\w{1}")){
                System.out.println("invalid input");
            }
            else if (input.equals("1")){
                AthenticateByPasswordHint(id);
                break;
            }
            else if (input.equals("2")){
                AthenticateBySecurityQuestion(id);
                break;
            }
            else if (input.equals("3")){
                System.out.println("we came back to loginPage");
                showLogIn();
                break;
            }
            else {
                System.out.println("invalid input");
            }

        }
    }
    public void AthenticateByPasswordHint(String id) throws SQLException, ClassNotFoundException {
        boolean running=true;
        String password=new String();
        String passwordHint = managerLoginPage.getPasswordHint(id);

        while (running) {
            System.out.print("||||||||||||||||||||||||||||||\n" +
                    "|       Password Hint        |\n" +
                    "|                            |\n" +
                    "| ");
            System.out.print("_" + id + "_");
            for (int i = 1; i < 30 - 3 - id.length() - 1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print("|  passwordHint:             |\n"
                    + "|  ");
            System.out.print(passwordHint);
            for (int i = 1; i < 30 - 1 - passwordHint.length() - 1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");


            System.out.print(
                            "|                            |\n" +
                            "|Passwrord:                  |\n" +
                            "|if you want Back type Back  |\n"

            );
            System.out.println("|____________________________|\n" +
                    "password: ");

            password = in.nextLine();
            if (managerLoginPage.checkBack(password)) {
                forgetPassword(id);
                break;
            }
            if (managerLoginPage.checkLogin(id, password) == 1) {
                running = false;
                break;
            }  else if (managerLoginPage.checkLogin(id, password) == -1) {
                System.out.println("Password is not valid.");
            }
        }

    }
    public void AthenticateBySecurityQuestion(String id) throws SQLException, ClassNotFoundException {
        boolean running=true;
        String answer=new String();
        String SecurityQuestion = managerLoginPage.getSecurityQuestion(id);

        while (running) {

            System.out.print("||||||||||||||||||||||||||||||\n" +
                               "|     Security Question      |\n" +
                    "|                            |\n" +
                    "| ");
            System.out.print("_" + id + "_");
            for (int i = 1; i < 30 - 3 - id.length() - 1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");


            System.out.print("|  SecurityQuestion:         |\n"
                    + "|  ");
            System.out.print(SecurityQuestion);
            for (int i = 1; i < 30 - 1 - SecurityQuestion.length() - 1; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");


            System.out.print(
                    "|                            |\n" +
                            "|answer:                     |\n" +
                            "|if you want Back type Back  |\n"

            );
            System.out.println("|____________________________|\n" +
                    "password: ");

            answer = in.nextLine();
            if (managerLoginPage.checkBack(answer)) {
                forgetPassword(id);
                break;
            }
            if (managerLoginPage.checkAnswer(id, answer)) {
                System.out.println("Your answer is correct, so you can change your password to login your account.");
                setNewPassword(id);
                running = false;
                break;
            }
            System.out.println("Your answer is incorrect");
        }

    }
    private void setNewPassword(String id) throws SQLException, ClassNotFoundException {
        String newPassword=new String();
        String newPassword2=new String();
        while (true) {
            while (true) {
                System.out.print("New Password: ");
                newPassword = in.nextLine();
                if (managerLoginPage.validPassword(newPassword)) {
                    break;
                }
            }
            System.out.print("Enter New Password again: ");
            newPassword2 = in.nextLine();
            if (newPassword2.equals(newPassword)){
                break;
            }
            System.out.println("you have entered your check password incorrectly.\n please enter your password");
        }
        System.out.println("password changed successfully.");
        managerLoginPage.setNewPassword(id,newPassword);

    }
    public void showSelectUserType(String userName, String id, String password, String passwordHint,String question,String ansQuestion) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            System.out.println("||||||||||||||||||||||||||||||\n" +
                    "|           Usertype         |\n" +
                    "|pls select your choice:     |\n" +
                    "|1-Normal User               |\n" +
                    "|2-Business User             |\n" +
                    "|3-Back                      |\n" +
                    "|____________________________|");
            System.out.print("input: ");
            input=in.nextLine();
            if (!input.matches("\\w{1}")){
                System.out.println("invalid input");
            }
            else if (input.equals("1")){
                running=false;
                String gender="Normal";
                dataBase.addUser(userName,id,password,passwordHint,in,gender,question,ansQuestion);
            }
            else if (input.equals("2")){
                dataBase.addUser(userName,id,password,passwordHint,in,"Business",question,ansQuestion);
                running=false;
            }
            else if (input.equals("3")){
                System.out.println("bye from showSelectUserType");
                running=false;
            }
            else {
                System.out.println("invalid input");
            }
        }
    }
}
