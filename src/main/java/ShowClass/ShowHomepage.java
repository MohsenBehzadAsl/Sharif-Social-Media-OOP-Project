package ShowClass;

import DataBase.DataBase;
import Manager.ManagerPv;
import Manager.ManagerShow;
import component.User;

import java.sql.SQLException;
import java.util.Scanner;

public class ShowHomepage {
     Scanner in;
     User user=new User();
     String input=new String();

    public ShowHomepage(User user,Scanner in){
        System.out.println("i am in ShowHomePage");
        this.user=user;
        this.in=in;
    }
    public  void main() throws SQLException, ClassNotFoundException {
        boolean logout=false;
        while (!logout){
           ManagerShow.showHomePageMain(user.getUserName(),user.getType(),user);
           input=in.nextLine();
           if (input.equals("9") && user.getType().equalsIgnoreCase("normal")){
               System.out.println("logout normal :)");
               logout=true;
           }
           else if (input.equals("10") &&user.getType().equalsIgnoreCase("business")){
               System.out.println("logout business :)");
               logout=true;
           }
           else if (input.equals("4")){ //Pv
               showPvsPage();
           }
           else if (input.equals("3")){ //Group
               showGroupPage();
           }
           else if (input.equals("2")){ //MainPage
               showMainPage();
           }
           else if (input.equals("1")){ //Home
               showMyHomePage();
           }
           else if (input.equals("5")){ //Explore
               showExplorePage();
           }
           else if (input.equals("6")){ //followers
               showFollowersPage();
           }
           else if (input.equals("7")){ //followings
               showFollowingsPage();
           }
           else if (input.equals("8")){ //setting
               showSettingPage();
           }
           else if (input.equals("9") &&user.getType().equalsIgnoreCase("business")){ //Analyse Post
               showAnalysePostPage();
           }
           else if (input.equals("10") &&user.getType().equalsIgnoreCase("normal")){
               ShowPv.main(user,user,in);
           }
        }
        ShowStartPage showLoginPage=new ShowStartPage(in);
        showLoginPage.processor();
    }
    private  void showAnalysePostPage() throws SQLException, ClassNotFoundException {
        ShowAnalyzePage showAnalyzePage=new ShowAnalyzePage(user,in);
        showAnalyzePage.main();
    }
    private  void showSettingPage() throws SQLException, ClassNotFoundException {
        ShowUserSetting showUserSetting=new ShowUserSetting(user,in);
        showUserSetting.showSetting();
    }
    private  void showFollowingsPage() throws SQLException, ClassNotFoundException {
        ShowFollowersFollowings showFollowersFollowings=new ShowFollowersFollowings(user,in);
        showFollowersFollowings.showFollowings(user);
    }
    private  void showFollowersPage() throws SQLException, ClassNotFoundException {
        ShowFollowersFollowings showFollowersFollowings=new ShowFollowersFollowings(user,in);
        showFollowersFollowings.showFollowers(user);
    }
    private  void showExplorePage() throws SQLException, ClassNotFoundException {
        ShowExplore showExplore=new ShowExplore(user,in);
        showExplore.showExplore();
    }
    private  void showMyHomePage() throws SQLException, ClassNotFoundException {
        ShowMyHomePost showMyHomePost=new ShowMyHomePost(user,in);
        showMyHomePost.ShowMyHome();
    }
    private  void showMainPage() throws SQLException, ClassNotFoundException {
        ShowFollowingPost showFollowingPost=new ShowFollowingPost(user,in);
        showFollowingPost.main();
    }
    public  void showPvsPage() throws SQLException, ClassNotFoundException {
        boolean running=true;
        while (running) {
            ManagerShow.showPvs(user);
            input=in.nextLine();
            if (user.getPvs().size() == 0) {
                if (input.equals("1")){
                    newPv(user);
                }else if (input.equals("2")){
                    return;
                }else {
                    System.out.println("invalid Input :/");
                }
            } else if (user.getPvs().size() != 0) {
                if (input.equals("1")) {
                    ManagerPv.selectPv(in,user);
                    return;
                }else if (input.equals("2")){
                    newPv(user);
                } else if (input.equals("3")) {
                    return;
                } else {
                    System.out.println("invalid Input :/");
                }
            }else {
                System.out.println("invalid Input :/");
            }
        }
    }

    private void newPv(User user) throws SQLException, ClassNotFoundException {
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
                        if (addUserPv(input,user)){
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
                        if (addUserPv(input,user)){
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
    private  boolean addUserPv(String input, User me) throws SQLException, ClassNotFoundException {
        User user= DataBase.getUserWithId(input);
        if (user==null){
            System.out.println("Id Not found :|");
            return false;
        }else if (user.getLinkedPvs().values().contains(user)){
            System.out.println("Pv Founded :)");
            ShowPv.main(me,user,in);
            return true;
        }else {
            System.out.println("Pv Maked :u");
            ShowPv.main(me,user,in);
            return true;
        }
    }


    public  void showGroupPage() throws SQLException, ClassNotFoundException {
        ShowGroup.user=user;
        ShowGroup.in=in;
        ShowGroup.main();
    }

}
