/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public static Connection conectar() {

        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String usuario = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        String url =
                "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {

            Connection conexion =
                    DriverManager.getConnection(
                            url,
                            usuario,
                            password
                    );

            System.out.println(
                    "✅ Conexión a MySQL exitosa."
            );

            return conexion;

        } catch (SQLException e) {

            System.out.println(
                    "❌ Error al conectar con MySQL:"
            );

            System.out.println(
                    e.getMessage()
            );

            return null;
        }
    }
}
