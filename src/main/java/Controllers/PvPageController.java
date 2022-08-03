package Controllers;

import DataBase.DataBase;
import Manager.ManagerPv;
import View.Controller;
import component.Message;
import component.Pv;
import component.User;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class PvPageController {

    Boolean selectPv=true;
    Pv pv;
    private double oldHeight=0;


    @FXML
    private GridPane rightGridPain;
    @FXML
    private GridPane messageGridPane;
    @FXML
    private ImageView block;
    @FXML
    private Circle pvPhoto;
    @FXML
    private Label pvName;
    @FXML
    private Label pvId;
    @FXML
    private GridPane searchInPvGridPane;
    @FXML
    private ImageView zarebbin;
    @FXML
    private GridPane searchUser;
    @FXML
    private ImageView plusPv;
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

//        Text textHolder=new Text();
//        textHolder.textProperty().bind(messageTextArea.textProperty());
//        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                if (oldHeight != newValue.getHeight()) {
//                    System.out.println("newValue = " + newValue.getHeight());
//                    oldHeight = newValue.getHeight();
//                    messageTextArea.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
//                }
//            }
//        });
//        textHolder.setWrappingWidth(messageTextArea.getWidth() - 10);
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
        searchUser.setVisible(false);
        updatePvs();
        visiblePv(false);
        visibleSearchInPv(false);

    }
    public void visibleSearchInPv(boolean visible){
        searchInPvGridPane.setVisible(visible);
    }
    public void visiblePv(boolean visible){
        totalGrid.getChildren().get(1).setVisible(visible);
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
            rightGridPain.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
            rightGridPain.getRowConstraints().get(2).setPercentHeight(84-messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
        });

    }
    @FXML
    void BlockUser(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (pv.getBlock() && pv.getBlocker()!=Controller.user){
            System.out.println(":[");
        } else if (!pv.getBlock()) {
            pv.setBlock(true, Controller.user);
            showPv(Controller.user.getLinkedPvs().get(pv));
        }else {
            pv.setBlock(false, Controller.user);
            showPv(Controller.user.getLinkedPvs().get(pv));
        }
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
    void newPv(MouseEvent event) throws IOException {
        if (plusPv.getRotate()==0){
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusPv);
            rotateTransition.setToAngle(45);
            rotateTransition.play();
            searchUser.setVisible(true);
        }else {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusPv);
            rotateTransition.setToAngle(0);
            rotateTransition.play();
            searchUser.setVisible(false);
            pvsVbox.getChildren().clear();
            updatePvs();
        }
    }
    @FXML
    void searchPv(MouseEvent event) throws IOException {
        pvsVbox.getChildren().clear();
        ArrayList<User> usersFind=new ArrayList<>();
        for (int i = 0; i<DataBase.getUsers().size();i++){
            if (Controller.find(DataBase.getUsers().get(i).getId(),searchIdTextField.getText())){
                usersFind.add(DataBase.getUsers().get(i));
            }
        }
        if (usersFind.size()==0){
            searchIdTextField.setStyle("-fx-background-color: red");
            searchIdTextField.setText("");
            searchIdTextField.setPromptText("Id not Found :[");
        }else {
            showNewUsers(usersFind);
        }
    }
    private void showNewUsers(ArrayList<User> usersFind) throws IOException {
        for (int i=0;i<usersFind.size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/UserIconInPv.fxml"));
            Parent parent=fxmlLoader.load();
            UserIconInPvController userIconInPvController=fxmlLoader.getController();
            userIconInPvController.pvPageController=this;
            userIconInPvController.set(usersFind.get(i));
            addPvIcon(parent);
        }
    }
    @FXML
    void selectFollowersNewPv(MouseEvent event) throws IOException {
        showNewUsers(Controller.user.getFollowers());
    }
    @FXML
    void selectPhotoMessage(MouseEvent event) {

    }
    @FXML
    void sendMessage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (!messageTextArea.getText().isEmpty()){
            Message message=new Message(Controller.user,"Text",messageTextArea.getText(),false,false,false);
            pv.addMessage(message);
        }
        showPv(Controller.user.getLinkedPvs().get(pv));
    }

    @FXML
    void searchWordInPv(MouseEvent event) {
    }
    @FXML
    void visibleSearchInPv(MouseEvent event) {
        if (zarebbin.getRotate()==0) {
            visibleSearchInPv(true);
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(zarebbin);
            rotateTransition.setToAngle(45);
            rotateTransition.play();
        }else {
            visibleSearchInPv(false);
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(zarebbin);
            rotateTransition.setToAngle(0);
            rotateTransition.play();
        }
    }
    public void selectNewPv(){

    }
    public void back(MouseEvent mouseEvent) {
        Controller.main.getChildren().clear();
    }
    public void updatePvs() throws IOException {
        User user=Controller.user;
        ArrayList<Pv> pvs=user.getPvs();
//        Collections.sort(pvs, new Comparator<Pv>() {
//            @Override
//            public int compare(Pv o1, Pv o2) {
//                if (o1.getMessages().get(o1.getMessages().size()-1).getDate().isAfter(o2.getMessages().get(o2.getMessages().size()-1).getDate())) {
//                    return -1;
//                }
//                return 1;
//            }
//        });
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
    public void showPv(User userWithId) throws SQLException, ClassNotFoundException, IOException {
        if (Controller.user.getPv(userWithId)==null){
            Controller.user.addPv(userWithId);
        }
        Pv pv=Controller.user.getPv(userWithId);
        this.pv=pv;
        if (pv.getBlock()) {
            if (pv.getBlocker()!=Controller.user){
                block.setDisable(true);
            }
            pvName.setText(ManagerPv.setName(pv,Controller.user));
            pvPhoto.setFill(new ImagePattern(new Image(Controller.Block_Photo)));
            pvId.setText("");
        }else {
            pvName.setText(ManagerPv.setName(pv,Controller.user));
            pvPhoto.setFill(new ImagePattern(new Image(userWithId.getPhotoNameFromImageFolder())));
            pvId.setText("@"+userWithId.getId());
        }
        visiblePv(true);
        showMessageOfPv(pv);
    }
    private void showMessageOfPv(Pv pv) throws IOException {
        for (int i=0;i<pv.getMessages().size();i++){
            if (i==0){
                addDayInPv(pv.getMessages().get(0).getDate());
            }else if (pv.getMessages().get(i).getDate().getDayOfYear()!=pv.getMessages().get(i-1).getDate().getDayOfYear()){
                addDayInPv(pv.getMessages().get(i).getDate());
            }
            if (pv.getMessages().get(i).getSender()==Controller.user){
                addMyMessage(pv.getMessages().get(i));
            }else {
                addAnotherMessage(pv.getMessages().get(i));
            }
        }
    }
    public void addDayInPv(LocalDateTime time){

    }
    public void addMyMessage(Message message) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MyMessageBox.fxml"));
        Parent parent=fxmlLoader.load();
        MyMessageBoxController myMessageBoxController=fxmlLoader.getController();
        myMessageBoxController.set(message);
        addToReverseVbox(parent);
        myMessageBoxController.handleResizing();
    }
    public void addToReverseVbox(Parent parent){
        parent.setRotate(180);
        reverseVboxForSendMessage.getChildren().add(0,parent);
    }
    public void addAnotherMessage(Message message) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/AnotherMessageBox.fxml"));
        Parent parent=fxmlLoader.load();
        AnotherMessageController anotherMessageController=fxmlLoader.getController();
        anotherMessageController.set(message);
        addToReverseVbox(parent);
    }
}
