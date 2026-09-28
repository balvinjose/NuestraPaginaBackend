/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ServidorWeb {

    public static void main(String[] args)
            throws IOException {

        String puertoVariable =
                System.getenv("PORT");

        int puerto =
                puertoVariable != null
                        ? Integer.parseInt(puertoVariable)
                        : 8080;

        HttpServer servidor =
                HttpServer.create(
                        new InetSocketAddress(
                                "0.0.0.0",
                                puerto
                        ),
                        0
                );

        servidor.createContext(
                "/api",
                ServidorWeb::responder
        );

        servidor.createContext(
                "/api/elementos",
                ApiElementos::agregar
        );

        servidor.createContext(
                "/api/categorias",
                ApiCategorias::manejar
        );

        servidor.start();

        System.out.println(
                "================================="
        );

        System.out.println(
                "Servidor iniciado"
        );

        System.out.println(
                "Puerto: " + puerto
        );

        System.out.println(
                "================================="
        );
    }

    private static void responder(
            HttpExchange intercambio
    ) throws IOException {

        String respuesta =
                """
                {
                    "mensaje": "Java está funcionando correctamente"
                }
                """;

        intercambio
                .getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );

        intercambio
                .getResponseHeaders()
                .set(
                        "Access-Control-Allow-Origin",
                        "*"
                );

        byte[] datos =
                respuesta.getBytes(
                        StandardCharsets.UTF_8
                );

        intercambio.sendResponseHeaders(
                200,
                datos.length
        );

        try (
                OutputStream salida =
                        intercambio.getResponseBody()
        ) {

            salida.write(datos);
        }
    }
}