module oop_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens View to javafx.fxml;
    exports View;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Controllers.PvControllers;
    opens Controllers.PvControllers to javafx.fxml;
    exports Controllers.StartControllers;
    opens Controllers.StartControllers to javafx.fxml;
    exports Controllers.OtherUserPageControllers;
    opens Controllers.OtherUserPageControllers to javafx.fxml;
    exports Controllers.GroupControllers;
    opens Controllers.GroupControllers to javafx.fxml;
    exports Controllers.SettingControllers;
    opens Controllers.SettingControllers to javafx.fxml;
    exports Controllers.AnalysePageControllers;
    opens Controllers.AnalysePageControllers to javafx.fxml;
    exports Controllers.PostControllers;
    opens Controllers.PostControllers to javafx.fxml;
}