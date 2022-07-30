package component;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Post {
    private static int i=0;
    private String postId;
    private String isComment="post";
    private User sender;
    private String format=new String(); // img||text||...
    private String content=new String();
    private String gender=new String(); //Ad || normal
    private boolean commentAbility=true;
    private LocalDateTime date; //***
    private LinkedHashMap<User,LocalDateTime> views = new LinkedHashMap<User,LocalDateTime>();
    private LinkedHashMap<User,LocalDateTime> likes = new LinkedHashMap<User,LocalDateTime>();
    private ArrayList<Comment> comments=new ArrayList<Comment>();

    public Post(User sender, String format, String content, boolean commentAbility) throws SQLException, ClassNotFoundException {
        this.postId=String.valueOf(i);
        i++;
        this.sender = sender;
        this.format = format;
        this.content = content;
        this.commentAbility = commentAbility;
        this.date=LocalDateTime.now();
        this.gender=sender.getType();
        addPostToTable(sender,postId,format,content,commentAbility);
    }
    public Post(User sender, String format, String content, boolean commentAbility,boolean isComment) throws SQLException, ClassNotFoundException {
        this.postId=String.valueOf(i);
        i++;
        this.sender = sender;
        this.format = format;
        this.content = content;
        this.commentAbility = commentAbility;
        this.date=LocalDateTime.now();
        this.gender=sender.getType();
        this.isComment="comment";
        addPostToTable(sender,postId,format,content,commentAbility);
    }
    public void addPostToTable(User sender,String postId,String format,String content,boolean commentAbility) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO tweets  (senderId,postId,format,content,gender,sendTime,commentAbility,isComment) VALUES (?,?,?,?,?,?,?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
        );
        preparedStatement.setString(1, sender.getId());
        preparedStatement.setString(2, postId);
        preparedStatement.setString(3, format);
        preparedStatement.setString(4, content);
        preparedStatement.setString(5, sender.getType());
        preparedStatement.setString(6, String.valueOf(LocalDateTime.now()));
        preparedStatement.setString(7, String.valueOf(commentAbility));
        preparedStatement.setString(8,isComment);
        preparedStatement.executeUpdate();
    }
    //addLikeToTable(user,postId,likeTime);
    public void addLikeToTable(User liker,String postId,LocalDateTime likeTime) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO likes  (likerId,postId,likeTime) VALUES (?,?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
        );
        preparedStatement.setString(1,liker.getId());
        preparedStatement.setString(2, postId);
        preparedStatement.setString(3, String.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();
    }
    public void addViewToTable(User viewer,String postId,LocalDateTime viewTime) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO views  (viewerId,postId,viewTime) VALUES (?,?,?)"

        );
        preparedStatement.setString(1,viewer.getId());
        preparedStatement.setString(2, postId);
        preparedStatement.setString(3, String.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();
    }
    public Post(){}

    public void setCommentAbility(boolean commentAbility) {
        this.commentAbility = commentAbility;
    }
    public void addLike(User user){

    }
    public void deleteLike(User user){

    }
    public void addView(User user){
    }
    public void addComment(Comment comment){
        //System.out.println(this);
        comments.add(comment);
    } //check commentAbility
    public void deleteComment(Comment comment){}
    public User getSender() {
        return sender;
    }
    public String getFormat() {
        return format;
    }
    public String getType() {
        return gender;
    }
    public boolean isCommentAble() {
        return commentAbility;
    }
    public LocalDateTime getDate() {
        return date;
    }
    
    public LinkedHashMap<User, LocalDateTime> getViews() {
        return views;
    }

    public LinkedHashMap<User, LocalDateTime> getLikes() {
        return likes;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        Post.i = i;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isCommentAbility() {
        return commentAbility;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setViews(LinkedHashMap<User, LocalDateTime> views) {
        this.views = views;
    }

    public void setLikes(LinkedHashMap<User, LocalDateTime> likes) {
        this.likes = likes;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
