package Controllers.GroupControllers;

import Controllers.PvControllers.*;
import DataBase.UpdateSqlTable;
import View.Controller;
import component.Group;
import component.Message;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;

public class GroupPageController {

    public Boolean selectGroup=true;
    public Group group;
    public boolean makeNewGroup=false;
    public boolean inSetting=false;

    public String photoName=new String();
    public boolean isPhotoType=false;


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
    private ImageView CancelSearchGroup;
    @FXML
    private ImageView arrow;
    @FXML
    private Label contentEditReply;
    @FXML
    private GridPane editReplyGridPane;
    @FXML
    private Label formatEditReply;
    @FXML
    private Label groupMembersNumber;
    @FXML
    private Label groupName;
    @FXML
    private GridPane helpColumn;
    @FXML
    private GridPane leftGridPane;
    @FXML
    private GridPane messageGridPane;
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private ImageView plusGroup;
    @FXML
    private Circle groupPhoto;
    @FXML
    private VBox groupsVbox;
    @FXML
    private VBox reverseVboxForSendMessage;
    @FXML
    private GridPane rightGridPain;
    @FXML
    private GridPane searchInPvGridPane;
    @FXML
    private TextField searchInGroupTextField;
    @FXML
    private Label searchIndex;
    @FXML
    private TextField searchNameTextField;
    @FXML
    private Label searchTotalFind;
    @FXML
    private GridPane searchUser;
    @FXML
    private GridPane totalGrid;
    @FXML
    private Label whoEditReply;
    @FXML
    private ImageView zarebbin;
    @FXML
    private ImageView photoDisplay;


