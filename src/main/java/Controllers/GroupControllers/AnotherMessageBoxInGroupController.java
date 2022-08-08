package Controllers.GroupControllers;

import Controllers.ForwardPopUpControllers;
import Controllers.GroupControllers.GroupPageController;
import Controllers.OtherUserPageControllers.ShowAnotherUserPageController;
import Controllers.PvControllers.PvPageController;
import View.Controller;
import component.Message;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class AnotherMessageBoxInGroupController {

    public GroupPageController groupPageController;
    public Parent nowParent;
    @FXML
    private HBox visiblePhoto;
    @FXML
    private Rectangle photoPlace;
    @FXML
    private GridPane messageBoxWithPhoto;
    @FXML
    private Circle image;
    @FXML
    private Label sender;
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
    public Message message;

    @FXML
    void forwardOrReplyClicked(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (message.getReply()) {
            groupPageController.goToMessageForReply(message.getReplyMessage());
        } else if (message.getForward()) {
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

    public void set(Message message) {
        this.message=message;
        messageBoxWithPhoto.widthProperty().addListener((obs, oldVal, newVal) -> {
            messageBoxWithPhoto.getColumnConstraints().get(0).setPercentWidth(70/messageBoxWithPhoto.getWidth()*100);
            messageBoxWithPhoto.getColumnConstraints().get(1).setPercentWidth(100-70/messageBoxWithPhoto.getWidth()*100);

        });
        date.setText(message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        if (!message.getEdited()){
            edited.setVisible(false);
        }
        if (message.getForward()){
            forwardOrReplyLabel.setText("Forwarded From "+message.getForwardFrom().getUserName());
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(50);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(50);
        }else if (message.getReply()){
            forwardOrReplyLabel.setText("Reply to "+message.getReplyMessage().getSender().getUserName()+" :"+message.getReplyMessage().getContent());
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(50);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(50);
        }else {
            vboxForwardOrReply.setVisible(false);
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(0);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(100);

        }
        ContentTextArea.setText(message.getContent());
        image.setFill(new ImagePattern(new Image(message.getSender().getPhotoNameFromImageFolder())));
        sender.setText(message.getSender().getUserName()+":");

        MenuItem reply = new MenuItem("Reply");
        reply.setOnAction(e -> {
            groupPageController.reply=true;
            groupPageController.replyMessage=message;
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
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(reply);
        menu.getItems().add(forward);
        messageGrid.setOnContextMenuRequested(e -> {
            menu.show(messageGrid.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });

    }
    private void Forward() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("/fxml/ForwardPopUp.fxml"));
        Parent parent = fxmlLoader.load();
        ForwardPopUpControllers forwardPopUpControllers = fxmlLoader.getController();
        forwardPopUpControllers.isPost = false;
        forwardPopUpControllers.message = message;
        forwardPopUpControllers.isInPv = false;
        forwardPopUpControllers.groupPageController = groupPageController;
        Scene scene = new Scene(parent, 520, 550);Stage stage = new Stage();
        stage.setScene(scene);
        stage.setX(Controller.stage.getX() + Controller.stage.getWidth() / 2 - 520 / 2);
        stage.setY(Controller.stage.getY() + Controller.stage.getHeight() / 2 - 550 / 2);
        stage.setResizable(false);
        forwardPopUpControllers.popUp = stage;
        forwardPopUpControllers.set();
        stage.show();
    }

    public void handleResizing() {
        System.out.println("here");
        ContentTextArea.applyCss();
        Node text = ContentTextArea.lookup(".text");
        ContentTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return text.getBoundsInLocal().getHeight();
            }
        }, text.boundsInLocalProperty()).add(20));
//        ContentTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
//            if (total.getHeight() <= 230) {
//                total.setPrefHeight(ContentTextArea.getPrefHeight() * 100 / 60);
//            } else {
//                total.setPrefHeight(ContentTextArea.getPrefHeight() + 30 + 53);
//                messageGrid.getRowConstraints().get(0).setPercentHeight(30 / total.getHeight() * 100);
//                messageGrid.getRowConstraints().get(2).setPercentHeight(53 / total.getHeight() * 100);
//                messageGrid.getRowConstraints().get(1).setPercentHeight(100 - (53 / total.getHeight() + 30 / total.getHeight()) * 100);
//
//            }
//
//
////            rightGridPain.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
////            rightGridPain.getRowConstraints().get(2).setPercentHeight(84-messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
//        });
        ContentTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (total.getHeight() <= 230 && message.getFormat().equalsIgnoreCase("text")) {
                total.setPrefHeight(ContentTextArea.getPrefHeight() * 100 / 64);
                System.out.println(ContentTextArea.getPrefHeight());
                messageGrid.setPrefHeight(total.getPrefHeight());
                messageGrid.getRowConstraints().get(0).setPercentHeight(23);
                messageGrid.getRowConstraints().get(2).setPercentHeight(64);
                messageGrid.getRowConstraints().get(3).setPercentHeight(13);
                messageGrid.getRowConstraints().get(1).setPercentHeight(0);
                visiblePhoto.setVisible(false);
            } else {
                if (message.getFormat().equalsIgnoreCase("text")) { //true
                    total.setPrefHeight(ContentTextArea.getPrefHeight() + 30 + 53);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(30 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(53 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(2).setPercentHeight(100 - (53 / total.getHeight() + 30 / total.getHeight()) * 100);
                } else if (message.getContent().isEmpty()) {
                    total.setPrefHeight(30 + 53 + 200);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(30 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(53 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(1).setPercentHeight(200 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(2).setPercentHeight(0);
                    visiblePhoto.setVisible(true);
                    photoPlace.setHeight(200);
                    photoPlace.setWidth(messageGrid.getWidth());
                    photoPlace.setFill(new ImagePattern(new Image(message.getPhotoAddress())));
                } else {
                    total.setPrefHeight(ContentTextArea.getPrefHeight() + 30 + 60 + 200);
                    messageGrid.getRowConstraints().get(3).setPercentHeight(30 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(0).setPercentHeight(53 / total.getHeight() * 100);
                    messageGrid.getRowConstraints().get(1).setPercentHeight(200 / total.getHeight() * 100);
                    visiblePhoto.setVisible(true);
                    photoPlace.setHeight(200);
                    photoPlace.setWidth(messageGrid.getWidth());
                    photoPlace.setFill(new ImagePattern(new Image(message.getPhotoAddress())));
                    messageGrid.getRowConstraints().get(2).setPercentHeight(100 - (53 / total.getHeight() + 30 / total.getHeight() + 200 / total.getHeight()) * 100);
                }
            }
        });
    }
}
