package yuiaragaki.microfun.com.persistencesimple.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuiaragaki on 17/5/18.
 */

public class SeriesBean implements Parcelable {

    /**
     * id : 2065
     * title : 新史学&多元对话系列
     */

    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(title);
    }

    public SeriesBean(Parcel in)
    {
        id = in.readString();
        title = in.readString();
    }

    public static final Parcelable.Creator<SeriesBean> CREATOR = new Creator<SeriesBean>() {
        @Override
        public SeriesBean createFromParcel(Parcel in) {
            return new SeriesBean(in);
        }

        @Override
        public SeriesBean[] newArray(int size) {
            return new SeriesBean[size];
        }
    };

}
