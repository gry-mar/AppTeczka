package edu.ib.appteczkaandroid;

import static java.lang.System.out;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class CustomListElement implements Parcelable {
    private String drugName;
    private String time;
    private boolean isChecked;

    public CustomListElement(String drugName, String time, boolean isChecked) {
        this.drugName = drugName;
        this.time = time;
        this.isChecked = isChecked;
    }


    protected CustomListElement(Parcel in) {
        drugName = in.readString();
        time = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(drugName);
        dest.writeString(time);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomListElement> CREATOR = new Creator<CustomListElement>() {
        @Override
        public CustomListElement createFromParcel(Parcel in) {
            return new CustomListElement(in);
        }

        @Override
        public CustomListElement[] newArray(int size) {
            return new CustomListElement[size];
        }
    };

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "CustomListElement{" +
                "drugName='" + drugName + '\'' +
                ", time='" + time + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

}
