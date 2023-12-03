/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import model.ConsultasVentas;
import view.CrearVenta_listar;

import view.MenuPrincipal;

public class ControladorVentaListar implements ActionListener {
    private CrearVenta_listar vistaVentaL;
    private ConsultasVentas modeloVentas;
    private MenuPrincipal menu;
    DefaultTableModel modeloTabla;
    
    public ControladorVentaListar(CrearVenta_listar vistaVentaL, ConsultasVentas modeloVentas, MenuPrincipal menu) {
        this.vistaVentaL = vistaVentaL;
        this.modeloVentas = modeloVentas;
        this.menu = menu;
       
         llenarTabla();
        vistaVentaL.botonMenu.addActionListener(this);
        
    }
     public void iniciar(){
        vistaVentaL.setVisible(true);
        vistaVentaL.setLocationRelativeTo(null);
        vistaVentaL.setTitle("Vender");
    }

     public void llenarTabla(){
         String busqueda = "a";
         String where="where t.nombre like '%" + busqueda + "%'";
         modeloTabla = new DefaultTableModel();
        vistaVentaL.tablaPedidos.setModel(modeloTabla);
        modeloVentas.traerPedido(vistaVentaL.cajaBuscar.getText(), modeloTabla, vistaVentaL, where);
         
         
         
     }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if(e.getSource()==vistaVentaL.botonMenu){
            menu.setVisible(true);
            vistaVentaL.dispose();
        }
        
        
        
        }
    
}
