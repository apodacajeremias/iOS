package iOS.vista.ventanas.principales;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.ConfiguracionDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Configuracion;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.PanelFondo;
import iOS.vista.ventanas.VentanaBanco;
import iOS.vista.ventanas.VentanaCajaApertura;
import iOS.vista.ventanas.VentanaCliente;
import iOS.vista.ventanas.VentanaColaborador;
import iOS.vista.ventanas.VentanaConfiguracion;
import iOS.vista.ventanas.VentanaMarca;
import iOS.vista.ventanas.VentanaProducto;
import iOS.vista.ventanas.buscadores.BuscadorRol;
import iOS.vista.ventanas.pedidos.PedidoCarteleria;
import iOS.vista.ventanas.pedidos.PedidoConfeccion;
import iOS.vista.ventanas.reportes.ReporteCaja;
import iOS.vista.ventanas.reportes.ReportePedido;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class VentanaPrincipal extends JFrame implements ColaboradorInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4166190286945252641L;
	private JPanel contentPane;
	private Colaborador colaborador;
	private LabelPersonalizado lblPODAC;

	private CajaDao dao = new CajaDao();
	private Caja caja;
	private JMenu mnRegistros;
	private JMenuItem mnItemCliente;
	private JMenuItem mnItemProducto;
	private JMenuItem mnItemBanco;
	private JMenuItem mnItemMaterial;
	private JMenuItem mnItemSector;
	private JMenuItem mnItemColaborador;
	private JMenuItem mnItemConfiguracion;
	private JMenuItem mnItemMarca;
	private JMenuItem mnItemProveedor;
	private JMenu mnMovimientos;
	private JMenuItem mnItemCaja;
	private JMenuItem mnItemCompra;
	private JMenuItem mnItemDeposito;
	private JMenuItem mnItemExistencia;
	private JMenuItem mnItemPedido;
	private JMenuItem mnItemRol;
	private JMenu mnBuscadores;
	private JMenuItem mnItemBuscadorBanco;
	private JMenuItem mnItemBuscadorCliente;
	private JMenuItem mnItemBuscadorColaborador;
	private JMenuItem mnItemBuscadorMarca;
	private JMenuItem mnItemBuscadorMaterial;

	private MiBoton mbtnCaja;
	private MiBoton mbtnPedidoCarteleria;
	private JMenuItem mnItemBuscadorModulo;
	private JMenuItem mnItemBuscadorOperacion;
	private JMenuItem mnItemBuscadorPedido;
	private JMenuItem mnItemBuscadorProducto;
	private JMenuItem mnItemBuscadorProveedor;
	private JMenuItem mnItemBuscadorRol;
	private JMenu mnReportes;
	private JMenuItem mnItemReporteCaja;
	private JMenuItem mnItemReportePedido;
	private JMenuItem mntmPedidoCostura;
	private MiBoton mbtnPedidoConfeccion;


	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("iOS Comunicación Visual - PODAC Sistemas Informaticos");
		// centramos la ventana
		setLocationRelativeTo(this);
		// maximizamos la ventana
		setExtendedState(Frame.MAXIMIZED_BOTH);

		setIconImage(new ImageIcon(getClass().getResource("/img/LOGO PODAC.png")).getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnRegistros = new JMenu("REGISTROS");
		mnRegistros.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnRegistros);

		mnItemBanco = new JMenuItem("Bancos");
		mnItemBanco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaBanco();
			}
		});
		mnItemBanco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemBanco);

		mnItemCliente = new JMenuItem("Clientes");
		mnItemCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaCliente();
			}
		});
		mnItemCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemCliente);

		mnItemColaborador = new JMenuItem("Colaboradores");
		mnItemColaborador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaColaborador();
			}
		});
		mnItemColaborador.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemColaborador);

		mnItemConfiguracion = new JMenuItem("Configuraciones");
		mnItemConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaConfiguracion();
			}
		});
		mnItemConfiguracion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemConfiguracion);

		mnItemMarca = new JMenuItem("Marcas");
		mnItemMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaMarca();
			}
		});
		mnItemMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemMarca);

		mnItemMaterial = new JMenuItem("Materiales");
		mnItemMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaMaterial();
			}
		});
		mnItemMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemMaterial);

		mnItemProducto = new JMenuItem("Productos");
		mnItemProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaProducto();
			}
		});
		mnItemProducto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemProducto);

		mnItemProveedor = new JMenuItem("Proveedores");
		mnItemProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaProducto();
			}
		});
		mnItemProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemProveedor);

		mnItemSector = new JMenuItem("Sectores");
		mnItemSector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaSector();
			}
		});
		mnItemSector.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemSector);

		mnBuscadores = new JMenu("BUSCADORES");
		mnBuscadores.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnBuscadores);

		mnItemBuscadorBanco = new JMenuItem("Buscar bancos");
		mnItemBuscadorBanco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorBanco();
			}
		});
		mnItemBuscadorBanco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorBanco);

		mnItemBuscadorCliente = new JMenuItem("Buscar clientes");
		mnItemBuscadorCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorCliente();
			}
		});
		mnItemBuscadorCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorCliente);

		mnItemBuscadorColaborador = new JMenuItem("Buscar colaboradores");
		mnItemBuscadorColaborador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorColaborador();
			}
		});
		mnItemBuscadorColaborador.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorColaborador);

		mnItemBuscadorMarca = new JMenuItem("Buscar marcas");
		mnItemBuscadorMarca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorMarca();
			}
		});
		mnItemBuscadorMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorMarca);

		mnItemBuscadorMaterial = new JMenuItem("Buscar materiales");
		mnItemBuscadorMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorMaterial();
			}
		});
		mnItemBuscadorMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorMaterial);

		mnItemBuscadorModulo = new JMenuItem("Buscar modulos");
		mnItemBuscadorModulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorModulo();
			}
		});
		mnItemBuscadorModulo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorModulo);

		mnItemBuscadorOperacion = new JMenuItem("Buscar operaciones");
		mnItemBuscadorOperacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorOperacion();
			}
		});
		mnItemBuscadorOperacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorOperacion);

		mnItemBuscadorPedido = new JMenuItem("Buscar pedidos");
		mnItemBuscadorPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorPedido();
			}
		});
		mnItemBuscadorPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorPedido);

		mnItemBuscadorProducto = new JMenuItem("Buscar productos");
		mnItemBuscadorProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorProducto();
			}
		});
		mnItemBuscadorProducto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorProducto);

		mnItemBuscadorProveedor = new JMenuItem("Buscar proveedores");
		mnItemBuscadorProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorProveedor();
			}
		});
		mnItemBuscadorProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorProveedor);

		mnItemBuscadorRol = new JMenuItem("Buscar roles");
		mnItemBuscadorRol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorRol();
			}
		});
		mnItemBuscadorRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorRol);

		mnMovimientos = new JMenu("MOVIMIENTOS");
		mnMovimientos.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnMovimientos);

		mnItemCaja = new JMenuItem("Caja");
		mnItemCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cajaAbierta()) {
					abrirTransaccionCaja();
				}
				if (!cajaAbierta()) {
					abrirVentanaCajaApertura();
				}
			}
		});
		mnItemCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemCaja);

		mnItemCompra = new JMenuItem("Compra");
		mnItemCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionCompra();
			}
		});
		mnItemCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemCompra);

		mnItemDeposito = new JMenuItem("Deposito");
		mnItemDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionDeposito();
			}
		});
		mnItemDeposito.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemDeposito);

		mnItemExistencia = new JMenuItem("Existencia");
		mnItemExistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionExistencia();
			}
		});
		mnItemExistencia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemExistencia);

		mnItemPedido = new JMenuItem("Pedido Impresion");
		mnItemPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionPedidoCarteleria();
			}
		});
		mnItemPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemPedido);


		mnItemRol = new JMenuItem("Rol");
		mnItemRol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionRol();
			}
		});
		
		mntmPedidoCostura = new JMenuItem("Pedido Costura");
		mntmPedidoCostura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionPedidoConfeccion();
			}
		});
		mnMovimientos.add(mntmPedidoCostura);
		mnItemRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemRol);
		
		mnReportes = new JMenu("REPORTES");
		mnReportes.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnReportes);
		
		mnItemReporteCaja = new JMenuItem("Reporte de caja");
		mnItemReporteCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirReporteCaja();
			}
		});
		mnItemReporteCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnReportes.add(mnItemReporteCaja);
		
		mnItemReportePedido = new JMenuItem("Reporte de pedidos impresiones");
		mnItemReportePedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirReportePedidoCarteleria();
			}
		});
		mnItemReportePedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnReportes.add(mnItemReportePedido);
		
		mnItemReportePedido = new JMenuItem("Reporte de pedidos confeccion");
		mnItemReportePedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirReportePedidoConfeccion();
			}
		});
		mnItemReportePedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnReportes.add(mnItemReportePedido);

		/*
		 * Se obtiene nustro toolbar para los botones de paciente renta vehiculo y salir
		 */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		// boton para mostrar ventana paciente paciente
		mbtnCaja = new MiBoton("Caja");
		mbtnCaja.setFont(new Font("Tahoma", Font.BOLD, 15));
		mbtnCaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cajaAbierta()) {
					abrirTransaccionCaja();
				}
				if (!cajaAbierta()) {
					abrirVentanaCajaApertura();
				}
			}
		});
		toolBar.add(mbtnCaja);

		mbtnPedidoCarteleria = new MiBoton("Impresion");
		mbtnPedidoCarteleria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTransaccionPedidoCarteleria();
			}
		});
		mbtnPedidoCarteleria.setFont(new Font("Tahoma", Font.BOLD, 15));
		toolBar.add(mbtnPedidoCarteleria);
		
		mbtnPedidoConfeccion = new MiBoton("Confeccion");
		mbtnPedidoConfeccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTransaccionPedidoConfeccion();
			}
		});
		mbtnPedidoConfeccion.setFont(new Font("Tahoma", Font.BOLD, 15));
		toolBar.add(mbtnPedidoConfeccion);
		

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 255));
		toolBar.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		LabelPersonalizado lblProgramadores = new LabelPersonalizado(15);
		lblProgramadores.setText("Sesi\u00F3n de");
		lblProgramadores.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProgramadores.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblProgramadores, BorderLayout.NORTH);

		lblPODAC = new LabelPersonalizado(15);
		//		lblPODAC.setText("@PODAC.SDG");
		lblPODAC.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblPODAC, BorderLayout.SOUTH);

		PanelFondo panelFondo = new PanelFondo("Fondo.jpg");
		contentPane.add(panelFondo, BorderLayout.CENTER);
		panelFondo.setLayout(null);

		CampoTextoPersonalizado lblVersion = new CampoTextoPersonalizado();
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVersion.setText("Version "+serialVersionUID);
		lblVersion.setForeground(new Color(0, 0, 0));
		lblVersion.setBounds(10, 563, 200, 15);
		panelFondo.add(lblVersion);
	}

	protected void abrirReporteCaja() {
		ReporteCaja ventana = new ReporteCaja();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}

	}
	
	private void abrirReportePedidoCarteleria() {
		ReportePedido ventana = new ReportePedido();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControladorCarteleria();
			ventana.getControladorCarteleria().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}
	
	private void abrirReportePedidoConfeccion() {
		ReportePedido ventana = new ReportePedido();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControladorConfeccion();
			ventana.getControladoConfeccion().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirTransaccionCaja() {
		TransaccionCaja ventana = new TransaccionCaja();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "MODIFICAR")) {
			ventana.setUpControlador();
			ventana.getControlador().setCaja(caja);
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirTransaccionPedidoCarteleria() {
		PedidoCarteleria ventana = new PedidoCarteleria();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}
	private void abrirTransaccionPedidoConfeccion() {
		PedidoConfeccion ventana = new PedidoConfeccion();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}


	protected void abrirTransaccionRol() {
		// TODO Auto-generated method stub

	}

	protected void abrirTransaccionExistencia() {
		// TODO Auto-generated method stub

	}

	protected void abrirTransaccionDeposito() {
		// TODO Auto-generated method stub

	}

	protected void abrirTransaccionCompra() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorRol() {
		BuscadorRol ventana = new BuscadorRol();
		if (EventosUtil.liberarAccesoSegunRol(colaborador, "ADMINISTRADOR")){
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	protected void abrirBuscadorProveedor() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorProducto() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorPedido() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorOperacion() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorModulo() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorMaterial() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorMarca() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorColaborador() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorCliente() {
		// TODO Auto-generated method stub

	}

	protected void abrirBuscadorBanco() {
		// TODO Auto-generated method stub

	}

	protected void abrirVentanaSector() {
		// TODO Auto-generated method stub

	}

	protected void abrirVentanaMaterial() {
		// TODO Auto-generated method stub

	}

	protected void abrirVentanaColaborador() {
		VentanaColaborador ventana = new VentanaColaborador();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaBanco() {
		VentanaBanco ventana = new VentanaBanco();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaCliente() {
		VentanaCliente ventana = new VentanaCliente();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaProducto() {
		VentanaProducto ventana = new VentanaProducto();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaCajaApertura() {
		VentanaCajaApertura ventana = new VentanaCajaApertura();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setCaja(caja);
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaConfiguracion() {
		VentanaConfiguracion ventana = new VentanaConfiguracion();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}
	
	private void abrirVentanaMarca() {
		VentanaMarca ventana = new VentanaMarca();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		}
	}

	public void recuperarConfiguracion() {
		ConfiguracionDao dao = new ConfiguracionDao();
		Configuracion configuracion = dao.recuperarPorId(1);
		if (configuracion == null) {
			abrirVentanaConfiguracion();
		}
	}

	public boolean cajaAbierta() {
		Date date = new Date();
		long d = date.getTime();
		java.sql.Date fecha = new java.sql.Date(d);

		caja = dao.encontrarCajaHoy(fecha, colaborador.getId());
		if (caja == null) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	public void gestionarColaborador() {
		if(colaborador == null) {
			return;
		}
		if (colaborador != null) {
			lblPODAC.setText(colaborador.toString());
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}
}