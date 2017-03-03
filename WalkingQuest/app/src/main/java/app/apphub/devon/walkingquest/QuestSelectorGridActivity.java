package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import app.apphub.devon.walkingquest.GridAdapters.QuestSelectorGridAdapter;

public class QuestSelectorGridActivity extends AppCompatActivity {
    public GridView questGrid;
    public String[] quests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_selector_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        quests = new String[8];
        for (int i = 0; i < 8; i++) {
            quests[i] = (i + 1) + "";
        }

        questGrid = (GridView) this.findViewById(R.id.quest_selector_grid_view);
        questGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(QuestSelectorGridActivity.this, QuestDetailsActivity.class));
            }
        });
        QuestSelectorGridAdapter questAdapter = new QuestSelectorGridAdapter(QuestSelectorGridActivity.this, quests);
        questGrid.setAdapter(questAdapter);
    }

}
