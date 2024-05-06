package com.adventure.nodes;

import com.adventure.Main;
import com.adventure.interfaces.ApplicationContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class StartNode extends StoryNode
{
    public StartNode()
    {
        super("Start state");
    }

    public void run(ApplicationContext applicationContext) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        applicationContext.getStage().setScene(scene);
    }
}
