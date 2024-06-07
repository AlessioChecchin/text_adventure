package com.adventure.models;

import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.items.UsableItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
     Player player;

    @BeforeEach
    void setUp() {
        player = new Player("test", new Inventory(30), new Stats(5,5,5,5));
    }

    @Test
    void useTest() throws NotUsableItemException {
        // Exercise.
        int previousHp = player.getStats().getHp();
        Inventory inventory = new Inventory(10);
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        inventory.addItem(apple);
        player.setInventory(inventory);
        player.getStats().setMaxHp(30);
        player.use("apple");

        // Test.
        assertEquals(previousHp +10, player.getStats().getHp(), "Problems with use method for a player");
    }

    @Test
    void testEquals() {
        Player playerTest = new Player("test", new Inventory(30), new Stats(5,5,5,5));
        assertEquals(playerTest, player);
    }
}