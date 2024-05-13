package com.adventure;

import com.adventure.interfaces.ApplicationContext;
import com.adventure.models.Game;
import com.adventure.models.Item;
import com.adventure.models.Player;
import com.adventure.nodes.Action;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNodeLink;
import com.adventure.nodes.StoryNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ApplicationContextProvider implements ApplicationContext
{
    /**
     * Context singleton.
     */
    private static ApplicationContextProvider instance;


    /**
     * Current game instance.
     */
    private Game game;

    /**
     * Private singleton constructor.
     */
    private ApplicationContextProvider()
    {}

    /**
     * Singleton getter.
     * @return Singleton instance.
     */
    public static ApplicationContextProvider getInstance()
    {
        if(instance == null) { instance = new ApplicationContextProvider(); }
        return instance;
    }

    @Override
    public void load(String json, Stage stage)
    {
        /*
        TODO: Implement graph serialization.

        try
        {
            // Mapper object used for deserialization.
            ObjectMapper mapper = new ObjectMapper();

            // Deserializes json game.
            this.game = mapper.readValue(json, Game.class);
        }
        catch(Exception e)
        {
        }*/

        // TODO: We expect whit method to populate the graph.
        // For now let's ignore this part and load by hand the graph.

        this.game = new Game();
        this.game.setStage(stage);

        // First room
        Room room = new Room("First room", "First room description");
        room.setTargetView("views/room.fxml");
        Item sword = new Item(0, "Sword description");
        Item healthPotion = new Item(1, "Health Potion description");

        // First room items.
        List<Item> items = new ArrayList<Item>();
        items.add(sword);
        items.add(healthPotion);
        room.setItems(items);

        // Action to reach left room or right room.
        Action actionLeftRoom = new Action("Left room");
        Action actionRightRoom = new Action("Right room");

        StoryNodeLink leftLink = new StoryNodeLink();
        leftLink.setAction(actionLeftRoom);

        StoryNodeLink rightLink = new StoryNodeLink();
        rightLink.setAction(actionRightRoom);


        Room leftRoom = new Room("Left room", "Left room description");
        leftRoom.setTargetView("views/room.fxml");
        Item food = new Item(2, "Food description");
        List<Item> leftItems = new ArrayList<Item>();
        leftItems.add(food);
        leftRoom.setItems(leftItems);

        Room rightRoom = new Room("Right room", "Right room description");
        rightRoom.setTargetView("views/room.fxml");
        Item bow = new Item(3, "Food description");
        List<Item> rightItems = new ArrayList<Item>();
        rightItems.add(bow);
        rightRoom.setItems(rightItems);

        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        g.addVertex(room);
        g.addVertex(leftRoom);
        g.addVertex(rightRoom);
        g.addEdge(room, leftRoom, leftLink);
        g.addEdge(room, rightRoom, rightLink);


        game.setCurrentNode(room);
    }


    @Override
    public void load(Game game, Stage stage)
    {
        this.game = game;
    }

    @Override
    public Game getGame()
    {
        return this.game;
    }
}
