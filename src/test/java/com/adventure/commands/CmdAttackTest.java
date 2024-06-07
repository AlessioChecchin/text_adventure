package com.adventure.commands;

import com.adventure.config.ApplicationConfig;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class CmdAttackTest {
    static Command command;

    @BeforeAll
    static void setup(){
        command = new CmdAttack();
    }
    @Test
    void executeTest() throws InterruptedException, ConfigurationException {
        // Set context-
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);
        AttackItem sword = new AttackItem("sword", 2, 1);
        Room test = (Room) applicationContextProvider.getGame().getCurrentNode();
        test.getMonster().getInventory().addItem(sword);
        test.getMonster().getInventory().equipItem(sword);
        applicationContextProvider.getGame().setCurrentNode(test);

        // Execute and test.
        command.execute();

        assertEquals(applicationContextProvider.getGame().getPlayer().getStats().getHp(), 9, "Problems with the attack command");
    }

    // Method for initialize a Context for the command testing.
    public void setTestContext(ApplicationContextProvider applicationContextProvider) throws ConfigurationException {
        Game game = new Game(applicationContextProvider.getConfig());
        game.setId("test");
        Room room = new Room("test", "test");
        Enemy monster = new Enemy(new Inventory(10),new Stats(5,5,5,5), "monster");
        room.setMonster(monster);
        game.setCurrentNode(room);
        game.setPlayer(new Player("player", new Inventory(10), new Stats(20,20,1,0)));
        applicationContextProvider.setGame(game);
    }
}

