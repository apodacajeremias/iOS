package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.pedidos.TransaccionPedido;
import iOS.vista.ventanas.reportes.ReportePedido;

public class ReportePedidoControlador implements ActionListener, MouseListener {
	private ReportePedido reporte;
	private ModeloTablaPedido modeloTabla;
	private PedidoDao dao;

	private Pedido pedido;
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public ReportePedidoControlador(ReportePedido reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaPedido();
		reporte.getTable().setModel(modeloTabla);
		tableMenu(reporte.getTable());

		dao = new PedidoDao();
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
		pedidos = new ArrayList<Pedido>();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}

	private void filtrar() {
		vaciarTabla();

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			reporte.getPanelGeneral().getcFechaDesde().setDate(new Date());
			reporte.getPanelGeneral().getcFechaHasta().setDate(new Date());
			pedidos = dao.recuperarHoy(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			pedidos = dao.recuperarPeriodo(reporte.getPanelGeneral().getcFechaDesde().getDate(),
					reporte.getPanelGeneral().getcFechaHasta().getDate());

		}

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			pedidos = pedidos.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
							&& c.isEsPresupuesto() == false)
					.collect(Collectors.toList());

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			pedidos = pedidos.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
							&& c.isEsPresupuesto() == true)
					.collect(Collectors.toList());

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {
			pedidos = pedidos.stream().filter(c -> c.isEsPresupuesto() == false).collect(Collectors.toList());

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador cc = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			pedidos = pedidos.stream()
					.filter(c -> c.getColaborador().getId() == cc.getId() && c.isEsPresupuesto() == false)
					.collect(Collectors.toList());
		} else {

		}

		// estado == true
		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			pedidos = pedidos.stream().filter(x -> x.isEstado() == true).collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			pedidos = pedidos.stream().filter(x -> x.isEstado() == false).collect(Collectors.toList());
		} else {

		}

		// Caja abierta
		if (reporte.getPanelEspecifico().getRdTipo1().isSelected()) {
			pedidos = pedidos.stream().filter(x -> x.isPedidoCarteleria() == true || x.isPedidoCostura() == true)
					.collect(Collectors.toList());

			// Caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo2().isSelected()) {
			pedidos = pedidos.stream().filter(x -> x.isPedidoCarteleria() == true).collect(Collectors.toList());

			// Caja abierta + caja cerrada
		} else if (reporte.getPanelEspecifico().getRdTipo3().isSelected()) {
			pedidos = pedidos.stream().filter(x -> x.isPedidoCostura() == true).collect(Collectors.toList());

		}

		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + pedidos.size() + " registros");
		reporte.getPanelGeneral().getlInfo2().setText("Se han encontrado " + pedidos.size() + " registros");

		modeloTabla.setPedidos(pedidos);
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
		Metodos.getInstance().imprimirReportePedido(pedidos, tipoReporte, claseReporte);

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
		ventana.setUpCarteleriaControlador();
		ventana.getCarteleriaControlador().modificar();
		ventana.getCarteleriaControlador().setPedido(pedido);
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
				Metodos.getInstance().imprimirPedidoCarteleriaIndividual(pedidos.get(row));
			}
		});
		JMenuItem anularPedido = new JMenuItem("Anular");
		anularPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Metodos.getInstance().anularRegistro(pedidos.get(row));
			}
		});
		popup.add(imprimirItem);
		popup.add(anularPedido);
		return popup;
	}
}