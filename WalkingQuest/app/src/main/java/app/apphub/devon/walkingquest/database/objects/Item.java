package app.apphub.devon.walkingquest.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

/** This class keeps track of data and logic for inventory items, such as shoes.
 *
 *  @author  Cole DeMan
 *  @author  Ian Pettigrew
 *  @version 1.4
 *  @see     app.apphub.devon.walkingquest.database.DatabaseHandler
 *  @see     app.apphub.devon.walkingquest.database.objects.DatabaseObject
 *  @since   2017-03-03
 */
public class Item extends DatabaseObject<Item> {
    private int value;             // The amount of currency and item is worth.
    private int invID;             // The ID of the inventory the item belongs to.
    private String name;           // The name of the item.
    private JSONObject attributes; // The modifiers the item grants when equipped.

    /**
     * Default constructor.
     */
    public Item() {
    }

    /**
     * Overloaded constructor specifying all instance except for attributes.
     *
     * @param id    The ID of an inventory item, as it appears in the database.
     * @param name  The name of the object.
     * @param invID The ID of the inventory the item belongs to.
     * @param value The value of an Item, in currency.
     */
    public Item(int id, String name, int invID, int value) {
        super(id);
        this.name = name;
        this.value = value;
        this.invID = invID;
        attributes = new JSONObject();
    }

    /**
     * Overloaded constructor specifying all instance variables.
     *
     * @param id    The ID of an inventory item, as it appears in the database.
     * @param name  The name of the object.
     * @param invID The ID of the inventory the item belongs to.
     * @param value The value of an Item, in currency.
     * @param json  A JSON object containing the attributes of the item.
     */
    public Item(int id, String name, int invID, int value, String json) {
        super(id);
        this.name = name;
        this.value = value;
        this.invID = invID;
        try {
            attributes = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Item(String name, int value, int invID) {
        this.name = name;
        this.value = value;
        this.invID = invID;
        attributes = new JSONObject();
    }

    /**
     * Accessor method for name.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method for name.
     *
     * @param name the name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method for the value of an item.
     *
     * @return the value of an item.
     */
    public int getValue() {
        return value;
    }

    /**
     * Accessor method for the value of an item.
     *
     * @param value the value of an item.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Accessor method for inventory ID.
     *
     * @return the inventory ID
     */
    public int getInvID() {
        return invID;
    }

    /**
     * Mutator method for inventory ID.
     *
     * @param invID the inventory ID.
     */
    public void setInvID(int invID) {
        this.invID = invID;
    }

    /**
     * toString method for JSON object which contains the item's attributes.
     *
     * @return toString object for attributes.
     */
    public String toJSONString(){
        return attributes.toString();
    }

    /**
     * Instantiates an attributes object with a new JSON object.
     *
     * @param json JSON object containing the attributes of an item.
     */
    public void fromJSONString(String json) {
        try {
            this.attributes = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accessor method for attributes.
     *
     * @return JSON object containing the attributes of an item.
     */
    public JSONObject getAttributes() {
        return attributes;
    }

    /**
     * Mutator method for attributes.
     *
     * @param attributes JSON object containing the attributes of an item.
     */
    public void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
    }

    /**
     * Compares two items to determine equivalence.
     *
     * @param item an item to be compared to this item for equivalence.
     * @return     true if the items are equivalent, false otherwise.
     */
    public boolean equals(Item item) {
        boolean result;

        if(this.name.equals(item.getName()) && this.value == item.getValue() && this.invID == item.getInvID()) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}
