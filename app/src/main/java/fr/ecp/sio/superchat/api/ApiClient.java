package fr.ecp.sio.superchat.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.fragment.UsersFragment;
import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 12/12/2014.
 */
public class ApiClient {

    private static final String API_BASE = "http://hackndo.com:5667/mongo/";

    public String login(String handle, String password) throws IOException {
        String url = Uri.parse(API_BASE + "session/").buildUpon()
                .appendQueryParameter("handle", handle)
                .appendQueryParameter("password", password)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Login: " + url);
        InputStream stream = new URL(url).openStream();
        return IOUtils.toString(stream);
    }

    public List<User> getUsers(Context context, int listUserType, String token) throws IOException {
        String api_route;
        String handle;
        switch (listUserType) {
            case UsersFragment.FOLLOWER_USERS:
                handle = AccountManager.getUserHandle(context);
                api_route = handle + "/followers/";
                break;
            case UsersFragment.FOLLOWING_USERS:
                handle = AccountManager.getUserHandle(context);
                api_route = handle + "/followings/";
                break;
            default:
                api_route = "users/";
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(API_BASE + api_route).openConnection();
        if (token != null)
            connection.setRequestProperty("Authorization", "Bearer-" + token);

        InputStream stream = connection.getInputStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public List<Tweet> getUserTweets(String handle) throws IOException {
        InputStream stream = new URL(API_BASE + handle + "/tweets/").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
    }

    public void postTweet(String handle, String token, String content) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/tweets/post/").buildUpon()
                .appendQueryParameter("content", content)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Add tweet: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
        //InputStream in = new BufferedInputStream(connection.getInputStream());

    }

    public void followUser(String handle, String token, String userToFollowHandle) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/followings/post/").buildUpon()
                .appendQueryParameter("handle", userToFollowHandle)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Add following: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
    }

    public void unFollowUser(String handle, String token, String userToUnfollowHandle) throws IOException {
        String url = Uri.parse(API_BASE + handle + "/followings/delete/").buildUpon()
                .appendQueryParameter("handle", userToUnfollowHandle)
                .build().toString();
        Log.i(ApiClient.class.getName(), "Remove following: " + url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer-" + token);
        connection.getInputStream();
    }
}