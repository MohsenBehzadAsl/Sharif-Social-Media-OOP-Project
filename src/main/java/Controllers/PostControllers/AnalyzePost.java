package Controllers.PostControllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AnalyzePost {
    @FXML
    private Label where;

    @FXML
    private VBox inbox;

    public Label getWhere() {
        return where;
    }

    public void setWhere(Label where) {
        this.where = where;
    }

    public VBox getInbox() {
        return inbox;
    }

    public void setInbox(VBox inbox) {
        this.inbox = inbox;
    }

    public void plot(MouseEvent mouseEvent) {
        plotII();
    }

    private LinkedHashMap<String,Integer> helpToPlot1=new LinkedHashMap<>();


    public LinkedHashMap<String, Integer> getHelpToPlot1() {
        return helpToPlot1;
    }

    public void setHelpToPlot1(LinkedHashMap<String, Integer> helpToPlot1) {
        this.helpToPlot1 = helpToPlot1;
    }


    private void plotII() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle(where.getText());
        xAxis.setLabel("Date");
        yAxis.setLabel("Count");

        List<String> keyListOfHelpToPlot2 = new ArrayList(helpToPlot1.keySet());
        List<Integer> valueListOfHelpToPlot2 = new ArrayList(helpToPlot1.values());

        //ArrayList<XYChart.Series> allSeries=new ArrayList<>();
        for (int i=0;i<keyListOfHelpToPlot2.size();i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName(where.getText());
            series.getData().add(new XYChart.Data(keyListOfHelpToPlot2.get(i),valueListOfHelpToPlot2.get(i) ));
            bc.getData().add(series);
        }

        Scene scene  = new Scene(bc,400,300);
        //bc.getData().addAll(allSeries);
        Stage stage2=new Stage();
        stage2.setScene(scene);
        stage2.show();

    }
}
