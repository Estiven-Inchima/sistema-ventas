/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectojava;

import controller.Controlador;
import model.Usuario;
import model.consultasUsuario;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ProyectoJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        
        MenuPrincipal menu = new MenuPrincipal();
        consultasUsuario modelo = new consultasUsuario();
        
        Controlador controlador = new Controlador(menu,usuario,modelo);

        
        controlador.iniciar();
        menu.setVisible(true);
        
        
    }
    
}
