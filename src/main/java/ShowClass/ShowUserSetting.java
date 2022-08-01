package ShowClass;

import DataBase.DataBase;
import DataBase.UpdateSqlTable;
import Manager.ManagerLoginPage;
import Manager.ManagerShow;
import component.Post;
import component.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static DataBase.UpdateSqlTable.setUserNameToTable;

public class ShowUserSetting {
    Scanner in;
    User user;
    DataBase dataBase = new DataBase();
    String input = new String();
    ManagerLoginPage managerLoginPage;
    public int num=0;
    public ShowUserSetting(User user, Scanner in) {
        this.in=in;
        this.user=user;
        managerLoginPage=new ManagerLoginPage();
    }

    public  void showSetting() throws SQLException, ClassNotFoundException {
        showMySetting(user);
    }

    private void showMySetting(User user1) throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running){
            ArrayList<Post> posts=new ArrayList<>();
            for (Post post : dataBase.getPosts()) {
                if (post.getIsComment().equals("post") && post.getSender().equals(user1)){
                    posts.add(post);
                }
            }
            showMySettingHelp(user1,posts);
            input=in.nextLine();
          //  System.out.println(input);
            if (!input.matches("\\w+")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                System.out.println("Please enter you new UserName. (You can press only Key-Enter to back to setting page. )");
                System.out.print("New UserName: ");
                input=in.nextLine();
                if (!input.isEmpty()) {

                    setUserNameToTable(user,input);
                    user.setUserName(input);
                    System.out.println("Your name has changed successfully");
                }
            } else if (input.equals("2")) {
                while (true) {
                    System.out.println("Please enter you new ID. (You can press only Key-Enter to back to setting page. )");
                    System.out.print("New ID: ");
                    input=in.nextLine();
                    if (input.isEmpty()) {
                        break;
                    }if (!managerLoginPage.validID(input)){
                        dataBase.getUserIDs().remove(user1.getId());
                        dataBase.getUserIDs().add(input);
                        user.setId(input);
                        System.out.println("Your ID has changed successfully");break;
                    }
                }
            } else if (input.equals("3")) {
                System.out.println("Please enter you new Bio. (You can press only Key-Enter to back to setting page. )");
                System.out.print("New Bio: ");
                input=in.nextLine();
                if (!input.isEmpty()) {
                   UpdateSqlTable.setBioUserToTable(user,input);
                    user.setBio(input);
                    System.out.println("Your Bio has changed successfully");
                }
            } else if (input.equals("4")) {
                while (true) {
                    while (true) {
                        System.out.println("Please enter you new Password. (You can press only Key-Enter to back to setting page. )");
                        System.out.print("New Password: ");
                        input=in.nextLine();
                        if (input.isEmpty()) {
                            break;
                        }
                        if (managerLoginPage.validPassword(input)) {
                            break;
                        }
                    }
                    if (input.isEmpty()) {
                        break;
                    }
                    System.out.print("Enter Password again: ");
                    String password2 = in.nextLine();
                    if (password2.isEmpty()) {
                        break;
                    }
                    if (password2.equals(input)){
                       UpdateSqlTable.setPasswordToTable(user,input);
                        user.setPassword(input);
                        System.out.println("Your Password has changed successfully");break;
                    }
                    System.out.println("you have entered your check password incorrectly.\n");
                }
            } else if (input.equals("5")) {
                System.out.println("Please enter you new password Hint. (You can press only Key-Enter to back to setting page. )");
                System.out.print("New password Hint: ");
                input=in.nextLine();
                if (!input.isEmpty()) {
                    UpdateSqlTable.setPasswordHintToTable(user,input);
                    user.setPasswordHint(input);
                    System.out.println("Your password Hint has changed successfully");
                }
            } else if (input.equals("6")) {
                System.out.println("Please enter you new password Question. (You can press only Key-Enter to back to setting page. )");
                System.out.print("New password Question: ");
                input=in.nextLine();
                if (!input.isEmpty()) {
                    UpdateSqlTable.setPasswordQuestionToTable(user,input);
                    user.setQuestion(input);
                    System.out.println("Your password Question has changed successfully");
                }
            } else if (input.equals("7")){
                System.out.println("Please enter you new Answer of password Question. (You can press only Key-Enter to back to setting page. )");
                System.out.print("New Answer of password Question: ");
                input=in.nextLine();
                if (!input.isEmpty()) {
                    UpdateSqlTable.setAnswerOfPasswordQuestionToTable(user,input);
                    user.setAnsQuestion(input);
                    System.out.println("Your Answer of password Question has changed successfully");
                }
            } else if (input.equals("8")){
                if (user.getType().equals("Business")) {
                    UpdateSqlTable.setGenderToTable(user,"Normal");
                    user.setType("Normal");
                    System.out.println("Now, Your new type is Normal");
                }else {
                    UpdateSqlTable.setGenderToTable(user,"Business");
                    user.setType("Business");
                    System.out.println("Now, Your new type is Business");
                }
            } else if (input.equals("9")){

            }
            else if (input.equals("10")){
                if (user.getAddToGroupAbility()) {
                    UpdateSqlTable.setAddToGroupAbilityToTable(user,false);
                    user.setAddToGroupAbility(false);
                    System.out.println("Now, You are not able to be added to any group");
                }else {
                    user.setAddToGroupAbility(true);
                    UpdateSqlTable.setAddToGroupAbilityToTable(user,true);
                    System.out.println("Now, Your are able to be added to a group");
                }
            }
            else if (input.equals("11")){
                return;
            }
            else {
                System.out.println("invalid input");
            }

        }
    }
    private void showMySettingHelp(User user1, ArrayList<Post> posts) {
        ManagerShow.showMypageSetting(user1,posts);
//        if (posts.size() == 0) {
//            System.out.println("this user haven't launched any post yet");
//        } else {
//            Collections.sort(posts, new Comparator<Post>() {
//                @Override
//                public int compare(Post o1, Post o2) {
//                    if(o1.getLocalDateTime().isAfter(o2.getLocalDateTime())){
//                        return -1;
//                    }
//                    return 1;
//                }
//            });
//            for (int i=num;i<num+5 && i<posts.size();i++){
//                posts.get(i).getViews().put(user, LocalDateTime.now());
//                ManagerShow.boxInLeft("Post#"+(i+1)+" : ","|","_",80);
//                ManagerShow.showPost(posts.get(i));
//            }
//            ManagerShow.closeDownLine("|","*",80);
//
//        }
        ManagerShow.showSettingPageChoicesIn();
    }


}
