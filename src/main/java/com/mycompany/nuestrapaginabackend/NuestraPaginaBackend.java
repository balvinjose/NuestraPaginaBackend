/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.nuestrapaginabackend;

public class NuestraPaginaBackend {

    public static void main(String[] args) {

        ElementoDAO dao = new ElementoDAO();

        System.out.println("📋 Elementos de la categoría Comidas:");

        for (Elemento elemento : dao.listarPorCategoria(1)) {
            System.out.println(
                    "ID: " + elemento.getId()
                    + " | Texto: " + elemento.getTexto()
            );
        }
    }
}
