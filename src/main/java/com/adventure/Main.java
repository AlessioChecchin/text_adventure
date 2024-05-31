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

import java.util.Properties;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContext context = ApplicationContextProvider.getInstance();

        commandParser.setContext(context);

        commandParser.registerCommand("help", CmdHelp.class, "Displays available commands with brief descriptions");
        commandParser.registerCommand("clear", CmdClear.class, "Clears the screen.");
        commandParser.registerCommand("listGames", CmdListGames.class, "Lists all available games.");
        commandParser.registerCommand("save", CmdSaveGame.class, "Saves the current game.");
        commandParser.registerCommand("newGame", CmdNewGame.class, "Creates a new game.");
        commandParser.registerCommand("fight", CmdFight.class, "Attack the monster");

        Properties props = context.getProperties();

        // Generating dummy game to host the proper game loader.
        Game dummyGame = new Game(props, stage);
        context.setGame(dummyGame);

        dummyGame.setCurrentNode(new GameLoaderNode());
        dummyGame.setCurrentNode(new Room("Test", "Test"));
        dummyGame.load();

        stage.setResizable(Boolean.parseBoolean(props.getProperty("resizable", "false")));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}