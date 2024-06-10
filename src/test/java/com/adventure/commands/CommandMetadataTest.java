package com.adventure.commands;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandMetadataTest
{
    @Test
    void testConstructor()
    {
        CommandMetadata meta = new CommandMetadata(CmdNewGame.class);
        assertEquals(CmdNewGame.class, meta.getCommandClass());
        assertEquals("", meta.getDescription());
    }

    @Test
    void testConstructorWithDescription()
    {
        String description = "Example description";
        CommandMetadata meta = new CommandMetadata(CmdNewGame.class, description);
        assertEquals(CmdNewGame.class, meta.getCommandClass());
        assertEquals(description, meta.getDescription());
    }
}
