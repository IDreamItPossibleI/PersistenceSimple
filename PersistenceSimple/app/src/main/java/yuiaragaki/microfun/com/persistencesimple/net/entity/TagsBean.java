package yuiaragaki.microfun.com.persistencesimple.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuiaragaki on 17/5/18.
 */

public class TagsBean implements Parcelable {

    /**
     * count : 2416
     * name : 小王子
     */

    private int count;
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(count);
        out.writeString(name);
    }

    public TagsBean(Parcel in)
    {
        count = in.readInt();
        name = in.readString();
    }

    public static final Parcelable.Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
        @Override
        public TagsBean createFromParcel(Parcel in) {
            return new TagsBean(in);
        }

        @Override
        public TagsBean[] newArray(int size) {
            return new TagsBean[size];
        }
    };

}
