package com.example.tarea2;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


import java.util.ArrayList;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creaMenu();
        //setContentView(R.layout.datos);

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

