package com.adventure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ApplicationContextTest
{
    ApplicationContext context;

    @BeforeEach
    void resetContext() throws NoSuchFieldException, IllegalAccessException {
        // Setting null in command parser through reflection.
        Field field = ApplicationContextProvider.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);

        context = ApplicationContextProvider.getInstance();
    }

    @Test
    void testValidConfiguration()
    {
        assert(context != null);
    }
}
