package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iOS.controlador.util.ConexionReporte;
import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCaja;
import iOS.vista.ventanas.reportes.ReporteCaja;
import iOS.vista.ventanas.transacciones.TransaccionCaja;
import net.sf.jasperreports.engine.JRException;

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
	}

	private void setUpEvents() {
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);
		reporte.getBtnLimpiar().addActionListener(this);
		reporte.getTable().addMouseListener(this);
	}

	private void estadoInicial(boolean b) {
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

	private String cajaCerrada(Caja c) {
		if (c.isCajaCerrada()) {
			return "CERRADA";
		} else {
			return "ABIERTA";
		}
	}

	private String estadoCaja(Caja c) {
		if (c.isEstado()) {
			return "VIGENTE";
		} else {
			return "ANULADO";
		}
	}
	private void imprimir(Caja c) {
		if (c == null) {
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
		parametros.put("cajaCerrada", cajaCerrada(c));
		parametros.put("estado", estadoCaja(c));

		// Creando reportes
		ConexionReporte<CajaMovimiento> conexionReporte = new ConexionReporte<CajaMovimiento>();
		try {
			conexionReporte.generarReporte(dao.recuperarMovimientoNoAnulados(c.getId()), parametros, "ReporteCaja2");
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
			filtro();
			break;
		case "Imprimir":
			imprimir(caja);
			break;

		default:
			break;
		}
	}
	
	private void filtro() {
		cajas = dao.consultaTest();
		modeloTabla.setCajas(cajas);
		modeloTabla.fireTableDataChanged();

	}

}