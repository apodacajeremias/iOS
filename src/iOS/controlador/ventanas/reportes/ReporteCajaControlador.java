package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCaja;
import iOS.vista.ventanas.reportes.ReporteCaja;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class ReporteCajaControlador implements ActionListener, MouseListener {
	private ReporteCaja reporte;
	private ModeloTablaCaja modeloTabla;
	private CajaDao dao;
	private Caja caja;
	private List<Caja> cajas = new ArrayList<Caja>();

	public ReporteCajaControlador(ReporteCaja reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaCaja();
		reporte.getTable().setModel(modeloTabla);
		dao = new CajaDao();

		estadoInicial(true);
		setUpEvents();
		cargarColaboradores();
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

	private void estadoInicial(boolean b) {
		if (!EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			reporte.getPanelEspecifico().setEnabled(false);
		}

		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelEspecifico().getlInfo2());
		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelGeneral().getlInfo2());
	}

	private void filtrar() {
		vaciarTabla();

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			reporte.getPanelGeneral().getcFechaDesde().setDate(new Date());
			reporte.getPanelGeneral().getcFechaHasta().setDate(new Date());
			cajas = dao.recuperarHoy(new Date());
		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			cajas = dao.recuperarPeriodo(reporte.getPanelGeneral().getcFechaDesde().getDate(),
					reporte.getPanelGeneral().getcFechaHasta().getDate());
		}

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			cajas = cajas.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId())
					.collect(Collectors.toList());

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			cajas = cajas.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId())
					.collect(Collectors.toList());

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {
			// No filtra colaborador, solo por el rango de fechas

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador cc = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			cajas = cajas.stream().filter(c -> c.getColaborador().getId() == cc.getId()).collect(Collectors.toList());
		} else {

		}

		// estado == true
		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			cajas = cajas.stream().filter(x -> x.isEstado() == true).collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			cajas = cajas.stream().filter(x -> x.isEstado() == false).collect(Collectors.toList());
		} else {

		}

		// Caja abierta
		if (reporte.getPanelEspecifico().getRdTipo1().isSelected()) {
			cajas = cajas.stream().filter(x -> x.isCajaCerrada() == true).collect(Collectors.toList());

			// Caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo2().isSelected()) {
			cajas = cajas.stream().filter(x -> x.isCajaCerrada() == false).collect(Collectors.toList());

			// Caja abierta + caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo3().isSelected()) {
			cajas = cajas.stream().filter(x -> x.isCajaCerrada() == true || x.isCajaCerrada() == false)
					.collect(Collectors.toList());
		}

		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + cajas.size() + " registros");
		reporte.getPanelGeneral().getlInfo2().setText("Se han encontrado " + cajas.size() + " registros");

		modeloTabla.setCajas(cajas);
		modeloTabla.fireTableDataChanged();
	}

	private void imprimir() {
		String tipoReporte = "";
		String claseReporte = "";
		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			tipoReporte = "REPORTE DIARIO. FECHA: " + EventosUtil.formatoFecha(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			tipoReporte = "PERIODO, " + " DESDE: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaDesde().getDate()) + " HASTA: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaHasta().getDate());
		}

		if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			claseReporte = "REPORTE DE VALES PARA COLABORADORES";
			List<CajaMovimiento> cm = new ArrayList<CajaMovimiento>();

			for (int i = 0; i < cajas.size(); i++) {
				cm.addAll(cajas
						.get(i).getCajaMovimientos().stream().filter(c -> c.isEsAnulado() == false
								&& c.isEsRetiro() == true && c.isEstado() == true && c.isEsVale() == true)
						.collect(Collectors.toList()));
			}
			Metodos.getInstance().imprimirReporteVale(cm, tipoReporte, claseReporte);
		} else {
			claseReporte = "REPORTE GENERAL SEGUN FILTROS";
			Metodos.getInstance().imprimirReporteCaja(cajas, tipoReporte, claseReporte);
		}

	}

	private void vaciarTabla() {
		cajas = new ArrayList<Caja>();
		modeloTabla.setCajas(cajas);
		modeloTabla.fireTableDataChanged();
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		caja = cajas.get(posicion);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirTransaccionCaja();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private void abrirTransaccionCaja() {
		if (caja == null) {
			return;
		}
		TransaccionCaja ventana = new TransaccionCaja();
		ventana.setUpControlador();
		ventana.getControlador().setCaja(caja);
		ventana.setVisible(true);
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

			break;
		default:
			break;
		}
	}
}