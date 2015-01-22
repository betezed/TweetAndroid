package fr.ecp.sio.superchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import fr.ecp.sio.superchat.fragment.TweetsFragment;

/**
 * Created by Michaël on 05/12/2014.
 */
public class TweetsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

        if (savedInstanceState == null) {
            Fragment tweetsFragment = new TweetsFragment();
            tweetsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.tweet_content, tweetsFragment)
                    .commit();
        }
    }

}
