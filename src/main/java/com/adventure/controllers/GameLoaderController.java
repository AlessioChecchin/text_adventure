package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.utils.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;

public class GameLoaderController implements BaseController
{
    @FXML
    private Display display;

    @FXML
    public void initialize() throws URISyntaxException, IOException
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
    }

    @Override
    public void shutdown() {
    }
}
