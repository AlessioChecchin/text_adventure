package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractCommandTest
{
    protected static ApplicationContext context;
    protected static Room room;
    protected static Enemy monster;
    protected static Player player;
    protected static Game game;

    // Method for initialize a Context for the command testing.
    public static void setTestContext(ApplicationContext context) throws ConfigurationException
    {
        game = new Game(context.getConfig());
        game.setId("test");
        room = new Room("test", "test");
        monster = new Enemy(new Inventory(10),new Stats(40,40,5,0), "monster");
        room.setMonster(monster);
        game.setCurrentNode(room);
        player = new Player("player", new Inventory(10), new Stats(20,20,1,0));
        game.setPlayer(player);
        context.setGame(game);
    }

    @BeforeAll
    static void setup() throws ConfigurationException
    {
        context = ApplicationContextProvider.getInstance();
        assert context != null;
        setTestContext(context);
    }

    @BeforeEach
    void resetContext() throws ConfigurationException
    {
        setTestContext(context);
    }
}

