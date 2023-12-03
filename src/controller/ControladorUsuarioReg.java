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
import view.CrearUsuario_registrar;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorUsuarioReg implements ActionListener{

    private CrearUsuario_registrar vistaUsuario;
    private Usuario usuario;
    private consultasUsuario modelo;
    private MenuPrincipal menu;
    
    public ControladorUsuarioReg(CrearUsuario_registrar vistaUsuario,Usuario usuario, consultasUsuario modelo,MenuPrincipal menu){
        this.vistaUsuario=vistaUsuario;
        this.usuario=usuario;
        this.modelo=modelo;
        this.menu=menu;
        vistaUsuario.botonCrear.addActionListener(this);
        vistaUsuario.botonMenu.addActionListener(this);
    }
    
    public void iniciar(){
        vistaUsuario.setLocationRelativeTo(null);
        vistaUsuario.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      
       if (e.getSource() == vistaUsuario.botonCrear) {
            System.out.println("boton crear");
            usuario.setUsuario(vistaUsuario.cajaUsuario.getText());
            usuario.setCedula(vistaUsuario.cajaCedula.getText());
            usuario.setCelular(vistaUsuario.cajaCelular.getText());
            usuario.setRol(vistaUsuario.comboRol.getSelectedItem().toString());
            usuario.setNombre(vistaUsuario.cajaNombre.getText());
            usuario.setCorreo(vistaUsuario.cajaCorreo.getText());
            String contraseña = new String(vistaUsuario.cajaContraseña.getPassword());
            usuario.setContraseña(contraseña);
            String confcontraseña = new String(vistaUsuario.cajaValidarContraseña.getPassword());
            usuario.setConfContraseña(confcontraseña);

            if (modelo.insertar(usuario)) {
                JOptionPane.showMessageDialog(null, "usuario creado");
                vaciar();
            } else {
                JOptionPane.showMessageDialog(null, "no creado");
            }

        } 
    
    
    
       if(e.getSource()==vistaUsuario.botonMenu){
           menu.setVisible(true);
           vistaUsuario.dispose();
       }
    
    
    }
    
    
    public void vaciar(){
        vistaUsuario.cajaUsuario.setText(null);
        vistaUsuario.cajaCedula.setText(null);
        vistaUsuario.cajaCelular.setText(null);
        vistaUsuario.comboRol.setSelectedIndex(0);
        vistaUsuario.cajaNombre.setText(null);
        vistaUsuario.cajaCorreo.setText(null);
        vistaUsuario.cajaContraseña.setText(null);
        vistaUsuario.cajaValidarContraseña.setText(null);
        
    }
    
}
