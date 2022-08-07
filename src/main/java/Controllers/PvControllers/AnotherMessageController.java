package Controllers.PvControllers;

import Controllers.ForwardPopUpControllers;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class AnotherMessageController {
    public PvPageController pvPageController;
    public Message message;
    public Parent nowParent;
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

    @FXML
    void forwardOrReplyClicked(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (message.getReply()){
            pvPageController.goToMessageForReply(message.getReplyMessage());
        }else if (message.getForward()){
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ShowAnotherUserPage.fxml"));
            Parent parent=fxmlLoader.load();
            ShowAnotherUserPageController showAnotherUserPageController=fxmlLoader.getController();
            Controller.main.getChildren().clear();
            Controller.main.getRowConstraints().removeAll();
            Controller.main.getColumnConstraints().removeAll();
            Controller.main.add(parent,0,0);
            showAnotherUserPageController.nowParent=parent;
            showAnotherUserPageController.backParent=nowParent;
            showAnotherUserPageController.set(message.getSender());
            showAnotherUserPageController.start(message.getSender());
        }
    }

    public void set(Message message) {
        this.message=message;
        date.setText(message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        if (!message.getEdited()){
            edited.setVisible(false);
        }
        if (message.getForward()){
            System.out.println(1);
        }else if (message.getReply()){
            forwardOrReplyLabel.setText("Reply to "+message.getReplyMessage().getSender().getUserName()+" :"+message.getReplyMessage().getContent());
        }else {
            vboxForwardOrReply.setVisible(false);
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(0);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(100);

        }
        ContentTextArea.setText(message.getContent());



        MenuItem reply = new MenuItem("Reply");
        reply.setOnAction(e -> {
            pvPageController.reply=true;
            pvPageController.replyMessage=message;
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
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(reply);
        menu.getItems().add(forward);
        messageGrid.setOnContextMenuRequested(e -> {
            menu.show(messageGrid.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });

    }
    private void Forward() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(Controller.class.getResource("/fxml/ForwardPopUp.fxml"));
        Parent parent=fxmlLoader.load();
        ForwardPopUpControllers forwardPopUpControllers=fxmlLoader.getController();
        forwardPopUpControllers.isPost=false;
        forwardPopUpControllers.message=message;
        forwardPopUpControllers.isInPv=true;
        forwardPopUpControllers.pvPageController=pvPageController;
        Scene scene = new Scene(parent, 520, 550);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setX(Controller.stage.getX()+Controller.stage.getWidth()/2-520/2);
        stage.setY(Controller.stage.getY()+Controller.stage.getHeight()/2-550/2);
        stage.setResizable(false);
        forwardPopUpControllers.popUp=stage;
        forwardPopUpControllers.set();
        stage.show();
    }

    public void handleResizing(){
        System.out.println("here");
        ContentTextArea.applyCss();
        Node text=ContentTextArea.lookup(".text");
        ContentTextArea.prefHeightProperty().bind(Bindings.createDoubleBinding(new Callable<Double>(){
            @Override
            public Double call() throws Exception {
                return text.getBoundsInLocal().getHeight();
            }
        }, text.boundsInLocalProperty()).add(20));
        ContentTextArea.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(total.getHeight()<=230) {
                total.setPrefHeight(ContentTextArea.getPrefHeight() *100/64);
            }else {
                total.setPrefHeight(ContentTextArea.getPrefHeight()+30+53);
                messageGrid.getRowConstraints().get(0).setPercentHeight(30/total.getHeight()*100);
                messageGrid.getRowConstraints().get(2).setPercentHeight(53/total.getHeight()*100);
                messageGrid.getRowConstraints().get(1).setPercentHeight(100-(53/total.getHeight()+30/total.getHeight())*100);

            }


//            rightGridPain.getRowConstraints().get(3).setPercentHeight(messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
//            rightGridPain.getRowConstraints().get(2).setPercentHeight(84-messageTextArea.getPrefHeight()/rightGridPain.getHeight()*100);
        });

    }
}
