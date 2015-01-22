package fr.ecp.sio.superchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import fr.ecp.sio.superchat.fragment.TweetsFragment;
import fr.ecp.sio.superchat.fragment.UsersFragment;

/**
 * Created by betezed on 22/01/15.
 */
public class FollowActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity);

        if (savedInstanceState == null) {
            Fragment userFragment = new UsersFragment();
            userFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.follow_content, userFragment)
                    .commit();
        }
    }
}
