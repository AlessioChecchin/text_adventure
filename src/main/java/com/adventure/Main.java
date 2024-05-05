package com.adventure;

import com.adventure.paths.StartState;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ApplicationContext applicationContext = DefaultApplicationContext.getInstance();
        applicationContext.load(StartState.class);
        applicationContext.nextNode();
    }
}
