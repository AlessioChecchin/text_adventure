package com.adventure.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {

    Stats stats;
    @BeforeEach
    void setUp() {
        stats = new Stats(10,10,10,10);
    }

    @Test
    void setTest() {
        //exercise
        stats.setMaxHp(10);
        stats.setBaseAttack(10);
        stats.setBaseDefense(10);
        stats.setHp(10);

        //test
        assertEquals(10, stats.getHp(), "Problems with Hp of the player");
        assertEquals(10, stats.getBaseAttack(), "Problems with Attack of the player");
        assertEquals(10, stats.getBaseDefense(), "Problems with Defense of the player");
        assertEquals(10, stats.getMaxHp(), "Problems with MaxHp of the player");
    }

    @Test
    void testEquals() {
        //exercise
        Stats statsTest = new Stats(10,10,10,10);
        stats.setBaseAttack(20);
        statsTest.setBaseAttack(20);

        //test
        assertEquals(statsTest, stats, "Problems with equals method");
    }
}