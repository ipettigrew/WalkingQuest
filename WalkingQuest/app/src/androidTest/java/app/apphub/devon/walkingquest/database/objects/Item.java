package app.apphub.devon.walkingquest.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract class for an item.
 *
 * Generally the kind of item that goes in a inventory.
 *
 * Created by Cole DeMan on 3/3/2017.
 */

public class Item extends DatabaseObject<Item>{
    int value;
    String name;
    //enum
    JSONObject attributes;

    public Item(int id, String name, int value, String json){
        super(id);
        this.name = name;
        this.value = value;
        try {
            attributes = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            attributes = new JSONObject();
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJSONString(){
        return attributes.toString();
    }

    public boolean fromJSONString(String json){
        try {
            this.attributes = new JSONObject(json);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            this.attributes = new JSONObject();
            return false;
        }
    }

    @Override
    public boolean equals(Item object) {
        return false;
    }
}
