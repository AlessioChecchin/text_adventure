package com.adventure.services;

import com.adventure.Resources;
import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.adventure.serializers.GraphSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class FileSystemStorageService implements StorageService
{
    public FileSystemStorageService(Properties properties)
    {
        this.properties = properties;
    }

    @Override
    public List<String> listGames()
    {
        ArrayList<String> result = new ArrayList<>();

        File directory = new File(Resources.getAssetsPath() + "saves");
        for (File file : directory.listFiles())
            result.add(file.getName().replace(".json", ""));

        return result;
    }

    @Override
    public void saveGame(Game game) throws GameStorageException
    {
        ObjectMapper mapper = new ObjectMapper();

        // Make all member fields serializable without further annotations, instead of just public fields (default setting)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Set custom serializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(Graph.class, new GraphSerializer());
        mapper.registerModule(module);

        try
        {
            //  Create the json string
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
            System.out.println(json);

            //  Get json file path
            String saveName = game.getId() + ".json";
            String savePath = Resources.getAssetsPath() + "saves";

            //  Create 'saves' directory
            new File(savePath).mkdirs();

            //  Create json file and write the json string into it
            File file = new File(savePath + "/" + saveName);
            if(file.createNewFile())
                System.out.println("Saved " + saveName);
            else
                System.out.println(saveName + " overwritten");
            FileWriter write = new FileWriter(file);
            write.write(json);
            write.close();
        }
        catch (JsonProcessingException e)
        {
            throw new GameStorageException(e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Game loadGame(String gameId) throws GameStorageException
    {
        return null;
    }

    @Override
    public void deleteGame(String gameId)
    {
        File game = new File(Resources.getAssetsPath() + "saves/" + gameId + ".json");
        if(game.delete())
            System.out.println("Deleted " + game.getName().replace(".json", ""));
        else
            throw new NoSuchElementException("No such game");
    }

    @Override
    public Game newGame(String playerName)
    {
        Game game = new Game(this.properties);
        Stats stats = new Stats();
        stats.setMaxHp(100);
        stats.setBaseDefense(99);
        stats.setBaseAttack(88);
        stats.setHp(100);

        Inventory inventory = new Inventory(100);
        UsableItem item1 = new UsableItem("Potion");
        AttackItem item2 = new AttackItem("Spada");
        DefenceItem item3 = new DefenceItem("Scudo");
        //inventory.addItem(item1);
        //inventory.addItem(item2);
        //inventory.addItem(item3);
        //inventory.equipItem(item3);

        Player player = new Player(playerName, inventory, stats);

        Room room = new Room("First room", "First room description");
        room.setBackgroundPath("assets/castle.png");
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

        game.setPlayer(player);

        game.setCurrentNode(room);

        return game;
    }

    /**
     * Application properties.
     */
    private final Properties properties;

}
