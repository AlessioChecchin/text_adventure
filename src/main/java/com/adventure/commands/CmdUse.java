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
        CommandParser commandParser = CommandParser.getInstance();
        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        commandParser.disableAll();

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
        String key = this.getArgs().remove(0);
        Item item = player.getInventory().getItems().stream().filter(Item -> key.equals(Item.getName())).findFirst().orElse(null);
        this.writer.println(item.getName());
        player.use(item);
        this.writer.println(player.getStats().getHp());


    }
}
