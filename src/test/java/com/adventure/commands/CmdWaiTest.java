package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.nodes.Action;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNodeLink;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class CmdWaiTest extends AbstractCommandTest
{
    @BeforeAll
    static void setup() {command = new CmdWai();}

    @Test
    void execute() throws ConfigurationException, InterruptedException
    {
        // Set the context.
        ApplicationContext context = resetContext();

        command.setContext(context);

        // Set the output of the command.
        StringWriter stringWriter    = new StringWriter();
        PrintWriter  writer = new PrintWriter(stringWriter);
        command.setWriter(writer);

        // Add path for the graph.
        Room room = new Room("test", "test");
        Room room1 = new Room("test", "test");
        context.getGame().getGameGraph().addVertex(room);
        context.getGame().getGameGraph().addVertex(room1);
        StoryNodeLink link = new StoryNodeLink();
        link.setAction(new Action("north"));
        link.setLocked(false);
        context.getGame().getGameGraph().addEdge(room, room1, link);
        context.getGame().setCurrentNode(room);

        // Execute and test output.
        command.execute();
        String value = String.format("test%nAllowed directions:%n%n* north, not locked%n");
        assertEquals(value, stringWriter.toString());

    }
}

