package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import iOS.controlador.util.ConexionReporte;
import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.pedidos.PedidoCarteleria;
import iOS.vista.ventanas.reportes.ReportePedido;
import net.sf.jasperreports.engine.JRException;

public class ReportePedidoCarteleriaControlador implements ActionListener, ColaboradorInterface, MouseListener {
	private ReportePedido reporte;
	private ModeloTablaPedido modeloTabla;
	private ModeloTablaPedidoDetalle modeloTablaDetalle;

	private PedidoDao dao;

	private Pedido pedido;
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private Colaborador colaborador;
	
	private List<PedidoDetalles> pedidoDetalles = new ArrayList<PedidoDetalles>();

	public ReportePedidoCarteleriaControlador(ReportePedido reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaPedido();
		modeloTablaDetalle = new ModeloTablaPedidoDetalle();
		reporte.getTable().setModel(modeloTabla);
		reporte.getTableDetalle().setModel(modeloTablaDetalle);
		dao = new PedidoDao();

		estadoInicial(true);
		setUpEvents();
	}

	private void setUpEvents() {
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);
		reporte.getTable().addMouseListener(this);

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosBotones(reporte.getBtnFiltrar(), b);
		EventosUtil.estadosBotones(reporte.getBtnImprimir(), b);
	}

	private void vaciarTabla() {
		pedidos = new ArrayList<Pedido>();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}

	private void filtro() {
		if (EventosUtil.liberarAccesoSegunRol(colaborador, "ADMINISTRADOR")){
			filtrarTodo();
			return;
		}
		
		if (EventosUtil.liberarAcceso(colaborador, reporte.modulo, "ABRIR")) {
			filtrarPorColaborador();
			return;
		}
		
//		for (int i = 0; i < colaborador.getRol().getRolesOperaciones().size(); i++) {
//			if (colaborador.getRol().getRolesOperaciones().get(i).getOperacion().getModulo().getNombreModulo().equalsIgnoreCase(reporte.modulo)) {
//				switch (colaborador.getRol().getRolesOperaciones().get(i).getOperacion().getNombreOperacion()) {
//				case "ABRIR":
//					filtrarPorColaborador();
//					break;
//				}
//			}
//		}
	}

	private void filtrarTodo() {
		vaciarTabla();
		pedidos = dao.recuperarPedidosCarteleria();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}


	private void filtrarPorColaborador() {
		vaciarTabla();
		pedidos = dao.recuperarPedidosCarteleria(colaborador.getId());
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		pedido = pedidos.get(posicion);
		EventosUtil.estadosBotones(reporte.getBtnImprimir(), true);
		modeloTablaDetalle.setDetalle(pedidos.get(posicion).getPedidoDetalles());
		modeloTablaDetalle.fireTableDataChanged();
	}
	

	private void imprimir() {
		Date date = new Date();
		long d = date.getTime();
		java.sql.Date fecha = new java.sql.Date(d);
		
		pedidoDetalles = dao.reporteVentasCarteleria(colaborador.getId(), date);		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
		

		// Creando reportes
		ConexionReporte<PedidoDetalles> conexionReporte = new ConexionReporte<PedidoDetalles>();
		try {
			conexionReporte.generarReporte(pedidoDetalles, parametros, "ReportePedido");
			filtro();
			conexionReporte.ventanaReporte.setLocationRelativeTo(reporte);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirTransaccionPedido();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}
		filtro();

	}

	private void abrirTransaccionPedido() {
		if (pedido == null) {
			return;
		}
		PedidoCarteleria ventana = new PedidoCarteleria();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setPedido(pedido);
			ventana.getControlador().setColaborador(colaborador);
			ventana.setVisible(true);
		} else {
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Filtrar":
			filtro();
			break;
		case "Imprimir":
			imprimir();
			break;

		default:
			break;
		}

	}

}