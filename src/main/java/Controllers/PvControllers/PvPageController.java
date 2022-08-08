package Controllers.PvControllers;

import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import DataBase.DataBase;
import Manager.ManagerPv;
import View.Controller;
import component.Message;
import component.Pv;
import component.User;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import DataBase.UpdateSqlTable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PvPageController {


    @FXML
    public ImageView photoDisplay;
    public String photoName=new String();
    public boolean isPhotoType=false;

    public Boolean selectPv=false;
    public Pv pv;
    public int indexOfSearch=0;
    public int totalFindSearch=0;
    public ArrayList<Integer> findMessages=new ArrayList<>();

    public boolean edit=false;
    public Label edited;
    public Message editMessage;
    public TextArea editMessageTextArea;
    public boolean reply=false;
    public Message replyMessage;

    public Parent nowParent;

    @FXML
    private ImageView arrow;
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private GridPane editReplyGridPane;
    @FXML
    private Label formatEditReply;
    @FXML
    private Label whoEditReply;
    @FXML
    private Label contentEditReply;
    @FXML
    private GridPane leftGridPane;
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
    private TextArea messageTextArea;
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
    private TextField searchInPvTextField;




    @FXML
    public void initialize() throws IOException {
        if (Controller.stage.getWidth()<990){
            totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
        }
        rightGridPain.getRowConstraints().get(2).setPercentHeight(0);
        editReplyGridPane.setVisible(false);
        searchInPvGridPane.setDisable(true);
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
                    leftGridPane.setVisible(false);
                    rightGridPain.setVisible(true);
                }else {
                    totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
                    totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
                    leftGridPane.setVisible(true);
                    rightGridPain.setVisible(false);
                }
            }
        });

        searchUser.setVisible(false);
        updatePvs();
        visiblePv(false);

    }
    public void visiblePv(boolean visible){
        totalGrid.getChildren().get(1).setVisible(visible);
        reverseVboxForSendMessage.getChildren().clear();
        reverseVboxForSendMessage.getChildren().removeAll();
        arrow.setRotate(180);
        if (visible){
//            messageTextArea.applyCss();

        }
        messageTextArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node text = messageTextArea.lookup(".text");
                messageTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                    @Override
                    public Double call() throws Exception {
                        return text.getBoundsInLocal().getHeight();
                    }
                }, text.boundsInLocalProperty()).add(20));
            }
        });
        messageTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            messageGridPane.setPrefHeight(messageTextArea.getPrefHeight()*100/90);
            rightGridPain.getRowConstraints().get(4).setPercentHeight(messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
            rightGridPain.getRowConstraints().get(3).setPercentHeight(84-messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
        });

    }
    @FXML
    public void BlockUser(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        if (pv.getBlock() && pv.getBlocker()!=Controller.user){
            System.out.println(":[");
        } else if (!pv.getBlock()) {
            pv.setBlock(true, Controller.user);
            messageTextArea.setDisable(true);
            showPv(Controller.user.getLinkedPvs().get(pv));
        }else {
            pv.setBlock(false, Controller.user);
            messageTextArea.setDisable(false);
            showPv(Controller.user.getLinkedPvs().get(pv));
        }
    }
    @FXML
    public void VisitPage(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
        Parent parent=fxmlLoader.load();
        ShowAnotherUserPageController showAnotherUserPageController=fxmlLoader.getController();
        Controller.main.getChildren().clear();
        Controller.main.getRowConstraints().removeAll();
        Controller.main.getColumnConstraints().removeAll();
        Controller.main.add(parent,0,0);
        showAnotherUserPageController.nowParent=parent;
        showAnotherUserPageController.backParent=nowParent;
        showAnotherUserPageController.set(Controller.user.getLinkedPvs().get(pv));
    }
    @FXML
    public void maximize(MouseEvent event) {
        if (Controller.stage.getWidth()<987){
            selectPv=false;
            totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
            leftGridPane.setVisible(true);
        }else if (totalGrid.getColumnConstraints().get(0).getPercentWidth()==0){
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            leftGridPane.setVisible(true);
        }else{
            totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
            leftGridPane.setVisible(false);
        }

    }
    @FXML
    public void newPv(MouseEvent event) throws IOException {
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
    public void searchPv(MouseEvent event) throws IOException {
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
        pvsVbox.getChildren().clear();
        pvsVbox.getChildren().removeAll();
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
    public void selectFollowersNewPv(MouseEvent event) throws IOException {
        pvsVbox.getChildren().removeAll();
        pvsVbox.getChildren().clear();
        if (plusPv.getRotate()==0){
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusPv);
            rotateTransition.setToAngle(45);
            rotateTransition.play();
            searchUser.setVisible(true);
            showNewUsers(Controller.user.getFollowers());
        }else {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusPv);
            rotateTransition.setToAngle(0);
            rotateTransition.play();
            searchUser.setVisible(false);
            pvsVbox.getChildren().clear();
            pvsVbox.getChildren().removeAll();
            updatePvs();
        }
    }
    @FXML
    public void selectPhotoMessage(MouseEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage stage=(Stage) totalGrid.getScene().getWindow();
        File file=  fileChooser.showOpenDialog(stage);
        if(file!=null){
            Image image = new Image(file.toURI().toString());
            photoName=file.toURI().toString();
            photoDisplay.setImage(image);
        }
    }
    @FXML
    public void sendMessage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (!edit && !reply) {
            if (!messageTextArea.getText().isEmpty()) {
                Message message = new Message(Controller.user, "Text", messageTextArea.getText(), false, false, false);
                message.setIsPvOrGroup("pv",pv.getPvId());
                pv.addMessage(message);
                addMyMessage(message);
                messageTextArea.setText("");
                message.addMessageToTable(Controller.user,message.getFormat(),message.getContent(),Controller.user.getGender(),message.getLocalDateTime(),message.getForward(),
                message.getForwardFrom(),message.getEdited(),message.getReply(),message.getIsMessage(),message.getReplyOfMessageId(),message.getMessageId(),
                        message.getIsPvOrGroup(),message.getPvOrGroupId(),message.getPhotoAddress());
            }

            updatePvs();
        }else if (edit){
            if (!messageTextArea.getText().isEmpty()) {
                editMessage.setContent(messageTextArea.getText());
                editMessage.setEdited(true);
                edited.setVisible(true);
                editMessageTextArea.setText(messageTextArea.getText());
                messageTextArea.setText("");
                closeEditReply(null);
                UpdateSqlTable.editMessage(editMessage);
                showMessageOfPv(pv);
            }
        }else if (reply){
            if (!messageTextArea.getText().isEmpty()) {
                Message message = new Message(Controller.user, "Text", messageTextArea.getText(), false, false, true);
                message.setIsPvOrGroup("pv",pv.getPvId());
                message.setReplyMessage(replyMessage);
                pv.addMessage(message);
                addMyMessage(message);
                messageTextArea.setText("");
                closeEditReply(null);
                message.addMessageToTable(Controller.user,message.getFormat(),message.getContent(),Controller.user.getGender(),message.getLocalDateTime(),message.getForward(),
                        message.getForwardFrom(),message.getEdited(),message.getReply(),message.getIsMessage(),message.getReplyOfMessageId(),message.getMessageId(),
                        message.getIsPvOrGroup(),message.getPvOrGroupId(),message.getPhotoAddress());
            }
        }
        Controller.user.getReadMessagePv().set(Controller.user.getPvs().indexOf(pv),pv.getMessages().size());
        UpdateSqlTable.setReadMessagePv(pv,Controller.user);
        updatePvs();
    }
    @FXML
    public void searchWordInPv(MouseEvent event) {
        if(indexOfSearch!=0) {
            reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch - 1)).setStyle("-fx-border-color: transparent");
        }
        findMessages.clear();
        indexOfSearch=0;
        searchIndex.setText("(0)");
        System.out.println(findMessages.size());
        if(searchInPvTextField.getText().isEmpty()){
            searchInPvTextField.setStyle("-fx-background-color: red");
            searchInPvTextField.setText("");
            searchInPvTextField.setPromptText("it's empty :[");
            searchTotalFind.setText("(0)");
            searchIndex.setText("(0)");
            totalFindSearch=0;
        }else {
            searchInPvTextField.setStyle("-fx-background-color: white");
            ArrayList<Message> messageFind=new ArrayList<>();
            System.out.println("****");
            System.out.println(pv.getMessages().size());
            System.out.println("****");
            for (int i = pv.getMessages().size()-1; i>=0;i--){
                System.out.println(pv.getMessages().get(i).getContent());
                System.out.println("___");
                if (Controller.find(pv.getMessages().get(i).getContent(),searchInPvTextField.getText())){
                    messageFind.add(pv.getMessages().get(i));
                    findMessages.add(pv.getMessages().size()-1-i);
                }
            }
            if (messageFind.size()==0){
                System.out.println(":(((");
                searchInPvTextField.setStyle("-fx-background-color: red");
                searchInPvTextField.setText("");
                searchInPvTextField.setPromptText("not Found :[");
                searchTotalFind.setText("(0)");
                searchIndex.setText("(0)");
                totalFindSearch=0;
            }else {
                searchInPvTextField.setStyle("-fx-background-color: Green");
                settingSearchInpv(messageFind);
            }
        }
    }
    private void settingSearchInpv(ArrayList<Message> messageFind) {
//        ArrayList<Integer> indexOfFindingMessages=new ArrayList<>();
//        for (int i=0;i<messageFind.size();i++){
//            indexOfFindingMessages.add(pv.getMessages().indexOf(messageFind.get(i)));
//        }
        searchTotalFind.setText("("+messageFind.size()+")");
        totalFindSearch=messageFind.size();
//        findMessages=indexOfFindingMessages;
        System.out.println(Arrays.asList(findMessages).toString());
    }
    @FXML
    public void visibleSearchInPv(MouseEvent event) {
        if (zarebbin.getRotate()==0) {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(zarebbin);
            rotateTransition.setToAngle(45);
            rotateTransition.play();
            searchInPvGridPane.setDisable(false);
        }else {
            if (indexOfSearch!=0) {
                reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch - 1)).setStyle("-fx-border-color: transparent");
            }
            indexOfSearch=0;
            searchIndex.setText("(0)");
            searchTotalFind.setText("(0)");
            findMessages.clear();
            searchInPvTextField.setText("");
            searchInPvTextField.setStyle("-fx-background-color: White");
            searchInPvGridPane.setDisable(true);
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(zarebbin);
            rotateTransition.setToAngle(0);
            rotateTransition.play();
        }
    }
    public void back(MouseEvent mouseEvent) {

        Controller.main.getChildren().clear();
    }
    public void updatePvs() throws IOException {
        pvsVbox.getChildren().clear();
        pvsVbox.getChildren().removeAll();
        User user=Controller.user;
        ArrayList<Pv> pvs=new ArrayList<>();
        for (int i=0;i<user.getPvs().size();i++){
            pvs.add(user.getPvs().get(i));
        }
        Collections.sort(pvs, new Comparator<Pv>() {
            @Override
            public int compare(Pv o1, Pv o2) {
                if (o1.getMessages().size()==0){
                    return 1;
                }else if (o2.getMessages().size()==0){
                    return -1;
                }
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
            pvIconController.pvPageController=this;
            pvIconController.set(pvs.get(i));
            addPvIcon(parent);
        }
    }
    public void addPvIcon(Parent pv){
        pvsVbox.getChildren().add(pv);
    }
    public void showPv(User userWithId) throws SQLException, ClassNotFoundException, IOException {
        if (Controller.stage.getWidth() < 990){
            totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
            leftGridPane.setVisible(false);
        }else {
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
        }
        if (Controller.user.getPv(userWithId)==null){
            Controller.user.addPv(userWithId);
        }
        selectPv=true;
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
        if (pv.getBlock()) {
            messageTextArea.setDisable(true);
        }else {
            messageTextArea.setDisable(false);
        }
        visiblePv(true);
        showMessageOfPv(pv);
        if (pv.getMessages().size()==Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv))){
            arrow.setRotate(0);
        }
    }
    public void showMessageOfPv(Pv pv) throws IOException {
        reverseVboxForSendMessage.getChildren().clear();
        reverseVboxForSendMessage.getChildren().removeAll();
        System.out.println(pv.getMessages().size());
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
        if (reverseVboxForSendMessage.getChildren().size()>0) {
        }
    }
    public void addDayInPv(LocalDateTime time){

    }
    public void addMyMessage(Message message) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MyMessageBox.fxml"));
        Parent parent=fxmlLoader.load();
        MyMessageBoxController myMessageBoxController=fxmlLoader.getController();
        myMessageBoxController.pvPageController=this;
        myMessageBoxController.nowParent=nowParent;
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
        anotherMessageController.pvPageController=this;
        anotherMessageController.nowParent=nowParent;
        anotherMessageController.set(message);
        addToReverseVbox(parent);
        anotherMessageController.handleResizing();
    }
    public void setFormatEditReply(String formatEditReply) {
        this.formatEditReply.setText(formatEditReply);
    }
    public void setWhoEditReply(String whoEditReply) {
        this.whoEditReply.setText(whoEditReply);
    }
    public void setContentEditReply(String contentEditReply) {
        this.contentEditReply.setText(contentEditReply);
    }
    public void enableEdit() {
        searchInPvGridPane.setVisible(false);
        rightGridPain.getRowConstraints().get(1).setPercentHeight(0);
        rightGridPain.getRowConstraints().get(2).setPercentHeight(6);
        editReplyGridPane.setVisible(true);
    }
    public void closeEditReply(MouseEvent mouseEvent) {
        searchInPvGridPane.setVisible(true);
        rightGridPain.getRowConstraints().get(1).setPercentHeight(6);
        rightGridPain.getRowConstraints().get(2).setPercentHeight(0);
        editReplyGridPane.setVisible(false);
        edit=false;
        reply=false;
    }
    public void nextSearchIndex(MouseEvent mouseEvent) {
        if (indexOfSearch<totalFindSearch){
            if (indexOfSearch!=0){
                reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch-1)).setStyle("-fx-border-color: transparent");
            }
            indexOfSearch++;
            searchIndex.setText("("+indexOfSearch+")");
        }
        goToFindedMessage();
    }
    public void backIndexSearch(MouseEvent mouseEvent) {
        if (indexOfSearch>1){
            reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch-1)).setStyle("-fx-border-color: transparent");
            indexOfSearch--;
            searchIndex.setText("("+indexOfSearch+")");
        }
        goToFindedMessage();
    }
    private void goToFindedMessage() {
        messageScrollPane.setVvalue(reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch-1)).getLayoutY()/reverseVboxForSendMessage.getHeight());
        reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch-1)).setStyle("-fx-border-color: Gold");
    }
    public void newMessageOrDown(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        if (arrow.getRotate()==180 && reverseVboxForSendMessage.getChildren().size()>0) {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setToAngle(0);
            rotateTransition.setNode(arrow);
            rotateTransition.play();
            if (pv.getMessages().size()==Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv))){
                messageScrollPane.setVvalue(0);
            }else {
                System.out.println(reverseVboxForSendMessage.getChildren().get(pv.getMessages().size() - Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv)) - 1).getLayoutY() / reverseVboxForSendMessage.getHeight());
                messageScrollPane.setVvalue(reverseVboxForSendMessage.getChildren().get(pv.getMessages().size() - Controller.user.getReadMessagePv().get(Controller.user.getPvs().indexOf(pv)) - 1).getLayoutY() / reverseVboxForSendMessage.getHeight());
            }
            Controller.user.getReadMessagePv().set(Controller.user.getPvs().indexOf(pv),pv.getMessages().size());
            UpdateSqlTable.setReadMessagePv(pv,Controller.user);
        }else {
            messageScrollPane.setVvalue(0);
        }
        updatePvs();
    }
    public void goToMessageForReply(Message message){
        if (pv.getMessages().indexOf(message)>=0) {
            messageScrollPane.setVvalue(reverseVboxForSendMessage.getChildren().get(pv.getMessages().size() - pv.getMessages().indexOf(message) - 1).getLayoutY() / reverseVboxForSendMessage.getHeight());
            reverseVboxForSendMessage.getChildren().get(pv.getMessages().size() - pv.getMessages().indexOf(message) - 1).setStyle("-fx-border-color: Gold");
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    reverseVboxForSendMessage.getChildren().get(pv.getMessages().size() - pv.getMessages().indexOf(message) - 1).setStyle("-fx-border-color: transparent");
                }
            }, 3000l);
        }
    }
}
