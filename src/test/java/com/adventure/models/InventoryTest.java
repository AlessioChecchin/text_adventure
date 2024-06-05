package com.adventure.models;

import com.adventure.exceptions.TooMuchWeightException;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.DefenceItem;
import com.adventure.models.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;
    AttackItem attackItem;
    DefenceItem defenceItem;
    ArrayList<Item> list;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(100);
        attackItem = new AttackItem("Hands");
        defenceItem = new DefenceItem("Hands");
        list = new ArrayList<>(2);
    }

    @Test
    void getTest(){
        //Default tests
        assertEquals(100, inventory.getMaxWeight(), "Problems with the get or with constructor of inventory");
        assertEquals(attackItem, inventory.getEquipedAttackItem(), "Problems with setting the default attack Item");
        assertEquals(defenceItem, inventory.getEquipedDefenceItem(), "Problems with the default defence item");
        assertEquals(0, inventory.getCurrentWeight(), "Problems with default current weight");
    }

    @Test
    void setTest() throws IllegalAccessException, NoSuchFieldException{
        //exercise
        inventory.setMaxWeight(200);
        final Field field = inventory.getClass().getDeclaredField("maxWeight");
        field.setAccessible(true);

        inventory.setCurrentWeight(20);
        final Field field1 = inventory.getClass().getDeclaredField("currentWeight");
        field1.setAccessible(true);

        inventory.setItems(list);
        final Field field2 = inventory.getClass().getDeclaredField("items");
        field2.setAccessible(true);
        ArrayList<Item> listTest = new ArrayList<>(2);

        //test
        assertEquals(200, field.get(inventory), "Problems with setMaxWeight");
        assertEquals(20, field1.get(inventory), "Problems with setCurrentWeight");
        assertEquals(listTest, field2.get(inventory), "Problems with setItems");
    }

    @Test
    void equipTest() throws IllegalAccessException, NoSuchFieldException{
        //exercise
        AttackItem attackTest = new AttackItem("Sword");
        DefenceItem defenceTest = new DefenceItem("Shield");
        inventory.equipItem(new AttackItem("Sword"));
        inventory.equipItem(new DefenceItem("Shield"));

        //test
        assertEquals(attackTest, inventory.getEquipedAttackItem(), "Problems with equip method if getEquiped is correct");
        assertEquals(defenceTest, inventory.getEquipedDefenceItem(), "Problems with equip method if getEquiped is correct");

        //exercise
        inventory.unequipItem(Inventory.equipType.ATTACK);
        inventory.unequipItem(Inventory.equipType.DEFENSE);

        //test
        assertEquals(attackItem, inventory.getEquipedAttackItem(),"Problems with unequip method if getEquiped is correct");
        assertEquals(defenceItem, inventory.getEquipedDefenceItem(), "Problems with unequip method if getEquiped is correct");
    }

    @Test //test addItem dropItem and canAdd
    void manageItemTest(){
        //exercise
        Inventory testInventory = new Inventory(10);
        testInventory.setItems(list);
        DefenceItem defTest = new DefenceItem("Test");
        defTest.setWeight(11);
        DefenceItem defTest2 = new DefenceItem("Test");
        defTest2.setWeight(10);

        //test canAdd
        //assertFalse(testInventory.canAdd(defTest), "Problems with the max weight");
        //assertTrue(testInventory.canAdd(defTest2), "Problems with the max weight");

        //test addItem
        assertThrows(TooMuchWeightException.class,() -> testInventory.addItem(defTest),"Problems with Exception in addItem method");
        assertDoesNotThrow(() -> testInventory.addItem(defTest2), "Problem with the weight of the inventory");
        assertThrows(TooMuchWeightException.class,() -> testInventory.addItem(defTest2), "Problems with Exception in addItem method");

        //test dropItem
        assertDoesNotThrow(() -> testInventory.dropItem(defTest2), "Problem with the dropItem method of the inventory");
        assertThrows(NoSuchElementException.class,() -> testInventory.dropItem(defTest2), "Problems with Exception in dropItem method");

    }
}