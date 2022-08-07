package Controllers.OtherUserPageControllers;

import Controllers.PostControllers.PostController;
import Controllers.PvControllers.PvPageController;
import Controllers.UserRecommendationController;
import DataBase.DataBase;
import Manager.UserRecommender;
import View.Controller;
import component.Post;
import component.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowAnotherUserPageController {

    public User user;
    public Parent backParent=null;
    public Parent nowParent=null;

    @FXML
    private VBox userRecommendation;
    @FXML
    private GridPane all;
    @FXML
    private Button followOrUnFollow;
    @FXML
    private Label bio;
    @FXML
    private Label id;
    @FXML
    private Circle image;
    @FXML
    private Label myFollowersLabel;
    @FXML
    private Label myFollowingsLabel;
    @FXML
    private Label name;
    @FXML
    private VBox postFill;

    @FXML
    void back(MouseEvent event) {
        Controller.main.getChildren().clear();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.add(backParent,0,0);
    }
    @FXML
    void followOrUnfollow(ActionEvent event) throws IOException {
        if (Controller.user.getFollowings().contains(user)){
            Controller.user.getFollowings().remove(user);
            user.getFollowers().remove(Controller.user);
            all.getRowConstraints().get(3).setPercentHeight(5);
            all.getRowConstraints().get(4).setPercentHeight(68);
        }else {
            Controller.user.getFollowings().add(user);
            user.getFollowers().add(Controller.user);
            all.getRowConstraints().get(3).setPercentHeight(25);
            all.getRowConstraints().get(4).setPercentHeight(48);
            showUserRecommendationHelp();

        }
        if (Controller.user.getFollowings().contains(user)){
            followOrUnFollow.setText("UnFollow");
        }else {
            followOrUnFollow.setText("Follow");
        }
        myFollowersLabel.setText(""+user.getFollowers().size());
        myFollowingsLabel.setText(""+user.getFollowings().size());
    }
    public void showUserRecommendationHelp() throws IOException {
        ArrayList<User> recommendedUsers=new ArrayList<>();
        UserRecommender userRecommender=new UserRecommender();
        recommendedUsers=userRecommender.findFinalUsersIndivisually(Controller.user);
        if (recommendedUsers.size() == 0) {

        } else {
            userRecommendation.getChildren().clear();
            for (int i = 0; i < recommendedUsers.size(); i++) {
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/UserRecommendation.fxml"));
                Parent parent=fxmlLoader.load();
                UserRecommendationController userRecommendationController=fxmlLoader.getController();
                userRecommendationController.getName().setText(recommendedUsers.get(i).getUserName());
                userRecommendationController.getId().setText("@"+recommendedUsers.get(i).getId());
                userRecommendationController.getImage().setFill(new ImagePattern(new Image(recommendedUsers.get(i).getPhotoNameFromImageFolder())));
                userRecommendationController.getFollowers().setText("Num of followers : " +  recommendedUsers.get(i).getFollowers().size());
                userRecommendationController.getFollowings().setText("Num of followings : " +  recommendedUsers.get(i).getFollowings().size());
                userRecommendationController.getType().setText(recommendedUsers.get(i).getType());
                userRecommendationController.setUser(recommendedUsers.get(i));
                userRecommendation.getChildren().add(parent);

            }

        }

    }
    @FXML
    void sendMessage(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvsPage.fxml"));
        Parent parent=fxmlLoader.load();
        PvPageController pvPageController=fxmlLoader.getController();
        Controller.main.getChildren().clear();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.add(parent,0,0);
        pvPageController.showPv(user);
        pvPageController.nowParent=parent;
    }
    @FXML
    void showFollowersOFUser(ActionEvent event) throws IOException {
        if (user.getFollowers().size()>0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FollowerOrFollowingPopUp.fxml"));
            Parent parent = fxmlLoader.load();
            FollowerOrFollowingPopUpController followerOrFollowingPopUpController = fxmlLoader.getController();
            Scene scene = new Scene(parent, 450, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(Controller.stage.getX()+Controller.stage.getWidth()/2-450/2);
            stage.setY(Controller.stage.getY()+Controller.stage.getHeight()/2-400/2);
            stage.setResizable(false);
            followerOrFollowingPopUpController.isFollowing = false;
            followerOrFollowingPopUpController.user = user;
            followerOrFollowingPopUpController.backParent = nowParent;
            followerOrFollowingPopUpController.popUp=stage;
            followerOrFollowingPopUpController.update();
            stage.show();
        }
    }
    @FXML
    void showFollowingOfUser(ActionEvent event) throws IOException {
        if (user.getFollowings().size()>0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FollowerOrFollowingPopUp.fxml"));
            Parent parent = fxmlLoader.load();
            FollowerOrFollowingPopUpController followerOrFollowingPopUpController = fxmlLoader.getController();
            Scene scene = new Scene(parent, 450, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setX(Controller.stage.getX() + Controller.stage.getWidth() / 2 - 450/2);
            stage.setY(Controller.stage.getY() + Controller.stage.getHeight() / 2 - 400/2);
            followerOrFollowingPopUpController.isFollowing = true;
            followerOrFollowingPopUpController.user = user;
            followerOrFollowingPopUpController.backParent = nowParent;
            followerOrFollowingPopUpController.popUp=stage;
            followerOrFollowingPopUpController.update();
            stage.show();
        }
    }
    public void set(User userWithId) {
        this.user=userWithId;
        System.out.println("hello");
        bio.setText(user.getBio());
        id.setText(user.getId());
        image.setFill(new ImagePattern(new Image(user.getPhotoNameFromImageFolder())));
        myFollowersLabel.setText(""+user.getFollowers().size());
        myFollowingsLabel.setText(""+user.getFollowings().size());
        name.setText(user.getUserName());
        if (Controller.user.getFollowings().contains(user)){
            followOrUnFollow.setText("UnFollow");
        }else {
            followOrUnFollow.setText("Follow");
        }

    }
    public void start(User sender) throws SQLException, IOException, ClassNotFoundException {
        name.setText(sender.getUserName());
        id.setText(sender.getId());
        myFollowersLabel.setText(String.valueOf(sender.getFollowers().size()));
        myFollowingsLabel.setText(String.valueOf(sender.getFollowings().size()));
        bio.setText("Bio : "+sender.getBio());
        image.setFill(new ImagePattern(new Image(sender.getPhotoNameFromImageFolder())));
        showPosts(sender);
    }
    private void showPosts(User sender) throws SQLException, ClassNotFoundException, IOException {

        ArrayList<Post> posts=new ArrayList<>();
        for (Post post : DataBase.getPosts()) {
            if (post.getIsComment().equals("post") && post.getSender().equals(sender)){
                posts.add(post);
            }
        }

        if (posts.size() == 0) {

        } else {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });
            for (int i = 0; i < posts.size(); i++) {
                posts.get(i).addViewToTable(Controller.user,posts.get(i).getPostId(), LocalDateTime.now());
                posts.get(i).getViews().put(Controller.user, LocalDateTime.now());


                FXMLLoader fxmlLoader=new FXMLLoader(PostController.class.getResource("/fxml/Post.fxml"));
                Parent parent=fxmlLoader.load();
                PostController postController=fxmlLoader.getController();
                //Controller.postController=postController;

                //postController.setMyHomePostPageController(this);
                postController.setPost(posts.get(i));
                postController.getAll().getColumnConstraints().get(0).setPercentWidth(100);
                postController.getAll().getColumnConstraints().get(1).setPercentWidth(0);
                postController.getAll().getColumnConstraints().get(2).setPercentWidth(0);
                postController.getUsername().setText(posts.get(i).getSender().getUserName());
                postController.getNumOfViews().setText("Num Of Views : "+String.valueOf(posts.get(i).getViews().size()));
                postController.getNumofLike().setText("Num Of Likes : "+String.valueOf(posts.get(i).getLikes().size()));
                postController.getNumOfComments().setText("Num Of Comments : "+String.valueOf(posts.get(i).getComments().size()));
                postController.setCommentCounter(posts.get(i).getComments().size());

                postController.getAnalyzePost().setVisible(false);




                if (!Controller.user.getFollowings().contains(posts.get(i).getSender()) ) {
                    postController.getBanCommentOrFollow().setText("Unfollow");
                }



                postController.getUserProfile().setFill(new ImagePattern(new Image(posts.get(i).getSender().getPhotoNameFromImageFolder())));

                //                commentController.getUserProfile().setFill(new ImagePattern(new Image(comment.getSender().getPhotoNameFromImageFolder())));

                if (posts.get(i).getLikes().containsKey(Controller.user)){
                    postController.getLiked().setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));
                }







                postController.getTextArea().setMinHeight(24);
                postController.getTextArea().setWrapText(true);
                postController.getTextArea().setText(posts.get(i).getContent());
                postController.getTextArea().setEditable(false);
                if (posts.get(i).getFormat().equalsIgnoreCase("text")){
                    postController.getImagePost().getRowConstraints().get(0).setPercentHeight(0);
                    postController.getImagePost().getRowConstraints().get(1).setPercentHeight(100);
                    postController.getImageOfPostRectangle().setFitHeight(0);
                    postController.getImageOfPostRectangle().setFitWidth(0);
                }else{
                    if (posts.get(i).getContent()==null){
                        postController.getImagePost().getRowConstraints().get(0).setPercentHeight(100);
                        postController.getImagePost().getRowConstraints().get(1).setPercentHeight(0);
                    }else{
                        postController.getImagePost().getRowConstraints().get(0).setPercentHeight(60);
                        postController.getImagePost().getRowConstraints().get(1).setPercentHeight(40);
                    }

                    System.out.println(posts.get(i).getPhotoAddress());

                    postController.getImageOfPostRectangle().setFitHeight(300);
                    postController.getImageOfPostRectangle().setFitWidth(300);
                    postController.getImageOfPostRectangle().setPreserveRatio(true);
                    postController.getImageOfPostRectangle().setImage(new Image(posts.get(i).getPhotoAddress()));
                    postController.getImageOfPostRectangle().setPreserveRatio(true);



                }
                if (posts.get(i).getContent().length()<1000)
                    postController.initializer();
                postFill.getChildren().add(parent);


            }



        }


    }
}
