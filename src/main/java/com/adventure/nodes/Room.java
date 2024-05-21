package com.adventure.nodes;

import com.adventure.models.Item;

import java.util.List;

public class Room extends StoryNode
{
    private String name;
    private String description;
    private List<Item> items;

    public Room(String name, String description)
    {
        super("views/room.fxml");
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    @Override
    public boolean equals(Object room){
        if (room == this) {
            return true;
        }

        if (room.getClass() != this.getClass()) return false;

        Room room1 = (Room) room;
        return (room1.getName().equals(this.name) && (room1.getDescription().equals(this.description))) /*&& (room1.getItems().equals(this.items)))*/;
    }

}
