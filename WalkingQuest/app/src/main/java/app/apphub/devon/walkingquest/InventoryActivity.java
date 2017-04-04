package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.GridAdapters.InventorySelectorGridAdapter;
import app.apphub.devon.walkingquest.GridAdapters.QuestSelectorGridAdapter;
import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Item;
import app.apphub.devon.walkingquest.database.objects.Quest;

public class InventoryActivity extends CustomActivity {

    private GridView itemView;
    private ArrayList<Item> items;
    private DatabaseHandler databaseHandler;

    private Character character;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());

        character = databaseHandler.getCharacterByID(Character.MAIN_PLAYER);
        items = databaseHandler.getItemsByInventoryId(character.getInvId());
        itemView = (GridView) this.findViewById(R.id.inventory_activity_item_gridview);

        final InventorySelectorGridAdapter questAdapter = new InventorySelectorGridAdapter(InventoryActivity.this, items);
        itemView.setAdapter(questAdapter);


        //set the click listener
        itemView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }
}
