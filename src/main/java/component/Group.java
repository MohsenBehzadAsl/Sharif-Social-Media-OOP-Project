package component;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
public class Group {
    private static int index=0;
    private String sqlId;



    private LinkedHashMap<User,Boolean> linkedMembers=new LinkedHashMap<User,Boolean>(); // Boolean-->ban  true==ban
    private ArrayList<User> members=new ArrayList<>();
    private ArrayList<User> admins=new ArrayList<User>();
    private User owner;
    private String groupId=new String();
    private ArrayList<Message> messages=new ArrayList<Message>();
    private String name=new String();
    private String bio=new String();
    private String photo;
    private Boolean banGroup=false; //all

    public LinkedHashMap<User, Boolean> getLinkedMembers() {
        return linkedMembers;
    }

    public Group(User owner , String name, String groupId) throws SQLException, ClassNotFoundException {

        sqlId= String.valueOf(index);
        index++;
        this.groupId=groupId;
        this.name=name;
        this.owner = owner;
        this.members.add(owner);
        this.linkedMembers.put(owner,false);
        this.admins.add(owner);
        addGroupMemberToTable(this,owner,"owner");
        addGroupToTable(this,name,bio,banGroup);
    }

    public Group(ArrayList<User> users, User owner,String name,String groupId) throws SQLException, ClassNotFoundException {
        for (User user : users) {
            this.linkedMembers.put(user,false);
        }
        this.owner = owner;
        this.groupId=groupId;
        this.name=name;
        addGroupToTable(this,name,bio,banGroup);
    }//alan bedard nmikhore
    public Group(){

    }
public void addGroupMemberToTable(Group group,User user,String nemidonam) throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection= DriverManager.
            getConnection("jdbc:mysql://localhost:3306/joel"
                    , "root",
            DataBase.password);
    PreparedStatement preparedStatement =connection.prepareStatement(
            " INSERT INTO groupMembers (groupId,userId,isAdminOrOwnerOrNormal,isBan) VALUES (?,?,?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
    );
    preparedStatement.setString(1,group.getSqlId());
    preparedStatement.setString(2,user.getId());
    preparedStatement.setString(3,nemidonam);
    preparedStatement.setString(4,String.valueOf(group.getBanGroup()));
    preparedStatement.executeUpdate();
}
public void addGroupToTable(Group group,String name,String bio,boolean banGroup) throws ClassNotFoundException, SQLException {
  //  System.out.println("TESSSTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection= DriverManager.
            getConnection("jdbc:mysql://localhost:3306/joel"
                    , "root",
                    DataBase.password);
    PreparedStatement preparedStatement =connection.prepareStatement(
            " INSERT INTO groupInformation (sqlId,groupId,nameOfGroup,bio,isBan) VALUES (?,?,?,?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
    );
    preparedStatement.setString(1,group.getSqlId());
    preparedStatement.setString(2,group.getGroupId());
    preparedStatement.setString(3,name);
    preparedStatement.setString(4,bio);
    preparedStatement.setString(5, String.valueOf(banGroup));
    preparedStatement.executeUpdate();


}
    public void addMember(User user) throws SQLException, ClassNotFoundException {
        this.members.add(user);
        this.linkedMembers.put(user,false);
        user.addGroup(this);
        addGroupMemberToTable(this,user,"normal");
    } //check addToGroup && set banMessage
    public void addAdmin(User user) throws SQLException, ClassNotFoundException {//
        if (!admins.contains(user)) {
            admins.add(user);
        }
       UpdateSqlTable.setAdminToTable(this,user,"admin");
    }
    public void removeMember(User user) throws SQLException, ClassNotFoundException {
        linkedMembers.remove(user);
        members.remove(user);
        admins.remove(user);
        user.deleteGroup(this);
        UpdateSqlTable.setRemoveMemberFromTable(this,user);
    } //check admin && check owner
    public void banAdmin(User user){} //owner
    public void setBanMessageAll(){} //owner & admins
    public void addMessage(Message message){
        this.messages.add(message);
    }
    public void removeMessage(Message message) throws SQLException, ClassNotFoundException {
        this.messages.remove(message);
        UpdateSqlTable.deleteMessage(message);
    }
    public void setBanMessage(User user){};
    public ArrayList<User> getMembers() {
        return members;
    }
    public void setMembers(LinkedHashMap<User, Boolean> members) {
        this.linkedMembers = members;
    }
    public ArrayList<User> getAdmins() {
        return admins;
    }
    public void setAdmins(ArrayList<User> admins) {
        this.admins = admins;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Boolean getBanGroup() {
        return banGroup;
    }
    public void setBanGroup(Boolean banGroup) throws SQLException, ClassNotFoundException {
        this.banGroup = banGroup;
        UpdateSqlTable.setBanGroupToTable(this,banGroup);
    }
    public void removeAdmin(User user) throws SQLException, ClassNotFoundException {
        if (admins.contains(user)){
            admins.remove(user);
        }
        UpdateSqlTable.setAdminToTable(this,user,"normal");
    }
    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Group.index = index;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public void setLinkedMembers(LinkedHashMap<User, Boolean> linkedMembers) {
        this.linkedMembers = linkedMembers;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }
}
