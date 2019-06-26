/*
 *   DbHelper
 *   This class holds the main and only activity for this app, in this activity the different
  *   fragments are loaded.
 * */


package vermeer.luc.siesta;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.work.Data;

import com.akaita.android.circularseekbar.CircularSeekBar;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Fragment timerFragment;
    private Fragment settingsFragment;
    private Fragment statisticsFragment;
    private ActionBar actionBar;


    public int random;
    public String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create tags for the WorkManager
        random = (int )(Math.random() * 50 + 1);
        tag = generateKey();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Timer");

        timerFragment = new TimerFragment();
        settingsFragment = new SettingsFragment();
        statisticsFragment = new StatisticsFragment();

        loadFragment(timerFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // This switch case loads the right fragment when a button is pressed.
                switch (item.getItemId()) {
                    case R.id.action_timer:
                        loadFragment(timerFragment);
                        actionBar.setTitle("Timer");
                        break;
                    case R.id.action_statistics:
                        actionBar.setTitle("Statistics");
                        loadFragment(statisticsFragment);
                        break;
                    case R.id.action_settings:
                        actionBar.setTitle("Settings");
                        loadFragment(settingsFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // Starts a transaction to go from one fragment to another, the frame in the
        // MainActivity is replaced with a the new fragment.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Generate a key for the WorkManager.
    private String generateKey() {
        return UUID.randomUUID().toString();
    }
}