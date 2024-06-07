package com.adventure.commands;

import com.adventure.config.Config;
import com.adventure.models.*;
import com.adventure.models.nodes.*;

public class CmdAttack extends AbstractCommand
{

    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters
        if (this.correctArgumentsNumber(0)) { return; }

        Config currentConfig = this.context.getConfig();
        Game game = this.context.getGame();
        Player player = game.getPlayer();

        StoryNode currentNode = game.getCurrentNode();

        if(!(currentNode instanceof Room))
        {
            this.writer.println("You can't perform this action here");
            return;
        }

        Room currentRoom = (Room) currentNode;
        Enemy monster = currentRoom.getMonster();

        if(monster == null)
        {
            this.writer.println("There is no monster to fight here");
            return;
        }

        // Monster set moves
        RandomCollection<Object> decision = new RandomCollection<>()
                .add(currentConfig.getMonsterAttackProbability(), CmdFight.Move.ATTACK)
                .add(currentConfig.getMonsterDodgeProbability(), CmdFight.Move.DODGE);

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
            monster.hit(player.getStats().getBaseAttack());
            this.writer.println(player.getName() + " hits the monster and damage is " + player.getStats().getBaseAttack() + " hp");
        }
        else
        {
            this.writer.println("Ouch, Monster dodges the attack!");
        }

        // The player always gets a hit if he decides to attack.
        int damage = monster.getAttackDamage();
        int inflectedDamage = player.hit(damage);

        this.writer.println("Monster hits " + player.getName() + " and damage is " + inflectedDamage + " hp");
    }
}
