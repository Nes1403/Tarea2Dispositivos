package com.example.tarea2;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int imageResId;
    private String title;
    private String description;
    private double price;
    private boolean isChecked;

    public Item(int imageResId, String title, String description, double price, boolean isChecked) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.isChecked = isChecked;
    }

    protected Item(Parcel in) {
        imageResId = in.readInt();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        isChecked = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
