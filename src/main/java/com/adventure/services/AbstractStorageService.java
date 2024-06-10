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

        Stats stats = new Stats(100,100,1,0);

        game.setPlayer(new Player(playerName, playerInventory, stats));

        UsableItem healthPotion = new UsableItem("healthPotion");
        healthPotion.setAdditionalHp(10);

        String key1 = "level 1";
        String key2 = "Level 2";
        String key3 = "Level 3";
        String key4 = "Level 4";
        String key5 = "Level 5";
        String key6 = "Level 6";
        String key7 = "Victory";

        // Room 1.
        Room room1 = new Room("Introduction room", "In a distant era, in the lands of Margorgh, in the darkest depths of a shadowy forest, it is said that there exists a Castle ruled by evil, housing an unimaginable power. For millennia, humanity has sought to wrest this power from the forces of evil, with battles and wars marking the quest for this power. Over time, the traces of the Castle and the power it held within were lost, vanished like leaves on the trees in winter, or so it was believed...\n" +
                "\n" +
                "A mysterious scroll was found by a warrior with a heart of gold, a scroll containing knowledge that even he could not imagine where it would lead him. The name of this hero was " + playerName + ", a name that, unbeknownst to him, would be remembered forever.\n" +
                "\n" +
                "The scroll led him through abandoned and devastated lands where the scent of death and torment still lingered. Our Hero arrived at the edge of a forest with a spectral aura, but driven by curiosity, he did not hesitate to venture into the dark woods...\n" + "Welcome, it is time to embark on this adventure as " + playerName + ". The forest that lies before your eyes is a dense network of dark canopies, creating a natural barrier to the sight of the horizon. Inside, all is silent, and not even the leaves stir in this gloomy and mysterious atmosphere. However, your determined warrior spirit urges you to venture into the forest, something calls to you. Explore the forest following the scroll, good luck Hero!\n" +
                "It seems that there are some items in this part of the forest, try to 'look' for finding something!");
        room1.setBackgroundPath("assets/castle.png");
        room1.getItems().add(sword);
        room1.getItems().add(healthPotion);
        room1.getItems().add(new Key(key1));
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
            Enemy enemy2 = new Enemy(enemyInventory2, enemyStats2, "Glorck");
            enemy2.setDefaultDialog("I'm Glorck the invincible Goblin, if you want to reach the castle you have to kill me!");

            UsableItem apple2 = new UsableItem("apple");
            apple2.setAdditionalHp(5);
            enemy2.getInventory().addItem(apple2);
            room2.setMonster(enemy2);
        }

        g.addVertex(room2);

        // Room 3.
        Room room3 = new Room("Left forest", "Oh no, it's a trap, a big Spider appears in front of you");
        //TODO set che correct background path
        room3.setBackgroundPath("assets/castle.png");

        {
            UsableItem spiderEye = new UsableItem("spiderEye");
            DefenceItem spiderHelmet = new DefenceItem("spiderHelmet", 0, 5);
            AttackItem blackSword = new AttackItem("blackSword", 1, 10);

            Inventory enemyInventory2 = new Inventory(100);
            Stats enemyStats2 = new Stats(40,40,8,2);
            Enemy enemy2 = new Enemy(enemyInventory2, enemyStats2, "Giant Spider");

            enemy2.getInventory().addItem(spiderEye);
            enemy2.getInventory().addItem(spiderHelmet);
            enemy2.getInventory().addItem(blackSword);

            enemy2.setDefaultDialog("Hi, human, now it's the hour of your death!");
            enemy2.getInventory().addItem(new Key(key3));
            room3.setMonster(enemy2);
        }

        g.addVertex(room3);

        // Room 4.
        Room room4 = new Room("right part forest", "You enter deeply in the forest, the darkness become stronger, in front of you there's a terrible creature");
        room4.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory3 = new Inventory(100);
            Stats enemyStats3 = new Stats(50, 50, 10, 5);
            Enemy enemy3 = new Enemy(enemyInventory3, enemyStats3, "Maglarg");
            enemy3.setDefaultDialog("It seems like there's an undesired guest here.\n I'm Maglarg, the BrainsEater, you are going to die human");
            enemy3.getInventory().addItem(new Key(key4));
            room4.setMonster(enemy3);
        }

        g.addVertex(room4);

        // Room 5.
        Room room5 = new Room("Looting Room", "This part of the forest has a lot of items!");
        room5.setBackgroundPath("assets/castle.png");
        {
            DefenceItem woodArmor = new DefenceItem("woodArmor", 1, 8);
            UsableItem gingerRoot = new UsableItem("gingerRoot");
            gingerRoot.setAdditionalHp(3);
            gingerRoot.setAdditionalDefence(2);
            AttackItem woodSword = new AttackItem("woodSword", 1, 7);
            room5.getItems().add(woodArmor);
            room5.getItems().add(gingerRoot);
            room5.getItems().add(woodSword);
        }

        g.addVertex(room5);

        // Room 6.
        Room room6 = new Room("K6 fight room", "Oh no, a dragon! Fight the dragon and take the loot that drops");
        room6.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory6 = new Inventory(100);
            Stats enemyStats6= new Stats(100,100,15,5);
            enemyInventory6.addItem(new Key(key6));
            Enemy enemy6 = new Enemy(enemyInventory6, enemyStats6, "Dragon");
            enemy6.setDefaultDialog("You will never take the castle's keys muhahahh.\nI'll kill u.");

            room6.setMonster(enemy6);
        }

        g.addVertex(room6);

        // Room 7.
        Room room7 = new Room("Loot room", "No enemies here :)");
        room7.setBackgroundPath("assets/castle.png");
        g.addVertex(room7);

        // Room 8.
        Room room8 = new Room("K5 fight room", "Oh no, a monster is attacking you!");
        room8.setBackgroundPath("assets/castle.png");

        {
            Inventory enemyInventory5 = new Inventory(100);
            Stats enemyStats5 = new Stats(80,80,10,8);
            enemyInventory5.addItem(new Key(key5));
            Enemy enemy5 = new Enemy(enemyInventory5, enemyStats5, "Goblin King");
            enemy5.setDefaultDialog("It's the hour of your death!");

            room8.setMonster(enemy5);


        }

        g.addVertex(room8);

        // Room 9.
        Room room9 = new Room("Loot room before the final boss", "The forest begins to thin out, and before you appears a grim scene: a huge castle dominates the horizon. The imposing towers pierce the dark sky like sharp claws, and eerie gargoyle statues along the rooftops seem to be watching you. The eyes of the gargoyles glow a blood-red, the oak wooden gate swings open, a flock of bats flies out from the castle, and the smell of death spreads through the surrounding area. A voice in your head calls you, inviting you to enter the castle. You draw your weapon, take a deep breath, and cross the threshold of the castle.\n" +
                "Inside, no natural light enters; there are candles that dimly illuminate the entrance. You hear suspicious noises, you are not alone in this mysterious place....");
        room9.setBackgroundPath("assets/castle.png");
        {
            DefenceItem darkArmor = new DefenceItem("darkArmor", 0, 25);
            UsableItem healingPotion = new UsableItem("healingPotion");
            healingPotion.setAdditionalHp(30);
            AttackItem bigHammer = new AttackItem("bigHammer", 2, 15);
            room5.getItems().add(darkArmor);
            room5.getItems().add(healingPotion);
            room5.getItems().add(bigHammer);
        }
        g.addVertex(room9);

        // Room 10.
        Room room10 = new Room("First boss Room", "The gold-adorned door swings open, allowing you to enter the throne room. Indeed, an imposing dark stone throne stands at the end of the hall. However, you notice a mysterious being sitting on the throne.\n " +
                "You take a step forward and hear a voice coming from the throne: “I have been expecting you, young human.” Along the walls of the hall, an infinite number of torches light up, revealing the hall in its entirety. Sitting on the throne, a small goblin with a long beard and withered face watches you.\n “After millennia, finally someone from outside comes to visit me. You must be here to try to take the legendary Sword of Shadows.” The goblin stands and points to a sword embedded in a rock.\n" +
                " “You must be very strong to have managed to defeat the castle's protectors. It's a pity that now you will meet your end. You should know that this sword was so powerful that even the forces of evil could not use it.\n " +
                "However, they decided to keep it away from humans to prevent this tremendous power from falling into their hands. No one has ever managed to pull it from that rock, maybe you could be the one to do it, who knows, but it’s a shame we will never find out.”\n" +
                " The goblin took a key from the bunch on his belt, opened a gate hidden behind the throne, and escaped toward a staircase leading to the tiers of the throne room. From the just-opened gate, a stormy roar was heard, and a three-headed beast leapt into the hall, shaking the entire room.\n" +
                " Three pairs of bloodthirsty eyes watch you. It's time to prove your worth in the hardest battle you've faced so far: you must defeat Kalist, the guardian of the Sword of Shadows....");
        room10.setBackgroundPath("assets/castle.png");
        {
            Inventory enemyInventory6 = new Inventory(100);
            AttackItem shadowsSword = new AttackItem("shadowSword", 2, 30);
            enemyInventory6.addItem(shadowsSword);
            Stats enemyStats6 = new Stats(200, 200, 30, 10);
            Enemy enemy6 = new Enemy(enemyInventory6, enemyStats6, "Kalist");
            enemy6.setDefaultDialog("Groarrrrrrr!!");
            room10.setMonster(enemy6);

        }
        g.addVertex(room10);

        // Room 11.
        Room room11 = new Room("First boss Room","Kalist collapses to the ground, exhaling his last breath. You head towards the sword and try to pull it out. With minimal force, the sword slides out of the rock and you brandish it.\n" +
                " A dark aura begins to emanate from the sword, and the hall starts to tremble as if shaken by a violent earthquake. However, the shadow surrounding the sword starts to be overwhelmed by a light coming from your heart, and now the sword also shines with this light.\n" +
                " The fear that had tormented evil for millennia was real: the Sword of Shadows has been overcome by the light of hope from a young man, transforming it into the Sword of Light.\n" +
                "“This is not possible; this should never have happened!” screams the Goblin as he rushes down the stairs toward the now lifeless body of Kalist.\n" +
                " “I never thought this could happen. I will have to play my trump card. Sorry for you, boy, even if you defeated Kalist, you will never beat me. I am Mucksnarl, the true protector of the castle, the one who will end your life once and for all.”\n" +
                " Mucksnarl took a vial with a bluish liquid and drank it all in one gulp. His body began to mutate; his muscles developed, and his height grew frightfully. The small, elderly goblin who had welcomed our hero had now become a terrifying monster almost 5 meters tall. “Prepare yourself; your time has come!”");
        {
            Inventory enemyInventory7 = new Inventory(100);
            enemyInventory7.addItem(new Key(key7));
            Stats enemyStats7 = new Stats(300, 300, 40, 20);
            Enemy enemy7 = new Enemy(enemyInventory7, enemyStats7, "Mucksnarl");
            enemy7.setDefaultDialog("It's the time for the last dance");
            room11.setMonster(enemy7);
        }

        g.addVertex(room11);


        // Edges
        StoryNodeLink edge12 = new StoryNodeLink();
        edge12.setKey(key1);
        edge12.setAction(new Action("north"));
        g.addEdge(room1, room2, edge12);

        StoryNodeLink edge21 = new StoryNodeLink();
        edge21.setAction(new Action("south"));
        g.addEdge(room2, room1, edge21);

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

        StoryNodeLink edge27 = new StoryNodeLink();
        edge27.setAction(new Action("north"));
        g.addEdge(room2, room7, edge27);

        StoryNodeLink edge72 = new StoryNodeLink();
        edge72.setAction(new Action("south"));
        g.addEdge(room7, room2, edge72);


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

        StoryNodeLink edge1011 = new StoryNodeLink();
        edge1011.setAction(new Action("north"));
        edge1011.setKey(key7);
        g.addEdge(room10, room11, edge1011);

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
