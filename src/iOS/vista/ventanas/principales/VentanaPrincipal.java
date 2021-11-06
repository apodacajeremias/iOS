package iOS.vista.ventanas.principales;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
import iOS.modelo.dao.ConfiguracionDao;
import iOS.modelo.entidades.Configuracion;
import iOS.modelo.singleton.Sesion;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.PanelFondo;
import iOS.vista.ventanas.VentanaBanco;
import iOS.vista.ventanas.VentanaCliente;
import iOS.vista.ventanas.VentanaColaborador;
import iOS.vista.ventanas.VentanaConfiguracion;
import iOS.vista.ventanas.VentanaMarca;
import iOS.vista.ventanas.VentanaProducto;
import iOS.vista.ventanas.buscadores.BuscadorRol;
import iOS.vista.ventanas.pedidos.PedidoCarteleria;
import iOS.vista.ventanas.pedidos.PedidoConfeccion;
import iOS.vista.ventanas.reportes.ReporteCaja;
import iOS.vista.ventanas.reportes.ReporteDeudasPagos;
import iOS.vista.ventanas.reportes.ReportePedido;
import iOS.vista.ventanas.reportes.ReporteVales;
import iOS.vista.ventanas.transacciones.TransaccionCaja2;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4166190286945252641L;
	private JPanel contentPane;
	private LabelPersonalizado lblPODAC;


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

		JMenu mnRegistros = new JMenu("REGISTROS");
		mnRegistros.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnRegistros);

		JMenuItem mnItemBanco = new JMenuItem("Bancos");
		mnItemBanco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaBanco();
			}
		});
		mnItemBanco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemBanco);

		JMenuItem mnItemCliente = new JMenuItem("Clientes");
		mnItemCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaCliente();
			}
		});
		mnItemCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemCliente);

		JMenuItem mnItemColaborador = new JMenuItem("Colaboradores");
		mnItemColaborador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaColaborador();
			}
		});
		mnItemColaborador.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemColaborador);

		JMenuItem mnItemConfiguracion = new JMenuItem("Configuraciones");
		mnItemConfiguracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaConfiguracion();
			}
		});
		mnItemConfiguracion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemConfiguracion);

		JMenuItem mnItemMarca = new JMenuItem("Marcas");
		mnItemMarca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaMarca();
			}
		});
		mnItemMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemMarca);

		JMenuItem mnItemMaterial = new JMenuItem("Materiales");
		mnItemMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaMaterial();
			}
		});
		mnItemMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemMaterial);

		JMenuItem mnItemProducto = new JMenuItem("Productos");
		mnItemProducto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaProducto();
			}
		});
		mnItemProducto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemProducto);

		JMenuItem mnItemProveedor = new JMenuItem("Proveedores");
		mnItemProveedor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaProducto();
			}
		});
		mnItemProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemProveedor);

		JMenuItem mnItemSector = new JMenuItem("Sectores");
		mnItemSector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaSector();
			}
		});
		mnItemSector.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK));
		mnRegistros.add(mnItemSector);

		JMenu mnBuscadores = new JMenu("BUSCADORES");
		mnBuscadores.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnBuscadores);

		JMenuItem mnItemBuscadorBanco = new JMenuItem("Buscar bancos");
		mnItemBuscadorBanco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorBanco();
			}
		});
		mnItemBuscadorBanco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorBanco);

		JMenuItem mnItemBuscadorCliente = new JMenuItem("Buscar clientes");
		mnItemBuscadorCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorCliente();
			}
		});
		mnItemBuscadorCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorCliente);

		JMenuItem mnItemBuscadorColaborador = new JMenuItem("Buscar getColaborador()es");
		mnItemBuscadorColaborador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorColaborador();
			}
		});
		mnItemBuscadorColaborador.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorColaborador);

		JMenuItem mnItemBuscadorMarca = new JMenuItem("Buscar marcas");
		mnItemBuscadorMarca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorMarca();
			}
		});
		mnItemBuscadorMarca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorMarca);

		JMenuItem mnItemBuscadorMaterial = new JMenuItem("Buscar materiales");
		mnItemBuscadorMaterial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorMaterial();
			}
		});
		mnItemBuscadorMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorMaterial);

		JMenuItem mnItemBuscadorModulo = new JMenuItem("Buscar modulos");
		mnItemBuscadorModulo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorModulo();
			}
		});
		mnItemBuscadorModulo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorModulo);

		JMenuItem mnItemBuscadorOperacion = new JMenuItem("Buscar operaciones");
		mnItemBuscadorOperacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorOperacion();
			}
		});
		mnItemBuscadorOperacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorOperacion);

		JMenuItem mnItemBuscadorPedido = new JMenuItem("Buscar pedidos");
		mnItemBuscadorPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorPedido();
			}
		});
		mnItemBuscadorPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorPedido);

		JMenuItem mnItemBuscadorProducto = new JMenuItem("Buscar productos");
		mnItemBuscadorProducto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorProducto();
			}
		});
		mnItemBuscadorProducto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorProducto);

		JMenuItem mnItemBuscadorProveedor = new JMenuItem("Buscar proveedores");
		mnItemBuscadorProveedor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorProveedor();
			}
		});
		mnItemBuscadorProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorProveedor);

		JMenuItem mnItemBuscadorRol = new JMenuItem("Buscar roles");
		mnItemBuscadorRol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirBuscadorRol();
			}
		});
		mnItemBuscadorRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.SHIFT_MASK));
		mnBuscadores.add(mnItemBuscadorRol);

		JMenu mnMovimientos = new JMenu("MOVIMIENTOS");
		mnMovimientos.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnMovimientos);

		JMenuItem mnItemCaja = new JMenuItem("Caja");
		mnItemCaja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionCaja();
			}
		});
		mnItemCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemCaja);

		JMenuItem mnItemCompra = new JMenuItem("Compra");
		mnItemCompra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionCompra();
			}
		});
		mnItemCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemCompra);

		JMenuItem mnItemDeposito = new JMenuItem("Deposito");
		mnItemDeposito.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionDeposito();
			}
		});
		mnItemDeposito.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemDeposito);

		JMenuItem mnItemExistencia = new JMenuItem("Existencia");
		mnItemExistencia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionExistencia();
			}
		});
		mnItemExistencia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemExistencia);

		JMenuItem mnItemPedido = new JMenuItem("Pedido Impresion");
		mnItemPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionPedidoCarteleria();
			}
		});
		mnItemPedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemPedido);


		JMenuItem mnItemRol = new JMenuItem("Rol");
		mnItemRol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionRol();
			}
		});
		
		JMenuItem mntmPedidoCostura = new JMenuItem("Pedido Costura");
		mntmPedidoCostura.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionPedidoConfeccion();
			}
		});
		mnMovimientos.add(mntmPedidoCostura);
		mnItemRol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnMovimientos.add(mnItemRol);
		
		JMenuItem mnReportes = new JMenu("REPORTES");
		mnReportes.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnReportes);
		
		JMenuItem mnItemReporteCaja = new JMenuItem("Reporte de caja");
		mnItemReporteCaja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirReporteCaja();
			}
		});
		mnItemReporteCaja.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnReportes.add(mnItemReporteCaja);
		
		JMenuItem mnItemReportePedido = new JMenuItem("Reporte de pedidos impresiones");
		mnItemReportePedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirReportePedidoCarteleria();
			}
		});
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Reporte deudas y pagos");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReporteDeudasPagos reporte = new ReporteDeudasPagos();
				reporte.setUpControlador();
				reporte.setVisible(true);
			}
		});
		mnReportes.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Reporte vales de getColaborador()es");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReporteVales ventana = new ReporteVales();
				ventana.setUpControlador();
				ventana.setVisible(true);
			}
		});
		mnReportes.add(mntmNewMenuItem_1);
		mnItemReportePedido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK));
		mnReportes.add(mnItemReportePedido);
		
		mnItemReportePedido = new JMenuItem("Reporte de pedidos confeccion");
		mnItemReportePedido.addActionListener(new ActionListener() {
			@Override
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
		MiBoton mbtnCaja = new MiBoton("Caja");
		mbtnCaja.setFont(new Font("Tahoma", Font.BOLD, 15));
		mbtnCaja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				abrirTransaccionCaja();
			}
		});
		toolBar.add(mbtnCaja);

		MiBoton mbtnPedidoCarteleria = new MiBoton("Impresion");
		mbtnPedidoCarteleria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTransaccionPedidoCarteleria();
			}
		});
		mbtnPedidoCarteleria.setFont(new Font("Tahoma", Font.BOLD, 15));
		toolBar.add(mbtnPedidoCarteleria);
		
		MiBoton mbtnPedidoConfeccion = new MiBoton("Confeccion");
		mbtnPedidoConfeccion.addActionListener(new ActionListener() {
			@Override
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
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}

	}
	
	private void abrirReportePedidoCarteleria() {
		ReportePedido ventana = new ReportePedido();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControladorCarteleria();
			ventana.setVisible(true);
		}
	}
	
	private void abrirReportePedidoConfeccion() {
		ReportePedido ventana = new ReportePedido();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControladorConfeccion();
			ventana.setVisible(true);
		}
	}

	private void abrirTransaccionCaja() {
		TransaccionCaja2 ventana = new TransaccionCaja2();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "MODIFICAR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}

	private void abrirTransaccionPedidoCarteleria() {
		PedidoCarteleria ventana = new PedidoCarteleria();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}
	private void abrirTransaccionPedidoConfeccion() {
		PedidoConfeccion ventana = new PedidoConfeccion();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
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
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")){
			ventana.setUpControlador();
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
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaBanco() {
		VentanaBanco ventana = new VentanaBanco();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaCliente() {
		VentanaCliente ventana = new VentanaCliente();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaProducto() {
		VentanaProducto ventana = new VentanaProducto();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}

	private void abrirVentanaConfiguracion() {
		VentanaConfiguracion ventana = new VentanaConfiguracion();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.setVisible(true);
		}
	}
	
	private void abrirVentanaMarca() {
		VentanaMarca ventana = new VentanaMarca();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public JPanel getContentPane() {
		return contentPane;
	}

	public LabelPersonalizado getLblPODAC() {
		return lblPODAC;
	}
}