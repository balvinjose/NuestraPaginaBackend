/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nuestrapaginabackend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElementoDAO {

    // AGREGAR ELEMENTO
    public boolean agregar(Elemento elemento) {

        String sql = """
                INSERT INTO elementos (categoria_id, texto)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, elemento.getCategoriaId());
            sentencia.setString(2, elemento.getTexto());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar elemento: " + e.getMessage());
            return false;
        }
    }

    // LISTAR ELEMENTOS
    public List<Elemento> listarPorCategoria(int categoriaId) {

        List<Elemento> elementos = new ArrayList<>();

        String sql = """
                SELECT id, categoria_id, texto
                FROM elementos
                WHERE categoria_id = ?
                ORDER BY id
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, categoriaId);

            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {

                Elemento elemento = new Elemento(
                        resultado.getInt("id"),
                        resultado.getInt("categoria_id"),
                        resultado.getString("texto")
                );

                elementos.add(elemento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar elementos: " + e.getMessage());
        }

        return elementos;
    }

    // MODIFICAR ELEMENTO
    public boolean modificar(Elemento elemento) {

        String sql = """
                UPDATE elementos
                SET texto = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, elemento.getTexto());
            sentencia.setInt(2, elemento.getId());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al modificar elemento: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR ELEMENTO
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM elementos
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar elemento: " + e.getMessage());
            return false;
        }
    }
}
