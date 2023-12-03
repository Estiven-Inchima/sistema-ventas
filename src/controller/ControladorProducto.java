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
import view.MenuPrincipal;
import view.crearProducto;

/**
 *
 * @author Usuario
 */
public class ControladorProducto implements ActionListener {

    private Producto producto;
    private crearProducto vistaProducto;
    private ConsultasProductos modeloProductos;
    private MenuPrincipal menu;

    public ControladorProducto(Producto producto, crearProducto vistaProducto, ConsultasProductos modeloProductos, MenuPrincipal menu) {
        this.producto = producto;
        this.vistaProducto = vistaProducto;
        this.modeloProductos = modeloProductos;
        this.menu = menu;
        vistaProducto.botonMenu.addActionListener(this);
        vistaProducto.botonCrear.addActionListener(this);
    }
    
    public void iniciar(){
        vistaProducto.setVisible(true);
        vistaProducto.setLocationRelativeTo(null);
    }
    
    
    
    
    public boolean validar(){
        boolean c1,c2;
        c1=vistaProducto.cajaCodigoProducto.getText().isEmpty();
        c2=vistaProducto.cajaNombre.getText().isEmpty();
        return c1||c2; 
    }
    public void vaciar(){
        vistaProducto.cajaCodigoProducto.setText(null);
        vistaProducto.cajaNombre.setText(null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==vistaProducto.botonMenu){
            vistaProducto.dispose();
            menu.setVisible(true);
        }
        
        
        if(e.getSource()==vistaProducto.botonCrear){
            
            if(!validar()){
                System.out.println("aqui");
                producto.setCodigo(vistaProducto.cajaCodigoProducto.getText());
                producto.setNombre(vistaProducto.cajaNombre.getText());
                if(modeloProductos.insertar(producto)){
                    modeloProductos.insertarCantidad(producto);
                    JOptionPane.showMessageDialog(null, "Producto Creado");
                    vaciar();
                }else{
                    JOptionPane.showMessageDialog(null, "no se pudo crear el producto");
                }
                
                
                
            }else{
                JOptionPane.showMessageDialog(null,"Por favor llene todos los campos");
            }
            
            
            
            
            
            
        }
        
        
        
    }
    
    
    
    
    
    
}
