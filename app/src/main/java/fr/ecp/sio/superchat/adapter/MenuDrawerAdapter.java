package fr.ecp.sio.superchat.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.ecp.sio.superchat.AccountManager;
import fr.ecp.sio.superchat.R;
import fr.ecp.sio.superchat.model.NavigationDrawerItem;

/**
 * Created by betezed on 23/12/14.
 */
public class MenuDrawerAdapter extends BaseAdapter {

    private String[] mMenuTitles;
    private TypedArray mMenuIcons;
    List<NavigationDrawerItem> mNavigationDrawerItemList;

    public MenuDrawerAdapter() {
        mNavigationDrawerItemList = new ArrayList<>();
    }

    public void addMenuItems(Context context, boolean isConnected) {
        mNavigationDrawerItemList.clear();
        mMenuTitles = context.getResources().getStringArray(R.array.nav_drawer_items);
        mMenuIcons = context.getResources().obtainTypedArray(R.array.nav_drawer_icons);
        if (!isConnected) {
            mNavigationDrawerItemList.add(new NavigationDrawerItem(0, mMenuTitles[0], mMenuIcons.getResourceId(0, -1)));
        } else {
            for (int i = 1; i < mMenuTitles.length; i++) {
                mNavigationDrawerItemList.add(new NavigationDrawerItem(i, mMenuTitles[i], mMenuIcons.getResourceId(i, -1)));
            }
        }
        mMenuIcons.recycle();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mNavigationDrawerItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavigationDrawerItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mNavigationDrawerItemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.drawer_item_icon);
        TextView textView = (TextView) convertView.findViewById(R.id.drawer_item_name);
        imageView.setImageResource(mNavigationDrawerItemList.get(position).getImageResourceId());
        textView.setText(mNavigationDrawerItemList.get(position).getItemName());
        return convertView;
    }
}
