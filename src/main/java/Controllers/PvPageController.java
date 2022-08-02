package Controllers;

import View.Controller;
import component.Message;
import component.Pv;
import component.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void initialize(){
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
        });    }

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
    void sendMessage(MouseEvent event) {

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
    public void updatePvs(){
        User user=Controller.user;
        ArrayList<Pv> pvs=new ArrayList<>();
        ArrayList<LocalDateTime> lastMessages=new ArrayList<>();
        HashMap<LocalDateTime,Pv> linked=new HashMap<>();
        for (int i=0;i<user.getPvs().size();i++){
            pvs.add(user.getPvs().get(i));
            lastMessages.add(user.getPvs().get(i).getMessages().get(user.getPvs().get(i).getMessages().size()-1).getDate());
            linked.put(lastMessages.get(i),pvs.get(i));
        }


    }
    public void addPvIcon(Parent pv){
        pvsVbox.getChildren().add(pv);
    }
}
