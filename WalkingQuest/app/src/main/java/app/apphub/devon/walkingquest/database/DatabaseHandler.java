package app.apphub.devon.walkingquest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.apphub.devon.walkingquest.database.objects.*;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.database.objects.Character;

/**
 * Handles the database for the app. It is used for saving and retrieving users
 * and quests.
 *
 * @author Cole DeMan cole@coledeman.com
 * @version 1.0                 (current version number of program)
 * @since 1.0          (the version of the package this class was first added to)
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "walkingQuest";

    //both
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    //user
    private static final String USER_TABLE = "users";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_QUESTS_COMPLETED = "questsCompleted";

    //iventory
    private static final String INV_TABLE = "inventory";
    private static final String CHARACTER_ID = "character_id";

    //item
    private static final String ITEM_TABLE = "items";
    private static final String INV_ID = "inventory_id";
    private static final String ITEM_ATTRIBUTES = "attributes";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_VALUE = "value";

    //quest
    private static final String DESCRIPTION = "description";
    private static final String QUEST_TABLE = "quests";
    private static final String KEY_ACTIVE_STEPS = "active_steps";
    private static final String KEY_STEP_GOAL = "step_goal";
    private static final String KEY_QUEST_COMPLETED = "questCompleted";
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LEVEL_REQUIREMENT = "level_requirement";


    //character
    private static final String CHARACTER_TABLE = "character";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_INVENTORY_ID = "invId";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_SHOES_ID = "shoesId";
    private static final String KEY_ACTIVEQUEST = "active_quest";
    private static final String KEY_CHARACTER_EXP = "xp";
    private static final String KEY_REQUIRED_EXP = "reqExp";
    private static final String KEY_SPEED_ID = "speed";
    private static final String KEY_LUCK_ID = "luck";

    private static DatabaseHandler sInstance;

    /**
     * Constructor for the Database handler
     *
     * @param context the application context;
     */
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    // http://stackoverflow.com/questions/13641250/sqlite-delete-cascade-not-working
    @Override
    public void onConfigure(SQLiteDatabase database) {
        database.setForeignKeyConstraintsEnabled(true);

    }

   /* @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_DATABASE = "CREATE TABLE " + USER_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUESTS_COMPLETED + " INTEGER,"
                + KEY_STEPS + " INTEGER" + ")";
        db.execSQL(CREATE_USER_DATABASE);
        String CREATE_QUEST_DATABASE = "CREATE TABLE "
                + QUEST_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + KEY_ACTIVE_STEPS + " INTEGER,"
                + KEY_STEP_GOAL + " INTEGER,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_QUEST_COMPLETED + " INTEGER,"
                + KEY_DIFFICULTY + " INTEGER,"
                + KEY_LEVEL_REQUIREMENT + "INTEGER"
                + ")";
        db.execSQL(CREATE_QUEST_DATABASE);
    }*/

    private void createQuestTable(SQLiteDatabase db) {
        String CREATE_QUEST_DATABASE = "CREATE TABLE "
                + QUEST_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + KEY_ACTIVE_STEPS + " INTEGER,"
                + KEY_STEP_GOAL + " INTEGER,"
                + KEY_QUEST_COMPLETED + " INTEGER,"
                + KEY_DIFFICULTY + " INTEGER,"
                + KEY_LEVEL_REQUIREMENT + " INTEGER"
                + ")";
        db.execSQL(CREATE_QUEST_DATABASE);
    }


    private void createUserTable(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_QUESTS_COMPLETED + " INTEGER,"
                + KEY_STEPS + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    private void createCharacterTable(SQLiteDatabase db) {
        String CREATE_CHARACTER_TABLE = "CREATE TABLE " + CHARACTER_TABLE + "("
                + KEY_ID + "INTEGER PRIMARY KEY,"
                + KEY_NAME + "TEXT,"
                + KEY_LEVEL + "SHORT,"
                + KEY_INVENTORY_ID + "INTEGER,"
                + KEY_CURRENCY + "LONG,"
                + KEY_SHOES_ID + "INTEGER,"
                + KEY_SPEED_ID + "SHORT"
                + KEY_LUCK_ID + "SHORT"
                + KEY_ACTIVEQUEST + "INTEGER,"
                + KEY_QUESTS_COMPLETED + "INTEGER,"
                + KEY_CHARACTER_EXP + "LONG"
                + KEY_REQUIRED_EXP + "LONG";
        db.execSQL(CREATE_CHARACTER_TABLE);
    }

    private void createInventoryTable(SQLiteDatabase db) {
        String CREATE_USER_DATABASE = "CREATE TABLE " + INV_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + CHARACTER_ID + " INTEGER REFERENCES " + CHARACTER_TABLE + "(" + KEY_ID + ") ON UPDATE CASCADE"
                + ")";
        db.execSQL(CREATE_USER_DATABASE);
    }

    private void createItemTable(SQLiteDatabase db) {
        String CREATE_ITEM_DATABASE = "CREATE TABLE " + ITEM_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + INV_ID + " INTEGER REFERENCES " + INV_TABLE + "(" + KEY_ID + ") ON UPDATE CASCADE,"
                + ITEM_NAME + "TEXT,"
                + ITEM_VALUE + "INTEGER,"
                + ITEM_ATTRIBUTES + "TEXT"
                + ")";
        db.execSQL(CREATE_ITEM_DATABASE);
    }

    /*
    TODO: Create an add, update, getById function. If feeling ambitious create a function
    to get all objects -> return in array list.
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createQuestTable(db);
        createUserTable(db);
        createCharacterTable(db);
        createInventoryTable(db);
        createItemTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QUEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS" + CHARACTER_TABLE);
        onCreate(db);
    }

    /**
     * Used for adding a new user to the database.
     *
     * @param   user A {@link User} object to add to the database.
     * @return  returns the {@link User} object with the id number assigned by the database.
     */

    //TODO: Clean up User-related entries from the database handler.
    /*public User addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_STEPS, user.getSteps());
        values.put(KEY_QUESTS_COMPLETED, user.getQuestsCompleted());

        //insert
        db.insert(USER_TABLE, null, values);
        db.close();
        //gets the user id number on insertion and returns the updated object;
        db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE, new String[]{KEY_ID}, "id", null, null, null, "id DESC", "1");
        if (cursor != null) cursor.moveToFirst();

        user.setId(cursor.getInt(0));
        db.close();
        return user;
    }*/

    /**
     * Get a user with a specified id number
     *
     * @param id The id number referencing a {@link User} object.
     * @return The {@link User} with the id number specified if it exists.
     */
    //TODO: Clean up User-related entries from the database handler.
    /*public User getUserByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                USER_TABLE,
                new String[] {
                        KEY_ID, KEY_NAME, KEY_STEPS, KEY_QUESTS_COMPLETED
                },
                KEY_ID + "=?",
                new String[] {
                        String.valueOf(id)
                }, null, null, null
        );
        if(cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        return new User(
                cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)
        );
    }*/

    //TODO: Figure out what this semi-colon does
    ;

    /**
     * Used to update the record for a {@link User}, simply change the name, steps,
     * or number of quests completed by a user then pass it into this function to save it.
     *
     * @param user A {@link User} that you want to update in the database.
     */
    //TODO: Clean up User-related entries from the database handler.
    /*public void updateUser(User user) {
        //TODO: check for the existance of a user and add it if it doesn't exist. or return a boolean if it fails.
        //TODO: return updated version of user. (encase it is added).
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_STEPS, user.getSteps());
        values.put(KEY_QUESTS_COMPLETED, user.getQuestsCompleted());

        db.update(USER_TABLE, values, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }*/

    /**
     * Used to add a quest to the database.
     *
     * @param quest the quest to be added.
     * @return the quest with it's updated id number.
     */
    public Quest addQuest(Quest quest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, quest.getName());
        values.put(DESCRIPTION, quest.getDescription());
        values.put(KEY_ACTIVE_STEPS, quest.getActiveSteps());
        values.put(KEY_STEP_GOAL, quest.getStepGoal());
        values.put(KEY_QUEST_COMPLETED, quest.getStepGoal());
        values.put(KEY_DIFFICULTY, quest.getDifficulty());
        values.put(KEY_LEVEL_REQUIREMENT, quest.getLevelRequirement());

        //insert
        db.insert(QUEST_TABLE, null, values);
        db.close();
        //gets the user id number on insertion and returns the updated object;
        db = this.getReadableDatabase();
        Cursor cursor = db.query(
                QUEST_TABLE,
                new String[]{KEY_ID},
                null, null, null, null,
                "id DESC", "1"
        );

        if(cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        quest.setId(cursor.getInt(0));
        db.close();
        return quest;
    }

    /**
     * Get a {@link Quest} by it's id number.
     *
     * @param id The id number of the desired {@link Quest}.
     * @return the requested {@link Quest}.
     */
    public Quest getQuestByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(
                QUEST_TABLE,
                new String[]{
                        KEY_ID, KEY_NAME, KEY_ACTIVE_STEPS, KEY_STEP_GOAL,
                        KEY_QUEST_COMPLETED, KEY_DIFFICULTY, KEY_LEVEL_REQUIREMENT
                },
                KEY_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null, null, null
        );
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        /**
         *  int id, String name, String description, long activeSteps,
         *  long stepGoal, boolean completed, int difficulty, short levelRequirement
         *
         *  KEY_ID + " INTEGER PRIMARY KEY,"    0
         *  KEY_NAME + " TEXT,"                 1
         *  DESCRIPTION + " TEXT,"              2
         *  KEY_ACTIVE_STEPS + " LONG,"         3
         *  KEY_STEP_GOAL + " LONG,"            4
         *  KEY_QUEST_COMPLETED + " INTEGER,"   5
         *  KEY_DIFFICULTY + " INTEGER,"        6
         *  KEY_LEVEL_REQUIREMENT + " SHORT"    7
         *  */
        return new Quest(
                cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3),
                cursor.getLong(4), cursor.getInt(5) != 0, cursor.getShort(6), cursor.getShort(7)
        );
    }

    /**
     * Used to update a {@link Quest} saved in the database. Change the {@link Quest} object as desired
     * and pass it to this to save the changes.
     *
     * @param quest the {@link Quest} the quest to be saved.
     */
    public void updateQuest(Quest quest) {
        //TODO: check for the existance of a quest and add it if it doesn't exist. or return a boolean if it fails.
        //TODO: return updated version of quest. (encase it is added).
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, quest.getName());
        values.put(DESCRIPTION, quest.getDescription());
        values.put(KEY_ACTIVE_STEPS, quest.getActiveSteps());
        values.put(KEY_STEP_GOAL, quest.getStepGoal());
        values.put(KEY_QUEST_COMPLETED, quest.getStepGoal());
        values.put(KEY_DIFFICULTY, quest.getDifficulty());
        values.put(KEY_LEVEL_REQUIREMENT, quest.getLevelRequirement());

        db.update(QUEST_TABLE, values, KEY_ID + "=?", new String[]{String.valueOf(quest.getId())});
    }

    //// TODO: 3/18/2017 finish this
    public Item addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_NAME, item.getName());
        values.put(ITEM_VALUE, item.getValue());
        values.put(INV_ID, item.getInvID());
        values.put(ITEM_ATTRIBUTES, item.toJSONString());

        //insert
        long id = db.insert(ITEM_TABLE, null, values);
        db.close();

        db = this.getReadableDatabase();
        Cursor cursor = db.query(ITEM_TABLE, new String[]{KEY_ID}, null, null, null, null, "id DESC", "1");
        if (cursor == null){
            return null;
        }

        cursor.moveToFirst();
        item.setId(cursor.getInt(0));
        cursor.close();
        db.close();

        return item;
    }

    public Item getItemByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(
                ITEM_TABLE,
                new String[] {
                        KEY_ID, ITEM_NAME, INV_ID, ITEM_ATTRIBUTES, ITEM_VALUE
                },
                KEY_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }, null, null, null
        );
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        return new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(4), cursor.getString(3));
    }

    public void updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_NAME, item.getName());
        values.put(ITEM_VALUE, item.getValue());
        values.put(INV_ID, item.getInvID());
        values.put(ITEM_ATTRIBUTES, item.toJSONString());

        //insert
        db.update(ITEM_TABLE, values, KEY_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public ArrayList<Item> getItemsByInventoryId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor = db.query(ITEM_TABLE, new String[]{KEY_ID, ITEM_NAME, INV_ID, ITEM_ATTRIBUTES, ITEM_VALUE}, INV_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(4), cursor.getString(3)));
        }
        cursor.close();

        return items;
    }

    // TODO: 3/18/2017 decide if this should go through the inventory list and add items aswell
    public Inventory addInventory(Inventory inventory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CHARACTER_ID, inventory.getCharacterId());

        //insert
        db.insert(CHARACTER_TABLE, null, values);
        db.close();

        db = this.getReadableDatabase();
        Cursor cursor = db.query(CHARACTER_TABLE, new String[]{KEY_ID}, null, null, null, null, "id DESC", "1");

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();

        inventory.setId(cursor.getInt(0));
        cursor.close();
        db.close();

        return inventory;
    }

    /**
     * Creates a inventory from the database matching the id number.
     *
     * @param id id number of the inventory
     * @return the {@Link Inventory} object
     */
    public Inventory getInventoryByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(INV_TABLE, new String[]{KEY_ID, CHARACTER_ID}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        Inventory inventory = new Inventory(cursor.getInt(0), cursor.getInt(1));

        ArrayList<Item> items = getItemsByInventoryId(cursor.getInt(0));
        inventory.addItems(items);
        cursor.close();

        return inventory;
    }

    /**
     * Updates the Inventory object passed in. (May not be needed)
     *
     * @param inv
     */
    public void updateInventory(Inventory inv) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, inv.getId());
        values.put(CHARACTER_ID, inv.getCharacterId());

        //insert
        db.update(ITEM_TABLE, values, KEY_ID + "=?", new String[]{String.valueOf(inv.getId())});
        db.close();
    }

    public Character addCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        /** Basic Character details */
        values.put(KEY_NAME, character.getName());
        values.put(KEY_LEVEL, character.getLevel());
        values.put(KEY_CHARACTER_EXP, character.getExp());
        values.put(KEY_REQUIRED_EXP, character.getRequiredExp());

        /** Character inventory details */
        values.put(KEY_INVENTORY_ID, character.getInvId());
        values.put(KEY_CURRENCY, character.getCurrency());
        values.put(KEY_SHOES_ID, character.getShoesId());

        /** Character stat details  */
        values.put(KEY_SPEED_ID, character.getSpeed());
        values.put(KEY_LUCK_ID, character.getLuck());

        /** Character quest details */
        values.put(KEY_QUESTS_COMPLETED, character.getQuestsCompleted());
        values.put(KEY_ACTIVEQUEST, character.getCurrentQuestId());

        //insert
        db.insert(CHARACTER_TABLE, null, values);
        db.close();

        db = this.getReadableDatabase();
        Cursor cursor = db.query(CHARACTER_TABLE, new String[]{KEY_ID}, null, null, null, null, "id DESC", "1");
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        character.setId(cursor.getInt(0));
        cursor.close();
        db.close();

        return character;
    }

    //TODO: From Adrian to Cole. Fix the commented out method
    public Character getCharacterByID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                CHARACTER_TABLE,
                new String[]{
                        /**
                         *  int id, String name, short level, int invId, Inventory inv,
                         *  long currency, int shoesId, short baseSpeed, short baseLuck,
                         *  long exp, long requiredExpForNextLevel, int currentQuestId, int questsCompleted
                         *  */
                        KEY_ID, KEY_NAME, KEY_LEVEL, KEY_INVENTORY_ID,
                        KEY_CURRENCY, KEY_SHOES_ID, KEY_SPEED_ID, KEY_LUCK_ID,
                        KEY_CHARACTER_EXP, KEY_REQUIRED_EXP, KEY_QUESTS_COMPLETED, KEY_ACTIVEQUEST
                },
                KEY_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null, null, null
        );

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        //remove "character = null", this is just temporary as I don't want to push a build with errors. - Adrian
        /**
         *  int id, String name, short level, int invId,
         *  Inventory inv, long currency, int shoesId, short baseSpeed,
         *  short baseLuck, long exp, long requiredExpForNextLevel, int currentQuestId,
         *  int questsCompleted
         *  */
        return new Character(
                cursor.getInt(0), cursor.getString(1), cursor.getShort(2), cursor.getInt(3),
                getInventoryByID(cursor.getInt(3)), cursor.getInt(4), cursor.getInt(5), cursor.getShort(6),
                cursor.getShort(7), cursor.getLong(8), cursor.getLong(9), cursor.getInt(11),
                cursor.getInt(10)
        );
    }

    public void updateCharacter(Character character) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, character.getName());
        values.put(KEY_LEVEL, character.getLevel());
        values.put(KEY_CHARACTER_EXP, character.getExp());
        values.put(KEY_REQUIRED_EXP, character.getRequiredExp());

        values.put(KEY_INVENTORY_ID, character.getInvId());
        values.put(KEY_CURRENCY, character.getCurrency());
        values.put(KEY_SHOES_ID, character.getShoesId());

        values.put(KEY_SPEED_ID, character.getSpeed());
        values.put(KEY_LUCK_ID, character.getLuck());

        values.put(KEY_QUESTS_COMPLETED, character.getQuestsCompleted());
        values.put(KEY_ACTIVEQUEST, character.getCurrentQuestId());

        db.update(
                CHARACTER_TABLE,
                values,
                KEY_ID + "=?",
                new String[] {
                        String.valueOf(character.getId())
                }
        );
    }

    //TODO: Get all the quests from the DB that are that same difficulty as the argument and less than or equal to the characterLevel argument
    public ArrayList<Quest> getQuestByRequirement(int characterLevel, int difficulty) {

        //Ian - "try sorting the data by difficulty and then completion status"


        return null;
    }
}

