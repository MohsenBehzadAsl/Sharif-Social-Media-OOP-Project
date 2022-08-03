package Controllers;

import View.Controller;
import component.Message;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.text.Format;
import java.util.concurrent.Callable;

public class MyMessageBoxController {

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
        date.setText(message.getDate().toString());
        if (!message.getEdited()){
            edited.setVisible(false);
        }
        if (message.getForward()){
            System.out.println(1);
        }else if (message.getReply()){
            System.out.println(2);
        }else {
            vboxForwardOrReply.setVisible(false);
            forwardOrReplyGridPane.getRowConstraints().get(1).setPercentHeight(0);
            forwardOrReplyGridPane.getRowConstraints().get(0).setPercentHeight(100);

        }
        ContentTextArea.setText(message.getContent());
        ContentTextArea.autosize();

    }
    public void handleResizing(){
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
