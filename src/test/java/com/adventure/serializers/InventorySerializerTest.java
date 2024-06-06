package com.adventure.serializers;

import com.adventure.Resources;
import com.adventure.models.Game;
import com.adventure.models.Inventory;
import com.adventure.models.items.AttackItem;
import com.adventure.models.items.UsableItem;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jgrapht.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class InventorySerializerTest {

    Inventory inventory;
    @BeforeEach
    void setUp() {
        inventory = new Inventory(10);
        AttackItem atkItem = new AttackItem("test");
        UsableItem apple = new UsableItem("apple");
        inventory.addItem(apple);
        inventory.addItem(atkItem);
    }

    @Test
    void testSerialize() throws JsonProcessingException {
        //exercise
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Inventory.class, new InventorySerializer());
        mapper.registerModule(module);

        //  Create the json string
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inventory);
        System.out.println(json);

        ObjectMapper mapper2 = new ObjectMapper();
        SimpleModule module1 = new SimpleModule();
        module1.addSerializer(Inventory.class, new InventorySerializer());
        mapper2.registerModule(module1);
        Inventory inventoryTest = mapper2.readValue(json, Inventory.class);

        //test
        assertEquals(inventoryTest, inventory);
    }
}