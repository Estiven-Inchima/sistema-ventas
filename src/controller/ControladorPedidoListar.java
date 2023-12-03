/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ConsultasPedidos;
import model.idpedidoAI;
import view.CrearPedidos_listar;
import view.CrearPedidos_modificar;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorPedidoListar implements ActionListener {
    
    private MenuPrincipal menu;
    private CrearPedidos_listar vistaPedidosL;
    private ConsultasPedidos modeloPedidos;
    DefaultTableModel modeloTabla;
    DefaultTableModel modeloTablaDetalle;
    idpedidoAI objID;
    CrearPedidos_modificar vistaPedidosM;
    private ControladorPedidoModf controlPedidoModf;
    
    public ControladorPedidoListar(MenuPrincipal menu, CrearPedidos_listar vistaPedidos, ConsultasPedidos modeloPedidos) {
        this.menu = menu;
        this.vistaPedidosL = vistaPedidos;
        this.modeloPedidos = modeloPedidos;
        eventoDelTeclado();
        llenarTabla("");
        oyenteDeRaton();
        vistaPedidos.botonMenu.addActionListener(this);
        vistaPedidos.grupoRadioBotones.add(vistaPedidos.radioBotonFecha);
        vistaPedidos.grupoRadioBotones.add(vistaPedidos.radioBotonProveedor);
        vistaPedidos.grupoRadioBotones.add(vistaPedidos.radioBotonMes);
        vistaPedidos.radioBotonProveedor.setSelected(true);
        vistaPedidos.botonEliminar.addActionListener(this);
        vistaPedidos.botonModificar.addActionListener(this);
    }
    
    public void iniciar() {
        vistaPedidosL.setVisible(true);
        vistaPedidosL.setLocationRelativeTo(null);
        vistaPedidosL.setTitle("Lista Pedidos");
        
    }
    
    private void llenarTabla(String busqueda) {
        
        String where = "";
        
        if (!busqueda.equals("")) {
            
            if (vistaPedidosL.radioBotonProveedor.isSelected()) {
                where = " where t.nombre like '%" + busqueda + "%'";
            } else if (vistaPedidosL.radioBotonFecha.isSelected()) {
                where = " where p.fecha like '%" + busqueda + "%'";
            } else if (vistaPedidosL.radioBotonMes.isSelected()) {
                where = " where p.fecha like '%-" + busqueda + "-%'";
            }
        }
        modeloTabla = new DefaultTableModel();
        vistaPedidosL.tablaPedidos.setModel(modeloTabla);
        modeloPedidos.traerPedido(vistaPedidosL.cajaBuscar.getText(), modeloTabla, vistaPedidosL, where);
    }
    
    private void eventoDelTeclado() {
        KeyListener eventoTeclado = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased");

                llenarTabla(vistaPedidosL.cajaBuscar.getText());
                
            }
        };
        vistaPedidosL.cajaBuscar.addKeyListener(eventoTeclado);
        
    }
    
    private void oyenteDeRaton() {
        MouseListener oyenteDeRaton = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaPedidosL.tablaPedidos.getSelectedRow();
                String idpedido = vistaPedidosL.tablaPedidos.getValueAt(fila, 0).toString();
                System.out.println(idpedido);
                modeloTablaDetalle = new DefaultTableModel();
                vistaPedidosL.tablaDatosPedido.setModel(modeloTablaDetalle);
                if (modeloPedidos.datosPedido(idpedido, modeloTablaDetalle, vistaPedidosL, objID)) {
                    System.out.println("ok");
                } else {
                    System.out.println("no");
                }
                
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        };
        vistaPedidosL.tablaPedidos.addMouseListener(oyenteDeRaton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vistaPedidosL.botonMenu) {
            menu.setVisible(true);
            vistaPedidosL.dispose();
        }
        
       
        
        if (e.getSource() == vistaPedidosL.botonEliminar) {
            
            if (vistaPedidosL.tablaDatosPedido.getRowCount() > 0) {
                
                if (vistaPedidosL.tablaDatosPedido.getSelectedRow() >= 0) {
                    System.out.println("sel" + vistaPedidosL.tablaDatosPedido.getRowSelectionAllowed());
                    int fila = vistaPedidosL.tablaDatosPedido.getSelectedRow();
                    objID = new idpedidoAI();
                    objID = (idpedidoAI) vistaPedidosL.tablaDatosPedido.getValueAt(fila, 0);
                    System.out.println(" id: " + objID.getIdpedidoAI());
                    System.out.println("nombre: " + objID.getNombre());
                    
                    if (modeloPedidos.eliminarProducto(objID.getIdpedidoAI())) {
                        //System.out.println("eliminado"+objID.getIdpedidoAI());
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una pedido");
            }
            
        }
        
        
          if (e.getSource() == vistaPedidosL.botonModificar) {
            
            if (vistaPedidosL.tablaDatosPedido.getRowCount() > 0) {
                
                if (vistaPedidosL.tablaDatosPedido.getSelectedRow() >= 0) {
                    int fila = vistaPedidosL.tablaDatosPedido.getSelectedRow();
                    objID = new idpedidoAI();
                    objID = (idpedidoAI) vistaPedidosL.tablaDatosPedido.getValueAt(fila, 0);
                    
                    Object datos[]=new Object[4];
                    datos[0]=objID;
                    datos[1]=vistaPedidosL.tablaDatosPedido.getValueAt(fila, 1);
                    datos[2]=vistaPedidosL.tablaDatosPedido.getValueAt(fila, 2);
                    datos[3]=vistaPedidosL.tablaDatosPedido.getValueAt(fila, 3);
                    
                            
                            
                    vistaPedidosM = new CrearPedidos_modificar(null,true);
                    controlPedidoModf =  new ControladorPedidoModf(vistaPedidosM,modeloPedidos,datos);
                    vistaPedidosM.setVisible(true);
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una pedido");
            }
            
        }
        
        
        
        
        
    }
    
}
