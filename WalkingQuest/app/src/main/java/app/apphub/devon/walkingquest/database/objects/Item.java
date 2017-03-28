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
    int invID;
    String name;
    //enum
    JSONObject attributes;

    public Item(int id, String name, int invID, int value, String json){
        super(id);
        this.name = name;
        this.value = value;
        this.invID = invID;
        try {
            attributes = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            attributes = new JSONObject();

        }
    }

    public Item(String name, int value, int invID){
        this.name = name;
        this.value = value;
        this.invID = invID;
        attributes = new JSONObject();
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

    public int getInvID() {
        return invID;
    }

    public void setInvID(int invID) {
        this.invID = invID;
    }

    public JSONObject getAttributes() {
        return attributes;
    }

    public void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Item object) {

        return (
                this.id == object.id && this.getValue() == value
                && this.name.equals(object.getName())
                && toJSONString().equals(object.toJSONString())
        );
    }
}
