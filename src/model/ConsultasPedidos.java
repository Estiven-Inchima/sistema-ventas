/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import view.CrearPedidos_listar;

/**
 *
 * @author Usuario
 */
public class ConsultasPedidos extends Conexion {

    PreparedStatement ps;
    ResultSet rs;

    public boolean contarFilas() {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select count(*) from pedido");
            rs = ps.executeQuery();
            rs.next();
            int resultado = rs.getInt("count(*)");
            System.out.println("resultado BD: " + resultado);

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

    public int numeroPedido() {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select * from pedido order by idpedido desc limit 1");
            rs = ps.executeQuery();
            rs.next();
            int idpedido = rs.getInt("idpedido");

            return idpedido;
        } catch (Exception e) {
            System.err.println("Error, " + e);
            return -1;

        } finally {
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, " + e);
            }
        }

    }

    public boolean insertar(String values) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("insert into pedido (idpedido,fecha,idtercero,idproducto,cantidad,preciocompra,precioventa) values " + values);
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
    
    public int obtenerids(String busqueda){
       
        Connection conexion = getConnection();
        
        try {
           
        
                
                ps = conexion.prepareStatement("select idCantidad from cantidad  where codigoProducto = '"+busqueda+"' ;");
                rs = ps.executeQuery();
                rs.next();
                int id = rs.getInt("idCantidad");
                
               
            
            
            
           return id;

        } catch (Exception e) {
            System.err.println("Error, " + e);
          return -1;
        } finally{
            try{
                conexion.close();
            }catch(Exception e){
                System.err.println("Error, " + e);
            }
            
        }
        
        
   
    }
    
    
    
    public void actualizarCantidad(ArrayList<String> codigo ,ArrayList<String> cantidad ){
        
        
        int tam = codigo.size();
        ArrayList<Integer> ids = new ArrayList();
        for(int i =0; i<tam;i++){
            int id = obtenerids(codigo.get(i));
            ids.add(id);
            System.out.println("estos son los ids" + ids.get(i));
                    
        }
        
        
        
        
        Connection conexion = getConnection();
        try {
            
            for(int i=0;i<tam;i++){
                
                ps = conexion.prepareStatement("UPDATE cantidad set cantidadTotal = cantidadTotal + "+ Integer.parseInt(cantidad.get(i)) + " where idCantidad = "+ ids.get(i) + "; ");
                int resultado = ps.executeUpdate();

            }
            
    
        } catch (Exception e) {
            System.err.println("Error, " + e);
            System.out.println("aqui");
          
        }
        
    }

    public boolean traerPedido(String busqueda, DefaultTableModel modeloTabla, CrearPedidos_listar vistaPedidoL, String where) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select distinct p.idpedido,p.fecha,t.nombre from pedido as p inner join tercero as t on p.idtercero=t.idtercero " + where);
            rs = ps.executeQuery();
            int filas = 0;//para validar
            modeloTabla.addColumn("ID pedido");
            modeloTabla.addColumn("Fecha");
            modeloTabla.addColumn("Proveedor");
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();

            int ancho[] = {50, 70, 150};
            for (int i = 0; i < cantidadColumnas; i++) {
                vistaPedidoL.tablaPedidos.getColumnModel().getColumn(i).setPreferredWidth(ancho[i]);
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

    public boolean datosPedido(String idpedido, DefaultTableModel modeloTablaDetalle, CrearPedidos_listar vistaPedidoL, idpedidoAI objID) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select p.idpedidoAI,r.nombre,p.cantidad,p.preciocompra,p.precioventa from pedido as p inner join producto as r on p.idproducto = r.idproducto where p.idpedido=" + idpedido);
            rs = ps.executeQuery();

            modeloTablaDetalle.addColumn("Producto");
            modeloTablaDetalle.addColumn("Cantidad");
            modeloTablaDetalle.addColumn("P-Compra");
            modeloTablaDetalle.addColumn("P-Venta");

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount() - 1;
            int ancho[] = {150, 50, 50, 50};
            for (int i = 0; i < cantidadColumnas; i++) {
                vistaPedidoL.tablaDatosPedido.getColumnModel().getColumn(i).setPreferredWidth(ancho[i]);
            }
            while (rs.next()) {
                objID = new idpedidoAI();
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 1; i < cantidadColumnas; i++) {

                    fila[i] = rs.getString(i + 2);
                }
                //fila[0]=rs.getString(1);
                objID.setIdpedidoAI(rs.getInt(1));
                objID.setNombre(rs.getString(2));
                fila[0] = objID;
                modeloTablaDetalle.addRow(fila);

            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error," + e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error, " + e);
            }
        }
    }

    public boolean eliminarProducto(int idpedidoAI){
        Connection conexion = getConnection();
        try {
            
            ps=conexion.prepareStatement("delete from pedido where idpedidoAI= "+idpedidoAI);
            int resultado=ps.executeUpdate();
            System.out.println("resultado:"+resultado);

            if(resultado >0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error, "+e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error, "+e);
            }
        }
        
        
    }
    
    
    public boolean modificar(int idproducto,int cantidad,double pcompra,double pventa,int idpedidoAI){
        Connection conexion = getConnection();
        try {
            ps=conexion.prepareStatement("update pedido set idproducto=?,cantidad=?,preciocompra=?,precioventa=? where idpedidoAI=?");
            ps.setInt(1, idproducto);
            ps.setInt(2,cantidad);
            ps.setDouble(3,pcompra);
            ps.setDouble(4,pventa);
            ps.setInt(5,idpedidoAI);
            
            int resultado = ps.executeUpdate();
            if(resultado>0){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error, "+e);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error, "+e);
            }
        }
    }
    
    
}
