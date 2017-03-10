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
    private int shoes_id;
    private Item shoes;
    private int pants_id;
    private Item pants;
    private int shirt_id;
    private Item shirt;
    //Assumed to be total exp from beginning of time
    private long exp;
    private int questsCompleted;

    //TODO: add quest to charcater
    private int activeQuest;

    public Character(int id, String name, short level, Inventory inv, int invId, long currency,
                     Item shoes, Item pants, Item shirt, long exp,
                     int questsCompleted, int shoes_id, int pants_id, int shirt_id, int activeQuest) {
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
        this.shoes_id = shoes_id;

        this.pants_id = pants_id;
        this.shirt_id = shirt_id;
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

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }

    public int getPants_id() {
        return pants_id;
    }

    public void setPants_id(int pants_id) {
        this.pants_id = pants_id;
    }

    public int getShirt_id() {
        return shirt_id;
    }

    public void setShirt_id(int shirt_id) {
        this.shirt_id = shirt_id;
    }

    public int getActiveQuest() { return activeQuest; }

    public void setActiveQuest(int activeQuest) { this.activeQuest = activeQuest; }
}
