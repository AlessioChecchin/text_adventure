package com.adventure.models;

import com.adventure.Main;
import com.adventure.controllers.BaseController;
import com.adventure.nodes.StoryNode;
import com.adventure.nodes.StoryNodeLink;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.Objects;
import java.util.Properties;


public class Game
{

    //
    //  CONSTRUCTOR
    //

    /**
     * Default constructor
     * @param properties Properties to use for this game
     */
    public Game(Properties properties, Stage stage)
    {
        this.properties = properties;
        this.gameGraph = new DirectedPseudograph<>(StoryNodeLink.class);
        this.stage = stage;
    }



    //
    //  GETTERS
    //

    /**
     * Graph getter
     * @return Graph used in the game
     */
    public Graph<StoryNode, StoryNodeLink> getGameGraph() { return this.gameGraph; }

    /**
     * Current node getter
     * @return StoryNode current node in the game
     */
    public StoryNode getCurrentNode() { return this.currentNode; }

    /**
     * Stage getter
     * @return Stage current stage in the game
     */
    @JsonIgnore
    public Stage getStage() { return this.stage; }

    /**
     * Previous node getter
     * @return StoryNode previous node
     */
    public StoryNode getPreviousNode() { return this.previousNode; }



    //
    //  SETTERS
    //

    /**
     * Current node setter
     * @param currentNode the current node to set
     */
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
            Scene currentScene = null;
            if(this.stage != null)
                currentScene = this.stage.getScene();



            // If a scene already exists its reused.
            if(currentScene != null)
            {
                currentScene.setRoot(fxmlLoader.load());
            }
            // If there's no scene, a new one is created.
            else
            {
                Scene scene = new Scene(
                        fxmlLoader.load(),
                        Integer.parseInt(this.properties.getProperty("display.width")),
                        Integer.parseInt(this.properties.getProperty("display.height"))
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

    /**
     * Stage setter
     * @param stage Stage to be set
     */
    public void setStage(Stage stage) { this.stage = stage; }



    //
    //  OTHERS
    //

    /**
     * Check it there's a valid previous node
     * @return True if previousNode != null, False otherwise
     */
    public boolean hasPreviousNode() { return this.previousNode != null; }

    /**
     * Set previousNode to "null"
     */
    public void invalidatePreviousNode() { this.previousNode = null; }



    //
    //  VARIABLES
    //

    /**
     * A decision graph that is incrementally loaded as the application flow proceeds.
     */
    private Graph<StoryNode, StoryNodeLink> gameGraph;

    /**
     * Currently active node.
     */
    private StoryNode currentNode;

    /**
     * Previous node, null if there is no previous node.
     */
    private StoryNode previousNode;

    /**
     * Properties of the game
     */
    @JsonIgnore
    private Properties properties;

    /**
     * Fxml stage.
     */
    private Stage stage;


}
