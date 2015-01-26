package fr.ecp.sio.superchat.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.MainActivity;
import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.api.ApiClient;
import fr.ecp.sio.superchat.fragment.UsersFragment;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Betezed on 05/12/2014.
 */
public class UsersAdapter extends BaseAdapter {

    private static final int IS_FOLLOWING = 1;
    private static final int IS_NOT_FOLLOWING = 0;
    private List<User> mUsers;

    public void setUsers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public User getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }
        User user = getItem(position);
        final TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        if (user.getHandle().equals(AccountManager.getUserHandle(parent.getContext()))) {
            handleView.setText(R.string.me);
        } else {
            handleView.setText(user.getHandle());
        }

        TextView statusView = (TextView) convertView.findViewById(R.id.status);
        switch (user.getStatus()) {
            case "online": statusView.setText(R.string.online); break;
            case "offline": statusView.setText(R.string.offline); break;
            default: statusView.setText("");
        }
        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getProfilePicture()).into(profilePictureView);
        final Button button = (Button) convertView.findViewById(R.id.following);
        if (AccountManager.isConnected(parent.getContext())
                && !user.getHandle().equals(AccountManager.getUserHandle(parent.getContext()))
                && parent.getContext().getClass().getName().equals(MainActivity.class.getName())) {
            setIsFollowing(button, user.isFollowing());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AsyncTask<View, Void, Void>() {

                        @Override
                        protected Void doInBackground(View... params) {
                            try {
                                if ((Integer) params[0].getTag() == IS_FOLLOWING)
                                    new ApiClient().unFollowUser(
                                            AccountManager.getUserHandle(parent.getContext()),
                                            AccountManager.getUserToken(parent.getContext()),
                                            String.valueOf(handleView.getText())
                                    );
                                else
                                    new ApiClient().followUser(
                                            AccountManager.getUserHandle(parent.getContext()),
                                            AccountManager.getUserToken(parent.getContext()),
                                            String.valueOf(handleView.getText())
                                    );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            setIsFollowing(button, (Integer) button.getTag() == IS_NOT_FOLLOWING);
                            int notification;
                            Style style;
                            if ((Integer) button.getTag() == IS_FOLLOWING) {
                                notification = R.string.follower_add;
                                style = Style.CONFIRM;
                            } else {
                                notification = R.string.follower_delete;
                                style = Style.ALERT;
                            }
                            Crouton.makeText((Activity) parent.getContext(), notification, style).show();
                            super.onPostExecute(aVoid);
                        }
                    }.execute(v);
                }
            });
        } else {
            button.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void setIsFollowing(Button b, boolean isFollowing) {
        if (isFollowing) {
            b.setText(R.string.unfollow);
            b.setTag(IS_FOLLOWING);
        }
        else {
            b.setText(R.string.follow);
            b.setTag(IS_NOT_FOLLOWING);
        }
    }

}
