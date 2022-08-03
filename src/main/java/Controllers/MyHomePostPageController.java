package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class MyHomePostPageController {

    @FXML
    private VBox down;

    public VBox getDown() {
        return down;
    }

    public void setDown(VBox down) {
        this.down = down;
    }

    public void startShowPost() throws IOException, SQLException, ClassNotFoundException {
        PostController postController=new PostController();
        postController.start(down,true);
    }
}
