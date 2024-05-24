package com.adventure.nodes;

import com.adventure.models.items.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Room extends StoryNode
{

    /**
     * Default constructor
     * @param name Name of the room
     * @param description Description of the room
     */
    @JsonCreator
    public Room(@JsonProperty("name") String name,@JsonProperty("description") String description)
    {
        this.name = name;
        this.description = description;
    }

    /**
     * ID getter
     * @return String current id of the room
     */
    @Override
    @JsonIgnore
    public String getID() { return "room_"+System.identityHashCode(this); }

    /**
     * Name getter
     * @return String name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Description getter
     * @return String description of the room
     */
    public String getDescription() {
        return description;
    }

    /**
     * Name setter
     * @param name name of the room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description setter
     * @param description description of the room
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Items list getter
     * @return List of all items in the room
     */
    public List<Item> getItems()
    {
        return items;
    }

    /**
     * Items list setter
     * @param items List of items to place in the room
     */
    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    /**
     * To string method
     * @return name, description, number of items
     */
    @Override
    public String toString()
    {
        return name + ", " + description + ", " + items.size();
    }


    /**
     * Name of the room
     */
    private String name;
    /**
     * Description of the room
     */
    private String description;
    /**
     * List of items in the room
     */
    private List<Item> items;
}
