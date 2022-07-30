package Manager;

import DataBase.DataBase;
import component.Post;
import component.User;

import java.util.*;

public class AdRecommender {
    ArrayList<Post> finalAds=new ArrayList<>();

    List<Post> mostFamouses=new ArrayList<>();
    List<Post> moreLikedAds=new ArrayList<>();
    public ArrayList<Post> findFinalAds(User user, DataBase dataBase){

        mostFamouses=recommendMostFamouses(dataBase);
        moreLikedAds=recommendMoreLikedAds(dataBase,user);

        int counter=0;
        for (int i=0;i<mostFamouses.size() && counter<4 ;i++){
            if ( ! finalAds.contains(mostFamouses.get(i)) ){
                finalAds.add(mostFamouses.get(i));
                counter++;
            }
        }

        counter=0;
        for (int i=0;i<moreLikedAds.size() && counter<6 ;i++){
            if ( ! finalAds.contains(moreLikedAds.get(i)) ){
                finalAds.add(moreLikedAds.get(i));
                counter++;
            }
        }

        return finalAds;
    }

    private List<Post> recommendMoreLikedAds(DataBase dataBase, User user) {
        ArrayList<Post> userIsViewedPost=new ArrayList<>();
        for (Post post : dataBase.getPosts()) {
            if (post.getType().equals("Business") && post.getViews().containsKey(user)){
                userIsViewedPost.add(post);
            }
        }
        Map<Post,Double> helpMap = new HashMap<>();
        LinkedHashMap<Post, Double> recommendMoreLikedAds = new LinkedHashMap<>();
        for (Post post : userIsViewedPost) {
            ArrayList<User> viewedPost=new ArrayList(post.getViews().keySet());
            for (User viewedUser : viewedPost) {
                if (!viewedUser.equals(user)) {
                    double mark1 = findMark(user, post);
                    double mark2 = findMark(viewedUser, post);
                    double finalMark = findSimilarity(Math.abs(mark1 - mark2));

                    ArrayList<Post> viewedPostByviewedUser = new ArrayList<>();
                    for (Post viewedUserPost : viewedUser.getPosts()) {
                        if (viewedUserPost.getType().equals("Business")) {
                            viewedPostByviewedUser.add(viewedUserPost);
                        }
                    }

                    for (Post post1 : viewedPostByviewedUser) {
                        double score = findMark(viewedUser, post1) * finalMark;
                        if (helpMap.containsKey(post1)) {
                            double previousMark = helpMap.get(post1);
                            helpMap.remove(post1);// mahz etminan hast ba in ke midanim niyazi nist
                            helpMap.put(post1, previousMark + score);
                        }else {
                            helpMap.put(post1, score);
                        }
                    }
                }
            }
        }

        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendMoreLikedAds.put(x.getKey(), x.getValue()));

        List<Post> keyList = new ArrayList(recommendMoreLikedAds.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;
    }

    private double findSimilarity(double abs) {
        double finalResult = 0;
        if (abs==0){
            finalResult=2;
        }else if(abs==0.5){
            finalResult=0.75;
        }else if(abs==1){
            finalResult=-1;
        }else if(abs==1.5){
            finalResult=-1.25;
        }else if(abs==2){
            finalResult=-1.5;
        }else if(abs==2.5){
            finalResult=-1.75;
        }else{
            finalResult=-2;
        }
        return finalResult;
    }

    private double findMark(User user, Post post) {
        double result=0;
        if (post.getLikes().containsKey(user)){
            result=1;
        }else if (user.getSelectedPosts().contains(post)){
            result=0.5;
        }else{
            result=-1;
        }
        return result;
    }


    private List<Post> recommendMostFamouses(DataBase dataBase) {
        ArrayList<Post> recommendMostFamouses0=new ArrayList<>();
        for (Post post : dataBase.getPosts()) {
            if (post.getType().equals("Business")){
                recommendMostFamouses0.add(post);
            }
        }

        Map<Post,Double> helpMap = new HashMap<>();
        LinkedHashMap<Post, Double> recommendMostFamouses1 = new LinkedHashMap<>();

        for (Post post : recommendMostFamouses0) {
            double score=post.getLikes().size()*post.getViews().size()*post.getSender().getFollowers().size();
            helpMap.put(post,score);
        }

        helpMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> recommendMostFamouses1.put(x.getKey(), x.getValue()));

        List<Post> keyList = new ArrayList(recommendMostFamouses1.keySet());
        //List<Integer> valueList = new ArrayList(recommendFriendByFriend2.values());


        return keyList;
    }
}
