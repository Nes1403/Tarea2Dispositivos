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

public class PedidoAdapter extends ArrayAdapter<Pedido> {

    private Context context;
    private ArrayList<Pedido> pedidos;
    private int viewRes;
    private Resources res;

    public PedidoAdapter(Context context, int textViewResourceId, ArrayList<Pedido> pedidos) {
        super(context, textViewResourceId, pedidos);
        this.context = context;
        this.pedidos = pedidos;
        this.viewRes = textViewResourceId;
        this.res = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(viewRes, parent, false);
        }

        Pedido pedido = pedidos.get(position);

        TextView txtCalle = convertView.findViewById(R.id.txtCalle);
        TextView txtNumero = convertView.findViewById(R.id.txtNumero);
        TextView txtColonia = convertView.findViewById(R.id.txtColonia);
        TextView txtItems = convertView.findViewById(R.id.txtItems);

        txtCalle.setText("Calle: " + pedido.getCalle());
        txtNumero.setText("Número: " + pedido.getNumero());
        txtColonia.setText("Colonia: " + pedido.getColonia());

        StringBuilder itemsText = new StringBuilder();
        for (Item item : pedido.getItems()) {
            itemsText.append("- ").append(item.getTitle())
                    .append(" (").append(item.getPrice()).append(" MXN)\n");
        }

        txtItems.setText(itemsText.toString().trim());

        return convertView;
    }


}


