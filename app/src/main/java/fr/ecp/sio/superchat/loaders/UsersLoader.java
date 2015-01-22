package fr.ecp.sio.superchat.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.api.ApiClient;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Betezed on 05/12/2014.
 */
public class UsersLoader extends AsyncTaskLoader<List<User>> {

    private List<User> mResult;
    private int mListUserType;
    private String mHandle;

    public UsersLoader(Context context, int listUserType, String handle) {
        super(context);
        mListUserType = listUserType;
        mHandle = handle;
    }

    @Override
    public List<User> loadInBackground() {
        try {
            String token = null;
            if (AccountManager.isConnected(getContext())) token = AccountManager.getUserToken(getContext());
            return new ApiClient().getUsers(getContext(), mListUserType, mHandle, token);
        } catch (IOException e) {
            Log.e(UsersLoader.class.getName(), "Failed to download users", e);
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<User> data) {
        mResult = data;
        super.deliverResult(data);
    }

}