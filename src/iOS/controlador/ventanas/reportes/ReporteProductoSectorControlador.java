package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.PedidoDetalleConfeccion;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Sector;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoConfeccionDetalle;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.reportes.ReporteProductoSector;

public class ReporteProductoSectorControlador implements ActionListener, MouseListener {
	private ReporteProductoSector reporte;
	private ModeloTablaPedidoConfeccionDetalle mtConfeccionDetalle;
	private ModeloTablaPedidoDetalle mtCarteleriaDetalle;
	private PedidoDao dao;
	
	private List<PedidoDetalleConfeccion> detalleconfecciones = new ArrayList<PedidoDetalleConfeccion>();
	private List<PedidoDetalles> detalleCartelerias = new ArrayList<PedidoDetalles>();

	public ReporteProductoSectorControlador(ReporteProductoSector reporte) {
		this.reporte = reporte;
		 
		mtCarteleriaDetalle = new ModeloTablaPedidoDetalle();
		reporte.getTable().setModel(mtCarteleriaDetalle);
		
		mtConfeccionDetalle = new ModeloTablaPedidoConfeccionDetalle();
		reporte.getTable2().setModel(mtConfeccionDetalle);

		dao = new PedidoDao();
		estadoInicial(true);
		setUpEvents();
		cargarColaboradores();
		cargarSectores();
	}

	private void setUpEvents() {
		reporte.getBtnActualizar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);
		reporte.getBtnBuscar().addActionListener(this);
		reporte.getTable().addMouseListener(this);
	}

	private void cargarColaboradores() {
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			for (int i = 0; i < Sesion.getInstance().getColaboradores().size(); i++) {
				reporte.getPanelEspecifico().getCbColaborador().addItem(Sesion.getInstance().getColaboradores().get(i));
			}
		} else {
			reporte.getPanelEspecifico().getCbColaborador().addItem(Sesion.getInstance().getColaborador());
		}
		reporte.getPanelEspecifico().getCbColaborador().getModel()
				.setSelectedItem(Sesion.getInstance().getColaborador());
	}

	private void cargarSectores() {
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			for (int i = 0; i < Sesion.getInstance().getSectores().size(); i++) {
				reporte.getCbSector().addItem(Sesion.getInstance().getSectores().get(i));
			}
		} else {
			reporte.getCbSector().addItem(Sesion.getInstance().getColaborador().getSector());
		}
		reporte.getCbSector().getModel().setSelectedItem(Sesion.getInstance().getColaborador().getSector());
	}

	private void estadoInicial(boolean b) {
		if (!EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			reporte.getPanelEspecifico().setEnabled(false);
		}

		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelEspecifico().getlInfo2());
		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelGeneral().getlInfo2());
	}

	private void vaciarTabla() {
		detalleCartelerias = new ArrayList<PedidoDetalles>();
		mtCarteleriaDetalle.setDetalle(detalleCartelerias);
		
		detalleconfecciones = new ArrayList<PedidoDetalleConfeccion>();
		mtConfeccionDetalle.setDetalle(detalleconfecciones);
	}

	private void filtrar() {
		vaciarTabla();
		
		Sector sector = (Sector) reporte.getCbSector().getSelectedItem();

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			reporte.getPanelGeneral().getcFechaDesde().setDate(new Date());
			reporte.getPanelGeneral().getcFechaHasta().setDate(new Date());
			
			detalleCartelerias = dao.recuperarDetalleCarteleriaPorSectorDeProductoHoy(sector.getId(), new Date());
			detalleconfecciones = dao.recuperarDetalleConfeccionPorSectorDeProductoHoy(sector.getId(), new Date());
			

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			
			detalleCartelerias = dao.recuperarDetalleCarteleriaPorSectorDeProductoPeriodo(sector.getId(), reporte.getPanelGeneral().getcFechaDesde().getDate(),
					reporte.getPanelGeneral().getcFechaHasta().getDate());
			detalleconfecciones = dao.recuperarDetalleConfeccionPorSectorDeProductoPeriodo(sector.getId(),reporte.getPanelGeneral().getcFechaDesde().getDate(),
					reporte.getPanelGeneral().getcFechaHasta().getDate());
		}


		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + detalleCartelerias.size()+detalleconfecciones.size() + " registros");
		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + detalleCartelerias.size()+detalleconfecciones.size() + " registros");

		mtCarteleriaDetalle.setDetalle(detalleCartelerias);
		mtConfeccionDetalle.setDetalle(detalleconfecciones);
	}

	private void imprimir() {
		String tipoReporte = "";
		String claseReporte = "";
		Sector sector = (Sector) reporte.getCbSector().getSelectedItem();
		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			tipoReporte = "REPORTE DIARIO. FECHA: " + EventosUtil.formatoFecha(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			tipoReporte = "REPORTE POR PERIODO DE TIEMPO." + " DESDE: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaDesde().getDate()) + " HASTA: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaHasta().getDate());
		}

		claseReporte = "REPORTE GENERAL SEGUN FILTROS, SECTOR: " + sector;
		Metodos.getInstance().imprimirReporteProductoSector(detalleCartelerias,detalleconfecciones, tipoReporte, claseReporte);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Filtrar":
			filtrar();
			break;
		case "Imprimir":
			imprimir();
			break;
		case "Limpiar":
			vaciarTabla();
			break;
		default:
			break;
		}

	}
}