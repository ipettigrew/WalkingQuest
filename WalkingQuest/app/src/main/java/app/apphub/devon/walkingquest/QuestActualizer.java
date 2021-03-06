package app.apphub.devon.walkingquest;

import android.content.Context;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.Character;

/**
 * Created by Devon on 3/8/2017.
 */

public class QuestActualizer {

    /** Jonathan Questions:
     *
     *  1)  Do we need explicit references to the character, quests, currentQuest, and dbHandler?*/
    Character character;
    ArrayList<Quest> quests;
    Quest currentQuest;
    DatabaseHandler databaseHandler;

    private int difficulty;

    /**
     * Takes in character object and the difficulty selected and gets a list of possible quests
     * that a character of that level can accept (level minimum for quests). The difficulty determines the difficulty.
     * Gets the list of possible quests from the database and stores them in an arraylist.
     * Checks to see if a quest has been completed before or not.
     */

    public QuestActualizer(Character character, int difficulty, Context context) {

        this.character = character;

        databaseHandler = DatabaseHandler.getInstance(context);
        quests = databaseHandler.getQuestByRequirement(character.getLevel(), difficulty);

        if (character.getCurrentQuestId() != -1) {
            currentQuest = databaseHandler.getQuestByID(character.getCurrentQuestId());
        } else {
            currentQuest = null;
        }

        this.difficulty = difficulty;

    }

    /**
     * Sets the quest at index i in the arraylist as the currently active quest in the character table.
     * Sets the quest id in the quest table as the active quest in the character table
     */

    public void setCurrentQuest(int i) {
        if (currentQuest != null) {
            //killQuest();
            currentQuest = null;
        }
        if (i != -1) {
            currentQuest = databaseHandler.getQuestByID(i);
            character.setCurrentQuestId(currentQuest.getId());
            databaseHandler.updateCharacter(character);
            StepCounterSensorRegister.characterAltered();
        }
    }

    /**
     * Removes the active quest id from character
     */

    public void killQuest() {
        currentQuest = null;
        setCurrentQuest(-1);
    }

    public Quest getQuestById(int i) {
        return databaseHandler.getQuestByID(i);
    }

    public ArrayList<Quest> getQuests() {
        quests = databaseHandler.getQuestByRequirement(character.getLevel(), difficulty);

        //return only quests that are not finished
        for(int i = 0; i < quests.size(); i++){
            if(quests.get(i).isCompleted()){
                quests.remove(i);
                //because the list gets shorter the indices change, i needs to be adjusted for every quest removed
                i--;
            }
        }
        return quests;
    }

}
