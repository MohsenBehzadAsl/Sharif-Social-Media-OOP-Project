package DataBase;

import ShowClass.ShowHomepage;
import component.Comment;
import component.Post;
import component.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    ShowHomepage showHomepage;
    private static ArrayList<User> users;
    private static ArrayList<String> userIDs;
    private static ArrayList<Post> posts;


    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBase.password = password;
    }

    private static ArrayList<Comment> comments = new ArrayList<>();
    public static ArrayList<Comment> getComments() {
        return comments;
    }

    public static void setComments(ArrayList<Comment> comments) {
        DataBase.comments = comments;
    }

    public static String password="Mohammad1381";


    public DataBase(){
        if (users==null){
            users=new ArrayList<User>();
            userIDs=new ArrayList<String>();
            posts=new ArrayList<Post>();
        }
    }

    public static User getUserWithId(String input) {
        User user=null;
        boolean find=false;
        for (int i=0;i<users.size();i++){
            if (users.get(i).getId().equals(input)){
                find=true;
                user=users.get(i);
                break;
            }
        }
        return user;
    }

    public static void updateComments() {
        for (int i=comments.size()-1;i>=0;i--){
            //ManagerShow.showCommentInMyHomePost(comments.get(i).getSender(),comments.get(i));
            for (Post dataBasePost : posts) {
                if(dataBasePost.getPostId().equals(comments.get(i).getCommentOfPost().getPostId())){
                    dataBasePost.addComment(comments.get(i));
                }
            }
        }
    }

    public void initializeAddUser(String userName, String id, String password, String passwordHint, Scanner in , String type,String question,String ansQuestion) {
        users.add(new User(userName, id, password, passwordHint, type, question, ansQuestion));
        userIDs.add(id);
    }
        public static void addUser(String userName, String id, String password, String passwordHint,  String type,String question,String ansQuestion) throws SQLException, ClassNotFoundException {
            User User=new User(userName,id,password,passwordHint,type,question,ansQuestion);
            //  System.out.printf("*****************uigyujgayfdgdsdfs");
            addUserToTable(User);
            users.add(User);
            userIDs.add(id);
            System.out.println("i am in addUser and add "+userName+" :)");
            //showHomepage=new ShowHomepage(users.get(users.size()-1),in);
            //showHomepage.main();
        }
    public static void addUserToTable(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "insert into users(userName,id,password,passwordHint,gender,question,ansQuestion)" +
                        "value (?,?,?,?,?,?,?,?)"
        );//yedone alamat soal va boolean addToGroupAbility
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getId());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getPasswordHint());
        preparedStatement.setString(5, user.getType());
        preparedStatement.setString(6, user.getQuestion());
        preparedStatement.setString(7, user.getAnsQuestion());
        preparedStatement.setString(8,String.valueOf(user.getAddToGroupAbility()));
        preparedStatement.executeUpdate();
    }
    public static void setNewPasswordInTable(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users SET password=(?) WHERE id=(?)"
        );
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(1, user.getId());
        preparedStatement.executeUpdate();
    }
    public static ArrayList<User> getUsers() {

        return users;
    }
    public ArrayList<String> getUserIDs(){
        return userIDs;
    }
    public static ArrayList<Post> getPosts() {
        return posts;
    }
    public static void add(Post post){
        posts.add(post);
    }
    public ShowHomepage getShowHomepage() {
        return showHomepage;
    }

    public void setShowHomepage(ShowHomepage showHomepage) {
        this.showHomepage = showHomepage;
    }

    public static void setUsers(ArrayList<User> users) {
        DataBase.users = users;
    }

    public static void setUserIDs(ArrayList<String> userIDs) {
        DataBase.userIDs = userIDs;
    }

    public static void setPosts(ArrayList<Post> posts) {
        DataBase.posts = posts;
    }
}
