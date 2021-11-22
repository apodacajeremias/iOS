package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.controlador.util.Impresiones;
import iOS.controlador.util.MetodosPedido;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.pedidos.TransaccionPedido;
import iOS.vista.ventanas.reportes.ReportePedido;

public class ReportePedidoConfeccionControlador implements ActionListener, MouseListener {
	private ReportePedido reporte;
	private ModeloTablaPedido modeloTabla;

	private PedidoDao dao;

	private Pedido pedido;
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public ReportePedidoConfeccionControlador(ReportePedido reporte) {
		this.reporte = reporte;
		reporte.setTitle("REPORTE DE CONFECCION");
		modeloTabla = new ModeloTablaPedido();
		reporte.getTable().setModel(modeloTabla);
		tableMenu(reporte.getTable());
		dao = new PedidoDao();

		estadoInicial(true);
		setUpEvents();
		cargarColaboradores();
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

	private void setUpEvents() {
		reporte.getBtnFiltrar().addActionListener(this);
		reporte.getBtnImprimir().addActionListener(this);
		reporte.getTable().addMouseListener(this);

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosBotones(reporte.getBtnFiltrar(), b);
		EventosUtil.estadosBotones(reporte.getBtnImprimir(), b);
	}

	private void vaciarTabla() {
		pedidos = new ArrayList<Pedido>();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}

	private void filtroPorColaborador(String claseReporte) {
		vaciarTabla();
		Colaborador c = (Colaborador) reporte.getCbColaborador().getSelectedItem();
		boolean pedidoCarteleria = false;
		boolean pedidoCostura = true;
		boolean estado = reporte.getRb3().isSelected();
		boolean esPresupuesto = reporte.getRb2().isSelected();
		Date fecha = reporte.getDateChooser().getDate();
		Integer mes = reporte.getDateChooser().getCalendar().get(Calendar.MONTH) + 1;
		Integer anho = reporte.getDateChooser().getCalendar().get(Calendar.YEAR);

		System.err.println("*****************************************************");
		System.err.println("Fecha: " + fecha + " Mes: " + mes + " Año:" + anho);
		System.err.println("*****************************************************");

		switch (claseReporte) {
		case "Diario":
			pedidos = dao.reporteDiarioPedido(c.getId(), pedidoCarteleria, pedidoCostura, estado, esPresupuesto, fecha);
			modeloTabla.setPedidos(pedidos);
			modeloTabla.fireTableDataChanged();
			break;
		case "Mensual":
			pedidos = dao.reporteMensualPedido(c.getId(), pedidoCarteleria, pedidoCostura, estado, esPresupuesto, mes,
					anho);
			modeloTabla.setPedidos(pedidos);
			modeloTabla.fireTableDataChanged();
			break;
		}

	}

	private void tableMenu(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopup(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopup(final JTable table, final int row) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem imprimirItem = new JMenuItem("Imprimir");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Impresiones.getInstance().imprimirPedidoConfeccionIndividual(pedidos.get(row), reporte);
			}
		});
		JMenuItem anularPedido = new JMenuItem("Anular");
		anularPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MetodosPedido.getInstance().anularPedido(pedidos.get(row), dao);

			}
		});
		popup.add(imprimirItem);
		popup.add(anularPedido);
		return popup;
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		pedido = pedidos.get(posicion);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirTransaccionPedido();
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

	private void abrirTransaccionPedido() {
		if (pedido == null) {
			return;
		}
		TransaccionPedido ventana = new TransaccionPedido();
		ventana.setUpConfeccionControlador();
		ventana.getConfeccionControlador().setPedido(pedido);
		ventana.setVisible(true);
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
				Impresiones.getInstance().imprimirReportePedido(pedidos, reporte.getRb5().getText().toUpperCase(),
						reporte.getTitle().toUpperCase(), reporte);
			}
			if (reporte.getRb6().isSelected()) {
				Impresiones.getInstance().imprimirReportePedido(pedidos, reporte.getRb6().getText().toUpperCase(),
						reporte.getTitle().toUpperCase(), reporte);
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
