package app.apphub.devon.walkingquest.database.objects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Cole DeMan on 3/3/2017.
 */

public class Inventory extends DatabaseObject<Inventory> {

    int characterId;
    ArrayList<Item> inventory;

    public Inventory(int id, int characterId){
        super(id);  /** We should be randomly generating the ID in the DatabaseObject class, not
                        taking in an ID */
        this.characterId = characterId;
        inventory = new ArrayList<>();
    }

    public Inventory(int characterId){
        this.characterId = characterId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int id){
        characterId = id;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item){
        inventory.add(item);
    }

    public void addItems(Collection<Item> items) {
        for(Item i : items) {
            inventory.add(i);
        }
    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    //TODO: make this actually do something.
    @Override
    public boolean equals(Inventory object) {
        if(characterId == object.getCharacterId() && id == object.getId()) return true;
        return false;
    }
}
