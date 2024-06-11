package com.adventure.models;

import com.adventure.exceptions.NotUsableItemException;
import com.adventure.models.items.UsableItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
     Player player;

    @BeforeEach
    void setUp()
    {
        player = new Player("test", new Inventory(30), new Stats(50,100,5,5));
    }

    @Test
    void testConstruction()
    {
        assertEquals("test", player.getName());
        assertEquals(new Stats(50,100,5,5), player.getStats());
        assertTrue(player.getAlive());
        assertTrue(player.getInventory().getItems().isEmpty());
        assertEquals(player.getInventory().getMaxWeight(), 30);
    }

    @Test
    void testHit()
    {

        int hp = player.getStats().getHp();
        int shield = player.getStats().getBaseDefense();

        // Should take 5 damage: 50 - (10 - 5)

        int damage1 = 10;
        hp = hp - (damage1 - shield);
        player.hit(damage1);
        assertEquals(player.getStats().getHp(), hp);

        // Should take 1 damage.
        int damage2 = 6;
        hp = hp - (damage2 - shield);
        player.hit(damage2);
        assertEquals(player.getStats().getHp(), hp);

        // Should take 0 damage, because the damage is lower than shield level.
        player.hit(4);
        assertEquals(player.getStats().getHp(), hp);
    }

    @Test
    void testHeal()
    {
        int heal = 5;
        int hp = player.getStats().getHp();

        hp += heal;
        player.heal(heal);
        assertEquals(player.getStats().getHp(), hp);

        // Should not go above max hp.
        player.heal(player.getStats().getMaxHp());
        assertEquals(player.getStats().getHp(), player.getStats().getMaxHp());
    }


    @Test
    void testFatalHit()
    {
        int damage = player.getStats().getHp() + player.getStats().getBaseDefense();
        player.hit(damage);

        assertEquals(player.getStats().getHp(), 0);
        assertFalse(player.getAlive());
    }


    @Test
    void testAboveFatalHit()
    {
        int hp = player.getStats().getHp();
        player.hit(hp + 100);

        assertEquals(player.getStats().getHp(), 0);
        assertFalse(player.getAlive());
    }

    @Test
    void useTest() throws NotUsableItemException {
        // Exercise.
        player.setStats(new Stats(5,5,5,5));
        int previousHp = player.getStats().getHp();
        Inventory inventory = new Inventory(10);
        UsableItem apple = new UsableItem("apple");
        apple.setAdditionalHp(10);
        inventory.addItem(apple);
        player.setInventory(inventory);
        player.getStats().setMaxHp(30);
        player.use("apple");

        // Test.
        assertEquals(previousHp + 10, player.getStats().getHp(), "Problems with use method for a player");
    }

    @Test
    void testEquals() {
        Player playerTest = new Player("test", new Inventory(30), new Stats(50,100,5,5));
        assertEquals(playerTest, player);
    }
}