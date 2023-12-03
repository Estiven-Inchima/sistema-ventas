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
import model.Usuario;
import model.consultasUsuario;
import view.CrearPedidos_modificar;
import view.CrearUsuario_ListarModificar;
import view.CrearUsuario_Modificar;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorUsuario implements ActionListener {

    private CrearUsuario_ListarModificar vistaUsuarioL;
    private Usuario usuario;
    private consultasUsuario modelo;
    private MenuPrincipal menu;
    private boolean seleccion = false, click = false;
    private int id;

    public ControladorUsuario(CrearUsuario_ListarModificar vistaUsuarioL, Usuario usuario, consultasUsuario modelo, MenuPrincipal menu) {
        this.vistaUsuarioL = vistaUsuarioL;
        this.usuario = usuario;
        this.modelo = modelo;
        this.menu = menu;
        vistaUsuarioL.botonBuscar.addActionListener(this);
        vistaUsuarioL.botonMenu.addActionListener(this);
        vistaUsuarioL.botonEliminar.addActionListener(this);
        vistaUsuarioL.grupoBotones.add(vistaUsuarioL.radioBotonUsuario);
        vistaUsuarioL.grupoBotones.add(vistaUsuarioL.radioBotonCedula);
        vistaUsuarioL.grupoBotones.add(vistaUsuarioL.radioBotonNombre);
        vistaUsuarioL.grupoBotones.add(vistaUsuarioL.radioBotonCorreo);
        vistaUsuarioL.radioBotonUsuario.setSelected(true);
        vistaUsuarioL.botonModificar.addActionListener(this);
        eventoOyenteDeRaton();
    }

    public void iniciar() {
        vistaUsuarioL.setVisible(true);
    }

    public void eventoOyenteDeRaton() {
        MouseListener oyenteDeRaton = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println("seleccionado");
                int fila = vistaUsuarioL.tablaUsuarios.getSelectedRow();
                String usuario = vistaUsuarioL.tablaUsuarios.getValueAt(fila, 0).toString();
                String consulta = "select idusuario,usuario,cedula,celular,rol,nombre,correo from usuario where usuario = '" + usuario + "'";
                if (modelo.datos(consulta, vistaUsuarioL)) {
                    System.out.println("datos");
                    click = true;
                    id = Integer.parseInt(vistaUsuarioL.cajaID.getText());
                } else {
                    System.out.println("no datos");

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
        vistaUsuarioL.tablaUsuarios.addMouseListener(oyenteDeRaton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaUsuarioL.botonBuscar) {
            click=false;
            vistaUsuarioL.cajaTexto.setText(null);
            System.out.println("boton buscar");
            DefaultTableModel modeloTabla = new DefaultTableModel();
            vistaUsuarioL.tablaUsuarios.setModel(modeloTabla);
            String campo = vistaUsuarioL.cajaBuscar.getText();
            String opcion = "";
            if (!"".equals(campo)) {

                if (campo.equals("*")) {
                    campo = opcion = "";
                } else {

                    if (vistaUsuarioL.radioBotonUsuario.isSelected() == true) {
                        opcion = "where usuario like '"+campo+"%'";
                    } else if (vistaUsuarioL.radioBotonCedula.isSelected() == true) {
                        opcion = "where cedula like '"+campo+"%'";
                    } else if (vistaUsuarioL.radioBotonNombre.isSelected() == true) {
                        opcion = "where nombre like '%"+campo+"%'";
                    } else if (vistaUsuarioL.radioBotonCorreo.isSelected() == true) {
                        opcion = "where correo like '%"+campo+"%'";
                    }

                }
                if (modelo.buscar(modeloTabla, vistaUsuarioL, opcion)) {
                    JOptionPane.showMessageDialog(null, "usuario");
                    seleccion = true;
                } else {
                    JOptionPane.showMessageDialog(null, "usuario no encontrado aqui");
                    seleccion = false;
                    click = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "porfavor llene el campo buscar");
            }

        }

        if (e.getSource() == vistaUsuarioL.botonMenu) {
            menu.setVisible(true);
            vistaUsuarioL.dispose();
        }

        if (e.getSource() == vistaUsuarioL.botonModificar) {

            if (seleccion && click) {
                System.out.println("boton modificar ::: " + vistaUsuarioL.tablaUsuarios.getRowSelectionAllowed());
                System.out.println("ID para modificar: " + id);
                CrearUsuario_Modificar vistaUsuarioMdf = new CrearUsuario_Modificar(vistaUsuarioL, true);
                ControladorUsuarioModf controladorMdf = new ControladorUsuarioModf(id, vistaUsuarioMdf, modelo, usuario, vistaUsuarioL);

                vistaUsuarioMdf.setVisible(true);
                System.out.println(vistaUsuarioL.tablaUsuarios.getSelectedRow());
            } else {
                System.out.println("seleccione una fila");
            }

        }

        if (e.getSource() == vistaUsuarioL.botonEliminar) {
            if (seleccion && click) {

                if (modelo.eliminar(id)) {
                    JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                    vistaUsuarioL.botonBuscar.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar");

                }
            } else {
                System.out.println("seleccione item para eliminar");
            }

        }
    }

}
