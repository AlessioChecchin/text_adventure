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
        //exercise
        usableItem.setAdditionalHp(10);
        usableItem.setAdditionalAttack(10);
        usableItem.setAdditionalDefence(10);
        final Field addHp = usableItem.getClass().getDeclaredField("hp");
        final Field addAtt = usableItem.getClass().getDeclaredField("atk");
        final Field addDef = usableItem.getClass().getDeclaredField("def");
        addHp.setAccessible(true);
        addAtt.setAccessible(true);
        addDef.setAccessible(true);

        //test
        assertEquals(10, addHp.get(usableItem), "problems with setHp method");
        assertEquals(10, addAtt.get(usableItem), "problems with setAtk method");
        assertEquals(10, addDef.get(usableItem), "problems with setDef method");
    }

    @Test
    void getTest(){
        //test
        assertEquals(0, usableItem.getHp(), "Problems with getHp method");
        assertEquals(0, usableItem.getAttack(), "Problems with getAttack method");
        assertEquals(0, usableItem.getDefence(), "Problems with getDefence method");
    }

    @Test
    void equalsTest(){
        //exercise
        UsableItem testUsable = new UsableItem("Key");

        //test
        assertEquals(testUsable, usableItem, "Problems with equals method");
    }
}