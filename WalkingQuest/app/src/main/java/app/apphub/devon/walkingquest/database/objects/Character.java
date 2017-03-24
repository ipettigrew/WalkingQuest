package app.apphub.devon.walkingquest.database.objects;

/**
 * Created by Cole DeMan on 3/3/2017.
 */

public class Character extends DatabaseObject<Character>{

    //TODO:Camelcase plz

    private String name;
    private short level;
    private short baseSpeed;
    private short baseLuck;
    private int invId;
    private Inventory inv;
    private int shoesId;
    private long currency;
    //Assumed to be total exp from beginning of time

    private long exp;
    private long requiredExpForNextLevel;

    //TODO: add quest to character
    private int currentQuestId;
    private int questsCompleted;

    public Character(String name, int invId, int shoesId) {
        super();

        this.name = name;
        this.level = 0;

        this.invId = invId;
        this.currency = 0;

        this.exp = 0;
        this.questsCompleted = 0;

        this.shoesId = shoesId;

        this.baseLuck = 0;
        this.baseSpeed = 10;

        /**
         *  Set the current quest ID to be nothing
         *  */
        this.currentQuestId = -1;
    }

    public Character(int id, String name, short level, int invId, Inventory inv, long currency,
                     int shoesId, short baseSpeed, short baseLuck, long exp,
                     long requiredExpForNextLevel, int currentQuestId, int questsCompleted
    ) {
        super(id);

        this.name = name;
        this.level = level;
        this.exp = exp;

        this.invId = invId;
        this.inv = inv;
        this.currency = currency;
        this.shoesId = shoesId;

        this.baseSpeed = baseSpeed;
        this.baseLuck = baseLuck;
        this.requiredExpForNextLevel = requiredExpForNextLevel;

        this.currentQuestId = currentQuestId;
        this.questsCompleted = questsCompleted;
    }

    //TODO: make this actually do something.
    public boolean equals(Character c){
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLevel() {
        return level;
    }

    public long getCurrency() {
        return currency;
    }

    public long getExp() {
        return exp;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public int getInvId() {
        return invId;
    }

    public int getShoesId() {
        return shoesId;
    }

    public short getSpeed() {
        return baseSpeed;
    }

    public short getLuck() {
        return baseLuck;
    }

    public void setCurrentQuestId(int currentQuestId) {
        this.currentQuestId = currentQuestId;
    }

    public long getRequiredExp() {
        return requiredExpForNextLevel;
    }

    public int getCurrentQuestId() {
        return currentQuestId;
    }

    public void addExperience(long exp) {
        this.exp += exp;
        if(this.exp >= requiredExpForNextLevel) {
            this.level++;
            this.exp -= requiredExpForNextLevel;
            this.requiredExpForNextLevel += 500;    /** Just a temporary placeholder till we decide
                                                        on exactly what we want to do here  */
            /** Award stats after here  */
        }
    }

    private void addCurrency(long currency) {
        this.currency += currency;
    }

    public void addRewards(Reward r) {
        for(Item i : r.getEquipmentRewards()) {
            this.inv.addItem(i);
        }
        addExperience(r.getExpReward());
        addCurrency(r.getCurrencyReward());
    }
}
