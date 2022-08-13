module com.vault6936.javascanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires java.net.http;

    opens com.vault6936.javascanner to javafx.fxml;
    exports com.vault6936.javascanner;
}