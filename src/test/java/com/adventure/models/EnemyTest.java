package com.adventure.models;

import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    static Enemy enemy;
    UsableItem apple;
    @BeforeEach
    void setUp() {
        enemy = new Enemy(new Inventory(10), new Stats(5, 5, 5, 5), "test");
        enemy.setDefaultDialog("test");
        apple = new UsableItem("apple");
        enemy.getInventory().addItem(apple);
    }

    @Test
    void equalsTest(){
        // Exercise.
        Enemy enemyTest = new Enemy(new Inventory(10), new Stats(5,5,5,5), "test");
        enemyTest.setDefaultDialog("test");
        enemyTest.getInventory().addItem(apple);

        // Test.
        assertEquals(enemyTest, enemy);
    }

    @Test
    void dropTest(){
        // Exercise.
        Room room = new Room("test", "test");
        enemy.getStats().setHp(0);
        enemy.drop(room);

        // Test.
        assertTrue(room.getItems().contains(apple));
    }
}