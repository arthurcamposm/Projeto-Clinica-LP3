module org.example.clinicafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.clinicafx to javafx.fxml;
    exports org.example.clinicafx;
    exports org.example.clinicafx.controller;
    opens org.example.clinicafx.controller to javafx.fxml;
}