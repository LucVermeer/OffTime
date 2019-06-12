package vermeer.luc.siesta;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akaita.android.circularseekbar.CircularSeekBar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class TimerFragment extends Fragment {
    private boolean countingDown;
    private TextView timerText;
    private CircularSeekBar seekBar;
    private Button startButton;
    private int minutes;
    private View myFragmentView;
    private CountDownTimer timer;

    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTimer();
            }
        });

        return myFragmentView;
    }

    public void startTimer() {
        if (!countingDown){
            countingDown = true;
            minutes = (int) seekBar.getProgress();
            int millis = 60000 * minutes;
            new CountDownTimer(millis, 100) {
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
}