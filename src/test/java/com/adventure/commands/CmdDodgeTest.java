package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdDodgeTest extends AbstractCommandTest
{

    @BeforeAll
    static void setup() { command = new CmdDodge(); }

    @Test
    void executeTest() throws InterruptedException, ConfigurationException
    {
        // Set the context.
        ApplicationContext context = makeContext();

        command.setContext(context);

        // Execute and test the command.
        command.execute();
        assertEquals(2, context.getGame().getPlayer().getAvailableDodges(), "Problems with dodge command");
    }
}