package Manager;

import DataBase.DataBase;
import component.Post;
import component.User;

import java.util.*;

public class UserRecommender {

    ArrayList<User> finalUsers=new ArrayList<>();

    List<User> friendByFriend=new ArrayList<>();
    List<User> mostFamouses=new ArrayList<>();
    List<User> businessUsers=new ArrayList<>();
    List<User> moreLikedUsers=new ArrayList<>();


    public ArrayList<User> findFinalUsersIndivisually(User user, DataBase dataBase){
        friendByFriend=recommendFriendByFriend(user);
        mostFamouses=recommendMostFamouses(dataBase);
        businessUsers=recommendBusinessUsers(user,dataBase);
        moreLikedUsers=recommendMoreLikedUsers(user);



        int counter=0;
        for (int i=0;i<friendByFriend.size() && counter<3 ;i++){
            if ( ! finalUsers.contains(friendByFriend.get(i)) && ! friendByFriend.get(i).equals(user) && ! user.getFollowings().contains(friendByFriend.get(i))){
                finalUsers.add(friendByFriend.get(i));
                counter++;
            }
        }
        counter=0;
        for (int i=0;i<mostFamouses.size() && counter<3 ;i++){
            if ( ! finalUsers.contains(mostFamouses.get(i)) && ! mostFamouses.get(i).equals(user) && ! user.getFollowings().contains(mostFamouses.get(i))){
                finalUsers.add(mostFamouses.get(i));
                counter++;
            }
        }
        counter=0;
        for (int i=0;i<businessUsers.size() && counter<2 ;i++){
            if ( ! finalUsers.contains(businessUsers.get(i)) && ! businessUsers.get(i).equals(user)  && ! user.getFollowings().contains(businessUsers.get(i))){
                finalUsers.add(businessUsers.get(i));
                counter++;
            }
        }
        counter=0;
        for (int i=0;i<moreLikedUsers.size() && counter<2 ;i++){
            if ( ! finalUsers.contains(moreLikedUsers.get(i)) && ! moreLikedUsers.get(i).equals(user) && ! user.getFollowings().contains(moreLikedUsers.get(i))){
                finalUsers.add(moreLikedUsers.get(i));
                counter++;
            }
        }


        return finalUsers;
    }

    private List<User> recommendMoreLikedUsers(User user) {
        Map<User,Integer> helpMap = new HashMap<>();
        LinkedHashMap<User, Integer> recommendMoreLikedUsers = new LinkedHashMap<>();
        helpMap=user.getLikedInfo();
        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendMoreLikedUsers.put(x.getKey(), x.getValue()));

        List<User> keyList = new ArrayList(recommendMoreLikedUsers.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;

    }

    private List<User> recommendBusinessUsers(User user, DataBase dataBase) {
        ArrayList<User> recommendMostFamouses0=new ArrayList<>();
        recommendMostFamouses0=dataBase.getUsers();

        Map<User,Integer> helpMap = new HashMap<>();
        LinkedHashMap<User, Integer> recommendMostFamouses1 = new LinkedHashMap<>();

        ArrayList<User> mainUserFollowings=new ArrayList<>();
        mainUserFollowings=user.getFollowings();

        for (User user1 : recommendMostFamouses0) {
            int mainscore=0;
            int scoreAlfa1=0;
            int scoreAlfa2=0;
            int alfa1=1;
            int alfa2=2;

            ArrayList<Post> posts= new ArrayList<>();
            posts=user1.getPosts();
            for (Post post : posts) {
                scoreAlfa1+=(post.getLikes().size()*post.getViews().size());
            }
            scoreAlfa1*=alfa1;
            mainscore+=scoreAlfa1;


            for (User following : mainUserFollowings) {
                if (following.getFollowings().contains(user1)){
                    scoreAlfa2+=2;
                }
                if (following.getFollowers().contains(user1)){
                    scoreAlfa2+=1;
                }
            }
            scoreAlfa2*=alfa2;
            mainscore+=scoreAlfa2;


            helpMap.put(user1,mainscore);
        }

        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendMostFamouses1.put(x.getKey(), x.getValue()));

        List<User> keyList = new ArrayList(recommendMostFamouses1.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;
    }


    private List<User> recommendFriendByFriend(User user) {
        ArrayList<User> recommendFriendByFriend0=new ArrayList<>();
        recommendFriendByFriend0=user.getFollowings();
        ArrayList<User> recommendFriendByFriend1=new ArrayList<>();
        for (User user1 : recommendFriendByFriend0) {
            ArrayList<User> followings=new ArrayList<>();
            followings=user1.getFollowings();
            for (User following : followings) {
                if (! recommendFriendByFriend0.contains(following)){
                    recommendFriendByFriend1.add(following);
                }
            }
        }
        Map<User,Integer> helpMap = new HashMap<>();
        LinkedHashMap<User, Integer> recommendFriendByFriend2 = new LinkedHashMap<>();
        for (User user1 : recommendFriendByFriend1) {
            int score=0;
            ArrayList<User> followings=new ArrayList<>();
            followings=user1.getFollowings();
            for (User following : followings) {
                if (recommendFriendByFriend0.contains(following)){
                    score+=1;
                }
            }

            ArrayList<User> followers=new ArrayList<>();
            followers=user1.getFollowers();
            for (User follower : followers) {
                if (recommendFriendByFriend0.contains(follower)){
                    score+=1;
                }
            }

            helpMap.put(user1,score);
        }
        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendFriendByFriend2.put(x.getKey(), x.getValue()));

        List<User> keyList = new ArrayList(recommendFriendByFriend2.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;

    }

    private List<User> recommendMostFamouses(DataBase dataBase) {
        ArrayList<User> recommendMostFamouses0=new ArrayList<>();
        recommendMostFamouses0=dataBase.getUsers();

        Map<User,Integer> helpMap = new HashMap<>();
        LinkedHashMap<User, Integer> recommendMostFamouses1 = new LinkedHashMap<>();

        for (User user : recommendMostFamouses0) {
            int score=0;

            ArrayList<Post> posts= new ArrayList<>();
            posts=user.getPosts();
            for (Post post : posts) {
                score+=(post.getLikes().size()*post.getViews().size());
            }

            helpMap.put(user,score);
        }

        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendMostFamouses1.put(x.getKey(), x.getValue()));

        List<User> keyList = new ArrayList(recommendMostFamouses1.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;
    }

}