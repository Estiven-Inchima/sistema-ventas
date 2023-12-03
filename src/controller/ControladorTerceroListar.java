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
import model.ConsultasTerceros;
import model.Tercero;
import view.CrearTerceros_Modificar;
import view.CrearTerceros_listar;
import view.MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class ControladorTerceroListar implements ActionListener {

    private CrearTerceros_listar vistaTercerosL;
    private ConsultasTerceros modeloTerceros;
    private MenuPrincipal menu;
    private Tercero tercero;
    private int id;
    private boolean seleccion = false,click = false;
    private ControladorTerceroModf controlTerceroMdf;
    private CrearTerceros_Modificar vistaTerceroMdf;

    public ControladorTerceroListar(CrearTerceros_listar vistaTercerosL, ConsultasTerceros modeloTerceros, MenuPrincipal menu, Tercero tercero) {
        this.vistaTercerosL = vistaTercerosL;
        this.modeloTerceros = modeloTerceros;
        this.menu = menu;
        this.tercero=tercero;
        vistaTercerosL.grupoRadioBotones.add(vistaTercerosL.radioBotonCedulaNit);
        vistaTercerosL.grupoRadioBotones.add(vistaTercerosL.radioBotonNombre);
        vistaTercerosL.botonMenu.addActionListener(this);
        vistaTercerosL.radioBotonNombre.setSelected(true);
        vistaTercerosL.botonBuscar.addActionListener(this);
        vistaTercerosL.botonModificar.addActionListener(this);
        vistaTercerosL.botonEliminar.addActionListener(this);
        eventoOyenteDeRaton();
    }

    public void iniciar() {
        vistaTercerosL.setVisible(true);
        vistaTercerosL.setLocationRelativeTo(null);
        vistaTercerosL.setTitle("Lista Terceros");
    }

    public void eventoOyenteDeRaton(){
        MouseListener oyenteDeRaton = new MouseListener(){
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("click");
                int fila = vistaTercerosL.tablaTerceros.getSelectedRow();
                String usuario = vistaTercerosL.tablaTerceros.getValueAt(fila, 0).toString();
                String consulta = "select * from tercero where cedulaNit = '"+usuario+"'";
                if(modeloTerceros.datos(consulta, vistaTercerosL)){
                    System.out.println("datos");
                    click = true;
                    id=Integer.parseInt(vistaTercerosL.cajaID.getText());
                }else{
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
        vistaTercerosL.tablaTerceros.addMouseListener(oyenteDeRaton);
    
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaTercerosL.botonMenu) {
            vistaTercerosL.dispose();
            menu.setVisible(true);
        }

        if (e.getSource() == vistaTercerosL.botonBuscar) {
            vistaTercerosL.cajaTexto.setText(null);
            DefaultTableModel modeloTabla = new DefaultTableModel();
            vistaTercerosL.tablaTerceros.setModel(modeloTabla);
            String campo = vistaTercerosL.cajaBuscar.getText();
            String opcion = "";
            if (!campo.equals("")) {

                if (campo.equals("*")) {
                    campo = opcion = "";
                } else {
                    if (vistaTercerosL.radioBotonCedulaNit.isSelected() == true) {
                        opcion = "where cedulaNit like '" + campo + "%'";
                    } else if (vistaTercerosL.radioBotonNombre.isSelected() == true) {
                        opcion = "where nombre like '%" + campo + "%'";
                    }

                }
                if(modeloTerceros.Buscar(modeloTabla, vistaTercerosL, opcion)){
                System.out.println("Tercero");
                seleccion = true;
                }else{
                 JOptionPane.showMessageDialog(null,"Tercero no encontrado");
                 seleccion = false;
                 click=false;
                }

            } else {
                JOptionPane.showMessageDialog(null,"Por favor llene el campo buscar");
            }
        }

        
        if(e.getSource()==vistaTercerosL.botonModificar){
            
            if(seleccion && click){
                System.out.println("FUNCIONA");
                vistaTerceroMdf = new CrearTerceros_Modificar(vistaTercerosL,true);
                tercero = new Tercero();
                controlTerceroMdf = new ControladorTerceroModf(id,vistaTerceroMdf,modeloTerceros,tercero,vistaTercerosL); 
                
                vistaTerceroMdf.setVisible(true);
            }else{
                System.out.println("Seleccione una fila");
            }
            
            
            
        }
        
        if(e.getSource()==vistaTercerosL.botonEliminar){
            if(seleccion && click){
                if(modeloTerceros.eliminar(id)){
                    JOptionPane.showMessageDialog(null, "Tercero Eliminado");
                    vistaTercerosL.botonBuscar.doClick();
                    vistaTercerosL.cajaID.setText(null);
                }else{
                    JOptionPane.showMessageDialog(null, "error al eliminar");
                }
            }else{
                System.out.println("seleecione item para eliminar");
            }
        }
        
        
    }

}
