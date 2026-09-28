/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiCategorias {

    public static void manejar(
            HttpExchange intercambio
    ) throws IOException {

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, OPTIONS"
        );

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );


        // =========================
        // OPTIONS
        // =========================

        if (
                intercambio
                        .getRequestMethod()
                        .equals("OPTIONS")
        ) {

            intercambio.sendResponseHeaders(
                    204,
                    -1
            );

            intercambio.close();

            return;
        }


        // =========================
        // GET
        // =========================

        if (
                intercambio
                        .getRequestMethod()
                        .equals("GET")
        ) {

            CategoriaDAO dao =
                    new CategoriaDAO();

            List<Categoria> categorias =
                    dao.listar();

            StringBuilder json =
                    new StringBuilder("[");

            for (
                    int i = 0;
                    i < categorias.size();
                    i++
            ) {

                Categoria categoria =
                        categorias.get(i);

                json.append("{");

                json.append("\"id\":")
                        .append(categoria.getId())
                        .append(",");

                json.append("\"nombre\":\"")
                        .append(
                                escapar(
                                        categoria.getNombre()
                                )
                        )
                        .append("\",");

                json.append("\"emoji\":\"")
                        .append(
                                escapar(
                                        categoria.getEmoji()
                                )
                        )
                        .append("\"");

                json.append("}");

                if (
                        i < categorias.size() - 1
                ) {
                    json.append(",");
                }
            }

            json.append("]");

            enviarRespuesta(
                    intercambio,
                    json.toString()
            );

            return;
        }


        // =========================
        // POST
        // =========================

        if (
                intercambio
                        .getRequestMethod()
                        .equals("POST")
        ) {

            String cuerpo =
                    new String(
                            intercambio
                                    .getRequestBody()
                                    .readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            System.out.println(
                    "POST categoría recibido: "
                    + cuerpo
            );

            String nombre =
                    obtenerValor(
                            cuerpo,
                            "nombre"
                    );

            String emoji =
                    obtenerValor(
                            cuerpo,
                            "emoji"
                    );

            Categoria categoria =
                    new Categoria(
                            nombre,
                            emoji
                    );

            CategoriaDAO dao =
                    new CategoriaDAO();

            int id =
                    dao.agregar(categoria);

            if (id != -1) {

                System.out.println(
                        "Categoría guardada. ID: "
                        + id
                );

                enviarRespuesta(
                        intercambio,
                        "{\"id\":" + id + "}"
                );

            } else {

                enviarRespuesta(
                        intercambio,
                        "{\"error\":\"No se pudo guardar la categoría\"}"
                );
            }

            return;
        }


        enviarRespuesta(
                intercambio,
                "{\"error\":\"Método no permitido\"}"
        );
    }


    // =========================
    // OBTENER VALOR JSON
    // =========================

    private static String obtenerValor(
            String json,
            String campo
    ) {

        String buscar =
                "\"" + campo + "\":";

        int inicio =
                json.indexOf(buscar);

        if (inicio == -1) {
            return "";
        }

        inicio += buscar.length();

        while (
                inicio < json.length()
                && Character.isWhitespace(
                        json.charAt(inicio)
                )
        ) {

            inicio++;
        }

        if (
                inicio < json.length()
                && json.charAt(inicio) == '"'
        ) {

            inicio++;

            int fin =
                    json.indexOf(
                            "\"",
                            inicio
                    );

            return json.substring(
                    inicio,
                    fin
            );
        }

        return "";
    }


    // =========================
    // ESCAPAR JSON
    // =========================

    private static String escapar(
            String texto
    ) {

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }


    // =========================
    // RESPUESTA
    // =========================

    private static void enviarRespuesta(
            HttpExchange intercambio,
            String respuesta
    ) throws IOException {

        byte[] datos =
                respuesta.getBytes(
                        StandardCharsets.UTF_8
                );

        intercambio
                .getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
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
