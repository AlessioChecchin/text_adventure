package com.adventure.commands;

import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CmdPickTest extends CmdAttackTest {

    static Command command;

    @BeforeAll
    static void setup(){ command = new CmdPick();}

    @Test
    void execute() throws ConfigurationException, InterruptedException {
        // Set the context.
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);

        // Set the args
        ArrayList<String> args = new ArrayList<>(1);
        args.add("apple");
        command.setArgs(args);
        command.setWriter(new PrintWriter(System.out));

        // Adding an Item fot testing.
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        Room room = (Room) applicationContextProvider.getGame().getCurrentNode();
        room.getItems().add(apple);

        // Execute and test.
        command.execute();
        assertTrue(applicationContextProvider.getGame().getPlayer().getInventory().getItems().contains(apple));
    }
}