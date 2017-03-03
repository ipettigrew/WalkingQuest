package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class QuestMainMenu extends AppCompatActivity {
    private Button easy, medium, hard;
    private Button tutorialQuest, randomQuest, dailyQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        easy = (Button)findViewById(R.id.beginner_quests_button);
        medium = (Button)findViewById(R.id.intermediate_quests_button);
        hard = (Button)findViewById(R.id.hard_quests_button);

        /** TODO:   Make the tutorial, random, and daily quest buttons initialize the relevant quests
         * */
        tutorialQuest = (Button)findViewById(R.id.tutorial_button);
        randomQuest = (Button)findViewById(R.id.random_quest_button);
        dailyQuest = (Button)findViewById(R.id.daily_quest_button);

        /** TODO: Make Username and Character Name clickable and link to appropriate menus
         * */
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestSelectorGrid();
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestSelectorGrid();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestSelectorGrid();
            }
        });
    }

    /**
     *  Simply sends the user to the next menu. In the future we need to implement this such that
     *  the function calls the hub to fetch a set of intermediate quests from the database
     * */
    private void goToQuestSelectorGrid() {
        this.startActivity(new Intent(QuestMainMenu.this, QuestSelectorGridActivity.class));
    }

}
