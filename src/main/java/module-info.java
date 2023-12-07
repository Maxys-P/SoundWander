module com.sw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;

    exports com.sw;


    opens com.sw.controllers to javafx.fxml;
}