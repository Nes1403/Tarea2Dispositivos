package com.example.tarea2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PedidoBDD {
    private static final int VERSION = 1;
    private static final String NOM_BDD = "pedido.db";
    private static final String TABLA_PEDIDOS = "tabla_pedido";
    private static final String TABLA_ITEMS = "tabla_items";

    private static final String COL_ID = "ID";
    private static final String COL_CALLE = "CALLE";
    private static final String COL_NUMERO = "NUMERO";
    private static final String COL_COLONIA = "COLONIA";
    private static final String COL_DELEGACION = "DELEGACION";
    private static final String COL_CODIGOP = "CODIGOP";

    private static final String COL_ITEM_ID = "ITEM_ID";
    private static final String COL_PEDIDO_ID = "PEDIDO_ID";
    private static final String COL_ITEM_NAME = "ITEM_NAME";
    private static final String COL_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    private static final String COL_ITEM_PRICE = "ITEM_PRICE";
    private static final String COL_ITEM_IMAGE = "ITEM_IMAGE";
    private static final String COL_ITEM_ISCHECKED = "ITEM_ISCHECKED";

    // Instancia de la base de datos
    private SQLiteDatabase bdd;
    private PedidoBaseSQLite pedidos;

    public PedidoBDD(Context context) {
        pedidos = new PedidoBaseSQLite(context, NOM_BDD, null, VERSION);
    }

    public void openForWrite() {
        bdd = pedidos.getWritableDatabase();
    }

    public void openForRead() {
        bdd = pedidos.getReadableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    // Insertar un nuevo pedido
    public long insertPedido(Pedido pedido) {
        ContentValues content = new ContentValues();
        content.put(COL_CALLE, pedido.getCalle());
        content.put(COL_NUMERO, pedido.getNumero());
        content.put(COL_COLONIA, pedido.getColonia());
        content.put(COL_DELEGACION, pedido.getDelegacion());
        content.put(COL_CODIGOP, pedido.getCodigoP());
        long pedidoId = bdd.insert(TABLA_PEDIDOS, null, content);

        // Insertar items asociados a este pedido
        for (Item item : pedido.getItems()) {
            insertItem(item, (int) pedidoId);
        }

        return pedidoId;
    }

    // Actualizar un pedido por ID
    public int updatePedido(int id, Pedido pedido) {
        ContentValues content = new ContentValues();
        content.put(COL_CALLE, pedido.getCalle());
        content.put(COL_NUMERO, pedido.getNumero());
        content.put(COL_COLONIA, pedido.getColonia());
        content.put(COL_DELEGACION, pedido.getDelegacion());
        content.put(COL_CODIGOP, pedido.getCodigoP());
        int result = bdd.update(TABLA_PEDIDOS, content, COL_ID + " = ?", new String[] { String.valueOf(id) });

        // Actualizar los items asociados al pedido
        removeItemsByPedidoId(id);
        for (Item item : pedido.getItems()) {
            insertItem(item, id);
        }

        return result;
    }

    // Eliminar un pedido por ID
    public int removePedido(int id) {
        removeItemsByPedidoId(id);
        return bdd.delete(TABLA_PEDIDOS, COL_ID + " = ?", new String[] { String.valueOf(id) });
    }

    // Obtener un pedido por ID con sus items
    public Pedido getPedido(int id) {
        Cursor c = bdd.query(TABLA_PEDIDOS, new String[] { COL_ID, COL_CALLE, COL_NUMERO, COL_COLONIA, COL_DELEGACION, COL_CODIGOP },
                COL_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);
        Pedido pedido = cursorToPedido(c);
        if (pedido != null) {
            // Obtener los items asociados a este pedido
            pedido.setItems(getItemsByPedidoId(id));
        }
        return pedido;
    }

    // Convertir un cursor a un objeto Pedido
    private Pedido cursorToPedido(Cursor c) {
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToFirst();
        Pedido pedido = new Pedido();
        pedido.setId(c.getInt(c.getColumnIndex(COL_ID)));
        pedido.setCalle(c.getString(c.getColumnIndex(COL_CALLE)));
        pedido.setNumero(c.getInt(c.getColumnIndex(COL_NUMERO)));
        pedido.setColonia(c.getString(c.getColumnIndex(COL_COLONIA)));
        pedido.setDelegacion(c.getString(c.getColumnIndex(COL_DELEGACION)));
        pedido.setCodigoP(c.getInt(c.getColumnIndex(COL_CODIGOP)));
        c.close();
        return pedido;
    }

    // Obtener todos los pedidos con sus items
    public ArrayList<Pedido> getAllPedidos() {
        Cursor c = bdd.query(TABLA_PEDIDOS, new String[] { COL_ID, COL_CALLE, COL_NUMERO, COL_COLONIA, COL_DELEGACION, COL_CODIGOP },
                null, null, null, null, null);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        while (c.moveToNext()) {
            Pedido pedido = new Pedido();
            pedido.setId(c.getInt(c.getColumnIndex(COL_ID)));
            pedido.setCalle(c.getString(c.getColumnIndex(COL_CALLE)));
            pedido.setNumero(c.getInt(c.getColumnIndex(COL_NUMERO)));
            pedido.setColonia(c.getString(c.getColumnIndex(COL_COLONIA)));
            pedido.setDelegacion(c.getString(c.getColumnIndex(COL_DELEGACION)));
            pedido.setCodigoP(c.getInt(c.getColumnIndex(COL_CODIGOP)));
            // Obtener los items asociados a este pedido
            pedido.setItems(getItemsByPedidoId(pedido.getId()));
            listaPedidos.add(pedido);
        }
        c.close();
        return listaPedidos;
    }

    // Obtener los items asociados a un pedido
    public ArrayList<Item> getItemsByPedidoId(int pedidoId) {
        ArrayList<Item> listaItems = new ArrayList<>();
        Cursor c = bdd.query(
                TABLA_ITEMS,
                new String[] { COL_ITEM_ID, COL_ITEM_NAME, COL_ITEM_DESCRIPTION, COL_ITEM_PRICE, COL_ITEM_IMAGE, COL_ITEM_ISCHECKED },
                COL_PEDIDO_ID + " = ?",
                new String[] { String.valueOf(pedidoId) },
                null, null, null
        );

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                Item item = new Item();
                item.setId(c.getInt(c.getColumnIndex(COL_ITEM_ID)));
                item.setTitle(c.getString(c.getColumnIndex(COL_ITEM_NAME)));
                item.setDescription(c.getString(c.getColumnIndex(COL_ITEM_DESCRIPTION)));
                item.setPrice(c.getFloat(c.getColumnIndex(COL_ITEM_PRICE)));
                item.setImageResId(c.getInt(c.getColumnIndex(COL_ITEM_IMAGE)));
                item.setChecked(c.getInt(c.getColumnIndex(COL_ITEM_ISCHECKED)) == 1);
                listaItems.add(item);
            }
        }
        c.close();
        return listaItems;
    }

    // Insertar un item asociado a un pedido
    public long insertItem(Item item, int pedidoId) {
        ContentValues content = new ContentValues();
        content.put(COL_ITEM_NAME, item.getTitle());
        content.put(COL_ITEM_DESCRIPTION, item.getDescription());
        content.put(COL_ITEM_PRICE, item.getPrice());
        content.put(COL_ITEM_IMAGE, item.getImageResId());
        content.put(COL_ITEM_ISCHECKED, item.isChecked() ? 1 : 0);
        content.put(COL_PEDIDO_ID, pedidoId); // Asocia el item con un pedido
        return bdd.insert(TABLA_ITEMS, null, content);
    }

    // Eliminar items asociados a un pedido
    public int removeItemsByPedidoId(int pedidoId) {
        return bdd.delete(TABLA_ITEMS, COL_PEDIDO_ID + " = ?", new String[] { String.valueOf(pedidoId) });
    }
}

