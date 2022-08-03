package Controllers;

import DataBase.DataBase;
import View.Controller;
import component.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyHomePostPageController {


    @FXML
    private VBox down;

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
                PostController postController=fxmlLoader.getController();
                postController=fxmlLoader.getController();
                postController.setPost(posts.get(i));
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




                postController.getBanCommentOrFollow().setText("Ban Comment");
                postController.getBanCommentOrFollow().setFont(Font.font(16));
                postController.getBanComment().setImage(new Image(getClass().getResource("/images/block.png").toExternalForm()));
                postController.getDeletOrVisit().setText("Delete");
                postController.getDeletePost().setImage(new Image(getClass().getResource("/images/trash_can.png").toExternalForm()));



                down.getChildren().add(parent);

            }

        }



    }
}
