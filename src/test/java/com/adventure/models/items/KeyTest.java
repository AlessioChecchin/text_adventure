package com.adventure.models.items;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    static Key key;
    @BeforeAll
    static void setup(){
        key = new Key("test");
    }
    @Test
    void testEquals() {
        Key keyTest = new Key("test");
        assertEquals(keyTest, key);
    }
}