package Manager;

import component.Group;
import component.Message;
import component.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static Manager.ManagerPv.editMessage;
import static Manager.ManagerPv.forwardMessage;

public class ManagerGroup {

    public static void showMessages(Group group, User user, int nPage, int postInOnePage) { //npage --> page n om
        ArrayList<Message> Messages = new ArrayList<Message>();
//        System.out.println("nPage: "+nPage);
        if (group.getMessages().size() >= nPage * postInOnePage) {
//            System.out.println("i am in 1111111111111111111111111111111111");
            for (int i = 0; i < postInOnePage; i++) {
                Messages.add(group.getMessages().get(group.getMessages().size()-1-(i+ (nPage - 1) * postInOnePage)));
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
        else if (group.getMessages().size()<postInOnePage && group.getMessages().size()!=0){
//            System.out.println("im in 222222222222222222222222222222222222222");
            int lastPostInLastPage = group.getMessages().size();
            for (int i = lastPostInLastPage; i >0 ; i--) {
                Messages.add(group.getMessages().get(i-1));
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
        else if (group.getMessages().size() != 0) { //bog
//            System.out.println("im in 3333333333333333333333333");
            int lastPostInLastPage =group.getMessages().size()-postInOnePage;
//            System.out.println("lastPostInLastPage : "+lastPostInLastPage);
            for (int i = postInOnePage-1; i>=0; i--) {
                Messages.add(group.getMessages().get(i));
            }
            for (int i = postInOnePage; i >0; i--) {
                if (i == postInOnePage) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                } else if (Messages.get(i -1).getLocalDateTime().getDayOfYear() != Messages.get(i).getLocalDateTime().getDayOfYear()) {
                    ManagerShow.showDate(Messages.get(i-1).getLocalDateTime());
                }
                ManagerShow.closeDownLine("|","_",80);
                ManagerShow.inLeftOfLine("Message#"+Integer.toString(group.getMessages().size()+i-postInOnePage)+":", "|", 80);
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
        }
    }

    public static void sendMessageProcessor(Group group, User user, Scanner in) throws SQLException, ClassNotFoundException {
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
                    message.setIsPvOrGroup("group",group.getSqlId());
                    message.addMessageToTable();

                    group.addMessage(message);
                    System.out.println("Send successfully number of messages:"+group.getMessages().size());
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

    public static void selectMessage(Group group, User user, Scanner in) throws SQLException, ClassNotFoundException {
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
                if (n >= group.getMessages().size()) {
                    System.out.println("this Message not exist pls select other message ;| ");
                } else {
                    running = false;
                    selectMessageChoices(group, user, in,group.getMessages().get(group.getMessages().size()-1-n));
                }
            }
        }
    }
    public static void selectMessageChoices(Group group, User user, Scanner in, Message message) throws SQLException, ClassNotFoundException {
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
                sendMessageReplyProcessor(group,user,in,message);
                running=false;
            }else if (input.equals("3") && !forMe){ //back
                System.out.println(":{} bye");
                running=false;
            }else if (input.equals("3") && forMe){ //delete for me
                group.removeMessage(message);
                System.out.println("delete :(");
                running=false;
            }else if (input.equals("4") && forMe && !message.getForward()){ //edit
                editMessage(message,in);
                running=false;
            }else if (input.equals("4") && forMe && message.getForward()){ //Back
                System.out.println(":{} bye");
                running=false;
            }else if (input.equals("5") && forMe && !message.getForward()){ //back
                System.out.println(":{} bye");
                running=false;
            }else {
                System.out.println("invalid input");
            }
        }
    }
    public static void sendMessageReplyProcessor(Group group, User user, Scanner in,Message message) throws SQLException, ClassNotFoundException {
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
                    messageReply.setIsPvOrGroup("group",group.getSqlId());
                  //  messageReply.setPvOrGroupId();
                   messageReply.addMessageToTable();
                    System.out.println("messageReply.getReply(): "+messageReply.getReply());
                    group.addMessage(messageReply);
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


}
