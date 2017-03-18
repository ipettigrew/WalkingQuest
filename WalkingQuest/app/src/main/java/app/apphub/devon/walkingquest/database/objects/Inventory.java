package app.apphub.devon.walkingquest.database.objects;

import java.util.ArrayList;

/**
 * Created by Cole DeMan on 3/3/2017.
 */

public class Inventory extends DatabaseObject<Inventory> {

    int characterId;
    ArrayList<Item> inventory;

    public Inventory(int id, int characterId, ArrayList<Item> inventory){
        super(id);
        this.characterId = characterId;
        this.inventory = inventory;
    }

    public Inventory(int id, int characterId){
        super(id);
        this.characterId = characterId;
    }

    public Inventory(int characterId){
        this.characterId = characterId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void addItem(Item item){
        inventory.add(item);
    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    //TODO: make this actually do something.
    @Override
    public boolean equals(Inventory object) {
        return false;
    }
}
