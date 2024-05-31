package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.nodes.*;
import com.adventure.utils.ApplicationContextProvider;

import java.util.Objects;

public class CmdFight extends AbstractCommand {

    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();

        player = applicationContextProvider.getGame().getPlayer();
        Room room = (Room) applicationContextProvider.getGame().getCurrentNode();
        monster = room.getMonster();

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

            //check command

            boolean correctCommand = true;
            while(correctCommand){
                String playerChoice = this.safeReadNext();

                if(playerChoice.equals("attack")){
                    correctCommand = false;
                    attack(choice);
                }

                if(playerChoice.equals("dodge")){
                    correctCommand = false;
                    //if he can't dodge change choice
                    if(!dodge(choice)) correctCommand = true;
                }
            }
        }
    }

    public void attack(Move monsterChoice){
        player.hit(monster.getStats().getBaseAttack());

        //if monster dodge he doesn't get damage
        if(monsterChoice != Move.DODGE){
            monster.hit(player.getStats().getBaseAttack());
        }
    }

    public boolean dodge(Move monsterChoice){
        if(playerDodge == 0) return false;
        if(monsterChoice == Move.ATTACK){
            monster.hit(player.getStats().getBaseAttack());
        }
        return true;
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

