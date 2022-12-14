package Controllers.PvControllers;

import Controllers.ForwardPopUpControllers;
import Controllers.GroupControllers.GroupPageController;
import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import DataBase.DataBase;
import View.Controller;
import component.Message;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class MyMessageBoxController {

    public Parent nowParent;
    public Message message;
    public boolean isInPv=true;
    public PvPageController pvPageController;



    public GroupPageController groupPageController;

    @FXML
    public HBox visiblePhoto;
    @FXML
    public Rectangle photoPlace;
    @FXML
    private GridPane messageGrid;
    @FXML
    private TextArea ContentTextArea;
    @FXML
    private GridPane total;

    @FXML
    private Label date;

    @FXML
    private Label edited;

    @FXML
    private GridPane forwardOrReplyGridPane;

    @FXML
    private Label forwardOrReplyLabel;

    @FXML
    private VBox vboxForwardOrReply;
    public boolean edit=true;
    public boolean forwarded=false;
    public boolean reply=false;


    @FXML
    void forwardOrReplyClicked(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (isInPv) {
            if (reply) {
                pvPageController.goToMessageForReply(message.getReplyMessage());
            } else if (forwarded) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
                Parent parent = fxmlLoader.load();
                ShowAnotherUserPageController showAnotherUserPageController = fxmlLoader.getController();
                Controller.main.getChildren().clear();
                Controller.main.getRowConstraints().removeAll();
                Controller.main.getColumnConstraints().removeAll();
                Controller.main.add(parent, 0, 0);
                showAnotherUserPageController.nowParent = parent;
                showAnotherUserPageController.backParent = nowParent;
                showAnotherUserPageController.set(message.getForwardFrom());
                showAnotherUserPageController.start(message.getForwardFrom());
            }
        }else{
            if (reply) {
                groupPageController.goToMessageForReply(message.getReplyMessage());
            } else if (forwarded) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
                Parent parent = fxmlLoader.load();
                ShowAnotherUserPageController showAnotherUserPageController = fxmlLoader.getController();
                Controller.main.getChildren().clear();
                Controller.main.getRowConstraints().removeAll();
                Controller.main.getColumnConstraints().removeAll();
                Controller.main.add(parent, 0, 0);
                showAnotherUserPageController.nowParent = parent;
                showAnotherUserPageController.backParent = nowParent;
                showAnotherUserPageController.set(message.getForwardFrom());
                showAnotherUserPageController.start(message.getForwardFrom());
            }
        }
    }

    public void set(Message message) {
        this.message=message;
        date.setText(message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        if (!message.getEdited()){
            edited.setVisible(false);
            edit=false;
        }
        if (message.getForward()){
            System.out.println(1);
            forwarded=true;
            forwardOrReplyLabel.setText("Forwarded From "+message.getForwardFrom().getUserName());
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(100);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(0);
        }else if (message.getReply()){
            reply=true;
            forwardOrReplyLabel.setText("Reply to ["+message.getReplyMessage().getSender().getUserName()+" :"+message.getReplyMessage().getContent()+"]");
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(100);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(0);
        }else {
            vboxForwardOrReply.setVisible(false);
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(0);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(100);
        }
        ContentTextArea.setText(message.getContent());
        if(isInPv) {
            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction(e -> {
                pvPageController.edit = true;
                pvPageController.editMessage = message;
                pvPageController.edited = edited;
                pvPageController.editMessageTextArea = ContentTextArea;
                pvPageController.enableEdit();
                pvPageController.setWhoEditReply("My Message");
                pvPageController.setFormatEditReply("Edit Message :");
                pvPageController.setContentEditReply(message.getContent());
            });
            MenuItem reply = new MenuItem("Reply");
            reply.setOnAction(e -> {
                pvPageController.reply = true;
                pvPageController.replyMessage = message;
                pvPageController.enableEdit();
                pvPageController.setWhoEditReply(message.getSender().getUserName());
                pvPageController.setFormatEditReply("Reply Message :");
                pvPageController.setContentEditReply(message.getContent());
            });
            MenuItem forward = new MenuItem("Forward");
            forward.setOnAction(e -> {
                try {
                    Forward();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(e -> {
                try {
                    pvPageController.pv.removeMessage(message);
                    pvPageController.showMessageOfPv(pvPageController.pv);
                    pvPageController.updatePvs();
                    pvPageController.closeEditReply(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            ContextMenu menu = new ContextMenu();
            if (!message.getForward()) {
                if (Duration.between(message.getLocalDateTime(), LocalDateTime.now()).getSeconds()<10800) {
                    menu.getItems().add(edit);
                }
            }
            menu.getItems().add(reply);
            menu.getItems().add(forward);
            if (Duration.between(message.getLocalDateTime(), LocalDateTime.now()).getSeconds()<10800) {
                menu.getItems().add(delete);
            }
            messageGrid.setOnContextMenuRequested(e -> {
                menu.show(messageGrid.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });
        }
        else {
            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction(e -> {
                groupPageController.edit = true;
                groupPageController.editMessage = message;
                groupPageController.edited = edited;
                groupPageController.editMessageTextArea = ContentTextArea;
                groupPageController.enableEdit();
                groupPageController.setWhoEditReply("My Message");
                groupPageController.setFormatEditReply("Edit Message :");
                groupPageController.setContentEditReply(message.getContent());
            });
            MenuItem reply = new MenuItem("Reply");
            reply.setOnAction(e -> {
                groupPageController.reply = true;
                groupPageController.replyMessage = message;
                groupPageController.enableEdit();
                groupPageController.setWhoEditReply(message.getSender().getUserName());
                groupPageController.setFormatEditReply("Reply Message :");
                groupPageController.setContentEditReply(message.getContent());
            });
            MenuItem forward = new MenuItem("Forward");
            forward.setOnAction(e -> {
                try {
                    Forward();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(e -> {
                try {
                    groupPageController.group.removeMessage(message);
                    groupPageController.showMessageOfGroup(groupPageController.group);
                    groupPageController.updateGroups();
                    groupPageController.closeEditReply(null);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            ContextMenu menu = new ContextMenu();
            if (!message.getForward()) {
                if (Duration.between(message.getLocalDateTime(), LocalDateTime.now()).getSeconds()<60) {
                    menu.getItems().add(edit);
                }
            }
            menu.getItems().add(reply);
            menu.getItems().add(forward);
            if (Duration.between(message.getLocalDateTime(), LocalDateTime.now()).getSeconds()<60) {
                menu.getItems().add(delete);
            }
            messageGrid.setOnContextMenuRequested(e -> {
                menu.show(messageGrid.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });
        }


//        if(total.getHeight()<=230) {
//            System.out.println(message.getFormat());
//            if (message.getFormat().equalsIgnoreCase("text")) {
//                System.out.println("1");
//                total.setPrefHeight(ContentTextArea.getPrefHeight()*100/64);
////                photoPlace.setWidth(0);
////                photoPlace.setHeight(0);
//                System.out.println(ContentTextArea.getPrefHeight());
//                System.out.println("######");
//                System.out.println(messageGrid.getRowConstraints().get(0).getPercentHeight());
//                System.out.println(messageGrid.getRowConstraints().get(1).getPercentHeight());
//                System.out.println(messageGrid.getRowConstraints().get(2).getPercentHeight());
//                System.out.println(messageGrid.getRowConstraints().get(3).getPercentHeight());
//                System.out.println("######");
//
//                messageGrid.setPrefHeight(total.getPrefHeight());
//                messageGrid.getRowConstraints().get(0).setPercentHeight(23);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(64);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(13);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(0);
////                visiblePhoto.setVisible(false);
//            }else if (message.getFormat().isEmpty()){
//                System.out.println("2");
//                total.setPrefHeight(30+53+200);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(0);
//            }else {
//                System.out.println("3");
//                total.setPrefHeight(ContentTextArea.getPrefHeight()+30+53+200);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight()+200/total.getHeight())*100);
//            }
//        }else {
//            System.out.println(message.getType());
//            if (message.getFormat().equalsIgnoreCase("text")) {
//                System.out.println("!****************");
//                total.setPrefHeight(ContentTextArea.getPrefHeight()+30+53);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight())*100);
//            }else if (message.getFormat().isEmpty()){
//                total.setPrefHeight(30+53+200);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(0);
//            }else {
//                total.setPrefHeight(ContentTextArea.getPrefHeight()+30+53+200);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight()+200/total.getHeight())*100);
//            }
//        }
    }

    private void Forward() throws IOException {
        if (isInPv) {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/fxml/ForwardPopUp.fxml"));
            Parent parent = fxmlLoader.load();
            ForwardPopUpControllers forwardPopUpControllers = fxmlLoader.getController();
            forwardPopUpControllers.isPost = false;
            forwardPopUpControllers.message = message;
            forwardPopUpControllers.isInPv = true;
            forwardPopUpControllers.pvPageController = pvPageController;
            Scene scene = new Scene(parent, 520, 550);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(Controller.stage.getX() + Controller.stage.getWidth() / 2 - 520 / 2);
            stage.setY(Controller.stage.getY() + Controller.stage.getHeight() / 2 - 550 / 2);
            stage.setResizable(false);
            forwardPopUpControllers.popUp = stage;
            forwardPopUpControllers.set();
            stage.show();
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/fxml/ForwardPopUp.fxml"));
            Parent parent = fxmlLoader.load();
            ForwardPopUpControllers forwardPopUpControllers = fxmlLoader.getController();
            forwardPopUpControllers.isPost = false;
            forwardPopUpControllers.message = message;
            forwardPopUpControllers.isInPv = false;
            forwardPopUpControllers.groupPageController = groupPageController;
            Scene scene = new Scene(parent, 520, 550);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setX(Controller.stage.getX() + Controller.stage.getWidth() / 2 - 520 / 2);
            stage.setY(Controller.stage.getY() + Controller.stage.getHeight() / 2 - 550 / 2);
            stage.setResizable(false);
            forwardPopUpControllers.popUp = stage;
            forwardPopUpControllers.set();
            stage.show();
        }
    }

    public void handleResizing(){
        System.out.println("here");
        ContentTextArea.applyCss();
        ContentTextArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node text=ContentTextArea.lookup(".text");
                ContentTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                    @Override
                    public Double call() throws Exception {
                        return text.getBoundsInLocal().getHeight();
                    }
                }, text.boundsInLocalProperty()).add(20));
            }
        });
        try {
            Node text=ContentTextArea.lookup(".text");
            ContentTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
                @Override
                public Double call() throws Exception {
                    return text.getBoundsInLocal().getHeight();
                }
            }, text.boundsInLocalProperty()).add(20));
        }catch (Exception E){
            System.out.println(E);
        }

        ContentTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(total.getHeight()<=230 && message.getFormat().equalsIgnoreCase("text")) {
                total.setPrefHeight(ContentTextArea.getPrefHeight()*100/64);
                System.out.println(ContentTextArea.getPrefHeight());
                messageGrid.setPrefHeight(total.getPrefHeight());
                messageGrid.getRowConstraints().get(0).setPercentHeight(23);
                messageGrid.getRowConstraints().get(2).setPercentHeight(64);
                messageGrid.getRowConstraints().get(3).setPercentHeight(13);
                messageGrid.getRowConstraints().get(1).setPercentHeight(0);
                visiblePhoto.setVisible(false);
            }else {
                if (message.getFormat().equalsIgnoreCase("text")) { //true
                    total.setPrefHeight(ContentTextArea.getPrefHeight()+30+53);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(53/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(2).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight())*100);
                }else if (message.getContent().isEmpty()){
                    total.setPrefHeight(30+53+200);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(30/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(53/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(2).setPercentHeight(0);
                    visiblePhoto.setVisible(true);
                    photoPlace.setHeight(200);
                    photoPlace.setWidth(messageGrid.getWidth());
                    photoPlace.setFill(new ImagePattern(new Image(message.getPhotoAddress())));
                }else {
                    total.setPrefHeight(ContentTextArea.getPrefHeight()+30+60+200);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(30/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(53/total.getHeight()*100);
                    messageGrid.getRowConstraints().get(1).setPercentHeight(200/total.getHeight()*100);
                    visiblePhoto.setVisible(true);
                    photoPlace.setHeight(200);
                    photoPlace.setWidth(messageGrid.getWidth());
                    photoPlace.setFill(new ImagePattern(new Image(message.getPhotoAddress())));
                    messageGrid.getRowConstraints().get(2).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight()+200/total.getHeight())*100);
                }
            }
//            rightGridPain.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
//            rightGridPain.getRowConstraints().get(2).setPercentHeight(84-messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
        });

    }
}
