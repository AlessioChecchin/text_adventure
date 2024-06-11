package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractCommandTest
{
    protected ApplicationContext context;
    protected Room room;
    protected Enemy monster;
    protected Player player;
    protected Game game;


    @BeforeEach
    void resetContext() throws ConfigurationException, NoSuchFieldException, IllegalAccessException
    {
        // Setting null in application context provider through reflection.
        Field field = ApplicationContextProvider.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);

        context = ApplicationContextProvider.getInstance();

        assertNotNull(context);

        game = new Game(context.getConfig());
        game.setId("test_game");
        room = new Room("test_name", "test_description");

        monster = new Enemy(new Inventory(10),new Stats(40,40,5,0), "monster_name");

        room.setMonster(monster);
        game.setCurrentNode(room);

        player = new Player("player_name", new Inventory(10), new Stats(20,20,1,0));
        game.setPlayer(player);

        context.setGame(game);
    }
}

