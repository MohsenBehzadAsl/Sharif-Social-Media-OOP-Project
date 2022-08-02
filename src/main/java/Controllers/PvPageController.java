package Controllers;

import View.Controller;
import component.Message;
import component.Pv;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PvPageController {

    Boolean selectPv=true;



    @FXML
    private ImageView backIndexSearch;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private ImageView nextSearchIndex;

    @FXML
    private VBox pvsVbox;

    @FXML
    private VBox reverseVboxForSendMessage;

    @FXML
    private TextField searchIdTextField;

    @FXML
    private Label searchIndex;

    @FXML
    private Label searchTotalFind;

    @FXML
    private GridPane totalGrid;

    @FXML
    public void initialize() throws IOException {
        Controller.stage.setMinWidth(755);
        Controller.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (Controller.stage.getWidth()<990) {
                if (selectPv) {
                    totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
                    totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
                }else {
                    totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
                    totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
                }
            }else if (!selectPv){
                totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
                totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            }
        });
        updatePvs();
    }


    @FXML
    void BlockUser(MouseEvent event) {

    }

    @FXML
    void VisitPage(MouseEvent event) {

    }

    @FXML
    void maximize(MouseEvent event) {
        if (Controller.stage.getWidth()<987){
            selectPv=false;
            totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
        }else if (totalGrid.getColumnConstraints().get(0).getPercentWidth()==0){
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
        }else{
            totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
        }

    }

    @FXML
    void newPv(MouseEvent event) {
        selectPv=true;
    }

    @FXML
    void searchPv(MouseEvent event) {

    }

    @FXML
    void selectFollowersNewPv(MouseEvent event) {

    }

    @FXML
    void selectPhotoMessage(MouseEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) throws IOException {


        updatePvs();
    }

    @FXML
    void serchWordInPv(MouseEvent event) {

    }

    @FXML
    void visibleSearchInPv(MouseEvent event) {

    }
    public void back(MouseEvent mouseEvent) {
        Controller.main.getChildren().clear();
    }
    public void updatePvs() throws IOException {
        User user=Controller.user;
        ArrayList<Pv> pvs=user.getPvs();
        Collections.sort(pvs, new Comparator<Pv>() {
            @Override
            public int compare(Pv o1, Pv o2) {
                if (o1.getMessages().get(o1.getMessages().size()-1).getDate().isAfter(o2.getMessages().get(o2.getMessages().size()-1).getDate())) {
                    return -1;
                }
                return 1;
            }
        });
        for (int i=0;i<pvs.size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/PvIcon.fxml"));
            Parent parent=fxmlLoader.load();
            PvIconController pvIconController=fxmlLoader.getController();
            pvIconController.set(pvs.get(i));
            addPvIcon(parent);
        }
    }
    public void addPvIcon(Parent pv){
        pvsVbox.getChildren().add(pv);
    }
}
