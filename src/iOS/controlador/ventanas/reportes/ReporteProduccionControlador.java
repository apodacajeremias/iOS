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
import iOS.modelo.dao.ProduccionDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Produccion;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaProduccionReporte;
import iOS.vista.ventanas.reportes.ReporteProduccion;
import iOS.vista.ventanas.transacciones.TransaccionProduccion;

public class ReporteProduccionControlador implements ActionListener, MouseListener {
	private ReporteProduccion reporte;
	private ProduccionDao dao;

	private Produccion produccion;
	private List<Produccion> producciones = new ArrayList<Produccion>();

	private ModeloTablaProduccionReporte tablaProduccion;

	public ReporteProduccionControlador(ReporteProduccion reporte) {
		this.reporte = reporte;

		tablaProduccion = new ModeloTablaProduccionReporte();
		reporte.getTable().setModel(tablaProduccion);
		tableMenu(reporte.getTable());

		dao = new ProduccionDao();
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
		producciones = new ArrayList<Produccion>();
		tablaProduccion.setProducciones(producciones);
		tablaProduccion.fireTableDataChanged();
	}

	private void filtrar() {
		vaciarTabla();

		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			producciones = dao.recuperarHoy(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			producciones = dao.recuperarMes(reporte.getPanelGeneral().getMonthChooser().getMonth() + 1);

		} else if (reporte.getPanelGeneral().getRdAnho().isSelected()) {
			producciones = dao.recuperarAnho(reporte.getPanelGeneral().getYearChooser().getYear());

		} else {

		}

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			producciones = producciones.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
							&& c.getProceso().equals("FINALIZADO"))
					.collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			producciones = producciones.stream()
					.filter(c -> c.getColaborador().getId() == Sesion.getInstance().getColaborador().getId()
							&& c.isDesperdicio() == true)
					.collect(Collectors.toList());
		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {
			// No filtra por colaborador
			producciones = producciones.stream().filter(c -> c.getProceso().equals("FINALIZADO"))
					.collect(Collectors.toList());
		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador cc = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			producciones = producciones.stream()
					.filter(c -> c.getColaborador().getId() == cc.getId() && c.getProceso().equals("FINALIZADO"))
					.collect(Collectors.toList());
		} else {

		}

		// estado == true
		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			producciones = producciones.stream().filter(x -> x.isEstado() == true).collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			producciones = producciones.stream().filter(x -> x.isEstado() == false).collect(Collectors.toList());
		} else {

		}

		// Carteleria + Confeccion
		if (reporte.getPanelEspecifico().getRdTipo1().isSelected()) {
			producciones = producciones.stream().filter(
					c -> c.getPedido().isPedidoCarteleria() == true || c.getPedido().isPedidoCostura() == true)
					.collect(Collectors.toList());

			// Carteleria
		} else if (reporte.getPanelEspecifico().getRdTipo2().isSelected()) {
			producciones = producciones.stream().filter(c -> c.getPedido().isPedidoCarteleria() == true)
					.collect(Collectors.toList());

			// Confeccion
		} else if (reporte.getPanelEspecifico().getRdTipo3().isSelected()) {
			producciones = producciones.stream().filter(c -> c.getPedido().isPedidoCostura() == true)
					.collect(Collectors.toList());

		}


		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + producciones.size() + " registros");
		reporte.getPanelGeneral().getlInfo2().setText("Se han encontrado " + producciones.size() + " registros");

		tablaProduccion.setProducciones(producciones);
		tablaProduccion.fireTableDataChanged();
	}

	private void imprimir() {
		String tipoReporte = "";
		String claseReporte = "";
		if (reporte.getPanelGeneral().getRdHoy().isSelected()) {
			tipoReporte = "REPORTE DIARIO. FECHA: " + EventosUtil.formatoFecha(new Date());

		} else if (reporte.getPanelGeneral().getRdMes().isSelected()) {
			tipoReporte = "REPORTE MENSUAL. MES: " + (reporte.getPanelGeneral().getMonthChooser().getMonth() + 1);

		} else if (reporte.getPanelGeneral().getRdAnho().isSelected()) {
			tipoReporte = "REPORTE ANUAL. AÑO: " + reporte.getPanelGeneral().getYearChooser().getYear();
		} else {
		}

		claseReporte = "REPORTE GENERAL SEGUN FILTROS";
		Metodos.getInstance().imprimirReporteProduccion(producciones, tipoReporte, claseReporte);

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
//		produccion = producciones.get(posicion);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirTransaccionProduccion();
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

	private void abrirTransaccionProduccion() {
		if (produccion == null) {
			return;
		}
		TransaccionProduccion ventana = new TransaccionProduccion();
		ventana.setUpControlador();
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
//				Metodos.getInstance().imprimirProduccionCarteleriaIndividual(producciones.get(row));
			}
		});
		JMenuItem anularProduccion = new JMenuItem("Anular");
		anularProduccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Metodos.getInstance().anularRegistro(producciones.get(row));
			}
		});
		popup.add(imprimirItem);
		popup.add(anularProduccion);
		return popup;
	}
}