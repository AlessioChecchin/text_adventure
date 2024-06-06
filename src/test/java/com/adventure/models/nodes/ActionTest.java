package com.adventure.models.nodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    Action action;

    @BeforeEach
    void setUp() {
        action = new Action("test");
    }

    @Test
    void testEquals() {
        Action actionTest = new Action("test");
        assertEquals(actionTest, action, "Problems with equals method of Action");
    }
}