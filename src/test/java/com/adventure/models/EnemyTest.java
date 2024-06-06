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
        enemy = new Enemy(new Inventory(10), new Stats(), "test");
        enemy.setDefaultDialog("test");
        apple = new UsableItem("apple");
        enemy.getInventory().addItem(apple);
    }

    @Test
    void equalsTest(){
        //exercise
        Enemy enemyTest = new Enemy(new Inventory(10), new Stats(), "test");
        enemyTest.setDefaultDialog("test");

        //test
        assertEquals(enemyTest, enemy);
    }

    @Test
    void dropTest(){
        //exercise
        Room room = new Room("test", "test");
        enemy.getStats().setHp(0);
        enemy.drop(room);

        //test
        assertTrue(room.getItems().contains(apple));
    }
}