    @FXML
    public void initialize() throws IOException {
        if (Controller.stage.getWidth()<990){
            totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
        }
        helpColumn.setVisible(false);
        totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
        rightGridPain.getRowConstraints().get(2).setPercentHeight(0);
        editReplyGridPane.setVisible(false);
        searchInPvGridPane.setDisable(true);
        Controller.stage.setMinWidth(755);
        Controller.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (!makeNewGroup && !inSetting) {
                if (Controller.stage.getWidth() < 990) {
                    if (selectGroup) {
                        totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
                        totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
                        leftGridPane.setVisible(false);
                    } else {
                        totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
                        totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
                        leftGridPane.setVisible(true);
                    }
                } else if (!selectGroup) {
                    totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
                    totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
                    leftGridPane.setVisible(true);

                }
            }
        });
        searchUser.setVisible(false);
        updateGroups();
        visibleGroup(false);
    }
    @FXML
    public void maximize(MouseEvent event) {
        if (Controller.stage.getWidth()<987){
            selectGroup=false;
            totalGrid.getColumnConstraints().get(0).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            leftGridPane.setVisible(true);
        }else if (totalGrid.getColumnConstraints().get(0).getPercentWidth()==0){
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            leftGridPane.setVisible(true);
        }else{
            totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            leftGridPane.setVisible(false);
        }
    }
    @FXML
    public void newGroup(MouseEvent event) throws IOException {
        if (plusGroup.getRotate()==0){
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusGroup);
            rotateTransition.setToAngle(45);
            rotateTransition.play();
            showMakeNewGroup(true);
            makeNewGroup=true;
        }else {
            RotateTransition rotateTransition=new RotateTransition();
            rotateTransition.setNode(plusGroup);
            rotateTransition.setToAngle(0);
            rotateTransition.play();
            searchUser.setVisible(false);
            groupsVbox.getChildren().clear();
            showMakeNewGroup(false);
            updateGroups();
            makeNewGroup=false;
        }
    }
    @FXML
    public void searchGroup(MouseEvent event) throws IOException {
        groupsVbox.getChildren().clear();
        ArrayList<Group> GroupFind=new ArrayList<>();
        for (int i = 0; i<Controller.user.getGroups().size(); i++){
            if (Controller.find(Controller.user.getGroups().get(i).getName(),searchNameTextField.getText())){
                GroupFind.add(Controller.user.getGroups().get(i));
            }
        }
        if (GroupFind.size()==0){
            searchNameTextField.setStyle("-fx-background-color: red");
            searchNameTextField.setText("");
            searchNameTextField.setPromptText("group not Found :[");
        }else {
            CancelSearchGroup.setVisible(true);
            showNewGroups(GroupFind);
            CancelSearchGroup.setVisible(true);
        }
    }
    private void showNewGroups(ArrayList<Group> groups) throws IOException {
        groupsVbox.getChildren().clear();
        for (int i=0;i<groups.size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/GroupIconInGroupPage.fxml"));
            Parent parent=fxmlLoader.load();
            GroupIconInGroupPageController groupIconInGroupPageController=fxmlLoader.getController();
            groupIconInGroupPageController.groupPageController=this;
            groupIconInGroupPageController.set(groups.get(i));
            addGroupIcon(parent);
        }
    }
    private void addGroupIcon(Parent parent) {

        groupsVbox.getChildren().add(parent);
    }
    @FXML
    void CancellSearchGroupClicked(MouseEvent event) throws IOException {
        searchNameTextField.setText("");
        searchNameTextField.setStyle("-fx-background-color: White;");
        CancelSearchGroup.setVisible(false);
        groupsVbox.getChildren().clear();
        updateGroups();
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
            isPhotoType=true;
        }
    }
    public void visibleGroup(boolean visible){
        totalGrid.getChildren().get(1).setVisible(visible);
        reverseVboxForSendMessage.getChildren().clear();
        reverseVboxForSendMessage.getChildren().removeAll();
        arrow.setRotate(180);
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
    public void sendMessage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (!isPhotoType) {
            if (!edit && !reply) {
                if (!messageTextArea.getText().isEmpty()) {
                    Message message = new Message(Controller.user, "Text", messageTextArea.getText(), false, false, false);
                    group.addMessage(message);
                    addMyMessage(message);
                    message.setIsPvOrGroup("group",group.getSqlId());
                    message.addMessageToTable();
                    messageTextArea.setText("");
                }
            } else if (edit) {
                if (!messageTextArea.getText().isEmpty()) {
                    editMessage.setContent(messageTextArea.getText());
                    editMessage.setEdited(true);
                    edited.setVisible(true);
                    editMessageTextArea.setText(messageTextArea.getText());
                    messageTextArea.setText("");
                    closeEditReply(null);
                    showMessageOfGroup(group);
                    UpdateSqlTable.editMessage(editMessage);
                }
            } else if (reply) {
                if (!messageTextArea.getText().isEmpty()) {
                    Message message = new Message(Controller.user, "Text", messageTextArea.getText(), false, false, true);
                    message.setReplyMessage(replyMessage);
                    group.addMessage(message);
                    addMyMessage(message);
                    message.setIsPvOrGroup("group",group.getSqlId());
                    messageTextArea.setText("");
                    closeEditReply(null);
                    message.addMessageToTable();
                }
            }
        }else {
            if (!edit && !reply) {
                Message message = new Message(Controller.user, "image", messageTextArea.getText(), false, false, false);
                group.addMessage(message);
                message.setPhotoAddress(photoName);
                message.setIsPvOrGroup("group",group.getSqlId());
                addMyMessage(message);
                messageTextArea.setText("");
                message.addMessageToTable();
            } else if (edit) {
                System.out.println("not handled :[]");
            } else if (reply) {
                if (!messageTextArea.getText().isEmpty()) {
                    Message message = new Message(Controller.user, "image", messageTextArea.getText(), false, false, true);
                    message.setReplyMessage(replyMessage);
                    message.setPhotoAddress(photoName);
                    group.addMessage(message);
                    addMyMessage(message);
                    message.setIsPvOrGroup("group",group.getSqlId());
                    messageTextArea.setText("");
                    message.addMessageToTable();
                    closeEditReply(null);
                }
            }
        }
        isPhotoType=false;
        photoName=null;
        photoDisplay.setImage(new Image(String.valueOf(getClass().getResource("/images/clip2.png"))));
        Controller.user.getReadMessageGroup().set(Controller.user.getGroups().indexOf(group),group.getMessages().size());
        UpdateSqlTable.setReadMessageGroup(group,Controller.user);
        updateGroups();
    }
    public void addMyMessage(Message message) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MyMessageBox.fxml"));
        Parent parent=fxmlLoader.load();
        MyMessageBoxController myMessageBoxController=fxmlLoader.getController();
        myMessageBoxController.groupPageController=this;
        myMessageBoxController.nowParent=nowParent;
        myMessageBoxController.isInPv=false;
        myMessageBoxController.set(message);
        addToReverseVbox(parent);
        myMessageBoxController.handleResizing();
    }
    public void addToReverseVbox(Parent parent){
        parent.setRotate(180);
        reverseVboxForSendMessage.getChildren().add(0,parent);
    }
    public void showMessageOfGroup(Group group) throws IOException {
        reverseVboxForSendMessage.getChildren().clear();
        reverseVboxForSendMessage.getChildren().removeAll();
        for (int i=0;i<group.getMessages().size();i++){
            if (i==0){
                addDayInPv(group.getMessages().get(0).getDate());
            }else if (group.getMessages().get(i).getDate().getDayOfYear()!=group.getMessages().get(i-1).getDate().getDayOfYear()){
                addDayInPv(group.getMessages().get(i).getDate());
            }
            if (group.getMessages().get(i).getSender()==Controller.user){
                addMyMessage(group.getMessages().get(i));
            }else {
                addAnotherMessage(group.getMessages().get(i));
            }
        }
    }
    public void addDayInPv(LocalDateTime time){

    }
    public void addAnotherMessage(Message message) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/AnotherMessageBoxInGroup.fxml"));
        Parent parent=fxmlLoader.load();
        AnotherMessageBoxInGroupController anotherMessageBoxInGroupController=fxmlLoader.getController();
        anotherMessageBoxInGroupController.groupPageController=this;
        anotherMessageBoxInGroupController.set(message);
        anotherMessageBoxInGroupController.nowParent=nowParent;
        addToReverseVbox(parent);
        anotherMessageBoxInGroupController.handleResizing();
    }
    @FXML
    public void searchWordInGroup(MouseEvent event) {
        if(indexOfSearch!=0) {
            reverseVboxForSendMessage.getChildren().get(findMessages.get(indexOfSearch - 1)).setStyle("-fx-border-color: transparent");
        }
        findMessages.clear();
        indexOfSearch=0;
        searchIndex.setText("(0)");
        System.out.println(findMessages.size());
        if(searchInGroupTextField.getText().isEmpty()){
            searchInGroupTextField.setStyle("-fx-background-color: red");
            searchInGroupTextField.setText("");
            searchInGroupTextField.setPromptText("it's empty :[");
            searchTotalFind.setText("(0)");
            searchIndex.setText("(0)");
            totalFindSearch=0;
        }else {
            searchInGroupTextField.setStyle("-fx-background-color: white");
            ArrayList<Message> messageFind=new ArrayList<>();
            for (int i = group.getMessages().size()-1; i>=0;i--){
                if (Controller.find(group.getMessages().get(i).getContent(),searchInGroupTextField.getText())){
                    messageFind.add(group.getMessages().get(i));
                    findMessages.add(group.getMessages().size()-1-i);
                }
            }
            if (messageFind.size()==0){
                System.out.println(":(((");
                searchInGroupTextField.setStyle("-fx-background-color: red");
                searchInGroupTextField.setText("");
                searchInGroupTextField.setPromptText("not Found :[");
                searchTotalFind.setText("(0)");
                searchIndex.setText("(0)");
                totalFindSearch=0;
            }else {
                searchInGroupTextField.setStyle("-fx-background-color: Green");
                settingSearchInGroup(messageFind);
            }
        }
    }
    private void settingSearchInGroup(ArrayList<Message> messageFind) {
        searchTotalFind.setText("("+messageFind.size()+")");
        totalFindSearch=messageFind.size();
    }
    @FXML
    public void visibleSearchInGroup(MouseEvent event) {
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
            searchInGroupTextField.setText("");
            searchInGroupTextField.setStyle("-fx-background-color: White");
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
    public void updateGroups() throws IOException {
        groupsVbox.getChildren().clear();
        groupsVbox.getChildren().removeAll();
        User user=Controller.user;
        ArrayList<Group> groups=new ArrayList<>();
        System.out.println(user.getGroups().size());
        for (int i=0;i<user.getGroups().size();i++){
            groups.add(user.getGroups().get(i));
        }
        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
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
        for (int i=0;i<groups.size();i++){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/GroupIconInGroupPage.fxml"));
            Parent parent=fxmlLoader.load();
            GroupIconInGroupPageController groupIconInGroupPageController=fxmlLoader.getController();
            groupIconInGroupPageController.groupPageController=this;
            groupIconInGroupPageController.set(groups.get(i));
            addGroupIcon(parent);
        }
    }
    public void showGroup (Group group) throws SQLException, ClassNotFoundException, IOException {
        this.group=group;
        if (Controller.stage.getWidth() < 990){
            totalGrid.getColumnConstraints().get(1).setPercentWidth(100);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(0);
            leftGridPane.setVisible(false);
            helpColumn.setVisible(false);
        }else {
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            leftGridPane.setVisible(true);
            helpColumn.setVisible(false);
        }
        groupName.setText(group.getName());
        groupPhoto.setFill(new ImagePattern(new Image(group.getPhoto())));
        groupMembersNumber.setText("Num:"+group.getMembers().size());
        if ((group.getBanGroup() && (!group.getAdmins().contains(Controller.user)) && (!(group.getOwner()==Controller.user)) )||(group.getLinkedMembers().get(Controller.user))) {
            if (group.getBanGroup()){
                messageTextArea.setText("Group In Ban Mode");
            }else {
                messageTextArea.setText("you are Banned From Group");
            }
            messageTextArea.setDisable(true);
        }else {
            messageTextArea.setDisable(false);
        }
        visibleGroup(true);
        showMessageOfGroup(group);
        if (group.getMessages().size()==Controller.user.getReadMessageGroup().get(Controller.user.getGroups().indexOf(group))){
            arrow.setRotate(0);
        }
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
            if (group.getMessages().size()==Controller.user.getReadMessageGroup().get(Controller.user.getGroups().indexOf(group))){
                messageScrollPane.setVvalue(0);
            }else {
                messageScrollPane.setVvalue(reverseVboxForSendMessage.getChildren().get(group.getMessages().size() - Controller.user.getReadMessageGroup().get(Controller.user.getGroups().indexOf(group)) - 1).getLayoutY() / reverseVboxForSendMessage.getHeight());
            }
            Controller.user.getReadMessageGroup().set(Controller.user.getGroups().indexOf(group),group.getMessages().size());
            UpdateSqlTable.setReadMessageGroup(group,Controller.user);
        }else {
            messageScrollPane.setVvalue(0);
        }
        updateGroups();
    }
    public void goToMessageForReply(Message message){
        if (group.getMessages().indexOf(message)>=0) {
            messageScrollPane.setVvalue(reverseVboxForSendMessage.getChildren().get(group.getMessages().size() - group.getMessages().indexOf(message) - 1).getLayoutY() / reverseVboxForSendMessage.getHeight());
            reverseVboxForSendMessage.getChildren().get(group.getMessages().size() - group.getMessages().indexOf(message) - 1).setStyle("-fx-border-color: Gold");
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    reverseVboxForSendMessage.getChildren().get(group.getMessages().size() - group.getMessages().indexOf(message) - 1).setStyle("-fx-border-color: transparent");
                }
            }, 3000l);
        }
    }
    public void showMakeNewGroup(boolean visible) throws IOException {
        if (visible){
            helpColumn.setVisible(true);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(70);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/MakeNewGroup.fxml"));
            Parent parent=fxmlLoader.load();
            helpColumn.getChildren().clear();
            helpColumn.getChildren().add(parent);
            rightGridPain.setVisible(false);
            MakeNewGroupController makeNewGroupController=fxmlLoader.getController();
            makeNewGroupController.groupPageController=this;
            makeNewGroupController.start();
        }else {
            helpColumn.setVisible(false);
            rightGridPain.setVisible(true);
            totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
            totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
            totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
            helpColumn.getChildren().clear();
            updateGroups();
        }
    }
    @FXML
    void Setting(MouseEvent event) throws IOException {
        inSetting=true;
        helpColumn.setVisible(true);
        totalGrid.getColumnConstraints().get(1).setPercentWidth(0);
        totalGrid.getColumnConstraints().get(2).setPercentWidth(70);
        totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/GroupInfoController.fxml"));
        Parent parent=fxmlLoader.load();
        helpColumn.getChildren().add(parent);
        GroupInfoController groupInfoController=fxmlLoader.getController();
        groupInfoController.groupPageController=this;
        groupInfoController.group=group;
        groupInfoController.setFirst();
    }
    public void beckFromSetting() throws SQLException, IOException, ClassNotFoundException {
        inSetting=false;
        helpColumn.setVisible(false);
        totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
        totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
        totalGrid.getColumnConstraints().get(0).setPercentWidth(30);
        helpColumn.getChildren().clear();
        showGroup(group);
    }
    public void closeInfo() throws SQLException, IOException, ClassNotFoundException {
        totalGrid.getColumnConstraints().get(2).setPercentWidth(0);
        totalGrid.getColumnConstraints().get(1).setPercentWidth(70);
        showGroup(group);
        helpColumn.setVisible(false);
    }
}
