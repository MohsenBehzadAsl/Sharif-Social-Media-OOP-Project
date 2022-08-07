package component;

import DataBase.DataBase;
import View.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import DataBase.UpdateSqlTable;

public class User {

    private String photoNameFromImageFolder;
    private String query;
    private DataBase dataBase=new DataBase();
    private String userName=new String();
    private String id=new String();
    private String password=new String();
    private String passwordHint=new String();
    private String bio=new String("Hi,I'm using Sharif twitter :)");
    private String gender=new String();
    private Boolean addToGroupAbility=true;
    private String email=new String();
    private String question=new String();
    private String ansQuestion=new String();
    private ArrayList<User> followings=new ArrayList<User>();
    private ArrayList<User> followers=new ArrayList<User>();
    private LinkedHashMap<Pv,User> linkedPvs=new LinkedHashMap<Pv,User>();
    private ArrayList<Pv> pvs=new ArrayList<>();
    private ArrayList<Integer> readMessagePv=new ArrayList<Integer>();
    private ArrayList<Group> groups=new ArrayList<Group>();
    public ArrayList<Integer> getReadMessagePv() {
        return readMessagePv;
    }
    public ArrayList<Integer> getReadMessageGroup() {
        return readMessageGroup;
    }
    private ArrayList<Integer> readMessageGroup=new ArrayList<Integer>();
    private ArrayList<Post> posts=new ArrayList<Post>();
    private LinkedHashMap<User, LocalDateTime> viewsFromPage = new LinkedHashMap<User,LocalDateTime>();

    public HashMap<User, Integer> getLikedInfo() {
        return LikedInfo;
    }

    public void setLikedInfo(HashMap<User, Integer> likedInfo) {
        LikedInfo = likedInfo;
    }

    private HashMap<User, Integer> LikedInfo = new HashMap<User,Integer>();
    private ArrayList<Post> selectedPosts=new ArrayList<>();

    public ArrayList<Post> getSelectedPosts() {
        return selectedPosts;
    }

    public void setSelectedPosts(ArrayList<Post> selectedPosts) {
        this.selectedPosts = selectedPosts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setType(String type) {
        this.gender = type;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setAnsQuestion(String ansQuestion) {
        this.ansQuestion = ansQuestion;
    }
    public void setFollowings(ArrayList<User> followings) {
        this.followings = followings;
    }
    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }
    public void setPvs(LinkedHashMap<Pv, User> pvs) {
        this.linkedPvs = pvs;
    }
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnsQuestion() {
        return ansQuestion;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

  /*  public DataBase.DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase.DataBase dataBase) {
        this.dataBase = dataBase;
    }*/

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLinkedPvs(LinkedHashMap<Pv, User> linkedPvs) {
        this.linkedPvs = linkedPvs;
    }

    public void setPvs(ArrayList<Pv> pvs) {
        this.pvs = pvs;
    }

    public void setReadMessagePv(ArrayList<Integer> readMessagePv) {
        this.readMessagePv = readMessagePv;
    }

    public void setReadMessageGroup(ArrayList<Integer> readMessageGroup) {
        this.readMessageGroup = readMessageGroup;
    }

    public ArrayList<User> getFollowings() {
        return followings;
    }
    public ArrayList<User> getFollowers() {
        return followers;
    }
    public User(String userName, String id, String password, String passwordHint , String gender, String question, String ansQuestion) {
        photoNameFromImageFolder=new String();
        int index= (int) Math.random()%7;
        photoNameFromImageFolder="sampleProfilePhoto"+index;
        //     Repository.statement.executeQuery("INSERT INTO meow  (userName,id,password,passwordHint,type,question,ansQuestion) VALUES (a,b,c,d,e,f,g))");
        this.userName = userName;
        this.id = id;
        this.password = password;
        this.passwordHint = passwordHint;
        this.gender=gender;
        this.ansQuestion=ansQuestion;
        this.question=question;
    }
    public User(){
    }
    public String getUserName() {
        return userName;
    }
    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getPasswordHint() {
        return passwordHint;
    }
    public String getBio() {
        return bio;
    }
    public String getType() {
        return gender;
    }
    public Boolean getAddToGroupAbility() {

        return addToGroupAbility;
    }
    public void setAddToGroupAbility(Boolean addToGroupAbility) {

        this.addToGroupAbility = addToGroupAbility;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public void addFollower(User user){
        followers.add(user);
    }    //2 tarafe
    public void addFollowing(User user){
        followings.add(user);
    }
    public void addPv(User otherUser) throws SQLException, ClassNotFoundException {
        Pv pv=new Pv(otherUser,this);
        if (!this.getLinkedPvs().containsValue(otherUser)) {
            this.linkedPvs.put(pv, otherUser);
            this.pvs.add(pv);
            this.readMessagePv.add(0);
        }
        if (!otherUser.getLinkedPvs().values().contains(this)) {
            otherUser.linkedPvs.put(pv,this);
            otherUser.pvs.add(pv);
            otherUser.readMessagePv.add(0);
        }
        addReadMessagePv(pv);//
    }
    public void deletePv(User otherUser){
        //...
    }
    public void addGroup(Group group) throws SQLException, ClassNotFoundException {
        this.groups.add(group);
        this.readMessageGroup.add(0);
        addReadMessageGroup(group);

    }
    public void addReadMessagePv(Pv pv) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        User user1= pv.getUser1();
        User user2=pv.getUser2();
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO readMessagePv  (userId,pvId,readMessage) VALUES (?,?,?)"
        );
        preparedStatement.setString(1,user1.getId());
        preparedStatement.setString(2, pv.getPvId());
        preparedStatement.setString(3, String.valueOf(user1.readMessagePv.get(user1.getReadMessagePv().size()-1)));
        PreparedStatement preparedStatement1 =connection.prepareStatement(
                " INSERT INTO readMessagePv  (userId,pvId,readMessage) VALUES (?,?,?)"
        );
        preparedStatement1.setString(1,user2.getId());
        preparedStatement1.setString(2, pv.getPvId());
        preparedStatement1.setString(3, String.valueOf(user2.readMessagePv.get(user2.getReadMessagePv().size()-1)));
        preparedStatement.executeUpdate();
        preparedStatement1.executeUpdate();
    }
    public void addViewsFromPageToTable(User viewer,User viewed,LocalDateTime sendTime) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO  viewsFromPage (viewerId,viewedId,viewTime) VALUES (?,?,?)"
        );
        preparedStatement.setString(1,viewer.getId());
        preparedStatement.setString(2, viewed.getId());
        preparedStatement.setString(3, String.valueOf(LocalDateTime.now()));
        preparedStatement.executeUpdate();
    }
    public void addReadMessageGroup(Group group) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO  readMessageGroup (userId,groupId,readMessage) VALUES (?,?,?)"
        );
        preparedStatement.setString(1,this.getId());
        preparedStatement.setString(2, group.getGroupId());
        preparedStatement.setString(3, String.valueOf(this.readMessageGroup.get(this.getGroups().indexOf(group))));
        preparedStatement.executeUpdate();
    }

    public ArrayList<Group> getGroups() {

        return groups;
    }
    public LinkedHashMap<Pv, User> getLinkedPvs() {
        return linkedPvs;
    }

    public ArrayList<Pv> getPvs() {
        return pvs;
    }

    public  Pv getPv(User user2){
        ArrayList<Pv> Pvs=new ArrayList<Pv>(this.linkedPvs.keySet());
        ArrayList<User> users=new ArrayList<User>(this.linkedPvs.values());
        if (users.indexOf(user2)<0){
            return null;
        }
        return Pvs.get(users.indexOf(user2));
    }
