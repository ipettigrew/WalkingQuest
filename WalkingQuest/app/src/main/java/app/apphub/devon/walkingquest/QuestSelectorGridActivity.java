package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import app.apphub.devon.walkingquest.GridAdapters.QuestSelectorGridAdapter;
import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class QuestSelectorGridActivity extends AppCompatActivity {
    public GridView questGrid;
    public List<Quest> quests;
    private QuestActualizer questActualizer;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_selector_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         *  Use quest actualizer to get the list of quests and present them to the user
         *  */


        quests = questActualizer.getQuests();
        for (int i = 0; i < 8; i++) {
            quests.get(i).setId((i + 1));
        }

        questGrid = (GridView) this.findViewById(R.id.quest_selector_grid_view);
        questGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //send the quest id to the details screen
                startActivity(new Intent(QuestSelectorGridActivity.this, QuestDetailsActivity.class));
            }
        });
        QuestSelectorGridAdapter questAdapter = new QuestSelectorGridAdapter(QuestSelectorGridActivity.this, (Quest[])quests.toArray());
        questGrid.setAdapter(questAdapter);
    }

}
