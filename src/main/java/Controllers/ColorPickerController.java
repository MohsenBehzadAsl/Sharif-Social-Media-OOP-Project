package Controllers;

import View.Controller;
import javafx.css.CssParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorPickerController {
    private String base="#373e43";
    private String accent="#1e74c6";
    private String label="#d3d3d3";
    private String TextField="#808080";
    private String button="#ffffff";
    @FXML
    private ColorPicker myLabelColorPicker;
    @FXML
    private ColorPicker myAccentColorPicker;
    @FXML
    private ColorPicker myBaseColorPicker;
    @FXML
    private ColorPicker myButtonColorPicker;
    @FXML
    private Button myChangeButton;
    @FXML
    private ColorPicker myTextFieldColorPicker;
    public Stage stage;
    @FXML
    void change(ActionEvent event) {
      //  myChangeButton.setText("CHANGED SUCCESSFULLY");
        stage.close();
    }
    @FXML
    void changeAccent(ActionEvent event) {
        Color color=myAccentColorPicker.getValue();
         accent=String.valueOf(color).replaceAll("0x","#");
         accent=accent.replaceAll("ff$","");
         update();
    }

    @FXML
    void changeBase(ActionEvent event) {
        Color color=myBaseColorPicker.getValue();
        base=String.valueOf(color).replaceAll("0x","#");
        base=base.replaceAll("ff$","");
        update();
    }
    @FXML
    void changeButton(ActionEvent event) {
        Color color=myButtonColorPicker.getValue();
         button=String.valueOf(color).replaceAll("0x","#");
        button=button.replaceAll("ff$","");
        update();
    }

    @FXML
    void changeLabel(ActionEvent event) {
        Color color=myLabelColorPicker.getValue();
        label=String.valueOf(color).replaceAll("0x","#");
        label=label.replaceAll("ff$","");
        update();
    }

    @FXML
    void changeTextField(ActionEvent event) {
        Color color=myTextFieldColorPicker.getValue();
        TextField=String.valueOf(color).replaceAll("0x","#");
        TextField=TextField.replaceAll("ff$","");
        update();
    }
    public void update(){
        String cssBase=Controller.class.getResource("/CSS/CSSBase.css").toExternalForm();
        String CSSButtonHober=Controller.class.getResource("/CSS/CSSButtonHober.css").toExternalForm();
      //  String css=Controller.class.getResource("/CSS/CUSTOM.css").toExternalForm();
        Controller.scene.getStylesheets().clear();
        Controller.scene.getStylesheets().removeAll();
        Controller.scene.getStylesheets().add(CSSButtonHober);
        Controller.scene.getStylesheets().add(cssBase);
        System.out.println("accent="+accent);
        System.out.println("label="+label);
        System.out.println("text field="+TextField);
        System.out.println("button="+button);
        System.out.println("base="+base);
        CssParser cssParser=new CssParser();
        Controller.scene.getRoot().setStyle("-root-filll:" + accent + " ;");
        Controller.scene.getRoot().setStyle("-label-text-mmm:" + label + " ;");
        Controller.scene.getRoot().setStyle("-text-field-fill:" + TextField + " ;");
        Controller.scene.getRoot().setStyle("-root-fill2:" + button + " ;");
        Controller.scene.getRoot().setStyle("-root-fill:" + base + " ;");
        Controller.scene.getStylesheets().clear();
        Controller.scene.getStylesheets().removeAll();
      //  Controller.scene.getStylesheets().add(css);
    }

  /*  String css=Controller.class.getResource("/CSS/CUSTOM.css").toExternalForm();
        Controller.scene.getStylesheets().clear();
        Controller.scene.getStylesheets().removeAll();
        Controller.scene.getStylesheets().add(css);
        Controller.scene.getRoot().setStyle("-root-filll:" + temp + " ;");
        Controller.scene.getStylesheets().clear();
        Controller.scene.getStylesheets().removeAll();
        Controller.scene.getStylesheets().add(css);*/
}
