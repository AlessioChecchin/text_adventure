package com.adventure;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Launcher {
    public static void main(String[] args)
    {
        if(args.length > 0 && (args[0].equals("-s") || args[0].equals("--setup")))
            setup();
        else
            Main.main(args);
    }

    private static void setup()
    {
        try (InputStream fis = Resources.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            Properties props = new Properties();
            props.load(fis);
            // Get config folder
            String configFolder = props.getProperty("config.folder");
            // Checks whether it exists or not
            if(!new File(configFolder).exists())
            {
                new File(configFolder).mkdirs();
                new File(configFolder + "saves/").mkdirs();
                new File(configFolder + "key.conf").createNewFile();
            }
        } catch (Exception e) {
            LogManager.getFormatterLogger().error(e + "\nCouldn't create setup files");
        }
    }
}
