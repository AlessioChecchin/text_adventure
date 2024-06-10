package com.adventure.commands;

import com.adventure.exceptions.GameStorageException;
import com.adventure.models.Game;
import com.adventure.models.Player;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Equipable;
import com.adventure.models.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Equips an item in the inventory.
 */
public class CmdEquip extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {

        if (!this.correctArgumentsNumber(1))
        {
            this.writer.println("Invalid number of arguments! Usage: equip <item>");
            return;
        }

        Game game = this.context.getGame();
        Player player = game.getPlayer();
        String key = this.getArgs().get(0);

        // Search item in the inventory
        Item item = player.getInventory().getItems().stream().filter(Item -> key.equals(Item.getName())).findFirst().orElse(null);

        // Check if item is in the inventory
        if(item != null)
        {
            if(item instanceof Equipable equipable)
            {
                if(equipable instanceof AttackItem)
                {
                    player.getInventory().getEquipedAttackItem().setEquiped(false);
                }
                if(equipable instanceof DefenceItem)
                {
                    player.getInventory().getEquipedDefenceItem().setEquiped(false);
                }

                player.getInventory().equipItem(equipable);
                this.writer.println(player.getName() + " equipped " + key);
            }
            else
            {
                this.writer.println("Item isn't equipable");
            }
        }
        else
        {
            this.writer.println("Item not found");
        }
    }

    @Override
    public List<String> getPossibleArgs() throws GameStorageException
    {
        List<String> result = new ArrayList<>();

        List<Item> items = this.context.getGame().getPlayer().getInventory().getItems();

        for(Item item : items)
        {
            if(item instanceof Equipable)
            {
                result.add(item.getName());
            }
        }

        return result;
    }
}
