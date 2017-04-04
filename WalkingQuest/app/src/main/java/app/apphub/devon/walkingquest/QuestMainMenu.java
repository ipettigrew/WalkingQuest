package app.apphub.devon.walkingquest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import app.apphub.devon.walkingquest.Helper.StringUtils;
import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.RewardGenerator;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.Reward;

public class QuestMainMenu extends CustomActivity {

    private final String TOO_MANY_QUESTS = "Your character has too many rewards waiting to be collected. Please collect the rewards before selecting a new quest";

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

        /** TODO:   Make the tutorial, random, and daily quest buttons initialize the relevant quests

        tutorialQuest = (Button)findViewById(R.id.tutorial_button);
        randomQuest = (Button)findViewById(R.id.random_quest_button);
        dailyQuest = (Button)findViewById(R.id.daily_quest_button);

         TODO: Make Username and Character Name clickable and link to appropriate menus
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

        if(StringUtils.getNumberOfQuestRewards(character.getRewardIds()) < 10) {

            Intent intent = new Intent(QuestMainMenu.this, QuestSelectorGridActivity.class);
            intent.putExtra(Quest.QUEST_DIFFICULTY, diff);
            this.startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(), TOO_MANY_QUESTS, Toast.LENGTH_LONG).show();
        }
    }


    /*
    * Check how many rewards there are. Action for rewards button is set here.
    * For the action if player has rewards force (modal) them to accept, offer option for
    * collecting other rewards if available
    *
    * Author: Devon Rimmington
    * */

    public void initRewardsDialog(){

        //get the character object and find out how many rewards their are
        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

        if(character != null) {

            // check to see if there are rewards and determine if the button needs to be shown
            checkHideRewardButton();

            // get the floating button and add an event listener to it
            rewardsButton = (FloatingActionButton) findViewById(R.id.rewards_button);
            rewardsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // get each of the quests from which rewards have yet to be collected
                    final Quest[] completedQuests = StringUtils.getQuestByCSV(databaseHandler, character.getRewardIds());
                    // create a shell of rewards
                    final Reward[] rewards = new Reward[completedQuests.length];

                    // fill the rewards shell with generated rewards
                    for (int i = 0; i < completedQuests.length; i++) {
                        try {
                            rewards[i] = RewardGenerator.generateReward(databaseHandler, completedQuests[i], character);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    //bring up the dialog
                    if (completedQuests.length == 1) {

                        singleRewardsDisplay(rewards);

                    } else if (completedQuests.length > 1) {

                        mulitRewardsDisplay(rewards);

                    }
                }
            });
        }
    }


    /*
    * Hides the framelayout containing the floating button used to collect the rewards if no rewards exist
    * If rewards do exist than display the number of rewards just above the button in a notification style
    *
    * Author: Devon
    */

    private void checkHideRewardButton(){
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.rewards_frame);
        int numberOfRewards = StringUtils.getNumberOfQuestRewards(character.getRewardIds());
        if(numberOfRewards == 0){
            frameLayout.setVisibility(View.INVISIBLE);
        }else{
            TextView tv = (TextView) findViewById(R.id.rewards_button_number);
            tv.setText(""+numberOfRewards);
        }
    }


    /*
    * Creates a dialog for displaying multiple rewards
    *
    * Author: Devon
    **/

    private void singleRewardsDisplay(Reward[] _rewards){

        final Reward[] rewards = _rewards;

        //modal for only one available reward
        final Dialog dialog = new Dialog(QuestMainMenu.this);
        dialog.setTitle("Collect your reward");
        dialog.setContentView(R.layout.dialog_single_reward);

        TextView rewardSummary = (TextView) dialog.findViewById(R.id.dialog_single_reward_summary_text);
        rewardSummary.setText(rewards[0].toString());

        Button confirmButton = (Button) dialog.findViewById(R.id.dialog_single_reward_confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // apply the reward to the character
                character.addRewards(rewards[0]);
                character.setRewardIds("");
                databaseHandler.updateCharacter(character);

                // when there are no longer any rewards remove the rewards button
                checkHideRewardButton();

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    /*
    * Creates a dialog for displaying multiple rewards
    *
    * Author: Devon
    **/

    private void mulitRewardsDisplay(Reward[] _rewards){

        final Reward[] rewards = _rewards;

        // modal for only one available reward
        final Dialog dialog = new Dialog(QuestMainMenu.this);
        dialog.setTitle("Collect your reward");
        dialog.setContentView(R.layout.dialog_mulit_reward);

        String[] rewardsArray = new String[rewards.length];
        for (int i = 0; i < rewards.length; i++) {
            rewardsArray[i] = rewards[i].toString();
        }

        // add the toStrings of each reward to an array adapter for being displayed
        ArrayAdapter<String> rewardAdapter = new ArrayAdapter<String>(QuestMainMenu.this, R.layout.generic_list_cell, R.id.generic_list_cell_text, rewardsArray);
        ListView listView = (ListView) dialog.findViewById(R.id.dialog_multi_reward_list);
        listView.setAdapter(rewardAdapter);

        Button confirmButton = (Button) dialog.findViewById(R.id.dialog_mulit_confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add each reward to the charcter
                for (int i = 0; i < rewards.length; i++) {
                    //apply the reward to the character
                    character.addRewards(rewards[i]);
                }

                // set the current rewards list to empty
                character.setRewardIds("");
                databaseHandler.updateCharacter(character);
                character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);

                // when there are no longer any rewards remove the rewards button
                checkHideRewardButton();

                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
