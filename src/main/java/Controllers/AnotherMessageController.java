package Controllers;

import component.Message;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

public class AnotherMessageController {
    public PvPageController pvPageController;
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
    void forwardOrReplyClicked(MouseEvent event) {

    }

    public void set(Message message) {
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
        menu.getItems().add(reply);
        menu.getItems().add(delete);
        messageGrid.setOnContextMenuRequested(e -> {
            menu.show(messageGrid.getScene().getWindow(), e.getScreenX(), e.getScreenY());
        });

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
