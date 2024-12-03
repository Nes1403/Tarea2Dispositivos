package com.example.tarea2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class HistorialPedidos extends AppCompatActivity {
    private ListView listViewPedidos;
    private PedidoBDD pedidoBDD;
    private ArrayList<Pedido> listaPedidos;
    private PedidoAdapter pedidoAdapter; // Tu adaptador personalizado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);

        Button botonMenu = findViewById(R.id.botonMenuPrincipal);

        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistorialPedidos.this, MenuActividad.class);
                startActivity(intent);
            }
        });


        listViewPedidos = findViewById(R.id.listViewPedidos);
        pedidoBDD = new PedidoBDD(this);


        pedidoBDD.openForRead();


        listaPedidos = pedidoBDD.getAllPedidos();


        if (listaPedidos != null && !listaPedidos.isEmpty()) {
            pedidoAdapter = new PedidoAdapter(this, R.layout.pedido, listaPedidos);
            listViewPedidos.setAdapter(pedidoAdapter);
        } else {

        }

        pedidoBDD.close();
    }
}
