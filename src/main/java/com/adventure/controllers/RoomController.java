package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.utils.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class RoomController implements BaseController
{
    @FXML
    private Display display;

    @FXML
    public void initialize() throws URISyntaxException {

        ApplicationContext context = ApplicationContextProvider.getInstance();

        // Enabling all commands.
        CommandParser commandParser = CommandParser.getInstance();
        commandParser.enableAll();

        Room room = (Room) context.getGame().getCurrentNode();

        BackgroundImage backgroundImage = Resources.getBackground(room.getBackgroundPath());

        if (backgroundImage != null)
        {
            this.display.getGraphics().setBackground(new Background(backgroundImage));
        }
    }


    public void shutdown()
    {
        display.shutdown();
    }

}
