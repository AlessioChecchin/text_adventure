package com.adventure.models.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class DefenceItemTest {
    DefenceItem defenceItem;
    @BeforeEach
    void setUp() {
        defenceItem = new DefenceItem("Shield");
    }

    @Test
    void setTest() throws IllegalAccessException, NoSuchFieldException{
        //exercise
        defenceItem.setAdder(10);
        defenceItem.setMultiplier(10.0);
        final Field addDef = defenceItem.getClass().getDeclaredField("additionalDefence");
        final Field defMulti = defenceItem.getClass().getDeclaredField("defenceMultiplier");
        addDef.setAccessible(true);
        defMulti.setAccessible(true);

        //test
        assertEquals(10, addDef.get(defenceItem), "setAdditionalDefence has problems");
        assertEquals(10.0, defMulti.get(defenceItem), "setDefenceMultiplier has problems");
    }

    @Test
    void getTest(){
        //test
        assertEquals(1.0, defenceItem.getMultiplier());
        assertEquals(0, defenceItem.getAdder());
    }

    @Test
    void equalsTest(){
        //exercise
        DefenceItem defTest = new DefenceItem("Shield");

        //test
        assertEquals(defTest, defenceItem);
    }

}