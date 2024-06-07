package com.adventure.commands;

import com.adventure.config.ApplicationContextProvider;
import com.adventure.exceptions.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CmdUseTest extends CmdAttackTest {

    Command command;

    @BeforeEach
    void setUp() {
        command = new CmdUse();
    }

    @Test
    void execute() throws InterruptedException, ConfigurationException {

        ApplicationContextProvider applicationContextProvider = ApplicationContextProvider.getInstance();
        this.setTestContext(applicationContextProvider);
        command.setContext(applicationContextProvider);
        ArrayList<String> args = new ArrayList<>(1);
        args.add("apple");
        command.setArgs(args);
        command.setWriter(new PrintWriter(System.out));
        command.execute();
        System.out.flush();

    }
}