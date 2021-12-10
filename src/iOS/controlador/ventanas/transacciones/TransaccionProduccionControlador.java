package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Produccion;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.SectorInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.modelotabla.ModeloTablaProduccion;
import iOS.vista.ventanas.transacciones.TransaccionProduccion;

public class TransaccionProduccionControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface,
		PropertyChangeListener, SectorInterface {
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
				JOptionPane.showMessageDialog(ventana, "El pedido es un presupuesto o est� anulado, verifique...");
				return;
			}
			setPedido(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void seleccionarRegistro(Integer posicion) {
		if (posicion < 0) {
			return;
		}
		pedidoDetalle = pedidosDetalles.get(posicion);

		cargarDetalleProduccion(pedidoDetalle);
	}

	private void cargarDetalleProduccion(PedidoDetalles pd) {
		if (pd == null) {
			return;
		}
		try {
			producciones = pd.getProducciones();
			modeloTablaProduccion.setProducciones(producciones);
			modeloTablaProduccion.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void iniciarProduccion() {
		produccion = new Produccion();
		produccion.setCantidadDesperdicio(0);
		produccion.setColaborador(Sesion.getInstance().getColaborador());
		produccion.setDesperdicio(false);
		
	}
	
	private void reiniciarProduccion() {
		// TODO Auto-generated method stub

	}
	
	private void finalizarProduccion() {
		// TODO Auto-generated method stub

	}
	
	private void cancelarProduccion() {
		// TODO Auto-generated method stub

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

		if (e.getSource() == ventana.getTableSeguimientoProduccion()) {

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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void setSector(Sector sector) {
		// TODO Auto-generated method stub

	}

}
