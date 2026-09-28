/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public int agregar(Categoria categoria) {

        String sql =
                "INSERT INTO categorias (nombre, emoji) VALUES (?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia =
                        conexion.prepareStatement(
                                sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            sentencia.setString(
                    1,
                    categoria.getNombre()
            );

            sentencia.setString(
                    2,
                    categoria.getEmoji()
            );

            sentencia.executeUpdate();

            ResultSet resultado =
                    sentencia.getGeneratedKeys();

            if (resultado.next()) {

                return resultado.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al agregar categoría: "
                    + e.getMessage()
            );
        }

        return -1;
    }


    public List<Categoria> listar() {

        List<Categoria> categorias =
                new ArrayList<>();

        String sql =
                "SELECT id, nombre, emoji " +
                "FROM categorias " +
                "ORDER BY id";

        try (
                Connection conexion =
                        ConexionBD.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Categoria categoria =
                        new Categoria(
                                resultado.getInt("id"),
                                resultado.getString("nombre"),
                                resultado.getString("emoji")
                        );

                categorias.add(categoria);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al listar categorías: "
                    + e.getMessage()
            );
        }

        return categorias;
    }
}
