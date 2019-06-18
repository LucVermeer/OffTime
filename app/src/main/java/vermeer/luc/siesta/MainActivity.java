package vermeer.luc.siesta;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.akaita.android.circularseekbar.CircularSeekBar;

public class MainActivity extends AppCompatActivity {

    private Fragment timerFragment;
    private Fragment settingsFragment;
    private Fragment statisticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Timer");
        timerFragment = new TimerFragment();
        settingsFragment = new SettingsFragment();
        statisticsFragment = new StatisticsFragment();
        loadFragment(timerFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_timer:
                        loadFragment(timerFragment);
                        getSupportActionBar().setTitle("Timer");
                        break;
                    case R.id.action_statistics:
                        getSupportActionBar().setTitle("Statistics");
                        loadFragment(statisticsFragment);
                        break;
                    case R.id.action_settings:
                        getSupportActionBar().setTitle("Settings");
                        loadFragment(settingsFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}