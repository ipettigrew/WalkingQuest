package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Item;
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
        startActivity(new Intent(WalkingQuestSplashScreen.this, MainViewActivity.class));
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
        Character character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

        // if the charcter is null add the things we need to play a demo
        if(character == null){

            // create the game world for storing items that do not exist in the character currently
            Character gameWorld = new Character("World");
            databaseHandler.addCharacter(gameWorld);
            gameWorld = databaseHandler.getCharacterByID(Character.GAME_WORLD);

            // create the character
            character = new Character("Player_1");
            character.setRewardIds("1");
            character = databaseHandler.addCharacter(character);
            character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);


            databaseHandler.getItemsByInventoryId(character.getInvId());


            // create some items to give the user
            for(int i = 0; i < 10; i++){
                JSONObject attr = new JSONObject();
                try {
                    attr.put("testEntry", "test");
                }catch (JSONException e){
                    e.printStackTrace();
                    return;
                }
                Item item = new Item();
                item.setName("Item " + i);
                item.setValue(12);
                item.setInvID(gameWorld.getInvId());
                item.setAttributes(attr);
                databaseHandler.addItem(item);
            }

            Log.i("SPLASHSCREEN", character.getInv() +"");

            databaseHandler.updateCharacter(character);

            StepCounterSensorRegister.characterAltered();

            Quest questEasy_1 = new Quest("Flour delivery", 50, 1);
            questEasy_1.setCompleted(true);
            Quest questEasy_2 = new Quest("Escort: Grandma Across the Road", 20, 1);
            //questEasy_2.setCompleted(true);
            Quest questEasy_3 = new Quest("Escort: Younger Sister to School", 50, 1);
            //questEasy_3.setCompleted(true);

            Quest questMed_1 = new Quest("Delivery: Out of State", 3250, 2);
            //questMed_1.setCompleted(true);
            Quest questMed_2 = new Quest("Charity Fun Run", 2750, 2);
            //questMed_2.setCompleted(true);
            Quest questMed_3 = new Quest("Delivery: Medicine for Child", 4200, 2);
            //questMed_3.setCompleted(true);

            Quest questHard_1 = new Quest("Slay Pumpkin Monster", 9500, 3);
            //questHard_1.setCompleted(true);
            Quest questHard_2 = new Quest("Treasure: Irish Gold", 12000, 3);
            //questHard_2.setCompleted(true);
            Quest questHard_3 = new Quest("Slay Ice Dragon", 15000, 3);
            //questHard_3.setCompleted(true);
            Quest questHard_4 = new Quest("Slay Ice Dragon: Part 2", 15000, 3);
            //questHard_4.setCompleted(true);

            databaseHandler.addQuest(questEasy_1);
            databaseHandler.addQuest(questEasy_2);
            databaseHandler.addQuest(questEasy_3);

            databaseHandler.addQuest(questMed_1);
            databaseHandler.addQuest(questMed_2);
            databaseHandler.addQuest(questMed_3);

            databaseHandler.addQuest(questHard_1);
            databaseHandler.addQuest(questHard_2);
            databaseHandler.addQuest(questHard_3);
            databaseHandler.addQuest(questHard_4);

        }
    }
}
