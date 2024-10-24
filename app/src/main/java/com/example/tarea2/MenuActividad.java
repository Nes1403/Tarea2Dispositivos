package com.example.tarea2;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class MenuActividad extends AppCompatActivity {

    private String[] mMenuSections;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creaMenu();
        //setContentView(R.layout.datos);

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

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            Log.e("MenuActividad", "ActionBar es null");
        }


    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent,
                                View view, int position, long id) {
            Log.d("HomeActivity",
                    (String) parent.getAdapter().getItem(position));
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
            Log.d("HomeActivity", "action_share");
            return true;
        } else if (id == R.id.algo) {
            Log.d("HomeActivity", "algo");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void creaMenu(){
        setContentView(R.layout.menu_layout);

        ArrayList<Item> menuItems = new ArrayList<>();
        menuItems.add(new Item(R.drawable.burger, "Hamburguesa", "Deliciosa hamburguesa con queso", 120, false));
        menuItems.add(new Item(R.drawable.pepsi, "Refresco", "lata de refresco de 355ml", 40, false));
        menuItems.add(new Item(R.drawable.papas, "Papas Fritas", "Crocantes papas fritas", 50, false));
        menuItems.add(new Item(R.drawable.alitas, "Alitas BBQ", "Deliciosas alitas, 8 pzs", 160, false));
        menuItems.add(new Item(R.drawable.boneless, "Bonles BBQ", "Deliciosos Boneless, 8 pzs", 180, false));
        menuItems.add(new Item(R.drawable.pizza, "Pizza de peperoni", "Deliciosa pizza grande de peperoni", 190, false));
        menuItems.add(new Item(R.drawable.hotdogs, "Hotdog", "Delicioso Hot Dog con papas ", 50, false));
        menuItems.add(new Item(R.drawable.chilaquiles, "Chilaquiles verdes", "Deliciosos chilaquiles", 70, false));

        ItemAdapter adapter = new ItemAdapter(this, R.layout.item_menu, menuItems);
        ListView listView = findViewById(R.id.listViewMenu);
        listView.setAdapter(adapter);
    }
}
