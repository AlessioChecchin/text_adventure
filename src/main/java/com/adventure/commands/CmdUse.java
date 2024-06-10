package com.adventure.commands;

import com.adventure.config.Config;
import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.Enemy;
import com.adventure.models.Game;
import com.adventure.models.Player;
import com.adventure.models.RandomCollection;
import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import com.adventure.models.nodes.StoryNode;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Command used to consume an item.
 */
public class CmdUse extends AbstractCommand
{
    @Override
    public void execute() throws InterruptedException
    {
        // Check the correct number of parameters
        if(!this.correctArgumentsNumber(1))
        {
            this.writer.println("Invalid number of arguments! Usage: use <usable item name>");
            return;
        }

        Config currentConfig = this.context.getConfig();
        Game game = this.context.getGame();
        Player player = game.getPlayer();
        StoryNode node = game.getCurrentNode();

        // Player uses the item
        String key = this.getArgs().get(0);

        try
        {
            player.use(key);
            this.writer.println("You used " + key + "!");
        }
        catch (NotUsableItemException notUsable)
        {
            this.writer.println("Item is not usable!");
            return;
        }
        catch (NoSuchElementException noElement)
        {
            this.writer.println("Item not found!");
            return;
        }

        if(node instanceof Room currentRoom && currentRoom.getMonster() != null && player.isFighting())
        {
            Enemy monster = currentRoom.getMonster();

            // Monster set moves.
            RandomCollection<Object> decision = new RandomCollection<>()
                    .add(currentConfig.getMonsterAttackProbability(), CmdFight.Move.ATTACK)
                    .add(currentConfig.getMonsterDodgeProbability(), CmdFight.Move.DODGE);

            CmdFight.Move monsterChoice = (CmdFight.Move) decision.next();

            // Monster dodges check.
            if (monsterChoice == CmdFight.Move.DODGE)
            {
                if (!monster.useDodge()) monsterChoice = CmdFight.Move.ATTACK;
            }

            // If use is call in a fight, monster can attack.
            if(monster.getAlive())
            {
                if (monsterChoice == CmdFight.Move.ATTACK)
                {
                    int damage = monster.getAttackDamage();
                    int inflictedDamage = player.hit(damage);
                    this.writer.println("Monster hits " + player.getName() + " and damage is " + inflictedDamage + "hp");
                }
                else
                {
                    this.writer.println("Monster dodges the attack!");
                }

            }
        }
    }

    /**
     * @return all possible items the player can use
     */
    public List<String> getPossibleArgs()
    {
        Player player = context.getGame().getPlayer();
        List<Item> item = player.getInventory().getItems().stream().filter(Item -> Item.getClass().equals(UsableItem.class)).toList();
        ArrayList<String> possibleItems = new ArrayList<>();
        for(Item i : item)
            possibleItems.add(i.getName());
        return possibleItems;
    }

    private RandomCollection<Object> decision;
}
