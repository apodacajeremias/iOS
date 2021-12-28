package iOS.controlador.ventanas.reportes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaCliente;
import iOS.vista.ventanas.VentanaCliente2;
import iOS.vista.ventanas.reportes.ReporteCliente;

public class ReporteClienteControlador implements ActionListener, MouseListener {
	private ReporteCliente reporte;
	private ModeloTablaCliente modeloTabla;
	private ClienteDao dao;

	private Cliente cliente;
	private List<Cliente> clientes = new ArrayList<Cliente>();

	public ReporteClienteControlador(ReporteCliente reporte) {
		this.reporte = reporte;
		modeloTabla = new ModeloTablaCliente();
		reporte.getTable().setModel(modeloTabla);

		dao = new ClienteDao();
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
		reporte.getPanelEspecifico().setEnabled(false);
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			reporte.getPanelEspecifico().setEnabled(true);
		}

		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelEspecifico().getlInfo2());
		EventosUtil.limpiarCampoPersonalizado(reporte.getPanelGeneral().getlInfo2());
	}

	private void vaciarTabla() {
		clientes = new ArrayList<Cliente>();
		modeloTabla.setLista(clientes);
		modeloTabla.fireTableDataChanged();
	}

	private void filtrar() {
		vaciarTabla();
		
		clientes = dao.recuperarTodoOrdenadoPorNombre();

		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			clientes = clientes.stream()
					.filter(c -> c.getDiferencia() > 0)
					.collect(Collectors.toList());

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			clientes = clientes.stream()
					.filter(c -> c.getDiferencia() <= 0)
					.collect(Collectors.toList());

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {
			//mantiene todos los registros

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			Colaborador cc = (Colaborador) reporte.getPanelEspecifico().getCbColaborador().getSelectedItem();
			clientes = clientes.stream()
					.filter(c -> c.getColaborador().getId() == cc.getId())
					.collect(Collectors.toList());
		} else {

		}

		// estado == true
		if (reporte.getPanelGeneral().getRdActivo().isSelected()) {
			clientes = clientes.stream().filter(x -> x.isEstado() == true).collect(Collectors.toList());
		} else if (reporte.getPanelGeneral().getRdInactivo().isSelected()) {
			clientes = clientes.stream().filter(x -> x.isEstado() == false).collect(Collectors.toList());
		} else {

		}

		// Caja abierta
		

		reporte.getPanelEspecifico().getlInfo2().setText("Se han encontrado " + clientes.size() + " registros");
		reporte.getPanelGeneral().getlInfo2().setText("Se han encontrado " + clientes.size() + " registros");

		modeloTabla.setLista(clientes);
		modeloTabla.fireTableDataChanged();
	}

	private void imprimir() {
		String tipoReporte = "";
		String claseReporte = "";
		
		if (reporte.getPanelGeneral().getRdTodo().isSelected()) {
			tipoReporte = "CLIENTE CON DEUDA MAYOR A 0 (CERO)";

		} else if (reporte.getPanelGeneral().getRdAlgunos().isSelected()) {
			tipoReporte = "CLIENTE CON DEUDA MENOR O IGUAL A 0 (CERO)";

		} else if (reporte.getPanelEspecifico().getRdTodoColaborador().isSelected()) {
			tipoReporte = "TODOS LOS CLIENTES";

		} else if (reporte.getPanelEspecifico().getRdColaboradorEspecifico().isSelected()) {
			tipoReporte = "CLIENTE ESPECIFICO";
		} else {

		}

		claseReporte = "REPORTE GENERAL SEGUN FILTROS";
		Metodos.getInstance().imprimirReporteCliente(clientes, tipoReporte, claseReporte);

	}

	private void seleccionarRegistro(int posicion) {
		if (posicion < 0) {
			return;
		}
		cliente = clientes.get(posicion);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == reporte.getTable() && e.getClickCount() == 1) {
			seleccionarRegistro(reporte.getTable().getSelectedRow());
		}

		if (e.getSource() == reporte.getTable() && e.getClickCount() == 2) {
			abrirVentanaCliente();
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

	private void abrirVentanaCliente() {
		if (cliente == null) {
			return;
		}
		VentanaCliente2 ventana = new VentanaCliente2();
		ventana.getControlador().setCliente(cliente);
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
}