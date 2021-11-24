package iOS.controlador.ventanas.reportes;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Pedido;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.reportes.ReporteDeudasPagos;

public class ReporteDeudasPagosControlador implements ActionListener {
	private ReporteDeudasPagos reporte;

	private PedidoDao pedidoDao;
	private CajaDao cajaDao;

	private ModeloTablaPedido modeloTablaPedido;
	private ModeloTablaCajaMovimiento modeloTablaCaja;

	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private List<CajaMovimiento> pagos = new ArrayList<CajaMovimiento>();

	public ReporteDeudasPagosControlador(ReporteDeudasPagos reporte) {
		this.reporte = reporte;

		modeloTablaCaja = new ModeloTablaCajaMovimiento();
		modeloTablaPedido = new ModeloTablaPedido();

		reporte.getTable().setModel(modeloTablaPedido);
		reporte.getTablePago().setModel(modeloTablaCaja);

		pedidoDao = new PedidoDao();
		cajaDao = new CajaDao();

		setUpEvents();
	}

	private void setUpEvents() {
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);

	}

	private void vaciarTablas() {
		pedidos = new ArrayList<Pedido>();
		modeloTablaPedido.setPedidos(pedidos);
		modeloTablaPedido.fireTableDataChanged();

		pagos = new ArrayList<CajaMovimiento>();
		modeloTablaCaja.setMovimiento(pagos);
		modeloTablaCaja.fireTableDataChanged();

	}

	private void filtrar() {
		reporte.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		//Pago sin pedido
		if (reporte.getRb3().isSelected()) {
			pagos = cajaDao.recuperaPagos();
			pagos = pagos.stream().filter(o -> o.getPedido() == null && o.isEsRetiro() == false && o.isEsAnulado() == false).collect(Collectors.toList());
			modeloTablaCaja.setMovimiento(pagos);
			modeloTablaCaja.fireTableDataChanged();
		}
		//Pedido sin pago
		if (reporte.getRb4().isSelected()) {
			pedidos = pedidoDao.recuperarTodo();
			pedidos = pedidos.stream().filter(o -> o.getPagosPedido().stream().filter(u -> u.isEsAnulado() == false).collect(Collectors.toList()).size() <= 0).collect(Collectors.toList());
			modeloTablaPedido.setPedidos(pedidos);
			modeloTablaPedido.fireTableDataChanged();
		}
		reporte.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Imprimir":

			break;
		case "Filtrar":
			filtrar();
			break;
		case "Limpiar":
			vaciarTablas();
			break;

		default:
			break;
		}

	}
}
