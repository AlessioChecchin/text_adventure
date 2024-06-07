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

class CmdUseTest extends CmdAttackTest {

    static Command command;

    @BeforeAll
    static void setUp() {
        command = new CmdUse();
    }

    @Test
    void execute() throws InterruptedException, ConfigurationException {
        // Set the context.
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);

        // Set the args and output.
        ArrayList<String> args = new ArrayList<>(1);
        args.add("apple");
        Room room = new Room("test", "test");
        applicationContextProvider.getGame().setCurrentNode(room);
        command.setArgs(args);
        command.setWriter(new PrintWriter(System.out));

        // Add an item for the test.
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        applicationContextProvider.getGame().getPlayer().getInventory().addItem(apple);

        // Execute and test.
        command.execute();
        assertEquals(20, applicationContextProvider.getGame().getPlayer().getStats().getHp(), "problems with use");
    }
}