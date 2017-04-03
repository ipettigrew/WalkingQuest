package app.apphub.devon.walkingquest.database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Item;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.Reward;

/**
 * Created by Jonathan McDevitt on 2017-03-30.
 */

public class RewardGenerator {


    public static Reward generateReward(DatabaseHandler databaseHandler, Quest quest, Character character) throws JSONException {

        long goldReward = generateGold(quest.getDifficulty());

        long expReward = generateExp(quest.getDifficulty(), quest.getStepGoal());

        Item[] itemReward = generateItems(databaseHandler, quest.getDifficulty(), character.getLevel(),
                character.getInvId());

        return new Reward(goldReward, expReward, itemReward);
    }

    private static Item[] generateItems(DatabaseHandler databaseHandler, int difficulty, short level,
                                        int charInvId) throws JSONException {

        int rand = (int)(Math.random()*3);
        Item[] rewards = new Item[rand];

        for(int i = 0; i < rand; i++) {
            // get an item that exists in the database
            rewards[i] = databaseHandler.getItemByID(i+1);
            rewards[i].setInvID(charInvId);

        }
        /**
         *  TODO    -   When adding Items to character inventory, make sure to set the invId of each
         *  TODO        item to the invId of the character's Inventory
         *
         *  TODO    -   Set aspects of rewards to random Item attributes (specifically, the effects)
         *
         *  TODO    -   Implement the Reward system
         *                  User can get to RewardActivity from a button in the corner of the
         *                      QuestMainMenu screen (create in the content_quest_main_menu.xml)
         *                  RewardActivity contains a list of Reward entries which the user can
         *                      interact with to obtain their reward
         *                  When a user clicks on a Reward, it pops up a modal dialog with the
         *                      currency, experience, and item rewards. To leave this dialog, the
         *                      user MUST interact with it
         *
         *  NOTE    -   Reward List Details
         *                  The list is persisted as a short in the Character class
         *                      short numRewards
         *
         *  NOTE
         *      When quest reward is collected, set Character's currentQuestId to 0
         *  */
        return rewards;
    }

    private static int getLevelModifier(short level) {
        if(level < 10) {
            return 1;
        } else if(level < 20) {
            return 2;
        } else {
            return 3;
        }
    }

    private static long generateExp(int difficulty, long stepGoal) {
        switch (difficulty) {
            case 1:
            default:
                return stepGoal;
            case 2:
                return 2 * stepGoal;
            case 3:
                return 4 * stepGoal;
        }
    }

    private static long generateGold(int difficulty) {
        long rand = (long) (Math.random() * 1000);
        switch (difficulty) {
            case 1:
            default:
                return rand;
            case 2:
                return 2 * rand;
            case 3:
                return 4 * rand;
        }
    }
}
