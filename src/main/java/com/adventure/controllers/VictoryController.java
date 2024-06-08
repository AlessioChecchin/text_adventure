package com.adventure.controllers;

import com.adventure.commands.CommandParser;
import com.adventure.components.Display;
import com.adventure.config.ApplicationContext;
import com.adventure.config.ApplicationContextProvider;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VictoryController implements BaseController
{
    @FXML
    private Display display;

    private static final Logger logger = LogManager.getFormatterLogger();

    @FXML
    public void initialize()
    {
        logger.debug("Initializing VictoryController...");

        ApplicationContext context = ApplicationContextProvider.getInstance();
        CommandParser commandParser = CommandParser.getInstance();

        this.display.getGraphics().setAlignment(Pos.CENTER);

        Label congrats = new Label();
        String playerName = context.getGame().getPlayer().getName();

        Label won = new Label();

        congrats.setText("Congratulations " + playerName);
        won.setText("You won!");

        congrats.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");
        won.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");

        Label instructions = new Label();
        instructions.setText("Type 'help' to view possible actions!");
        instructions.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 30px;");

        this.display.getGraphics().getChildren().add(congrats);
        this.display.getGraphics().getChildren().add(won);

        commandParser.disableAll();
        commandParser.enable("newGame");
        commandParser.enable("listGames");
        commandParser.enable("loadGame");
        commandParser.enable("help");
        commandParser.enable("clear");
        commandParser.enable("delete");
        commandParser.enable("save");
    }

    @Override
    public void shutdown()
    {
        logger.debug("Shutting down VictoryController...");
        this.display.shutdown();
    }
}
