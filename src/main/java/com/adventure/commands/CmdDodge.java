package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;

public class CmdDodge extends AbstractCommand{

    @Override
    public void execute() throws InterruptedException {
        //check the correct number of parameters
        int size = this.getArgs().size();
        if (size == 0) {
            //setting player and monster
            Player player = this.context.getGame().getPlayer();
            if (player.useDodge()) {
                Room currentRoom = (Room) this.context.getGame().getCurrentNode();
                Enemy monster = currentRoom.getMonster();

                //Monster set moves
                RandomCollection<Object> decision = new RandomCollection<>()
                        .add(85, CmdFight.Move.ATTACK).add(15, CmdFight.Move.DODGE);
                CmdFight.Move choice = (CmdFight.Move) decision.next();

                //monster dodges check
                if (choice == CmdFight.Move.DODGE) {
                    if (!monster.useDodge()) choice = CmdFight.Move.ATTACK;
                }

                //player interaction with monster
                if (choice == CmdFight.Move.ATTACK) {
                    monster.hit(player.getStats().getBaseAttack());
                    this.writer.println(player.getName() + " dodge the incoming attack, hits the monster and damage is " + player.getStats().getBaseAttack() + "hp");
                }
                else this.writer.println("You and the monster dodge in the same time");

            }
            else this.writer.println("You have already use all your available dodges");
        }
        else this.writer.println("Wrong parameters for this command");
    }

}
