package com.adventure.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    static Enemy enemy;
    @BeforeEach
    void setUp() {
        enemy = new Enemy(new Inventory(10), new Stats());
        enemy.setDefaultDialog("test");
    }

    @Test
    void equalsTest(){
        //exercise
        Enemy enemyTest = new Enemy(new Inventory(10), new Stats());
        enemyTest.setDefaultDialog("test");

        //test
        assertEquals(enemyTest, enemy);
    }
}