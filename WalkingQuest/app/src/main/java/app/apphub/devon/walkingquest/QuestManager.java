package app.apphub.devon.walkingquest;

import app.apphub.devon.walkingquest.database.objects.Character;

/**
 * Created by Devon on 3/8/2017.
 */

public class QuestManager {

    Character character;

    public QuestManager(Character character){

    }

    public void completedQuest(){

        giveRewards();
        removeCompletedQuest();
    }

    public void failedQuest(){

        giveConsolation();
    }

    public double getProgress(){
        return 1.0;
    }

    public void giveRewards(){

    }

    public void giveConsolation(){

    }

    public void removeCompletedQuest(){

    }


}
