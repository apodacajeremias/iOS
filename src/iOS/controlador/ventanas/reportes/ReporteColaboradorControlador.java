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
import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.VentanaColaborador;
import iOS.vista.ventanas.reportes.ReporteColaborador;

public class ReporteColaboradorControlador implements ActionListener, MouseListener {
	private ReporteColaborador reporte;
	private ModeloTablaCajaMovimiento modeloTabla;
	private ColaboradorDao dao;

	private Colaborador colaborador;

	private CajaMovimiento vale;
	private List<CajaMovimiento> vales = new ArrayList<CajaMovimiento>();

	public ReporteColaboradorControlador(ReporteColaborador reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaCajaMovimiento();
		reporte.getTable().setModel(modeloTabla);

		dao = new ColaboradorDao();
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

	private void vaciarTabla() {
	}

	private void filtrar() {
		vaciarTabla();

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			reporte.getPanelGeneral().getcFechaDesde().setDate(new Date());
			reporte.getPanelGeneral().getcFechaHasta().setDate(new Date());
			vales = dao.recuperarValesHoy(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			vales = dao.recuperarPeriodo(reporte.getPanelGeneral().getcFechaDesde().getDate(),
					reporte.getPanelGeneral().getcFechaHasta().getDate());

		}

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			vales = vales.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId())
					.collect(Collectors.toList());

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			vales = vales.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId())
					.collect(Collectors.toList());

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador cc = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			vales = vales.stream().filter(c -> c.getColaborador().getId() == cc.getId()).collect(Collectors.toList());
		} else {

		}

		// estado == true
		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			vales = vales.stream().filter(x -> x.isEstado() == true).collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			vales = vales.stream().filter(x -> x.isEstado() == false).collect(Collectors.toList());
		} else {

		}

		// Caja abierta
		if (reporte.getPanelEspecifico().getRdTipo1().isSelected()) {

			// Caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo2().isSelected()) {

			// Caja abierta + caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo3().isSelected()) {

		}

		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + vales.size() + " registros");
		reporte.getPanelGeneral().getlInfo2().setText("Se han encontrado " + vales.size() + " registros");
		modeloTabla.setMovimiento(vales);
		modeloTabla.fireTableDataChanged();
	}

	private void imprimir() {
		String tipoReporte = "";
		String claseReporte = "";
		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			tipoReporte = "REPORTE DIARIO. FECHA: " + EventosUtil.formatoFecha(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			tipoReporte = "REPORTE POR PERIODO DE TIEMPO." + " DESDE: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaDesde().getDate()) + " HASTA: "
					+ EventosUtil.formatoFecha(reporte.getPanelGeneral().getcFechaHasta().getDate());
		}

		claseReporte = "REPORTE GENERAL SEGUN FILTROS";
		Metodos.getInstance().imprimirReporteVale(vales, tipoReporte, claseReporte);

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		vale = vales.get(posicion);
		colaborador = vale.getColaborador();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirVentanaColaborador();
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

	private void abrirVentanaColaborador() {
		if (colaborador == null) {
			return;
		}
		VentanaColaborador ventana = new VentanaColaborador();
		ventana.setUpControlador();
		ventana.getControlador().modificar();
		ventana.getControlador().setColaborador(colaborador);
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
			vaciarTabla();
			break;
		default:
			break;
		}

	}
}