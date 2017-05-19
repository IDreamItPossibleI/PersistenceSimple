package yuiaragaki.microfun.com.persistencesimple.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by yuiaragaki on 17/5/18.
 */

public class ImagesBean implements Parcelable {

    /**
     * small : https://img3.doubanio.com/spic/s1001902.jpg
     * large : https://img3.doubanio.com/lpic/s1001902.jpg
     * medium : https://img3.doubanio.com/mpic/s1001902.jpg
     */

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(small);
        out.writeString(large);
        out.writeString(medium);
    }

    public static final Parcelable.Creator<ImagesBean> CREATOR = new Parcelable.Creator<ImagesBean>() {
        @Override
        public ImagesBean createFromParcel(Parcel in) {
            return new ImagesBean(in);
        }

        @Override
        public ImagesBean[] newArray(int size) {
            return new ImagesBean[size];
        }
    };

    public ImagesBean(Parcel in)
    {
        small = in.readString();
        large = in.readString();
        medium = in.readString();
    }

}
