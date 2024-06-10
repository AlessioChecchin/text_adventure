package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;

public abstract class AbstractCommandTest
{
    static Command command;

    // Method for initialize a Context for the command testing.
    public static void setTestContext(ApplicationContext context) throws ConfigurationException
    {
        Game game = new Game(context.getConfig());
        game.setId("test");
        Room room = new Room("test", "test");
        Enemy monster = new Enemy(new Inventory(10),new Stats(40,40,5,5), "monster");
        room.setMonster(monster);
        game.setCurrentNode(room);
        game.setPlayer(new Player("player", new Inventory(10), new Stats(20,20,1,0)));
        context.setGame(game);
    }

    public static ApplicationContext resetContext() throws ConfigurationException
    {
        ApplicationContext context = ApplicationContextProvider.getInstance();

        assert context != null;

        setTestContext(context);

        return context;
    }
}

