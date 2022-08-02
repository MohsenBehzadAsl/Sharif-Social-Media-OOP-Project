package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MyHomePostPageController {

    @FXML
    public GridPane down;

    public void startShowPost() throws IOException {
        down.getChildren().clear();
        down.getRowConstraints().removeAll();
        down.getColumnConstraints().removeAll();

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/Post.fxml"));
        GridPane gridPane=fxmlLoader.load();
        down.add(gridPane,0,0);


        PostController postController=fxmlLoader.getController();
        postController.startPostChoices();
    }
}
