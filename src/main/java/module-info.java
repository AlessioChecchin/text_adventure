module com.adventure {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;
    requires com.fasterxml.jackson.databind;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.s3;
    requires software.amazon.awssdk.services.ec2;
    requires javafx.media;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires software.amazon.awssdk.core;

    // Opening to com.fasterxml.jackson.databind necessary for serialization
    opens com.adventure to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.adventure.models to com.fasterxml.jackson.databind;
    opens com.adventure.models.nodes to com.fasterxml.jackson.databind;
    exports com.adventure.serializers to com.fasterxml.jackson.databind;

    exports com.adventure;
    exports com.adventure.models.nodes;
    exports com.adventure.controllers;
    exports com.adventure.models;
    exports com.adventure.exceptions;
    opens com.adventure.controllers to javafx.fxml;
    opens com.adventure.components to javafx.fxml;
    exports com.adventure.commands;
    opens com.adventure.commands to javafx.fxml;
    exports com.adventure.utils;
    opens com.adventure.utils to javafx.fxml;
    exports com.adventure.models.items;
    exports com.adventure.services;
    opens com.adventure.services to javafx.fxml;
    opens com.adventure.models.items to com.fasterxml.jackson.databind, javafx.fxml;
    exports com.adventure.deserializers to com.fasterxml.jackson.databind;
}