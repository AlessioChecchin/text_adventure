package com.adventure.models;

import com.adventure.Main;
import com.adventure.Resources;
import com.adventure.controllers.BaseController;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.Objects;
import java.util.Properties;

public class Game
{

    //
    //  CONSTRUCTOR
    //

    /**
     * Constructor.
     * @param properties Application properties.
     */
    public Game(Properties properties)
    {
        this(properties, null);
    }

    /**
     * Constructor
     * @param properties Properties to use for this game
     * @param stage Game stage.
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
    public Graph<StoryNode, StoryNodeLink> getGameGraph()
    {
        return this.gameGraph;
    }

    /**
     * Current node getter
     * @return StoryNode current node in the game
     */
    public StoryNode getCurrentNode()
    {
        return this.currentNode;
    }

    /**
     * Stage getter
     * @return Stage current stage in the game
     */
    @JsonIgnore
    public Stage getStage()
    {
        return this.stage;
    }

    /**
     * Previous node getter
     * @return StoryNode previous node
     */
    public StoryNode getPreviousNode()
    {
        return this.previousNode;
    }

    /**
     * Game id getter.
     * @return Game id.
     */
    public String getId()
    {
        return this.id;
    }

    //
    //  SETTERS
    //

    /**
     * Current node setter
     * @param currentNode the current node to set
     */
    public void setCurrentNode(StoryNode currentNode)
    {
        Objects.requireNonNull(currentNode);
        this.previousNode = this.currentNode;
        this.currentNode = currentNode;
    }

    /**
     * Game di setter.
     * @param id Unique game id.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Stage setter
     * @param stage Stage to be set
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    //
    //  OTHERS
    //

    public boolean hasPreviousNode()
    {
        return this.previousNode != null;
    }

    public void invalidatePreviousNode()
    {
        this.previousNode = null;
    }

    /**
     * Loads current game.
     */
    public void load()
    {
        Objects.requireNonNull(this.stage, "stage cannot be null");
        try {
            // Loads view.
            FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource(currentNode.getTargetView()));
            // Sets game font.
            Font.loadFont(Objects.requireNonNull(Resources.class.getResource("assets/retro_gaming.ttf")).toExternalForm(), -1);

            Parent root = fxmlLoader.load();

            // Creates scene.
            Scene currentScene = this.stage.getScene();

            if(this.currentController != null)
            {
                this.currentController.shutdown();
            }

            // If a scene already exists its reused.
            if(currentScene != null)
            {
                currentScene.setRoot(root);
            }
            // If there's no scene, a new one is created.
            else
            {
                Scene scene = new Scene(
                        root,
                        Integer.parseInt(this.properties.getProperty("display.width")),
                        Integer.parseInt(this.properties.getProperty("display.height"))
                );
                this.stage.setScene(scene);
            }

            BaseController controller = fxmlLoader.getController();
            this.currentController = controller;

            stage.setOnHidden(e -> controller.shutdown());
        }
        catch(Exception e)
        {
            logger.fatal("Error loading game", e);
        }
    }

    /**
     * Method that should be called when unloading a game.
     */
    public void shutdown()
    {
        if(this.currentController != null)
        {
            this.currentController.shutdown();
        }
    }

    //
    //  VARIABLES
    //

    /**
     * A decision graph that is incrementally loaded as the application flow proceeds.
     */
    private final Graph<StoryNode, StoryNodeLink> gameGraph;

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
    private final Properties properties;

    /**
     * Current node controller.
     */
    @JsonIgnore
    private BaseController currentController;

    /**
     * Fxml stage.
     */
    private Stage stage;

    /**
     * Game identifier.
     */
    private String id;

    /**
     * Logger.
     */
    @JsonIgnore
    protected static final Logger logger = LogManager.getLogger();

}
