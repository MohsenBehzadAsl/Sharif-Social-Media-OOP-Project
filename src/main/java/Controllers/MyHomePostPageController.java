package Controllers;

import Controllers.PostControllers.PostController;
import DataBase.DataBase;
import Manager.UserRecommender;
import View.Controller;
import component.Post;
import component.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Callable;

public class MyHomePostPageController {


    public Parent nowParent = null;

    @FXML
    private VBox userRecommendation;

    private String photoAddress;
    @FXML
    private VBox down;

    @FXML
    private Circle imageProfile ;

    @FXML
    private Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @FXML
    private ImageView imageOfPostRectangle;

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getId() {
        return id;
    }

    public void setId(Label id) {
        this.id = id;
    }

    public Label getFollowers() {
        return followers;
    }

    public void setFollowers(Label followers) {
        this.followers = followers;
    }

    public Label getFollowing() {
        return following;
    }

    public void setFollowing(Label following) {
        this.following = following;
    }

    public Circle getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(Circle imageProfile) {
        this.imageProfile = imageProfile;
    }

    public PostController getPostController() {
        return postController;
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    @FXML
    private Label username;

    @FXML
    private Label id;

    @FXML
    private Label followers;

    public TextArea getCreatePostText() {
        return createPostText;
    }

    public void setCreatePostText(TextArea createPostText) {
        this.createPostText = createPostText;
    }

    @FXML
    private TextArea createPostText;


    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }

    @FXML
    private GridPane all;

    @FXML
    private Label following;



    public PostController postController;

    public void startShowPost() throws IOException, SQLException, ClassNotFoundException {

        down.getChildren().clear();

        ArrayList<Post> posts=new ArrayList<>();
        for (Post post : DataBase.getPosts()) {
            if (post.getIsComment().equals("post") && post.getSender().equals(Controller.user)){
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
                postController=fxmlLoader.getController();
                //Controller.postController=postController;

                postController.setMyHomePostPageController(this);
                postController.setPost(posts.get(i));
                postController.nowParent=nowParent;
                postController.getAll().getColumnConstraints().get(0).setPercentWidth(100);
                postController.getAll().getColumnConstraints().get(1).setPercentWidth(0);
                postController.getAll().getColumnConstraints().get(2).setPercentWidth(0);
                if (posts.get(i).getSender().getType().equalsIgnoreCase("Normal")){
                    postController.getIsAd().setVisible(false);
                }
                postController.getUsername().setText(Controller.user.getUserName());
                postController.getNumOfViews().setText("Num Of Views : "+String.valueOf(posts.get(i).getViews().size()));
                postController.getNumofLike().setText("Num Of Likes : "+String.valueOf(posts.get(i).getLikes().size()));
                postController.getNumOfComments().setText("Num Of Comments : "+String.valueOf(posts.get(i).getComments().size()));
                postController.setCommentCounter(posts.get(i).getComments().size());
                if (!Controller.user.getType().equals("Business")){
                    postController.getAnalyzePost().setVisible(false);
                }





                postController.getBanCommentOrFollow().setText("Ban Comment");
                postController.getBanCommentOrFollow().setFont(Font.font(16));
                postController.getBanComment().setImage(new Image(getClass().getResource("/images/block.png").toExternalForm()));
                postController.getDeletOrVisit().setText("Delete");
                postController.getDeletePost().setImage(new Image(getClass().getResource("/images/trash_can.png").toExternalForm()));

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







                down.getChildren().add(parent);


            }



        }



    }

    public void back(MouseEvent mouseEvent) {
        Controller.main.getChildren().clear();
    }

