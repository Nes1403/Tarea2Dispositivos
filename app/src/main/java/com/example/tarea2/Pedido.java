package com.example.tarea2;

import java.util.ArrayList;

public class Pedido {

    int id;
    private String calle;
    private int numero;
    private String colonia;
    private String delegacion;
    private int codigoP;
    private ArrayList<Item> items;



    public Pedido(){}

    public Pedido(String calle, int numero, String colonia, String delegacion, int codigoP, ArrayList<Item> items){
        this.calle=calle;
        this.numero=numero;
        this.colonia=colonia;
        this.delegacion = delegacion;
        this.codigoP=codigoP;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public int getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(int codigoP) {
        this.codigoP = codigoP;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }




}
