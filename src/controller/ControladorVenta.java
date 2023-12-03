/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ConsultasVentas;
import model.Producto;
import model.Tercero;
import view.CrearVentas;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorVenta implements ActionListener{

    private CrearVentas vistaVenta;
    private ConsultasVentas modeloVentas;
    private MenuPrincipal menu;
    
    
    private Tercero tercero;
    private DefaultTableModel modeloTabla;
    private Producto producto;
    DefaultComboBoxModel modeloComboProductos;
    public ControladorVenta(CrearVentas vistaVenta, ConsultasVentas modeloVentas, MenuPrincipal menu) {
        this.vistaVenta = vistaVenta;
        this.modeloVentas = modeloVentas;
        this.menu = menu;
        producto = new Producto();
        modeloTabla = new DefaultTableModel();
        vistaVenta.tablaVenta.setModel(modeloTabla);
        cargarDatos();
        vistaVenta.botonMenu.addActionListener(this);
        vistaVenta.botonVender.addActionListener(this);
        vistaVenta.botonCancelar.addActionListener(this);
        vistaVenta.btnFilaNueva.addActionListener(this);
        
    }

    public void iniciar(){
        vistaVenta.setVisible(true);
        vistaVenta.setLocationRelativeTo(null);
        vistaVenta.setTitle("Vender");
    }
    
    public Object[] nuevafila(){
        Object fila[]= new Object[2];
        JComboBox c = new JComboBox();
        
        DefaultComboBoxModel modeloComboProductos = new DefaultComboBoxModel(producto.mostrarProductos());
        modeloComboProductos.removeElementAt(0);
        c.setModel(modeloComboProductos);
        
        vistaVenta.tablaVenta.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(c));
        
        return fila;
    }
    
    public void cargarDatos(){
        Date fecha = new Date();
        vistaVenta.cajaFecha.setDate(fecha);
        
        tercero=new Tercero();
        DefaultComboBoxModel modeloComboClientes = new DefaultComboBoxModel(tercero.mostrarTercerosClientes());
        vistaVenta.comboProveedor.setModel(modeloComboClientes);
        
        
        
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("P venta");
        
       
        Object fila[]= nuevafila();
        
        int anchos[] = {150, 50, 50};
        for (int i = 0; i < anchos.length; i++) {
            vistaVenta.tablaVenta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        modeloTabla.addRow(fila);
        
        
        
    }
    private void vaciarTabla(){
        int filas = vistaVenta.tablaVenta.getModel().getRowCount();
        Date fecha = new Date();
        vistaVenta.cajaFecha.setDate(fecha);
        vistaVenta.comboProveedor.setSelectedIndex(0);
        
        for(int i=filas; i>0;i--){
            modeloTabla.removeRow(i-1);
 
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==vistaVenta.botonMenu){
            menu.setVisible(true);
            vistaVenta.dispose();
        }
        
        if(e.getSource()==vistaVenta.botonCancelar){
            menu.setVisible(true);
            vistaVenta.dispose();
        }
        
        if(e.getSource()==vistaVenta.botonVender){
            
            
            
            Tercero tercero = (Tercero) vistaVenta.comboProveedor.getSelectedItem();
            int idtercero = tercero.getIdtercero();
            String datos[] = new String[2];
            String values ="";
            int filas=modeloTabla.getRowCount();
            for(int i=0;i<filas;i++){
            Producto producto = (Producto) vistaVenta.tablaVenta.getModel().getValueAt(i, 0);
            String codigoProducto = producto.getCodigo();
                for(int j=1;j<=2;j++){
                    datos[j-1]=(String)vistaVenta.tablaVenta.getValueAt(i, j);
                } 
                
                if(i == filas - 1){
                    values += "('" + codigoProducto + "','" + datos[0] + "','" + datos[1] +"','" + idtercero +"')";
                }else{
                    values += "('" + codigoProducto + "','" + datos[0] + "','" + datos[1] +"','" + idtercero+ "'),";
                }
            }
            System.out.println(values);
            if(modeloVentas.insertar(values)){
                JOptionPane.showMessageDialog(null, "Venta Creada Correctamente.");
                    vaciarTabla();

                
            }
            
            
           
            
            
            
        }
        
        
        
        if(e.getSource()==vistaVenta.btnFilaNueva){
              Object fila[]= nuevafila();
       
            modeloTabla.addRow(fila);
        }
        
        
    }
    
    
}
