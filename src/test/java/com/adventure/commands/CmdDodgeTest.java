package com.adventure.commands;

import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdDodgeTest extends CmdAttackTest {

    static Command command;
    @BeforeAll
    static void setup() {command = new CmdDodge();}
    @Test
    void executeTest() throws InterruptedException, ConfigurationException {
        // Set the context.
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);

        // Execute and test the command.
        command.execute();
        assertEquals(2, applicationContextProvider.getGame().getPlayer().getAvailableDodges(), "Problems with dodge command");
    }
}