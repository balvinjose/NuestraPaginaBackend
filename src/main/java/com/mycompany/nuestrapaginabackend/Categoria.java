/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

public class Categoria {

    private int id;
    private String nombre;
    private String emoji;

    public Categoria() {
    }

    public Categoria(int id, String nombre, String emoji) {
        this.id = id;
        this.nombre = nombre;
        this.emoji = emoji;
    }

    public Categoria(String nombre, String emoji) {
        this.nombre = nombre;
        this.emoji = emoji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return emoji + " " + nombre;
    }
}
