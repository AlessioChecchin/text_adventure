package com.adventure.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppController implements BaseController
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void shutdown()
    {

    }
}