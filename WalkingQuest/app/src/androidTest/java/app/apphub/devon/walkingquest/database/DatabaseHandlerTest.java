package app.apphub.devon.walkingquest.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import app.apphub.devon.walkingquest.database.objects.Character;
import app.apphub.devon.walkingquest.database.objects.IDandNameRow;
import app.apphub.devon.walkingquest.database.objects.Inventory;
import app.apphub.devon.walkingquest.database.objects.Item;
import app.apphub.devon.walkingquest.database.objects.Quest;

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

        DatabaseHandler handler = getHandler();
        assertTrue(handler != null);
        handler.close();
    }

    @Test
    public void deleteQuest(){
        // Context of the app under test.
        DatabaseHandler handler = getHandler();
        Quest quest1;
        quest1 = handler.addQuest(new Quest("test", 20000, 2));
        assertTrue("Quest not added.", quest1.getId()>-1);

        int deleted = handler.deleteQuest(quest1);

        assertTrue("Quest not deleted.", deleted>0);
        assertTrue("too many quests deleted. Quests Deleted: "+deleted, deleted<2);

        handler.close();
    }

    @Test
    public void addQuestTest(){
        Quest quest1, quest2 = new Quest("test", 20000, 2);

        DatabaseHandler handler = getHandler();

        quest1 = handler.addQuest(quest2);
        assertTrue(quest1.getId()>0);
        handler.deleteQuest(quest1);
        handler.close();
    }

    @Test
    public void getAllQuestTest(){
        Quest quest1, quest2;
        quest1 = new Quest("test", 20000, 2);
        quest2 = new Quest("ALso Test", 453453, 2);

        DatabaseHandler handler = getHandler();

        quest1 = handler.addQuest(quest1);
        quest2 = handler.addQuest(quest2);
        ArrayList<Quest> quests = handler.getQuestByRequirement(-1,2);
        assertTrue("Quests found: "+ quests.size(), quests.size()>0);
        handler.deleteQuest(quest1);
        handler.deleteQuest(quest2);
        handler.close();
    }

    @Test
    public void deleteInventory(){
        // Context of the app under test.
        DatabaseHandler handler = getHandler();
        Character char1;
        char1 = new Character("Bob");

        char1 = handler.addCharacter(char1);

        Inventory inv1;
        inv1 = new Inventory(char1.getId());

        inv1 = handler.addInventory(inv1);

        int deleted = handler.deleteInventory(inv1);

        assertTrue("Inventory not deleted.", deleted>0);
        assertTrue("too many quests deleted. Quests Deleted: "+deleted, deleted<2);

        handler.deleteCharacter(char1);

        handler.close();
    }

    @Test
    public void addInventoryTest(){
        Character char1;
        char1 = new Character("Bob");

        DatabaseHandler handler = getHandler();
        char1 = handler.addCharacter(char1);

        Inventory inv2, inv1;
        inv1 = new Inventory(char1.getId());

        inv2 = handler.addInventory(inv1);
        System.out.print(inv2.getId());
        assertTrue(inv2.getId() > -1);
        handler.deleteInventory(inv1);
        handler.deleteCharacter(char1);
        handler.close();
    }

    @Test
    public void updateInventory(){
        Character char1, char2;
        char1 = new Character("Bob");
        char2 = new Character("Alice");

        DatabaseHandler handler = getHandler();
        char1 = handler.addCharacter(char1);
        char2 = handler.addCharacter(char2);

        Inventory inv2, inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        inv1.setCharacterId(char2.getId());
        handler.updateInventory(inv1);

        inv2 = handler.getInventoryByID(inv1.getId());

        handler.deleteCharacter(char1);
        handler.deleteCharacter(char2);
        handler.deleteInventory(inv2);

        assertTrue(inv1.equals(inv2));
        handler.close();
    }

    @Test
    public void getInventoryByIdTest(){
        // Context of the app under test.
        DatabaseHandler handler = getHandler();
        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1, inv2;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        inv2 = handler.getInventoryByID(inv1.getId());

        assertTrue(inv1.equals(inv2));

        int deleted = handler.deleteInventory(inv1);

        assertTrue("Inventory not deleted.", deleted>0);
        assertTrue("too many Inventories deleted. # Deleted: "+deleted, deleted<2);

        handler.deleteCharacter(char1);

        handler.close();
    }

    @Test
    public void getQuestTest(){
        Quest quest1 = new Quest("test", 55555, 3);

        DatabaseHandler handler = getHandler();

        quest1 = handler.addQuest(quest1);

        assertTrue(quest1.getId() > -1);

        Quest quest2 = handler.getQuestByID(quest1.getId());
        System.out.print(quest2.getId());
        assertTrue("Quest Id: "+quest2.getId(),quest2.equals(quest2));
        handler.deleteQuest(quest1);
        handler.close();
    }

    @Test
    public void updateQuestTest(){
        Quest quest2, quest1;
        quest1 = new Quest("test", 666, 1);

        DatabaseHandler handler = getHandler();

        quest2 = handler.addQuest(quest1);
        assertTrue(quest1.equals(quest2));
        quest1.setName("test2");

        handler.updateQuest(quest1);
        quest2 = handler.getQuestByID(quest1.getId());
        assertTrue(quest2.getName().equals("test2"));
        handler.deleteQuest(quest2);
        handler.close();
    }

    @Test
    public void updateCharacterTest(){

        DatabaseHandler handler = getHandler();

        Character char1, char2;
        char1 = new Character("Bob");

        char1 = handler.addCharacter(char1);
        char1.setName("alice");
        handler.updateCharacter(char1);

        handler.updateCharacter(char1);
        char2 = handler.getCharacterByID(char1.getId());
        assertTrue(char2.getName().equals("alice"));
        handler.deleteCharacter(char1);
        handler.close();
    }

    @Test
    public void cascadeDeleteTest(){
        // Context of the app under test.
        DatabaseHandler handler = getHandler();
        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        Item item1 = new Item("test item", 300, inv1.getId());
        item1 = handler.addItem(item1);
        assertTrue("Item not added.", item1.getId()>-1);

        int deleted = handler.deleteCharacter(char1);

        assertTrue("Character not deleted.", deleted>0);
        assertTrue("too many Characters deleted. Characters Deleted: "+deleted, deleted<2);

        inv1 = handler.getInventoryByID(inv1.getId());
        assertTrue("Inventory not Deleted.", inv1 == null);

        item1 = handler.getItemByID(item1.getId());
        assertTrue("Item not Deleted.", item1 == null);

        handler.close();
    }

    @Test
    public void deleteCharacterTest(){
        // Context of the app under test.
        DatabaseHandler handler = getHandler();
        Character char1;
        char1 = new Character("Bob", 12, 12);
        char1 = handler.addCharacter(char1);
        assertTrue("Character not added.", char1.getId()>-1);

        int deleted = handler.deleteCharacter(char1);

        assertTrue("Character not deleted.", deleted>0);
        assertTrue("too many Characters deleted. Characters Deleted: "+deleted, deleted<2);

        handler.close();
    }

    @Test
    public void addCharacterTest(){
        Character char1, char2;
        char1 = new Character("Bob", 12, 12);
        DatabaseHandler handler = getHandler();

        char2 = handler.addCharacter(char1);
        System.out.print(char2.getId());
        assertTrue(char2.getId() > -1);
        handler.deleteCharacter(char1);

        handler.close();
    }

    @Test
    public void getCharacterbyIdTest(){

        DatabaseHandler handler = getHandler();

        Character char1, char2;
        char1 = new Character("Bob");

        char1 = handler.addCharacter(char1);
        handler.updateCharacter(char1);

        handler.updateCharacter(char1);
        char2 = handler.getCharacterByID(char1.getId());
        assertTrue(char2.getName().equals("Bob"));
        handler.deleteCharacter(char2);
        handler.close();
    }

    @Test
    public void getAllCharactersTest(){
        DatabaseHandler handler = getHandler();
        ArrayList<Character> characters;
        //characters = handler.getAllCharacters();
        //assertTrue(characters.size() < 1);

        Character char1 = handler.addCharacter(new Character("Bob"));
        characters = handler.getAllCharacters();

        handler.deleteCharacter(char1);

        assertTrue(characters.size() > 0);
        handler.close();
    }

    @Test
    public void getAllCharactersIDAndNameRowsTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseHandler handler = DatabaseHandler.getInstance(appContext);
        Character char1 = handler.addCharacter(new Character("Bob"));
        IDandNameRow[] characters;
        characters = handler.getAllCharacterIDAndNameRow();
        assertTrue(characters.length > 0);
        handler.deleteCharacter(char1);
        handler.close();
    }

    @Test
    public void deleteItemTest(){
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseHandler handler = DatabaseHandler.getInstance(appContext);
        Character char1;
        char1 = new Character("Bob");
        char1 = handler.addCharacter(char1);
        Inventory inv1;
        inv1 = new Inventory(char1.getId());
        inv1 = handler.addInventory(inv1);

        Item item1 = new Item("test item", 300, inv1.getId());
        item1 = handler.addItem(item1);
        assertTrue("Item not added.", item1.getId()>-1);

        int deleted = handler.deleteItem(item1);

        assertTrue("Item not deleted.", deleted>0);
        assertTrue("too many Items deleted. Items Deleted: "+deleted, deleted<2);
        handler.deleteInventory(inv1);
        handler.deleteCharacter(char1);

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

        assertTrue("ERROR: Item not added. "+item1.getId(),item1.getId()>-1);
        handler.deleteCharacter(char1);
        handler.deleteInventory(inv1);
        handler.deleteItem(item1);
        handler.close();
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
        handler.deleteCharacter(char1);
        handler.deleteInventory(inv1);
        handler.deleteItem(item1);
        handler.close();
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
        handler.deleteInventory(inv1);
        handler.deleteCharacter(char1);
        handler.deleteItem(item1);

        assertTrue(item1.equals(item2));
    }

    private DatabaseHandler getHandler(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        return DatabaseHandler.getInstance(appContext);
    }

}
