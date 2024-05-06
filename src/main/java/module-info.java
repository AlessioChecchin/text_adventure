module com.adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;


    opens com.adventure to javafx.fxml;
    exports com.adventure;
    exports com.adventure.nodes;
    exports com.adventure.interfaces;
    opens com.adventure.interfaces to javafx.fxml;
    exports com.adventure.controllers;
    opens com.adventure.controllers to javafx.fxml;
}