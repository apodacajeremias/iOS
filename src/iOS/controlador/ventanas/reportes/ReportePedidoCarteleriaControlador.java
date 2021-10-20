package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.ConexionReporte;
import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CajaDao;
import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.pedidos.PedidoCarteleria;
import iOS.vista.ventanas.reportes.ReportePedido;
import net.sf.jasperreports.engine.JRException;

public class ReportePedidoCarteleriaControlador implements ActionListener, ColaboradorInterface, MouseListener {
	private ReportePedido reporte;
	private ModeloTablaPedido modeloTabla;
	private ModeloTablaPedidoDetalle modeloTablaDetalle;

	private PedidoDao dao;

	private Pedido pedido;
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private Colaborador colaborador;

	private List<PedidoDetalles> pedidoDetalles = new ArrayList<PedidoDetalles>();

	public ReportePedidoCarteleriaControlador(ReportePedido reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaPedido();
		modeloTablaDetalle = new ModeloTablaPedidoDetalle();
		reporte.getTable().setModel(modeloTabla);
		tableMenu(reporte.getTable());
		reporte.getTableDetalle().setModel(modeloTablaDetalle);
		dao = new PedidoDao();
		estadoInicial(true);
		setUpEvents();
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
	
	@SuppressWarnings("unchecked")
	private void cargarColaboradores(Colaborador c) {
		List<Colaborador> cs = null;
		ColaboradorDao cDao = new ColaboradorDao();
		if (EventosUtil.liberarAccesoSegunRol(c, "ADMINISTRADOR")){
			cs = cDao.recuperarTodoOrdenadoPorNombre();
			try {
				for (int i = 0; i < cs.size(); i++) {
					reporte.getCbColaborador().addItem(cs.get(i));
				}
			} catch (Exception e) {
				reporte.getCbColaborador().addItem(null);
			}
			return;
		}
		reporte.getCbColaborador().addItem(c);
	}

	private void vaciarTabla() {
		pedidos = new ArrayList<Pedido>();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}

	private void filtro() {
//		if (EventosUtil.liberarAccesoSegunRol(colaborador, "ADMINISTRADOR")){
//			filtrarTodo();
//			return;
//		}

		if (EventosUtil.liberarAcceso(colaborador, reporte.modulo, "ABRIR")) {
			filtrarPorColaborador();
			return;
		}
	}

	private void filtrarTodo() {
		vaciarTabla();
		pedidos = dao.recuperarPedidosCarteleria();
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
	}


	private void filtrarPorColaborador() {
		vaciarTabla();
		Colaborador c = (Colaborador) reporte.getCbColaborador().getSelectedItem();
		pedidos = dao.recuperarPedidosCarteleriaPorFiltros(c.getId(), reporte.getDcMeses().getMonth()+1, reporte.getDcAnos().getYear(), !reporte.getRb1().isSelected(), reporte.getRb2().isSelected());
		modeloTabla.setPedidos(pedidos);
		modeloTabla.fireTableDataChanged();
		System.out.println(pedidos.size());
	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		pedido = pedidos.get(posicion);
		EventosUtil.estadosBotones(reporte.getBtnImprimir(), true);
		modeloTablaDetalle.setDetalle(pedidos.get(posicion).getPedidoDetalles());
		modeloTablaDetalle.fireTableDataChanged();
	}
	


	private void imprimir() {
		Date date = new Date();

		pedidoDetalles = dao.reporteVentasCarteleria(colaborador.getId(), date);		
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("nombreEmpresa", "iOS Comunicacion Visual");


		// Creando reportes
		ConexionReporte<PedidoDetalles> conexionReporte = new ConexionReporte<PedidoDetalles>();
		try {
			conexionReporte.generarReporte(pedidoDetalles, parametros, "ReportePedido");
			filtro();
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

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}

		cargarColaboradores(colaborador);

	}

	private void abrirTransaccionPedido() {
		if (pedido == null) {
			return;
		}
		PedidoCarteleria ventana = new PedidoCarteleria();
		if (EventosUtil.liberarAcceso(colaborador, ventana.modulo, "ABRIR")) {
			ventana.setUpControlador();
			ventana.getControlador().setPedido(pedido);
			ventana.getControlador().setColaborador(colaborador);
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
			imprimir();
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
		pedido = pedidos.get(row);
		JPopupMenu popup = new JPopupMenu("Popup");		
		JMenuItem imprimirItem = new JMenuItem("Imprimir");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimir(pedido);

			}
		});
		popup.add(imprimirItem);
		return popup;
	}
	
	private double verificarValidezPago(Pedido pedido) {
		CajaDao cajaDao = new CajaDao();
		List<CajaMovimiento> pagos = cajaDao.recuperarPagoValido(pedido.getId());
		for (int i = 0; i < pagos.size(); i++) {
			if (!pagos.get(i).isEsAnulado()) {
				return pedido.getPagosPedido().get(i).getValorGS();
			}
		}
		return 0;
	}

	public void imprimir(Pedido p) {
		if (p == null) {
			return;
		}		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Desea imprimir?",
				"Impresion", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
			parametros.put("esPresupuesto", esPresupuesto(p));
			parametros.put("entrega", verificarValidezPago(p));

			List<Pedido> ps = new ArrayList<Pedido>();
			ps.add(p);

			ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
			try {
				conexionReporte.generarReporte(ps, parametros, "PedidoImpreso4");
				conexionReporte.ventanaReporte.setLocationRelativeTo(reporte);
				conexionReporte.ventanaReporte.setVisible(true);
			} catch (JRException e) {
				e.printStackTrace();
			}	
		} else {
			return;
		}	
	}
	private String esPresupuesto(Pedido p) {
		if (p.isEsPresupuesto()) {
			return "PRESUPUESTO";
		} else {
			return "PEDIDO";
		}

	}

}