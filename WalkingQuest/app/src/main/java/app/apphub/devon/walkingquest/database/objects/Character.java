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
    private int questsCompleted;

    //TODO: add quest to characater
    private int activeQuest;

    public Character(int id, String name, short level, Inventory inv, int invId, long currency,
                     Item shoes, Item pants, Item shirt, long exp,
                     int questsCompleted, int shoesId, int pantsId, int shirtId, int activeQuest) {
        super(id);
        this.name = name;
        this.level = level;
        this.inv = inv;
        this.currency = currency;
        this.shoes = shoes;
        this.pants = pants;
        this.shirt = shirt;
        this.exp = exp;
        this.questsCompleted = questsCompleted;
        this.invId = invId;
        this.shoesId = shoesId;

        this.pantsId = pantsId;
        this.shirtId = shirtId;
        this.activeQuest = activeQuest;
    }

    public Character(String name) {
        this.name = name;
        level = 0;
        currency = 0;
        exp = 0;
        questsCompleted = 0;
        activeQuest = -1;
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

    public void setLevel(short level) {
        this.level = level;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public long getCurrency() {
        return currency;
    }

    public void setCurrency(long currency) {
        this.currency = currency;
    }

    public Item getShoes() {
        return shoes;
    }

    public void setShoes(Item shoes) {
        this.shoes = shoes;
    }

    public Item getPants() {
        return pants;
    }

    public void setPants(Item pants) {
        this.pants = pants;
    }

    public Item getShirt() {
        return shirt;
    }

    public void setShirt(Item shirt) {
        this.shirt = shirt;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public void setQuestsCompleted(int questsCompleted) {
        this.questsCompleted = questsCompleted;
    }

    public int getInvId() {
        return invId;
    }

    public void setInvId(int invId) {
        this.invId = invId;
    }

    public int getshoesId() {
        return shoesId;
    }

    public void setshoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public int getPantsid() {
        return pantsId;
    }

    public void setpantsId(int pantsId) {
        this.pantsId = pantsId;
    }

    public int getshirtId() {
        return shirtId;
    }

    public void setshirtId(int shirtId) {
        this.shirtId = shirtId;
    }

    public int getActiveQuest() { return activeQuest; }

    public void setActiveQuest(int activeQuest) { this.activeQuest = activeQuest; }
}
