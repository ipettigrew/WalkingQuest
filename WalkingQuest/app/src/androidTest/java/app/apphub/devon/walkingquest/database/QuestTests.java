package app.apphub.devon.walkingquest.database;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by cole_ on 2/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class QuestTests {

    @Test
    public void equalsTest() throws Exception {
        // Context of the app under test.
        Quest quest1, quest2;
        quest1 = new Quest("test", 20000, 2);
        quest2 = new Quest(quest1.getId(),quest1.getName(),quest1.getActiveSteps(),
                            quest1.getStepGoal(), quest1.getUserID(), quest1.isCompleted(),quest1.getDifficulty());
        assertTrue(quest1.equals(quest2));
    }
}