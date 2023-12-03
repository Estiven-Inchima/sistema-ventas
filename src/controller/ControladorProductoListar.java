/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ConsultasProductos;
import model.Producto;
import view.MenuPrincipal;
import view.crearProducto_listar;
import view.crearProducto_modificar;

/**
 *
 * @author Usuario
 */
public class ControladorProductoListar implements ActionListener {

    private Producto producto;
    private crearProducto_listar vistaProductoL;
    private crearProducto_modificar vistaProductoM;
    private ConsultasProductos modeloProducto;
    private MenuPrincipal menu;
    private boolean seleccion = false, click = false;
    private int id;
    private ControladorProductoModf controladorProductoModf;

    public ControladorProductoListar(Producto producto, crearProducto_listar vistaProductoL, ConsultasProductos modeloProducto, MenuPrincipal menu) {
        this.producto = producto;
        this.vistaProductoL = vistaProductoL;
        this.modeloProducto = modeloProducto;
        this.menu = menu;
        vistaProductoL.botonMenu.addActionListener(this);
        vistaProductoL.botonBuscar.addActionListener(this);
        vistaProductoL.botonModificar.addActionListener(this);
        vistaProductoL.botonEliminar.addActionListener(this);
        
        vistaProductoL.grupoRadioBotones.add(vistaProductoL.radioBotonCodigoProducto);
        vistaProductoL.grupoRadioBotones.add(vistaProductoL.radioBotonNombre);
        vistaProductoL.radioBotonNombre.setSelected(true);

        oyenteDeRaton();
    }

    public void iniciar() {
        vistaProductoL.setVisible(true);
        vistaProductoL.setLocationRelativeTo(null);
        vistaProductoL.setTitle("Lista Productos");
    }

    private void oyenteDeRaton() {

        MouseListener oyenteDeRaton = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaProductoL.tablaProductos.getSelectedRow();
                String codigo = vistaProductoL.tablaProductos.getValueAt(fila, 0).toString();
                if (modeloProducto.datos(codigo, vistaProductoL)) {
                    System.out.println("datos");
                    click = true;
                    id = Integer.parseInt(vistaProductoL.cajaID.getText());
                } else {
                    System.out.println("no es posible ");
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

        vistaProductoL.tablaProductos.addMouseListener(oyenteDeRaton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaProductoL.botonMenu) {
            vistaProductoL.dispose();
            menu.setVisible(true);
        }

        if (e.getSource() == vistaProductoL.botonBuscar) {
            click = false;
            if (!(vistaProductoL.cajaBuscar.getText().isEmpty())) {
                System.out.println("no esta vacia");
                String campo = vistaProductoL.cajaBuscar.getText();
                String where = "";

                if (!(campo.equals("*"))) {
                    if (vistaProductoL.radioBotonCodigoProducto.isSelected()) {
                        where = "where codigo like '%" + campo + "%'";
                    } else if (vistaProductoL.radioBotonNombre.isSelected()) {
                        where = "where nombre like '%" + campo + "%'";
                    }
                }
                DefaultTableModel modeloTabla = new DefaultTableModel();
                vistaProductoL.tablaProductos.setModel(modeloTabla);

                if (modeloProducto.buscar(modeloTabla, where, vistaProductoL)) {
                    System.out.println("Busqueda Exitosa");
                    seleccion = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No hay coincidencias");
                    seleccion = false;
                    click = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Por favor llene el campo buscar");
            }
        }

        if (e.getSource() == vistaProductoL.botonModificar) {

            if (seleccion & click) {
                producto = new Producto();
                vistaProductoM = new crearProducto_modificar(vistaProductoL, true);

                controladorProductoModf = new ControladorProductoModf(producto, modeloProducto, vistaProductoL, id, vistaProductoM);
                controladorProductoModf.iniciar();

            } else {
                JOptionPane.showMessageDialog(null, "seleccione una fila");
            }

        }

        if(e.getSource()==vistaProductoL.botonEliminar){
            
            if(seleccion & click){
                
            
            if(modeloProducto.eliminar(id)){
                JOptionPane.showMessageDialog(null, "Producto Eliminado");
                vistaProductoL.botonBuscar.doClick();
                vistaProductoL.cajaID.setText(null);
                
            }else{
                JOptionPane.showMessageDialog(null, "no se puede eliminar el producto");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Por favor seleccione una fila");

            }
        }
        
    }

}
