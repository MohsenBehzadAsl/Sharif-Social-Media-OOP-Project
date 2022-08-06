package Controllers;

import View.Controller;
import component.Comment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CommentController {

    @FXML
    private Circle userProfile;

    public Circle getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Circle userProfile) {
        this.userProfile = userProfile;
    }

    @FXML
    private ImageView profile;

    @FXML
    private Label username;

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane all;

    @FXML
    private GridPane left;

    @FXML
    private ImageView liked;

    public PostController postControllerHelp;

    public PostController getPostControllerHelp() {
        return postControllerHelp;
    }

    public void setPostControllerHelp(PostController postControllerHelp) {
        this.postControllerHelp = postControllerHelp;
    }

    public ImageView getLiked() {
        return liked;
    }

    public void setLiked(ImageView liked) {
        this.liked = liked;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    private Comment comment;

    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public Comment getComment() {
        return comment;
    }


    public void like(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        comment.addLikeToTable(Controller.user,comment.getPostId(), LocalDateTime.now());
        comment.getLikes().put(Controller.user, LocalDateTime.now());
        if (Controller.user.getLikedInfo().containsKey(comment.getSender())){
            int kkk=Controller.user.getLikedInfo().get(comment.getSender());
            Controller.user.getLikedInfo().put(comment.getSender(),kkk+1);
        }else{
            Controller.user.getLikedInfo().put(comment.getSender(),1);
        }
        liked.setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));
    }

    public void showComment(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        showCommentII();
    }

    public void showCommentII() throws IOException, SQLException, ClassNotFoundException {


        postControllerHelp.getHelpInListener().getRowConstraints().get(0).setPercentHeight(8);
        postControllerHelp.getHelpInListener().getRowConstraints().get(1).setPercentHeight(34);
        postControllerHelp.getHelpInListener().getRowConstraints().get(2).setPercentHeight(48);
        postControllerHelp.getHelpInListener().getRowConstraints().get(3).setPercentHeight(10);
        postControllerHelp.setPost(comment);

        postControllerHelp.getMainComment().getChildren().clear();
        FXMLLoader fxmlLoader0 = new FXMLLoader(PostController.class.getResource("/fxml/Comment.fxml"));
        Parent parent0 = fxmlLoader0.load();
        CommentController commentController0 = fxmlLoader0.getController();
        commentController0.getUsername().setText(comment.getSender().getUserName());
        commentController0.getUserProfile().setFill(new ImagePattern(new Image(comment.getSender().getPhotoNameFromImageFolder())));
        commentController0.getTextArea().setMinHeight(24);
        commentController0.getTextArea().setWrapText(true);
        commentController0.getTextArea().setText(comment.getContent());
        commentController0.getTextArea().setEditable(false);
        parent0.setDisable(true);
        postControllerHelp.getMainComment().getChildren().add(parent0);

        postControllerHelp.getShowLists().getChildren().clear();
        ArrayList<Comment> comments = new ArrayList<>();
        comments = comment.getComments();
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
                CommentController commentController = fxmlLoader.getController();
                commentController.setPostControllerHelp(this.postControllerHelp);

                commentController.setComment(comments.get(i));
                commentController.getUsername().setText(comments.get(i).getSender().getUserName());
                commentController.getUserProfile().setFill(new ImagePattern(new Image(comment.getSender().getPhotoNameFromImageFolder())));

                commentController.getTextArea().setMinHeight(24);
                commentController.getTextArea().setWrapText(true);
                commentController.getTextArea().setText(comments.get(i).getContent());
                commentController.getTextArea().setEditable(false);


                if (comments.get(i).getLikes().containsKey(Controller.user)){
                    commentController.getLiked().setImage(new Image(getClass().getResource("/images/liked.png").toExternalForm()));

                }
                postControllerHelp.getShowLists().getChildren().add(parent);
            }

        }
    }
}
