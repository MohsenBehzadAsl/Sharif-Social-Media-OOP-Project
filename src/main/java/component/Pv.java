package component;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pv {
    private static int index=0;
    private String pvId;
    private User user1;
    private User user2;
    private ArrayList<Message> messages=new ArrayList<Message>();
    private Boolean block=false;
    private User blocker=new User();
    public Pv(User user1,User user2) throws SQLException, ClassNotFoundException {
        pvId= String.valueOf(index);
        index++;
        this.user1=user1;
        this.user2=user2;
        addPvToTable(this.getPvId(),user1.getId(),user2.getId(),block,blocker.getId());
    }
    public Pv(){

    }
    public void addPvToTable(String pvId,String user1Id,String user2Id,boolean block,String blockerId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO pv  (pvId,user1Id,user2Id,block,blockerId) VALUES (?,?,?,?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
        );
        preparedStatement.setString(1,pvId);
        preparedStatement.setString(2,user1Id);
        preparedStatement.setString(3,user2Id);
        preparedStatement.setString(4, String.valueOf(block));
        preparedStatement.setString(5,blockerId);
        preparedStatement.executeUpdate();
    }

    public User getUser1() {

        return user1;
    }
    public void setUser1(User user1) {
        this.user1 = user1;

    }
    public User getUser2() {
        return user2;
    }
    public void setUser2(User user2) {

        this.user2 = user2;
    }
    public ArrayList<Message> getMessages() {

        return messages;
    }
    public void setMessages(ArrayList<Message> messages) {

        this.messages = messages;
    }
    public Boolean getBlock() {

        return block;
    }
    public User getBlocker() {

        return blocker;
    }

    public void setBlock(Boolean block, User user) {
        blocker=user;
        this.block = block;
    }
    public void addMessage(Message message){
        messages.add(message);
    } //check block
    public void removeMessage(Message message) throws SQLException, ClassNotFoundException {
        if (!messages.contains(message)){
            System.out.println("nadarim :////////////");
        }
        else {
            messages.remove(message);
            UpdateSqlTable.deleteMessage(message);
        }

    }
    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Pv.index = index;
    }

    public String getPvId() {
        return pvId;
    }

    public void setPvId(String pvId) {
        this.pvId = pvId;
    }

}
