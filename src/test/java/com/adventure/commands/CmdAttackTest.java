package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.items.AttackItem;
import com.adventure.models.nodes.Room;
import com.adventure.config.ApplicationContextProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdAttackTest extends AbstractCommandTest
{
    @BeforeAll
    static void setup(){ command = new CmdAttack(); }

    @Test
    void executeTest() throws InterruptedException, ConfigurationException
    {

        ApplicationContext context = makeContext();

        command.setContext(context);
        AttackItem sword = new AttackItem("sword", 2, 1);
        Room test = (Room) context.getGame().getCurrentNode();
        test.getMonster().getInventory().addItem(sword);
        test.getMonster().getInventory().equipItem(sword);
        context.getGame().setCurrentNode(test);

        // Execute and test.
        command.execute();

        assertEquals(context.getGame().getPlayer().getStats().getHp(), 9, "Problems with the attack command");
    }
}

