package vermeer.luc.siesta;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    private View myFragmentView;
    private SeekBar seekBar;
    private TextView progressText;
//    private Button saveProd;
    private SaveSettings db;

    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SaveSettings.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        seekBar = myFragmentView.findViewById(R.id.prodSeekBar);
        progressText = myFragmentView.findViewById(R.id.progressText);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener());
        seekBar.setProgress(getProductivity());

//        saveProd = myFragmentView.findViewById(R.id.prodButton);
//        saveProd.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                saveProductivity();
//            }
//        });
        return myFragmentView;
    }

    private int getProductivity() {
        return db.getProductivity();
    }

    private void saveProductivity(){
        SaveSettings db = SaveSettings.getInstance(getActivity());
        db.saveProductivity(seekBar.getProgress());
//        Toast.makeText(getActivity(), "Productivity saved!", Toast.LENGTH_SHORT).show();
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            //set textView's text
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
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

    }
}
