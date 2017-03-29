package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class WalkingQuestSplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_quest_splash_screen);


        /*TODO:remove this*/
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getApplicationContext());

        Quest quest = new Quest("Quest", 2500, 0);
        databaseHandler.addQuest(quest);


        Log.i("Splash1", ""+quest.getId());

        Character character = new Character("Devon");
        character.setCurrentQuestId(quest.getId());
        databaseHandler.addCharacter(character);

        Log.i("Splash2", ""+character.getCurrentQuestId());

        databaseHandler.updateCharacter(character);

        character = databaseHandler.getCharacterByID(1);
        Log.i("Splash3", ""+character.getCurrentQuestId() +" "+character.getName());


        /** Test code to be removed later, simply tests that the splash screen will move to the
         *  login screen    */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(WalkingQuestSplashScreen.this, LoginActivity.class));
    }


    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
