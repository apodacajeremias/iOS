package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Maquina;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalleConfeccion;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Produccion;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.modelotabla.ModeloTablaProduccion;
import iOS.vista.ventanas.transacciones.TransaccionProduccion;

public class TransaccionProduccionControlador
		implements ActionListener, MouseListener, KeyListener, PedidoInterface, PropertyChangeListener {
	private TransaccionProduccion ventana;

	private PedidoDao dao;

	private Pedido pedido;
	private PedidoDetalles pedidoDetalle;
	private List<PedidoDetalles> pedidosDetalles = new ArrayList<PedidoDetalles>();
	private Produccion produccion;
	private List<Produccion> producciones = new ArrayList<Produccion>();

	private ModeloTablaPedidoDetalle modeloTablaPedidoDetalle;
	private ModeloTablaProduccion modeloTablaProduccion;

	public TransaccionProduccionControlador(TransaccionProduccion ventana) {
		this.ventana = ventana;

		dao = new PedidoDao();

		modeloTablaPedidoDetalle = new ModeloTablaPedidoDetalle();
		ventana.getTablePedidoDetalle().setModel(modeloTablaPedidoDetalle);
		tableMenu(ventana.getTablePedidoDetalle());
		modeloTablaProduccion = new ModeloTablaProduccion();
		ventana.getTableSeguimientoProduccion().setModel(modeloTablaProduccion);

		setUpEvents();
		estadoInicial();
	}

	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPedido());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlCliente());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlPedido1());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlPedido2());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlPedido3());

		vaciarTablas();

	}

	private void setUpEvents() {
		ventana.getBtnBuscar().addActionListener(this);
		ventana.getTablePedidoDetalle().addMouseListener(this);
		ventana.getTableSeguimientoProduccion().addMouseListener(this);
	}

	private void vaciarTablas() {
		pedidosDetalles = new ArrayList<PedidoDetalles>();
		modeloTablaPedidoDetalle.setDetalle(pedidosDetalles);
		modeloTablaPedidoDetalle.fireTableDataChanged();

		producciones = new ArrayList<Produccion>();
		modeloTablaProduccion.setProducciones(producciones);
		modeloTablaProduccion.fireTableDataChanged();

	}

	private void encontrarPedido() {
		if (ventana.gettPedido().getText().isEmpty()) {
			return;
		}
		Integer id = Integer.parseInt(ventana.gettPedido().getText());
		try {
			estadoInicial();
			pedido = dao.encontrarPedido(id);
			if (pedido.isEsPresupuesto() || !pedido.isEstado()) {
				JOptionPane.showMessageDialog(ventana, "El pedido es un presupuesto o está anulado, verifique...");
				return;
			}
			setPedido(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void seleccionarRegistro(Integer posicion) {
		if (posicion < 0) {
			producciones = null;
			pedidoDetalle = null;
			pedido = null;
			return;
		}
		pedidoDetalle = pedidosDetalles.get(posicion);
		pedido = pedidoDetalle.getPedido();

		cargarDetalleProduccion(pedidoDetalle);
	}

	private void cargarDetalleProduccion(Object detalle) {
		if (detalle instanceof PedidoDetalles) {
			PedidoDetalles pd = (PedidoDetalles) detalle;
			try {
				producciones = pd.getProducciones();
				modeloTablaProduccion.setProducciones(producciones);
				modeloTablaProduccion.fireTableDataChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void verificarEstadoProduccionDetalle(Object detalle) {
		try {
			if (detalle instanceof PedidoDetalles) {
				PedidoDetalles pd = (PedidoDetalles) detalle;
				List<Produccion> pr = pd.getProducciones().stream().sorted(Comparator.comparing(Produccion::getId).reversed()).collect(Collectors.toList());
				System.out.println(pr.stream().findFirst().get().getProceso());
			}

			if (detalle instanceof PedidoDetalleConfeccion) {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Maquina pedirMaquina() {
		List<Maquina> maquinas = Sesion.getInstance().getMaquinas();
		Object[] possibilities = new Object[maquinas.size()];

		for (int i = 0; i < possibilities.length; i++) {
			possibilities[i] = maquinas.get(i);
		}

		Maquina maquina = (Maquina) JOptionPane.showInputDialog(ventana,
				"Seleccione la maquina que utilizará:\n" + "\"Indique;\"", "Maquina utilizada en el proceso",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "");

		// If a string was returned, say so.
		if (maquina != null) {
			System.out.println();
		}
		return maquina;
	}

	private boolean finalizarProduccionPedido(Pedido p) {

		boolean generaDeuda = p.getPedidoDetalles().stream().filter(pd -> pd.isEstado() == true)
				.allMatch(x -> x.isProduccionFinalizada() == true);
		return generaDeuda;
	}

	private void iniciarProduccion() {
		produccion = new Produccion();
		produccion.setCantidadDesperdicio(0);
		produccion.setColaborador(Sesion.getInstance().getColaborador());
		produccion.setComentario("N/A");
		produccion.setDesperdicio(false);
		produccion.setMaquina(pedirMaquina());
		produccion.setPedidoDetalle(pedidoDetalle);
		produccion.setPedidoDetalleConfeccion(null);
		produccion.setProceso("INICIAR");
		produccion.setSector(Sesion.getInstance().getSector());

		producciones.add(produccion);

		pedidoDetalle.setProducciones(producciones);

		guardar();
	}

	private void reiniciarProduccion() {
		desperdiciarProduccion();

		produccion = new Produccion();
		produccion.setCantidadDesperdicio(0);
		produccion.setColaborador(Sesion.getInstance().getColaborador());
		produccion.setComentario("N/A");
		produccion.setDesperdicio(false);
		produccion.setMaquina(pedirMaquina());
		produccion.setPedidoDetalle(pedidoDetalle);
		produccion.setPedidoDetalleConfeccion(null);
		produccion.setProceso("REINICIAR");
		produccion.setSector(Sesion.getInstance().getSector());

		producciones.add(produccion);

		pedidoDetalle.setProducciones(producciones);

		guardar();
	}

	private void finalizarProduccion() {
		produccion = new Produccion();
		produccion.setCantidadDesperdicio(0);
		produccion.setColaborador(Sesion.getInstance().getColaborador());
		produccion.setComentario("N/A");
		produccion.setDesperdicio(false);
		produccion.setMaquina(pedirMaquina());
		produccion.setPedidoDetalle(pedidoDetalle);
		produccion.setPedidoDetalleConfeccion(null);
		produccion.setProceso("FINALIZAR");
		produccion.setSector(Sesion.getInstance().getSector());

		producciones.add(produccion);

		pedidoDetalle.setProducciones(producciones);

		pedidoDetalle.setProduccionFinalizada(true);

		pedido.setGeneraDeuda(finalizarProduccionPedido(pedido));

		guardar();
	}

	private void desperdiciarProduccion() {
		produccion = new Produccion();
		produccion.setCantidadDesperdicio(pedidoDetalle.getMedidaDetalle());
		produccion.setColaborador(Sesion.getInstance().getColaborador());
		produccion.setComentario("N/A");
		produccion.setDesperdicio(true);
		produccion.setMaquina(null);
		produccion.setPedidoDetalle(pedidoDetalle);
		produccion.setPedidoDetalleConfeccion(null);
		produccion.setProceso("DESPERDICIAR");
		produccion.setSector(Sesion.getInstance().getSector());

		producciones.add(produccion);

		pedidoDetalle.setProducciones(producciones);

		guardar();
	}

//	private void cambiarEstado() {
//		if (procesoRepetido(proceso)) {
//			return;
//		}
//
//		produccion = new Produccion();
//		produccion.setCantidadDesperdicio(0d);
//		produccion.setColaborador(Sesion.getInstance().getColaborador());
//		produccion.setDesperdicio(false);
//		produccion.setMaquina(null);
//		produccion.setObservacion("Sin observaciones.");
//		produccion.setPedidoDetalle(pedidoDetalle);
//		produccion.setSector(Sesion.getInstance().getColaborador().getSector());
//		produccion.setTipoTrabajo("Sin definicion de trabajo");
//		producciones.add(produccion);
//		guardar();
//		modeloTablaProduccion.setProducciones(producciones);
//		modeloTablaProduccion.fireTableDataChanged();
//	}

//	private void solicitarProceso() {
//		List<SectorProceso> procesos = Sesion.getInstance().getColaborador().getSector().getProcesos();
//		Object[] possibilities = new Object[procesos.size()];
//
//		for (int i = 0; i < possibilities.length; i++) {
//			possibilities[i] = procesos.get(i);
//		}
//
//		SectorProceso proceso = (SectorProceso) JOptionPane.showInputDialog(ventana,
//				"Seleccione el proceso:\n" + "\"Indique;\"", "Estado de seguimiento", JOptionPane.PLAIN_MESSAGE, null,
//				possibilities, "");
//
//		// If a string was returned, say so.
//		if (proceso != null) {
//			System.out.println(proceso.getId() + " " + proceso.getNombreProceso());
//		}
//		setProceso(proceso);
//	}

	private void guardar() {
		try {
			dao.modificar(pedido);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			dao.rollBack();
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
		gestionarPedido();
	}

	private void gestionarPedido() {
		if (pedido == null) {
			return;
		}
		vaciarTablas();

		ventana.getlPedido1()
				.setText("PEDIDO " + pedido.getId() + " " + EventosUtil.formatoFecha(pedido.getFechaRegistro()));
		ventana.getlPedido2()
				.setText("PEDIDO " + pedido.getPedidoCarteleria() != null && pedido.getPedidoCarteleria() == true
						? "CARTELERIA"
						: "CONFECCION".concat(pedido.isEstado() ? " VIGENTE" : " ANULADO"));
		ventana.getlPedido3()
				.setText("METRAJE TOTAL DEL PEDIDO " + EventosUtil.separadorDecimales(pedido.getMetrosTotal()));
		ventana.getlCliente().setText(pedido.getCliente().toString());
		ventana.getlColaborador().setText(pedido.getColaborador().toString());

		pedidosDetalles = pedido.getPedidoDetalles();
		modeloTablaPedidoDetalle.setDetalle(pedidosDetalles);
		modeloTablaPedidoDetalle.fireTableDataChanged();

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventana.getTablePedidoDetalle()) {
			seleccionarRegistro(ventana.getTablePedidoDetalle().getSelectedRow());
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == ventana.getTablePedidoDetalle()) {
			seleccionarRegistro(ventana.getTablePedidoDetalle().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == ventana.getTablePedidoDetalle()) {
			seleccionarRegistro(ventana.getTablePedidoDetalle().getSelectedRow());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Buscar":
			encontrarPedido();
			break;
		case "Seguimiento":
//			solicitarProceso();
			break;
		default:
			break;
		}

	}

	private void tableMenu(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopup(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopup(final JTable table, final int row) {
		seleccionarRegistro(row);
		verificarEstadoProduccionDetalle(pedidoDetalle);
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem item = new JMenuItem("\u25B6 Iniciar");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarProduccion();

			}
		});
		JMenuItem item2 = new JMenuItem("\uD83D\uDD01 Reiniciar");
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reiniciarProduccion();

			}
		});
		JMenuItem item3 = new JMenuItem("\u23F9 Desperdiciar");
		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desperdiciarProduccion();

			}
		});
		JMenuItem item4 = new JMenuItem("\u23F8 Finalizar");
		item4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizarProduccion();

			}
		});

		popup.add(item);
		popup.add(item2);
		popup.add(item3);
		popup.add(item4);
		return popup;
	}
}
