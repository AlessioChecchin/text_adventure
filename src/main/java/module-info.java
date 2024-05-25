module com.adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;
    requires com.fasterxml.jackson.databind;

    // Opening to com.fasterxml.jackson.databind necessary for serialization
    opens com.adventure to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.adventure.models to com.fasterxml.jackson.databind;
    opens com.adventure.models.items to com.fasterxml.jackson.databind;
    opens com.adventure.nodes to com.fasterxml.jackson.databind;
    exports com.adventure.serializers to com.fasterxml.jackson.databind;

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
    exports com.adventure.models.items;
}