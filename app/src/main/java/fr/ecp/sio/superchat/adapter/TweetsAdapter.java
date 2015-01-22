package fr.ecp.sio.superchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.model.Tweet;

/**
 * Created by Betezed on 05/12/2014.
 */
public class TweetsAdapter extends BaseAdapter {

    private List<Tweet> mTweets;

    public List<Tweet> getTweets() {
        return mTweets;
    }

    public void setTweets(List<Tweet> tweets) {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTweets != null ? mTweets.size() : 0;
    }

    @Override
    public Tweet getItem(int position) {
        return mTweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false);
        }
        Tweet tweet = getItem(position);
        TextView contentView = (TextView) convertView.findViewById(R.id.tweet_content);
        contentView.setText(tweet.getContent());
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setText(tweet.getDate());
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
