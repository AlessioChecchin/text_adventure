package com.adventure.commands;

import com.adventure.config.Config;
import com.adventure.models.Enemy;
import com.adventure.models.Game;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;

/**
 * Command used to dodge an attack.
 */
public class CmdDodge extends AbstractCommand
{

    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters
        if (!this.correctArgumentsNumber(0)) { return; }

        Config currentConfig = this.context.getConfig();
        Game game = this.context.getGame();
        Player player = game.getPlayer();

        StoryNode currentNode = game.getCurrentNode();

        // Check that the player is in a room.
        if(!(currentNode instanceof Room))
        {
            this.writer.println("You can't perform this action here");
            return;
        }

        if (player.useDodge())
        {
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

            // Check if the option for the monster is 'dodge'
            if (monsterMove == CmdFight.Move.DODGE)
            {
                // If the monster can't consume a dodge (the monster already consumed all available dodges)
                // then the move becomes an attack.
                if (!monster.useDodge())
                {
                    monsterMove = CmdFight.Move.ATTACK;
                }
            }

            // Player interaction with monster.
            if (monsterMove == CmdFight.Move.ATTACK)
            {
                int damage = player.getAttackDamage();
                int inflectedDamage = monster.hit(damage);

                this.writer.println(player.getName() + " dodge the incoming attack, hits the monster and damage is " + inflectedDamage + " hp");
            }
            // Both player and monster dodge the attack.
            else
            {
                this.writer.println("You and the monster dodge in the same time");
            }
        }
        else
        {
            this.writer.println("You have already use all your available dodges.");
        }
    }

    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
