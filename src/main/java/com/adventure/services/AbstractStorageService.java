package com.adventure.services;

import com.adventure.config.Config;
import com.adventure.models.*;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Key;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;
import com.adventure.models.nodes.StoryNodeLink;
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

        Stats stats = new Stats(100,100,1,1);

        game.setPlayer(new Player(playerName, playerInventory, stats));

        UsableItem potion = new UsableItem("Potion");
        potion.setAdditionalHp(10);


        String key2 = "Level 2";
        String key3 = "Level 3";
        String key4 = "Level 4";
        String key5 = "Level 5";
        String key6 = "Level 6";

        // Room 1.
        Room room1 = new Room("Introduction room", "In a distant era, in the lands of Margorgh, in the darkest depths of a shadowy forest, it is said that there exists a Castle ruled by evil, housing an unimaginable power. For millennia, humanity has sought to wrest this power from the forces of evil, with battles and wars marking the quest for this power. Over time, the traces of the Castle and the power it held within were lost, vanished like leaves on the trees in winter, or so it was believed...\n" +
                "\n" +
                "A mysterious scroll was found by a warrior with a heart of gold, a scroll containing knowledge that even he could not imagine where it would lead him. The name of this hero was " + playerName + ", a name that, unbeknownst to him, would be remembered forever.\n" +
                "\n" +
                "The scroll led him through abandoned and devastated lands where the scent of death and torment still lingered. Our Hero arrived at the edge of a forest with a spectral aura, but driven by curiosity, he did not hesitate to venture into the dark woods...\n" + "Welcome, it is time to embark on this adventure as " + playerName + ". The forest that lies before your eyes is a dense network of dark canopies, creating a natural barrier to the sight of the horizon. Inside, all is silent, and not even the leaves stir in this gloomy and mysterious atmosphere. However, your determined warrior spirit urges you to venture into the forest, something calls to you. Explore the forest following the scroll, good luck Hero!\n" +
                "It seems that there are some items in this part of the forest, try to 'look' for finding something!");
        room1.setBackgroundPath("assets/castle.png");
        room1.getItems().add(sword);
        room1.getItems().add(potion);
        g.addVertex(room1);

        // Set to current node.
        game.setCurrentNode(room1);

        // Room 2.
        Room room2 = new Room("K2 fight room", "Oh no, a goblin appears in front of you! Fight him and take the loot that drops");
        room2.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory2 = new Inventory(100);
            Stats enemyStats2 = new Stats(30,30,5,0);
            enemyInventory2.addItem(new Key(key2));
            Enemy enemy2 = new Enemy(enemyInventory2, enemyStats2, "Goblin");
            enemy2.setDefaultDialog("I'm the KeyHolder of the Dark Castle, if you want to open the doors of the castle you have to kill me. \nFight me stupid human!");

            room2.setMonster(enemy2);
            UsableItem apple = new UsableItem("apple");
            apple.setAdditionalHp(5);
            room2.getItems().add(apple);
        }

        g.addVertex(room2);

        // Room 3.
        Room room3 = new Room("Enter of the castle", "The forest begins to thin out, and in front of you appears a grim scene: a huge castle dominates the horizon. The imposing towers pierce the dark sky like sharp claws, and eerie gargoyle statues along the rooftops seem to be watching you. The eyes of the gargoyles glow a blood-red, the oak wooden gate swings open, a flock of bats flies out from the castle, and the smell of death spreads through the surrounding area. \nA voice in your head calls you, inviting you to enter the castle. You draw your weapon, take a deep breath, and cross the threshold of the castle.\n" +
                "Inside, no natural light enters; there are candles that dimly illuminate the entrance. You hear suspicious noises, you are not alone in this mysterious place....");
        //TODO set che correct background path
        room3.setBackgroundPath("assets/castle.png");

        {
            UsableItem spiderEye = new UsableItem("spiderEye");
            DefenceItem goblinHelmet = new DefenceItem("goblinHelmet", 0, 5);
            AttackItem blackSword = new AttackItem("blackSword", 1, 10);
            room3.getItems().add(spiderEye);
            room3.getItems().add(goblinHelmet);
            room3.getItems().add(blackSword);
        }

        g.addVertex(room3);

        // Room 4.
        Room room4 = new Room("Loot Room", "It seems there's no one in this part of the forest, search if there are some items");
        room4.setBackgroundPath("assets/castle.png");

        {
            DefenceItem woodArmor = new DefenceItem("woodArmor", 0, 8);
            UsableItem gingerRoot = new UsableItem("gingerRoot");
            gingerRoot.setAdditionalHp(3);
            gingerRoot.setAdditionalDefence(2);
            AttackItem woodSword = new AttackItem("woodSword", 1, 7);
            room4.getItems().add(woodArmor);
            room4.getItems().add(gingerRoot);
            room4.getItems().add(woodSword);
        }

        g.addVertex(room4);

        // Room 5.
        Room room5 = new Room("Second Monster room", "Oh no, a monster appears in front of you!");
        room5.setBackgroundPath("assets/castle.png");
        {
            Inventory enemyInventory2 = new Inventory(100);
            Stats enemyStats2 = new Stats(40,40,8,2);
            Enemy enemy2 = new Enemy(enemyInventory2, enemyStats2, "Gian Spider");
            enemy2.setDefaultDialog("Hi, human, now it's the hour of your death!");
        }

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

        // Edges
        StoryNodeLink edge12 = new StoryNodeLink();
        edge12.setAction(new Action("north"));
        g.addEdge(room1, room2, edge12);

        StoryNodeLink edge23 = new StoryNodeLink();
        edge23.setAction(new Action("north"));
        edge23.setKey(key2);
        g.addEdge(room2, room3, edge23);

        StoryNodeLink edge32 = new StoryNodeLink();
        edge32.setAction(new Action("south"));
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
     * Application properties.
     */
    protected final Config config;

    /**
     * Logger.
     */
    protected static final Logger logger = LogManager.getLogger();
}
