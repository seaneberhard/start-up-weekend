package illenial.dosediary;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Countdown extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TrackerPile mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each tab
        mSectionsPagerAdapter = new TrackerPile(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countdown, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class Tracker extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private String label;
        private ArrayList<Pair<Integer, Long>> limits;
        private ArrayList<Date> log;

        public Tracker() {
            label = "";
            limits = new ArrayList<Pair<Integer, Long>>();
            log = new ArrayList<Date>();
        }

        public void setLabel(String label) { this.label = label; }

        public void addLimit(int quantity, long time) { limits.add(Pair.<Integer, Long>create(quantity, time)); }

        public void pop(View view) {
            if (timeLeft() > 0) return;
                log.add(new Date());
                // todo: save state in shared prefs
            countdown(view);
        }

        public long timeLeft() {
            Date now = new Date();
            long left = 0;
            for (Pair<Integer, Long> limit : limits) {
                int quantity = limit.first;
                long time = limit.second;
                if (log.size() < quantity) {
                    continue;
                }
                Date pivot = log.get(log.size() - quantity);
                long diff = (pivot.getTime() + time) - now.getTime();
                if (left < diff) {
                    left = diff;
                }
            }
            return left;
        }

        public String log() {
            String str = "";
            for(Date d : log) {
                str = d.toString() + "\n" + str;
            }
            return str;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_countdown, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(label);
            countdown(rootView);
            return rootView;
        }


        private void countdown(View rootView) {
            final TextView text = (TextView) rootView.findViewById(R.id.countdown);
            new CountDownTimer(timeLeft(), 1000) {
                @Override
                public void onTick(long l) {
                    text.setText(String.format(Locale.US, "%02d:%02d:%02d", l / (3600 * 1000), l / (60 * 1000) % 60, l / 1000 % 60));
                }

                @Override
                public void onFinish() {
                    text.setText(R.string.log_dose_text);
                }
            }.start();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class TrackerPile extends FragmentPagerAdapter {

        private ArrayList<Tracker> trackers;

        public TrackerPile(FragmentManager fm)
        {
            super(fm);
            // todo: attempt to deserialize saved state, else continue
            trackers = new ArrayList<Tracker> ();

            // add default tracker
            Tracker t = new Tracker();
            t.addLimit(1, 4*3600*1000);
            t.addLimit(4, 24*3600*1000);
            trackers.add(t);
        }

        @Override
        public Fragment getItem(int position) { return trackers.get(position); }

        @Override
        public int getCount() { return trackers.size(); }
    }

    private void serialize() {
        // todo : serialize state
    }
}
