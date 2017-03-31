package app.apphub.devon.walkingquest.database.objects;

/**
 *  Class for the Character database object.
 *
 *  @author  Cole DeMan
 *  @author  Devon Rimmington
 *  @author  Ian Pettigrew
 *  @author  Adrian Metzler
 *  @author  Jonathan McDevitt
 *  @version 1.9
 *  @see     app.apphub.devon.walkingquest.database.DatabaseHandler
 *  @see     app.apphub.devon.walkingquest.database.objects.DatabaseObject
 *  @since   2017-03-03
 */
public class Character extends DatabaseObject<Character>{
    private String name;                  // The name of the character.
    private short level;                  // The current level of the character.
    private short baseSpeed;              // The character's Speed, without any modifiers.
    private short baseLuck;               // The character's Luck, without any modifiers.
    private int invId;                    // TODO: Merge Inventory with Character.
    private Inventory inv;                // TODO: Merge Inventory with Character.
    private int shoesId;                  // The ID denoting the shoes currently equipped.
    private long currency;                // The amount of currency the character has available.
    private long exp;                     // Total EXP the character has accumulated.
    private long requiredExpForNextLevel; // Remaining EXP the character needs to level up.
    private int currentQuestId;           // The ID denoting the quest the current quest.
    private int questsCompleted;          // The number of quests the character has completed.
    private short numRewards;             //TODO: add this change to database handler

    public Character(String name, int invId, int shoesId) {
        super();

        this.name = name;
        this.level = 1;

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
        this.numRewards = 0;
    }

    public Character(String name) {
        //super();

        this.name = name;
        this.level = 1;

        this.invId = -1;
        this.currency = 0;

        this.exp = 0;
        this.questsCompleted = 0;

        this.shoesId = 0;

        this.baseLuck = 0;
        this.baseSpeed = 10;

        /**
         *  Set the current quest ID to be nothing
         *  */
        this.currentQuestId = -1;
        this.numRewards = 0;
    }

    public Character(int id, String name, short level, int invId, Inventory inv, long currency,
                     int shoesId, short baseSpeed, short baseLuck, long exp,
                     long requiredExpForNextLevel, int currentQuestId, int questsCompleted, short numRewards
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
        this.numRewards = numRewards;
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

    public void setInvId(int id){
        invId = id;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
        setInvId(inv.getId());
    }

    public int getShoesId() {
        return shoesId;
    }
    public void setShoesId(int shoesId){ this.shoesId = shoesId; }

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

    public short getNumRewards(){ return numRewards; }
    public void setNumRewards(short numRewards){ this.numRewards = numRewards; }

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

    public void addItemToInventory(Item item){
        inv.addItem(item);
    }
}
