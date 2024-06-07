package com.adventure.models.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class UsableItemTest {

    UsableItem usableItem;
    @BeforeEach
    void setUp() {
      usableItem = new UsableItem("Key");
    }

    @Test
    void setTest() throws NoSuchFieldException, IllegalAccessException {
        // Exercise.
        usableItem.setAdditionalHp(10);
        usableItem.setAdditionalAttack(10);
        usableItem.setAdditionalDefence(10);
        final Field addHp = usableItem.getClass().getDeclaredField("hp");
        final Field addAtt = usableItem.getClass().getDeclaredField("atk");
        final Field addDef = usableItem.getClass().getDeclaredField("def");
        addHp.setAccessible(true);
        addAtt.setAccessible(true);
        addDef.setAccessible(true);

        // Test.
        assertEquals(10, addHp.get(usableItem), "problems with setHp method");
        assertEquals(10, addAtt.get(usableItem), "problems with setAtk method");
        assertEquals(10, addDef.get(usableItem), "problems with setDef method");
    }

    @Test
    void getTest(){
        // Test.
        assertEquals(0, usableItem.getHp(), "Problems with getHp method");
        assertEquals(0, usableItem.getAttack(), "Problems with getAttack method");
        assertEquals(0, usableItem.getDefence(), "Problems with getDefence method");
    }

    @Test
    void equalsTest(){
        // Exercise.
        UsableItem testUsable = new UsableItem("Key");

        // Test.
        assertEquals(testUsable, usableItem, "Problems with equals method");
    }
}