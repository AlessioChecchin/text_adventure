package com.adventure.nodes;

import com.adventure.Main;
import com.adventure.controllers.AppController;
import com.adventure.interfaces.ApplicationContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class StartNode extends StoryNode
{
    public void run(ApplicationContext applicationContext) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/menu.fxml"));
        Font.loadFont(Objects.requireNonNull(Main.class.getResource("views/assets/ARCADECLASSIC.TTF")).toExternalForm(), -1);
        Scene scene = new Scene(fxmlLoader.load(), 900, 400);
        applicationContext.getStage().setScene(scene);
    }
}
