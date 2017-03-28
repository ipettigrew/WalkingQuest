package app.apphub.devon.walkingquest.database;

import android.content.Context;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.Inventory;
import app.apphub.devon.walkingquest.database.objects.Item;
import app.apphub.devon.walkingquest.database.objects.Quest;
import app.apphub.devon.walkingquest.database.objects.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by cole_ on 2/24/2017.
 *
 *
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseHandlerTest {

    @Test
    public void createQuestDatabase() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);
        assertTrue(handler != null);
        handler.close();
    }

    @Test
    public void addQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest1, quest2 = new Quest("test", 20000, 2);

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);;

        quest1 = handler.addQuest(quest2);
        assertTrue(quest1.getName().equals(quest2.getName()));
        handler.close();
    }

    @Test
    public void addInventoryTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Character char1;
        char1 = new Character("Bob");

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);
        char1 = handler.addCharacter(char1);

        Inventory inv2, inv1;
        inv1 = new Inventory(char1.getId());

        inv2 = handler.addInventory(inv1);
        System.out.print(inv2.getId());
        assertTrue(inv2.getId() > -1);
        handler.close();
    }

    @Test
    public void updateInventory(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Character char1, char2;
        char1 = new Character("Bob");
        char2 = new Character("Alice");

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);
        char1 = handler.addCharacter(char1);
        char2 = handler.addCharacter(char2);

        Inventory inv2, inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        inv1.setCharacterId(char2.getId());
        handler.updateInventory(inv1);

        inv2 = handler.getInventoryByID(inv1.getId());

        assertTrue(inv1.equals(inv2));
        handler.close();
    }

    @Test
    public void getInventoryByIdTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);
        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv2, inv1;
        inv1 = new Inventory(char1.getId());
        handler.addInventory(inv1);
        inv2 = handler.getInventoryByID(inv1.getId());

        assertTrue(inv1.equals(inv2));
    }

    @Test
    public void getQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest1 = new Quest("test", 55555, 3);

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);

        handler.addQuest(quest1);
        quest1 = handler.addQuest(quest1);

        assertTrue(quest1.getId() > -1);

        Quest quest2 = handler.getQuestByID(quest1.getId());
        System.out.print(quest2.getId());
        assertTrue("Quest Id: "+quest2.getId(),quest2.equals(quest2));
        handler.close();
    }

    @Test
    public void updateQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest2, quest1;
        quest1 = new Quest("test", 666, 1);

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);

        quest2 = handler.addQuest(quest1);
        assertTrue(quest1.equals(quest2));
        quest1.setName("test2");

        handler.updateQuest(quest1);
        quest2 = handler.getQuestByID(quest1.getId());
        assertTrue(quest2.getName().equals("test2"));
        handler.close();
    }

    @Test
    public void updateCharacterTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);

        Character char1, char2;
        char1 = new Character("Bob");

        char1 = handler.addCharacter(char1);
        char1.setName("alice");
        handler.updateCharacter(char1);

        handler.updateCharacter(char1);
        char2 = handler.getCharacterByID(char1.getId());
        assertTrue(char2.getName().equals("alice"));
        handler.close();
    }

    @Test
    public void addCharacterTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Character char1, char2;
        char1 = new Character("Bob", 12, 12);
        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);

        char2 = handler.addCharacter(char1);
        System.out.print(char2.getId());
        assertTrue(char2.getId() > -1);
        handler.close();
    }

    @Test
    public void getCharacterbyIdTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHandler handler = null;
        handler = DatabaseHandler.getInstance(appContext);

        Character char1, char2;
        char1 = new Character("Bob");

        char1 = handler.addCharacter(char1);
        handler.updateCharacter(char1);

        handler.updateCharacter(char1);
        char2 = handler.getCharacterByID(char1.getId());
        assertTrue(char2.getName().equals("Bob"));
        handler.close();
    }

    @Test
    public void addItemTest(){
        DatabaseHandler handler = getHandler();

        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        Item item1 = new Item("test item", 300, inv1.getId());
        item1 = handler.addItem(item1);

        assertTrue("ERROR: Item not added.",item1.getId()>-1);
    }

    @Test
    public void updateItemTest(){
        DatabaseHandler handler = getHandler();

        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        Item item1, item2;
        item1 = new Item("test item", 300, inv1.getId());
        item1 = handler.addItem(item1);

        assertTrue("ERROR: Item not added.",item1.getId()>-1);

        item1.setValue(200);

        handler.updateItem(item1);
        item2 = handler.getItemByID(item1.getId());

        assertTrue(item1.equals(item2));
    }

    @Test
    public void getItemByIdTest(){
        DatabaseHandler handler = getHandler();

        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        Item item1, item2;
        item1 = new Item("test item", 300, inv1.getId());
        item1 = handler.addItem(item1);

        assertTrue("ERROR: Item not added.",item1.getId()>-1);

        item2 = handler.getItemByID(item1.getId());

        assertTrue(item1.equals(item2));
    }

    private DatabaseHandler getHandler(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        return DatabaseHandler.getInstance(appContext);
    }

}
