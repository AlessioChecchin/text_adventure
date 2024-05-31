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

    @BeforeAll
    static void initJfxRuntime(){
        Platform.startup(() -> {});
    }
    @BeforeEach
    void setUp() {
        stringProperty = new SimpleStringProperty();
        stringProperty.set("Test");
        stringPropertyWriter = new StringPropertyWriter(stringProperty);
    }

    @Test
    void writeTest() throws Exception{
        //exercise
        stringPropertyWriter.write("Test");

        //test
        assertEquals("Test", stringPropertyWriter.toString());

        //exercise
        stringPropertyWriter.write("Test", 0, 4);

        //test
        assertEquals("TestTest", stringPropertyWriter.toString());

        //exercise
        stringPropertyWriter.write(1);

        //test
        assertEquals("TestTest\u0001", stringPropertyWriter.toString());

        //exercise
        char[] test = {'T', 'e', 's', 't'};
        stringPropertyWriter.write(test, 0, 4);
        System.out.println(stringPropertyWriter.toString());

        //test
        assertEquals("TestTest\u0001Test", stringPropertyWriter.toString());
    }

    @Test
    void appendTest(){
        //exercise
        stringPropertyWriter.append('t');
        //test
        assertEquals("t", stringPropertyWriter.toString());

        //exercise
        stringPropertyWriter.append("Test", 0, 4);
        //test
        assertEquals("tTest", stringPropertyWriter.toString());

        //exercise
        stringPropertyWriter.append("Test");
        //test
        assertEquals("tTestTest", stringPropertyWriter.toString());
    }

    @Test
    void equalsTest(){
        //exercise
        StringPropertyWriter strTest = new StringPropertyWriter(stringProperty);
        strTest.write("Test");
        stringPropertyWriter.write("Test");

        //test
        assertEquals(strTest.toString(), stringPropertyWriter.toString());

    }
}
abstract class FxTest {
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }
}