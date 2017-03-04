package app.apphub.devon.walkingquest.database;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by cole_ on 2/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class UserTests {

    @Test
    public void equalsTest() throws Exception {
        // Context of the app under test.
        User user1, user2;
        user1 = new User("test");
        user2 = new User(user1.getId(),user1.getName(),user1.getSteps(),user1.getQuestsCompleted());
        assertTrue(user1.equals(user2));
    }
}
