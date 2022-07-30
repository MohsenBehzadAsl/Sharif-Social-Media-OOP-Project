package ShowClass;

import DataBase.DataBase;
import Manager.ManagerShow;
import component.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class ShowAnalyzePage {
    Scanner in;
    User user = new User();
    String input = new String();
    DataBase dataBase = new DataBase();
    private int num=0;

    public ShowAnalyzePage(User user, Scanner in) {
        System.out.println("i 1am in ShowAnalyzePage");
        this.user = user;
        this.in = in;
    }
    public void main() throws SQLException, ClassNotFoundException {
        boolean running = true;
        num=0;
        while (running){
            ManagerShow.showAnalyzePage(num,user);
            input = in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (user.getViewsFromPage().size()>0) {
                    System.out.println("Well,Please enter post number to select this post directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= user.getViewsFromPage().size()) {
                            LinkedHashMap<User, LocalDateTime> viewsFromPage=user.getViewsFromPage();
                            List<User> keyList = new ArrayList(viewsFromPage.keySet());
                            Collections.reverse(keyList);
                            keyList.get(Integer.parseInt(input)-1).getViewsFromPage().put(user, LocalDateTime.now());
                            ShowMyHomePost showMyHomePost=new ShowMyHomePost(user,in);
                            showMyHomePost.showOthersPage(user, keyList.get(Integer.parseInt(input)-1));
                        } else {
                            System.out.println("invalid input please enter a valid post number");
                        }
                    }
                }else{
                    System.out.println("There is no User to select.  :)");
                }
            } else if (input.equals("2")) {
                if (user.getViewsFromPage().size()<=num+10){
                    System.out.println("this page is last possible page.");
                }else{
                    num+=10;
                }
            } else if (input.equals("3")) {
                break;
            }
        }
    }

}