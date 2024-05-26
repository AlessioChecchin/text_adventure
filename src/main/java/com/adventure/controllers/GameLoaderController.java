package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.commands.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class GameLoaderController implements BaseController
{
    @FXML
    private Display display;

    @FXML
    public void initialize()
    {
        BackgroundImage backgroundImage = Resources.getBackground("assets/background.png");

        if (backgroundImage != null)
        {
            this.display.getGraphics().setBackground(new Background(backgroundImage));
        }

        CommandParser parser = CommandParser.getInstance();
        parser.disableAll();
        parser.enable("newGame");
        parser.enable("listGames");
        parser.enable("loadGame");
        parser.enable("help");
        parser.enable("clear");

        Label instructions = new Label();
        instructions.setText("Type help to view possible actions");

    }

    @Override
    public void shutdown() {
    }
}
