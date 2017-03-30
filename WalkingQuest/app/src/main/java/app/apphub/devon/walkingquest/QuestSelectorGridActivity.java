package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.apphub.devon.walkingquest.GridAdapters.QuestSelectorGridAdapter;
import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class QuestSelectorGridActivity extends AppCompatActivity {
    public GridView questGrid;
    public ArrayList<Quest> quests;
    private QuestActualizer questActualizer;
    private DatabaseHandler databaseHandler;
    private TextView currentlySelectedQuest;

    private Character character;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_selector_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         *  Use quest actualizer to get the list of quests and present them to the user
         *  */

        init();

        questGrid = (GridView) this.findViewById(R.id.quest_selector_grid_view);

        final QuestSelectorGridAdapter questAdapter = new QuestSelectorGridAdapter(QuestSelectorGridActivity.this, quests);
        questGrid.setAdapter(questAdapter);


        //set the click listener
        questGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get the quest clicked on
                Quest selectedQuest = questAdapter.getItem(i);
                //load the next activity and pass the quest id to the activity
                Intent intent = new Intent(QuestSelectorGridActivity.this, QuestDetailsActivity.class);
                intent.putExtra(Quest.QUEST_ID, selectedQuest.getId());
                startActivity(intent);
            }
        });

    }


    /*
    * Initalizes the quest actualizer and the quest list
    **/
    private void init(){

        currentlySelectedQuest = (TextView) findViewById(R.id.currentQuest);

        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
        character = databaseHandler.getCharacterByID(1);
        Quest quest = databaseHandler.getQuestByID(character.getCurrentQuestId());
        difficulty = getIntent().getIntExtra(Quest.QUEST_DIFFICULTY, 1);

        questActualizer = new QuestActualizer(character, difficulty, getApplicationContext());
        quests = questActualizer.getQuests();

        if(quest != null){
            currentlySelectedQuest.setText(quest.getId() + " " + quest.getName() +
                    "\n" + quest.getActiveSteps() + "/" + quest.getStepGoal() +
                    "\n" + quest.getDescription());
        }else{
            currentlySelectedQuest.setText("No current quest");
        }
    }

}
