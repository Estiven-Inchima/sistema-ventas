/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ConsultasTerceros;
import model.Tercero;
import view.CrearTerceros;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorTercero implements ActionListener {

    private CrearTerceros vistaTerceros;
    private ConsultasTerceros modeloTerceros;
    private MenuPrincipal menu;
    private Tercero tercero;

    public ControladorTercero(CrearTerceros vistaTerceros, MenuPrincipal menu, ConsultasTerceros modeloTerceros, Tercero tercero) {
        this.vistaTerceros = vistaTerceros;
        this.menu = menu;
        this.modeloTerceros = modeloTerceros;
        this.tercero = tercero;
        vistaTerceros.botonMenu.addActionListener(this);
        vistaTerceros.botonCrear.addActionListener(this);
        
    }

    public void iniciar() {
        vistaTerceros.setVisible(true);
        vistaTerceros.setLocationRelativeTo(null);
        vistaTerceros.setTitle("Terceros");
        
    }

    public void vaciar() {
        vistaTerceros.cajaCedula.setText(null);
        vistaTerceros.cajaNombre.setText(null);
        vistaTerceros.cajaDireccion.setText(null);
        vistaTerceros.comboRol.setSelectedIndex(0);
        vistaTerceros.cajaCelular.setText(null);
    }
    
    public boolean validar(){
        boolean r1,r2,r3,r4,r5;
        r1=vistaTerceros.cajaCedula.getText().isEmpty();
        r2=vistaTerceros.cajaNombre.getText().isEmpty();
        r3=vistaTerceros.cajaDireccion.getText().isEmpty();
        r4=vistaTerceros.comboRol.getSelectedItem().equals("Seleccione");
        r5=vistaTerceros.cajaCelular.getText().isEmpty();

        
        return r1||r2||r3||r4||r5;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaTerceros.botonMenu) {
            vistaTerceros.dispose();
            menu.setVisible(true);
            
        }

        if (e.getSource() == vistaTerceros.botonCrear) {

            if(validar()){
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos.");
            }else{
            
            tercero.setCedulaNit(vistaTerceros.cajaCedula.getText());
            tercero.setNombre(vistaTerceros.cajaNombre.getText());
            tercero.setDireccion(vistaTerceros.cajaDireccion.getText());
            tercero.setRol(vistaTerceros.comboRol.getSelectedItem().toString());
            tercero.setCelular(vistaTerceros.cajaCelular.getText());

            if (modeloTerceros.Insertar(tercero)) {
                JOptionPane.showMessageDialog(null, "Tercero Creado");
                vaciar();

            } else {
                JOptionPane.showMessageDialog(null, "no se pudo crear el tercero");
            }
          }

        }

    }

}
