package com.adventure.commands;

import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.Enemy;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CmdUse extends AbstractCommand{
    @Override
    public void execute() throws InterruptedException {

        //check the correct number of parameters
        int size = getArgs().size();
        if(size == 1) {
            //setting player and monster
            Player player = this.context.getGame().getPlayer();
            StoryNode node = this.context.getGame().getCurrentNode();
            if(node instanceof Room currentRoom) {

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
                    player.use(key);
                    this.writer.println("You use " + key);
                }
                catch (NotUsableItemException notUsable) {
                    this.writer.println("Item is not usable");
                }
                catch (NoSuchElementException noElement){
                    this.writer.println("Item not found");
                }

                //if use is call in a fight, monster can attack
                if (monster.getAlive()) {
                    if (choice == CmdFight.Move.ATTACK) {
                        player.hit(monster.getStats().getBaseAttack());
                        this.writer.println("Monster hits " + player.getName() + " and damage is " + monster.getStats().getBaseAttack() + "hp");
                    } else this.writer.println("Monster dodge");
                }
            }
        }
        else this.writer.println("Wrong parameters for this command");
    }

    /**
     * @return all possible items the player can use
     */
    public ArrayList<String> getPossibleArgs()
    {

        Player player = context.getGame().getPlayer();
        List<Item> item = player.getInventory().getItems().stream().filter(Item -> Item.getClass().equals(UsableItem.class)).toList();
        ArrayList<String> possibleItems = new ArrayList<>();
        for(Item i : item)
            possibleItems.add(i.getName());
        return possibleItems;
    }

}
