package com.adventure.nodes;

import com.adventure.models.items.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        super("views/room.fxml");
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
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
        Objects.requireNonNull(items, "items cannot be null");
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


    @Override
    public boolean equals(Object room)
    {
        if (room == this) {
            return true;
        }

        if (room.getClass() != this.getClass()) return false;

        Room room1 = (Room) room;
        return (room1.getName().equals(this.name) && (room1.getDescription().equals(this.description))) /*&& (room1.getItems().equals(this.items)))*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, items);
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
