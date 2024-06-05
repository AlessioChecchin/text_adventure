package com.adventure.commands;

import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.items.Item;
import com.adventure.models.nodes.Room;
import com.adventure.utils.ApplicationContextProvider;

import java.util.List;

public class CmdUse extends AbstractCommand{
    @Override
    public void execute() throws InterruptedException {

        //check the correct number of parameters
        int size = getArgs().size();
        if(size == 1) {
            //setting player and monster
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

            //player interactions with the monster
            String key = this.getArgs().get(0);
            try {
                Item item = player.getInventory().getItems().stream().filter(Item -> key.equals(Item.getName())).findFirst().orElse(null);
                this.writer.println("You use " + item.getName());
                //this.writer.println(player.getName() + " gains " + player.use(item));
            }
            catch(Exception e){
                this.writer.println("Item not found");
            }

            //if use is call in a fight, monster can attack
            if(monster.getAlive()) {
                if(choice == CmdFight.Move.ATTACK) {
                    player.hit(monster.getStats().getBaseAttack());
                    this.writer.println("Monster hits " + player.getName() + " and damage is " + monster.getStats().getBaseAttack() + "hp");
                }
                else this.writer.println("Monster dodge");
            }
        }
        else this.writer.println("Wrong parameters for this command");
    }

}
