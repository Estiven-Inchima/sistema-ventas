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
import view.CrearTerceros_Modificar;
import view.CrearTerceros_listar;



/**
 *
 * @author Usuario
 */
public class ControladorTerceroModf implements ActionListener {

    private int id;
    private CrearTerceros_Modificar vistaTercerosMdf;
    private ConsultasTerceros modeloTercero;
    private Tercero tercero;
    private CrearTerceros_listar vistaListar;
    
    
    public ControladorTerceroModf(int id, CrearTerceros_Modificar vistaTercerosMdf, ConsultasTerceros modeloTercero,Tercero tercero,CrearTerceros_listar vistaListar) {
        this.id = id;
        this.vistaTercerosMdf = vistaTercerosMdf;
        this.modeloTercero = modeloTercero;
        this.tercero=tercero;
        this.vistaListar=vistaListar;
        cargarDatos();
        vistaTercerosMdf.botonGuardar.addActionListener(this);
        vistaTercerosMdf.botonCancelar.addActionListener(this);
    }
    
    public void cargarDatos(){
        modeloTercero.llenarDatos(id, vistaTercerosMdf);
        vistaTercerosMdf.setLocationRelativeTo(null);
        vistaTercerosMdf.setTitle("Modificar Tercero");
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getSource()==vistaTercerosMdf.botonGuardar){
            tercero.setCedulaNit(vistaTercerosMdf.cajaCedula.getText());
            System.out.println("cedula::::"+tercero.getCedulaNit());
            tercero.setNombre(vistaTercerosMdf.cajaNombre.getText());
            tercero.setDireccion(vistaTercerosMdf.cajaDireccion.getText());
            tercero.setRol(vistaTercerosMdf.comboRol.getSelectedItem().toString());
            tercero.setCelular(vistaTercerosMdf.cajaCelular.getText());
            
            if(modeloTercero.modificar(id, tercero)){
                JOptionPane.showMessageDialog(null,"Tercero Modificado");
                vistaTercerosMdf.dispose();
                vistaListar.botonBuscar.doClick();
            }else{
                System.out.println("Error al modificar");
            }
            
            
        }
        if(e.getSource()==vistaTercerosMdf.botonCancelar){
            vistaTercerosMdf.dispose();
        }
        
    }
    
}
