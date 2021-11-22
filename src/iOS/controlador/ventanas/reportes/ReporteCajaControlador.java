package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.controlador.util.Impresiones;
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
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);
		reporte.getBtnLimpiar().addActionListener(this);
		reporte.getTable().addMouseListener(this);
	}
	
	@SuppressWarnings("unchecked")
	private void cargarColaboradores() {
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			for (int i = 0; i < Sesion.getInstance().getColaboradores().size(); i++) {
				reporte.getCbColaborador().addItem( Sesion.getInstance().getColaboradores().get(i));
			}
		} else {
			reporte.getCbColaborador().addItem(Sesion.getInstance().getColaborador());
		}
	}

	private void estadoInicial(boolean b) {
	}

	private void vaciarTabla() {
		cajas = new ArrayList<Caja>();
		modeloTabla.setCajas(cajas);
		modeloTabla.fireTableDataChanged();
	}

	private void filtroPorColaborador(String claseReporte) {
		vaciarTabla();
		Colaborador c = (Colaborador) reporte.getCbColaborador().getSelectedItem();
		boolean estado = reporte.getRb3().isSelected();
		boolean cajaCerrada = reporte.getRb1().isSelected();
		Date fecha = reporte.getDateChooser().getDate();
		Integer mes = reporte.getDateChooser().getCalendar().get(Calendar.MONTH) + 1;
		Integer anho = reporte.getDateChooser().getCalendar().get(Calendar.YEAR);

		System.err.println("*****************************************************");
		System.err.println("Fecha: " + fecha + " Mes: " + mes + " Año:" + anho);
		System.err.println("*****************************************************");
		switch (claseReporte) {
		case "Diario":
			cajas = dao.reporteDiarioCaja(c.getId(), estado, cajaCerrada, fecha);
			modeloTabla.setCajas(cajas);
			modeloTabla.fireTableDataChanged();
			break;
		case "Mensual":
			cajas = dao.reporteMensualCaja(c.getId(), estado, cajaCerrada, mes, anho);
			modeloTabla.setCajas(cajas);
			modeloTabla.fireTableDataChanged();
			break;
		}

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

	private void abrirTransaccionCaja() {
		if (caja == null) {
			return;
		}
		TransaccionCaja ventana = new TransaccionCaja();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setCaja(caja);
			ventana.setVisible(true);
		} else {
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Filtrar":
			if (reporte.getRb5().isSelected()) {
				filtroPorColaborador("Diario");
			}
			if (reporte.getRb6().isSelected()) {
				filtroPorColaborador("Mensual");
			}
			break;
		case "Imprimir":
			if (reporte.getRb5().isSelected()) {
				Impresiones.getInstance().imprimirReporteCaja(cajas, reporte.getRb5().getText().toUpperCase(), reporte.getTitle().toUpperCase(), reporte);
			}
			if (reporte.getRb6().isSelected()) {
				Impresiones.getInstance().imprimirReporteCaja(cajas, reporte.getRb6().getText().toUpperCase(), reporte.getTitle().toUpperCase(), reporte);
			}
			break;
		case "Limpiar":
			vaciarTabla();
			break;

		default:
			break;
		}
	}
}