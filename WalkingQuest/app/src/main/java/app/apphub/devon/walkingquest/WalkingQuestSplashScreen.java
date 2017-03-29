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

        //TODO: remove this when real data is implimented
        populateDatabase();


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

    private void populateDatabase(){
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
        Character character = databaseHandler.getCharacterByID(1);

        //if the charcter is null add the things we need to play a demo
        if(character == null){
            character = new Character("Player_1");
            databaseHandler.addCharacter(character);
            StepCounterSensorRegister.characterAultered();

            Quest questEasy_1 = new Quest("Flour delivery", 50, 1);
            Quest questEasy_2 = new Quest("Escort: Grandma Across the Road", 20, 1);
            Quest questEasy_3 = new Quest("Escort: Younger Sister to School", 50, 1);

            Quest questMed_1 = new Quest("Delivery: Out of State", 3250, 2);
            Quest questMed_2 = new Quest("Charity Fun Run", 2750, 2);
            Quest questMed_3 = new Quest("Delivery: Medicine for Child", 4200, 2);

            Quest questHard_1 = new Quest("Slay Pumpkin Monster", 9500, 3);
            Quest questHard_2 = new Quest("Treasure: Irish Gold", 12000, 3);
            Quest questHard_3 = new Quest("Slay Ice Dragon", 15000, 3);

            databaseHandler.addQuest(questEasy_1);
            databaseHandler.addQuest(questEasy_2);
            databaseHandler.addQuest(questEasy_3);

            databaseHandler.addQuest(questMed_1);
            databaseHandler.addQuest(questMed_2);
            databaseHandler.addQuest(questMed_3);

            databaseHandler.addQuest(questHard_1);
            databaseHandler.addQuest(questHard_2);
            databaseHandler.addQuest(questHard_3);

        }
    }


}
