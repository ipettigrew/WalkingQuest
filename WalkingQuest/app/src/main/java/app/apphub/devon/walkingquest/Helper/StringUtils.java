package app.apphub.devon.walkingquest.Helper;

import android.util.Log;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.Reward;

/**
 * Created by Devon on 4/1/2017.
 */

public class StringUtils {

    /*
    * Takes a CSV string of questIds as an input and a database handler. Finds the quests related to the questIds and constructs an array of Quests and
    * returns them
    *
    * Author: Devon Rimmington & Jonathan McDavit
    **/

    public static Quest[] getQuestByCSV(DatabaseHandler databaseHandler, String csv){
        String[] ids = csv.split(",");

        Log.i("STRING UTILS", csv.toString());

        if(!csv.equals("")) {

            int length = ids.length;
            Quest[] quests = new Quest[length];
            for (int i = 0; i < length; i++) {
                Quest quest = databaseHandler.getQuestByID(Integer.parseInt(ids[i]));

                if (quest == null)
                    throw new NullPointerException();

                if (quest.isCompleted())
                    quests[i] = quest;
            }

            return quests;
        }

        return new Quest[0];

    }

    /*
    * Adds a new questId to the end of the list of questIds
    *
    * Author: Devon Rimmington
    **/

    public static String addCompletedQuestToCharacter(String csv, int newlyCompletedQuest){
        if(!csv.equals("")) {
            csv += "," + newlyCompletedQuest;
        }else{
            csv += newlyCompletedQuest;
        }
        return  csv;
    }

    /*
    * Returns the number of questIds in the string
    *
    * Author: Devon Rimmington
    **/

    public static int getNumberOfQuestRewards(String csv){
        if(csv.equals(""))
            return 0;

        return csv.split(",").length;
    }

}
