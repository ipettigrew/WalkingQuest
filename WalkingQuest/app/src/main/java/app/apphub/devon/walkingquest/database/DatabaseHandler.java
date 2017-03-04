package app.apphub.devon.walkingquest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles the database for the app. It is used for saving and retrieving users
 * and quests.
 *
 * @author      Cole DeMan cole@coledeman.com
 * @version     1.0                 (current version number of program)
 * @since       1.0          (the version of the package this class was first added to)
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "walkingQuest";

    //both
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    //user
    private static final String USER_TABLE = "users";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_QUESTS_COMPLETED = "questsCompleted";

    //quest
    private static final String QUEST_TABLE = "quests";
    private static final String KEY_ACTIVE_STEPS = "active_steps";
    private static final String KEY_STEP_GOAL = "step_goal";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_QUEST_COMPLETED = "questCompleted";
    private static final String KEY_DIFFICULTY = "difficulty";

    /**
     * Constructor for the Database handler
     * @param context the application context;
     */
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_DATABASE = "CREATE TABLE "+ USER_TABLE +"("
                + KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_NAME +" TEXT,"
                + KEY_QUESTS_COMPLETED +" INTEGER,"
                + KEY_STEPS +" INTEGER"+")";
        db.execSQL(CREATE_USER_DATABASE);
        String CREATE_QUEST_DATABASE = "CREATE TABLE "
                + QUEST_TABLE +"("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_NAME +" TEXT,"
                + KEY_ACTIVE_STEPS +" INTEGER,"
                + KEY_STEP_GOAL +" INTEGER,"
                + KEY_USER_ID +" INTEGER,"
                + KEY_QUEST_COMPLETED +" INTEGER,"
                + KEY_DIFFICULTY+ " INTEGER"
                +")";
        db.execSQL(CREATE_QUEST_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+QUEST_TABLE);

        onCreate(db);
    }

    /**
     * Used for adding a new user to the database.
     * @param user A {@link User} object to add to the database.
     * @return returns the {@link User} object with the id number assigned by the database.
     */
    public User addUser(User user){
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
        Cursor cursor = db.query(USER_TABLE, new String[] {KEY_ID}, "id",null,null,null,"id DESC", "1");
        if(cursor != null) cursor.moveToFirst();

        user.setId(cursor.getInt(0));
        db.close();
        return user;
    }

    /**
     * Get a user with a specified id number
     * @param id The id number referencing a {@link User} object.
     * @return The {@link User} with the id number specified if it exists.
     */
    public User getUserByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_STEPS, KEY_QUESTS_COMPLETED}, KEY_ID+"=?",new String[] {String.valueOf(id)},null,null,null);
        if(cursor != null) cursor.moveToFirst();
        User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
        return user;
    };

    /**
     * Used to update the record for a {@link User}, simply change the name, steps,
     * or number of quests completed by a user then pass it into this function to save it.
     * @param user A {@link User} that you want to update in the database.
     */
    public void updateUser(User user){
        //TODO: check for the existance of a user and add it if it doesn't exist. or return a boolean if it fails.
        //TODO: return updated version of user. (encase it is added).
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_STEPS, user.getSteps());
        values.put(KEY_QUESTS_COMPLETED, user.getQuestsCompleted());

        db.update(USER_TABLE, values, KEY_ID+"=?", new String[] {String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Used to add a quest to the database.
     * @param quest the quest to be added.
     * @return the quest with it's updated id number.
     */
    public Quest addQuest(Quest quest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, quest.getName());
        values.put(KEY_ACTIVE_STEPS, quest.getActiveSteps());
        values.put(KEY_STEP_GOAL, quest.getStepGoal());
        values.put(KEY_USER_ID, quest.getUserID());
        values.put(KEY_QUEST_COMPLETED, quest.getStepGoal());
        values.put(KEY_DIFFICULTY, quest.getDifficulty());

        //insert
        db.insert(QUEST_TABLE, null, values);
        db.close();
        //gets the user id number on insertion and returns the updated object;
        db = this.getReadableDatabase();
        Cursor cursor = db.query(QUEST_TABLE, new String[] {KEY_ID}, null,null,null,null,"id DESC", "1");
        if(cursor != null) cursor.moveToFirst();

        quest.setId(cursor.getInt(0));
        db.close();
        return quest;
    }

    /**
     * Get a {@link Quest} by it's id number.
     * @param id The id number of the desired {@link Quest}.
     * @return the requested {@link Quest}.
     */
    public Quest getQuestByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(QUEST_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_ACTIVE_STEPS, KEY_STEP_GOAL, KEY_USER_ID, KEY_QUEST_COMPLETED, KEY_DIFFICULTY}, KEY_ID+"=?",new String[] {String.valueOf(id)},null,null,null);
        if(cursor != null) cursor.moveToFirst();
        Quest quest = new Quest(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),(cursor.getInt(5) != 0),cursor.getInt(6));
        return quest;
    }

    /**
     * Used to update a {@link Quest} saved in the database. Change the {@link Quest} object as desired
     * and pass it to this to save the changes.
     * @param quest the {@link Quest} the quest to be saved.
     */
    public void updateQuest(Quest quest){
        //TODO: check for the existance of a quest and add it if it doesn't exist. or return a boolean if it fails.
        //TODO: return updated version of quest. (encase it is added).
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, quest.getName());
        values.put(KEY_ACTIVE_STEPS, quest.getActiveSteps());
        values.put(KEY_STEP_GOAL, quest.getStepGoal());
        values.put(KEY_USER_ID, quest.getUserID());
        values.put(KEY_QUEST_COMPLETED, quest.getStepGoal());
        values.put(KEY_DIFFICULTY, quest.getDifficulty());

        db.update(QUEST_TABLE, values, KEY_ID+"=?", new String[] {String.valueOf(quest.getId())});
    }
}
