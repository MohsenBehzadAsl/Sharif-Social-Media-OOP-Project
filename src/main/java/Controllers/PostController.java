package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Post;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
import java.util.List;

public class PostController {




    @FXML
    private GridPane all;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;

    @FXML
    private Label numofLike;

    @FXML
    private Label numOfComments;

    @FXML
    private Label numOfViews;

    @FXML
    private ImageView liked;

    @FXML
    private Label where;

    @FXML
    private VBox showLists;

    @FXML
    private ImageView comment;

    @FXML
    private Label banCommentOrFollow;

    @FXML
    private Label deletOrVisit;

    private PostController postController;


    @FXML
    private ImageView BanComment;

    @FXML
    private ImageView deletePost;


    @FXML
    private GridPane analyzePost;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post post=new Post();


    public PostController getPostController() {
        return postController;
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public ImageView getBanComment() {
        return BanComment;
    }

    public void setBanComment(ImageView banComment) {
        BanComment = banComment;
    }

    public ImageView getDeletePost() {
        return deletePost;
    }

    public void setDeletePost(ImageView deletePost) {
        this.deletePost = deletePost;
    }

    public GridPane getAnalyzePost() {
        return analyzePost;
    }

    public void setAnalyzePost(GridPane analyzePost) {
        this.analyzePost = analyzePost;
    }

    public  void start(VBox down, boolean flag) throws IOException, SQLException, ClassNotFoundException {
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
                postController.setPost(post);
                postController.getAll().getColumnConstraints().get(0).setPercentWidth(100);
                postController.getAll().getColumnConstraints().get(1).setPercentWidth(0);
                postController.getAll().getColumnConstraints().get(2).setPercentWidth(0);
                postController.getUsername().setText(Controller.user.getUserName());
                postController.getNumOfViews().setText("Num Of Views : "+String.valueOf(posts.get(i).getViews().size()));
                postController.getNumofLike().setText("Num Of Likes : "+String.valueOf(posts.get(i).getLikes().size()));
                postController.getNumOfComments().setText("Num Of Comments : "+String.valueOf(posts.get(i).getComments().size()));
                if (!Controller.user.getType().equals("Business")){
                    postController.getAnalyzePost().setVisible(false);
                }
                if (flag){
                    postController.getBanCommentOrFollow().setText("Ban Comment");
                    postController.getBanCommentOrFollow().setFont(Font.font(16));
                    postController.getBanComment().setImage(new Image(getClass().getResource("/images/block.png").toExternalForm()));

                    postController.getDeletOrVisit().setText("Delete");
                    postController.getDeletePost().setImage(new Image(getClass().getResource("/images/trash_can.png").toExternalForm()));
                }
                down.getChildren().add(parent);

            }

        }




    }


    public void showButtons(MouseEvent mouseEvent) {
        all.getChildren().get(2).setVisible(true);
        if (all.getColumnConstraints().get(1).getPercentWidth()==0){
            all.getColumnConstraints().get(1).setPercentWidth(30);
            all.getColumnConstraints().get(0).setPercentWidth(70);
        }else{
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(0).setPercentWidth(100);
        }
    }





    public void back(MouseEvent mouseEvent) {
        if (all.getColumnConstraints().get(1).getPercentWidth() != 0) {
            all.getColumnConstraints().get(2).setPercentWidth(0);
            all.getColumnConstraints().get(1).setPercentWidth(30);
            all.getColumnConstraints().get(0).setPercentWidth(70);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(0);
            all.getColumnConstraints().get(0).setPercentWidth(100);
        }
    }

    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getNumofLike() {
        return numofLike;
    }

    public void setNumofLike(Label numofLike) {
        this.numofLike = numofLike;
    }

    public Label getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(Label numOfComments) {
        this.numOfComments = numOfComments;
    }

    public Label getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(Label numOfViews) {
        this.numOfViews = numOfViews;
    }

    public ImageView getLiked() {
        return liked;
    }

    public void setLiked(ImageView liked) {
        this.liked = liked;
    }

    public Label getWhere() {
        return where;
    }

    public void setWhere(Label where) {
        this.where = where;
    }

    public VBox getShowLists() {
        return showLists;
    }

    public void setShowLists(VBox showLists) {
        this.showLists = showLists;
    }

    public ImageView getComment() {
        return comment;
    }

    public void setComment(ImageView comment) {
        this.comment = comment;
    }

    public Label getBanCommentOrFollow() {
        return banCommentOrFollow;
    }

    public void setBanCommentOrFollow(Label banCommentOrFollow) {
        this.banCommentOrFollow = banCommentOrFollow;
    }

    public Label getDeletOrVisit() {
        return deletOrVisit;
    }

    public void setDeletOrVisit(Label deletOrVisit) {
        this.deletOrVisit = deletOrVisit;
    }

    public void visitPage(MouseEvent mouseEvent) {
    }

    public void follow(MouseEvent mouseEvent) {
    }

    public void showLikedUsers(MouseEvent mouseEvent) {
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(30);
            all.getColumnConstraints().get(0).setPercentWidth(70);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(25);
            all.getColumnConstraints().get(1).setPercentWidth(25);
            all.getColumnConstraints().get(0).setPercentWidth(50);
        }
    }

    public void showViewdUsers(MouseEvent mouseEvent) {
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(30);
            all.getColumnConstraints().get(0).setPercentWidth(70);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(25);
            all.getColumnConstraints().get(1).setPercentWidth(25);
            all.getColumnConstraints().get(0).setPercentWidth(50);
        }
    }

    public void forward(MouseEvent mouseEvent) {
    }

    public void comment(MouseEvent mouseEvent) {
        where.setText("Comments");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(30);
            all.getColumnConstraints().get(0).setPercentWidth(70);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(25);
            all.getColumnConstraints().get(1).setPercentWidth(25);
            all.getColumnConstraints().get(0).setPercentWidth(50);
        }
    }

    public void like(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        post.addLikeToTable(Controller.user,post.getPostId(), LocalDateTime.now());
        post.getLikes().put(Controller.user, LocalDateTime.now());
        if (Controller.user.getLikedInfo().containsKey(post.getSender())){
            int kkk=Controller.user.getLikedInfo().get(post.getSender());
            Controller.user.getLikedInfo().put(post.getSender(),kkk+1);
        }else{
            Controller.user.getLikedInfo().put(post.getSender(),1);
        }
        liked.setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));
        numofLike.setText("hhhh"+String.valueOf(post.getLikes().size()));
        List<User> keyList = new ArrayList(post.getLikes().keySet());
        for (User user : keyList) {
            System.out.println(user.getUserName());
            System.out.println(post.getContent());
        }

    }


}
