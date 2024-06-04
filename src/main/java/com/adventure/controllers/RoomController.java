package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.utils.ApplicationContext;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.commands.CommandParser;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;

public class RoomController implements BaseController
{
    @FXML
    private Display display;

    protected static final Logger logger = LogManager.getLogger();

    @FXML
    public void initialize() {

        logger.debug("Initializing RoomController...");

        ApplicationContext context = ApplicationContextProvider.getInstance();

        // Enabling all commands.
        CommandParser commandParser = CommandParser.getInstance();
        commandParser.enableAll();
        // TODO Check enable and disable for the rooms
//        commandParser.enable("newGame");
//        commandParser.enable("listGames");
//        commandParser.enable("loadGame");
//        commandParser.enable("help");
//        commandParser.enable("clear");
//        commandParser.enable("fight");
//        commandParser.enable("use");
//        commandParser.enable("loadGame");
//        commandParser.enable("delete");
//        commandParser.enable("");


        Room room = (Room) context.getGame().getCurrentNode();

        BackgroundImage backgroundImage = Resources.getBackground(room.getBackgroundPath());

        if (backgroundImage != null)
        {
            this.display.getGraphics().setBackground(new Background(backgroundImage));
        }
        else
        {
            logger.warn("{} not found.", room.getBackgroundPath());
        }

        this.display.setStdOut(room.getDescription());
    }


    public void shutdown()
    {
        logger.debug("Shutting down RoomController...");
        display.shutdown();
    }

}
