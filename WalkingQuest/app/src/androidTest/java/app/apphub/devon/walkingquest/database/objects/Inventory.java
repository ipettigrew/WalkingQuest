package app.apphub.devon.walkingquest.database.objects;

import java.util.ArrayList;

/**
 * Created by Cole DeMan on 3/3/2017.
 */

public class Inventory extends DatabaseObject<Inventory> {

    ArrayList<Item> inventory;

    public Inventory(int id, ArrayList<Item> inventory){
        super(id);
    }

    //TODO: make this actually do something.
    @Override
    public boolean equals(Inventory object) {
        return false;
    }
}
