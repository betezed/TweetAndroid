package fr.ecp.sio.superchat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.FollowActivity;
import fr.ecp.sio.superchat.PostActivity;
import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.TabsActivity;
import fr.ecp.sio.superchat.TweetsActivity;
import fr.ecp.sio.superchat.adapter.UsersAdapter;
import fr.ecp.sio.superchat.loaders.UsersLoader;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Betezed on 05/12/2014.
 */
public class UsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int LOADER_USERS = 1000;
    private static final int REQUEST_LOGIN_FOR_POST = 1;

    public static final int ALL_USERS = 0;
    public static final int FOLLOWER_USERS = 1;
    public static final int FOLLOWING_USERS = 2;
    public static final String LIST_TYPE = "listUsers";
    public static final String HANDLE = "handle";


    private UsersAdapter mListAdapter;
    private boolean mIsMasterDetailsMode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new UsersAdapter();
        setListAdapter(mListAdapter);
        view.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsMasterDetailsMode = getActivity().findViewById(R.id.tweets_content) != null;
        if (mIsMasterDetailsMode) {
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        }
        if (getActivity() instanceof FollowActivity) {
            int listType = ALL_USERS;
            String title;
            if (getArguments() != null)
                listType = getArguments().getInt(LIST_TYPE);
            switch (listType) {
                case FOLLOWING_USERS:
                    title = getString(R.string.followings);
                    break;
                case FOLLOWER_USERS:
                    title = getString(R.string.followers);
                    break;
                default:
                    title = "";
            }
            getActivity().setTitle(title);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USERS, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        int listType = ALL_USERS;
        String handle = AccountManager.getUserHandle(getActivity());
        if (getArguments() != null) {
            listType = getArguments().getInt(LIST_TYPE);
            if (getArguments().containsKey(TweetsFragment.ARG_USER)) {
                User user = getArguments().getParcelable(TweetsFragment.ARG_USER);
                handle = user.getHandle();
            }
        }
        return new UsersLoader(getActivity(), listType, handle);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        mListAdapter.setUsers(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        User user = mListAdapter.getItem(position);
        if (mIsMasterDetailsMode) {
            Fragment tweetsFragment = new TweetsFragment();
            tweetsFragment.setArguments(TweetsFragment.setUserArgument(user));
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tweets_content, tweetsFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), TabsActivity.class);
            intent.putExtras(TweetsFragment.setUserArgument(user));
            startActivity(intent);
        }
    }

    private void post() {
        if (AccountManager.isConnected(getActivity())) {
            startActivity(new Intent(getActivity(), PostActivity.class));
        } else {
            LoginFragment fragment = new LoginFragment();
            fragment.setTargetFragment(this, REQUEST_LOGIN_FOR_POST);
            fragment.show(getFragmentManager(), "login_dialog");
        }
    }
}