/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ConsultasPedidos;
import model.ConsultasProductos;
import model.ConsultasTerceros;
import model.ConsultasVentas;
import model.Pedido;
import model.Producto;
import model.Tercero;
import model.Usuario;
import model.consultasUsuario;
import view.CrearPedidos;
import view.CrearPedidos_listar;
import view.CrearTerceros;
import view.CrearTerceros_listar;
import view.CrearUsuario_ListarModificar;
import view.CrearUsuario_registrar;
import view.CrearVenta_listar;
import view.CrearVentas;
import view.MenuPrincipal;
import view.crearProducto;
import view.crearProducto_listar;

/**
 *
 * @author Usuario
 */
public class Controlador implements ActionListener {

   
    private Usuario usuario;
    private Tercero tercero;
    private Producto producto;
    private Pedido pedido;
    
    private MenuPrincipal menu;
    private CrearUsuario_registrar vistaUsuario;
    private CrearUsuario_ListarModificar vistaUsuarioL;
    private CrearTerceros vistaTerceros;
    private CrearTerceros_listar vistaTercerosL;
    private crearProducto vistaProducto;
    private crearProducto_listar vistaProductoL;
    private CrearPedidos vistaPedido;
    private CrearPedidos_listar vistaPedidoL;
    private CrearVentas vistaVenta;
    private CrearVenta_listar vistaVentaL;
    
    private consultasUsuario modelo;
    private ConsultasTerceros modeloTerceros;
    private ConsultasProductos modeloProductos;
    private ConsultasPedidos modeloPedidos;
    private ConsultasVentas modeloVentas;
     
    private ControladorUsuarioReg  controlUr;
    private ControladorUsuario controlU;
    private ControladorTercero controlTercero;
    private ControladorTerceroListar controlTerceroL;
    private ControladorProducto controlProducto;
    private ControladorProductoListar controlProductoL;
    private ControladorPedido controlPedido;
    private ControladorPedidoListar controlPedidoL;
    private ControladorVenta controlVentas;
    private ControladorVentaListar controlVentasL;
    
   
    public Controlador(MenuPrincipal menu, Usuario usuario, consultasUsuario modelo) {
        this.menu=menu;
        this.usuario = usuario;
        this.modelo = modelo;
        menu.botonCrearUsuario.addActionListener(this);
        menu.botonListaUsuarios.addActionListener(this);
        menu.botonSalir1.addActionListener(this);
        menu.botonCrearTerceros.addActionListener(this);
        menu.botonListaTerceros.addActionListener(this);
        menu.BotonCrearProducto.addActionListener(this);
        menu.BotonListaProducto.addActionListener(this);
        menu.botonCrearPedido.addActionListener(this);
        menu.botonListaPedido.addActionListener(this);
        menu.BotonVentas.addActionListener(this);
        menu.BotonListaVentas.addActionListener(this);
        menu.BotonSalir.addActionListener(this);
    }

    public void iniciar() {
        menu.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //usuario,cedula,celular,rol,nombre,correo,contraseña,confcontraseña
        
        
        if(e.getSource()==menu.botonCrearUsuario){
            vistaUsuario= new CrearUsuario_registrar();
            controlUr = new ControladorUsuarioReg(vistaUsuario,usuario,modelo,menu);
            controlUr.iniciar();
            menu.setVisible(false);
            
        }

        if(e.getSource()==menu.botonListaUsuarios){
           //vistaUsuario.dispose();
           vistaUsuarioL = new CrearUsuario_ListarModificar();
           controlU = new ControladorUsuario(vistaUsuarioL,usuario,modelo,menu);
           controlU.iniciar();
           menu.setVisible(false);

        }     
        
        if(e.getSource()==menu.botonCrearTerceros){
            vistaTerceros = new CrearTerceros();
            modeloTerceros= new ConsultasTerceros();
            tercero = new Tercero();
            controlTercero = new ControladorTercero(vistaTerceros,menu,modeloTerceros,tercero);
            controlTercero.iniciar();
            menu.setVisible(false);
        }
        
        if(e.getSource()==menu.botonListaTerceros){
            vistaTercerosL = new CrearTerceros_listar();
            modeloTerceros= new ConsultasTerceros();
            controlTerceroL  = new ControladorTerceroListar(vistaTercerosL,modeloTerceros,menu,tercero);
            controlTerceroL.iniciar();
            menu.setVisible(false);
           
        }
        
        
        if(e.getSource()==menu.BotonCrearProducto){
            vistaProducto = new crearProducto();
            modeloProductos = new ConsultasProductos();
            producto = new Producto();
            controlProducto= new ControladorProducto(producto,vistaProducto,modeloProductos,menu);
            controlProducto.iniciar();
            menu.setVisible(false);
            
            
        }
        
        if(e.getSource()==menu.BotonListaProducto){
            producto = new Producto();
            vistaProductoL = new crearProducto_listar();
            modeloProductos = new ConsultasProductos();
            
            controlProductoL = new ControladorProductoListar(producto,vistaProductoL,modeloProductos,menu);
            controlProductoL.iniciar();
            menu.setVisible(false);
            
        }
        
        
        
        if(e.getSource()==menu.botonCrearPedido){
            System.out.println("click en botoncrearPedido");
            vistaPedido = new CrearPedidos();
            pedido = new Pedido();
            modeloPedidos = new ConsultasPedidos();
            controlPedido = new ControladorPedido(menu, vistaPedido, modeloPedidos, pedido);
            menu.setVisible(false);
            controlPedido.iniciar();
            
            
            
        }
        
        
        if(e.getSource()==menu.botonListaPedido){
            vistaPedidoL = new CrearPedidos_listar();
            modeloPedidos = new ConsultasPedidos();

            controlPedidoL = new ControladorPedidoListar(menu,vistaPedidoL,modeloPedidos);
            menu.setVisible(false);
            controlPedidoL.iniciar();
            
        }
        
        
        if(e.getSource()==menu.BotonVentas){
            
            vistaVenta = new CrearVentas();
            modeloVentas = new ConsultasVentas();
            controlVentas = new ControladorVenta(vistaVenta, modeloVentas, menu);
            controlVentas.iniciar();
            menu.setVisible(false);
        }
        
        
        if(e.getSource()==menu.BotonListaVentas){
               vistaVentaL = new CrearVenta_listar();
               modeloVentas = new ConsultasVentas();
               controlVentasL = new ControladorVentaListar(vistaVentaL,modeloVentas,menu);
               controlVentasL.iniciar();
               menu.setVisible(false);

            
        }
        if(e.getSource()==menu.botonSalir1){
            System.exit(0);
        }
         if(e.getSource()==menu.BotonSalir){
            System.exit(0);
        }
        
    }

}//class
