/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import view.CrearTerceros_listar;
import java.sql.ResultSetMetaData;
import view.CrearTerceros_Modificar;

public class ConsultasTerceros extends Conexion {

    PreparedStatement ps;
    ResultSet rs;

    public boolean Insertar(Tercero tercero) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("insert into tercero (cedulaNit,nombre,direccion,rol,celular) values (?,?,?,?,?)");
            ps.setString(1, tercero.getCedulaNit());
            ps.setString(2, tercero.getNombre());
            ps.setString(3, tercero.getDireccion());
            ps.setString(4, tercero.getRol());
            ps.setString(5, tercero.getCelular());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            System.err.println("Erro, " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error, " + ex);
            }
        }

    }

    public boolean Buscar(DefaultTableModel modeloTabla, CrearTerceros_listar vistaTercerosL, String opcion) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("select cedulaNit,nombre,rol from tercero " + opcion);
            rs = ps.executeQuery();
            int filas = 0;
            modeloTabla.addColumn("Cedula/Nit");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Rol");

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();

            int anchos[] = {50, 150, 20};
            for (int i = 0; i < cantidadColumnas; i++) {
                vistaTercerosL.tablaTerceros.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }

            while (rs.next()) {
                Object fila[] = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modeloTabla.addRow(fila);
                filas++;
            }

            if (filas >= 1) {
                if (filas == 1) {
                    vistaTercerosL.labelRegistros.setText("1 Usuario");
                } else if (filas > 1) {
                    vistaTercerosL.labelRegistros.setText(String.valueOf(filas) + " Usuarios");

                }
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            System.err.println("Error, " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error, " + ex);
            }
        }

    }
    
    
    
    
    
    public boolean datos (String consulta,CrearTerceros_listar vistaTercerosL){
        Connection conexion = getConnection();
        try{
            ps=conexion.prepareStatement(consulta);
            rs=ps.executeQuery();
            
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            String textoCompleto ="";
            
            while(rs.next()){
                vistaTercerosL.cajaID.setText(String.valueOf(rs.getString(1)));
                for(int i=2;i<=cantidadColumnas;i++){
                    int longitud = rsMD.getColumnName(i).length();
                    textoCompleto += rsMD.getColumnName(i).toUpperCase().charAt(0)+rsMD.getColumnName(i).substring(1,longitud)+": "+rs.getString(i)+"\n";
                }
            }
            vistaTercerosL.cajaTexto.setText("Información:\n"+textoCompleto);

            return true;
        }catch(Exception ex){
            System.err.println("Error, "+ex);
            return false;
        }finally{
            try{
                conexion.close();
            }catch(Exception ex){
                System.err.println("Error, "+ex);
            }
        }
        
        
    }
    
    
    public boolean llenarDatos(int id,CrearTerceros_Modificar vistaTercerosMdf){
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select * from tercero where idtercero = "+id);
            rs=ps.executeQuery();
            
            rs.next();
            System.out.println(rs.getInt(1));
            vistaTercerosMdf.cajaCedula.setText(rs.getString("cedulaNit"));
            vistaTercerosMdf.cajaNombre.setText(rs.getString("nombre"));
            vistaTercerosMdf.cajaDireccion.setText(rs.getString("direccion"));
            vistaTercerosMdf.comboRol.setSelectedItem(rs.getString("rol"));
            vistaTercerosMdf.cajaCelular.setText(rs.getString("celular"));
            
            
            
            
            return true;
        } catch (Exception e) {
            System.err.println("Error, "+e);
            return false;
        }finally{
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("Error, "+e);
            }
        }
        
        
        
    }
    
    public boolean modificar(int id,Tercero tercero){
      Connection conexion = getConnection();
        try {
            ps=conexion.prepareStatement("update tercero set cedulaNit=?,nombre=?,direccion=?,rol=?,celular=? where idtercero=?");
            ps.setString(1, tercero.getCedulaNit());
            ps.setString(2, tercero.getNombre());
            ps.setString(3, tercero.getDireccion());
            ps.setString(4, tercero.getRol());
            ps.setString(5, tercero.getCelular());
            ps.setInt(6, id);
            
            int resultado=ps.executeUpdate();
            if(resultado>0){
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
            } catch (Exception e) {
                System.err.println("Error, "+e);
            }
        }
        
        
        
    }
    
    public boolean eliminar(int id){
        Connection conexion = getConnection();
        try {
            ps=conexion.prepareStatement("delete from tercero where idtercero =?");
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
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
            } catch (Exception e) {
                System.err.println("Error, "+e);
            }
        }
    }

}
