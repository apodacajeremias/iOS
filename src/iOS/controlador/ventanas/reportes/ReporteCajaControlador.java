package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.Colaborador;
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
	}

	private void filtrar() {
		vaciarTabla();
		String sql = "FROM Caja WHERE ";

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			Date hoy = new Date();
			SimpleDateFormat objSDF = new SimpleDateFormat("YYYY-MM-dd");
			sql = sql + "(DATE(fechaRegistro) = " + objSDF.format(hoy) + ") ";

			System.out.println(sql);
		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			int mes = reporte.getPanelGeneral().getMonthChooser().getMonth() + 1;
			sql = sql + ("(MONTH(fechaRegistro) = " + mes + ") ");

		} else if (reporte.getPanelGeneral().getRdAnho().isSelected()) {
			int anho = reporte.getPanelGeneral().getYearChooser().getYear();
			sql = sql + ("(YEAR(fechaRegistro) = " + anho + ") ");

		} else {

		}

		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			sql = sql + ("AND (estado = true) ");

		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			sql = sql + ("AND (estado = false) ");

		} else {

		}

		// Caja abierta
		if (reporte.getPanelEspecifico().getRdTipo1().isSelected()) {
			sql = sql + ("AND (cajaCerrada = false) ");

			// Caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo2().isSelected()) {
			sql = sql + ("AND (cajaCerrada = true) ");

			// Caja abierta + caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo3().isSelected()) {
			sql = sql + ("AND (cajaCerrada = false OR cajaCerrada = true) ");

		}

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			Colaborador colaborador = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			int id = colaborador.getId();
			sql = sql + ("AND (colaborador.id = " + id + ") ");

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			Colaborador colaborador = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			int id = colaborador.getId();
			sql = sql + ("AND (colaborador.id = " + id + ") ");

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador colaborador = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			int id = colaborador.getId();
			sql = sql + ("AND (colaborador.id = " + id + ") ");

		} else {

		}

		System.out.println(sql);

		cajas = dao.recuperarRegistrosPorFiltro(sql);
		modeloTabla.setCajas(cajas);
		modeloTabla.fireTableDataChanged();
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

			break;
		case "Limpiar":

			break;
		default:
			break;
		}
	}
}