/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.ConsultasPedidos;
import model.Pedido;
import model.Producto;
import model.Tercero;
import view.CrearPedidos;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorPedido implements ActionListener {

    private final MenuPrincipal menu;
    private final CrearPedidos vistaPedidos;

    private final ConsultasPedidos modeloPedidos;

    private final Pedido pedido;
    private Tercero tercero;
    private Producto producto;

    private DefaultTableModel modeloTabla;
    ArrayList<String> codigoProducto;
    ArrayList<String> cantidadASumar;

    public ControladorPedido(MenuPrincipal menu, CrearPedidos vistaPedidos, ConsultasPedidos modeloPedidos, Pedido pedido) {
        this.menu = menu;
        this.vistaPedidos = vistaPedidos;
        this.modeloPedidos = modeloPedidos;
        this.pedido = pedido;
        
        
        modeloTabla = new DefaultTableModel();
        vistaPedidos.tablaPedido.setModel(modeloTabla);
        llenarDatos();
        codigoProducto= new ArrayList();
        cantidadASumar= new ArrayList();
        
        this.vistaPedidos.botonMenu.addActionListener(this);
        this.vistaPedidos.botonCancelar.addActionListener(this);
        this.vistaPedidos.botonAgregar.addActionListener(this);
        this.vistaPedidos.botonEliminar.addActionListener(this);
        this.vistaPedidos.botonCrear.addActionListener(this);
    }

    public void iniciar() {
        vistaPedidos.setVisible(true);
        vistaPedidos.setLocationRelativeTo(null);
        vistaPedidos.setTitle("Crear Pedido");

    }

    private void llenarDatos() {
        Date fecha = new Date();
        vistaPedidos.cajaFecha.setDate(fecha);

        tercero = new Tercero();
        DefaultComboBoxModel modeloComboTerceros = new DefaultComboBoxModel(tercero.mostrarTerceros());
        vistaPedidos.comboProveedor.setModel(modeloComboTerceros);

        producto = new Producto();
        DefaultComboBoxModel modeloComboProductos = new DefaultComboBoxModel(producto.mostrarProductos());
        vistaPedidos.comboProducto.setModel(modeloComboProductos);

        
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("<html>Precio Compra</html>");
        modeloTabla.addColumn("Precio Venta");

        int anchos[] = {150, 50, 50, 50};
        for (int i = 0; i < anchos.length; i++) {
            vistaPedidos.tablaPedido.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    private void vaciar() {
        vistaPedidos.comboProducto.setSelectedIndex(0);
        vistaPedidos.cajaPrecioCompra.setText(null);
        vistaPedidos.cajaPrecioVenta.setText(null);
        vistaPedidos.cajaCantidad.setText(null);
    }
    
    private void vaciarTabla(){
        int filas = vistaPedidos.tablaPedido.getModel().getRowCount();
        Date fecha = new Date();
        vistaPedidos.cajaFecha.setDate(fecha);
        vistaPedidos.comboProveedor.setSelectedIndex(0);
        System.out.println("filas: "+filas);
        for(int i=filas; i>0;i--){
            modeloTabla.removeRow(i-1);
            System.out.println(i-1);
            
        }
    }

    private boolean validarProveedor() {
        String texto = tercero.mostrarTerceros().get(0).toString();
        System.out.println(texto);
        String texto2 = vistaPedidos.comboProveedor.getSelectedItem().toString();
        System.out.println(texto2);
        return !(texto.equals(texto2));
    }

    private boolean validarCampos() {
        boolean c1, c2, c3, c4;
        String texto = producto.mostrarProductos().get(0).toString();
        String texto2 = vistaPedidos.comboProducto.getSelectedItem().toString();
        c1 = texto.equals(texto2);
        c2 = vistaPedidos.cajaPrecioCompra.getText().isEmpty();
        c3 = vistaPedidos.cajaPrecioVenta.getText().isEmpty();
        c4 = vistaPedidos.cajaCantidad.getText().isEmpty();

        return c1 || c2 || c3 || c4;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPedidos.botonMenu) {
            vistaPedidos.dispose();
            menu.setVisible(true);
        }

        if (e.getSource() == vistaPedidos.botonCancelar) {
            vistaPedidos.dispose();
            menu.setVisible(true);
        }

        if (e.getSource() == vistaPedidos.botonAgregar) {

            if (validarProveedor()) {
                if (!(validarCampos())) {
                    Object fila[] = new Object[4];
                    Producto producto = (Producto) vistaPedidos.comboProducto.getSelectedItem();
                    fila[0] = producto;
                    fila[1] = vistaPedidos.cajaCantidad.getText();
                    fila[2] = vistaPedidos.cajaPrecioCompra.getText();
                    fila[3] = vistaPedidos.cajaPrecioVenta.getText();

                    modeloTabla.addRow(fila);
                    vaciar();
                    codigoProducto.add(producto.getCodigo());
                    cantidadASumar.add(fila[1].toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    System.out.println("complete todos los campos");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione el Proveedor");
            }
            System.out.println("");

        }

        if (e.getSource() == vistaPedidos.botonEliminar) {

            if (vistaPedidos.tablaPedido.getModel().getRowCount() > 0) {
                int fila = vistaPedidos.tablaPedido.getSelectedRow();
                if (fila > -1) {

                    System.out.println("Fila: " + fila);
                    modeloTabla.removeRow(fila);

                    System.out.println("eliminar");
                    System.out.println(vistaPedidos.tablaPedido.getModel().getRowCount());

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una Fila");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Agregue una fila");
            }

        }

        if (e.getSource() == vistaPedidos.botonCrear) {

            int idpedido = 0;
            if (modeloPedidos.contarFilas()) {
                idpedido = modeloPedidos.numeroPedido() + 1;
            }

            Date date = vistaPedidos.cajaFecha.getDate();
            System.out.println("fecha: " + date);
            long d = date.getTime();
            java.sql.Date fecha = new java.sql.Date(d);//la convertimos por que la BD no recibe Date de java.util sino de java.sql
            System.out.println("feha2: " + fecha);

            int filas = vistaPedidos.tablaPedido.getModel().getRowCount();
            System.out.println("filas: " + filas);
            System.out.println(vistaPedidos.tablaPedido.getValueAt(0, 0));
            System.out.println("");
            String values = "";
            String datos[] = new String[3];
            Tercero tercero = (Tercero) vistaPedidos.comboProveedor.getSelectedItem();
            int idtercero = tercero.getIdtercero();
            for (int i = 0; i < filas; i++) {
                Producto producto = (Producto) vistaPedidos.tablaPedido.getModel().getValueAt(i, 0);
                int idproducto = producto.getIdproducto();
                for (int j = 1; j <= 3; j++) {
                    //System.out.print(vistaPedidos.tablaPedido.getValueAt(i,j));
                    datos[j - 1] = (String) vistaPedidos.tablaPedido.getValueAt(i, j);
                }
                if (i == filas - 1) {
                    values += "('" + idpedido + "','" + fecha + "','" + idtercero + "','" + idproducto + "','" + datos[0] + "','" + datos[1] + "','" + datos[2] + "')";
                } else {
                    values += "('" + idpedido + "','" + fecha + "','" + idtercero + "','" + idproducto + "','" + datos[0] + "','" + datos[1] + "','" + datos[2] + "'),";
                }
                System.out.println("idPRODUCTO: " + producto.getIdproducto());
            }

            System.out.println(values);

            if(modeloPedidos.insertar(values)){
                JOptionPane.showMessageDialog(null, "Pedido Creado Correctamente.");
                vaciar();
                vaciarTabla();
                
                /*esto es para actualizar la cantidad de la tabla "cantidad"*/  
               modeloPedidos.actualizarCantidad(codigoProducto,cantidadASumar );
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo crear el Pedido");

            }
        }

    }

}
