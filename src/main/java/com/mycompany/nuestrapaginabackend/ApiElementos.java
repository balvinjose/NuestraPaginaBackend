/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ApiElementos {

    public static void agregar(HttpExchange intercambio) throws IOException {

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );

        intercambio.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );

        // =========================
        // OPTIONS
        // =========================

        if (intercambio.getRequestMethod().equals("OPTIONS")) {

            intercambio.sendResponseHeaders(204, -1);
            intercambio.close();

            return;
        }

        // =========================
        // DELETE
        // =========================

        if (intercambio.getRequestMethod().equals("DELETE")) {

            String query =
                    intercambio.getRequestURI().getQuery();

            int id = Integer.parseInt(
                    query.substring(3)
            );

            ElementoDAO dao =
                    new ElementoDAO();

            boolean eliminado =
                    dao.eliminar(id);

            if (eliminado) {

                System.out.println(
                        "Elemento eliminado de MySQL. ID: " + id
                );

                enviarRespuesta(
                        intercambio,
                        "{\"mensaje\":\"Elemento eliminado\"}"
                );

            } else {

                enviarRespuesta(
                        intercambio,
                        "{\"error\":\"No se pudo eliminar\"}"
                );
            }

            return;
        }

        // =========================
        // PUT - MODIFICAR
        // =========================

        if (intercambio.getRequestMethod().equals("PUT")) {

            String query =
                    intercambio.getRequestURI().getQuery();

            int id = Integer.parseInt(
                    query.substring(3)
            );

            String cuerpo =
                    new String(
                            intercambio.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            String texto =
                    obtenerValor(
                            cuerpo,
                            "texto"
                    );

            System.out.println(
                    "PUT recibido: " + cuerpo
            );

            System.out.println(
                    "ID: " + id
            );

            System.out.println(
                    "Nuevo texto: " + texto
            );

            Elemento elemento =
                    new Elemento(
                            id,
                            0,
                            texto
                    );

            ElementoDAO dao =
                    new ElementoDAO();

            boolean modificado =
                    dao.modificar(elemento);

            if (modificado) {

                System.out.println(
                        "Elemento modificado en MySQL."
                );

                enviarRespuesta(
                        intercambio,
                        "{\"mensaje\":\"Elemento modificado\"}"
                );

            } else {

                System.out.println(
                        "No se pudo modificar."
                );

                enviarRespuesta(
                        intercambio,
                        "{\"error\":\"No se pudo modificar\"}"
                );
            }

            return;
        }

        // =========================
        // GET
        // =========================

        if (intercambio.getRequestMethod().equals("GET")) {

            int categoriaId = 1;

            String query =
                    intercambio.getRequestURI().getQuery();

            if (query != null &&
                    query.startsWith("categoria=")) {

                categoriaId =
                        Integer.parseInt(
                                query.substring(10)
                        );
            }

            ElementoDAO dao =
                    new ElementoDAO();

            var elementos =
                    dao.listarPorCategoria(categoriaId);

            StringBuilder json =
                    new StringBuilder("[");

            for (int i = 0; i < elementos.size(); i++) {

                Elemento elemento =
                        elementos.get(i);

                json.append("{");

                json.append("\"id\":")
                        .append(elemento.getId())
                        .append(",");

                json.append("\"categoriaId\":")
                        .append(elemento.getCategoriaId())
                        .append(",");

                json.append("\"texto\":\"")
                        .append(
                                elemento.getTexto()
                                        .replace("\\", "\\\\")
                                        .replace("\"", "\\\"")
                        )
                        .append("\"");

                json.append("}");

                if (i < elementos.size() - 1) {
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

        if (intercambio.getRequestMethod().equals("POST")) {

            String cuerpo =
                    new String(
                            intercambio.getRequestBody().readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            System.out.println(
                    "POST recibido: " + cuerpo
            );

            int categoriaId =
                    Integer.parseInt(
                            obtenerValor(
                                    cuerpo,
                                    "categoriaId"
                            )
                    );

            String texto =
                    obtenerValor(
                            cuerpo,
                            "texto"
                    );

            System.out.println(
                    "Categoría: " + categoriaId
            );

            System.out.println(
                    "Texto: " + texto
            );

            Elemento elemento =
                    new Elemento(
                            categoriaId,
                            texto
                    );

            ElementoDAO dao =
                    new ElementoDAO();

            boolean guardado =
                    dao.agregar(elemento);

            if (guardado) {

                System.out.println(
                        "Elemento guardado en MySQL."
                );

                enviarRespuesta(
                        intercambio,
                        "{\"mensaje\":\"Elemento guardado\"}"
                );

            } else {

                System.out.println(
                        "No se pudo guardar."
                );

                enviarRespuesta(
                        intercambio,
                        "{\"error\":\"No se pudo guardar\"}"
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
    // OBTENER VALORES JSON
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

        if (json.charAt(inicio) == '"') {

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

        int fin =
                json.indexOf(",", inicio);

        if (fin == -1) {

            fin =
                    json.indexOf(
                            "}",
                            inicio
                    );
        }

        return json.substring(
                inicio,
                fin
        ).trim();
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

        intercambio.getResponseHeaders().set(
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

