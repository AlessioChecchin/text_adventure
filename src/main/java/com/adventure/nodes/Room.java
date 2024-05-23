package com.adventure.nodes;

import com.adventure.models.items.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Room extends StoryNode
{
    private String name;
    private String description;
    private List<Item> items;

    @JsonCreator
    public Room(@JsonProperty("name") String name,@JsonProperty("description") String description)
    {
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
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Name     \t").append(name).append("\n");
        result.append("Description \t").append(description).append("\n");
        result.append("Items\n");
        for (Item item : items) {
            result.append(item.toString()).append("\n");
        }
        return result.toString();
    }

}
