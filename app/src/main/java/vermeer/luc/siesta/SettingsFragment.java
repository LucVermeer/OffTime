/*
*   SettingsFragment
*   In this fragment a user can adjust his productivity and notification settings.
* */
package vermeer.luc.siesta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import vermeer.luc.siesta.DbHelper;
import vermeer.luc.siesta.MainActivity;
import vermeer.luc.siesta.NotificationHandler;
import vermeer.luc.siesta.R;


public class SettingsFragment extends Fragment {

    private View myFragmentView;
    private SeekBar seekBar;
    private TextView progressText;
    private DbHelper db;
    private Switch notifySwitch;

    private int random;
    private String tag;

    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DbHelper.getInstance(getActivity());

        MainActivity mainActivity = (MainActivity) getActivity();
        random = mainActivity.random;
        tag = mainActivity.tag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        seekBar = myFragmentView.findViewById(R.id.prodSeekBar);
        progressText = myFragmentView.findViewById(R.id.progressText);

        // Set listeners to switch and seekbar
        notifySwitch = myFragmentView.findViewById(R.id.switch1);
        notifySwitch.setOnCheckedChangeListener(new onSwitchListener());
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener());
        seekBar.setProgress(getProductivity());

        return myFragmentView;
    }

    private int getProductivity() {
        // Retrieves productivity from database.
        return db.getProductivity();
    }

    private void saveProductivity(){
        //  Saves productivity.
        DbHelper db = DbHelper.getInstance(getActivity());
        db.saveProductivity(seekBar.getProgress());
    }

    private class onSwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // Starts notification handler if true, ends it if false.
            if (b) {
                NotificationHandler.scheduleReminder(db);
            } else {
                NotificationHandler.cancelReminder(tag);
            }
        }
    }


    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // Set textView's text and color. Also saves whenever a change is made.
            String productivityIndicator;
            switch(progress) {
                case 0:
                    productivityIndicator = "Really not productive";
                    progressText.setTextColor(Color.parseColor("#ad1313"));
                    break;
                case 1:
                    productivityIndicator = "Not really productive";
                    progressText.setTextColor(Color.parseColor("#ad5f13"));
                    break;
                default:
                    productivityIndicator = "Productive";
                    progressText.setTextColor(Color.parseColor("#ad9313"));
                    break;
                case 3:
                    productivityIndicator = "Really roductive";
                    progressText.setTextColor(Color.parseColor("#86ad13"));
                    break;
                case 4:
                    productivityIndicator = "REALLY productive";
                    progressText.setTextColor(Color.parseColor("#53ad13"));
                    break;
            }
            progressText.setText(productivityIndicator);
            saveProductivity();

            // If switch is checked make a new WorkManager with adjusted productivity interval.
            if (notifySwitch.isChecked()){
                NotificationHandler.cancelReminder(tag);
                NotificationHandler.scheduleReminder(db);
            }
        }

        // Two necessary empty functions.
        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}
    }
}
