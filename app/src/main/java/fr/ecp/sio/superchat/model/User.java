package fr.ecp.sio.superchat.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michaël on 05/12/2014.
 */
public class User implements Parcelable {

    private String _id;
    private String handle;
    private String status;
    private String profilePicture;
    private boolean following;

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean isFollowing) {
        this.following = isFollowing;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(handle);
        dest.writeString(status);
        dest.writeString(profilePicture);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            User user = new User();
            user._id = source.readString();
            user.handle = source.readString();
            user.status = source.readString();
            user.profilePicture = source.readString();
            return user;
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }

    };

}