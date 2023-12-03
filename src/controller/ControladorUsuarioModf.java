/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import model.consultasUsuario;
import view.CrearUsuario_ListarModificar;
import view.CrearUsuario_Modificar;

/**
 *
 * @author Usuario
 */
public class ControladorUsuarioModf implements ActionListener {

    private int id;
    private CrearUsuario_Modificar vistaUsuarioMdf;
    private consultasUsuario modelo;
    private Usuario usuario;
    private CrearUsuario_ListarModificar vistaUsuarioL;

    public ControladorUsuarioModf(int id, CrearUsuario_Modificar vistaUsuarioMdf, consultasUsuario modelo, Usuario usuario,CrearUsuario_ListarModificar vistaUsuarioL) {
        this.id = id;
        this.vistaUsuarioMdf = vistaUsuarioMdf;
        this.modelo = modelo;
        this.usuario = usuario;
        this.vistaUsuarioL=vistaUsuarioL;
        cargarDatos();
        vistaUsuarioMdf.botonCancelar.addActionListener(this);
        vistaUsuarioMdf.botonGuardar.addActionListener(this);
        
        
    }

    private void cargarDatos() {
        modelo.llenarDatos(id, vistaUsuarioMdf);
        vistaUsuarioMdf.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaUsuarioMdf.botonGuardar) {
            usuario.setUsuario(vistaUsuarioMdf.cajaUsuario.getText());
            usuario.setCedula(vistaUsuarioMdf.cajaCedula.getText());
            usuario.setCelular(vistaUsuarioMdf.cajaCelular.getText());
            usuario.setRol(vistaUsuarioMdf.comboRol.getSelectedItem().toString());
            usuario.setNombre(vistaUsuarioMdf.cajaNombre.getText());
            usuario.setCorreo(vistaUsuarioMdf.cajaCorreo.getText());
            String contraseña = new String(vistaUsuarioMdf.cajaContraseña.getPassword());
            usuario.setContraseña(contraseña);
            String confContraseña = new String (vistaUsuarioMdf.cajaValidarContraseña.getPassword());
            usuario.setConfContraseña(confContraseña);
            
            if(modelo.modificar(id,usuario)){
                JOptionPane.showMessageDialog(null,"Usuario Modificado");
                vistaUsuarioMdf.dispose();
                vistaUsuarioL.botonBuscar.doClick();
                
            }else{
                System.err.println("Error al modificar");
            }

        }
        if (e.getSource() == vistaUsuarioMdf.botonCancelar) {
            vistaUsuarioMdf.dispose();
            
        }

    }

}
