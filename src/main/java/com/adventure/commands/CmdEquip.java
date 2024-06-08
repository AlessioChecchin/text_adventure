package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Player;
import com.adventure.models.items.Equipable;
import com.adventure.models.items.Item;

import java.util.ArrayList;

/**
 * Equips an item in the inventory.
 */
public class CmdEquip extends AbstractCommand{
    @Override
    public void execute() throws InterruptedException {
        if (!this.correctArgumentsNumber(1)) { return; }
        Game game = this.context.getGame();
        Player player = game.getPlayer();
        String key = this.getArgs().get(0);
        //search item in the inventory
        Item item = player.getInventory().getItems().stream().filter(Item -> key.equals(Item.getName())).findFirst().orElse(null);

        //check if item is in the inventory
        if(item != null) {
            if(item instanceof Equipable equipable) {
                player.getInventory().equipItem(equipable);
                this.writer.println(player.getName() + " equiped " + key);
            }
            else {this.writer.println("Item isn't equipable");}
        }
        else {this.writer.println("Item not found");}
    }

    @Override
    public ArrayList<String> getPossibleArgs() throws GameStorageException
    {
        return new ArrayList<>();
    }
}
