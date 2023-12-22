module sio.leo.conservatoire {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens sio.leo.conservatoire to javafx.fxml;
    exports sio.leo.conservatoire;
    //exports sio.leo.conservatoire.controleur;
}
