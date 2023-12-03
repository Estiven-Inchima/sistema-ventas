/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import view.crearProducto_listar;
import java.sql.ResultSetMetaData;
import view.crearProducto_modificar;

public class ConsultasProductos extends Conexion {

    PreparedStatement ps;
    ResultSet rs;

    public boolean insertar(Producto producto) {
        Connection conexion = getConnection();
        try {

            ps = conexion.prepareStatement("insert into producto (codigo,nombre) values (?,?)");
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }

    }
    
    
    public void insertarCantidad(Producto producto){
         Connection conexion = getConnection();
         try {
             
            ps=conexion.prepareStatement("insert into cantidad (codigoProducto,cantidadTotal) values(?,?)");
            ps.setString(1, producto.getCodigo());
            ps.setInt(2, 0);
            int resultado2 = ps.executeUpdate();
        
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
            
    
}

    public boolean buscar(DefaultTableModel modeloTabla, String where, crearProducto_listar vistaProductoL) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select codigo,nombre from producto " + where);
            rs = ps.executeQuery();
            int filas = 0;
            modeloTabla.addColumn("Codigo");
            modeloTabla.addColumn("Nombre");

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            int ancho[] = {50, 150};
            for (int i = 0; i < cantidadColumnas; i++) {
                vistaProductoL.tablaProductos.getColumnModel().getColumn(i).setPreferredWidth(ancho[i]);
            }
            while (rs.next()) {
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                modeloTabla.addRow(fila);
                filas++;
            }

            if (filas >= 1) {
                if (filas == 1) {
                    vistaProductoL.labelFilas.setText("1 Producto");
                } else {
                    vistaProductoL.labelFilas.setText(filas + " Productos ");
                }
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }

    }

    public boolean datos(String codigo, crearProducto_listar vistaProductoL) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select idproducto from producto where codigo = '" + codigo + "'");
            rs = ps.executeQuery();

            while (rs.next()) {
                vistaProductoL.cajaID.setText(String.valueOf(rs.getInt(1)));
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean llenarDatos(int id, crearProducto_modificar vistaProductoM) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select * from producto where idproducto = " + id);
            rs = ps.executeQuery();
            rs.next();
            vistaProductoM.cajaCodigoProducto.setText(rs.getString("codigo"));
            vistaProductoM.cajaNombre.setText(rs.getString("nombre"));

            return true;

        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }

    }

    public boolean modificar(int id, Producto producto) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("update producto set codigo=?,nombre=? where idproducto = ?");
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setInt(3, id);

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }

    }

    public boolean eliminar(int id) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("delete from producto where idproducto = ?");
            ps.setInt(1, id);

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }

        }

    }

}
