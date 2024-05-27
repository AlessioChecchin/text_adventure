package com.adventure;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Resources
{
    public static BackgroundImage getBackground(String source)
    {
        URL imageUrl = Resources.class.getResource(source);

        if (imageUrl != null)
        {
            return new BackgroundImage(
                    new Image(imageUrl.toString()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    // Set to cover
                    new BackgroundSize(0,0, false, false, false, true)
            );
        }

        return null;
    }

}
