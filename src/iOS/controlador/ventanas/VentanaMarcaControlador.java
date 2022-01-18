package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.MarcaDao;
import iOS.modelo.entidades.Marca;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MarcaInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaMarca;

public class VentanaMarcaControlador implements AccionesABM, ActionListener, MouseListener, KeyListener, MarcaInterface {
	private VentanaMarca ventanaMarca;
	private Marca marca;
	private MarcaDao dao;

	private String accion;

	public VentanaMarcaControlador(VentanaMarca ventanaMarca) {
		this.ventanaMarca = ventanaMarca;
		this.ventanaMarca.getMiToolBar().setAcciones(this);
		
		dao = new MarcaDao();
		nuevo();
		estadoInicial(true);
		setUpEvents();
	}

	private void setUpEvents() {
		ventanaMarca.gettNombreMarca().addKeyListener(this);

	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventanaMarca.gettNombreMarca());
		EventosUtil.limpiarCampoPersonalizado(ventanaMarca.getlMensaje());
		
		accion = null;
	}

	private boolean validarFormulario() {
		if (ventanaMarca.gettNombreMarca().getText().isEmpty()) {
			ventanaMarca.getlMensaje().setText("El nombre está vacio");
			ventanaMarca.getlMensaje().setForeground(Color.RED);
			ventanaMarca.gettNombreMarca().requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevo() {
		estadoInicial(false);
		accion = "NUEVO";
		ventanaMarca.getlMensaje().setText(accion + " REGISTRO");
		ventanaMarca.gettNombreMarca().requestFocus();
	}

	@Override
	public void modificar() {
		estadoInicial(false);
		accion = "MODIFICAR";
		ventanaMarca.getlMensaje().setText(accion + " REGISTRO");
		ventanaMarca.gettNombreMarca().requestFocus();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			marca = new Marca();
			marca.setColaborador(Sesion.getInstance().getColaborador());
		}
		marca.setDescripcion(ventanaMarca.gettNombreMarca().getText());
		
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(marca);
				ventanaMarca.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(marca);
				ventanaMarca.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
			modificar();
			setMarca(marca);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
		
		
	}

	@Override
	public void cancelar() {
		EventosUtil.limpiarCampoPersonalizado(ventanaMarca.getlMensaje());
		estadoInicial(true);
	}

	@Override
	public void setMarca(Marca marca) {
		this.marca = marca;
		gestionarMarca();
	}

	private void gestionarMarca() {
		ventanaMarca.gettNombreMarca().setText(marca.getDescripcion());
	}
}
