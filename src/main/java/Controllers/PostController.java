package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PostController {


    @FXML
    private GridPane right;

    public void startPostChoices() throws IOException {
        right.getChildren().clear();
        right.getRowConstraints().removeAll();
        right.getColumnConstraints().removeAll();

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/postChoices.fxml"));
        GridPane gridPane=fxmlLoader.load();
        right.add(gridPane,0,0);

        PostChoicesController postChoicesController=fxmlLoader.getController();


    }
}
