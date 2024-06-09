package com.adventure;

import com.adventure.commands.*;
import com.adventure.models.Game;
import com.adventure.models.nodes.GameLoaderNode;
import com.adventure.config.ApplicationContextProvider;
import com.adventure.config.ApplicationContext;
import com.adventure.commands.CommandParser;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Main class used to start the application.
 */
public class Main extends Application
{

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage)
    {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContext context = ApplicationContextProvider.getInstance();

        if(context == null)
        {
            logger.fatal("Unable to load application context!");
            return;
        }

        commandParser.setContext(context);

        commandParser.registerCommand("help", CmdHelp.class, "Displays available commands with brief descriptions");
        commandParser.registerCommand("clear", CmdClear.class, "Clears the screen.");
        commandParser.registerCommand("listGames", CmdListGames.class, "Lists all available games.");
        commandParser.registerCommand("save", CmdSaveGame.class, "Saves the current game.");
        commandParser.registerCommand("newGame", CmdNewGame.class, "Creates a new game.");
        commandParser.registerCommand("fight", CmdFight.class, "Start a fight");
        commandParser.registerCommand("attack", CmdAttack.class, "Attack the monster");
        commandParser.registerCommand("dodge", CmdDodge.class, "Dodge the monster's attack");
        commandParser.registerCommand("use", CmdUse.class, "Use an Item");
        commandParser.registerCommand("loadGame", CmdLoadGame.class, "Loads a game.");
        commandParser.registerCommand("delete", CmdDeleteGame.class, "Remove the specified game.");
        commandParser.registerCommand("show", CmdShow.class, "Shows inventory or stats, usage: show [inventory/stats]");
        commandParser.registerCommand("look", CmdLook.class, "Inspects the current room.");
        commandParser.registerCommand("move", CmdMove.class, "Moves in the map.");
        commandParser.registerCommand("pick", CmdPick.class, "Picks a specific item.");
        commandParser.registerCommand("wai", CmdWai.class, "Gives information about valid paths.");
        commandParser.registerCommand("back", CmdBack.class, "Go back to the previous room.");
        commandParser.registerCommand("run", CmdRun.class,"Run away from a fight");
        commandParser.registerCommand("equip", CmdEquip.class,"Equip an Item" );
        commandParser.registerCommand("drop", CmdDrop.class,"Drops the specified item.");

        // Generating dummy game to host the proper game loader.
        Game dummyGame = new Game(context.getConfig(), stage);
        context.setGame(dummyGame);

        dummyGame.setCurrentNode(new GameLoaderNode());
        dummyGame.load();

        stage.setResizable(context.getConfig().isResizable());
        stage.show();
    }

    /**
     * Main function, launches the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        launch();
    }
}