package app.apphub.devon.walkingquest.database.objects;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Jonathan McDevitt on 2017-03-18.
 */

public class Reward extends DatabaseObject {
    private long currencyReward;
    private Item[] equipmentRewards;
    private long expReward;

    /** Not sure if we'll need this right now but I'm putting it in just in case. I'm using it as a
     *  temporary reward constructor in Quest.java  */
    public Reward() {
        super((int)(Math.random()*99999));
        currencyReward = 0;
        equipmentRewards = null;
        expReward = 0;
    }

    public Reward(int id, long currencyReward, Item[] equipmentRewards, long expReward) {
        super(id);
        this.currencyReward = currencyReward;
        this.equipmentRewards = equipmentRewards;
        this.expReward = expReward;
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
        return getCurrencyReward() + " currency\t" + getExpReward() + " experience";
    }
}
