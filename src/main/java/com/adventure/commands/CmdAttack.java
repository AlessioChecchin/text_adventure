package com.adventure.commands;

import com.adventure.models.*;
import com.adventure.models.nodes.*;
import com.adventure.utils.ApplicationContextProvider;

public class CmdAttack extends AbstractCommand {

    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();

        Player player = applicationContextProvider.getGame().getPlayer();
        Room currentRoom = (Room) applicationContextProvider.getGame().getCurrentNode();
        Enemy monster = currentRoom.getMonster();

        //Monster set moves
        RandomCollection<Object> decision = new RandomCollection<>()
                .add(85, CmdFight.Move.ATTACK).add(15, CmdFight.Move.DODGE);
        CmdFight.Move choice = (CmdFight.Move) decision.next();

        //monster dodges check
        if (choice == CmdFight.Move.DODGE) {
            if (!monster.useDodge()) choice = CmdFight.Move.ATTACK;
        }

        //player move
        player.hit(monster.getStats().getBaseAttack());
        this.writer.println("Monster hits player");

        //if monster dodge he doesn't get damage
        if(choice != CmdFight.Move.DODGE){
            monster.hit(player.getStats().getBaseAttack());
            this.writer.println("Player hits Monster");
        }
        else this.writer.println("Ouch, Monster dodge");
    }
}
