package com.adventure.services;

import com.adventure.Resources;
import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.*;
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
        //  Create or ensure the existence of the 'saves' folder
        ensureSaveFolder();
        this.savePath = Resources.getAssetsPath() + "saves/";
        this.properties = properties;
    }

    @Override
    public List<String> listGames()
    {
        ArrayList<String> result = new ArrayList<>();
        File directory = new File(this.savePath);
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

            //  Create json file and write the json string into it
            File file = new File(this.savePath + saveName);
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
        File json = new File(this.savePath + gameId + ".json");
        if(! json.exists())
            throw new GameStorageException("Game with id " + gameId + " does not exist");

        //  Creates game from json
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Game.class);
        } catch (IOException e) {
            throw new GameStorageException(e.getMessage());
        }
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
        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();

        Inventory playerInventory = new Inventory(100);

        // Adding sword.
        playerInventory.addItem(new AttackItem("Sword"));

        Stats stats = new Stats();
        stats.setMaxHp(100);
        stats.setHp(100);
        stats.setBaseAttack(1);
        stats.setBaseDefense(1);

        game.setPlayer(new Player(playerName, playerInventory, stats));

        game.getPlayer().getInventory().addItem(new UsableItem("Pozione"));

        String key1 = "Level 1";
        String key2 = "Level 2";
        String key3 = "Level 3";
        String key4 = "Level 4";
        String key5 = "Level 5";
        String key6 = "Level 6";

        // Room 1.
        Room room1 = new Room("Introduction room", "Welcome to the first room, take the key and go on an adventure!");
        room1.setBackgroundPath("assets/castle.png");
        room1.getItems().add(new Key(key1));
        g.addVertex(room1);

        // Set to current node.
        game.setCurrentNode(room1);

        // Room 2.
        Room room2 = new Room("K2 fight room", "Oh no, a goblin! Fight him and take the loot that drops");
        room2.setBackgroundPath("assets/castle.png");
        room2.getItems().add(new Key(key2));
        g.addVertex(room2);

        // Room 3.
        Room room3 = new Room("K3 fight room", "Oh no, a witch! Fight the witch and take the loot that drops");
        room3.setBackgroundPath("assets/castle.png");
        room3.getItems().add(new Key(key3));
        g.addVertex(room3);

        // Room 4.
        Room room4 = new Room("K4 fight room", "Oh no, a troll! Fight the troll and take the loot that drops");
        room4.setBackgroundPath("assets/castle.png");
        room4.getItems().add(new Key(key4));
        g.addVertex(room4);

        // Room 5.
        Room room5 = new Room("Loot room", "No enemies here :)");
        room5.setBackgroundPath("assets/castle.png");
        //room5.getItems().add(new Key(key5));
        g.addVertex(room5);

        // Room 6.
        Room room6 = new Room("K6 fight room", "Oh no, a dragon! Fight the dragon and take the loot that drops");
        room6.setBackgroundPath("assets/castle.png");
        room6.getItems().add(new Key(key6));
        g.addVertex(room6);

        // Room 7.
        Room room7 = new Room("Loot room", "No enemies here :)");
        room7.setBackgroundPath("assets/castle.png");
        g.addVertex(room7);

        // Room 8.
        Room room8 = new Room("K5 fight room", "Oh no, a gym bro! Fight the gym bro and take the loot that drops");
        room8.setBackgroundPath("assets/castle.png");
        room8.getItems().add(new Key(key5));
        g.addVertex(room8);

        // Room 9.
        Room room9 = new Room("Loot room before the final boss", "Loot room before the final boss");
        room9.setBackgroundPath("assets/castle.png");
        g.addVertex(room9);

        // Room 10.
        Room room10 = new Room("Kill the boss and win!", "Kill the boss and win");
        room10.setBackgroundPath("assets/castle.png");
        g.addVertex(room10);

        // Edges
        StoryNodeLink edge12 = new StoryNodeLink();
        edge12.setAction(new Action("north"));
        edge12.setKey(key1);
        g.addEdge(room1, room2, edge12);

        StoryNodeLink edge23 = new StoryNodeLink();
        edge23.setAction(new Action("west"));
        edge23.setKey(key2);
        g.addEdge(room2, room3, edge23);

        StoryNodeLink edge32 = new StoryNodeLink();
        edge32.setAction(new Action("east"));
        g.addEdge(room3, room2, edge32);

        StoryNodeLink edge24 = new StoryNodeLink();
        edge24.setAction(new Action("east"));
        edge24.setKey(key3);
        g.addEdge(room2, room4, edge24);

        StoryNodeLink edge42 = new StoryNodeLink();
        edge42.setAction(new Action("west"));
        g.addEdge(room4, room2, edge42);

        StoryNodeLink edge45 = new StoryNodeLink();
        edge45.setAction(new Action("north"));
        edge45.setKey(key4);
        g.addEdge(room4, room5, edge45);

        StoryNodeLink edge54 = new StoryNodeLink();
        edge54.setAction(new Action("south"));
        g.addEdge(room5, room4, edge54);

        StoryNodeLink edge56 = new StoryNodeLink();
        edge56.setAction(new Action("north"));
        edge56.setKey(key5);
        g.addEdge(room5, room6, edge56);

        StoryNodeLink edge65 = new StoryNodeLink();
        edge65.setAction(new Action("south"));
        g.addEdge(room6, room5, edge65);

        StoryNodeLink edge27 = new StoryNodeLink();
        edge27.setAction(new Action("north"));
        g.addEdge(room2, room7, edge27);

        StoryNodeLink edge72 = new StoryNodeLink();
        edge72.setAction(new Action("south"));
        g.addEdge(room7, room2, edge72);

        StoryNodeLink edge78 = new StoryNodeLink();
        edge78.setAction(new Action("north"));
        g.addEdge(room7, room8, edge78);

        StoryNodeLink edge87 = new StoryNodeLink();
        edge87.setAction(new Action("south"));
        g.addEdge(room8, room7, edge87);

        StoryNodeLink edge79 = new StoryNodeLink();
        edge79.setAction(new Action("west"));
        edge79.setKey(key6);
        g.addEdge(room7, room9, edge79);

        StoryNodeLink edge97 = new StoryNodeLink();
        edge97.setAction(new Action("east"));
        g.addEdge(room9, room7, edge97);

        StoryNodeLink edge910 = new StoryNodeLink();
        edge910.setAction(new Action("north"));
        g.addEdge(room9, room10, edge910);

        return game;
    }

    /**
     * Checks whether the 'saves' folder exist.
     * If not it creates it inside
     *      target > classes > com > adventure > assets
     */
    private void ensureSaveFolder()
    {
        String savePath = Resources.getAssetsPath() + "saves";
        if(!new File(savePath).exists())
            new File(savePath).mkdirs();
    }

    /**
     * Save folder path
     */
    private final String savePath;

    /**
     * Application properties.
     */
    private final Properties properties;

}
