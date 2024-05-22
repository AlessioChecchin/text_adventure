package com.adventure.controllers;

import com.adventure.components.Display;
import javafx.fxml.FXML;

public class RoomController implements BaseController
{
    @FXML
    private Display display;

    @FXML
    public void initialize()
    {

    }


    public void shutdown()
    {
        display.shutdown();
    }

}
