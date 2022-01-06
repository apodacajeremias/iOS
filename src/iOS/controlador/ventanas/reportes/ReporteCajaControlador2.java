package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.reportes.ReporteCaja;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class ReporteCajaControlador2 implements ActionListener, MouseListener {
	private ReporteCaja reporte;
	private ModeloTablaCajaMovimiento modeloTabla;
	private CajaDao dao;
	private Caja caja;
	private List<CajaMovimiento> cajas = new ArrayList<CajaMovimiento>();

	public ReporteCajaControlador2(ReporteCaja reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaCajaMovimiento();
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
		
		cajas = dao.recuperarTodos();
		
		for (int i = 0; i < cajas.size(); i++) {
			CajaMovimiento cm = cajas.get(i);
			
			cm.setCotizacionGS(Sesion.getInstance().getCotizacion().getCotizacionGS());
			cm.setCotizacionGS(Sesion.getInstance().getCotizacion().getCotizacionRS());
			cm.setCotizacionGS(Sesion.getInstance().getCotizacion().getCotizacionUS());
			
			try {
				dao.modificar(cm.getCaja());
				dao.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dao.rollBack();
			}
		}
	}

	private void vaciarTabla() {
		cajas = new ArrayList<CajaMovimiento>();
		modeloTabla.setMovimiento(cajas);
		modeloTabla.fireTableDataChanged();
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
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
		case "Limpiar":

			break;
		default:
			break;
		}
	}
}