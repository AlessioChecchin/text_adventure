package com.adventure.models;

import com.adventure.models.items.Item;
import com.adventure.models.items.UsableItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Player extends Entity
{
    private String name;


    public Player(@JsonProperty("name") String name,@JsonProperty("inventory") Inventory inventory,@JsonProperty("stats") Stats stats)
    {
        super(inventory, stats);
        stats.setHp(20);
        stats.setBaseAttack(3);
        stats.setBaseDefense(3);

        this.setName(name);
    }

    @JsonIgnore
    public void setName(String name)
    {
        Objects.requireNonNull(name, "Can't set a null name");
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String use(Item item){
        UsableItem usableItem = (UsableItem) item;
        String result = "";
        if(usableItem.getHp() != 0){
            this.heal(usableItem.getHp());
            result += usableItem.getHp() + " HP ";
        }
        if(usableItem.getAttack() != 0){
            int newAttack = usableItem.getAttack() + this.getStats().getBaseAttack();
            this.getStats().setBaseAttack(newAttack);
            result += usableItem.getAttack() + " ATK ";
        }
        if(usableItem.getDefence() != 0){
            int newDefence = usableItem.getDefence() + this.getStats().getBaseDefense();
            this.getStats().setBaseDefense(newDefence);
            result += usableItem.getDefence() + " DEF ";
        }
        this.getInventory().getItems().remove(item);
        return result;
    }
}
