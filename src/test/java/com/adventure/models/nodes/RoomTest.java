package com.adventure.models.nodes;

import com.adventure.models.nodes.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room room;
    Room testRoom;
    @BeforeEach
    public void setUp() {
        room = new Room("Test", "Test");
        testRoom = new Room("Test", "Test");

    }

    @Test
    void equalsTest(){
       assertEquals(room, room, "Problems with equals method");
    }

    @Test
    void getTest(){
        // Exercise.
        String test = "Test";
        String notTest = "test";

        // Test.
        assertEquals(test, room.getName(), "Problems with get methods");
        assertEquals(test, room.getDescription(),"Problems with get methods");
        assertNotEquals(notTest, room.getName(), "Problems with get methods");
        assertNotEquals(notTest, room.getDescription(), "Problems with get methods");
    }

    @Test
    void setTest() throws IllegalAccessException, NoSuchFieldException {
        // Exercise.
        room.setName("test");
        final Field field = room.getClass().getDeclaredField("name");
        field.setAccessible(true);

        room.setDescription("test");
        final Field field1 = room.getClass().getDeclaredField("description");
        field1.setAccessible(true);

        // Test.
        assertEquals(field.get(room),"test", "Problems with set methods"); //setName test
        assertEquals(field1.get(room),"test", "Problems with set methods"); //setDescription test
    }
}