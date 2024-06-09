package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class CmdLookTest extends AbstractCommandTest
{
    @BeforeAll
    static void setup(){ command = new CmdLook(); }

    @Test
    void execute() throws ConfigurationException, InterruptedException
    {
        // Set the context.
        ApplicationContext context = resetContext();

        command.setContext(context);

        // Set the output of the command.
        StringWriter out    = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        command.setWriter(writer);

        String value = String.format("In the current room you can see: %n* apple, atk: 0, def: 0, hp: 0%n");

        // Add an item for the test.
        UsableItem apple = new UsableItem("apple");
        Room room = (Room) context.getGame().getCurrentNode();
        room.getItems().add(apple);

        // Execute and test.
        command.execute();

        assertEquals(out.toString(), value);
    }
}