package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

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

	private List<Colaborador> colaboradoresDelSector = new ArrayList<Colaborador>();

	public VentanaSectorControlador(VentanaSector ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		modeloTablaColaborador = new ModeloTablaColaborador();
		ventana.getTableColaboradores().setModel(modeloTablaColaborador);

		dao = new SectorDao();
		nuevo();
		setUpEvents();
	}

	private void setUpEvents() {
		// MOUSE LISTENER
		ventana.getTableColaboradores().addMouseListener(this);
	}

	private void estadoInicial() {
		ventana.getlMensaje().setText(null);

		// LIMPIAR JTEXTFIELD
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDescripcion());
		vaciarFormularios();

	}

	private void vaciarFormularios() {
		colaboradoresDelSector = new ArrayList<Colaborador>();
		modeloTablaColaborador.setLista(colaboradoresDelSector);
	}

	private boolean validarFormulario() {
		if (ventana.gettDescripcion().getText().isEmpty()) {
			ventana.gettDescripcion().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			ventana.gettDescripcion().requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial();
		accion = "NUEVO";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettDescripcion().requestFocus();
	}

	@Override
	public void modificar() {
		estadoInicial();
		accion = "MODIFICAR";
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
			modificar();
			setSector(sector);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void cancelar() {
		estadoInicial();
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

		colaboradoresDelSector = sector.getColaboradoresDelSector();
		modeloTablaColaborador.setLista(colaboradoresDelSector);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		default:
			break;
		}

	}
}
