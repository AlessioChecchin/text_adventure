package com.adventure.controllers;

import com.adventure.Resources;
import com.adventure.components.Display;
import com.adventure.commands.CommandParser;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;

public class GameLoaderController implements BaseController
{
    @FXML
    private Display display;

    @FXML
    public void initialize()
    {
        URL videoUrl = Resources.class.getResource("assets/presentation.mp4");

        if(videoUrl != null)
        {
            Media media = new Media(videoUrl.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                MediaView mediaView = new MediaView(mediaPlayer);

                mediaView.fitWidthProperty().bind(this.display.getGraphics().widthProperty());
                mediaView.fitHeightProperty().bind(this.display.getGraphics().heightProperty());
                this.display.getGraphics().setAlignment(Pos.CENTER);

                this.display.getGraphics().getChildren().add(mediaView);

                mediaPlayer.setAutoPlay(true);
                mediaPlayer.play();
            });
        }


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

    @Override
    public void shutdown() {
    }
}
