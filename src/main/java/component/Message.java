package component;

import DataBase.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Message extends Post{

    private static int index=0;
    private String messageId;
    private Boolean forward=false;
    private User forwardFrom=new User();//az jens usereeeeeeeeeeeeeee
    private Boolean edited=false;
    private Boolean reply=false;
    private Message replyMessage;
    private String isMessage;
    private String replyOfMessageId=new String();
    private String isPvOrGroup;
    private String pvOrGroupId;

    public Message(){

    }

    public Message(User sender, String format, String content, boolean commentAbility, Boolean forward, Boolean reply) throws SQLException, ClassNotFoundException {
        messageId= String.valueOf(index);
        index++;
        this.setSender(sender);
        this.setDate(LocalDateTime.now());
        this.setFormat(format);
        this.setContent(content);
        this.setGender(sender.getType());
        this.forward=forward;
        this.reply=reply;
        if(!reply){
            isMessage="message";
        }
        else {
            isMessage="reply";
        }
    }
    public void addMessageToTable(User sender,String format,String content,String gender,LocalDateTime sendTime,boolean forward,
                                  User forwardFrom,boolean edited,boolean reply,String isMessage,String replyOfMessageId,String messageId,
                                  String isPvOrGroup,String pvOrGroupId,String photoAddress) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.
                getConnection("jdbc:mysql://localhost:3306/joel"
                        , "root",
                        DataBase.password);
        PreparedStatement preparedStatement =connection.prepareStatement(
                " INSERT INTO message  (senderId,format,content,gender,sendTime,forward,forwardedFromId,edited," +
                        "reply,isMessage,replyOfMessageId,messageId,isPvOrGroup,pvOrGroupId,photoAddress)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        );
        if(reply) {
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "INSERT INTO reply(senderId,format,content,gender,sendTime,edited,isMessage" +
                            ",replyOfMessageId,messageId,isPvOrGroup,pvOrGroupId,photoAddress)" +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            preparedStatement1.setString(1,sender.getId());
            preparedStatement1.setString(2,format);
            preparedStatement1.setString(3,content);
            preparedStatement1.setString(4,gender);
            preparedStatement1.setString(5, String.valueOf(LocalDateTime.now()));
            preparedStatement1.setString(6, String.valueOf(edited));
            preparedStatement1.setString(7,isMessage);
            preparedStatement1.setString(8,replyOfMessageId);
            preparedStatement1.setString(9,messageId);
            preparedStatement1.setString(10,isPvOrGroup);
            preparedStatement1.setString(11,pvOrGroupId);
            preparedStatement1.setString(12,photoAddress);
        }
        preparedStatement.setString(1,sender.getId());
        preparedStatement.setString(2,format);
        preparedStatement.setString(3,content);
        preparedStatement.setString(4,gender);
        preparedStatement.setString(5, String.valueOf(LocalDateTime.now()));
        preparedStatement.setString(6, String.valueOf(forward));
        preparedStatement.setString(7,forwardFrom.getId());
        preparedStatement.setString(8, String.valueOf(edited));
        preparedStatement.setString(9, String.valueOf(reply));
        preparedStatement.setString(10,isMessage);
        preparedStatement.setString(11,replyOfMessageId);
        preparedStatement.setString(12,messageId);
        preparedStatement.setString(13,isPvOrGroup);
        preparedStatement.setString(14,pvOrGroupId);
        preparedStatement.setString(15,photoAddress);
        preparedStatement.executeUpdate();




    }


    public void edit(){

    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public Boolean getForward() {

        return forward;
    }
    public User getForwardFrom() {

        return forwardFrom;
    }
    public Boolean getEdited() {

        return edited;
    }
    public Boolean getReply() {

        return reply;
    }
    public Message getReplyMessage() {

        return replyMessage;
    }
    public void setForward(Boolean forward) {

        this.forward = forward;
    }
    public void setForwardFrom(User forwardFrom) {

        this.forwardFrom = forwardFrom;
    }
    public void setReply(Boolean reply) {

        this.reply = reply;
    }
    public void setReplyMessage(Message replyMessage) {

        this.replyMessage = replyMessage;
        replyOfMessageId=replyMessage.getMessageId();
    }
    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Message.index = index;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(String isMessage) {
        this.isMessage = isMessage;
    }

    public String getReplyOfMessageId() {
        return replyOfMessageId;
    }

    public void setReplyOfMessageId(String replyOfMessageId) {
        this.replyOfMessageId = replyOfMessageId;
    }

    public String getIsPvOrGroup() {
        return isPvOrGroup;
    }

    public void setIsPvOrGroup(String isPvOrGroup,String pvOrGroupId) throws SQLException, ClassNotFoundException {

        this.isPvOrGroup = isPvOrGroup;
        this.pvOrGroupId = pvOrGroupId;
     //   checkExists(messageId);

    }
 /*   public void checkExists(String messageId){

    }*/
    public void addMessageToTable() throws SQLException, ClassNotFoundException {
        addMessageToTable(this.getSender(),this.getFormat(),this.getContent(),this.getSender().getType(),LocalDateTime.now(),forward,
                forwardFrom,edited,reply,isMessage,replyOfMessageId,messageId,isPvOrGroup,pvOrGroupId,this.getPhotoAddress());
    }
    public String getPvOrGroupId() {
        return pvOrGroupId;
    }

  /*  public void setPvOrGroupId(String pvOrGroupId) {
        this.pvOrGroupId = pvOrGroupId;
    }*/
}
