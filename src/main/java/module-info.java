module com.sw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    exports com.sw;

    opens com.sw.controllers.users to javafx.fxml;
}