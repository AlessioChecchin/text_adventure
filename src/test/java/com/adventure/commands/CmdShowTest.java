package com.adventure.commands;

import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.items.UsableItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class CmdShowTest extends CmdAttackTest {

    static Command command;

    @BeforeAll
    static void setup() {command = new CmdShow();}
    @Test
    public void executeTest() throws InterruptedException, ConfigurationException {
        // Set the context.
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);

        // Set the output of the command.
        StringWriter out    = new StringWriter();
        PrintWriter  writer = new PrintWriter(out);
        String value = String.format("Inventory content: %nname: apple, atk: 0, def: 0, hp: 0%nTotal weight: 0%n");
        command.setWriter(writer);

        // Add an item for test.
        UsableItem apple = new UsableItem("apple");
        applicationContextProvider.getGame().getPlayer().getInventory().addItem(apple);

        // Execute and test.
        command.execute();
        System.out.println(out);
        assertEquals(out.toString(), value);
    }
}