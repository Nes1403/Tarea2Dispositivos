package com.example.tarea2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import java.util.ArrayList;
import java.util.Iterator;

public class DatosActividad extends AppCompatActivity {

    private ArrayList<Item> menuItems;
    private String[] mMenuSections;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private PedidoBDD base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos);
        base = new PedidoBDD(this);
        Intent intent = getIntent();
        menuItems = intent.getParcelableArrayListExtra("miLista");
        Iterator<Item> iterator = menuItems.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (!item.isChecked()) {
                iterator.remove();
            }
        }
        ItemAdapterConf adapter = new ItemAdapterConf(this, R.layout.item_menu_conf, menuItems);
        ListView listView = findViewById(R.id.listViewMenuConf);
        listView.setAdapter(adapter);

        mMenuSections = getResources().getStringArray(com.example.tarea2.R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(com.example.tarea2.R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(com.example.tarea2.R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                com.example.tarea2.R.layout.left_drawer, mMenuSections));

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.DrawerOpen,  /* "open drawer" description */
                R.string.DrawerClosed  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                Log.d("HomeActivity", "onDrawerClosed");
            }

            public void onDrawerOpened(View drawerView) {
                Log.d("HomeActivity", "onDrawerOpened");
            }
        };


        mDrawerList.setOnItemClickListener(new com.example.tarea2.DatosActividad.DrawerItemClickListener());

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            Log.e("MenuActividad", "ActionBar es null");
        }

        Button botonContinuar = findViewById(R.id.continueButton);

        botonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText calleField = findViewById(R.id.calle);
                EditText numeroField = findViewById(R.id.numberEditText);
                EditText coloniaField = findViewById(R.id.colonia);
                Spinner delegacionSpinner = findViewById(R.id.delegationSpinner);
                EditText codigoPostalField = findViewById(R.id.codigo_postal);

                String calle = calleField.getText().toString().trim();
                String colonia = coloniaField.getText().toString().trim();
                String delegacion = delegacionSpinner.getSelectedItem().toString();
                int numero;
                int codigoPostal;

                if (calle.isEmpty()) {
                    Toast.makeText(DatosActividad.this, "El campo 'Calle' es obligatorio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (colonia.isEmpty()) {
                    Toast.makeText(DatosActividad.this, "El campo 'Colonia' es obligatorio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    numero = Integer.parseInt(numeroField.getText().toString().trim());
                    if (numero <= 0) {
                        Toast.makeText(DatosActividad.this, "El número debe ser mayor a 0.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DatosActividad.this, "El campo 'Número' es obligatorio y debe ser un número válido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    codigoPostal = Integer.parseInt(codigoPostalField.getText().toString().trim());
                    if (String.valueOf(codigoPostal).length() != 5) {
                        Toast.makeText(DatosActividad.this, "El código postal debe tener exactamente 5 dígitos.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DatosActividad.this, "El campo 'Código Postal' es obligatorio y debe ser un número válido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (delegacion.equals("Selecciona una delegación")) { // Asegúrate de que el primer ítem sea esta opción
                    Toast.makeText(DatosActividad.this, "Por favor selecciona una delegación.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (menuItems == null || menuItems.isEmpty()) {
                    Toast.makeText(DatosActividad.this, "No hay elementos seleccionados en el menú.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Pedido pedido = new Pedido(calle, numero, colonia, delegacion, codigoPostal, menuItems);
                Toast.makeText(DatosActividad.this, "Pedido creado con éxito.", Toast.LENGTH_SHORT).show();
                base.openForWrite();
                base.insertPedido(pedido);
                goToNextActivity();
            }
        });
    }

    private void goToNextActivity() {
        Intent intent = new Intent(DatosActividad.this, MenuActividad.class);
        startActivity(intent);
    }

    public class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent,
                                View view, int position, long id) {
            switch (position) {
                case 0:
                    Log.d("HomeActivity", "Inicio");
                    break;
                case 1:
                    Log.d("HomeActivity", "Histroial de pedidos");
                    Intent intent = new Intent(DatosActividad.this, HistorialPedidos.class);
                    startActivity(intent);
                    break;
                case 2:
                    Log.d("HomeActivity", "Ofertas");
                    break;
                case 3:
                    Log.d("HomeActivity", "Metodos de pago");
                    break;
                case 4:
                    Log.d("HomeActivity", "Cerrar sesion");
                    break;
                default:
                    Log.d("HomeActivity", "caso default");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.tarea2.R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.cancelar_pedido) {
            Log.d("HomeActivity", "Cancelar Pedido");
            return true;
        } else if (id == R.id.action_share) {
            Log.d("HomeActivity", "perfil");
            return true;
        } else if (id == R.id.comYsug) {
            Log.d("HomeActivity", "comentarios y sugerencias");
            return true;
        }else if (id == R.id.action_cart) {
            Log.d("HomeActivity", "Carrito de compras");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}