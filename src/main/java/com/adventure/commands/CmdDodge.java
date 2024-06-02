package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;

public class CmdDodge extends AbstractCommand{

    @Override
    public void execute() throws InterruptedException {
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();

        Player player = applicationContextProvider.getGame().getPlayer();
        if (player.useDodge()) {
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
            if(choice == CmdFight.Move.ATTACK) {
                monster.hit(player.getStats().getBaseAttack());
                this.writer.println("Player dodge and hit");
            }
            else this.writer.println("Both Dodge");
        }
    }
}
