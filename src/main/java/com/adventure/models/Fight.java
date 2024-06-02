package com.adventure.models;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Fight {

    public Fight(Player player, Enemy monster){

        this.player = player;
        this.monster = monster;
        this.playerDodge = MAXDODGE;
        this.monsterDodge = MAXDODGE;

        //start fight when Object is initialized
        startFight();
    }

    public void startFight(){

        while((player.getAlive()) && (monster.getAlive())){

            //Monster set moves
            RandomCollection<Object> decision = new RandomCollection<>()
                    .add(85, Move.ATTACK).add(15, Move.DODGE);
            Move choice = (Move) decision.next();

            //manage case if choice is Dodge
            if(choice == Move.DODGE){
                if(monsterDodge == 0) {
                    choice = Move.ATTACK;
                }
                else monsterDodge --;
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

    public enum Move{
        ATTACK,
        DODGE
    }


}

