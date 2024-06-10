package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdDodgeTest extends AbstractCommandTest
{

    @Test
    void executeTest() throws InterruptedException, ConfigurationException
    {
        Command command = new CmdDodge();

        command.setContext(context);

        // Execute and test the command.
        command.execute();
        assertEquals(2, context.getGame().getPlayer().getAvailableDodges(), "Problems with dodge command");
    }
}