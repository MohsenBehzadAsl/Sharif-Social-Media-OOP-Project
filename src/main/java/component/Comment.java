package component;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Comment extends Post{
    private Post commentOfPost;
    private static int index=0;

    private String commentId;

    public Comment(User user, String format, String text, boolean b, Post post) throws SQLException, ClassNotFoundException {
        super(user,format,text,b,true);
        this.commentOfPost=post;

        addCommentToTable(user,this.getPostId(),commentOfPost.getPostId(),String.valueOf(index),format,text,b);
        index++;
        // Post post=new Post();
        // System.out.println("post.getPostId());
    } // ziad niaz nist
    public Comment(){

    }

    @Override
    public void addComment(Comment comment) {
        super.addComment(comment);
    }

    public void addCommentToTable(User sender, String postId, String commentOfPostId, String commentId, String format, String content, boolean commentAbility) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO comments  (senderId,postId,format,content,gender,sendTime,commentAbility,commentOfPostId,commentId) VALUES (?,?,?,?,?,?,?,?,?)"
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
        preparedStatement.setString(8,commentOfPostId);
        preparedStatement.setString(9,commentId);

        preparedStatement.executeUpdate();
    }
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
        preparedStatement.setString(3, String.valueOf(likeTime));
        preparedStatement.executeUpdate();
    }
    public Post getCommentOfPost() {
        return commentOfPost;
    }

    public void setCommentOfPost(Post commentOfPost) {
        this.commentOfPost = commentOfPost;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

}
