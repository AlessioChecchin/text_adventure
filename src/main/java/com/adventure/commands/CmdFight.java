package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.*;
import com.adventure.utils.ApplicationContextProvider;


public class CmdFight extends AbstractCommand {

    private Command command;

    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        commandParser.disableAll();
        commandParser.enable("attack");
        commandParser.enable("dodge");
        commandParser.enable("use");
        commandParser.enable("help");
        commandParser.enable("clear");

        player = applicationContextProvider.getGame().getPlayer();
        Room room = (Room) applicationContextProvider.getGame().getCurrentNode();
        monster = room.getMonster();
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        player.setInventory(new Inventory(100));
        player.getInventory().addItem(apple);
        while ((player.getAlive()) || (monster.getAlive())) {

            //player have to digit a command
            String s =this.safeReadNextLine();
            try{
            this.command = commandParser.parseCommand(s);
            this.command.setInputStream(this.getInputStream());
            this.command.setWriter(this.getWriter());
            this.command.execute();
            }
            catch(Exception e){
                this.writer.println("Wrong command, try again");
            }

        }
        this.writer.println("End fight");
        commandParser.disableAll();
        commandParser.enable("newGame");
        commandParser.enable("listGames");
        commandParser.enable("loadGame");
        commandParser.enable("help");
        commandParser.enable("clear");
        commandParser.enable("fight");
        commandParser.enable("use");
    }

    public void kill(){
        super.kill();
        if(this.command != null){
            this.kill();
        }
    }


    private Player player;
    private Enemy monster;
    public enum Move {
        ATTACK,
        DODGE
    }
}

