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
public class Producto {
    
    private int idproducto;
    private String codigo;
    private String nombre;

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   public String getNombre(){
       return nombre;
   }
   
   public void setNombre(String nombre){
       this.nombre=nombre;
   }
    
    
   public String toString(){
       return this.nombre+", "+this.codigo;
   } 
   
   public Vector<Producto> mostrarProductos(){//va a retornar un vector que contiene objetos de tipo producto
   
       PreparedStatement ps;
       ResultSet rs;
       
       Vector<Producto> vectorProductos = new Vector<Producto>();
       Producto producto = null;
       
       try{
           Conexion con = new Conexion();
           Connection conexion = con.getConnection();
           
           ps=conexion.prepareStatement("select * from producto");
           rs=ps.executeQuery();
           
           producto = new Producto();
           producto.setCodigo("");
           producto.setNombre("Seleccione un Producto");
           vectorProductos.add(producto);
           while(rs.next()){
           producto = new Producto();
           producto.setIdproducto(rs.getInt("idproducto"));
           producto.setCodigo(rs.getString("codigo"));
           producto.setNombre(rs.getString("nombre"));
           vectorProductos.add(producto);
           }
           rs.close();
           
       }catch(Exception ex){
           System.err.println("Error, "+ex);
       }
       
       return vectorProductos;
       
   }
    
}
