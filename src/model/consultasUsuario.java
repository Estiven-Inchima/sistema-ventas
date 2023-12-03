/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Usuario
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import view.CrearUsuario_ListarModificar;
import view.CrearUsuario_Modificar;

public class consultasUsuario extends Conexion {

    PreparedStatement ps;
    ResultSet rs;

    public boolean insertar(Usuario usuario) {
        Connection conexion = getConnection();//metodo de la clase Conexion.java
        System.out.println("conexion");
        try {
            ps = conexion.prepareStatement("insert into usuario (usuario,cedula,celular,rol,nombre,correo,contraseña,confcontraseña) values(?,?,?,?,?,?,?,?)");

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getCedula());
            ps.setString(3, usuario.getCelular());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getNombre());
            ps.setString(6, usuario.getCorreo());
            ps.setString(7, usuario.getContraseña());
            ps.setString(8, usuario.getConfContraseña());

            int resultadoDeLaInsercion = ps.executeUpdate();

            if (resultadoDeLaInsercion > 0) {
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
            } catch (Exception e) {
                System.err.println("error, " + e);
            }

        }

    }

    public boolean buscar(DefaultTableModel modeloTabla, CrearUsuario_ListarModificar vistaUsuarioL, String opcion) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("Select usuario,nombre,rol from usuario " + opcion);
            rs = ps.executeQuery();
            int filas = 0;
            modeloTabla.addColumn("Usuario");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Rol");

            ResultSetMetaData rsMD = rs.getMetaData();//obtenemos el metadata para acceder a las filas
            int cantidadColumnas = rsMD.getColumnCount();//contamos las columnas y guardamos el valor en una variable int

            int anchos[] = {50, 150, 20};//definimos el ancho de las columnas
            for (int i = 0; i < cantidadColumnas; i++) {//pasamos los anchos a las columnas
                vistaUsuarioL.tablaUsuarios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
            }

            //insertamos los datos de la BD
            while (rs.next()) {
                Object fila[] = new Object[cantidadColumnas];//fila es un array que guarda cualquier tipo de dato por eso es object, y es de tamaño cantidadColumnas
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);//esto ya serea la fila con los datos de las columnas.
                }
                //agregamos la fila al modelo
                modeloTabla.addRow(fila);
                filas++;
                
            }

            if (filas >= 1) {
                
                if(filas==1){
                    vistaUsuarioL.labelRegistros.setText("1 Usuario");
                }else if(filas>1){
                    vistaUsuarioL.labelRegistros.setText(String.valueOf(filas)+" Usuarios");
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

    public boolean datos(String consulta, CrearUsuario_ListarModificar vistaUsuarioL) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            System.out.println("aqui");

            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            System.out.println(cantidadColumnas);
            String texto[] = new String[cantidadColumnas];
            String textoCompleto = "";

            while (rs.next()) {
                vistaUsuarioL.cajaID.setText(String.valueOf(rs.getString(1)));
                //para hacer estos for es importante llamar a next() por eso estan en el while, se llama a next() para poder comenzar a leer desde la primera fila
                for (int i = 2; i <= cantidadColumnas; i++) {
                    int longitud = rsMD.getColumnName(i).length();
                    textoCompleto += rsMD.getColumnName(i).toUpperCase().charAt(0) + rsMD.getColumnName(i).substring(1, longitud) + ": " + rs.getString(i) + "\n";
                }


            }


            vistaUsuarioL.cajaTexto.setText("Información:\n" + textoCompleto);

            return true;
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

    public boolean llenarDatos(int id, CrearUsuario_Modificar vistaUsuarioMdf) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("select * from usuario where idusuario=" + id);
            rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getInt(1)); //usuario,cedula,celular,rol,nombre,correo,contraseña,confcontraseña

            vistaUsuarioMdf.cajaUsuario.setText(rs.getString("usuario"));
            vistaUsuarioMdf.cajaCedula.setText(rs.getString("cedula"));
            vistaUsuarioMdf.cajaCelular.setText(rs.getString("celular"));
            vistaUsuarioMdf.comboRol.setSelectedItem(rs.getString("rol"));
            vistaUsuarioMdf.cajaNombre.setText(rs.getString("nombre"));
            vistaUsuarioMdf.cajaCorreo.setText(rs.getString("correo"));
            vistaUsuarioMdf.cajaContraseña.setText(rs.getString("contraseña"));
            vistaUsuarioMdf.cajaValidarContraseña.setText(rs.getString("confcontraseña"));

            return true;
        } catch (Exception ex) {
            System.err.println("Error, llenar Datos: " + ex);
            return false;
        } finally {
            try {
                conexion.close();
            } catch (Exception ex) {
                System.err.println("Error, cerrar Conexion " + ex);
            }

        }

    }

    public boolean modificar(int id, Usuario usuario) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("update usuario set  usuario=?,cedula=?,celular=?,rol=?,nombre=?,correo=?,contraseña=?,confcontraseña=? where idusuario = ?");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getCedula());
            ps.setString(3, usuario.getCelular());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getNombre());
            ps.setString(6, usuario.getCorreo());
            ps.setString(7, usuario.getContraseña());
            ps.setString(8, usuario.getConfContraseña());
            ps.setInt(9, id);

            int resultado = ps.executeUpdate();
            if (resultado > 0) {
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

    public boolean eliminar(int id) {
        Connection conexion = getConnection();
        try {
            ps = conexion.prepareStatement("delete from usuario where idusuario = ? ");
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
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
}
