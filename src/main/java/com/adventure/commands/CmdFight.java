package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
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
            StoryNode node = this.context.getGame().getCurrentNode();
            if((node instanceof Room currentRoom) && (currentRoom.getMonster() != null)) {
                Enemy monster = currentRoom.getMonster();

                //check if you have killed the monster
                if (monster.getAlive()) {

                    //command enable during the fight
                    CommandParser commandParser = CommandParser.getInstance();
                    commandParser.disableAll();
                    commandParser.enable("attack");
                    commandParser.enable("dodge");
                    commandParser.enable("use");
                    commandParser.enable("help");
                    commandParser.enable("clear");
                    commandParser.enable("show");

                    //Introduction fight
                    this.writer.println(monster.getDefaultDialog());
                    player.resetDodge();
                    monster.resetDodge();
                    this.writer.println("");
                    this.writer.println("The battle against " + monster.getName() + " is starting...");

                    //iterate battle
                    while ((player.getAlive()) || (monster.getAlive())) {
                        //player have to digit a command
                        this.writer.println("What do you want to do?");
                        String s = this.safeReadNextLine();
                        try {
                            this.writer.flush();
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
                } else this.writer.println("You have already kill the monster in this room");
            } else this.writer.println("No monster to fight");
        }
        else this.writer.println("Wrong number of parameters");
    }


    public void kill(){
        super.kill();
        if(this.command != null){
            super.kill();
        }
    }

    //enum for choosing what a monster has to do in a random way
    public enum Move {
        ATTACK,
        DODGE
    }
}

