package DataBase;

import component.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSqlTable {
   public UpdateSqlTable(){

   }
    public static void setProfilePhotoToTable(User user, String address) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET profilePhoto = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,address);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setBioUserToTable(User user, String bio) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET bio = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,bio);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setUserNameToTable(User user, String userName) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET userName = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setPasswordToTable(User user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET password = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,password);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setPasswordHintToTable(User user, String passwordHint) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET passwordHint = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,passwordHint);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setPasswordQuestionToTable(User user, String question) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET question = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,question);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setAnswerOfPasswordQuestionToTable(User user, String ansQuestion) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET ansQuestion = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,ansQuestion);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setGenderToTable(User user, String gender) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET gender = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1,gender);
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setAddToGroupAbilityToTable(User user, boolean addToGroupAbility) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET addToGroupAbility = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1, String.valueOf(addToGroupAbility));
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setCommentAbilityPostTable(Post post, boolean commentAbility) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE users " +
                        "SET commentAbility = ?" +
                        "WHERE id = ?"
        );
        preparedStatement.setString(1, String.valueOf(commentAbility));
        preparedStatement.setString(2,post.getPostId());
        preparedStatement.executeUpdate();
    }
    public static void deletePostFromTable(Post post) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM tweets WHERE postId=?"
        );
   /*     PreparedStatement preparedStatement1=connection.prepareStatement("" +
                "DELETE FROM tweets WHERE postId=?");*/
        preparedStatement.setString(1,post.getPostId());
        preparedStatement.executeUpdate();
    }
    public static void setBlockToPv(Pv pv, User blocker,boolean blockOrUnblock) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE pv " +
                        "SET block=?,blockerId=? " +
                        "WHERE pvId = ?"
        );
        preparedStatement.setString(1, String.valueOf(blockOrUnblock));
        preparedStatement.setString(2,blocker.getId());
        preparedStatement.setString(3,pv.getPvId());
        preparedStatement.executeUpdate();
    }
    public static void deletePv(Pv pv) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM pv WHERE pvId=?"
        );
        preparedStatement.setString(1, pv.getPvId());
        preparedStatement.executeUpdate();
        readMessageDeletePv(pv);
    }
    public static void setBanGroupToTable(Group group,boolean isBan) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupInformation " +
                        "SET isBan = ? " +
                        "WHERE sqlId = ?"
        );
        preparedStatement.setString(1, String.valueOf(isBan));
        preparedStatement.setString(2,group.getSqlId());
        preparedStatement.executeUpdate();
    }
    public static void setBioGroupToTable(Group group,String bio) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupInformation " +
                        "SET bio = ?" +
                        "WHERE sqlId = ?"
        );
        preparedStatement.setString(1,bio);
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.executeUpdate();
    }
    public static void leaveFromGroup(Group group,User user) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM groupMembers WHERE groupId=?,userId=? "
        );
        preparedStatement.setString(1,group.getSqlId());
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
        readMessageLeaveFromGroup(group,user);
    }
    public static void setGroupNameToTable(Group group,String groupName) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupInformation " +
                        "SET nameOfGroup = ?" +
                        "WHERE sqlId = ?"
        );
        preparedStatement.setString(1,groupName);
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.executeUpdate();
    }
    public static void setGroupIdToTable(Group group,String groupId) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupInformation " +
                        "SET groupId = ?" +
                        "WHERE sqlId = ?"
        );
        preparedStatement.setString(1,groupId);
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.executeUpdate();
    }
/*    public static void setGroupBioToTable(Group group,String bio) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupInformation " +
                        "SET bio = ?" +
                        "WHERE sqlId = ?"
        );
        preparedStatement.setString(1,bio);
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.executeUpdate();
    }*/
    public static void setBanMemberToTable(Group group,User user,boolean isBan) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupMembers " +
                        "SET isBan = ?" +
                        "WHERE (groupId = ?) AND (userId=?) "
        );
        preparedStatement.setString(1, String.valueOf(isBan));
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.setString(3, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setRemoveMemberFromTable(Group group,User user) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM groupMembers WHERE (groupId=?) AND (userId=?)"
        );
        preparedStatement.setString(1,group.getSqlId());
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setAdminToTable(Group group,User user,String isAdminOrOwnerOrNormal) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE groupMembers " +
                        "SET isAdminOrOwnerOrNormal = ?" +
                        "WHERE (groupId=?) AND (userId=?) "
        );
        preparedStatement.setString(1, isAdminOrOwnerOrNormal);
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.setString(3, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void readMessageLeaveFromGroup(Group group,User user) throws ClassNotFoundException, SQLException {//taghir tedad payam ha pas
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM readMessageGroup WHERE (groupId=?) AND (userId=?)"
        );
        preparedStatement.setString(1,group.getSqlId());
        preparedStatement.setString(2, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void readMessageDeletePv(Pv pv) throws ClassNotFoundException, SQLException {//taghirr tedad payam ha


        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM readMessagePv WHERE pvId=?"
        );
        preparedStatement.setString(1, pv.getPvId());
        preparedStatement.executeUpdate();
    }
    public static void setReadMessagePv(Pv pv,User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE readMessagePv " +
                        "SET readMessage = ?" +
                        "WHERE (pvId=?) AND (userId=?) "
        );
        preparedStatement.setString(1, String.valueOf(user.getReadMessagePv().get(user.getPvs().indexOf(pv))));
        preparedStatement.setString(2, pv.getPvId());
        preparedStatement.setString(3, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void setReadMessageGroup(Group group,User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE readMessageGroup " +
                        "SET readMessage = ?" +
                        "WHERE (groupId=?) AND (userId=?) "
        );
        preparedStatement.setString(1, String.valueOf(user.getReadMessageGroup().get(user.getGroups().indexOf(group))));
        preparedStatement.setString(2, group.getSqlId());
        preparedStatement.setString(3, user.getId());
        preparedStatement.executeUpdate();
    }
    public static void deleteMessage(Message message) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                "DELETE FROM message WHERE messageId=?"
        );
        preparedStatement.setString(1, message.getMessageId());
        preparedStatement.executeUpdate();
    }
    public static void editMessage(Message message) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " UPDATE message " +
                        "SET content = ?" +
                        "WHERE messageId=? "
        );
        preparedStatement.setString(1, message.getContent());
        preparedStatement.setString(2, message.getMessageId());
        preparedStatement.executeUpdate();
    }
}
