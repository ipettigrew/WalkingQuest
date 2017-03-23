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
    private Item shoes;
    private int pantsId;
    private Item pants;
    private int shirtId;
    private Item shirt;
    //Assumed to be total exp from beginning of time

    private long exp;
    private long requiredExpForNextLevel;

    //TODO: add quest to character
    private Quest currentQuest;
    private int questsCompleted;

    public Character(String name, int shoesId, int pantsId, int shirtId) {
        /** Something we should consider is enforcing unique IDs, for obvious reasons. We can easily
         *  do this if, whenever we create a DatabaseObject, we assign the ID with a call to the
         *  DatabaseHandler, which generates a unique ID for us to use.*/
        super((int)(Math.random()*99999));

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

        /**
         *  Something we should explore is the idea of an "Empty" item - i.e. a placeholder object
         *  rather than setting it to null as I have.
         *
         *  Also same comment as above re: the inventory - we should only be using IDs to access,
         *  not have fields for each item.
         *  */
        this.shoes = null;
        this.pants = null;
        this.shirt = null;

        this.exp = 0;
        this.questsCompleted = 0;

        /**
         *  Do we really need these? We should be able to access the ID from the Item/Inventory
         *  class itself with a get method or something similar, we shouldn't require extra space in
         *  the Character class to use them
         *  */
        this.invId = this.inv.id;
        this.shoesId = shoesId;

        this.pantsId = pantsId;
        this.shirtId = shirtId;

        /**
         *  How do we get the current quest from the database? We can use the 'activeQuest' int as
         *  an identifier to pull it from the DB. The problem is that the database is not static -
         *  in order to access getQuestById, we need to create an instance of the database. How do
         *  we access these functions?
         *  */
//        this.currentQuest = DatabaseHandler.getQuestById(activeQuest);
    }

    public Character(int id, String name, short level, Inventory inv, int invId, long currency,
                     int shoesId, Item shoes, int pantsId, Item pants, int shirtId, Item shirt,
                     long exp, long requiredExpForNextLevel, Quest currentQuest, int questsCompleted)
    {
        super(id);
        this.name = name;
        this.level = level;
        this.inv = inv;
        this.invId = invId;
        this.currency = currency;
        this.shoesId = shoesId;
        this.shoes = shoes;
        this.pantsId = pantsId;
        this.pants = pants;
        this.shirtId = shirtId;
        this.shirt = shirt;
        this.exp = exp;
        this.requiredExpForNextLevel = requiredExpForNextLevel;
        this.currentQuest = currentQuest;
        this.questsCompleted = questsCompleted;
    }

    public Character(String name) {
        this.name = name;
        level = 0;
        currency = 0;
        exp = 0;
        questsCompleted = 0;
        currentQuest = null;
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

    public Inventory getInv() {
        return inv;
    }

    public long getCurrency() {
        return currency;
    }

    public Item getShoes() {
        return shoes;
    }

    public Item getPants() {
        return pants;
    }

    public Item getShirt() {
        return shirt;
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

    public int getPantsId() {
        return pantsId;
    }

    public int getShirtId() {
        return shirtId;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public int getCurrentQuestId() {
        return currentQuest.getId();
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