//    public void addGroup(ArrayList<User> users , String name, String groupId) throws SQLException, ClassNotFoundException {
//        Group group=new Group(users,this,name,groupId,"23");
//        for (User user : users) {
//            user.groups.add(group);
//        }
//    }
    public LinkedHashMap<User, LocalDateTime> getViewsFromPage() {
        return viewsFromPage;
    }
    public void setViewsFromPage(LinkedHashMap<User, LocalDateTime> viewsFromPage) {
        this.viewsFromPage = viewsFromPage;
    }

    public void deleteGroup(Group group) {
        this.readMessageGroup.remove(this.groups.indexOf(group));
        this.groups.remove(group);
    }

    public void removePv(Pv pv) throws SQLException, ClassNotFoundException {
        User user2=pv.getUser2();
        if (user2.getPvs().contains(pv)) {
            user2.getPvs().remove(pv);
            user2.getLinkedPvs().remove(pv);
        }
        User user1=pv.getUser1();
        if (user1.getPvs().contains(pv)) {
            user1.getPvs().remove(pv);
            user1.getLinkedPvs().remove(pv);
        }
        UpdateSqlTable.deletePv(pv);
        UpdateSqlTable.readMessageDeletePv(pv);
    }
    public static void addFollowerAndFollowingToTable(User user,User user1) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO follow  (followerId,followingId) VALUES (?,?)"
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
        );
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user1.getId());
        preparedStatement.executeUpdate();
    }
    public static void removeFollowerAndFollowingFromTable(User user,User user1) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " DELETE FROM follow  where  followerId=? "
            /*    UPDATE Customers
                SET ContactName='Juan'
                WHERE Country='Mexico';*/ /*"insert into users(userName,id,password,passwordHint,gender,question,ansQuestion,addToGroupAbility)" +
                        "value (?,?,?,?,?,?,?,?)"*/
        );
        preparedStatement.setString(1, user.getId());
        //  preparedStatement.setString(1, user2.getId());
        PreparedStatement preparedStatement1=connection.prepareStatement(" DELETE FROM follow  where  followingId=? ");
        preparedStatement1.setString(1,user1.getId());
        preparedStatement.executeUpdate();
        preparedStatement1.executeUpdate();
    }
    public String getPhotoNameFromImageFolder() {
        if (photoNameFromImageFolder==null){
            photoNameFromImageFolder=new String();
            int index= ((int) (Math.random()*10))%7;
            photoNameFromImageFolder="sampleProfilePhoto"+index;
            photoNameFromImageFolder= String.valueOf(Controller.class.getResource("/images/"+photoNameFromImageFolder+".png"));
        }
        return photoNameFromImageFolder;
    }

    public void setPhotoNameFromImageFolder(String photoNameFromImageFolder) {
        this.photoNameFromImageFolder = photoNameFromImageFolder;
    }
 /*   @Override
    public String toString() {
        return "User{" +
                "query='" + query + '\'' +
                ", dataBase=" + dataBase +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", passwordHint='" + passwordHint + '\'' +
                ", bio='" + bio + '\'' +
                ", gender='" + gender + '\'' +
                ", addToGroupAbility=" + addToGroupAbility +
                ", email='" + email + '\'' +
                ", question='" + question + '\'' +
                ", ansQuestion='" + ansQuestion + '\'' +
                ", followings=" + followings +
                ", followers=" + followers +
                ", linkedPvs=" + linkedPvs +
                ", pvs=" + pvs +
                ", readMessagePv=" + readMessagePv +
                ", groups=" + groups +
                ", readMessageGroup=" + readMessageGroup +
                ", posts=" + posts +
                ", viewsFromPage=" + viewsFromPage +
                ", LikedInfo=" + LikedInfo +
                ", selectedPosts=" + selectedPosts +
                '}';
    }*/
}
