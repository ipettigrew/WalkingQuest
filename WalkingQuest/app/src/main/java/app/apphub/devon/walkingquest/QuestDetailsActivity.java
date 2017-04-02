package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class QuestDetailsActivity extends AppCompatActivity {
    private Button accept, deny;
    private TextView questName, questStepRequirement, questDescription;
    private DatabaseHandler databaseHandler;
    private Quest selectedQuest;
    private Character character;
    private QuestActualizer questActualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        accept = (Button)findViewById(R.id.accept_quest);
        deny = (Button)findViewById(R.id.deny_quest);

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if the quest selected is not null use the questActualizer to set the quest
                if(selectedQuest != null){
                    //use the quest actualizer to change the characters current quest
                    questActualizer = new QuestActualizer(character, selectedQuest.getDifficulty(), getApplicationContext());
                    questActualizer.setCurrentQuest(selectedQuest.getId());

                    //prepare the intent to take the user to the main page
                    //TODO: change this to the correct activity (main menu)
                    Intent home = new Intent(QuestDetailsActivity.this, QuestMainMenu.class);
                    startActivity(home);
                }
            }
        });
    }

    /*
    * Initalizes the activity
    * Gets the activity selected from the previous screen and loads its details
    **/

    private void init(){
        //initalize the database handler
        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());

        //set the character object
        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

        //get the current intent
        Intent intent = getIntent();
        //from the current intent get the quest id
        final int questId = intent.getIntExtra(Quest.QUEST_ID, -1);
        //get the quest using the quest id
        selectedQuest = databaseHandler.getQuestByID(questId);
        //set the quest name, steps required quest description
        questName = (TextView) findViewById(R.id.quest_name);
        questStepRequirement = (TextView) findViewById(R.id.quest_step_requirement);
        questDescription = (TextView) findViewById(R.id.quest_description);

        //set the text in the activities view
        if(selectedQuest != null){
            questName.setText(selectedQuest.getName());
            questStepRequirement.setText(selectedQuest.getStepGoal()+" Steps");
            questDescription.setText(selectedQuest.getDescription());
        }else{
            questDescription.setText("ERROR UNABLE TO RESOLVE QUEST");
        }
    }

}
