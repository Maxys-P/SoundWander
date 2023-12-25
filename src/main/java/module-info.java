module com.sw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires jlayer;
    requires jbcrypt;

    exports com.sw;

    opens com.sw.controllers to javafx.fxml;
    opens com.sw.controllers.users to javafx.fxml;
    opens com.sw.controllers.musicPlay to javafx.fxml;
}