package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.commands.CommandParser;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class GameLoaderController implements BaseController
{
    @FXML
    private Display display;

    private MediaPlayer player;

    private static final Logger logger = LogManager.getFormatterLogger();

    @FXML
    public void initialize()
    {
        logger.debug("Initializing GameLoader...");

        this.loadScreen();

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

    private void loadScreen()
    {
        URL videoUrl = Resources.class.getResource("assets/presentation.mp4");

        if(videoUrl != null)
        {
            Media media = new Media(videoUrl.toString());
            this.player = new MediaPlayer(media);

            this.player.setOnReady(() -> {
                MediaView mediaView = new MediaView(player);

                mediaView.fitWidthProperty().bind(this.display.getGraphics().widthProperty());
                mediaView.fitHeightProperty().bind(this.display.getGraphics().heightProperty());

                this.display.getGraphics().setAlignment(Pos.CENTER);
                this.display.getGraphics().getChildren().add(mediaView);

                this.player.setAutoPlay(true);
                this.player.play();
            });

            this.player.setOnError(() -> {
                logger.error("Error loading presentation video");
                logger.debug("Falling back to textual view...");
            });
        }
        else
        {
            logger.warn("Presentation video not found, falling back to textual view...");
        }
    }


    @Override
    public void shutdown()
    {
        logger.debug("Shutting down GameLoader...");

        this.player.stop();
        this.display.shutdown();
    }
}
