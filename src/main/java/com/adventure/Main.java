package com.adventure;

import com.adventure.commands.*;
import com.adventure.models.Game;
import com.adventure.models.nodes.GameLoaderNode;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;
import com.adventure.utils.ApplicationContext;
import com.adventure.commands.CommandParser;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.Properties;

public class Main extends Application
{

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage)
    {
        // TODO: integrate in log4j config file

            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            loggerConfig.setLevel(Level.DEBUG);
            ctx.updateLoggers();

        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContext context = ApplicationContextProvider.getInstance();

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

        Properties props = context.getProperties();

        // Generating dummy game to host the proper game loader.
        Game dummyGame = new Game(props, stage);
        context.setGame(dummyGame);

        dummyGame.setCurrentNode(new GameLoaderNode());
        //dummyGame.setCurrentNode(new Room("Test", "Test"));
        dummyGame.load();

        stage.setResizable(Boolean.parseBoolean(props.getProperty("resizable", "false")));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}