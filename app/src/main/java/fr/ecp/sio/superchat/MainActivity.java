package fr.ecp.sio.superchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import fr.ecp.sio.superchat.adapter.MenuDrawerAdapter;
import fr.ecp.sio.superchat.fragment.LoginFragment;
import fr.ecp.sio.superchat.fragment.TweetsFragment;
import fr.ecp.sio.superchat.fragment.UsersFragment;
import fr.ecp.sio.superchat.model.NavigationDrawerItem;
import fr.ecp.sio.superchat.model.User;

public class MainActivity extends ActionBarActivity {
    ListView mDrawerList;
    MenuDrawerAdapter mDrawerAdapter;
    DrawerLayout mDrawerLayout;

    private static final int REQUEST_LOGIN_FOR_POST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            UsersFragment usersFragment = new UsersFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, usersFragment)
                    .commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mDrawerLayout.findViewById(R.id.drawer_list);
        mDrawerAdapter = new MenuDrawerAdapter();
        mDrawerAdapter.addMenuItems(this, AccountManager.isConnected(this));
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        Fragment fragment;
        LoginFragment loginFragment;
        int id = ((NavigationDrawerItem) mDrawerAdapter.getItem(position)).getId();
        switch (id) {
            case 0:
                loginFragment = new LoginFragment();
                loginFragment.setTargetFragment(new UsersFragment(), REQUEST_LOGIN_FOR_POST);
                loginFragment.show(getSupportFragmentManager(), "login_dialog");
                break;
            case 1:
                updateNavigationDrawer();
                break;
            case 2:
                Intent intent = new Intent(this, TweetsActivity.class);
                User user = new User();
                user.setHandle(AccountManager.getUserHandle(this));
                intent.putExtras(TweetsFragment.setUserArgument(user));
                startActivity(intent);
                break;
            case 3:
                fragment = new UsersFragment();
                Bundle following_args = new Bundle();
                following_args.putInt(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWING_USERS);
                fragment.setArguments(following_args);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 4:
                fragment = new UsersFragment();
                Bundle followers_args = new Bundle();
                followers_args.putInt(UsersFragment.LIST_TYPE, UsersFragment.FOLLOWER_USERS);
                fragment.setArguments(followers_args);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commit();
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 5:
                AccountManager.logout(this);
                Toast.makeText(this, R.string.deconnexion, Toast.LENGTH_SHORT).show();
                updateNavigationDrawer();
                break;
        }
    }
    public void updateNavigationDrawer() {
        mDrawerAdapter.addMenuItems(this, AccountManager.isConnected(this));
        UsersFragment usersFragment = new UsersFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_content, usersFragment)
            .commit();
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
