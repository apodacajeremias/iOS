package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.SectorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.SectorInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaColaborador;
import iOS.vista.ventanas.VentanaSector;

public class VentanaSectorControlador implements AccionesABM, MouseListener, ActionListener, SectorInterface {
	private VentanaSector ventana;
	private SectorDao dao;
	private String accion;
	private Sector sector;

	private ModeloTablaColaborador modeloTablaColaborador;
//	private ModeloTablaProceso modeloTablaProceso;

	private List<Colaborador> colaboradoresDelSector = new ArrayList<Colaborador>();
//	private List<SectorProceso> procesos = new ArrayList<SectorProceso>();

//	private SectorProceso proceso;

	public VentanaSectorControlador(VentanaSector ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		modeloTablaColaborador = new ModeloTablaColaborador();
		ventana.getTableColaboradores().setModel(modeloTablaColaborador);

//		modeloTablaProceso = new ModeloTablaProceso();
//		ventana.getTableProcesos().setModel(modeloTablaProceso);
		tableMenu(ventana.getTableProcesos());

		dao = new SectorDao();
		nuevo();
		estadoInicial(true);
		setUpEvents();
	}

	private void setUpEvents() {
		// MOUSE LISTENER
		ventana.getTableProcesos().addMouseListener(this);
		ventana.getTableColaboradores().addMouseListener(this);

		ventana.getBtnAgregar().addActionListener(this);

	}

	private void estadoInicial(boolean b) {
		ventana.getlMensaje().setText(null);

		// LIMPIAR JTEXTFIELD
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDescripcion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFechaRegistro());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlID());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlEstado());

		vaciarFormularios();

	}

	private void vaciarFormularios() {
		colaboradoresDelSector = new ArrayList<Colaborador>();
		modeloTablaColaborador.setLista(colaboradoresDelSector);
		modeloTablaColaborador.fireTableDataChanged();

//		procesos = new ArrayList<SectorProceso>();
//		modeloTablaProceso.setProcesos(procesos);
//		modeloTablaProceso.fireTableDataChanged();

	}

	private boolean validarFormulario() {
		if (ventana.gettDescripcion().getText().isEmpty()) {
			ventana.gettDescripcion().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			ventana.gettDescripcion().requestFocus();
			return false;
		}
		return true;
	}

	private void agregarProceso(String nombre) {
		if (sector == null) {
			return;
		}
//		proceso = new SectorProceso();
//		proceso.setEsRepetible(true);
//		proceso.setColaborador(Sesion.getInstance().getColaborador());
//		proceso.setSector(sector);
//		proceso.setNombreProceso(nombre);
//
//		procesos.add(proceso);
//		modeloTablaProceso.setProcesos(procesos);
//		modeloTablaProceso.fireTableDataChanged();

		guardar();

	}

	private void pedirNombreProceso() {
		String nombre = JOptionPane.showInputDialog("Descripcion del proceso de produccion.");

		try {
			while (nombre.isEmpty()) {
				nombre = JOptionPane.showInputDialog("Corrija la informacion.");
				if (nombre == null) {
					return;
				}
			}
		} catch (Exception e) {
			EventosUtil.formatException(e);
			return;
		}
		agregarProceso(nombre.toUpperCase());
	}

	private void quitarProceso(int row) {
		if (row < 0) {
			return;
		}

		int opcion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el proceso?", "Error",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
//			procesos.remove(row);
//			modeloTablaProceso.setProcesos(procesos);
//			modeloTablaProceso.fireTableDataChanged();
			guardar();
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
		JMenuItem imprimirItem = new JMenuItem("Eliminar proceso");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarProceso(row);

			}
		});
		popup.add(imprimirItem);
		return popup;
	}

	@Override
	public void nuevo() {
		accion = "NUEVO";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettDescripcion().requestFocus();
	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettDescripcion().requestFocus();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			ventana.getlMensaje().setForeground(Color.red);
			ventana.getlMensaje().setText("VERIFIQUE LA INFORMACION NUEVAMENTE");
			return;
		}
		if (accion.equals("NUEVO")) {
			sector = new Sector();
			sector.setColaborador(Sesion.getInstance().getColaborador());
		}
		sector.setDescripcion(ventana.gettDescripcion().getText());

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(sector);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(sector);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void cancelar() {
		estadoInicial(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void setSector(Sector sector) {
		this.sector = sector;

		gestionarSector();
	}

	private void gestionarSector() {
		if (sector == null) {
			return;
		}

		ventana.gettDescripcion().setText(sector.toString());
		ventana.getlColaborador().setText("No hay informacion.");
		ventana.getlFechaRegistro().setText(EventosUtil.formatoFecha(sector.getFechaRegistro()));
		ventana.getlID().setText(sector.getId() + "");
		ventana.getlEstado().setText(sector.isEstado() ? "Vigente" : "Anulado");

		colaboradoresDelSector = sector.getColaboradoresDelSector();
		modeloTablaColaborador.setLista(colaboradoresDelSector);
		modeloTablaColaborador.fireTableDataChanged();

//		procesos = sector.getProcesos();
//		modeloTablaProceso.setProcesos(procesos);
//		modeloTablaProceso.fireTableDataChanged();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Agregar":
			pedirNombreProceso();
			break;

		default:
			break;
		}

	}
}
