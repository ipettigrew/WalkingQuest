package app.apphub.devon.walkingquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import app.apphub.devon.walkingquest.database.DatabaseHandler;
import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Inventory;
import app.apphub.devon.walkingquest.database.objects.Item;

public class MainViewActivity extends AppCompatActivity {

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

        //just for testing purposes
        character = new Character("Bob");
        databaseHandler.addCharacter(character);

        inventory = new Inventory(character.getId());
        databaseHandler.addInventory(inventory);

        item1 = new Item("test item", 301, inventory.getId());
        databaseHandler.addItem(item1);

        item2 = new Item("test item2", 302, inventory.getId());
        databaseHandler.addItem(item2);

        item3 = new Item("test item3", 303, inventory.getId());
        databaseHandler.addItem(item3);

        ListView listView = (ListView) findViewById(R.id.equipment_list);
        ArrayAdapter<Item> adapter =
                new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, databaseHandler.getItemsByInventoryId(inventory.getId()));
        listView.setAdapter(adapter);
        databaseHandler.close();
    }


}
