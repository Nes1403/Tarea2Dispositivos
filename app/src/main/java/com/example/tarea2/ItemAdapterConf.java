package com.example.tarea2;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ItemAdapterConf extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> Items;
    private int viewRes;
    private Resources res;
    private boolean[] checkedItems;

    public ItemAdapterConf(Context context, int textViewResourceId, ArrayList<Item> menuItems) {
        super(context, textViewResourceId, menuItems);
        this.context = context;
        this.Items = menuItems;
        this.viewRes = textViewResourceId;
        this.res = context.getResources();
        this.checkedItems = new boolean[menuItems.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(viewRes, parent, false);
        }

        Item item = Items.get(position);

        ImageView imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView descripcion = (TextView) convertView.findViewById(R.id.descripcion);
        TextView precio = (TextView) convertView.findViewById(R.id.precio);

        imgItem.setImageResource(item.getImageResId());
        titulo.setText(item.getTitle());
        descripcion.setText(item.getDescription());
        precio.setText("Precio: $" + String.format("%.2f", item.getPrice()));

        return convertView;
    }

}