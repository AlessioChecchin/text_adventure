package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CmdUseTest extends AbstractCommandTest
{
    @BeforeAll
    static void setUp()
    {
        command = new CmdUse();
    }

    @Test
    void execute() throws InterruptedException, ConfigurationException
    {
        // Set the context.
        ApplicationContext context = resetContext();

        command.setContext(context);

        // Set the args and output.
        ArrayList<String> args = new ArrayList<>(1);
        args.add("apple");
        Room room = new Room("test", "test");

        context.getGame().setCurrentNode(room);
        command.setArgs(args);
        command.setWriter(new PrintWriter(System.out));

        // Add an item for the test.
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        context.getGame().getPlayer().getInventory().addItem(apple);

        // Execute and test.
        command.execute();
        assertEquals(20, context.getGame().getPlayer().getStats().getHp(), "problems with use");
    }
}