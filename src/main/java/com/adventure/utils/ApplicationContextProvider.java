package com.adventure.utils;

import com.adventure.Main;
import com.adventure.interfaces.ApplicationContext;
import com.adventure.models.Game;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.nodes.Action;
import com.adventure.nodes.Room;
import com.adventure.nodes.StoryNodeLink;
import com.adventure.nodes.StoryNode;
import javafx.stage.Stage;
import org.jgrapht.Graph;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ApplicationContextProvider implements ApplicationContext
{
    /**
     * Context singleton.
     */
    private static ApplicationContextProvider instance;

    /**
     * Application properties.
     */
    private final Properties properties;

    /**
     * Current game instance.
     */
    private Game game;

    /**
     * Private singleton constructor.
     */
    private ApplicationContextProvider()
    {
        this.properties = new Properties();
        try (InputStream fis = Main.class.getClassLoader().getResourceAsStream("application.conf"))
        {
            this.properties.load(fis);
        }
        catch (IOException ignored)
        {
            ignored.printStackTrace();
        }
    }

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

        // Window not resizable
        //stage.setResizable(false);

        this.game = new Game(this.properties, stage);
        this.game.setStage(stage);

        // First room
        Room room = new Room("First room", "First room description");
        AttackItem sword = new AttackItem("Sword");
        sword.setAdder(3);
        sword.setMultiplier(1.2);
        sword.setWeight(4);
        UsableItem healthPotion = new UsableItem("Potion");
        healthPotion.setAdditionalHp(10);
        healthPotion.setWeight(3);

        // First room items.
        List<Item> items = new ArrayList<>();
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
        UsableItem food = new UsableItem("Food");
        food.setWeight(3);
        List<Item> leftItems = new ArrayList<>();
        leftItems.add(food);
        leftRoom.setItems(leftItems);

        Room rightRoom = new Room("Right room", "Right room description");
        AttackItem bow = new AttackItem("Bow");
        bow.setAdder(3);
        bow.setMultiplier(1);
        bow.setWeight(2);
        List<Item> rightItems = new ArrayList<>();
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

    @Override
    public Properties getProperties() {
        return this.properties;
    }
}
