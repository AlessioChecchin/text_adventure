package com.adventure.models.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AttackItemTest {

    AttackItem attackItem;
    @BeforeEach
    void setUp() {
        attackItem = new AttackItem("Sword");
    }

    @Test
    void setTest() throws IllegalAccessException, NoSuchFieldException{
        //exercise
        attackItem.setAdder(10);
        attackItem.setMultiplier(10.0);
        final Field addAtt = attackItem.getClass().getDeclaredField("additionalAttack");
        final Field attMulti = attackItem.getClass().getDeclaredField("attackMultiplier");
        addAtt.setAccessible(true);
        attMulti.setAccessible(true);

        //test
        assertEquals(10, addAtt.get(attackItem), "setAdditionalAttack has problems");
        assertEquals(10.0, attMulti.get(attackItem), "setAttackMultiplier has problems");
    }

    @Test
    void getTest(){
        //test
        assertEquals(1.0, attackItem.getMultiplier());
        assertEquals(0, attackItem.getAdder());
    }

    @Test
    void equalsTest(){
        //exercise
        AttackItem attTest = new AttackItem("Sword");

        //test
        assertEquals(attTest, attackItem);
    }
}