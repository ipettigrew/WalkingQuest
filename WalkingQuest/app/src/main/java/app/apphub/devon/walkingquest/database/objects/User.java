package app.apphub.devon.walkingquest.database.objects;

/**
 * A class to keep track of user data and related logic.
 * Created by cole_ on 2/24/2017.
 */

public class User extends DatabaseObject<User>{
    private int id;
    private String name;
    private long steps;
    private Character character;
    private int charID;
    //private int questsCompleted;

    /**
     * Constructor for the {@link User} object. This constructor is
     * genrally used for creating the obect from the databse.
     * @param id The id number of the object in the database.
     * @param name the name of the {@link User}.
     * @param steps The steps the user has taken.
     */
    public User(int id, String name, long steps, Character character, int charID){
        this.id = id;
        this.name = name;
        this.steps = steps;
        this.character = character;
        this.charID = charID;
    }

    /**
     * Creates a new user when suplied with a name;
     * @param name The name of the created user.
     */
    public User(String name) {
        this.name = name;
        this.steps = 0;
    }

    /**
     * Checks all data stored in 2 different users is equal.
     * @param u The {@link User} being compared.
     * @return True if users are equal, false if they are not.
     */
    public boolean equals(User u){
        boolean result = true;
        if (u.getId() != id) result = false;
        if (!u.getName().equals(name)) result = false;
        if (u.getSteps() != steps) result = false;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getCharID() {
        return charID;
    }

    public void setCharID(int charID) {
        this.charID = charID;
    }
}
