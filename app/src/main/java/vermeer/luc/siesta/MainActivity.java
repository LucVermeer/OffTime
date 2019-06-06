package vermeer.luc.siesta;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akaita.android.circularseekbar.CircularSeekBar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends AppCompatActivity {
    private boolean countingDown;
    private TextView timerText;
    private CircularSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countingDown = false;

        timerText = findViewById(R.id.timerText);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setRingColor(Color.GREEN);

        seekBar.setOnCenterClickedListener(new CircularSeekBar.OnCenterClickedListener() {
            @Override
            public void onCenterClicked(CircularSeekBar seekBar, float progress) {
                // Nothing
            }
        });
        seekBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (!countingDown){
                    timerText.setText(String.format("%02d:00", (int) progress));
                    if (progress < 30) {
                        seekBar.setRingColor(Color.GREEN);
                    } else if (progress < 60) {
                        seekBar.setRingColor(Color.YELLOW);
                    } else {
                        seekBar.setRingColor(Color.RED);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                // Nothing

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                // Nothing
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_timer:
                        break;
                    case R.id.action_achievements:
                        Intent achievementIntent = new Intent(MainActivity.this, AchievementsActivity.class);
                        startActivity(achievementIntent);
                        break;
                    case R.id.action_statistics:
                        Intent statisticsIntent = new Intent(MainActivity.this, StatisticsActivity.class);
                        startActivity(statisticsIntent);
                        break;
                    case R.id.action_settings:
                        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                }
                return true;
            }
        });
    }

    public void startTimer(View view) {
        if (!countingDown){
            countingDown = true;
            int millis = 60000 * (int) seekBar.getProgress();
            new CountDownTimer(millis, 1000) {
                public void onTick(long millisUntilFinished) {
                    int secondsUntilFinished = (int) millisUntilFinished / 1000;
                    int minutesUntilFinished = secondsUntilFinished / 60;
                    int secondsWithoutMinute = secondsUntilFinished % 60;
                    timerText.setText(String.format("%02d:%02d", minutesUntilFinished, secondsWithoutMinute));
                }

                public void onFinish() {
                    countingDown = false;
                    confetti();
                }
            }.start();
        }
    }

    public void confetti () {
        final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.BLUE, Color.RED)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
    }
}