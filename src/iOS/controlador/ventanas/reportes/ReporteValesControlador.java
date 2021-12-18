package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import iOS.modelo.dao.CajaDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.singleton.Metodos;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.ventanas.reportes.ReporteVales;

public class ReporteValesControlador implements ActionListener {
	private ReporteVales reporte;
	private CajaDao cajaDao;
	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private ModeloTablaCajaMovimiento modeloTablaCajaMovimiento;

	public ReporteValesControlador(ReporteVales reporte) {
		this.reporte = reporte;
		cajaDao = new CajaDao();

		modeloTablaCajaMovimiento = new ModeloTablaCajaMovimiento();
		reporte.getTable().setModel(modeloTablaCajaMovimiento);

		setUpEvents();
		recuperarCandidatosVale();
	}

	private void setUpEvents() {
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);

	}

	private void recuperarCandidatosVale() {
		movimientos = cajaDao.recuperarCandidatosVales("VALE");
		movimientos = movimientos.stream().filter(mov -> mov.isEsAnulado() == false).collect(Collectors.toList());
		modeloTablaCajaMovimiento.setMovimiento(movimientos);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Imprimir":
//			Metodos.getInstance().imprimirReporteVale(movimientos, "", "", reporte);
			break;

		default:
			break;
		}

	}
}
