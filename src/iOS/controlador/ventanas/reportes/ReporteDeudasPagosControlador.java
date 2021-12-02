package iOS.controlador.ventanas.reportes;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Pedido;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.reportes.ReporteDeudasPagos;

public class ReporteDeudasPagosControlador implements ActionListener {
	private ReporteDeudasPagos reporte;

	private PedidoDao pedidoDao;

	private ModeloTablaPedido modeloTablaPedido;

	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public ReporteDeudasPagosControlador(ReporteDeudasPagos reporte) {
		this.reporte = reporte;

		modeloTablaPedido = new ModeloTablaPedido();

		reporte.getTable().setModel(modeloTablaPedido);

		pedidoDao = new PedidoDao();

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
	}

	private void filtrar() {
		reporte.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// Pago sin pedido
		if (reporte.getRb1().isSelected()) {
			pedidos = pedidoDao.aislarPedidosCarteleria();
		}
		// Pedido sin pago
		if (reporte.getRb2().isSelected()) {
			pedidos = pedidoDao.aislarPedidosConfeccion();
		}
		if (reporte.getRb3().isSelected()) {
			pedidos = pedidos.stream()
					.filter(a -> a.getPagosPedido().stream()
							.filter(p -> p.isEsAnulado() == false && p.isEsRetiro() == false)
							.mapToDouble(p -> p.getValorGS()).sum() >= a.getPrecioPagar())
					.collect(Collectors.toList());
		}
		// Pedido sin pago
		if (reporte.getRb4().isSelected()) {
			pedidos = pedidos.stream()
					.filter(a -> a.getPagosPedido().stream()
							.filter(p -> p.isEsAnulado() == false && p.isEsRetiro() == false)
							.mapToDouble(p -> p.getValorGS()).sum() < a.getPrecioPagar())
					.collect(Collectors.toList());
		}
		if (reporte.getRb5().isSelected()) {
			if (reporte.getRb1().isSelected()) {
			
			}
			if (reporte.getRb2().isSelected()) {

			}
		}
		// Pedido sin pago
		if (reporte.getRb6().isSelected()) {
			if (reporte.getRb1().isSelected()) {

			}
			if (reporte.getRb2().isSelected()) {

			}
		}
		modeloTablaPedido.setPedidos(pedidos);
		modeloTablaPedido.fireTableDataChanged();
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
