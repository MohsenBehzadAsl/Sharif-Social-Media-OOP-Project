module sample.oop_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens sample.oop_project to javafx.fxml;
    exports sample.oop_project;
}