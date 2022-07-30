package ShowClass;

import DataBase.DataBase;
import Manager.AdRecommender;
import Manager.ManagerPv;
import Manager.ManagerShow;
import Manager.UserRecommender;
import component.Comment;
import component.Message;
import component.Post;
import component.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class ShowExplore {
    // only normal posts from normal users
    Scanner in;
    User user;
    DataBase dataBase = new DataBase();
    String input = new String();
    private int num=0;private int num1=0;

    public ShowExplore(User user,Scanner in){
        this.in=in;
        this.user=user;
    }
    public void showExplore() throws SQLException, ClassNotFoundException {
        boolean running = true;
        ArrayList<Post> recommendedAds=new ArrayList<>();
        num=0;
        while (running) {
            ArrayList<Post> posts=new ArrayList<>();
            for (Post post : dataBase.getPosts()) {
                if (post.getIsComment().equals("post")){
                    posts.add(post);
                }
            }



            AdRecommender adRecommender = new AdRecommender();
            recommendedAds=adRecommender.findFinalAds(user,dataBase);
            showExplorePage(num,recommendedAds,posts);
            input = in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (posts.size()>0) {
                    System.out.println("Well,Please enter post number to select this post directly");
                    input = in.nextLine();
                    if (!input.matches("\\w+")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= posts.size()) {
                            user.getSelectedPosts().add(posts.get(Integer.parseInt(input) - 1));
                            showPost2(input,posts);
                        } else {
                            System.out.println("invalid input please enter a valid post number");
                        }
                    }
                }else{
                    System.out.println("There is no post to select.  :)");
                }
            } else if (input.equals("2")) {
                if (posts.size()<=num+10){
                    System.out.println("this page is last possible page.");
                }else{
                    num+=10;
                }
            } else if (input.equals("3")) {
                // ????***
            } else if (input.equals("4")) {
                break;
            }else if (input.equals("5")) {

                if (recommendedAds.size() > 0) {
                    System.out.println("Well,Please enter User number to visit this user's page");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 5 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= recommendedAds.size()) {
                            showPost(input,recommendedAds);
                        } else {
                            System.out.println("invalid input please enter a valid User number");
                        }
                    }
                } else {
                    System.out.println("There is no recommended user to select.  :)");
                }
            }
        }

    }
    private void showExplorePage(int num, ArrayList<Post> recommendedAds, ArrayList<Post> posts) throws SQLException, ClassNotFoundException {
        ManagerShow.showExplorePage(num,posts,user,recommendedAds);
    }
    private void showPost(String index, ArrayList<Post> recommendedAds) throws SQLException, ClassNotFoundException {
        boolean showUserRecommendation=false;
        boolean running = true;
        ArrayList<User> recommendedUsers=new ArrayList<>();
        while (running) {
            if (showUserRecommendation){
                UserRecommender userRecommender=new UserRecommender();
                recommendedUsers=userRecommender.findFinalUsersIndivisually(user,dataBase);
            }
            Post post=recommendedAds.get(Integer.parseInt(index) - 1);
            User user2=recommendedAds.get(Integer.parseInt(index) - 1).getSender();
            ManagerShow.showPostInExplore(index,dataBase,user,post,user2,showUserRecommendation,recommendedUsers);

            input=in.nextLine();
            if (!input.matches("\\w+")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) { //Add Comment
                if ( post.isCommentAble()) {
                    System.out.println("Please enter your comment. (You can press only Key-Enter to back to create post page. )");
                    System.out.print("Comment: ");

                    input = in.nextLine();

                    if (!input.isEmpty()) {
                        Comment comment = new Comment(user, "text", input, true,post);
                        post.addComment(comment);
                        System.out.println("Your text is commented successfully");


                    }
                }else{
                    System.out.println(" leaving down comment is banned to this post.");
                }
            } else if (input.equals("2")) {//Like post
                post.addLikeToTable(user,post.getPostId(),LocalDateTime.now());
                post.getLikes().put(user, LocalDateTime.now());
                if (user.getLikedInfo().containsKey(post.getSender())){
                    int kkk=user.getLikedInfo().get(post.getSender());
                    user.getLikedInfo().put(post.getSender(),kkk+1);
                }else{
                    user.getLikedInfo().put(post.getSender(),1);
                }
            } else if (input.equals("3")) {//Select Comment
                if (post.getComments().size()>0) {
                    System.out.println("Well,Please enter comment number to select this comment directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= post.getComments().size()) {
                            post.addViewToTable(user, post.getPostId(), LocalDateTime.now());
                            post.getComments().get(Integer.parseInt(input) - 1).getViews().put(user, LocalDateTime.now());
                            showComment(post.getComments().get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid comment number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            }  else if (input.equals("4")) { //check Liked users
                if (post.getLikes().size()>0){
                    showLikedUsers(post);
                }else {
                    System.out.println("There is no Like");
                }

            } else if (input.equals("5")) {//check viewed users
                showViewedUsers(post);
            }  else if (input.equals("6")) { //visit
                post.getSender().getViewsFromPage().put(user, LocalDateTime.now());
                showOthersPage(user,post.getSender());
            } else if (input.equals("7")) {//follow
                if (!user.getFollowings().contains(user2) ) {
                    System.out.println("man nashod ba to bemonam");
                    if ( !user.equals(user2)){
                        User.addFollowerAndFollowingToTable(user,user2);
                        user.addFollowing(user2);
                        user2.addFollower(user);
                        showUserRecommendation = true;
                    }
                }else{
                    User.removeFollowerAndFollowingFromTable(user,user2);
                    user.getFollowings().remove(user2);
                    user2.getFollowers().remove(user);
                    showUserRecommendation=false;
                }
            }else if (input.equals("8")){ //Forward
                ManagerPv.forwardMessage(user,new Message(post.getSender(),post.getFormat(),post.getContent(),true,false,false),in);
                System.out.println("Forwarded :O)");
            }else if (input.equals("9")) {//back
                System.out.println();
                break;
            }else if (input.equals("10")) {
                if (showUserRecommendation) {

                    if (recommendedUsers.size() > 0) {
                        System.out.println("Well,Please enter User number to visit this user's page");
                        input = in.nextLine();
                        if (!input.matches("\\w{1}")) {
                            System.out.println("invalid input please enter 5 to select again");
                        } else {
                            if (Integer.parseInt(input) - 2 <= recommendedUsers.size()) {
                                showOthersPage(user, recommendedUsers.get(Integer.parseInt(input) - 1));
                            } else {
                                System.out.println("invalid input please enter a valid User number");
                            }
                        }
                    } else {
                        System.out.println("There is no recommended user to select.  :)");
                    }
                }
            }else {
                System.out.println("invalid Input ::(");
            }
        }
    }


    private void showPost2(String index, ArrayList<Post> posts) throws SQLException, ClassNotFoundException {
        boolean showUserRecommendation=false;
        boolean running = true;
        ArrayList<User> recommendedUsers=new ArrayList<>();
        while (running) {
            if (showUserRecommendation){
                UserRecommender userRecommender=new UserRecommender();
                recommendedUsers=userRecommender.findFinalUsersIndivisually(user,dataBase);
            }
            Post post=posts.get(Integer.parseInt(index) - 1);
            User user2=post.getSender();
            ManagerShow.showPostInExplore(index,dataBase,user,post,user2,showUserRecommendation,recommendedUsers);

            input=in.nextLine();
            if (!input.matches("\\w+")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) { //Add Comment
                if ( post.isCommentAble()) {
                    System.out.println("Please enter your comment. (You can press only Key-Enter to back to create post page. )");
                    System.out.print("Comment: ");

                    input = in.nextLine();

                    if (!input.isEmpty()) {
                        Comment comment = new Comment(user, "text", input, true, post);
                        post.addComment(comment);
                        System.out.println("Your text is commented successfully");


                    }
                }else{
                    System.out.println(" leaving down comment is banned to this post.");
                }
            } else if (input.equals("2")) {//Like post
                post.addLikeToTable(user,post.getPostId(), LocalDateTime.now());
                post.getLikes().put(user, LocalDateTime.now());
                if (user.getLikedInfo().containsKey(post.getSender())){
                    int kkk=user.getLikedInfo().get(post.getSender());
                    user.getLikedInfo().put(post.getSender(),kkk+1);
                }else{
                    user.getLikedInfo().put(post.getSender(),1);
                }
            } else if (input.equals("3")) {//Select Comment
                if (post.getComments().size()>0) {
                    System.out.println("Well,Please enter comment number to select this comment directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= post.getComments().size()) {
                            post.addViewToTable(user, post.getPostId(), LocalDateTime.now());
                           post.getComments().get(Integer.parseInt(input) - 1).getViews().put(user, LocalDateTime.now());
                            showComment(post.getComments().get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid comment number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            }  else if (input.equals("4")) { //check Liked users
                if (post.getLikes().size()>0){
                    showLikedUsers(post);
                }else {
                    System.out.println("There is no Like");
                }

            } else if (input.equals("5")) {//check viewed users
                showViewedUsers(post);
            }  else if (input.equals("6")) { //visit
                post.getSender().getViewsFromPage().put(user, LocalDateTime.now());
                showOthersPage(user,post.getSender());
            } else if (input.equals("7")) {//follow
                if (!user.getFollowings().contains(user2)) {
                    if ( !user.equals(user2)) {
                        User.addFollowerAndFollowingToTable(user,user2);
                        user.addFollowing(user2);
                        user2.addFollower(user);
                        showUserRecommendation = true;
                    }
                }else{
                    User.removeFollowerAndFollowingFromTable(user,user2);
                    user.getFollowings().remove(user2);
                    user2.getFollowers().remove(user);
                    showUserRecommendation=false;
                }
            }else if (input.equals("8")){ //Forward
                ManagerPv.forwardMessage(user,new Message(post.getSender(),post.getFormat(),post.getContent(),true,false,false),in);
                System.out.println("Forwarded :O)");
            }else if (input.equals("9")) {//back
                System.out.println();
                break;
            }else if (input.equals("10")) {
                if (showUserRecommendation) {
                    if (recommendedUsers.size() > 0) {
                        System.out.println("Well,Please enter User number to visit this user's page");
                        input = in.nextLine();
                        if (!input.matches("\\w{1}")) {
                            System.out.println("invalid input please enter 5 to select again");
                        } else {
                            if (Integer.parseInt(input) - 2 <= recommendedUsers.size()) {
                                showOthersPage(user, recommendedUsers.get(Integer.parseInt(input) - 1));
                            } else {
                                System.out.println("invalid input please enter a valid User number");
                            }
                        }
                    } else {
                        System.out.println("There is no recommended user to select.  :)");
                    }
                }

            }else {
                System.out.println("invalid Input ::(");
            }
        }
    }
    private void showPost(String index,User user1,User user) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            Post post = user1.getPosts().get(Integer.parseInt(index) - 1);
            ManagerShow.showPostInExplore2(index,user1,user,dataBase,post);

            input = in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) { //Add Comment
                if (user1.getPosts().get(Integer.parseInt(index) - 1).isCommentAble()) {
                    System.out.println("Please enter your comment. (You can press only Key-Enter to back to create post page. )");
                    System.out.print("Comment: ");

                    input = in.nextLine();
                    //if (input.equals("4")) {
                    //System.out.println();
                    //break;
                    //}
                    if (!input.isEmpty()) {
                        Comment comment = new Comment(user, "text", input, true,post);
                        post.addComment(comment);
                        System.out.println("Your text is commented successfully");


                    }
                } else {
                    System.out.println(" leaving down comment is banned to this post.");
                }
            } else if (input.equals("2")) {//Like post
                post.addLikeToTable(user,post.getPostId(), LocalDateTime.now());
                post.getLikes().put(user, LocalDateTime.now());
                if (user.getLikedInfo().containsKey(post.getSender())){
                    int kkk=user.getLikedInfo().get(post.getSender());
                    user.getLikedInfo().put(post.getSender(),kkk+1);
                }else{
                    user.getLikedInfo().put(post.getSender(),1);
                }
            } else if (input.equals("3")) {//Select Comment
                if (post.getComments().size() > 0) {
                    System.out.println("Well,Please enter comment number to select this comment directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= post.getComments().size()) {
                            post.addViewToTable(user, post.getPostId(), LocalDateTime.now());
                            post.getComments().get(Integer.parseInt(input) - 1).getViews().put(user, LocalDateTime.now());
                            showComment(post.getComments().get(Integer.parseInt(input) - 1),user1,user);
                        } else {
                            System.out.println("invalid input please enter a valid comment number");
                        }
                    }
                } else {
                    System.out.println("There is no comment to select.  :)");
                }
            } else if (input.equals("4")) { //check Liked users
                if (post.getLikes().size() > 0) {
                    showLikedUsers(post);
                } else {
                    System.out.println("There is no Like");
                }

            } else if (input.equals("5")) {//check viewed users
                showViewedUsers(post);
            } else if (input.equals("7")) { //BACK
                System.out.println();
                break;
            } else if (input.equals("6")){ //Forward
                ManagerPv.forwardMessage(user,new Message(post.getSender(),post.getFormat(),post.getContent(),true,false,false),in);
                System.out.println("Forwarded :O)");
            }
        }

    }
    private void showComment(Comment comment) throws SQLException, ClassNotFoundException {
        boolean showUserRecommendation=false;
        boolean running = true;
        ArrayList<User> recommendedUsers=new ArrayList<>();
        while (running) {
            if (showUserRecommendation){
                UserRecommender userRecommender=new UserRecommender();
                recommendedUsers=userRecommender.findFinalUsersIndivisually(user,dataBase);
            }
            User user2= comment.getSender();
            //Comment comment = dataBase.getPosts().get(Integer.parseInt(index) - 1).getComments().get(Integer.parseInt(index2) - 1);
            ManagerShow.showCommentInExplore(dataBase,comment,user2,user,showUserRecommendation,recommendedUsers);


            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) { //Add Comment
                if ( comment.isCommentAble()) {
                    System.out.println("Please enter your comment. (You can press only Key-Enter to back to create post page. )");
                    System.out.print("Comment: ");

                    input = in.nextLine();
                    //if (input.equals("4")) {
                    //System.out.println();
                    //break;
                    //}
                    if (!input.isEmpty()) {
                        Comment comment2 = new Comment(user, "text", input, true,comment);
                        comment.getComments().add(comment2);
                        System.out.println("Your text is commented successfully");


                    }
                }else{
                    System.out.println(" leaving down comment is banned to this post.");
                }
            } else if (input.equals("2")) {//Like post
                comment.addLikeToTable(user,comment.getPostId(), LocalDateTime.now());
                comment.getLikes().put(user, LocalDateTime.now());
            } else if (input.equals("3")) {//Select Comment
                if (comment.getComments().size()>0) {
                    System.out.println("Well,Please enter comment number to select this comment directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= comment.getComments().size()) {
                            comment.addViewToTable(user, comment.getPostId(), LocalDateTime.now());
                           comment.getComments().get(Integer.parseInt(input) - 1).getViews().put(user, LocalDateTime.now());
                            showComment(comment.getComments().get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid comment number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            }  else if (input.equals("4")) { //check Liked users
                if (comment.getLikes().size()>0){
                    showLikedUsers(comment);
                }else {
                    System.out.println("There is no Like");
                }

            } else if (input.equals("5")) {//check viewed users
                showViewedUsers(comment);
            }  else if (input.equals("6")) { //visit
                comment.getSender().getViewsFromPage().put(user, LocalDateTime.now());
                showOthersPage(user,comment.getSender());
            } else if (input.equals("7")) {//follow
                if (!user.getFollowings().contains(user2)) {
                    if ( !user.equals(user2)) {
                        User.addFollowerAndFollowingToTable(user,user2);
                        user.addFollowing(user2);
                        user2.addFollower(user);
                        showUserRecommendation = true;
                    }
                }else{
                    User.removeFollowerAndFollowingFromTable(user,user2);
                    user.getFollowings().remove(user2);
                    user2.getFollowers().remove(user);
                    showUserRecommendation=false;
                }
            } else if (input.equals("8")) {//back
                System.out.println();
                break;
            }else if (input.equals("9")) {
                if (showUserRecommendation) {

                    if (recommendedUsers.size() > 0) {
                        System.out.println("Well,Please enter User number to visit this user's page");
                        input = in.nextLine();
                        if (!input.matches("\\w{1}")) {
                            System.out.println("invalid input please enter 5 to select again");
                        } else {
                            if (Integer.parseInt(input) - 2 <= recommendedUsers.size()) {
                                showOthersPage(user, recommendedUsers.get(Integer.parseInt(input) - 1));
                            } else {
                                System.out.println("invalid input please enter a valid User number");
                            }
                        }
                    } else {
                        System.out.println("There is no recommended user to select.  :)");
                    }
                }

            }
        }
    }
    private void showComment(Comment comment,User user1,User user) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            //Comment comment = user1.getPosts().get(Integer.parseInt(index) - 1).getComments().get(Integer.parseInt(index2) - 1);
            ManagerShow.showCommentInExplore2(dataBase,user,user1,comment);

            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) { //Add Comment
                if ( comment.isCommentAble()) {
                    System.out.println("Please enter your comment. (You can press only Key-Enter to back to create post page. )");
                    System.out.print("Comment: ");

                    input = in.nextLine();
                    //if (input.equals("4")) {
                    //System.out.println();
                    //break;
                    //}
                    if (!input.isEmpty()) {
                        Comment comment2 = new Comment(user, "text", input, true,comment);
                        comment.getComments().add(comment2);
                        System.out.println("Your text is commented successfully");


                    }
                }else{
                    System.out.println(" leaving down comment is banned to this post.");
                }
            } else if (input.equals("2")) {//Like post
                comment.addLikeToTable(user,comment.getPostId(), LocalDateTime.now());
                comment.getLikes().put(user, LocalDateTime.now());
            } else if (input.equals("3")) {//Select Comment
                if (comment.getComments().size()>0) {
                    System.out.println("Well,Please enter comment number to select this comment directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= comment.getComments().size()) {
                            comment.addViewToTable(user, comment.getPostId(), LocalDateTime.now());
                            comment.getComments().get(Integer.parseInt(input) - 1).getViews().put(user, LocalDateTime.now());
                            showComment(comment,user1,user);
                        } else {
                            System.out.println("invalid input please enter a valid comment number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            }  else if (input.equals("4")) { //check Liked users
                if (comment.getLikes().size()>0){
                    showLikedUsers(comment);
                }else {
                    System.out.println("There is no Like");
                }

            } else if (input.equals("5")) {//check viewed users
                showViewedUsers(comment);
            }  else if (input.equals("6")) { //ban commments
                System.out.println();
                break;
            }
        }
    }
    private void showViewedUsers(Post post) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            List<User> keyList = new ArrayList(post.getViews().keySet());
            ManagerShow.showViewedUsersInExplore(keyList,post);


            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (keyList.size()>0) {
                    System.out.println("Well,Please enter user number to select this user directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= keyList.size()) {
                            keyList.get(Integer.parseInt(input) - 1).getViewsFromPage().put(user,LocalDateTime.now());
                            showOthersPage(user,keyList.get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid user number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            } else if (input.equals("2")) {
                break;
            }


        }
    }
    private void showLikedUsers(Post post) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            List<User> keyList = new ArrayList(post.getLikes().keySet());
            ManagerShow.showLikedUsersInExplore(keyList,post);


            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (keyList.size()>0) {
                    System.out.println("Well,Please enter user number to select this user directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= keyList.size()) {
                            keyList.get(Integer.parseInt(input) - 1).getViewsFromPage().put(user,LocalDateTime.now());
                            showOthersPage(user,keyList.get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid user number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            } else if (input.equals("2")) {
                break;
            }


        }




    }
    private void showOthersPage(User user, User user1) throws SQLException, ClassNotFoundException {


        boolean showUserRecommendation=false;
        boolean running = true;
        ArrayList<User> recommendedUsers=new ArrayList<>();
        num1=0;
        while (running){

            ArrayList<Post> posts=new ArrayList<>();
            for (Post post : dataBase.getPosts()) {
                if (post.getIsComment().equals("post") && post.getSender().equals(user1)){
                    posts.add(post);
                }
            }


            if (showUserRecommendation){
                UserRecommender userRecommender=new UserRecommender();
                recommendedUsers=userRecommender.findFinalUsersIndivisually(user,dataBase);
            }
            showOthersPageHelp(user1,num1,showUserRecommendation,recommendedUsers,posts);
            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (posts.size()>0) {
                    System.out.println("Well,Please enter post number to select this post directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= user.getPosts().size()) {
                            user.getSelectedPosts().add(posts.get(Integer.parseInt(input) - 1));
                            showPost(input,user1,user);
                        } else {
                            System.out.println("invalid input please enter a valid post number");
                        }
                    }
                }else{
                    System.out.println("There is no post to select.  :)");
                }
            } else if (input.equals("2")) {
                if (posts.size()<=num1+5){
                    System.out.println("this page is last possible page.");
                }else{
                    num1+=5;
                }
            } else if (input.equals("4")) {
                showFollowers(user1);
            } else if (input.equals("5")) {
                showFollowings(user1);
            } else if (input.equals("6")) {
                if (!user.getFollowings().contains(user1)) {
                    if ( !user.equals(user1)) {
                        User.addFollowerAndFollowingToTable(user,user1);
                        user.addFollowing(user1);
                        user1.addFollower(user);
                        showUserRecommendation = true;
                    }
                }else{
                    User.removeFollowerAndFollowingFromTable(user,user1);
                    user.getFollowings().remove(user1);
                    user1.getFollowers().remove(user);
                    showUserRecommendation=false;
                }
            } else if (input.equals("8")) {
                break;
            } else if (input.equals("7")){
                ShowPv.main(user,user1,in);
            } else if (input.equals("3")){
                if (num1-5<0){
                    System.out.println("this page is first page.");
                }else{
                    num1-=5;
                }
            }else if (input.equals("9")) {
                if (showUserRecommendation) {

                    if (recommendedUsers.size() > 0) {
                        System.out.println("Well,Please enter User number to visit this user's page");
                        input = in.nextLine();
                        if (!input.matches("\\w{1}")) {
                            System.out.println("invalid input please enter 5 to select again");
                        } else {
                            if (Integer.parseInt(input) - 2 <= recommendedUsers.size()) {
                                showOthersPage(user, recommendedUsers.get(Integer.parseInt(input) - 1));
                            } else {
                                System.out.println("invalid input please enter a valid User number");
                            }
                        }
                    } else {
                        System.out.println("There is no recommended user to select.  :)");
                    }
                }
            }

        }
    }
    private void showOthersPageHelp(User user1, int num1, boolean showUserRecommendation, ArrayList<User> recommendedUsers, ArrayList<Post> posts) throws SQLException, ClassNotFoundException {
        ManagerShow.showExploreOtherPageTop(user1,showUserRecommendation,recommendedUsers);

        if (posts.size() == 0) {
            System.out.println("this user haven't launched any post yet");
        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getLocalDateTime().isAfter(o2.getLocalDateTime())){
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i=num;i<num+5 && i<posts.size();i++){
                posts.get(i).addViewToTable(user, posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(user, LocalDateTime.now());
                ManagerShow.boxInLeft("Post#"+(i+1)+" : ","|","_",80);
                ManagerShow.showPost(posts.get(i));
            }
            ManagerShow.closeDownLine("|","*",80);

        }
        ManagerShow.showExploreOtherPageChoicesIn(user.getFollowings().contains(user1),showUserRecommendation,recommendedUsers);
    }
    private void showFollowings(User user1) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            List<User> keyList = new ArrayList(user1.getFollowings());
            ManagerShow.showFollowingsInExplore(user1,keyList);


            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (keyList.size()>0) {
                    System.out.println("Well,Please enter user number to select this user directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= keyList.size()) {
                            keyList.get(Integer.parseInt(input) - 1).getViewsFromPage().put(user,LocalDateTime.now());
                            showOthersPage(user,keyList.get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid user number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            } else if (input.equals("2")) {
                break;
            }


        }


    }
    private void showFollowers(User user1) throws SQLException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            List<User> keyList = new ArrayList(user1.getFollowers());
            ManagerShow.showFollowersInExplore(user1,keyList);


            input=in.nextLine();
            if (!input.matches("\\w{1}")) {
                System.out.println("invalid input");
            } else if (input.equals("1")) {
                if (keyList.size()>0) {
                    System.out.println("Well,Please enter user number to select this user directly");
                    input = in.nextLine();
                    if (!input.matches("\\w{1}")) {
                        System.out.println("invalid input please enter 1 to select again");
                    } else {
                        if (Integer.parseInt(input) - 2 <= keyList.size()) {
                            keyList.get(Integer.parseInt(input) - 1).getViewsFromPage().put(user,LocalDateTime.now());
                            showOthersPage(user,keyList.get(Integer.parseInt(input) - 1));
                        } else {
                            System.out.println("invalid input please enter a valid user number");
                        }
                    }
                }else{
                    System.out.println("There is no comment to select.  :)");
                }
            } else if (input.equals("2")) {
                break;
            }


        }
    }
}
