package com.adventure.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class StringPropertyWriterTest extends FxTest {

    StringPropertyWriter stringPropertyWriter;
    StringProperty stringProperty;

    @BeforeEach
    void setUp() {
        stringProperty = new SimpleStringProperty();
        stringProperty.set("Test");
        stringPropertyWriter = new StringPropertyWriter(stringProperty);
    }

    @Test
    void writeTest() throws Exception{
        // Exercise.
        stringPropertyWriter.write("Test");

        // Test.
        assertEquals("Test", stringPropertyWriter.toString());

        // Exercise.
        stringPropertyWriter.write("Test", 0, 4);

        // Test.
        assertEquals("TestTest", stringPropertyWriter.toString());

        // Exercise.
        stringPropertyWriter.write(1);

        // Test.
        assertEquals("TestTest\u0001", stringPropertyWriter.toString());

        // Exercise.
        char[] test = {'T', 'e', 's', 't'};
        stringPropertyWriter.write(test, 0, 4);

        // Test.
        assertEquals("TestTest\u0001Test", stringPropertyWriter.toString());
    }

    @Test
    void appendTest(){
        // Exercise.
        stringPropertyWriter.append('t');
        // Test.
        assertEquals("t", stringPropertyWriter.toString());

        // Exercise.
        stringPropertyWriter.append("Test", 0, 4);
        // Test.
        assertEquals("tTest", stringPropertyWriter.toString());

        // Exercise.
        stringPropertyWriter.append("Test");
        // Test.
        assertEquals("tTestTest", stringPropertyWriter.toString());
    }

    @Test
    void equalsTest(){
        // Exercise.
        StringPropertyWriter strTest = new StringPropertyWriter(stringProperty);
        strTest.write("Test");
        stringPropertyWriter.write("Test");

        // Test.
        assertEquals(strTest.toString(), stringPropertyWriter.toString());

    }
}
abstract class FxTest {
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }
}