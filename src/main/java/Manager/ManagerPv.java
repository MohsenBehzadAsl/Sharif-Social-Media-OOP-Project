package Manager;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;
import ShowClass.ShowPv;
import component.Group;
import component.Message;
import component.Pv;
import component.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
public class ManagerPv {
    private static String input = new String();

    public static String setName(Pv pv, User user) {
        String Name = new String();
        if (pv.getUser1() == pv.getUser2()) {
            Name = "Saved Message";
        } else if (pv.getUser2() == user) {
            Name = pv.getUser1().getUserName();
        } else if (pv.getUser1() == user) {
            Name = pv.getUser2().getUserName();
        }
        if (pv.getBlock() && pv.getBlocker() == user) {
            Name = " *Block*";
        } else if (pv.getBlock()) {
            Name = " [Blocked]";
        }
        return Name;
    }
    public static void showMessages(Pv pv, User user, int nPage, int postInOnePage) { //npage --> page n om
        ArrayList<Message> Messages = new ArrayList<Message>();
//        System.out.println("nPage: "+nPage);
        if (pv.getMessages().size() >= nPage * postInOnePage) {
//            System.out.println("i am in 1111111111111111111111111111111111");
            for (int i = 0; i < postInOnePage; i++) {
                Messages.add(pv.getMessages().get(pv.getMessages().size()-1-(i+ (nPage - 1) * postInOnePage)));
            }
            for (int i = postInOnePage; i > 0; i--) {
                if (i == postInOnePage) { //Display Date
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i-1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                ManagerShow.inLeftOfLine("Message#"+Integer.toString(i + (nPage - 1) * postInOnePage)+":", "|", 80);
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else if (pv.getMessages().size()<postInOnePage && pv.getMessages().size()!=0){
//            System.out.println("im in 222222222222222222222222222222222222222");
            int lastPostInLastPage = pv.getMessages().size();
            for (int i = lastPostInLastPage; i >0 ; i--) {
                Messages.add(pv.getMessages().get(i-1));
            }
            for (int i = lastPostInLastPage; i > 0; i--) {
                if (i == lastPostInLastPage) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i-1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                ManagerShow.inLeftOfLine("Message#"+Integer.toString(i)+":", "|", 80);
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else if (pv.getMessages().size() != 0) { //bog
//            System.out.println("im in 3333333333333333333333333");
            int lastPostInLastPage =pv.getMessages().size()-postInOnePage;
//            System.out.println("lastPostInLastPage : "+lastPostInLastPage);
            for (int i = postInOnePage-1; i>=0; i--) {
                Messages.add(pv.getMessages().get(i));
            }
            for (int i = postInOnePage; i >0; i--) {
                if (i == postInOnePage) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i -1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                ManagerShow.inLeftOfLine("Message#"+Integer.toString(pv.getMessages().size()+i-postInOnePage)+":", "|", 80);
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else {
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.inMiddleOfLine("**  no message too show  **", "|", 80);
            ManagerShow.inMiddleOfLine(" send your first message :)", "|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.closeDownLine("|","_",80);
        }
    }
    public static void sendMessageProcessor(Pv pv, User user, Scanner in) throws SQLException, ClassNotFoundException {
        Boolean running = true;
        String input = new String();
        while (running) {
            ManagerShow.showSendMessagePage();
            input = in.nextLine();
            if (input.equals("1")) {
                System.out.println("Please enter you text. (You can press only Key-Enter to back to create post page. )");
                System.out.print("Text: ");
                input = in.nextLine();
                if (!input.isEmpty()) {
                    Message message = new Message(user, "text", input, true, false, false);
                    message.setIsPvOrGroup("pv",pv.getPvId());
                //  message.setPvOrGroupId();
                    message.addMessageToTable();
                    pv.addMessage(message);
                    System.out.println("Send successfully number of messages:"+pv.getMessages().size());
                    break;
                }
            } else if (input.equals("2")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("3")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("4")) {
                running = false;
            } else {
                System.out.println("invalid input :/");
            }
        }
    }
    public static void selectMessage(Pv pv, User user, Scanner in) throws SQLException, ClassNotFoundException {
        Boolean running = true;
        String input = new String();
        while (running) {
            ManagerShow.showSelectMessage();
            input = in.nextLine();
            if (input.isEmpty()){
                running=false;
            }
            else if (!input.matches("^\\d+$")) {
                System.out.println("invalid input :p");
            }
            else {
                int n = Integer.parseInt(input)-1;
                if (n >= pv.getMessages().size()) {
                    System.out.println("this Message not exist pls select other message ;| ");
                } else {
                    running = false;
//                    Boolean forMe=pv.getMessages().get(n).getSender().equals(user); //if for me true
                    selectMessageChoices(pv, user, in,pv.getMessages().get(pv.getMessages().size()-1-n));
                }
            }
        }
    }
    public static void selectMessageChoices(Pv pv, User user, Scanner in, Message message) throws SQLException, ClassNotFoundException {
        Boolean forMe = message.getSender().equals(user); //if for me true
        String input = new String();
        Boolean running = true;
        while (running) {
            ManagerShow.showSelectMessageChoices(forMe,message.getForward());
            ManagerShow.boxInLeft("Selected Message: ","|","_",80);
            ManagerShow.showMessage(message,forMe);
            System.out.println("input: ");
            input = in.nextLine();
            if (input.equals("1")) { //forward
                forwardMessage(user,message,in);
                return;
            } else if (input.equals("2")) { //reply
                sendMessageReplyProcessor(pv,user,in,message);
                running=false;
            }else if (input.equals("3") && !forMe){ //back
                System.out.println(":{} bye");
                running=false;
            }else if (input.equals("3") && forMe){ //delete forMe
                pv.removeMessage(message);
                System.out.println("delete :(");

                running=false;
            }else if (input.equals("4") && forMe && !message.getForward()){ //edit
                editMessage(message,in);
                running=false;
            }else if (input.equals("4") && forMe && message.getForward()){ //Back
                System.out.println(":{} bye");
                running=false;
            }else if (input.equals("5") && forMe){ //back
                System.out.println(":{} bye");
                running=false;
            }else {
                System.out.println("invalid input");
            }
        }
    }
    public static void forwardMessage(User forwarder, Message message, Scanner in) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            ManagerShow.showForward(forwarder,message);
            input=in.nextLine();
            if (input.equals("1")){ //Save Message
                saveMessage(message,forwarder);
                return;
            }else if (input.equals("2")){ //to Pvs
                forwardToPv(message,forwarder,in);
                return;
            }else if (input.equals("3")){
                forwardToGroup(message,forwarder,in);
                return;
            }else if (input.equals("4")){ //back
                System.out.println("pashiman :(");
                return;
            } else {
                System.out.println("invalid Input :(");
            }
        }
    }

    private static void forwardToPv(Message message, User forwarder, Scanner in) throws SQLException, ClassNotFoundException {
        ManagerShow.showPvs(forwarder);
        boolean running=true;
        while (running) {
            input=in.nextLine();
//            System.out.println(input+"***");
            if (forwarder.getPvs().size() == 0 && input.equals("1")) {
                return;
            } else if (forwarder.getPvs().size() != 0) {
                if (input.equals("1")) {
                    selectForwardToPv(message,forwarder,in);
                    return;
                }else if (input.equals("2")){
                    newPvForward(forwarder,in,message);
                    return;
                }else {
                    System.out.println("invalid Input :/");
                }
            }else {
                System.out.println("invalid Input :/");
            }
        }
    }
    public static void newPvForward(User user,Scanner in,Message message) throws SQLException, ClassNotFoundException {
        ManagerShow.showNewPv(user);
        boolean running=true;
        while (running){
            input=in.nextLine();
            if (user.getFollowers().size()>0){
                if (input.equals("1")){
                    while (running){
                        System.out.print("select Index From Followers : ((press Enter -> Back))");
                        input=in.nextLine();
                        if (input.matches("\\d+")){
                            if (Integer.parseInt(input)>0 && Integer.parseInt(input)<=user.getFollowers().size()){
                                ShowPv.main(user,user.getFollowers().get(Integer.parseInt(input)-1),in);
                                return;
                            }else {
                                System.out.println("Index Out Of Bound :(");
                            }
                        }else if (input.isEmpty()){
                            break;
                        }else {
                            System.out.println("invalid Input Format :[");
                        }
                    }
                }else if (input.equals("2")){
                    while (running){
                        System.out.println("type User Id For Adding to the Group : ((if you want to back just press enter))");
                        input=in.nextLine();
                        if (input.isEmpty()){
                            break;
                        }
                        if (addUserPvForward(input,user,in)){
                            forwardMessageToPv(user,user.getPv(DataBase.getUserWithId(input)),message);
                            System.out.println("Message added :P");
                            return;
                        }
                    }
                }else if (input.equals("3")){
                    return;
                }else {
                    System.out.println("invalid Input :^!");
                }
            }else {
                if (input.equals("1")){
                    while (running){
                        System.out.println("type User Id For Adding to the Group : ((if you want to back just press enter))");
                        input=in.nextLine();
                        if (input.isEmpty()){
                            break;
                        }
                        if (addUserPvForward(input,user,in)){
                            forwardMessageToPv(user,user.getPv(DataBase.getUserWithId(input)),message);
                            System.out.println("Message added :P");
                            return;
                        }
                    }
                }else if (input.equals("2")){
                    return;
                }else {
                    System.out.println("invalid Input :^!");
                }
            }
        }

    }
    public static   boolean addUserPvForward(String input, User me,Scanner in) throws SQLException, ClassNotFoundException {
        User user= DataBase.getUserWithId(input);
        if (user==null){
            System.out.println("Id Not found :|");
            return false;
        }else if (user.getLinkedPvs().values().contains(user)){
            System.out.println("Pv Founded :)");
            return true;
        }else {
            me.addPv(DataBase.getUserWithId(input));
            System.out.println("Pv Maked :u");
            return true;
        }
    }

    private static void forwardToGroup(Message message, User forwarder, Scanner in) throws SQLException, ClassNotFoundException {
        ManagerShow.showGroupsForward(forwarder);
        boolean running=true;
        while (running) {
            input=in.nextLine();
//            System.out.println(input+"***");
            if (forwarder.getGroups().size() == 0 && input.equals("1")) {
                return;
            } else if (forwarder.getGroups().size() != 0) {
                if (input.equals("1")) {
                    selectForwardToGroup(message,forwarder,in);
                    return;
                }else if (input.equals("2")){
                    return;
                }else {
                    System.out.println("invalid Input :/");
                }
            }else if (input.equals("1")){
                return;
            }else {
                System.out.println("invalid Input");
            }
        }
    }
    private static void selectForwardToGroup(Message message, User forwarder, Scanner in) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            System.out.println("select a Pv with Index:");
            input = in.nextLine();
            if (input.matches("\\d+")) {
                if (Integer.parseInt(input) - 1 < forwarder.getGroups().size()) {
                    forwardMessageToGroup(forwarder, forwarder.getGroups().get(Integer.parseInt(input) - 1), message);
                    return;
                } else {
                    System.out.println("invalid Index Input :/");
                }
            } else {
                System.out.println("invalid Index Input :/");
            }
        }
    }
    private static void forwardMessageToGroup(User forwarder, Group group, Message message) throws SQLException, ClassNotFoundException {
        Message messageForward=new Message(forwarder,message.getFormat(),message.getContent(),true,true,false);
        if (!message.getForward()) {
            messageForward.setForwardFrom(message.getSender());
        }else {
            messageForward.setForwardFrom(message.getForwardFrom());
        }
        messageForward.setIsPvOrGroup("group",group.getSqlId());
     //  messageForward.setPvOrGroupId();
       messageForward.addMessageToTable();
        group.addMessage(messageForward);
    }
    private static void selectForwardToPv(Message message, User forwarder, Scanner in) throws SQLException, ClassNotFoundException {
        boolean havaSavedMessage=false;
        int IndexOfSavedMessageInPvs=0;
        int q=-1;
        for (int i=0;i<forwarder.getLinkedPvs().size();i++){
            if (forwarder.getLinkedPvs().get(forwarder.getPvs().get(i))==forwarder){
                havaSavedMessage=true;
                IndexOfSavedMessageInPvs=i;
                q=0;
                break;
            }
        }
        boolean running=true;
        while (running) {
            System.out.println("select a Pv with Index:");
            input=in.nextLine();
            if (input.matches("\\d+")){
                if (input.equals("0") && havaSavedMessage){
                    saveMessage(message,forwarder);
                    return;
                }
                else if (Integer.parseInt(input)<forwarder.getPvs().size() && havaSavedMessage){
                    if (Integer.parseInt(input)<=IndexOfSavedMessageInPvs&&Integer.parseInt(input)>0){
                        forwardMessageToPv(forwarder,forwarder.getPvs().get(Integer.parseInt(input)-1),message);
                        return;
                    }else if (Integer.parseInt(input)-1<forwarder.getPvs().size()){
                        forwardMessageToPv(forwarder,forwarder.getPvs().get(Integer.parseInt(input)+q),message);
                        return;
                    }else {
                        System.out.println("invalid Index Input :/");
                    }
                }else if (Integer.parseInt(input)<=forwarder.getPvs().size()){
                    if (Integer.parseInt(input)-1<forwarder.getPvs().size()){
                        forwardMessageToPv(forwarder,forwarder.getPvs().get(Integer.parseInt(input)+q),message);
                        return;
                    }else {
                        System.out.println("invalid Index Input :/");
                    }
                }
                else {
                    System.out.println("invalid Index Input :/");
                }
            }
            else {
                System.out.println("invalid Input Format :/");
            }
        }
    }
    private static void forwardMessageToPv(User forwarder,Pv pv, Message message) throws SQLException, ClassNotFoundException {
        Message messageForward=new Message(forwarder,message.getFormat(),message.getContent(),true,true,false);
        if (!message.getForward()) {
            messageForward.setForwardFrom(message.getSender());
        }else {
            messageForward.setForwardFrom(message.getForwardFrom());
        }
    //   messageForward.setPvOrGroupId();
        messageForward.setIsPvOrGroup("pv",pv.getPvId());
        messageForward.addMessageToTable();
        pv.addMessage(messageForward);
    }
    private static void saveMessage(Message message, User forwarder) throws SQLException, ClassNotFoundException {
        if (forwarder.getPv(forwarder)==null){
            forwarder.addPv(forwarder);
        }
        Message messageForward=new Message(forwarder,message.getFormat(),message.getContent(),true,true,false);
     // messageForward.setPvOrGroupId();
        messageForward.setForwardFrom(message.getSender());
        messageForward.setIsPvOrGroup("pv",forwarder.getPv(forwarder).getPvId());
       messageForward.addMessageToTable();
        forwarder.getPv(forwarder).addMessage(messageForward);
    }
    public static void sendMessageReplyProcessor(Pv pv, User user, Scanner in,Message message) throws SQLException, ClassNotFoundException {
        Boolean running = true;
        String input = new String();
        while (running) {
            ManagerShow.showSendReplyMessagePage();
            System.out.println("input: ");
            input = in.nextLine();
            if (input.equals("1")) {
                System.out.println("Please enter you text. (You can press only Key-Enter to back to create post page. )");
                System.out.print("Text: ");
                input = in.nextLine();
                if (!input.isEmpty()) {
                    Message messageReply = new Message(user, "text", input, true, false, true);
                    messageReply.setReplyMessage(message);
                    System.out.println("messageReply.getReply(): "+messageReply.getReply());
                    messageReply.setIsPvOrGroup("pv",pv.getPvId());
                    messageReply.addMessageToTable();
                   // messageReply.setPvOrGroupId();
                    pv.addMessage(messageReply);
                    System.out.println("Reply successfully");
                    break;
                }
            } else if (input.equals("2")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("3")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("4")) {
                running = false;
            } else {
                System.out.println("invalid input :/\\");
            }
        }
    }
    public static void editMessage(Message message,Scanner in) throws SQLException, ClassNotFoundException {
        Boolean running = true;
        String input = new String();
        while (running) {
            ManagerShow.showEditMessagePage(message);
            input = in.nextLine();
            if (input.equals("1")) {
                System.out.println("Please enter your text. (You can press only Key-Enter to back to create post page. )");
                System.out.print("Text: ");
                input = in.nextLine();
                if (!input.isEmpty()) {
                    message.setEdited(true);
                    message.setContent(input);
                    UpdateSqlTable.editMessage(message);
                    System.out.println("edit successfully");
                    break;
                }
            } else if (input.equals("2")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("3")) {
                System.out.println(":) Coming soon ...");
            } else if (input.equals("4")) {
                running = false;
            } else {
                System.out.println("invalid input :/\\");
            }
        }
    }
    public static void selectPv(Scanner in,User user) throws SQLException, ClassNotFoundException {
        boolean havaSavedMessage=false;
        int IndexOfSavedMessageInPvs=0;
        for (int i=0;i<user.getLinkedPvs().size();i++){
            if (user.getLinkedPvs().get(user.getPvs().get(i))==user){
                havaSavedMessage=true;
                IndexOfSavedMessageInPvs=i;
                break;
            }
        }
        boolean running=true;
        while (running) {
            System.out.println("select a Pv with Index:");
            input=in.nextLine();
            if (input.matches("\\d+")){
                if (input.equals("0") && havaSavedMessage){
                    ShowPv.main(user,user,in);
                    return;
                }
                else if (Integer.parseInt(input)<user.getPvs().size() && havaSavedMessage){
                    if (Integer.parseInt(input)-1<IndexOfSavedMessageInPvs&&Integer.parseInt(input)-1>=0){
                        ShowPv.main(user,user.getLinkedPvs().get(user.getPvs().get(Integer.parseInt(input)-1)),in);
                        return;
                    }else if (Integer.parseInt(input)-1<user.getPvs().size()){
                        ShowPv.main(user,user.getLinkedPvs().get(user.getPvs().get(Integer.parseInt(input))),in);
                        return;
                    }else {
                        System.out.println("invalid Index Input :/");
                    }
                }else if (Integer.parseInt(input)<=user.getPvs().size()){
                    if (Integer.parseInt(input)-1<user.getPvs().size()){
                        ShowPv.main(user,user.getLinkedPvs().get(user.getPvs().get(Integer.parseInt(input)-1)),in);
                        return;
                    }else {
                        System.out.println("invalid Index Input :/");
                    }
                }
                else {
                    System.out.println("invalid Index Input :/");
                }
            }
            else {
                System.out.println("invalid Input Format :/");
            }
        }
    }

    public static void showMessagesInSearchMode(Pv pv, User user, int nPage, int postInOnePage,int selectIndex,ArrayList<Integer> indexFindMessages) {
        ArrayList<Message> Messages = new ArrayList<Message>();
//        System.out.println("nPage: "+nPage);
        if (pv.getMessages().size() >= nPage * postInOnePage) {
//            System.out.println("i am in 1111111111111111111111111111111111");
            for (int i = 0; i < postInOnePage; i++) {
                Messages.add(pv.getMessages().get(pv.getMessages().size()-1-(i+ (nPage - 1) * postInOnePage)));
            }
            for (int i = postInOnePage; i > 0; i--) {
                if (i == postInOnePage) { //Display Date
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i-1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                if (selectIndex!=0 && Messages.get(i-1)==pv.getMessages().get(indexFindMessages.get(selectIndex-1))){
                    ManagerShow.inLeftOfLine("*** Message#" + Integer.toString(i + (nPage - 1) * postInOnePage) + " *** ["+selectIndex+"] :", "|", 80);
                }else {
                    ManagerShow.inLeftOfLine("Message#" + Integer.toString(i + (nPage - 1) * postInOnePage) + ":", "|", 80);
                }
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else if (pv.getMessages().size()<postInOnePage && pv.getMessages().size()!=0){
//            System.out.println("im in 222222222222222222222222222222222222222");
            int lastPostInLastPage = pv.getMessages().size();
            for (int i = lastPostInLastPage; i >0 ; i--) {
                Messages.add(pv.getMessages().get(i-1));
            }
            for (int i = lastPostInLastPage; i > 0; i--) {
                if (i == lastPostInLastPage) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i-1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                if (selectIndex!=0 && Messages.get(i-1)==pv.getMessages().get(indexFindMessages.get(selectIndex-1))){
                    ManagerShow.inLeftOfLine("*** Message#" + Integer.toString(i + (nPage - 1) * postInOnePage) + " *** ["+selectIndex+"] :", "|", 80);
                }else {
                    ManagerShow.inLeftOfLine("Message#" + Integer.toString(i) + ":", "|", 80);
                }
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else if (pv.getMessages().size() != 0) { //bog
//            System.out.println("im in 3333333333333333333333333");
            int lastPostInLastPage =pv.getMessages().size()-postInOnePage;
//            System.out.println("lastPostInLastPage : "+lastPostInLastPage);
            for (int i = postInOnePage-1; i>=0; i--) {
                Messages.add(pv.getMessages().get(i));
            }
            for (int i = postInOnePage; i >0; i--) {
                if (i == postInOnePage) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i -1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                if (selectIndex!=0 && Messages.get(i-1)==pv.getMessages().get(indexFindMessages.get(selectIndex-1))){
                    ManagerShow.inLeftOfLine("*** Message#" + Integer.toString(i + (nPage - 1) * postInOnePage) + " *** ["+selectIndex+"] :", "|", 80);
                }else {
                    ManagerShow.inLeftOfLine("Message#" + Integer.toString(pv.getMessages().size() + i - postInOnePage) + ":", "|", 80);
                }
                ManagerShow.showMessage(Messages.get(i-1), user == Messages.get(i-1).getSender());
            }
        }
        else {
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.inMiddleOfLine("**  no message too show  **", "|", 80);
            ManagerShow.inMiddleOfLine(" send your first message :)", "|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.showEmpty("|", 80);
            ManagerShow.closeDownLine("|","_",80);
        }
    }
}