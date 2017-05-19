package yuiaragaki.microfun.com.persistencesimple.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuiaragaki on 17/5/18.
 */

public class RatingBean implements Parcelable {

    /**
     * max : 10
     * numRaters : 9438
     * average : 9.1
     * min : 0
     */

    private int max;
    private int numRaters;
    private String average;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(max);
        out.writeInt(numRaters);
        out.writeString(average);
        out.writeInt(min);
    }

    public RatingBean(Parcel in)
    {
        max = in.readInt();
        numRaters = in.readInt();
        average = in.readString();
        min = in.readInt();
    }

    public static final Parcelable.Creator<RatingBean> CREATOR = new Creator<RatingBean>() {
        @Override
        public RatingBean createFromParcel(Parcel in) {
            return new RatingBean(in);
        }

        @Override
        public RatingBean[] newArray(int size) {
            return new RatingBean[size];
        }
    };

}
