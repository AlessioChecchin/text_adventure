package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.Player;
import com.adventure.models.items.UsableItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class CmdShowTest extends AbstractCommandTest
{
    @BeforeAll
    static void setup() { command = new CmdShow(); }

    @Test
    public void checkOutput() throws InterruptedException, ConfigurationException
    {
        // Set the context.
        ApplicationContext context = resetContext();

        command.setContext(context);

        // Set the output of the command.
        StringWriter out    = new StringWriter();
        PrintWriter  writer = new PrintWriter(out);

        Player player = context.getGame().getPlayer();
        String playerName = player.getName();

        String value = String.format("%s's stats:%nMax Hp: 20%nHp: 20%nBase attack: 1%nBase defence: 0%n%nInventory content: %napple (atk: 0, def: 0, hp: 0)%nTotal weight: 0%n", playerName);
        command.setWriter(writer);

        // Add an item for test.
        UsableItem apple = new UsableItem("apple");
        context.getGame().getPlayer().getInventory().addItem(apple);

        // Execute and test.
        command.execute();

        assertEquals(out.toString(), value);
    }
}