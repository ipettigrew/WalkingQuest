package app.apphub.devon.walkingquest.database.objects;

/**
 * Created by Cole DeMan on 3/3/2017.
 */

public class Character extends DatabaseObject<Character>{

    //TODO:Camelcase plz

    private String name;
    private short level;
    private Inventory inv;
    private int invId;
    private long currency;
    private int shoesId;
    //Assumed to be total exp from beginning of time

    private long exp;
    private long requiredExpForNextLevel;

    //TODO: add quest to character
    private int currentQuestId;
    private int questsCompleted;

    public Character(String name) {
        /** Something we should consider is enforcing unique IDs, for obvious reasons. We can easily
         *  do this if, whenever we create a DatabaseObject, we assign the ID with a call to the
         *  DatabaseHandler, which generates a unique ID for us to use.*/
        super();

        /** Randomize ID*/
        int id = (int)(Math.random()*99999);

        /** while(database.getCharacterById() != null) {
         *      id = (int)(Math.random());
         *  }*/
        this.id = id;

        /**
         *  Refactor
         *
         *  We don't need a constructor to take in all these values, that's really gross. Instead,
         *  we should only have the user enter a name for the character. We can generate a random
         *  identification number for database purposes.
         *  */

        this.name = name;
        this.level = 0;

        /** Frankly we shouldn't need this. We should just use an ID number to access.  */
        this.inv = new Inventory((int)(Math.random()*99999), this.id);  /** Five-digit random is
                                                                            sufficient*/
        this.currency = 0;

        this.exp = 0;
        this.questsCompleted = 0;

        /**
         *  Do we really need these? We should be able to access the ID from the Item/Inventory
         *  class itself with a get method or something similar, we shouldn't require extra space in
         *  the Character class to use them
         *  */
        this.invId = this.inv.id;


        /**
         *  this.shoesId = getEmptyShoesId();
         *  */

        /**
         *  Set the current quest ID to be nothing
         *  */
        this.currentQuestId = -1;
    }

    public Character(int id, String name, short level, int invId, long currency, int shoesId,
                     long exp, long requiredExpForNextLevel, int currentQuestId, int questsCompleted)
    {
        super(id);
        this.name = name;
        this.level = level;
        this.invId = invId;
        this.currency = currency;
        this.shoesId = shoesId;
        this.exp = exp;
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

    public void setCurrentQuestId(int currentQuestId) {
        this.currentQuestId = currentQuestId;
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
