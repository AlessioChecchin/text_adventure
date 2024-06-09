package com.adventure.models.nodes;

import com.adventure.deserializers.RoomDeserializer;
import com.adventure.models.Enemy;
import com.adventure.models.Inventory;
import com.adventure.models.Stats;
import com.adventure.models.items.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(using = RoomDeserializer.class)
public class Room extends StoryNode
{
    /**
     * Constructor
     * @param name Name of the room
     * @param description Description of the room
     * @apiNote The ID of the class is set using the IdManager
     */
    public Room(@JsonProperty("name")  String name, @JsonProperty("description") String description)
    {
        super("views/room.fxml");
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.backgroundPath = "";
        this.monster = null;
        this.completed = false;
        this.setID();
    }

    /**
     * Constructor for deserialization
     * @param name Name of the room
     * @param description Description of the room
     * @param ID int ID of the class
     */
    @JsonCreator
    public Room(@JsonProperty("name")  String name, @JsonProperty("description") String description,@JsonProperty("numericID") int ID)
    {
        super("views/room.fxml");
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.backgroundPath = "";
        this.monster = null;
        this.completed = false;
        this.setID(ID);
    }

    //
    // GETTERS.
    //

    /**
     * Name getter
     * @return String name of the room
     */
    public String getName()
    {
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
     * Items list getter
     * @return List of all items in the room
     */
    public List<Item> getItems()
    {
        return items;
    }

    /**
     * Background path getter.
     * @return Background path relative to resources.com.adventure.
     */
    public String getBackgroundPath()
    {
        return this.backgroundPath;
    }

    /**
     * Monster getter
     * @return Monster inside the room
     */
    public Enemy getMonster()
    {
        return monster;
    }

    /**
     * Completed getter.
     * @return True if the room is completed, false otherwise.
     */
    public boolean getCompleted()
    {
        return this.completed;
    }

    //
    // SETTERS.
    //

    /**
     * Name setter
     * @param name name of the room
     */
    public void setName(String name)
    {
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
     * Items list setter
     * @param items List of items to place in the room
     */
    public void setItems(List<Item> items)
    {
        Objects.requireNonNull(items, "items cannot be null");
        this.items = items;
    }

    /**
     * Background path setter.
     * @param backgroundPath Background path relative to resources.com.adventure.
     */
    public void setBackgroundPath(String backgroundPath)
    {
        Objects.requireNonNull(backgroundPath, "backgroundPath cannot be null");
        this.backgroundPath = backgroundPath;
    }

    /**
     * Monster setter
     * @param monster Monster to set
     */
    public void setMonster(Enemy monster)
    {
        this.monster = monster;
    }

    /**
     * Completed setter.
     * @param completed Sets if the room is completed.
     */
    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    //
    // OTHERS.
    //

    /**
     * Remove an item from the room
     * @param item Item to be removed
     * @return The removed item.
     */
    public Item removeItem(Item item)
    {
        if(! this.items.contains(item))
            return null;
        this.items.remove(item);
        return item;
    }

    /**
     * To string method
     * @return name, description, number of items
     */
    @Override
    public String toString()
    {
        return String.format("name: %s, description: %s", name, description);
    }

    //
    // VARIABLES.
    //

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

    /**
     * Monster entity inside the room
     */
    private Enemy monster;

    /**
     * Default background image path relative to resources.com.adventure.
     */
    private String backgroundPath;

    /**
     * Flag if the room is completed.
     */
    private boolean completed;
}
