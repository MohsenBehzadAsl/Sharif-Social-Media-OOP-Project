package component;

import DataBase.All;

import java.util.Random;

public class Explore {
    private All all=new All();
    private Random random=new Random();
    private User user; //***?!

    public Explore(User user){

        this.user=user;
    }

    public Post findPost(){

      return new Post();
    }
    public Post findAd(){

        return new Ad();
    }
}
