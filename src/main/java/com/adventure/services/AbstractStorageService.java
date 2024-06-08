package com.adventure.services;

import com.adventure.config.Config;
import com.adventure.models.*;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.Key;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;

import java.util.Objects;

abstract class AbstractStorageService implements StorageService
{
    public AbstractStorageService(Config config)
    {
        Objects.requireNonNull(config, "properties cannot be null");
        this.config = config;
    }

    @Override
    public Game newGame(String playerName)
    {
        Game game = new Game(this.config);
        Graph<StoryNode, StoryNodeLink> g = game.getGameGraph();

        Inventory playerInventory = new Inventory(100);

        // Adding sword.
        AttackItem sword = new AttackItem("Sword");
        sword.setMultiplier(2);
        sword.setAdder(4);
        sword.setWeight(20);

        Stats stats = new Stats(100,100,1,1);

        game.setPlayer(new Player(playerName, playerInventory, stats));

        UsableItem potion = new UsableItem("Potion");
        potion.setAdditionalHp(10);
        game.getPlayer().getInventory().addItem(potion);
        game.getPlayer().getInventory().addItem(sword);


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

        {
            Inventory enemyInventory2 = new Inventory(100);
            Stats enemyStats2 = new Stats(100,100,1,1);
            enemyInventory2.addItem(new Key(key2));
            Enemy enemy2 = new Enemy(enemyInventory2, enemyStats2, "Goblin");
            enemy2.setDefaultDialog("I'm monster that holds K2.\nI'll kill u.");

            room2.setMonster(enemy2);
        }

        g.addVertex(room2);

        // Room 3.
        Room room3 = new Room("K3 fight room", "Oh no, a witch! Fight the witch and take the loot that drops");
        room3.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory3 = new Inventory(100);
            Stats enemyStats3 = new Stats(100,100,1,1);
            enemyInventory3.addItem(new Key(key3));
            Enemy enemy3 = new Enemy(enemyInventory3, enemyStats3, "Monster K3");
            enemy3.setDefaultDialog("I'm monster that holds K3.\nI'll kill u.");

            room3.setMonster(enemy3);
        }

        g.addVertex(room3);

        // Room 4.
        Room room4 = new Room("K4 fight room", "Oh no, a troll! Fight the troll and take the loot that drops");
        room4.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory4 = new Inventory(100);
            Stats enemyStats4 = new Stats(100,100,1,1);
            enemyInventory4.addItem(new Key(key4));
            Enemy enemy4 = new Enemy(enemyInventory4, enemyStats4, "Monster K4");
            enemy4.setDefaultDialog("I'm monster that holds K4.\nI'll kill u.");

            room4.setMonster(enemy4);
        }

        g.addVertex(room4);

        // Room 5.
        Room room5 = new Room("Loot room", "No enemies here :)");
        room5.setBackgroundPath("assets/castle.png");
        //room5.getItems().add(new Key(key5));
        g.addVertex(room5);

        // Room 6.
        Room room6 = new Room("K6 fight room", "Oh no, a dragon! Fight the dragon and take the loot that drops");
        room6.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory6 = new Inventory(100);
            Stats enemyStats6= new Stats(100,100,1,1);
            enemyInventory6.addItem(new Key(key6));
            Enemy enemy6 = new Enemy(enemyInventory6, enemyStats6, "Monster K6");
            enemy6.setDefaultDialog("I'm monster that holds K6.\nI'll kill u.");

            room6.setMonster(enemy6);
        }

        room6.getItems().add(new Key(key6));
        g.addVertex(room6);

        // Room 7.
        Room room7 = new Room("Loot room", "No enemies here :)");
        room7.setBackgroundPath("assets/castle.png");
        g.addVertex(room7);

        // Room 8.
        Room room8 = new Room("K5 fight room", "Oh no, a gym bro! Fight the gym bro and take the loot that drops");
        room8.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory5 = new Inventory(100);
            Stats enemyStats5 = new Stats(100,100,1,1);
            enemyInventory5.addItem(new Key(key5));
            Enemy enemy5 = new Enemy(enemyInventory5, enemyStats5, "Monster K5");

            room8.setMonster(enemy5);

            room8.getItems().add(new Key(key5));
        }

        g.addVertex(room8);

        // Room 9.
        Room room9 = new Room("Loot room before the final boss", "Loot room before the final boss");
        room9.setBackgroundPath("assets/castle.png");
        g.addVertex(room9);

        // Room 10.
        Room room10 = new Room("Kill the boss and win!", "Kill the boss and win");
        room10.setBackgroundPath("assets/castle.png");
        g.addVertex(room10);

        // Victory room

        VictoryNode victoryNode = new VictoryNode();
        g.addVertex(victoryNode);

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

        StoryNodeLink test = new StoryNodeLink();
        test.setAction(new Action("win"));
        g.addEdge(room1, victoryNode, test);

        return game;
    }


    /**
     * Application properties.
     */
    protected final Config config;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();
}
