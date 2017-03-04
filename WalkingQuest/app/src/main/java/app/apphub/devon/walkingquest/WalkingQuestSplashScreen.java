package app.apphub.devon.walkingquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WalkingQuestSplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_quest_splash_screen);
        /** Test code to be removed later, simply tests that the splash screen will move to the
         *  login screen    */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(WalkingQuestSplashScreen.this, LoginActivity.class));
    }
}
