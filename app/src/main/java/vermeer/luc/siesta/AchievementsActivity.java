package vermeer.luc.siesta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class AchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_timer:
                        Intent timerIntent = new Intent(AchievementsActivity.this, MainActivity.class);
                        startActivity(timerIntent);
                        break;
                    case R.id.action_achievements:
                        break;
                    case R.id.action_statistics:
                        Intent achievementIntent = new Intent(AchievementsActivity.this, StatisticsActivity.class);
                        startActivity(achievementIntent);
                        break;
                    case R.id.action_settings:
                        Intent statisticsIntent = new Intent(AchievementsActivity.this, SettingsActivity.class);
                        startActivity(statisticsIntent);
                        break;
                }
                return true;
            }
        });
    }
}
