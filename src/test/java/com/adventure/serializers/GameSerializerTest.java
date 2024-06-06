package com.adventure.serializers;

import com.adventure.Resources;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.Key;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameSerializerTest {

    @Test
    public void testGraphSerialization() throws IOException {

        Game game = new Game(new Properties());
        game.setId("test");

        Inventory playerInventory = new Inventory(100);

        playerInventory.addItem(new AttackItem("Sword"));

        Stats stats = new Stats();
        stats.setMaxHp(100);
        stats.setHp(100);
        stats.setBaseAttack(1);
        stats.setBaseDefense(1);

        game.setPlayer(new Player("test", playerInventory, stats));

        String firstFightRoomKey = "Level 1";

        Room startingRoom = new Room("Introduction room", "Welcome to the first room, take the key and go on an adventure!");
        startingRoom.setBackgroundPath("assets/castle.png");
        startingRoom.getItems().add(new Key(firstFightRoomKey));

        game.setCurrentNode(startingRoom);

        Room firstFightRoom = new Room("First fight room", "Oh no, a goblin! Fight him and take the loot that drops");
        firstFightRoom.setBackgroundPath("assets/castle.png");

        game.setCurrentNode(firstFightRoom);

        // Populating the game graph.
        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();
        g.addVertex(startingRoom);
        g.addVertex(firstFightRoom);

        StoryNodeLink toFirstFightRoom = new StoryNodeLink();
        toFirstFightRoom.setLocked(true);
        toFirstFightRoom.setKey(firstFightRoomKey);

        g.addEdge(startingRoom, firstFightRoom, toFirstFightRoom);
        ObjectMapper mapper = new ObjectMapper();

        // Make all member fields serializable without further annotations, instead of just public fields (default setting)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Set custom serializers
        SimpleModule module = new SimpleModule();
        module.addSerializer(Graph.class, new GraphSerializer());
        mapper.registerModule(module);

            //  Create the json string
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(game);
            System.out.println(json);

            //  Get json file path
            String saveName = game.getId() + ".json";

            //  Create json file and write the json string into it
            File file = new File(Resources.getAssetsPath() + "saves" + saveName);
            if (file.createNewFile())
                System.out.println("Saved " + saveName);
            else
                System.out.println(saveName + " overwritten");
            FileWriter write = new FileWriter(file);
            write.write(json);
            write.close();

            File jsonOut = new File(Resources.getAssetsPath() + "saves" + saveName);
            //  Creates game from json
            ObjectMapper mapper2 = new ObjectMapper();
            SimpleModule module1 = new SimpleModule();
            module1.addSerializer(Graph.class, new GraphSerializer());
            mapper2.registerModule(module1);
            Game gameTest = mapper2.readValue(jsonOut, Game.class);
            String jOut = mapper2.writerWithDefaultPrettyPrinter().writeValueAsString(gameTest);
            System.out.println(jOut);
            assertEquals(gameTest, game);

    }

}