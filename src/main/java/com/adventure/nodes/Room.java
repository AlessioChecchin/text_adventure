package com.adventure.nodes;

import com.adventure.models.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room extends StoryNode
{
    private String name;
    private String description;
    private List<Item> items;
    private String backgroundPath;

    public Room(String name, String description)
    {
        super("views/room.fxml");
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
        this.backgroundPath = "";
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
        Objects.requireNonNull(items, "items cannot be null");
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

    public void setBackgroundPath(String backgroundPath)
    {
        Objects.requireNonNull(backgroundPath, "backgroundPath cannot be null");
        this.backgroundPath = backgroundPath;
    }

    public String getBackgroundPath()
    {
        return this.backgroundPath;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + name.hashCode();
        hash = hash * 31 + description.hashCode();
        hash = hash * 31 + items.hashCode();

        return hash;
    }
}
