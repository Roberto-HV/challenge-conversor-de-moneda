package com.alura.conversor.modelos;

public class Moneda {
    private String nombre;
    private int valor;

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public double calculaConversion(int moneda1, int moneda2){

        return moneda1 * moneda2;
    }
}
