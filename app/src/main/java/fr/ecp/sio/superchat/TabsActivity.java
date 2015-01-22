package fr.ecp.sio.superchat;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import fr.ecp.sio.superchat.fragment.UsersFragment;

/**
 * Created by betezed on 22/01/15.
 */
public class TabsActivity extends TabActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabHost.TabSpec tabFollowings = tabHost.newTabSpec(getString(R.string.followings));
        TabHost.TabSpec tabTweets = tabHost.newTabSpec(getString(R.string.tweets));
        TabHost.TabSpec tabFollowers = tabHost.newTabSpec(getString(R.string.followers));

        Intent intent = new Intent(this, FollowActivity.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWING_USERS);

        tabFollowings.setIndicator(getString(R.string.followings));
        tabFollowings.setContent(intent);

        intent = new Intent(this, TweetsActivity.class);
        intent.putExtras(getIntent().getExtras());

        tabTweets.setIndicator(getString(R.string.tweets));
        tabTweets.setContent(intent);

        intent = new Intent(this, FollowActivity.class);
        intent.putExtras(getIntent().getExtras());
        intent.putExtra(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWER_USERS);

        tabFollowers.setIndicator(getString(R.string.followers));
        tabFollowers.setContent(intent);

        tabHost.addTab(tabFollowings);
        tabHost.addTab(tabTweets);
        tabHost.addTab(tabFollowers);

        tabHost.setCurrentTabByTag(getString(R.string.tweets));

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.material_blue_grey_800));
        }
    }
}