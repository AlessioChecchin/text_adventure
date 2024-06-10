package com.adventure.commands;

import com.adventure.models.Inventory;
import com.adventure.models.Player;
import com.adventure.models.Stats;
import com.adventure.models.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Command used to show player stats/inventory.
 */
public class CmdShow extends AbstractCommand
{

    @Override
    public void execute() throws InterruptedException
    {
        // Default 'show' shows the inventory.
        if(this.correctArgumentsNumber(0))
        {
            showStats();
            showInventory();
        }
        // Checking parameters.
        else if(this.correctArgumentsNumber(1))
        {
            String parameter = this.getArgs().get(0);

            if(parameter != null)
            {
                if(parameter.equalsIgnoreCase("stats"))
                {
                    this.showStats();
                }
                else if(parameter.equalsIgnoreCase("inventory"))
                {
                    this.showInventory();
                }
                else
                {
                    this.writer.println("Invalid parameter value");
                }
            }
            else
            {
                this.writer.println("Invalid parameter value");
            }
        }
    }

    /**
     * Shows inventory content.
     */
    private void showInventory()
    {
        Inventory inventory = this.context.getGame().getPlayer().getInventory();

        this.writer.println("Inventory content: ");

        for(Item item : inventory.getItems())
        {
            this.writer.println(item);
        }
        this.writer.println("Total weight: " + inventory.getCurrentWeight());
    }

    /**
     * Shows player stats.
     */
    private void showStats()
    {
        Player player = this.context.getGame().getPlayer();
        Stats stats = player.getStats();

        String englishPossessive = player.getName();
        if(player.getName().toLowerCase().endsWith("s"))
            englishPossessive += "'";
        else
            englishPossessive += "'s";

        this.writer.printf("%s stats:%n", englishPossessive);
        this.writer.printf("Max Hp: %d%n", stats.getMaxHp());
        this.writer.printf("Hp: %d%n", stats.getHp());
        this.writer.printf("Base attack: %d%n", stats.getBaseAttack());
        this.writer.printf("Base defence: %d%n%n", stats.getBaseDefense());
    }

    /**
     * @return 'stats' or 'inventory'
     */
    public List<String> getPossibleArgs()
    {
        ArrayList<String> result = new ArrayList<>();
        result.add("stats");
        result.add("inventory");
        return result;
    }
}
