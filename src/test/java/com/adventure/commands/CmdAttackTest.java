package com.adventure.commands;

import com.adventure.config.ApplicationContext;
import com.adventure.exceptions.ConfigurationException;
import com.adventure.models.*;
import com.adventure.models.items.AttackItem;
import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdAttackTest extends AbstractCommandTest
{
    @BeforeAll
    static void setup(){  }

    @Test
    void testReceiveDamage() throws InterruptedException, ConfigurationException
    {
        ApplicationContext context = resetContext();

        CmdAttack command = new CmdAttack();

        AttackItem sword = new AttackItem("sword", 2, 1);
        Room test = (Room) context.getGame().getCurrentNode();

        Player player = context.getGame().getPlayer();
        Enemy monster = test.getMonster();

        // Giving sword to monster.
        monster.getInventory().addItem(sword);
        monster.getInventory().equipItem(sword);
        context.getGame().setCurrentNode(test);

        int damagePrediction = monster.getAttackDamage();
        int playerHealth = player.getStats().getHp();

        // Execute and test.
        command.execute();

        int newHp = playerHealth - damagePrediction;
        if(newHp < 0) newHp = 0;

        assertEquals(newHp, context.getGame().getPlayer().getStats().getHp(), "Problems with the attack command");
    }

    @Test
    void testBothAttack() throws InterruptedException, ConfigurationException
    {
        ApplicationContext context = resetContext();


        // The monster will always attack
        RandomCollection<Object> collection = new RandomCollection<>().add(100, CmdFight.Move.ATTACK);
        Command command = new CmdAttack(collection);
        command.setContext(context);

        Room test = (Room) context.getGame().getCurrentNode();
        test.setMonster(new Enemy(new Inventory(10),new Stats(40,40,5,0), "monster"));

        AttackItem monsterSword = new AttackItem("monsterSword", 2, 5);
        Enemy monster = test.getMonster();
        monster.getInventory().addItem(monsterSword);
        monster.getInventory().equipItem(monsterSword);

        Player player =  context.getGame().getPlayer();
        AttackItem playerSword = new AttackItem("playerSword", 2, 5);
        player.getInventory().addItem(playerSword);
        player.getInventory().equipItem(playerSword);

        context.getGame().setCurrentNode(test);

        Stats playerStats = player.getStats();
        Stats monsterStats = monster.getStats();

        int monsterAttackDamage = monster.getAttackDamage();
        int playerAttackDamage = player.getAttackDamage();
        int playerHealth = playerStats.getHp();
        int monsterHealth = monsterStats.getHp();

        // Execute and test.
        command.execute();

        int newPlayerHealth = playerHealth - monsterAttackDamage;
        int newMonsterHealth = monsterHealth - playerAttackDamage;

        if(newMonsterHealth < 0) newMonsterHealth = 0;
        if(newPlayerHealth < 0) newPlayerHealth = 0;

        assertEquals(newPlayerHealth, playerStats.getHp(), "Problems with the attack command");
        assertEquals(newMonsterHealth, monsterStats.getHp(), "Problems with the attack command");
    }


    @Test
    void testMonsterDodge() throws InterruptedException, ConfigurationException
    {
        ApplicationContext context = resetContext();


        // The monster will always attack
        RandomCollection<Object> collection = new RandomCollection<>().add(100, CmdFight.Move.DODGE);
        Command command = new CmdAttack(collection);
        command.setContext(context);

        Room test = (Room) context.getGame().getCurrentNode();
        test.setMonster(new Enemy(new Inventory(10),new Stats(40,40,5,0), "monster"));

        AttackItem monsterSword = new AttackItem("monsterSword", 2, 5);
        Enemy monster = test.getMonster();
        monster.getInventory().addItem(monsterSword);
        monster.getInventory().equipItem(monsterSword);

        Player player =  context.getGame().getPlayer();
        AttackItem playerSword = new AttackItem("playerSword", 2, 5);
        player.getInventory().addItem(playerSword);
        player.getInventory().equipItem(playerSword);

        context.getGame().setCurrentNode(test);

        Stats playerStats = player.getStats();
        Stats monsterStats = monster.getStats();

        int monsterAttackDamage = monster.getAttackDamage();
        int playerAttackDamage = player.getAttackDamage();
        int playerHealth = playerStats.getHp();
        int monsterHealth = monsterStats.getHp();

        // Execute and test.
        command.execute();

        int newPlayerHealth = playerHealth - monsterAttackDamage;
        int newMonsterHealth = monsterHealth;

        if(newMonsterHealth < 0) newMonsterHealth = 0;
        if(newPlayerHealth < 0) newPlayerHealth = 0;

        // We expect only the player to receive damage.
        assertEquals(newPlayerHealth, playerStats.getHp(), "Problems with the attack command");
        assertEquals(newMonsterHealth, monsterStats.getHp(), "Problems with the attack command");
    }



    @Test
    void testMonsterFinishDodge() throws InterruptedException, ConfigurationException
    {
        ApplicationContext context = resetContext();


        // The monster will always attack
        RandomCollection<Object> collection = new RandomCollection<>().add(100, CmdFight.Move.DODGE);
        Command command = new CmdAttack(collection);
        command.setContext(context);

        Room test = (Room) context.getGame().getCurrentNode();
        test.setMonster(new Enemy(new Inventory(10),new Stats(40,40,5,0), "monster"));

        AttackItem monsterSword = new AttackItem("monsterSword", 2, 5);
        Enemy monster = test.getMonster();

        // Consume all dodges.
        while(monster.useDodge());

        monster.getInventory().addItem(monsterSword);
        monster.getInventory().equipItem(monsterSword);

        Player player =  context.getGame().getPlayer();
        AttackItem playerSword = new AttackItem("playerSword", 4, 5);
        player.getInventory().addItem(playerSword);
        player.getInventory().equipItem(playerSword);

        context.getGame().setCurrentNode(test);

        Stats playerStats = player.getStats();
        Stats monsterStats = monster.getStats();

        int monsterAttackDamage = monster.getAttackDamage();
        int playerAttackDamage = player.getAttackDamage();
        int playerHealth = playerStats.getHp();
        int monsterHealth = monsterStats.getHp();

        // Execute and test.
        command.execute();

        int newPlayerHealth = playerHealth - monsterAttackDamage;
        int newMonsterHealth = monsterHealth - playerAttackDamage;

        if(newMonsterHealth < 0) newMonsterHealth = 0;
        if(newPlayerHealth < 0) newPlayerHealth = 0;

        // We expect only the player to receive damage.
        assertEquals(newPlayerHealth, playerStats.getHp(), "Problems with the attack command");
        assertEquals(newMonsterHealth, monsterStats.getHp(), "Problems with the attack command");
    }


}

