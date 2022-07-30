package ShowClass;

import DataBase.UpdateSqlTable;
import Manager.ManagerPv;
import Manager.ManagerShow;
import component.Message;
import component.Pv;
import component.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowPv {
    public static Scanner in;
    public static void main(User user1,User user2,Scanner in) throws SQLException, ClassNotFoundException {
        ShowPv.in=in;
        String input=new String();
        int postInOnePage=5;
        if (user1==user2){//save message
            if (!user1.getLinkedPvs().values().contains(user2)) {
                user1.addPv(user1);
            }
        }else {
            if (!user1.getLinkedPvs().values().contains(user2)) {
                user1.addPv(user2);
            }
        }
        int nPage=1;
        Pv pv=user1.getPv(user2);
        boolean running=true;
        while (running){
            ManagerShow.showPv(pv,user1,nPage,postInOnePage);
            ManagerShow.showPvChoicesIn(pv,user1);
            input=in.nextLine();
            if (pv.getBlock()){
                if (input.equals("1")){
                    if (nPage!=Math.ceil((double)pv.getMessages().size()/postInOnePage)){
                        nPage++;
                    }
                }else if (input.equals("2")){
                    if (nPage!=1){
                        nPage--;
                    }
                }else if (input.equals("3")){ //Block
                    if (user1==pv.getBlocker()){
                        UpdateSqlTable.setBlockToPv(pv,user1,false);
                        pv.setBlock(false,user1);
                    }
                    else {
                        System.out.println("you can't change it :(");
                    }
                } else if (input.equals("4")){
                    nPage=searchInPv(user1,pv);
                }else if (input.equals("5")){ //DELETE Chat
                    UpdateSqlTable.deletePv(pv);
                    user1.removePv(pv);
                    System.out.println("Chat Deleted :[");
                    return;
                }else if (input.equals("6")){ //Back
                    if (user1.getPvs().indexOf(pv)>=user1.getReadMessagePv().size()){
                        user1.getReadMessagePv().add(user1.getPvs().indexOf(pv), pv.getMessages().size());
                        UpdateSqlTable.setReadMessagePv(pv,user1);
                    }else {
                        user1.getReadMessagePv().set(user1.getPvs().indexOf(pv), pv.getMessages().size());
                        UpdateSqlTable.setReadMessagePv(pv,user1);
                    }
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
            else {
                if (input.equals("1")){ //send message
                    ManagerPv.sendMessageProcessor(pv,user1,in);
                }else if (input.equals("2")){ //select message
                    ManagerPv.selectMessage(pv,user1,in);
                }else if (input.equals("3")){
                    if (nPage<Math.ceil((double)pv.getMessages().size()/postInOnePage)){
                        nPage++;
                    }
                }else if (input.equals("4")){
                    if (nPage!=1){
                        nPage--;
                    }
                }else if (input.equals("5")){ //Block
                    UpdateSqlTable.setBlockToPv(pv,user1,true);
                    pv.setBlock(true,user1);
                }else if (input.equals("6")){ //Search
                    nPage=searchInPv(user1,pv);
                }else if (input.equals("7")){
                    UpdateSqlTable.deletePv(pv);
                    user1.removePv(pv);
                    System.out.println("Chat Deleted :[");
                    return;
                }
                else if (input.equals("8")){
                    if (user1.getPvs().indexOf(pv)>=user1.getReadMessagePv().size()){//back
                        user1.getReadMessagePv().add(user1.getPvs().indexOf(pv), pv.getMessages().size());
                        UpdateSqlTable.setReadMessagePv(pv,user1);
                    }else {
                        user1.getReadMessagePv().set(user1.getPvs().indexOf(pv), pv.getMessages().size());
                        UpdateSqlTable.setReadMessagePv(pv,user1);
                    }
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
        }

    }

    public static ArrayList<Integer> findMessage(String key,Pv pv){
        ArrayList<Message> messages=pv.getMessages();
        ArrayList<Integer> indexes=new ArrayList<>();
        for (int i=messages.size()-1;i>=0;i--){
            ArrayList<String> sentences=new ArrayList<>();
            sentences.add(messages.get(i).getContent());
            if (find(sentences,key)){
                indexes.add(i);
            }
        }
        return indexes;
    }
    public static boolean find(ArrayList<String> sentences, String key) {
        if (key.matches("(^\")(.+)(\"$)")) { //exact search
            key=key.replaceAll("\"","");
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);
                if (sentence.matches("(.*)" + "(\\s+)" + key + "(\\s+)" + "(.*)") ||
                        sentence.matches("^" + key + "(\\s+)" + "(.*)") ||
                        sentence.matches("(.*)" + "(\\s+)" + key + "$") ||
                        sentence.matches("^"+key+"$")) {
                    return true;
                }
            }
            return false;
        }else{
            for (int i = 0; i < sentences.size(); i++) { // week search
                String sentence = sentences.get(i);
                if (sentence.matches("(.*)"+ key +"(.*)")){
                    return true;
                }
            }
            return false;
        }
    }
    private static int searchInPv(User user1, Pv pv) throws SQLException, ClassNotFoundException {
        Integer postInOnePage=5;
        int nPage=1;
        boolean running=true;
        String input=new String();
        String key=new String();
        System.out.println("type your Search key to find that if you want to do exact search { \"key\" } : ((Or press Enter to Back))");
        key=in.nextLine();
        if (key.isEmpty()){
            return 1;
        }
        int selectIndex=0; //from 1 | 0==not find
        while (running) {
            ArrayList<Integer> indexFindMessages=new ArrayList<>();
            indexFindMessages=findMessage(key,pv);
            if (selectIndex==0){
                nPage=1;
            }else {
                nPage= (int) Math.ceil((pv.getMessages().size()-(indexFindMessages.get(selectIndex-1)))/postInOnePage.doubleValue());
                System.out.println("nPage="+nPage);
                System.out.println("selectIndex="+selectIndex);
                System.out.println(indexFindMessages.get(selectIndex-1));
                System.out.println((Math.ceil((indexFindMessages.get(selectIndex-1)+1))/postInOnePage.doubleValue()));
            }
            ManagerShow.showPvInSearchMode(pv,user1,selectIndex,postInOnePage,indexFindMessages,nPage,key);
            input=in.nextLine();
            if (pv.getBlock()){
                if (input.equals("1")){ //Next
                    if (selectIndex<indexFindMessages.size()){
                        selectIndex++;
                    }
                }else if (input.equals("2")){ //Back
                    if (selectIndex>1){
                        selectIndex--;
                    }
                }else if (input.equals("3")){ //Block
                    if (user1==pv.getBlocker()){
                        UpdateSqlTable.setBlockToPv(pv,user1,false);
                        pv.setBlock(false,user1);
                    }
                    else {
                        System.out.println("you can't change it :(");
                    }
                } else if (input.equals("4")){ //Change Search
                    System.out.println("type your Search key to find that if you want to do exact search { \"key\" } : ((Or press Enter to Back))");
                    input=in.nextLine();
                    if (!input.isEmpty()){
                        key=input;
                        selectIndex=0;
                    }
                }else if (input.equals("5")){
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
            else {
                if (input.equals("1")){ //send message
                    ManagerPv.sendMessageProcessor(pv,user1,in);
                }else if (input.equals("2")){ //select message
                    ManagerPv.selectMessage(pv,user1,in);
                }else if (input.equals("3")){ //Next
                    if (selectIndex<indexFindMessages.size()){
                        selectIndex++;
                    }
                }else if (input.equals("4")){ //Back
                    if (selectIndex>1){
                        selectIndex--;
                    }
                }else if (input.equals("5")){ //Block
                    UpdateSqlTable.setBlockToPv(pv,user1,true);
                    pv.setBlock(true,user1);
                }else if (input.equals("6")){ //Change Search
                    System.out.println("type your Search key to find that if you want to do exact search { \"key\" } : ((Or press Enter to Back))");
                    input=in.nextLine();
                    if (!input.isEmpty()){
                        key=input;
                        selectIndex=0;
                    }                }
                else if (input.equals("7")){
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
        }
        return nPage;
    }
}
