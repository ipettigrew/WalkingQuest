package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Inventory;
import app.apphub.devon.walkingquest.database.objects.Item;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class MainViewActivity extends CustomActivity {

    private DatabaseHandler databaseHandler;
    private Inventory inventory;
    private Item item1, item2, item3;
    private Character character;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        //initialize the databaseHandler
        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

        this.populateCharacter();
        this.populateQuest();
        this.populateEquipment();

    }

    public void populateCharacter() {
        TextView characterName = (TextView) findViewById(R.id.character_name);
        TextView characterLevel = (TextView) findViewById(R.id.character_level);
        ProgressBar characterProgressBar = (ProgressBar) findViewById(R.id.mainview_activity_level_progress_bar);

        characterName.setText(character.getName());
        characterLevel.setText("Level: "+character.getLevel());

        characterProgressBar.setMax((int)character.getRequiredExp());
        characterProgressBar.setProgress((int)character.getExp());
    }

    public void populateEquipment() {
        ArrayList<Item> invItems = databaseHandler.getItemsByInventoryId(character.getInv().getId());
        ArrayList<String> invStrings = new ArrayList<String>();
        int length = invItems.size();
        for(int i = 0; i < length; i++){
            invStrings.add(invItems.get(i).getName());
        }
        //frees memory used by invItems.
        invItems = null;

        ListView listView = (ListView) findViewById(R.id.equipment_list);
        ArrayAdapter<Item> adapter =
                new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, databaseHandler.getItemsByInventoryId(character.getInv().getId()));
        listView.setAdapter(adapter);

        LinearLayout equipment = (LinearLayout) findViewById(R.id.mainview_activity_item_details_linear_layout);
        equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainViewActivity.this.startActivity(new Intent(MainViewActivity.this, InventoryActivity.class));
            }
        });

    }

    public void populateQuest() {
        Quest quest = databaseHandler.getQuestByID(character.getCurrentQuestId());
        TextView questTitle = (TextView) findViewById(R.id.mainview_activity_quest_name_text);
        TextView questDescription = (TextView) findViewById(R.id.mainview_activity_quest_description_text);
        ProgressBar questProgressBar = (ProgressBar) findViewById(R.id.mainview_activity_quest_progress_bar);

        if(quest != null) {
            questTitle.setText(quest.getName());
            questDescription.setText(quest.getDescription());
            questProgressBar.setMax((int)quest.getStepGoal());
            //Todo add API check for setProgress animation. If API > 24 then add animation
            //questProgressBar.setProgress((int)quest.getStepGoal()/2);
            questProgressBar.setProgress((int)quest.getActiveSteps());
        }
        else {
            questTitle.setText("No Current Quest Selected");
            questProgressBar.setVisibility(View.INVISIBLE);
        }


        LinearLayout questStats = (LinearLayout) findViewById(R.id.quest_stats);
        questStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainViewActivity.this.startActivity(new Intent(MainViewActivity.this, QuestMainMenu.class));
            }
        });
    }
    @Override
    public void onResume() {
        Log.i("BACK BUTTON PRESS","ON RESUME WAS CALLED");
        super.onResume();
        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);
        this.populateCharacter();
        this.populateQuest();
        this.populateEquipment();
    }


}
