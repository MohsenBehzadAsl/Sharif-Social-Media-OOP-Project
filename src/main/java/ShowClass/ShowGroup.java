package ShowClass;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;
import Manager.ManagerGroup;
import Manager.ManagerShow;
import component.Group;
import component.Message;
import component.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
public class ShowGroup {
    public static User user;
    public static String input=new String();
    public static Scanner in;

    public static void main() throws SQLException, ClassNotFoundException {
        processMain();
    }
    public static void processMain() throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running) {
            ManagerShow.showGroups(user);
            input=in.nextLine();
            if (user.getGroups().size()==0){
                if (input.equals("1")){
                    newGroup();
                }else if (input.equals("2")){
                    ShowHomepage showHomepage=new ShowHomepage(user,in);
                    showHomepage.main();
                }
                else {
                    System.out.println("invalid Input");
                }
            }
            else {
                if (input.equals("1")){
                    selectGroup();
                }else if (input.equals("2")){
                    newGroup();
                }else if (input.equals("3")){
                    ShowHomepage showHomepage=new ShowHomepage(user,in);
                    showHomepage.main();
                }else {
                    System.out.println("invalid Input");
                }
            }
        }
    }
    private static void selectGroup() throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            System.out.println("select a Group with Index: ((if you want to Back enter 0))");
            input = in.nextLine();
            if (input.matches("\\d+")) {
                if (Integer.parseInt(input)>0&&Integer.parseInt(input)-1<user.getGroups().size()){
                    showAGroup(user.getGroups().get(Integer.parseInt(input)-1),user);
                    return;
                }
                else if (input.equals("0")){
                    return;
                }
                else{
                    System.out.println("invalid Index Input :/");
                }
            } else {
                System.out.println("invalid Input Format :/");
            }
        }
    }
    private static void showAGroup(Group group, User user) throws SQLException, ClassNotFoundException {
        int postInOnePage=5;
//        if (user1==user2){//save message
//            if (!user1.getLinkedPvs().values().contains(user2)) {
//                user1.addPv(user1);
//            }
//        }else {
//            if (!user1.getLinkedPvs().values().contains(user2)) {
//                user1.addPv(user2);
//            }
//        }
        int nPage=1;
        boolean running=true;
        while (running){
            ManagerShow.showGroup(group,user,nPage,postInOnePage);
            ManagerShow.showGroupChoicesIn(group,user);
            input=in.nextLine();
            if (group.getLinkedMembers().get(user) ||(group.getBanGroup()&&(!group.getAdmins().contains(user)))){
                if (input.equals("1")){
                    if (nPage!=Math.ceil((double)group.getMessages().size()/postInOnePage)){
                        nPage++;
                    }
                }else if (input.equals("2")){
                    if (nPage!=1){
                        nPage--;
                    }
                }else if (input.equals("3")){ //Setting
                    settingGroup(group,user);
                }else if (input.equals("4")){ //Search
                    nPage=groupSearch(group,user);
                }else if (input.equals("5")){ //Back
                    if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {

                        user.getReadMessageGroup().add(group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }else {
                        user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
            else {
                if (input.equals("1")){ //send message
                    ManagerGroup.sendMessageProcessor(group,user,in);
                }else if (input.equals("2")){ //select message
                    ManagerGroup.selectMessage(group,user,in);
                }else if (input.equals("3")){
                    if (nPage<Math.ceil((double)group.getMessages().size()/postInOnePage)){
                        nPage++;
                    }
                }else if (input.equals("4")){
                    if (nPage!=1){
                        nPage--;
                    }
                }else if (input.equals("5")){ //Setting
                    settingGroup(group,user);
                }else if (input.equals("6")){
                    nPage=groupSearch(group,user);
                }else if ((input.equals("7")&&group.getOwner()!=user)){
                    if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {
                        user.getReadMessageGroup().add(group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }else {
                        user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }
                    running=false;
                }else if (input.equals("7") && group.getOwner()==user){
                    if (group.getBanGroup()) {
                        group.setBanGroup(false);
                    }else {
                        group.setBanGroup(true);
                    }
                }else if (input.equals("8") && group.getOwner()==user){//back
                    if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {
                        user.getReadMessageGroup().add(group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }else {
                        user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                        UpdateSqlTable.setReadMessageGroup(group,user);
                    }
                    running=false;
                }else {
                    System.out.println("invalid input :|");
                }
            }
        }

    }
    public static ArrayList<Integer> findMessage(String key,Group group){
        ArrayList<Message> messages=group.getMessages();
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
    private static int groupSearch(Group group, User user) throws SQLException, ClassNotFoundException {
        int postInOnePage=5;

        int nPage=1;
        boolean running=true;
        String key=new String();
        System.out.println("type your Search key to find that if you want to do exact search { \"key\" } : ((Or press Enter to Back))");
        key=in.nextLine();
        if (key.isEmpty()){
            return 1;
        }
        int selectIndex=0; //from 1 | 0==not find
        while (running){
        ManagerShow.showGroupInSearchMode(group,user,nPage,postInOnePage);
        input=in.nextLine();
        if (group.getLinkedMembers().get(user) ||(group.getBanGroup()&&(!group.getAdmins().contains(user)))){
            if (input.equals("1")){
                if (nPage!=Math.ceil((double)group.getMessages().size()/postInOnePage)){
                    nPage++;
                }
            }else if (input.equals("2")){
                if (nPage!=1){
                    nPage--;
                }
            }else if (input.equals("3")){ //Setting
                settingGroup(group,user);
            }else if (input.equals("4")){ //Search
                nPage=groupSearch(group,user);
            }else if (input.equals("5")){ //Back
                if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {
                    user.getReadMessageGroup().add(group.getMessages().size());
                }else {
                    user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                }
                running=false;
            }else {
                System.out.println("invalid input :|");
            }
        }
        else {
            if (input.equals("1")){ //send message
                ManagerGroup.sendMessageProcessor(group,user,in);
            }else if (input.equals("2")){ //select message
                ManagerGroup.selectMessage(group,user,in);
            }else if (input.equals("3")){
                if (nPage<Math.ceil((double)group.getMessages().size()/postInOnePage)){
                    nPage++;
                }
            }else if (input.equals("4")){
                if (nPage!=1){
                    nPage--;
                }
            }else if (input.equals("5")){ //Setting
                settingGroup(group,user);
            }else if (input.equals("6")){
                nPage=groupSearch(group,user);
            }else if ((input.equals("7")&&group.getOwner()!=user)){
                if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {
                    user.getReadMessageGroup().add(group.getMessages().size());
                }else {
                    user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                }
                running=false;
            }else if (input.equals("7") && group.getOwner()==user){
                if (group.getBanGroup()) {
                    group.setBanGroup(false);
                }else {
                    group.setBanGroup(true);
                }
            }else if (input.equals("8") && group.getOwner()==user){
                if (user.getReadMessageGroup().size()-1<user.getGroups().indexOf(group)) {
                    user.getReadMessageGroup().add(group.getMessages().size());
                }else {
                    user.getReadMessageGroup().set(user.getGroups().indexOf(group), group.getMessages().size());
                }
                running=false;
            }else {
                System.out.println("invalid input :|");
            }
        }
    }
    return nPage;
}

    private static void changeBio(Group group, User user) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            System.out.println("type new Bio For Group : ((if you want to back jost press enter))");
            input=in.nextLine();
            if (input.isEmpty()){
                return;
            }else {
                group.setBio(input);
              UpdateSqlTable.setBioGroupToTable(group,input);
                return;
            }
        }
    }
    private static void settingGroup(Group group, User user) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running) {
            ManagerShow.showSettingGroup(group, user);
            input=in.nextLine();
            if (input.equals("1")){
                showMembers(group,user);
            }else if (input.equals("2")){
                leaveGroup(group,user);
                main();
                return;
            }else if (input.equals("3")){
                if (group.getAdmins().contains(user)) {
                    changeName(group);
                }else {
                    return;
                }
            }else if (input.equals("4")){
                if (group.getAdmins().contains(user)) {
                    changeId(group);
                }else {
                    System.out.println("invalid Input");
                }
            }else if (input.equals("5")){
                if (group.getAdmins().contains(user) && group.getOwner()==user) {
                    changeBio(group,user);
                }else {
                    return;
                }
            }else if (input.equals("6")){
                if (group.getAdmins().contains(user) && group.getOwner()==user) {
                    addNewMember(group,user);
                }else {
                    return;
                }
            }else if (input.equals("7")){
                if (group.getAdmins().contains(user)) {
                    return;
                }else {
                    System.out.println("invalid Input");
                }
            }else {
                System.out.println("invalid Input");
            }
        }
    }
    private static boolean addUserToGroup(String input, Group group) throws SQLException, ClassNotFoundException {
        User user= DataBase.getUserWithId(input);
        if (user==null){
            System.out.println("Id Not found :|");
            return false;
        }else if (user.getAddToGroupAbility() && (!group.getMembers().contains(user))){
            group.addMember(user);
            return true;
        }else if (group.getMembers().contains(user)){
            System.out.println("this user now is in group :@");
            return false;
        } else if (!user.getAddToGroupAbility()) {
            System.out.println("you can't add this user to group because Add To Group Ability of this user is false :[");
            return false;
        }
        return false;
    }
    private static void addNewMember(Group group, User user) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            System.out.println("type User Id For Adding to the Group : ((if you want to back jost press enter))");
            input=in.nextLine();
            if (input.isEmpty()){
                return;
            }
            if (addUserToGroup(input,group)){
                System.out.println("user added successfully :]");
                return;
            }
        }
    }
    private static void changeId(Group group) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            System.out.println("type new Id For Group : ((if you want to back jost press enter))");
            input=in.nextLine();
            if (input.isEmpty()){
                return;
            }if (!invalidId(input)){
                group.setGroupId(input);
                UpdateSqlTable.setGroupIdToTable(group,input);
                return;
            }
        }
    }
    private static boolean invalidId(String input) {

        return false;
    }
    private static void changeName(Group group) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            System.out.println("type new Name For Group : ((if you want to back jost press enter))");
            input=in.nextLine();
            if (input.isEmpty()){
                return;
            }else {
                group.setName(input);
                UpdateSqlTable.setGroupNameToTable(group,input);
                return;
            }
        }
    }
    private static void leaveGroup(Group group, User user) throws SQLException, ClassNotFoundException {
        System.out.println("): you lived Group :(");
        group.removeMember(user);
        UpdateSqlTable.leaveFromGroup(group,user);
    }
    private static void showMembers(Group group, User user) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running) {
            ManagerShow.showMembers(group, user);
            input=in.nextLine();
            if (input.equals("1")){
                boolean running2=true;
                while (running2){
                    System.out.println("Select Member Index: (( press Enter to Back)) ");
                    input=in.nextLine();
                    if (input.matches("\\d*") && !input.isEmpty()){
                        if (Integer.parseInt(input)-1<group.getMembers().size() && Integer.parseInt(input)>0){
                            selectMember(group,user,group.getMembers().get(Integer.parseInt(input)-1));
                            running2=false;
                        }else {
                            System.out.println("Index Out Of Bound :P");
                        }
                    }else if (input.isEmpty()){
                        return;
                    }else {
                        System.out.println("invalid Input :(");
                    }
                }
            }else if (input.equals("2")){
                return;
            }else {
                System.out.println("invalid Input");
            }
        }
    }
    private static void selectMember(Group group, User user,User select) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running) {
            ManagerShow.selectMember(group, user, select);
            input=in.nextLine();
            if (input.equals("1")){
                ShowPv.main(user,select,in);
                return;
            }else if (input.equals("2")){
                //visit Page
            }else if ((!group.getAdmins().contains(user) || user==select)&&input.equals("3")){
                return;
            }else if (group.getAdmins().contains(user) && group.getOwner()!=user){
                if ((group.getAdmins().contains(select)) && input.equals("3")){
                    return;
                }else {
                    if (input.equals("3")){
                        if (group.getLinkedMembers().get(select)){
                            group.getLinkedMembers().replace(select, false);
                            UpdateSqlTable.setBanMemberToTable(group,select,false);
                            System.out.println("UnBanned :)");
                        }else {
                            group.getLinkedMembers().replace(select, true);
                            UpdateSqlTable.setBanMemberToTable(group,select,true);
                            System.out.println("Banned :(");
                        }
                    }else if (input.equals("4")){
                        group.removeMember(select);
                        System.out.println("removed Member :%");
                        return;
                    }else if (input.equals("5")) {
                        return;
                    }else {
                        System.out.println("invalid Input :o");
                    }
                }
            }else if (group.getOwner()==user){
                if (input.equals("3")){
                    if (group.getLinkedMembers().get(select)){
                        group.getLinkedMembers().replace(select, false);
                        UpdateSqlTable.setBanMemberToTable(group,select,false);
                        System.out.println("UnBanned :)");
                    }else {
                        group.getLinkedMembers().replace(select, true);
                        UpdateSqlTable.setBanMemberToTable(group,select,true);
                        System.out.println("Banned :(");
                    }
                }else if (input.equals("4")){
                    group.removeMember(select);
                    System.out.println("removed Member :%");
                    return;
                }else if (input.equals("5")) {
                    if (group.getAdmins().contains(select)){
                        group.removeAdmin(select);
                        System.out.println("user is now Normal :^I");
                    }else {
                        group.addAdmin(select);
                    //    group.addGroupMemberToTable(group,select,"admin");

                        System.out.println("admin added :I");
                    }
                }else if (input.equals("6")) {
                    return;
                }else {
                    System.out.println("invalid Input :o");
                }
            }else {
                System.out.println("invalid Input");
            }
        }
    }
    private static void newGroup() throws SQLException, ClassNotFoundException {
        boolean running=true;
        String name=new String();
        String id=new String();
        ManagerShow.makeNewGroup();
        while (running) {
            System.out.print("Group Name:");
            name=in.nextLine();
            if (!name.isEmpty()){
                break;
            }
        }
        while (running){
            System.out.print("Group Id:");
            id=in.nextLine();
            if (!id.isEmpty()){
                break;
            }
        }
//        Group group=new Group(user,name,id);
//        user.addGroup(group);
//        showAGroup(group,user);
    }
}
