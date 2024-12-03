package com.example.tarea2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLA_PEDIDOS = "tabla_pedido";
    private static final String COL_ID = "ID";
    private static final String COL_CALLE = "CALLE";
    private static final String COL_NUMERO = "NUMERO";
    private static final String COL_COLONIA = "COLONIA";
    private static final String COL_DELEGACION = "DELEGACION";
    private static final String COL_CODIGOP = "CODIGOP";


    private static final String CREATE_BDD_PEDIDOS = "CREATE TABLE " + TABLA_PEDIDOS + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CALLE + " TEXT NOT NULL, " +
            COL_NUMERO + " INTEGER NOT NULL, " +
            COL_COLONIA + " TEXT NOT NULL, " +
            COL_DELEGACION + " TEXT NOT NULL, " +
            COL_CODIGOP + " INTEGER NOT NULL" +
            ");";

    private static final String TABLA_ITEMS = "tabla_items";
    private static final String COL_ITEM_ID = "ITEM_ID";
    private static final String COL_PEDIDO_ID = "PEDIDO_ID";
    private static final String COL_ITEM_NAME = "ITEM_NAME";
    private static final String COL_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    private static final String COL_ITEM_PRICE = "ITEM_PRICE";
    private static final String COL_ITEM_IMAGE = "ITEM_IMAGE";
    private static final String COL_ITEM_ISCHECKED = "ITEM_ISCHECKED";


    private static final String CREATE_BDD_ITEMS = "CREATE TABLE " + TABLA_ITEMS + " (" +
            COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_PEDIDO_ID + " INTEGER NOT NULL, " +
            COL_ITEM_NAME + " TEXT NOT NULL, " +
            COL_ITEM_DESCRIPTION + " TEXT, " +
            COL_ITEM_PRICE + " REAL NOT NULL, " +
            COL_ITEM_IMAGE + " INTEGER NOT NULL, " +
            COL_ITEM_ISCHECKED + " INTEGER NOT NULL, " +
            "FOREIGN KEY (" + COL_PEDIDO_ID + ") REFERENCES " + TABLA_PEDIDOS + "(" + COL_ID + ") ON DELETE CASCADE" +
            ");";

    public PedidoBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BDD_PEDIDOS);
        db.execSQL(CREATE_BDD_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDOS);
        onCreate(db);
    }
}
