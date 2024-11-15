package com.example.tarea2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tarea2.Item;
import com.example.tarea2.ItemAdapter;
import com.example.tarea2.R;

import java.util.ArrayList;
import java.util.Iterator;

public class DatosActividad extends AppCompatActivity {

    private ArrayList<Item> menuItems;
    private String[] mMenuSections;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos);
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

        mDrawerList.setOnItemClickListener(new com.example.tarea2.MenuActividad.DrawerItemClickListener());

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            Log.e("MenuActividad", "ActionBar es null");
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