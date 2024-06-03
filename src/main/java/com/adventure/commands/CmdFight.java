package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.*;
import com.adventure.utils.ApplicationContextProvider;

import java.util.ArrayList;


public class CmdFight extends AbstractCommand {

    private Command command;

    @Override
    public void execute() throws InterruptedException {

        //check the correct number of parameters for this command
        int size = this.getArgs().size();
        if (size == 0) {

            //set Player and monster
            Player player = this.context.getGame().getPlayer();
            Room currentRoom = (Room) this.context.getGame().getCurrentNode();
            monster = currentRoom.getMonster();

            //check if you have killed the monster
            if(monster.getAlive()) {

                //command enable during the fight
                CommandParser commandParser = CommandParser.getInstance();
                commandParser.disableAll();
                commandParser.enable("attack");
                commandParser.enable("dodge");
                commandParser.enable("use");
                commandParser.enable("help");
                commandParser.enable("clear");
                commandParser.enable("show");

                //test for fight
                UsableItem apple = new UsableItem("apple");
                apple.setAdditionalHp(10);
                apple.setAdditionalDefence(2);
                apple.setAdditionalAttack(2);
                player.setInventory(new Inventory(100));
                player.getInventory().addItem(apple);
                monster.setDefaultDialog("Hi human, i'm here to destroy you");
                this.writer.println(monster.getDefaultDialog());
                player.resetDodge();
                this.writer.println("");
                this.writer.println("The battle against a monster is starting...");

                //iterate battle
                while ((player.getAlive()) || (monster.getAlive())) {
                    //player have to digit a command
                    this.writer.println("What do you want to do?");
                    String s = this.safeReadNextLine();
                    try {
                        this.command = commandParser.parseCommand(s);
                        this.command.setInputStream(this.getInputStream());
                        this.command.setWriter(this.getWriter());
                        this.command.execute();
                    } catch (Exception e) {
                        this.writer.println("Unknown command, try with attack, dodge or use");
                    }

                }
                this.writer.println("End fight");
                this.writer.println(player.getName() + " wins");

                //enable and disable command after finish the fight
                commandParser.enableAll();
                commandParser.disable("attack");
                commandParser.disable("dodge");
            }
            else this.writer.println("You have already kill the monster in this room");
        }
        else this.writer.println("Wrong number of parameters");
    }


    public void kill(){
        super.kill();
        if(this.command != null){
            super.kill();
        }
    }


    private Player player;
    private Enemy monster;

    //enum for choosing what a monster has to do in a random way
    public enum Move {
        ATTACK,
        DODGE
    }
}