    public void showUserRecommendation(MouseEvent mouseEvent) throws IOException {
        if ( all.getRowConstraints().get(3).getPercentHeight()==30){
            userRecommendation.getChildren().clear();
            all.getRowConstraints().get(0).setPercentHeight(13.6);
            all.getRowConstraints().get(1).setPercentHeight(86.4);
            all.getRowConstraints().get(2).setPercentHeight(0);
            all.getRowConstraints().get(3).setPercentHeight(0);
            all.getRowConstraints().get(4).setPercentHeight(0);
        }else {
            all.getRowConstraints().get(0).setPercentHeight(13.6);
            all.getRowConstraints().get(1).setPercentHeight(50);
            all.getRowConstraints().get(2).setPercentHeight(6.4);
            all.getRowConstraints().get(4).setPercentHeight(0);
            all.getRowConstraints().get(3).setPercentHeight(30);


            ArrayList<User> recommendedUsers = new ArrayList<>();
            UserRecommender userRecommender = new UserRecommender();
            recommendedUsers = userRecommender.findFinalUsersIndivisually(Controller.user);
            if (recommendedUsers.size() == 0) {

            } else {
                userRecommendation.getChildren().clear();
                for (int i = 0; i < recommendedUsers.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserRecommendation.fxml"));
                    Parent parent = fxmlLoader.load();
                    UserRecommendationController userRecommendationController = fxmlLoader.getController();
                    userRecommendationController.getName().setText(recommendedUsers.get(i).getUserName());
                    userRecommendationController.getId().setText("@" + recommendedUsers.get(i).getId());
                    userRecommendationController.nowParent = nowParent;
                    userRecommendationController.getImage().setFill(new ImagePattern(new Image(recommendedUsers.get(i).getPhotoNameFromImageFolder())));
                    userRecommendationController.getFollowers().setText("Num of followers : " + recommendedUsers.get(i).getFollowers().size());
                    userRecommendationController.getFollowings().setText("Num of followings : " + recommendedUsers.get(i).getFollowings().size());
                    userRecommendationController.getType().setText(recommendedUsers.get(i).getType());
                    userRecommendationController.setUser(recommendedUsers.get(i));
                    userRecommendation.getChildren().add(parent);

                }

            }
        }

    }

    public void addPost(MouseEvent mouseEvent) throws IOException {
        all.getRowConstraints().get(0).setPercentHeight(13.6);
        all.getRowConstraints().get(1).setPercentHeight(0);
        all.getRowConstraints().get(2).setPercentHeight(0);
        all.getRowConstraints().get(4).setPercentHeight(86.4);
        all.getRowConstraints().get(3).setPercentHeight(0);
        visibleCreatPost(true);
    }

    public void clicked(MouseEvent mouseEvent) {

    }

    public void visibleCreatPost(boolean visible){
        //button.setVisible(true);
        createPostText.setMinHeight(24);
        createPostText.setWrapText(true);
        System.out.println("**********");
        if (visible){
            Node text = createPostText.lookup(".text");
            createPostText.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                @Override
                public Double call() throws Exception {
                    return text.getBoundsInLocal().getHeight();
                }
            }, text.boundsInLocalProperty()).add(20));
        }

    }

    public void addPostToDataBase(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {

        System.out.println("/////////////////////");
        all.getRowConstraints().get(0).setPercentHeight(13.6);
        all.getRowConstraints().get(1).setPercentHeight(86.4);
        all.getRowConstraints().get(2).setPercentHeight(0);
        all.getRowConstraints().get(3).setPercentHeight(0);
        all.getRowConstraints().get(4).setPercentHeight(0);



        //button.setVisible(false);

        if (!createPostText.getText().isEmpty() || photoAddress!=null){
            if (photoAddress==null) {
                System.out.println("****");
                Post post = new Post(Controller.user, "text", createPostText.getText(), true);
                Controller.user.getPosts().add(post);
                DataBase.add(post);

            }else{
                Post post = new Post(Controller.user, "image", createPostText.getText(),photoAddress, true);
                Controller.user.getPosts().add(post);
                DataBase.add(post);
            }
            imageOfPostRectangle.setVisible(false);




            createPostText.setText("");
            imageOfPostRectangle.setFitWidth(57);
            imageOfPostRectangle.setFitHeight(49);


            down.getChildren().clear();
            startShowPost();
        }
    }

    public void browseImage(MouseEvent mouseEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        Stage stage=(Stage) all.getScene().getWindow();
        File file=  fileChooser.showOpenDialog(stage);
        if(file!=null){
            Image image = new Image(file.toURI().toString());
            //Controller.user.setPhotoNameFromImageFolder(file.toURI().toString());
            //myImageView.setImage(image);
            imageOfPostRectangle.setVisible(true);
            photoAddress=file.toURI().toString();
            imageOfPostRectangle.setVisible(true);
            imageOfPostRectangle.setImage(null);
            imageOfPostRectangle.setFitHeight(500);
            imageOfPostRectangle.setFitWidth(500);
            imageOfPostRectangle.setPreserveRatio(true);
            imageOfPostRectangle.setImage(new Image(photoAddress));
        }
    }
}
