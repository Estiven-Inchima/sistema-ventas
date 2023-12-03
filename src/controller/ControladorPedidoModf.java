/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.ConsultasPedidos;
import model.Producto;
import model.idpedidoAI;
import view.CrearPedidos_modificar;

/**
 *
 * @author Usuario
 */
public class ControladorPedidoModf implements ActionListener {

    private CrearPedidos_modificar vistaPedidosM;
    private ConsultasPedidos modeloPedidos;
    private Object datos[];
    private Producto producto;

    public ControladorPedidoModf(CrearPedidos_modificar vistaPedidosM, ConsultasPedidos modeloPedidos, Object datos[]) {
        this.vistaPedidosM = vistaPedidosM;
        this.modeloPedidos = modeloPedidos;
        this.datos = datos;
        producto = new Producto();

        vistaPedidosM.setLocationRelativeTo(null);
        vistaPedidosM.setTitle("Modificar Producto de Pedido");

        llenarDatos();
    }

    private void llenarDatos() {

        DefaultComboBoxModel modeloComboProducto = new DefaultComboBoxModel(producto.mostrarProductos());
        vistaPedidosM.comboProducto.setModel(modeloComboProducto);
        vistaPedidosM.botonCancelar.addActionListener(this);
        vistaPedidosM.botonGuardar.addActionListener(this);
        idpedidoAI dato = new idpedidoAI();
        dato = (idpedidoAI) datos[0];
        String texto = dato.getNombre();
        System.out.println(texto);
        vistaPedidosM.cajaPrecioCompra.setText((String) datos[2]);
        vistaPedidosM.cajaPrecioVenta.setText((String) datos[3]);
        vistaPedidosM.cajaCantidad.setText((String) datos[1]);
        vistaPedidosM.comboProducto.getModel().setSelectedItem(texto);

//        for(int i=0;i<producto.mostrarProductos().capacity()){
//            
//        }
    }

    private boolean validarCampos() {
        boolean c1, c2, c3, c4;
        c1 = vistaPedidosM.cajaCantidad.getText().isEmpty();
        c2 = vistaPedidosM.cajaPrecioCompra.getText().isEmpty();
        c3 = vistaPedidosM.cajaPrecioVenta.getText().isEmpty();
        String texto = producto.mostrarProductos().get(0).toString();
        String texto2 = vistaPedidosM.comboProducto.getSelectedItem().toString();
        c4 = texto.equals(texto2);

        return c1 || c2 || c3 || c4;
    }

    private int indiceObjeto(Vector<Producto> producto, String texto) {
        int a = -1;
        for (int i = 0; i < producto.size(); i++) {
            if (producto.get(i).getNombre().equals(texto)) {
                System.out.println("indice:" + i);
                a = i;
            }
        }
        return a;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaPedidosM.botonCancelar) {
            vistaPedidosM.dispose();
        }

        if (e.getSource() == vistaPedidosM.botonGuardar) {

            if (!validarCampos()) {

                int cantidad = Integer.parseInt(vistaPedidosM.cajaCantidad.getText());
                double pcompra = Double.parseDouble(vistaPedidosM.cajaPrecioCompra.getText());
                double pventa = Double.parseDouble(vistaPedidosM.cajaPrecioVenta.getText());
                Producto producto = (Producto)vistaPedidosM.comboProducto.getSelectedItem();
                int idproducto = producto.getIdproducto();
                
                idpedidoAI dato = (idpedidoAI) datos[0];
                int idpedidoAI = dato.getIdpedidoAI();
                System.out.println("texto del comboBox:  " + producto.getNombre());

                
                if(modeloPedidos.modificar(idproducto, cantidad, pcompra, pventa, idpedidoAI)){
                    JOptionPane.showMessageDialog(null, "registro Modificado Correctamente");
                    vistaPedidosM.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo modificar el registro");

                }
                
                
            } else {
                JOptionPane.showMessageDialog(null, "Llene todos los campos");
            }

        }

    }

}
