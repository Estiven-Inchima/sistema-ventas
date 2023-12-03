/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
//IMPORTANTE AÑADIR A LA LIBRERIA, EL DRIVER PARA HACER LA CONEXION CON MYSQL

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static final String URL="jdbc:mysql://localhost:3306/proyecto_quesos?autoReconnet=true&useSSL=false";
    public static final String usuario="root";
    public static final String contraseña="1234";
    
    
        //metodo para hacer la conexion a la BD
    public Connection getConnection(){
        Connection conexion = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");//ESTA ES LA CLASE DE LA LIBRERIA(DRIVER)
            conexion = (Connection) DriverManager.getConnection(URL, usuario, contraseña);//OBTENEMOS LA CONEXION, ADEMAS LE ENVIAMOS LOS DATOS PARA QUE SE PUEDA CONECTAR
            //JOptionPane.showMessageDialog(null,"conecto");
        }catch(Exception ex){
            System.err.println("Error, "+ex);
        }
        return conexion;
    }
    
    
}
