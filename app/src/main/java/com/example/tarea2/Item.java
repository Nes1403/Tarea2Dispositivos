package com.example.tarea2;

public class Item {
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
