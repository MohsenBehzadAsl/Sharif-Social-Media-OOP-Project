package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Post;
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
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Callable;

public class MyHomePostPageController {


    @FXML
    private VBox down;

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

    public ImageView getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(ImageView imageProfile) {
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

    @FXML
    private ImageView imageProfile;

    public PostController postController;

    public void startShowPost() throws IOException, SQLException, ClassNotFoundException {


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
                postController.setPost(posts.get(i));
                postController.getAll().getColumnConstraints().get(0).setPercentWidth(100);
                postController.getAll().getColumnConstraints().get(1).setPercentWidth(0);
                postController.getAll().getColumnConstraints().get(2).setPercentWidth(0);
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
                }
                if (posts.get(i).getContent().length()<1000)
                postController.initializer();







                down.getChildren().add(parent);


            }



        }



    }

    public void back(MouseEvent mouseEvent) {

    }

    public void showUserRecommendation(MouseEvent mouseEvent) {

    }

    public void addPost(MouseEvent mouseEvent) throws IOException {
        all.getRowConstraints().get(0).setPercentHeight(13.6);
        all.getRowConstraints().get(1).setPercentHeight(0);
        all.getRowConstraints().get(2).setPercentHeight(86.4);
        all.getRowConstraints().get(3).setPercentHeight(0);


        visibleCreatPost(true);
    }

    public void clicked(MouseEvent mouseEvent) {

    }

    public void visibleCreatPost(boolean visible){

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
        if (!createPostText.getText().isEmpty()){
            System.out.println("****");
            Post post=new Post(Controller.user,"text",createPostText.getText(),true);
            Controller.user.getPosts().add(post);
            DataBase.add(post);
            all.getRowConstraints().get(0).setPercentHeight(13.6);
            all.getRowConstraints().get(1).setPercentHeight(86.4);
            all.getRowConstraints().get(2).setPercentHeight(0);
            all.getRowConstraints().get(3).setPercentHeight(0);
            down.getChildren().clear();
            startShowPost();
        }
    }
}
