package com.adventure.models;

import com.adventure.Main;
import com.adventure.controllers.BaseController;
import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.Objects;
import java.util.Properties;

public class Game
{
    /**
     * This represents a decision graph that is incrementally loaded as the application flow proceeds.
     */
    private Graph<StoryNode, StoryNodeLink> gameGraph;

    /**
     * Currently active node.
     */
    private StoryNode currentNode;

    /**
     * Previous node, is null if there is no previous node.
     */
    private StoryNode previousNode;

    /**
     * Fxml stage.
     */
    private Stage stage;

    private Properties properties;


    public Game(Properties properties)
    {
        this.properties = properties;
        this.gameGraph = new DefaultDirectedGraph<>(StoryNodeLink.class);
    }

    public Graph<StoryNode, StoryNodeLink> getGameGraph()
    {
        return this.gameGraph;
    }

    public StoryNode getCurrentNode()
    {
        return this.currentNode;
    }

    public void setCurrentNode(StoryNode currentNode)
    {
        this.previousNode = this.currentNode;
        this.currentNode = currentNode;

        try {
            // Loads view.
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(currentNode.getTargetView()));

            // Sets game font.
            Font.loadFont(Objects.requireNonNull(Main.class.getResource("assets/ubuntu.ttf")).toExternalForm(), -1);

            // Creates scene.
            Scene currentScene = this.stage.getScene();

            // If a scene already exists its reused.
            if(currentScene != null)
            {
                currentScene.setRoot(fxmlLoader.load());
            }
            // If there's no scene, a new one is created.
            else
            {
                Scene scene = new Scene(
                        fxmlLoader.load()
                );
                this.stage.setScene(scene);
            }

            BaseController controller = fxmlLoader.getController();
            stage.setOnHidden(e -> controller.shutdown());

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public Stage getStage()
    {
        return this.stage;
    }

    public boolean hasPreviousNode()
    {
        return this.previousNode != null;
    }

    public void invalidatePreviousNode()
    {
        this.previousNode = null;
    }

    public StoryNode getPreviousNode()
    {
        return this.previousNode;
    }
}
