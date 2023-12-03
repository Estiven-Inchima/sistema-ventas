/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

import view.CrearVenta_listar;

/**
 *
 * @author Usuario
 */
public class ConsultasVentas extends Conexion {
    
    
    PreparedStatement ps;
    ResultSet rs;
    
     public boolean insertar(String values) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("insert into ventas (codigoProducto,cantidad,precio,idtercero) values " + values);
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return true;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }
    
     
     
     
     
      public boolean traerPedido(String busqueda, DefaultTableModel modeloTabla, CrearVenta_listar vistaVentaL, String where) {
        Connection conexion = getConnection();
        
        try {
            ps = conexion.prepareStatement("select distinct v.idtercero,t.nombre from ventas as v inner join tercero as t on t.idtercero = v.idtercero;");
            rs = ps.executeQuery();
            int filas = 0;//para validar
            modeloTabla.addColumn("Id Tercero");
            modeloTabla.addColumn("Nombre");
            
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            
          
            
            

            int ancho[] = {50,150};
            for (int i = 0; i < cantidadColumnas; i++) {
                vistaVentaL.tablaPedidos.getColumnModel().getColumn(i).setPreferredWidth(ancho[i]);
            }

            while (rs.next()) {
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                modeloTabla.addRow(fila);
                filas++;
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error, " + e);
            }
        }

    }
     
     
    
    
}
