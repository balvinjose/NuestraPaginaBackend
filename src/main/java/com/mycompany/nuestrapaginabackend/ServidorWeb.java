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

        HttpServer servidor =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );


        // =========================
        // API PRINCIPAL
        // =========================

        servidor.createContext(
                "/api",
                ServidorWeb::responder
        );


        // =========================
        // ELEMENTOS
        // =========================

        servidor.createContext(
                "/api/elementos",
                ApiElementos::agregar
        );


        // =========================
        // CATEGORÍAS
        // =========================

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
                "http://localhost:8080/api"
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

        intercambio.sendResponseHeaders(
                200,
                respuesta
                        .getBytes(
                                StandardCharsets.UTF_8
                        )
                        .length
        );

        try (
                OutputStream salida =
                        intercambio.getResponseBody()
        ) {

            salida.write(
                    respuesta.getBytes(
                            StandardCharsets.UTF_8
                    )
            );
        }
    }
}
