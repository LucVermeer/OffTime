/*
 *   StatisticsFragment
 *   In this fragment a user can see statistics about his siesta history.
 * */

package vermeer.luc.siesta;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatisticsFragment extends Fragment {
    private View myFragmentView;
    private DbHelper db;

    public StatisticsFragment() {
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
        myFragmentView = inflater.inflate(R.layout.fragment_statistics, container, false);
        db = DbHelper.getInstance(getActivity());
        Cursor cursor = db.getSiestas();
        if (!cursor.moveToFirst()){
            return myFragmentView;
        }

        TextView siestaCount = myFragmentView.findViewById(R.id.siestaCount);
        siestaCount.setText("Total siestas taken: " + siestaCount(cursor));

        TextView siestaSum = myFragmentView.findViewById(R.id.siestaSum);
        siestaSum.setText("Total time focused: " + siestaSum(cursor) + " minutes");

        TextView siestaLongest = myFragmentView.findViewById(R.id.siestaLongest);
        siestaLongest.setText("Longest siesta taken: " + siestaLongest(cursor) + " minutes");

        cursor.close();


        return myFragmentView;
    }

    private int siestaCount(Cursor c) {
        // Counts all the siestas taken by looping through the cursor and counting instances.
        int total_siesta = 1;
        while (c.moveToNext()) {
            total_siesta += 1;
        }
        c.moveToFirst();
        return total_siesta;
    }

    private int siestaSum(Cursor c) {
        // Calculates the sum of all siesta lengths by looping through the cursor and adding
        // all the lengths.
        int sum = 0;
        while (c.moveToNext()){
            sum +=  c.getInt(1);
        }
        c.moveToFirst();
        return sum;
    }

    private int siestaLongest(Cursor c) {
        // Finds the longest siesta by looping through all siestas and replacing the max whenever
        // the value is higher than the current.
        int max = 0;
        while (c.moveToNext()) {
            if (c.getInt(1) > max) {
                max = c.getInt(1);
            }
        }
        return max;
    }
}
