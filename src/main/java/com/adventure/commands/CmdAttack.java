package com.adventure.commands;

import com.adventure.config.Config;
import com.adventure.models.*;
import com.adventure.models.nodes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used during a fight to attack an enemy.
 */
public class CmdAttack extends AbstractRandomDecisionCommand
{
    public CmdAttack()
    {
        super();
    }

    public CmdAttack(RandomCollection<Object> collection)
    {
        super(collection);
    }

    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters
        if (!this.correctArgumentsNumber(0))
        {
            this.writer.println("Invalid number of arguments! Usage: attack");
            return;
        }

        Config currentConfig = this.context.getConfig();
        Game game = this.context.getGame();
        Player player = game.getPlayer();

        StoryNode currentNode = game.getCurrentNode();

        if(!(currentNode instanceof Room currentRoom))
        {
            this.writer.println("You can't perform this action here");
            return;
        }

        Enemy monster = currentRoom.getMonster();

        if(monster == null)
        {
            this.writer.println("There is no monster to fight here");
            return;
        }

        CmdFight.Move monsterMove = (CmdFight.Move) decision.next();

        // Checks if the monster decides to dodge.
        if (monsterMove == CmdFight.Move.DODGE)
        {
            // If the monster can't consume a dodge (the monster already consumed all available dodges)
            // then the move becomes an attack.
            if (!monster.useDodge()) monsterMove = CmdFight.Move.ATTACK;
        }

        //if monster dodge he doesn't get damage
        if (monsterMove != CmdFight.Move.DODGE)
        {
            int damage = player.getAttackDamage();
            int inflectedDamage = monster.hit(damage);
            this.writer.println(player.getName() + " hits " + monster.getName() +" and damage is " + inflectedDamage + " hp");
        }
        else
        {
            this.writer.println("Ouch, " + monster.getName() + " dodges the attack!");
        }

        // The player always gets a hit if he decides to attack.
        int damage = monster.getAttackDamage();
        int inflectedDamage = player.hit(damage);

        this.writer.println(monster.getName() + " hits " + player.getName() + " and damage is " + inflectedDamage + " hp");
    }
    public List<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
