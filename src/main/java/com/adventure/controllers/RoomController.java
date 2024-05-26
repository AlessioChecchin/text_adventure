package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.utils.ApplicationContext;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.commands.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.net.URISyntaxException;

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
