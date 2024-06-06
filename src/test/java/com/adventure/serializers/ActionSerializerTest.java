package com.adventure.serializers;

import com.adventure.models.Inventory;
import com.adventure.models.nodes.Action;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionSerializerTest {

    Action action;

    @BeforeEach
    void setUp() {
        action = new Action("test");
    }

    @Test
    void serializeTest() throws JsonProcessingException {
        //exercise
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Action.class, new ActionSerializer());
        mapper.registerModule(module);

        //  Create the json string
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(action);
        System.out.println(json);

        ObjectMapper mapper2 = new ObjectMapper();
        ObjectNode rootNode = mapper2.createObjectNode();
        rootNode.put("name", "test");

        String jsonString = mapper2.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        System.out.println(jsonString);

        //test
        assertEquals(json,jsonString);
    }
}