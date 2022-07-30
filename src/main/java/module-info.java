module oop_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens View to javafx.fxml;
    exports View;
    exports Controllers;
    opens Controllers to javafx.fxml;
}