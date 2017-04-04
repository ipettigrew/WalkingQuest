package app.apphub.devon.walkingquest.database.objects;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Jonathan McDevitt on 2017-03-18.
 */

public class Reward extends DatabaseObject {
    private long currencyReward;
    private long expReward;
    private Item[] equipmentRewards;

    /** Not sure if we'll need this right now but I'm putting it in just in case. I'm using it as a
     *  temporary reward constructor in Quest.java  */
    public Reward(long currencyReward, long expReward, Item[] equipmentRewards) {
        this.currencyReward = currencyReward;
        this.expReward = expReward;
        this.equipmentRewards = equipmentRewards;
    }

    public Reward(int id, long currencyReward, long expReward, Item[] equipmentRewards) {
        super(id);
        this.currencyReward = currencyReward;
        this.expReward = expReward;
        this.equipmentRewards = equipmentRewards;
    }

    public long getCurrencyReward() {
        return currencyReward;
    }

    public void setCurrencyReward(long currencyReward) {
        this.currencyReward = currencyReward;
    }

    public Item[] getEquipmentRewards() {
        return equipmentRewards;
    }

    public void setEquipmentRewards(Item[] equipmentRewards) {
        this.equipmentRewards = equipmentRewards;
    }

    public void setEquipmentRewards(Collection<Item> equipmentRewards) {
        this.equipmentRewards = (Item[])equipmentRewards.toArray();
    }

    public long getExpReward() {
        return expReward;
    }

    public void setExpReward(long expReward) {
        this.expReward = expReward;
    }

    @Override
    public boolean equals(DatabaseObject object) {
        return this.id == object.id;
    }

    @Override
    public String toString() {
        String message = getCurrencyReward() + " currency\n" + getExpReward() + " experience\n";

        message += "Items:\n\n";

        for (Item i: equipmentRewards) {
            message += i +"\n";
        }

        return message;
    }
}
