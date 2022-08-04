package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Comment;
import component.Message;
import component.Post;
import component.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.util.List;
import java.util.concurrent.Callable;

public class PostController {


    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @FXML
    private GridPane imagePost;

    public GridPane getImagePost() {
        return imagePost;
    }

    public void setImagePost(GridPane imagePost) {
        this.imagePost = imagePost;
    }

    public CommentController commentController;

    public CommentController getCommentController() {
        return commentController;
    }

    public void setCommentController(CommentController commentController) {
        this.commentController = commentController;
    }

    @FXML
    private GridPane all;

    @FXML
    private GridPane left;

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

    public GridPane getLeft() {
        return left;
    }

    public void setLeft(GridPane left) {
        this.left = left;
    }

    public GridPane getHelpInListener() {
        return helpInListener;
    }

    public void setHelpInListener(GridPane helpInListener) {
        this.helpInListener = helpInListener;
    }

    public GridPane getMessageGridPane() {
        return messageGridPane;
    }

    public void setMessageGridPane(GridPane messageGridPane) {
        this.messageGridPane = messageGridPane;
    }

    public TextArea getCommentTextArea() {
        return messageTextArea;
    }

    public void setCommentTextArea(TextArea messageTextArea) {
        this.messageTextArea = messageTextArea;
    }

    public GridPane getHelpShowButtonsInListener() {
        return helpShowButtonsInListener;
    }

    public void setHelpShowButtonsInListener(GridPane helpShowButtonsInListener) {
        this.helpShowButtonsInListener = helpShowButtonsInListener;
    }

    public Label getDeletOrVisit1() {
        return deletOrVisit1;
    }

    public void setDeletOrVisit1(Label deletOrVisit1) {
        this.deletOrVisit1 = deletOrVisit1;
    }

    public boolean isCommentShown() {
        return commentShown;
    }

    public void setCommentShown(boolean commentShown) {
        this.commentShown = commentShown;
    }

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane helpInListener;

    @FXML
    private Label where;

    @FXML
    private VBox showLists;

    public VBox getMainComment() {
        return mainComment;
    }

    public void setMainComment(VBox mainComment) {
        this.mainComment = mainComment;
    }

    @FXML
    private VBox mainComment;

    @FXML
    private GridPane messageGridPane;

    @FXML
    private TextArea messageTextArea;


    @FXML
    private GridPane helpShowButtonsInListener;



    @FXML
    private ImageView BanComment;

    @FXML
    private ImageView deletePost;

    @FXML
    private Label banCommentOrFollow;

    @FXML
    private Label deletOrVisit;

    @FXML
    private GridPane analyzePost;

    @FXML
    private Label deletOrVisit1;

    private boolean commentShown=false;

    public void initializer(){
        textArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            all.setPrefHeight(textArea.getPrefHeight()*100/67);
            if (all.getPrefHeight()<=475){
                all.setPrefHeight(475);
            }
            left.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100);
            left.getRowConstraints().get(2).setPercentHeight(80/left.getHeight()*100);
            left.getRowConstraints().get(1).setPercentHeight(100-(80/left.getHeight()*100+80/left.getHeight()*100));

