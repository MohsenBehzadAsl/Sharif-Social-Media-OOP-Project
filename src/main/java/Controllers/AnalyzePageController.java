package Controllers;

import Manager.UserRecommender;
import View.Controller;
import component.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class AnalyzePageController {
    private LinkedHashMap<String,Integer> helpToPlot1=new LinkedHashMap<>();

    @FXML
    private GridPane all;

    @FXML
    private Label where;

    public GridPane getAll() {
        return all;
    }

    public void setAll(GridPane all) {
        this.all = all;
    }

    public Label getWhere() {
        return where;
    }

    public void setWhere(Label where) {
        this.where = where;
    }

    public VBox getFill() {
        return fill;
    }

    public void setFill(VBox fill) {
        this.fill = fill;
    }

    @FXML
    private VBox fill;


    public void back(MouseEvent mouseEvent) {
        Controller.main.getChildren().clear();
    }

    public void plot(MouseEvent mouseEvent) {

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

    public void startShowPost() throws IOException {
        LinkedHashMap<User, LocalDateTime> viewsFromPage = Controller.user.getViewsFromPage();
        List<User> keyList = new ArrayList(viewsFromPage.keySet());
        List<LocalDateTime> valueList = new ArrayList(viewsFromPage.values());
        Collections.reverse(keyList);
        Collections.reverse(valueList);
        if (keyList.size() == 0) {

        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            fill.getChildren().clear();
            for (int i = 0; i < keyList.size(); i++) {
                if (helpToPlot1.containsKey(dtf.format(valueList.get(i)))){
                    Integer r=helpToPlot1.get(dtf.format(valueList.get(i)));
                    r++;
                    helpToPlot1.put(dtf.format(valueList.get(i)),r);
                }else{
                    helpToPlot1.put(dtf.format(valueList.get(i)),1);
                }
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/ViewUserInAnalyzePage.fxml"));
                Parent parent=fxmlLoader.load();
                ViewUserInAnalyzePageController viewUserInAnalyzePageController=fxmlLoader.getController();
                viewUserInAnalyzePageController.getName().setText(keyList.get(i).getUserName());
                viewUserInAnalyzePageController.getId().setText("@"+keyList.get(i).getId());
            //  viewUserInAnalyzePageController.getImage().setFill(new ImagePattern(new Image(keyList.get(i).getPhotoNameFromImageFolder())));
                viewUserInAnalyzePageController.getFollowers().setText("Num of followers : " +  keyList.get(i).getFollowers().size());
                viewUserInAnalyzePageController.getFollowings().setText("Num of followings : " +  keyList.get(i).getFollowings().size());
                viewUserInAnalyzePageController.getType().setText(keyList.get(i).getType());
                viewUserInAnalyzePageController.setUser(keyList.get(i));
                viewUserInAnalyzePageController.getViewDate().setText(dtf.format(valueList.get(i)));
                fill.getChildren().add(parent);
            }
        }
    }
}
