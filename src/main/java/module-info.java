module com.sw {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires jlayer;
    requires jbcrypt;
    requires javafx.media;
    requires nv.i18n;
    requires javafx.web;
    requires jdk.jsobject;
    requires java.dotenv;

    exports com.sw;

    opens com.sw.controllers to javafx.fxml;
    opens com.sw.controllers.users to javafx.fxml;
    opens com.sw.controllers.musicPlay to javafx.fxml;
    opens com.sw.controllers.proposal to javafx.fxml;
    opens com.sw.controllers.profil to javafx.fxml;
    opens com.sw.controllers.artists to javafx.fxml;
    opens com.sw.controllers.admins to javafx.fxml;
    opens com.sw.controllers.payments to javafx.fxml;
    opens com.sw.controllers.publicPlaylist to javafx.fxml;
    opens com.sw.controllers.home to javafx.fxml, javafx.web;
    opens com.sw.controllers.search to javafx.fxml;
}