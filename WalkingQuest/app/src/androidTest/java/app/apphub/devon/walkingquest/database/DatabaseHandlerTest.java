package app.apphub.devon.walkingquest.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by cole_ on 2/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseHandlerTest {
    @Test
    public void createQuestDatabase() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);
        assertTrue(handler != null);
        handler.close();
    }

    @Test
    public void addQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest1, quest2 = new Quest("test", 20000, 2);

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        quest1 = handler.addQuest(quest2);
        assertTrue(quest1.getName().equals(quest2.getName()));
        handler.close();
    }



    @Test
    public void getQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest1 = new Quest("test", 55555, 3);

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        handler.addQuest(quest1);
        quest1 = handler.addQuest(quest1);

        Quest quest2 = handler.getQuestByID(quest1.getId());
        assertTrue(quest2.equals(quest2));
        handler.close();
    }

    @Test
    public void updateQuestTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Quest quest2, quest1;
        quest1 = new Quest("test", 666, 1);

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        quest2 = handler.addQuest(quest1);
        assertTrue(quest1.equals(quest2));
        quest1.setName("test2");

        handler.updateQuest(quest1);
        quest2 = handler.getQuestByID(quest1.getId());
        assertTrue(quest2.getName().equals("test2"));
        handler.close();
    }
    @Test
    public void addUserTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        User user1, user2 = new User("test");
        Log.v("GetUserID", "Logging test");

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        user1 = handler.addUser(user2);
        assertTrue(user1.getName().equals(user2.getName()));
        System.err.println("User ID: "+user1.getId());
        handler.close();
    }



    @Test
    public void getUserTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        User user1 = new User("test");

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        handler.addUser(user1);
        user1 = handler.addUser(user1);

        Log.d("GetUserID", ""+user1.getId());
        User user2 = handler.getUserByID(user1.getId());
        assertTrue(user2.equals(user2));
        handler.close();
    }

    @Test
    public void updateUserTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        User user2, user1;
        user1 = new User("test");

        DatabaseHandler handler = null;
        handler = new DatabaseHandler(appContext);

        user2 = handler.addUser(user1);
        assertTrue(user1.equals(user2));
        user1.setName("test2");

        handler.updateUser(user1);
        user2 = handler.getUserByID(user1.getId());
        assertTrue(user2.getName().equals("test2"));
        handler.close();
    }
}
