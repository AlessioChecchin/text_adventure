package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;

public class CmdFight extends AbstractCommand {

    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();

        while ((player.getAlive()) && (monster.getAlive())) {

            //Monster set moves
            RandomCollection<Object> decision = new RandomCollection<>()
                    .add(85, CmdFight.Move.ATTACK).add(15, CmdFight.Move.DODGE);
            CmdFight.Move choice = (CmdFight.Move) decision.next();

            //manage case if choice is Dodge
            if (choice == CmdFight.Move.DODGE) {
                if (monsterDodge == 0) {
                    choice = CmdFight.Move.ATTACK;
                } else monsterDodge--;
            }

            //Player choose what he wants to do

        }


    }

    private Player player;
    private Enemy monster;
    private int turns;
    private int playerDodge;
    private int monsterDodge;
    private static final int MAXDODGE = 3;

    public enum Move {
        ATTACK,
        DODGE
    }
}

