/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author Usuario
 */
public class Tercero {
    
    private int idtercero;
    private String cedulaNit;
    private String nombre;
    private String direccion;
    private String rol;
    private String celular;

    public int getIdtercero() {
        return idtercero;
    }

    public void setIdtercero(int idtercero) {
        this.idtercero = idtercero;
    }

    public String getCedulaNit() {
        return cedulaNit;
    }

    public void setCedulaNit(String cedulaNit) {
        this.cedulaNit = cedulaNit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String Direccion) {
        this.direccion = Direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    //modificamos el metodo toString
    public String toString(){
        return this.nombre+", "+this.direccion;
    }
    //creamos un vector de TERCEROS
    public Vector<Tercero> mostrarTerceros(){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Tercero> vectorTerceros = new Vector<Tercero>();
        Tercero tercero=null;
        
        try {
            Conexion con = new Conexion();//objeto de la clase Conexion.java
            Connection conexion = con.getConnection();
            ps=conexion.prepareStatement("select * from tercero where rol = 'Proveedor'");
            rs=ps.executeQuery();
            
            
            tercero = new Tercero();
            tercero.setIdtercero(0);
            tercero.setNombre("Seleccione Un Tercero");
            tercero.setDireccion("");
            vectorTerceros.add(tercero);
            
            while(rs.next()){
                tercero = new Tercero();
                tercero.setIdtercero((rs.getInt("idtercero")));
                tercero.setNombre(rs.getString("nombre"));
                tercero.setDireccion(rs.getString("direccion"));
                vectorTerceros.add(tercero);
            }
           
            rs.close();
            
        } catch (Exception e) {
            System.err.println("Error, "+e);
        }
        
        return vectorTerceros;
    }
    
    
    
    
      //creamos un vector de paises
    public Vector<Tercero> mostrarTercerosClientes(){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        Vector<Tercero> vectorTerceros = new Vector<Tercero>();
        Tercero tercero=null;
        
        try {
            Conexion con = new Conexion();//objeto de la clase Conexion.java
            Connection conexion = con.getConnection();
            ps=conexion.prepareStatement("select * from tercero where rol = 'Cliente'");
            rs=ps.executeQuery();
            
            
            tercero = new Tercero();
            tercero.setIdtercero(0);
            tercero.setNombre("Seleccione Un Cliente");
            tercero.setDireccion("");
            vectorTerceros.add(tercero);
            
            while(rs.next()){
                tercero = new Tercero();
                tercero.setIdtercero((rs.getInt("idtercero")));
                tercero.setNombre(rs.getString("nombre"));
                tercero.setDireccion(rs.getString("direccion"));
                vectorTerceros.add(tercero);
            }
           
            rs.close();
            
        } catch (Exception e) {
            System.err.println("Error, "+e);
        }
        
        return vectorTerceros;
    }
    
    
    
    
    
    
}
