module tech.revolicise.crms_dev {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens tech.revolicise.crms_dev to javafx.fxml;
    exports tech.revolicise.crms_dev;
}
