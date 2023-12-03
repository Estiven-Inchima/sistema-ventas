/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ConsultasProductos;
import model.Producto;
import view.crearProducto_listar;
import view.crearProducto_modificar;

/**
 *
 * @author Usuario
 */
public class ControladorProductoModf implements ActionListener{
    
    private Producto producto;
    private ConsultasProductos modeloProductos;
    private crearProducto_listar vistaProductosL;
    private int id;
    private crearProducto_modificar vistaProductoM;

    public ControladorProductoModf(Producto producto, ConsultasProductos modeloProductos, crearProducto_listar vistaProductosL, int id, crearProducto_modificar vistaProductoM) {
        this.producto = producto;
        this.modeloProductos = modeloProductos;
        this.vistaProductosL = vistaProductosL;
        this.id = id;
        this.vistaProductoM = vistaProductoM;
        vistaProductoM.setLocationRelativeTo(null);
        vistaProductoM.setTitle("Modificar Producto");
        vistaProductoM.botonCancelar.addActionListener(this);
        vistaProductoM.botonGuardar.addActionListener(this);
        llenarDatos();
    }
    
    public void iniciar(){
        vistaProductoM.setVisible(true);
        
    }
    private void vaciar(){
        vistaProductoM.cajaCodigoProducto.setText(null);
        vistaProductoM.cajaNombre.setText(null);
    }
    
    private void llenarDatos(){
        modeloProductos.llenarDatos(id, vistaProductoM);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==vistaProductoM.botonCancelar){
            vistaProductoM.dispose();
        }
        
        if(e.getSource()==vistaProductoM.botonGuardar){
            producto.setCodigo(vistaProductoM.cajaCodigoProducto.getText());
            producto.setNombre(vistaProductoM.cajaNombre.getText());
            
            if(modeloProductos.modificar(id, producto)){
                JOptionPane.showMessageDialog(null,"Producto Modificado");
                vaciar();
                
                vistaProductosL.botonBuscar.doClick();
                vistaProductosL.cajaID.setText(null);
                vistaProductoM.dispose();
            }else{
                System.err.println("Error al insertar");
            }
            
            
        }
        
        
    }
    
}
