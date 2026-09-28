/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

public class Elemento {

    private int id;
    private int categoriaId;
    private String texto;

    public Elemento() {
    }

    public Elemento(int id, int categoriaId, String texto) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.texto = texto;
    }

    public Elemento(int categoriaId, String texto) {
        this.categoriaId = categoriaId;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}