            helpInListener.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100/2);
            helpShowButtonsInListener.getRowConstraints().get(0).setPercentHeight(80/left.getHeight()*100);
        });
    }


    private int commentCounter;

    public int getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(int commentCounter) {
        this.commentCounter = commentCounter;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post post;


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



    public void showButtons(MouseEvent mouseEvent) {
        if (commentShown){
            all.getColumnConstraints().get(2).setPercentWidth(0);
        }

        all.getChildren().get(2).setVisible(true);
        if (all.getColumnConstraints().get(1).getPercentWidth()==0){
            if (all.getColumnConstraints().get(2).getPercentWidth()==0) {
                all.getColumnConstraints().get(1).setPercentWidth(18);
                all.getColumnConstraints().get(0).setPercentWidth(82);
            }else{
                all.getColumnConstraints().get(2).setPercentWidth(28);
                all.getColumnConstraints().get(1).setPercentWidth(18);
                all.getColumnConstraints().get(0).setPercentWidth(54);
            }
        }else{
            if (all.getColumnConstraints().get(2).getPercentWidth()==0) {
                all.getChildren().get(2).setVisible(false);
                all.getColumnConstraints().get(1).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(100);
            }else{
                all.getChildren().get(2).setVisible(false);
                all.getColumnConstraints().get(2).setPercentWidth(32);
                all.getColumnConstraints().get(1).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(70);
            }
        }
    }





    public void back(MouseEvent mouseEvent) throws SQLException, IOException, ClassNotFoundException {
        if (helpInListener.getRowConstraints().get(1).getPercentHeight()==0) {
            if (all.getColumnConstraints().get(1).getPercentWidth() != 0) {
                all.getColumnConstraints().get(2).setPercentWidth(0);
                all.getColumnConstraints().get(1).setPercentWidth(30);
                all.getColumnConstraints().get(0).setPercentWidth(70);
            } else {
                all.getColumnConstraints().get(2).setPercentWidth(0);
                all.getColumnConstraints().get(0).setPercentWidth(100);
            }
        }else{

            Comment comment = (Comment) post;
            this.post=comment.getCommentOfPost();

            if (post.getIsComment().equals("post")) {
                mainComment.getChildren().clear();
                this.post.setIsComment("post");
                showCommentII();
            }else{
                commentController.setComment((Comment) post);
                commentController.showCommentII();
            }
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

    public void showLikedUsers(MouseEvent mouseEvent) throws IOException {
        helpInListener.getRowConstraints().get(0).setPercentHeight(8);
        helpInListener.getRowConstraints().get(1).setPercentHeight(92);
        helpInListener.getRowConstraints().get(2).setPercentHeight(0);


        commentShown=false;
        where.setText("Liked Users");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();
        List<User> users = new ArrayList(post.getLikes().keySet());

        if (users.size() == 0) {
        } else {
            for (int i = 0; i < users.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                Parent parent = fxmlLoader.load();
                OtherUsersController otherUsersController = fxmlLoader.getController();
                otherUsersController = fxmlLoader.getController();



                otherUsersController.getUserName().setText(users.get(i).getUserName());
                otherUsersController.getUserName().setFont(Font.font(16));


                otherUsersController.getUserProfile().setImage(new Image(users.get(i).getPhotoNameFromImageFolder()));



                showLists.getChildren().add(parent);
            }

        }
    }

    public void showViewdUsers(MouseEvent mouseEvent) throws IOException {
        helpInListener.getRowConstraints().get(0).setPercentHeight(8);
        helpInListener.getRowConstraints().get(1).setPercentHeight(92);
        helpInListener.getRowConstraints().get(2).setPercentHeight(0);

        commentShown=false;
        where.setText("Viewd Users");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();
        List<User> users = new ArrayList(post.getViews().keySet());

        if (users.size() == 0) {
        } else {
            for (int i = 0; i < users.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/OtherUsers.fxml"));
                Parent parent = fxmlLoader.load();
                OtherUsersController otherUsersController = fxmlLoader.getController();
                otherUsersController = fxmlLoader.getController();



                otherUsersController.getUserName().setText(users.get(i).getUserName());
                otherUsersController.getUserName().setFont(Font.font(16));


                otherUsersController.getUserProfile().setImage(new Image(users.get(i).getPhotoNameFromImageFolder()));



                showLists.getChildren().add(parent);
            }

        }
    }

    public void forward(MouseEvent mouseEvent) {

    }

    public void comment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        showCommentII();
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
        numofLike.setText("Num of likes : "+String.valueOf(post.getLikes().size()));
    }


    public void startHandleTextArea() {
        textArea.applyCss();
        Node text = textArea.lookup(".text");
        textArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
            @Override
            public Double call() throws Exception {
                return text.getBoundsInLocal().getHeight();
            }
        }, text.boundsInLocalProperty()).add(20));
    }

    public void help(MouseEvent mouseEvent) {
        startHandleTextArea();
    }

    public void sendMessage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        if (!messageTextArea.getText().isEmpty()){
            Comment comment = new Comment(Controller.user, "text", messageTextArea.getText(), true, post);
            post.addComment(comment);
            messageTextArea.setText("");
            numOfComments.setText("Num of comments : "+ (++commentCounter));
        }
        showCommentII();
    }

    private void showCommentII() throws SQLException, ClassNotFoundException, IOException {







        visibleComment(true);
        if (post.getIsComment().equals("post")) {
            helpInListener.getRowConstraints().get(0).setPercentHeight(8);
            helpInListener.getRowConstraints().get(1).setPercentHeight(0);
            helpInListener.getRowConstraints().get(2).setPercentHeight(82);
            helpInListener.getRowConstraints().get(3).setPercentHeight(10);
        }else{

            helpInListener.getRowConstraints().get(0).setPercentHeight(8);
            helpInListener.getRowConstraints().get(1).setPercentHeight(34);
            helpInListener.getRowConstraints().get(2).setPercentHeight(48);
            helpInListener.getRowConstraints().get(3).setPercentHeight(10);
        }

        commentShown=false;
        where.setText("Comments");
        if (all.getColumnConstraints().get(1).getPercentWidth() == 0) {
            all.getChildren().get(2).setVisible(false);
            all.getColumnConstraints().get(1).setPercentWidth(0);
            all.getColumnConstraints().get(2).setPercentWidth(32);
            all.getColumnConstraints().get(0).setPercentWidth(68);
        } else {
            all.getColumnConstraints().get(2).setPercentWidth(28);
            all.getColumnConstraints().get(1).setPercentWidth(18);
            all.getColumnConstraints().get(0).setPercentWidth(54);
        }


        showLists.getChildren().clear();
        ArrayList<Comment> comments = new ArrayList<>();
        comments = post.getComments();
        if (comments.size() == 0) {

        } else {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (o1.getLocalDateTime().isAfter(o2.getLocalDateTime())) {
                        return -1;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < comments.size(); i++) {
                comments.get(i).addViewToTable(Controller.user, comments.get(i).getPostId(), LocalDateTime.now());
                comments.get(i).getViews().put(Controller.user, LocalDateTime.now());

                FXMLLoader fxmlLoader = new FXMLLoader(PostController.class.getResource("/fxml/Comment.fxml"));
                Parent parent = fxmlLoader.load();
                commentController = fxmlLoader.getController();
                commentController.setPostControllerHelp(this);

                commentController.setComment(comments.get(i));
                commentController.getUsername().setText(comments.get(i).getSender().getUserName());
                commentController.getProfile().setImage(new Image(comments.get(i).getSender().getPhotoNameFromImageFolder()));

                commentController.getTextArea().setMinHeight(24);
                commentController.getTextArea().setWrapText(true);
                commentController.getTextArea().setText(comments.get(i).getContent());
                commentController.getTextArea().setEditable(false);


                if (comments.get(i).getLikes().containsKey(Controller.user)){
                    commentController.getLiked().setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));

                }
                showLists.getChildren().add(parent);
            }

        }
    }

    public void clicked(MouseEvent mouseEvent) {

    }

    public void visibleComment(boolean visible){
        if (visible){
//            messageTextArea.applyCss();
            Node text = messageTextArea.lookup(".text");
            messageTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                @Override
                public Double call() throws Exception {
                    return text.getBoundsInLocal().getHeight();
                }
            }, text.boundsInLocalProperty()).add(20));
        }
        messageTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            messageGridPane.setPrefHeight(messageTextArea.getPrefHeight()*100/90);
            helpInListener.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/helpInListener.getHeight()*100);
            if (helpInListener.getRowConstraints().get(1).getPercentHeight()==0) {
                helpInListener.getRowConstraints().get(2).setPercentHeight(92 - messageTextArea.getPrefHeight() / helpInListener.getHeight() * 100);
            } else{
                helpInListener.getRowConstraints().get(2).setPercentHeight(58 - messageTextArea.getPrefHeight() / helpInListener.getHeight() * 100);
            }
        });

    }
}
