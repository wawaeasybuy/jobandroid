package com.mardin.job;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.mardin.job.activities.AddJobActivity;
import com.mardin.job.activities.JobDetailActivity;
import com.mardin.job.activities.JobPageActivity;
import com.mardin.job.activities.LoginActivity;
import com.mardin.job.fragments.JobListFragment;
import com.mardin.job.fragments.MeFragment;
import com.mardin.job.fragments.SettingFragment;
import com.mardin.job.fragments.zhiwei.HomeFragment;
import com.mardin.job.fragments.zhiwei.TopicFragment;
import com.mardin.job.network.Constants;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, JobListFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private JobListFragment mJobListFragment;
    private SettingFragment mSettingFragment;
    private MeFragment mMeFragment;
    private TopicFragment topicFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        //this.getActionBar().setTitle(mTitle);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

//        if (Constants.getToken(this).equals("")) {
//            this.gotoLogin();
//        }else {
//            Toast.makeText(this, "you have signed in", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
        Fragment fragment = null;
        switch (position) {
            case 0:
                if (mJobListFragment != null) {
                    fragment = mJobListFragment;
                }else {
                    mJobListFragment = new JobListFragment();
                    fragment = mJobListFragment;
                }
//                fragment = JobListFragment.newInstance("job", "list");

                break;
            case 1:
                if (mMeFragment != null) {
                    fragment = mMeFragment;
                }else {
                    mMeFragment = new MeFragment();
                    fragment = mMeFragment;
                }
//                fragment = SettingFragment.newInstance("setting","fragment");

                break;
            case 2:
                if (topicFragment != null) {
                    fragment = topicFragment;
                }else {
                    topicFragment = new TopicFragment();
                    fragment =topicFragment;
                }
//                fragment = SettingFragment.newInstance("setting","fragment");

                break;
            case 3:
                if (homeFragment != null) {
                    fragment = homeFragment;
                }else {
                    homeFragment = new HomeFragment();
                    fragment =homeFragment;
                }
//                fragment = SettingFragment.newInstance("setting","fragment");

                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {

        }


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            if (Constants.getToken(this).equals("")) {
//                this.gotoLogin();
//            }else {
//                Toast.makeText(this, "you have signed in", Toast.LENGTH_SHORT).show();
//            }
//
//            return true;
//        }else if (id == R.id.action_logout) {
//            if (Constants.getToken(this).equals("")) {
//
//                Toast.makeText(this, "you have not logged in", Toast.LENGTH_SHORT).show();
//            }else {
//                Constants.setToken(this, "");
//                Toast.makeText(this, "you have successfully logged out", Toast.LENGTH_SHORT).show();
//            }
//            return true;
//        }else
        if (id == R.id.action_addjob) {
            if (Constants.getToken(this).equals("")) {
                this.gotoLogin();
            }else {
                this.gotAddJob();
                //Toast.makeText(this, "you have signed in", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentInteraction(String id) {

    }

    public void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        this.startActivity(intent);
    }

    public void gotAddJob() {
        Intent intent = new Intent(this, JobPageActivity.class);
        //Intent intent = new Intent(this, AddJobActivity.class);
        //intent.putExtra("hospParam", hospParam);
        this.startActivity(intent);
    }

    public void gotoJobDetail() {
        Intent intent = new Intent(this, JobDetailActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.LoginIntent:
                if (resultCode == RESULT_OK) {
                    mMeFragment.loadMe();
                }

                break;
            case Constants.UPEMPLOYER_INTENT:
                if (resultCode == RESULT_OK) {
                    mMeFragment.loadMe();
                }
                break;
            case Constants.UPECANDIDATE_INTENT:
                if (resultCode == RESULT_OK) {
                    mMeFragment.loadMe();
                }
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
