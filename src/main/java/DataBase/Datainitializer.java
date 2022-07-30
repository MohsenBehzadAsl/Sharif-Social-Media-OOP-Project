package DataBase;

import component.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Datainitializer {
    private ArrayList<Message> messageArrayList=new ArrayList<>();
    public void createTable(Connection connection) throws SQLException {
        Datainitialize(connection.createStatement());
        Datainitialize1(connection.createStatement());
        Datainitialize2(connection.createStatement());
        Datainitialize3(connection.createStatement());
        Datainitialize4(connection.createStatement());
        Datainitialize5(connection.createStatement());
        Datainitialize6(connection.createStatement());
        Datainitialize7(connection.createStatement());
        Datainitialize8(connection.createStatement());
        Datainitialize9(connection.createStatement());
        Datainitialize10(connection.createStatement());
        Datainitialize11(connection.createStatement());
        Datainitialize12(connection.createStatement());
    }

    public void Datainitialize1(Statement statement) throws SQLException {
        // System.out.printf("*****");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "users(id varchar(255) NOT NULL, " +
                "userName varchar (255)," +
                "password varchar (255)," +
                "passwordHint varchar (255)," +
                "gender varchar (255)," +
                "ansQuestion varchar (255)," +
                "bio varchar (255)," +
                "question varchar(255)," +
                "email varchar(255)," +
                "addToGroupAbility  varchar(255)," +
                "PRIMARY KEY (id))");
    }
    public void Datainitialize2(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE   IF  NOT EXISTS "+
                "tweets(senderId varchar(255)NOT NULL,"+
                "postId varchar(255),"+
                "format varchar(255),"+
                "content varchar(255),"+
                "gender varchar(255),"+
                "sendTime  varchar(255), "+
                "commentAbility varchar(255),"+
                "isComment varchar(255))"
        );

    }
    public void Datainitialize3(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE  IF NOT EXISTS "+
                "follow(followerId varchar(255) NOT NULL,"+
                "followingId varchar(255))");
    }
    public void Datainitialize4(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "comments(senderId varchar(255) NOT NULL,"+
                "postId varchar(255),"+
                "format varchar(255),"+
                "content varchar(255), "+
                "gender varchar(255),"+
                "sendTime varchar(255),"+
                "commentAbility varchar(255), "+
                "commentOfPostId varchar(255),"+
                "commentId varchar(255))"
        );
    }
    public void Datainitialize(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "views(viewerId varchar(255),"+
                "postId varchar(255),"+
                "viewTime varchar(255))");

    }
    public void Datainitialize5(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "likes(likerId varchar(255) NOT NULL,"+
                "postId varchar(255), "+
                "likeTime varchar(255))");
    }
    public void Datainitialize6(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "pv(pvId varchar(255) NOT NULL,"+
                "user1Id varchar(255), "+
                "user2Id varchar(255),"+
                "block  varchar(255) ,"+
                "blockerId varchar(255))");
    }
    public void Datainitialize7(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "message(senderId varchar(255) NOT NULL,"+
                "format varchar(255), "+
                "content varchar(255),"+
                "gender varchar(255) ,"+
                "sendTime varchar(255),"+
                "forward  varchar(255) ,"+
                "forwardedFromId varchar(255),"+
                "edited  varchar(255) ,"+
                "reply  varchar(255) ,"+
                "isMessage varchar(255),"+
                "replyOfMessageId varchar(255),"+//many to one va in ro neshon mide k in payam b kodom payam reply shode
                "messageId varchar(255),"+
                "isPvOrGroup varchar(255),"+
                "pvOrGroupId varchar(255))");
    }
    public void Datainitialize8(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "reply(senderId varchar(255) NOT NULL,"+
                "format varchar(255), "+
                "content varchar(255),"+
                "gender varchar(255) ,"+
                "sendTime varchar(255),"+
                "edited varchar(255) ,"+
                "isMessage varchar(255),"+
                "replyOfMessageId varchar(255),"+//many to one va in ro neshon mide k in payam b kodom payam reply shode
                "messageId varchar(255),"+
                "isPvOrGroup varchar(255),"+
                "pvOrGroupId varchar(255))");
    }
    public void Datainitialize9(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "groupMembers(groupId varchar(255) NOT NULL,"+
                "userId varchar(255), "+
                "isAdminOrOwnerOrNormal varchar(255),"+
                "isBan varchar(255))");
    }
    public void Datainitialize10(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "groupInformation(sqlId varchar(255) NOT NULL,"+
                "groupId varchar(255),"+
                "nameOfGroup varchar(255),"+
                "bio varchar(255),"+
                "isBan varchar(255))");


    }
    public void Datainitialize11(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "readMessagePv(userId varchar(255) NOT NULL,"+
                "pvId varchar(255),"+
                "readMessage varchar(255))");
    }
    public void Datainitialize12(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+
                "readMessageGroup(userId varchar(255) NOT NULL,"+
                "groupId varchar(255),"+
                "readMessage varchar(255))");
    }
    public void initializeuser(Connection connection) throws SQLException, ClassNotFoundException {
        //   System.out.printf("hiiiiiii");
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT * FROM users"
        );
        ResultSet resultSet1 = connection.createStatement().executeQuery(
                "SELECT * FROM follow"
        );
        ResultSet resultSet2 = connection.createStatement().executeQuery(
                "SELECT * FROM tweets"
        );
        ResultSet resultSet3 = connection.createStatement().executeQuery(
                "SELECT * FROM comments"
        );
        ResultSet resultSet4 = connection.createStatement().executeQuery(
                "SELECT * FROM likes"
        );
        ResultSet resultSet5 = connection.createStatement().executeQuery(
                "SELECT * FROM views"
        );
        ResultSet resultSet6 = connection.createStatement().executeQuery(
                "SELECT * FROM pv"
        );
        ResultSet resultSet7 = connection.createStatement().executeQuery(
                "SELECT * FROM groupInformation"
        );
        ResultSet resultSet8 = connection.createStatement().executeQuery(
                "SELECT * FROM groupMembers"
        );
        ResultSet resultSet9 = connection.createStatement().executeQuery(
                "SELECT * FROM message"
        );
        ResultSet resultSet10 = connection.createStatement().executeQuery(
                "SELECT * FROM reply"
        );
        ResultSet resultSet11 = connection.createStatement().executeQuery(
                "SELECT * FROM readMessagePv"
        );
        ResultSet resultSet12 = connection.createStatement().executeQuery(
                "SELECT * FROM readMessageGroup"
        );
        while (resultSet.next()){
            User user=new User();
            user.setId(resultSet.getString("id"));
            user.setAnsQuestion(resultSet.getString("ansQuestion"));
            user.setPassword(resultSet.getString("password"));
            user.setType(resultSet.getString("gender"));
            user.setQuestion(resultSet.getString("question"));
            user.setPasswordHint(resultSet.getString("passwordHint"));
            user.setUserName(resultSet.getString("userName"));
            DataBase dataBase=new DataBase();
            dataBase.getUsers().add(user);
            dataBase.getUserIDs().add(user.getId());
        }
        while (resultSet1.next()){
            DataBase dataBase=new DataBase();
            User user=new User();
            User user1=new User();
            user.setId(resultSet1.getString("followerId"));
            user1.setId(resultSet1.getString("followingId"));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    for (User baseUser : dataBase.getUsers()) {
                        if(baseUser.getId().equals(user1.getId())){
                            dataBaseUser.addFollowing(baseUser);
                            baseUser.addFollower(dataBaseUser);
                        }
                    }
                }
            }
        }
        while (resultSet2.next()){
            Post post=new Post();
            User user=new User();
            user.setId(resultSet2.getString("senderId"));
            DataBase dataBase=new DataBase();
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    post.setSender(dataBaseUser);
                }
            }
            post.setPostId(resultSet2.getString("postId"));
            post.setFormat(resultSet2.getString("format"));
            post.setContent(resultSet2.getString("content"));
            post.setGender(resultSet2.getString("gender"));
            post.setDate(LocalDateTime.parse(resultSet2.getString("sendTime")));
            post.setCommentAbility(Boolean.parseBoolean(resultSet2.getString("commentAbility")));
            post.setIsComment(resultSet2.getString("isComment"));
            dataBase.getPosts().add(post);
        }
        while (resultSet3.next()){
            Post post=new Post();
            User user=new User();
            Comment comment=new Comment();
            user.setId(resultSet3.getString("senderId"));
            DataBase dataBase=new DataBase();
            for (User dataBaseUser : dataBase.getUsers()) {
                if (dataBaseUser.getId().equals(user.getId())) {
                    comment.setSender(dataBaseUser);
                }
            }
            comment.setCommentId(resultSet3.getString("commentId"));
            comment.setPostId(resultSet3.getString("postId"));
            comment.setFormat(resultSet3.getString("format"));
            comment.setContent(resultSet3.getString("content"));
            comment.setGender(resultSet3.getString("gender"));
            comment.setDate(LocalDateTime.parse(resultSet3.getString("sendTime")));
            comment.setIsComment("comment");
            comment.setCommentAbility(Boolean.parseBoolean(resultSet3.getString("commentAbility")));
            post.setPostId(resultSet3.getString("commentOfPostId"));
            for (Post dataBasePost : dataBase.getPosts()) {
                if(dataBasePost.getPostId().equals(post.getPostId())){
                   // dataBasePost.getComments().add(comment);
                    post=dataBasePost;
                }
            }
            comment.setCommentOfPost(post);
            DataBase.getComments().add(comment);
        }

        DataBase.updateComments();
        while (resultSet4.next()){
            Post post=new Post();
            User user=new User();
            post.setPostId(resultSet4.getString("postId"));
            user.setId(resultSet4.getString("likerId"));
            DataBase dataBase=new DataBase();
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    for (Post dataBasePost : dataBase.getPosts()) {
                        if(dataBasePost.getPostId().equals(post.getPostId())){
                            dataBasePost.getLikes().put(dataBaseUser, LocalDateTime.parse(resultSet4.getString("likeTime")));
                        }
                    }
                }
            }
        }
        while (resultSet5.next()){//view.......
            DataBase dataBase=new DataBase();//viewTime
            Post post=new Post();
            User user=new User();
            user.setId(resultSet5.getString("viewerId"));
            post.setPostId(resultSet5.getString("postId"));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    for (Post dataBasePost : dataBase.getPosts()) {
                        if(dataBasePost.getPostId().equals(post.getPostId())){
                            dataBasePost.getViews().put(dataBaseUser, LocalDateTime.parse(resultSet5.getString("viewTime")));
                        }
                    }
                }
            }

        }
        while (resultSet6.next()){
            Pv pv=new Pv();
            User user1=new User();
            User user2=new User();
            User user3=new User();
            DataBase dataBase=new DataBase();
            pv.setPvId(resultSet6.getString("pvId"));
            user1.setId(resultSet6.getString("user1Id"));
            for (User user : dataBase.getUsers()) {
                if(user.getId().equals(user1.getId())){
                    pv.setUser1(user);
                }
            }
            user2.setId(resultSet6.getString("user2Id"));
            for (User user : dataBase.getUsers()) {
                if(user.getId().equals(user2.getId())){
                    pv.setUser2(user);
                }
            }
            if(Boolean.parseBoolean(resultSet6.getString("block"))){
                user3.setId(resultSet6.getString("blockerId"));
                for (User user : dataBase.getUsers()) {
                    if(user.getId().equals(user3.getId())){
                        pv.setBlock(Boolean.parseBoolean(resultSet6.getString("block")),user);
                    }
                }
            }
            for (User user : dataBase.getUsers()) {
                if(user.getId().equals(user1.getId())){
                    for (User dataBaseUser : dataBase.getUsers()) {
                        if(dataBaseUser.getId().equals(user2.getId())){
                            user.getLinkedPvs().put(pv,dataBaseUser);
                            dataBaseUser.getLinkedPvs().put(pv,user);
                            user.getPvs().add(pv);
                            dataBaseUser.getPvs().add(pv);
                        }
                    }

                }
            }
        }
        while (resultSet7.next()){
            Group group=new Group();
            group.setBanGroup(Boolean.valueOf(resultSet7.getString("isBan")));
            group.setName(resultSet7.getString("nameOfGroup"));
            group.setBio(resultSet7.getString("bio"));
            group.setSqlId(resultSet7.getString("sqlId"));
            group.setGroupId(resultSet7.getString("groupId"));
            group.setBanGroup(Boolean.valueOf(resultSet7.getString("isBan")));
            while (resultSet8.next()){
                User user=new User();
                DataBase dataBase=new DataBase();
                user.setId(resultSet8.getString("userId"));
                String gender=resultSet8.getString("isAdminOrOwnerOrNormal");
                if(resultSet8.getString("groupId").equals(group.getSqlId())) {
                    if (gender.equalsIgnoreCase("admin")) {
                        for (User dataBaseUser : dataBase.getUsers()) {
                            if (dataBaseUser.getId().equals(user.getId())) {
                                group.getMembers().add(dataBaseUser);
                                group.getAdmins().add(dataBaseUser);

                                dataBaseUser.getGroups().add(group);
                            }
                        }
                    }
                    if (gender.equalsIgnoreCase("owner")) {
                        for (User dataBaseUser : dataBase.getUsers()) {
                            if (dataBaseUser.getId().equals(user.getId())) {
                                group.getMembers().add(dataBaseUser);
                                group.getLinkedMembers().put(dataBaseUser, Boolean.valueOf(resultSet8.getString("isBan")));
                                group.setOwner(dataBaseUser);
                                dataBaseUser.getGroups().add(group);
                            }
                        }
                    }
                    if (gender.equalsIgnoreCase("normal")) {
                        for (User dataBaseUser : dataBase.getUsers()) {
                            if (dataBaseUser.getId().equals(user.getId())) {
                                group.getMembers().add(dataBaseUser);
                                group.getLinkedMembers().put(dataBaseUser, Boolean.valueOf(resultSet8.getString("isBan")));
                                dataBaseUser.getGroups().add(group);
                            }
                        }
                    }
                }
            }
        }
        while (resultSet9.next()){
            User user=new User();
            User user2=new User();
            Message message=new Message();
            DataBase dataBase=new DataBase();
            user.setId(resultSet9.getString("senderId"));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    message.setSender(dataBaseUser);
                }
            }
            message.setForward(Boolean.valueOf(resultSet9.getString("forward")));
            user2.setId(resultSet9.getString("forwardedFromId"));
            message.setFormat(resultSet9.getString("format"));
            message.setContent(resultSet9.getString("content"));
            message.setGender(resultSet9.getString("gender"));
            message.setDate(LocalDateTime.parse(resultSet9.getString("sendTime")));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user2.getId())){
                    message.setForwardFrom(dataBaseUser);
                }
            }
            message.setEdited(Boolean.valueOf(resultSet9.getString("edited")));
            message.setReply(Boolean.valueOf(resultSet9.getString("reply")));
            message.setIsMessage(resultSet9.getString("isMessage"));
            message.setMessageId(resultSet9.getString("messageId"));
            message.setIsPvOrGroup(resultSet9.getString("isPvOrGroup"),resultSet9.getString("pvOrGroupId"));
            // message.setPvOrGroupId();
            if(message.getReply()) {
                Message message1=new Message();
                message1=getMessageWithId(resultSet9.getString("replyOfMessageId"));
                if(message1!=null){
                    message.setReplyOfMessageId(resultSet9.getString("replyOfMessageId"));
                    message.setReplyMessage(message1);
                }
                else {
                    message.setReply(false);
                    //bayad reply boolean v reply id as in message delete she
                }
            }
            if(message.getIsPvOrGroup().equals("pv")){
                Pv pv=new Pv();
                pv.setPvId(message.getPvOrGroupId());
                for (Pv pv1 : message.getSender().getPvs()) {
                    if(pv1.getPvId().equals(pv.getPvId())){
                        pv1.getMessages().add(message);
                    }
                }
            }
            else {
                Group group=new Group();
                group.setSqlId(message.getPvOrGroupId());
                for (Group group1 : message.getSender().getGroups()) {
                    if(group1.getSqlId().equals(group.getSqlId())){
                        group1.getMessages().add(message);
                    }
                }
            }
            messageArrayList.add(message);
        }
        while (resultSet11.next()){
            User user=new User();
            Pv pv=new Pv();
            DataBase dataBase=new DataBase();
            pv.setPvId(resultSet11.getString("pvId"));
            user.setId(resultSet11.getString("userId"));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    for (Pv dataBaseUserPv : dataBaseUser.getPvs()) {
                        if(dataBaseUserPv.getPvId().equals(pv.getPvId())){
                            dataBaseUser.getReadMessagePv().add(Integer.valueOf(resultSet11.getString("readMessage")));
                        }
                    }
                }
            }
        }
        while (resultSet12.next()){
            User user=new User();
            Group group=new Group();
            DataBase dataBase=new DataBase();
            user.setId(resultSet12.getString("userId"));
            group.setSqlId(resultSet12.getString("groupId"));
            for (User dataBaseUser : dataBase.getUsers()) {
                if(dataBaseUser.getId().equals(user.getId())){
                    dataBaseUser.getReadMessageGroup().add(Integer.valueOf(resultSet12.getString("readMessage")));
                }
            }
        }
    }
    public Message getMessageWithId(String id){
        Message message1=null;
        for (Message message : messageArrayList) {
            if(message.getMessageId().equals(id)){
                message1=message;
            }
        }
        return message1;
    }
}