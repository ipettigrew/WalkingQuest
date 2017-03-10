package app.apphub.devon.walkingquest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class QuestDetailsActivity extends AppCompatActivity {
    private Button accept, deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        * Check that an id was passed from the previous activity and find it in the database
        * Fills in the sections of the activity from the quest object that has been returned
        * */


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
                /** TODO:   populate this with a function call when we decide what the ActiveQuest view will look like
                 * Use the questActulaizer to set the current quest
                 *  */

            }
        });
    }

}
