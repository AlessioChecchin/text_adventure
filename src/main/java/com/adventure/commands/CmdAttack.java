package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.*;
import com.adventure.models.nodes.*;

import java.util.ArrayList;

public class CmdAttack extends AbstractCommand {

    @Override
    public void execute() throws InterruptedException {
        //check the correct number of parameters
        int size = this.getArgs().size();
        if (size == 0) {
            Player player = this.context.getGame().getPlayer();
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

            //if monster dodge he doesn't get damage
            if (choice != CmdFight.Move.DODGE) {
                monster.hit(player.getStats().getBaseAttack());
                this.writer.println(player.getName() + " hits the monster and damage is " + player.getStats().getBaseAttack() + "hp");
            } else this.writer.println("Ouch, Monster dodge");

            //player move
            player.hit(monster.getStats().getBaseAttack());
            this.writer.println("Monster hits " + player.getName() + " and damage is " + monster.getStats().getBaseAttack() + "hp");
        }
        else{ this.writer.println("Wrong parameters for this command");}
    }


    public ArrayList<String> getPossibleArgs()
    {
        return new ArrayList<>();
    }
}
