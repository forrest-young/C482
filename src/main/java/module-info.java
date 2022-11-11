module com.qkmtwo.c482 {
    requires javafx.controls;
    requires javafx.fxml;

    opens model to javafx.base;
    opens com.qkmtwo.c482 to javafx.fxml;
    exports com.qkmtwo.c482;
    exports control;
    opens control to javafx.fxml;
}