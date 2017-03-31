package app.apphub.devon.walkingquest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.RewardGenerator;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.Reward;

public class QuestMainMenu extends CustomActivity {
    private Button easy, medium, hard;
    private Button tutorialQuest, randomQuest, dailyQuest;
    private FloatingActionButton rewardsButton;

    private DatabaseHandler databaseHandler;
    private Character character;
    private Quest quest;
    private short numberOfRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the databaseHandler
        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
        initRewardsDialog();



        easy = (Button)findViewById(R.id.beginner_quests_button);
        medium = (Button)findViewById(R.id.intermediate_quests_button);
        hard = (Button)findViewById(R.id.hard_quests_button);

        rewardsButton = (FloatingActionButton) findViewById(R.id.rewards_button);

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
                goToQuestSelectorGrid(1);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestSelectorGrid(2);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuestSelectorGrid(3);
            }
        });
    }

    /**
     *  Simply sends the user to the next menu. In the future we need to implement this such that
     *  the function calls the hub to fetch a set of intermediate quests from the database
     * */
    private void goToQuestSelectorGrid(int diff) {
        Intent intent = new Intent(QuestMainMenu.this, QuestSelectorGridActivity.class);
        intent.putExtra(Quest.QUEST_DIFFICULTY, diff);
        this.startActivity(intent);
    }

    /*
    * Check how many rewards there are. Action for rewards button is set here.
    * For the action if player has rewards force (modal) them to accept, offer option for
    * collecting other rewards if available
    * */

    public void initRewardsDialog(){

        //get the character object and find out how many rewards their are
        character = databaseHandler.getCharacterByID(1);
        quest = databaseHandler.getQuestByID(character.getCurrentQuestId());
        numberOfRewards = character.getNumRewards();

        rewardsButton = (FloatingActionButton) findViewById(R.id.rewards_button);

        if(quest != null) {

            Log.i("QUESTMAINMENU", "Quest != null");

            final Reward[] rewards = new Reward[numberOfRewards];

            for(int i = 0; i < rewards.length; i++){
                rewards[i] = RewardGenerator.generateReward(databaseHandler, quest, character);
            }

            rewardsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    Log.i("QUESTMAINMENU", "button clicked");

                    //bring up the dialog
                    if (numberOfRewards == 1) {

                        //modal for only one available reward
                        final Dialog dialog = new Dialog(QuestMainMenu.this);
                        dialog.setTitle("Collect your reward");
                        dialog.setContentView(R.layout.dialog_single_reward);

                        TextView rewardSummary = (TextView) dialog.findViewById(R.id.reward_text_single);
                        rewardSummary.setText(rewards[0].toString());

                        Button confirmButton = (Button) dialog.findViewById(R.id.dialog_button_ok_single_button);

                        confirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //apply the reward to the character
                                character.addRewards(rewards[0]);
                                character.setNumRewards(numberOfRewards--);
                                databaseHandler.updateCharacter(character);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    } else if (numberOfRewards > 1) {
                        //modal for multiple available rewards

                        //modal for only one available reward
                        final Dialog dialog = new Dialog(QuestMainMenu.this);
                        dialog.setTitle("Collect your reward");
                        dialog.setContentView(R.layout.dialog_mulit_reward);

                        //create the list of rewards to be used in the adapter
                        ArrayList<String> rewardsList = new ArrayList<String>();
                        for(int i = 0; i < rewards.length; i++){
                            rewardsList.add(rewards[i].toString());
                        }

                        String[] rewardsArray = new String[rewards.length];
                        for(int i = 0; i < rewards.length; i++){
                            rewardsArray[i] = rewards[i].toString();
                        }

                        /*
                        GenericAdapter genericAdapter = new GenericAdapter(QuestMainMenu.this, R.id.generic_list_cell, R.id.reward_text_multi, rewardsArray);
                        ListView rewardSummary = (ListView) dialog.findViewById(R.id.reward_text_multi);
                        rewardSummary.setAdapter(genericAdapter);
                        */

                        ArrayAdapter<String> rewardAdapter = new ArrayAdapter<String>(QuestMainMenu.this, R.layout.generic_list_cell, R.id.generic_list_cell_text, rewardsArray);
                        ListView listView = (ListView) dialog.findViewById(R.id.reward_text_multi);
                        listView.setAdapter(rewardAdapter);

                        Button confirmButton = (Button) dialog.findViewById(R.id.dialog_button_ok_multi_button);

                        confirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for(int i = 0; i < rewards.length && numberOfRewards > 0; i++) {
                                    //apply the reward to the character
                                    character.addRewards(rewards[i]);
                                    character.setNumRewards(numberOfRewards--);
                                }

                                databaseHandler.updateCharacter(character);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    } else {
                        //no rewards
                    }
                }

            });
        }else{
            Log.i("QUESTMAINMENU", "Quest == null");
        }

    }


}
