package DataBase;

import component.Post;

import java.util.ArrayList;

public class All {
    private static ArrayList<Post> normalPost;
    private static ArrayList<Post> ad;
    //......

    public All(){
        if (normalPost==null){
            normalPost=new ArrayList<Post>();
            ad=new ArrayList<Post>();
        }
    }

    public  ArrayList<Post> getNormalPost() {

        return normalPost;
    }
    public  void setNormalPost(ArrayList<Post> normalPost) {

        All.normalPost = normalPost;
    }
    public  ArrayList<Post> getAd() {

        return ad;
    }
    public  void setAd(ArrayList<Post> ad) {

        All.ad = ad;
    }
}
