package fr.ecp.sio.superchat.model;

/**
 * Created by betezed on 23/12/14.
 */
public class NavigationDrawerItem {
    int mId;
    String mItemName;
    int mImageResourceId;

    public NavigationDrawerItem(int id, String itemName, int imageResourceId) {
        mId = id;
        mItemName = itemName;
        mImageResourceId = imageResourceId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }
}
