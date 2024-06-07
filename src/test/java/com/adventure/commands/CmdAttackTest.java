package com.adventure.commands;

import com.adventure.config.ApplicationConfig;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CmdAttackTest {
    Command command;

    @BeforeEach
    void setup(){
        command = new CmdAttack();
    }
    @Test
    void executeTest() throws InterruptedException, ConfigurationException {
        //exercise
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);
        command.execute();

        //test
        assertEquals(applicationContextProvider.getGame().getPlayer().getStats().getHp(), 8, "Problems with the attack command");
    }

    public void setTestContext(ApplicationContextProvider applicationContextProvider) throws ConfigurationException {
        Game game = new Game(new ApplicationConfig(new Properties()));
        game.setId("test");
        Room room = new Room("test", "test");
        Enemy monster = new Enemy(new Inventory(10),new Stats(5,5,5,5), "monster");
        room.setMonster(monster);
        Player player = new Player("player", new Inventory(10), new Stats(10,10,10,10));
        game.setPlayer(player);
        game.setCurrentNode(room);
        applicationContextProvider.setGame(game);
    }
}

