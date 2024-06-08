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
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * Controller used to handle game loading for the first time during application lifetime.
 */
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
        parser.enable("delete");

        Label instructions = new Label();
        instructions.setText("Type help to view possible actions");

    }

    private void loadScreen()
    {
        URL videoUrl = Resources.class.getResource("assets/presentation.mp4");

        if(videoUrl != null)
        {
            try
            {
                Media media = new Media(videoUrl.toString());
                this.player = new MediaPlayer(media);

                this.player.setOnReady(() -> {
                    MediaView mediaView = new MediaView(player);

                    mediaView.fitWidthProperty().bind(this.display.getGraphics().widthProperty());
                    mediaView.fitHeightProperty().bind(this.display.getGraphics().heightProperty());

                    this.display.getGraphics().setAlignment(Pos.CENTER);
                    this.display.getGraphics().getChildren().add(mediaView);

                    this.player.setMute(true);
                    this.player.setAutoPlay(true);

                    player.setOnEndOfMedia(() -> {
                        player.seek(Duration.ZERO);
                        player.play();
                    });

                    this.player.play();
                });

                // We noticed that on some windows versions loading the video results in an unknown error.
                // In these cases the system fallbacks into a textual visualization.
                this.player.setOnError(() -> {
                    logger.error("Error loading presentation video");
                    logger.debug("Falling back to textual view...");

                    this.loadFallback();
                });
            }
            catch (Exception e)
            {
                logger.error("Error instantiating MediaPlayer", e);
                this.loadFallback();
            }
        }
        else
        {
            logger.warn("Presentation video not found, falling back to textual view...");
        }
    }

    private void loadFallback()
    {
        this.display.getGraphics().setAlignment(Pos.CENTER);

        Label welcome = new Label();
        welcome.setText("Welcome to Text Adventure");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 30px;");

        Label instructions = new Label();
        instructions.setText("Type 'help' to view possible actions!");
        instructions.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 30px;");


        this.display.getGraphics().getChildren().add(welcome);
        this.display.getGraphics().getChildren().add(instructions);
    }

    @Override
    public void shutdown()
    {
        logger.debug("Shutting down GameLoader...");

        if(this.player != null)
        {
            this.player.stop();
        }

        this.display.shutdown();
    }
}
