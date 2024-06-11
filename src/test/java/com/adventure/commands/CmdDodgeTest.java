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
    void consumeDodgeTest() throws InterruptedException, ConfigurationException
    {
        Command command = new CmdDodge();

        command.setContext(context);

        int dodges = player.getAvailableDodges();
        command.execute();
        assertEquals(dodges - 1, player.getAvailableDodges(), "Problems with dodge command");

    }
}