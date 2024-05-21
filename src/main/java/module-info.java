module com.adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;
    requires com.fasterxml.jackson.databind;


    opens com.adventure to javafx.fxml;
    exports com.adventure;
    exports com.adventure.nodes;
    exports com.adventure.interfaces;
    opens com.adventure.interfaces to javafx.fxml;
    exports com.adventure.controllers;
    exports com.adventure.models;
    opens com.adventure.controllers to javafx.fxml;
    opens com.adventure.components to javafx.fxml;
    exports com.adventure.commands;
    opens com.adventure.commands to javafx.fxml;
    exports com.adventure.utils;
    opens com.adventure.utils to javafx.fxml;
}