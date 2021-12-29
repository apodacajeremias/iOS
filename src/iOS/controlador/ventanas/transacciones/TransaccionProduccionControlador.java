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
import java.util.Date;
import java.util.List;

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
import iOS.vista.modelotabla.ModeloTablaPedidoConfeccionDetalle;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.modelotabla.ModeloTablaProduccion;
import iOS.vista.ventanas.transacciones.TransaccionProduccion;

public class TransaccionProduccionControlador
		implements ActionListener, MouseListener, KeyListener, PedidoInterface, PropertyChangeListener {
	private TransaccionProduccion ventana;

	private PedidoDao dao;

	private Pedido pedido;
	private PedidoDetalles detalleCarteleria;
	private List<PedidoDetalles> detallesCarteleria = new ArrayList<PedidoDetalles>();

	private PedidoDetalleConfeccion detalleConfeccion;
	private List<PedidoDetalleConfeccion> detallesConfeccion = new ArrayList<PedidoDetalleConfeccion>();

	private Produccion produccion;
	private List<Produccion> producciones = new ArrayList<Produccion>();

	private ModeloTablaPedidoDetalle modeloTablaPedidoDetalle;
	private ModeloTablaPedidoConfeccionDetalle modeloTablaPedidoConfeccionDetalle;
	private ModeloTablaProduccion modeloTablaProduccion;

	public TransaccionProduccionControlador(TransaccionProduccion ventana) {
		this.ventana = ventana;

		dao = new PedidoDao();

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
		try {
			detallesCarteleria = new ArrayList<PedidoDetalles>();
			modeloTablaPedidoDetalle.setDetalle(detallesCarteleria);
			modeloTablaPedidoDetalle.fireTableDataChanged();

			detallesConfeccion = new ArrayList<PedidoDetalleConfeccion>();
			modeloTablaPedidoConfeccionDetalle.setDetalle(detallesConfeccion);
			modeloTablaPedidoConfeccionDetalle.fireTableDataChanged();

			producciones = new ArrayList<Produccion>();
			modeloTablaProduccion.setProducciones(producciones);
			modeloTablaProduccion.fireTableDataChanged();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void encontrarPedido() {
		if (ventana.gettPedido().getText().isEmpty()) {
			return;
		}
		Integer id = Integer.parseInt(ventana.gettPedido().getText());
		try {
			estadoInicial();
			pedido = dao.encontrarPedido(id);

			if (pedido == null) {
				JOptionPane.showMessageDialog(ventana, "No se ha encontrado ningun pedido.");
			} else if (pedido.isEsPresupuesto() || !pedido.isEstado()) {
				JOptionPane.showMessageDialog(ventana, "El pedido es un presupuesto o está anulado, verifique...");
				return;
			}

			if (pedido.getPedidoCarteleria()) {
				modeloTablaPedidoDetalle = new ModeloTablaPedidoDetalle();
				ventana.getTablePedidoDetalle().setModel(modeloTablaPedidoDetalle);
			}
			if (pedido.getPedidoCostura()) {
				modeloTablaPedidoConfeccionDetalle = new ModeloTablaPedidoConfeccionDetalle();
				ventana.getTablePedidoDetalle().setModel(modeloTablaPedidoConfeccionDetalle);
			}
			setPedido(pedido);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	private void seleccionarRegistro(Integer posicion) {
		if (posicion < 0) {
			producciones = null;
			produccion = null;
			detalleCarteleria = null;
			detalleConfeccion = null;
			return;
		}
		if (pedido.getPedidoCarteleria()) {
			detalleCarteleria = detallesCarteleria.get(posicion);
			cargarDetalleProduccion(detalleCarteleria);
		}
		if (pedido.getPedidoCostura()) {
			detalleConfeccion = detallesConfeccion.get(posicion);
			cargarDetalleProduccion(detalleConfeccion);

		}
	}

	private void cargarDetalleProduccion(Object detalle) {
		if (detalle instanceof PedidoDetalles) {
			PedidoDetalles pd = (PedidoDetalles) detalle;
			try {
				producciones = pd.getProducciones();
//				producciones = producciones.stream().sorted(Comparator.comparing(Produccion::getId))
//						.collect(Collectors.toList());
				modeloTablaProduccion.setProducciones(producciones);
				modeloTablaProduccion.fireTableDataChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (detalle instanceof PedidoDetalleConfeccion) {
			PedidoDetalleConfeccion pd = (PedidoDetalleConfeccion) detalle;
			try {
				producciones = pd.getProducciones();
//				producciones = producciones.stream().sorted(Comparator.comparing(Produccion::getId))
//						.collect(Collectors.toList());
				modeloTablaProduccion.setProducciones(producciones);
				modeloTablaProduccion.fireTableDataChanged();

//				String infoString = "PEDIDO "+pd.getPedido().getId()
//						+"DETALLE "+pd.getId()
//						+"PRODUCCIONES "+producciones.size();
//				
//				JOptionPane.showMessageDialog(ventana, infoString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Maquina pedirMaquina() {
		List<Maquina> maquinas = new ArrayList<Maquina>();
		maquinas.add(null);
		maquinas.addAll(Sesion.getInstance().getMaquinas());
		Object[] possibilities = new Object[maquinas.size()];

		for (int i = 0; i < possibilities.length; i++) {
			possibilities[i] = maquinas.get(i);
		}

		Maquina maquina = (Maquina) JOptionPane.showInputDialog(ventana,
				"Seleccione la maquina que utilizará:\n" + "\"Indique;\"", "Maquina utilizada en el proceso",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "");

		if (maquina != null) {
			JOptionPane.showMessageDialog(ventana, maquina);
		}
		return maquina;
	}

	private boolean verificarDetallesParaFinalizar(Pedido p) {
		boolean generaDeuda = p.getPedidoDetalles().stream().filter(pd -> pd.isEstado() == true)
				.allMatch(x -> x.isProduccionFinalizada() == true);
		return generaDeuda;
	}

	// SOLICITAR CONFIRMACION PARA REALIZAR LOS PROCESO
	private void iniciarProduccion() {
		int acepta = JOptionPane.showConfirmDialog(null, "Iniciar la producción de este item?", "INICIAR PRODUCCION",
				JOptionPane.YES_NO_OPTION);
		if (acepta == JOptionPane.YES_OPTION) {
			if (pedido.getPedidoCarteleria()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(pedirMaquina());
				produccion.setPedidoDetalle(detalleCarteleria);
				produccion.setPedidoDetalleConfeccion(null);
				produccion.setProceso("INICIADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleCarteleria.setProduccionFinalizada(false);
				detalleCarteleria.setFechaUltimoRegistroProduccion(new Date());
				detalleCarteleria.setProducciones(producciones);

				pedido.setGeneraDeuda(false);
				pedido.setProduccionFinalizada(false);
				pedido.setFechaUltimoRegistroProduccion(new Date());

				if (produccion.getMaquina() == null) {
					JOptionPane.showMessageDialog(ventana, "Se debe indicar la maquina ha utilizar");
					return;
				}

				guardar();
			}
			if (pedido.getPedidoCostura()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(pedirMaquina());
				produccion.setPedidoDetalle(null);
				produccion.setPedidoDetalleConfeccion(detalleConfeccion);
				produccion.setProceso("INICIADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleConfeccion.setProduccionFinalizada(false);
				detalleConfeccion.setFechaUltimoRegistroProduccion(new Date());
				detalleConfeccion.setProducciones(producciones);

				pedido.setGeneraDeuda(false);
				pedido.setProduccionFinalizada(false);
				pedido.setFechaUltimoRegistroProduccion(new Date());

				if (produccion.getMaquina() == null) {
					JOptionPane.showMessageDialog(ventana, "Se debe indicar la maquina ha utilizar");
					return;
				}

				guardar();
			}

		} else {
			return;
		}

	}

	private void reiniciarProduccion() {
		int acepta = JOptionPane.showConfirmDialog(null,
				"Al reiniciar la produccion, se crea un registro de desperdicio y otro que indica el reinicio",
				"REINICIAR PRODUCCION", JOptionPane.YES_NO_OPTION);
		if (acepta == JOptionPane.YES_OPTION) {
			if (pedido.getPedidoCarteleria()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(pedirMaquina());
				produccion.setPedidoDetalle(detalleCarteleria);
				produccion.setPedidoDetalleConfeccion(null);
				produccion.setProceso("REINICIADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleCarteleria.setProduccionFinalizada(false);
				detalleCarteleria.setFechaUltimoRegistroProduccion(new Date());
				detalleCarteleria.setProducciones(producciones);

				pedido.setGeneraDeuda(false);
				pedido.setProduccionFinalizada(false);
				pedido.setFechaUltimoRegistroProduccion(new Date());

				if (produccion.getMaquina() == null) {
					JOptionPane.showMessageDialog(ventana, "Se debe indicar la maquina ha utilizar");
					return;
				}

				guardar();
			}
			if (pedido.getPedidoCostura()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(pedirMaquina());
				produccion.setPedidoDetalle(null);
				produccion.setPedidoDetalleConfeccion(detalleConfeccion);
				produccion.setProceso("REINICIADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleConfeccion.setProduccionFinalizada(false);
				detalleConfeccion.setFechaUltimoRegistroProduccion(new Date());
				detalleConfeccion.setProducciones(producciones);

				pedido.setGeneraDeuda(false);
				pedido.setProduccionFinalizada(false);
				pedido.setFechaUltimoRegistroProduccion(new Date());

				if (produccion.getMaquina() == null) {
					JOptionPane.showMessageDialog(ventana, "Se debe indicar la maquina ha utilizar");
					return;
				}

				guardar();
			}

		} else {
			return;
		}

	}

	private void finalizarProduccion() {
		int acepta = JOptionPane.showConfirmDialog(null, "Finalizar la producción de este item?",
				"FINALIZAR PRODUCCION", JOptionPane.YES_NO_OPTION);
		if (acepta == JOptionPane.YES_OPTION) {
			if (pedido.getPedidoCarteleria()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(null);
				produccion.setPedidoDetalle(detalleCarteleria);
				produccion.setPedidoDetalleConfeccion(null);
				produccion.setProceso("FINALIZADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleCarteleria.setProducciones(producciones);
				detalleCarteleria.setProduccionFinalizada(true);
				detalleCarteleria.setFechaUltimoRegistroProduccion(new Date());

				pedido.setGeneraDeuda(verificarDetallesParaFinalizar(pedido));
				pedido.setProduccionFinalizada(verificarDetallesParaFinalizar(pedido));
				pedido.setFechaUltimoRegistroProduccion(new Date());

				guardar();
			}
			if (pedido.getPedidoCostura()) {
				produccion = new Produccion();
				produccion.setPedido(pedido);
				produccion.setCantidadDesperdicio(0);
				produccion.setColaborador(Sesion.getInstance().getColaborador());
				produccion.setComentario("N/A");
				produccion.setDesperdicio(false);
				produccion.setMaquina(null);
				produccion.setPedidoDetalle(null);
				produccion.setPedidoDetalleConfeccion(detalleConfeccion);
				produccion.setProceso("FINALIZADO");
				produccion.setSector(Sesion.getInstance().getSector());
				producciones.add(produccion);

				detalleConfeccion.setProducciones(producciones);
				detalleConfeccion.setProduccionFinalizada(true);
				detalleConfeccion.setFechaUltimoRegistroProduccion(new Date());

				pedido.setGeneraDeuda(verificarDetallesParaFinalizar(pedido));
				pedido.setProduccionFinalizada(verificarDetallesParaFinalizar(pedido));
				pedido.setFechaUltimoRegistroProduccion(new Date());

				guardar();
			}

		} else {
			return;
		}

	}

	private void desperdiciarProduccion() {
		int acepta = JOptionPane.showConfirmDialog(null, "Debe confirmar el desperdicio para poder seguir",
				"DESPERDICIO DE PRODUCION", JOptionPane.YES_NO_OPTION);
		if (acepta == JOptionPane.YES_OPTION) {
			if (pedido.getPedidoCarteleria()) {
				if (detalleCarteleria.getUltimoEstadoProduccion().equalsIgnoreCase("CANCELADO")) {
					return;
				} else {
					produccion = new Produccion();
					produccion.setPedido(pedido);
					produccion.setCantidadDesperdicio(detalleCarteleria.getMedidaDetalle());
					produccion.setColaborador(Sesion.getInstance().getColaborador());
					produccion.setComentario("N/A");
					produccion.setDesperdicio(true);
					produccion.setMaquina(null);
					produccion.setPedidoDetalle(detalleCarteleria);
					produccion.setPedidoDetalleConfeccion(null);
					produccion.setProceso("CANCELADO");
					produccion.setSector(Sesion.getInstance().getSector());
					producciones.add(produccion);

					detalleCarteleria.setProduccionFinalizada(false);
					detalleCarteleria.setProducciones(producciones);
					detalleCarteleria.setFechaUltimoRegistroProduccion(new Date());

					pedido.setGeneraDeuda(false);
					pedido.setProduccionFinalizada(false);
					pedido.setFechaUltimoRegistroProduccion(new Date());

					guardar();
				}
			}
			if (pedido.getPedidoCostura()) {
				if (detalleConfeccion.getUltimoEstadoProduccion().equalsIgnoreCase("CANCELADO")) {

				} else {
					produccion = new Produccion();
					produccion.setPedido(pedido);
					produccion.setCantidadDesperdicio(detalleCarteleria.getMedidaDetalle());
					produccion.setColaborador(Sesion.getInstance().getColaborador());
					produccion.setComentario("N/A");
					produccion.setDesperdicio(true);
					produccion.setMaquina(null);
					produccion.setPedidoDetalle(null);
					produccion.setPedidoDetalleConfeccion(detalleConfeccion);
					produccion.setProceso("CANCELADO");
					produccion.setSector(Sesion.getInstance().getSector());
					producciones.add(produccion);

					detalleConfeccion.setProduccionFinalizada(false);
					detalleConfeccion.setProducciones(producciones);
					detalleConfeccion.setFechaUltimoRegistroProduccion(new Date());

					pedido.setGeneraDeuda(false);
					pedido.setProduccionFinalizada(false);
					pedido.setFechaUltimoRegistroProduccion(new Date());

					guardar();
				}
			}

		} else {
			return;
		}

	}

	private void guardar() {
		try {
			dao.modificar(pedido);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			dao.rollBack();
		}
		modeloTablaProduccion.fireTableDataChanged();
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

		String info1 = pedido.isGeneraDeuda() ? "FINALIZADO" : "NO FINALIZADO";
		String info2 = pedido.getPedidoCarteleria() ? "PEDIDO CARTELERIA" : "PEDIDO CONFECCION";

		ventana.gettPedido().setValue((double) pedido.getId());

		ventana.getlPedido1()
				.setText("PEDIDO " + pedido.getId() + " del " + EventosUtil.formatoFecha(pedido.getFechaRegistro()));

		ventana.getlPedido2().setText(info1 + " " + info2);

		ventana.getlPedido3()
				.setText("METRAJE TOTAL DEL PEDIDO " + EventosUtil.separadorDecimales(pedido.getMetrosTotal()));
		ventana.getlCliente().setText(pedido.getCliente().toString());
		ventana.getlColaborador().setText(pedido.getColaborador().toString());

		if (pedido.getPedidoCarteleria()) {
			detallesCarteleria = pedido.getPedidoDetalles();
			modeloTablaPedidoDetalle.setDetalle(detallesCarteleria);
			modeloTablaPedidoDetalle.fireTableDataChanged();
		}
		if (pedido.getPedidoCostura()) {
			detallesConfeccion = pedido.getPedidosConfecciones();
			modeloTablaPedidoConfeccionDetalle.setDetalle(detallesConfeccion);
			modeloTablaPedidoConfeccionDetalle.fireTableDataChanged();
		}

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
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem iniciar = new JMenuItem("\u25B6 Iniciar");
		iniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarProduccion();

			}
		});
		JMenuItem reiniciar = new JMenuItem("\uD83D\uDD01 Reiniciar");
		reiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reiniciarProduccion();

			}
		});
		JMenuItem cancelar = new JMenuItem("\u23F9 Cancelar");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desperdiciarProduccion();

			}
		});
		JMenuItem finalizar = new JMenuItem("\u23F8 Finalizar");
		finalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizarProduccion();

			}
		});

		if (pedido.getPedidoCarteleria()) {
			switch (detalleCarteleria.getUltimoEstadoProduccion()) {
			case "INICIADO":
				popup.add(cancelar);
				popup.add(finalizar);
				break;

			case "CANCELADO":
				popup.add(reiniciar);

				break;
			case "FINALIZADO":
//				popup.add(cancelar);
				break;
			case "REINICIADO":
				popup.add(cancelar);
				popup.add(finalizar);
				break;
			default:
				popup.add(iniciar);
				break;
			}
		}

		if (pedido.getPedidoCostura()) {
			switch (detalleConfeccion.getUltimoEstadoProduccion()) {
			case "INICIADO":
				popup.add(cancelar);
				popup.add(finalizar);
				break;

			case "CANCELADO":
				popup.add(reiniciar);

				break;
			case "FINALIZADO":
//				popup.add(cancelar);
				break;
			case "REINICIADO":
				popup.add(cancelar);
				popup.add(finalizar);
				break;
			default:
				popup.add(iniciar);
				break;
			}
		}

		return popup;
	}
}
