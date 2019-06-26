/*
*   TimerFragment
*   In this fragment users can start a siesta. This fragment also shows the confetti.
* */
package vermeer.luc.siesta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akaita.android.circularseekbar.CircularSeekBar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import vermeer.luc.siesta.DbHelper;
import vermeer.luc.siesta.MainActivity;
import vermeer.luc.siesta.R;
import vermeer.luc.siesta.TimerService;


public class TimerFragment extends Fragment {
    private boolean countingDown;
    private TextView timerText;
    private CircularSeekBar seekBar;
    private Button startButton;
    private int minutes;
    private View myFragmentView;
    private CountDownTimer timer;

    private int random;
    private String tag;

    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        random = mainActivity.random;
        tag = mainActivity.tag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_timer, container, false);
        timerText = myFragmentView.findViewById(R.id.timerText);
        seekBar = myFragmentView.findViewById(R.id.seekbar);
        startButton = myFragmentView.findViewById(R.id.startButton);
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
                // Change the color of the progressbar, but only if not counting down.
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
            // Two necessary functions but don't do anything.
            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) { }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTimer();
            }
        });
        return myFragmentView;
    }

    public void startTimer() {
        // First checks if counter is already running/ no value is set.
        // Starts a timer with the give amount of minutes.
        if (countingDown) {
            Toast.makeText(getActivity(), "Already counting down!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (seekBar.getProgress() == 0.0){
            Toast.makeText(getActivity(), "Set a time!", Toast.LENGTH_SHORT).show();
            return;
        }
        countingDown = true;
        minutes = (int) seekBar.getProgress();
        int millisInMinute = 60000;
        int millis = millisInMinute * minutes;

        timer = new CountDownTimer(millis, 100) {
            public void onTick(long millisUntilFinished) {
                int secondsUntilFinished = (int) millisUntilFinished / 1000;
                int minutesUntilFinished = secondsUntilFinished / 60;
                int secondsWithoutMinute = secondsUntilFinished % 60;
                timerText.setText(String.format("%02d:%02d", minutesUntilFinished, secondsWithoutMinute));
            }
            public void onFinish() {
                // On finish sets the appropiate variables, setProgress is brought back to 0
                // in case the user set the seekbar to a different value during countdown.
                countingDown = false;
                seekBar.setProgress(0);
                confetti();
                saveSiesta(minutes);
                stopTimerService();
            }
        };
        timer.start();
        startTimerService(millis);
    }

    public void saveSiesta(int minutes){
        // Saves siesta to database.
        DbHelper db = DbHelper.getInstance(getActivity());
        db.saveSiesta(minutes);
        Toast.makeText(getActivity(), "Siesta finished!", Toast.LENGTH_SHORT).show();
    }

    public void confetti () {
        // Builds the confetti view and executes it.
        final KonfettiView konfettiView = myFragmentView.findViewById(R.id.viewKonfetti);
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

    public void startTimerService(int millis) {
        // Sets up the serviceIntent with the milli seconds provide by the users.
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        serviceIntent.putExtra("int_millis", millis);
        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    public void stopTimerService() {
        // Stops the timerService.
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        getActivity().stopService(serviceIntent);
    }
}