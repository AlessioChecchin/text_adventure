package com.adventure.commands;

import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest
{
    CommandParser parser;

    @BeforeEach
    void resetParser() throws NoSuchFieldException, IllegalAccessException
    {
        // Setting null in command parser through reflection.
        Field field = CommandParser.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);

        parser = CommandParser.getInstance();
        parser.setContext(ApplicationContextProvider.getInstance());

        parser.registerCommand("test1", CmdNewGame.class);
        parser.registerCommand("test2", CmdUse.class);
        parser.registerCommand("test3", CmdFight.class);
        // Defining an alias.
        parser.registerCommand("test4", CmdFight.class);
        parser.enableAll();
    }

    @Test
    void testRegistration()
    {
        List<String> expected = new ArrayList<>();
        expected.add("test1");
        expected.add("test2");
        expected.add("test3");
        expected.add("test4");

        assertEquals(expected, parser.getCommands());
        assertEquals(CmdNewGame.class.getName(), parser.getCommandMetadata("test1").getCommandClass().getName());
        assertNull(parser.getCommandMetadata("notfound"));
    }

    @Test
    void testUnregister()
    {
        parser.unregisterCommand("test1");
        parser.enableAll();

        List<String> expected = new ArrayList<>();
        expected.add("test2");
        expected.add("test3");
        expected.add("test4");

        assertEquals(expected, parser.getCommands());
    }


    @Test
    void testDisableEnable()
    {
        parser.disable("test1");
        parser.disable("test2");


        List<String> expected1 = new ArrayList<>();
        expected1.add("test3");
        expected1.add("test4");

        assertEquals(expected1, parser.getCommands());

        List<String> expected2 = new ArrayList<>();
        expected2.add("test1");
        expected2.add("test3");
        expected2.add("test4");

        parser.enable("test1");
        assertEquals(expected2, parser.getCommands());
    }

    @Test
    void testParseCommand()
    {
        Command cmd;

        cmd = parser.parseCommand("test1");
        assertNotNull(cmd);
        assertInstanceOf(CmdNewGame.class, cmd);

        cmd = parser.parseCommand("test2");
        assertNotNull(cmd);
        assertInstanceOf(CmdUse.class, cmd);

        cmd = parser.parseCommand("test3");
        assertNotNull(cmd);
        assertInstanceOf(CmdFight.class, cmd);

        cmd = parser.parseCommand("test4");
        assertNotNull(cmd);
        assertInstanceOf(CmdFight.class, cmd);

        cmd = parser.parseCommand("test5");
        assertNull(cmd);
    }
}
