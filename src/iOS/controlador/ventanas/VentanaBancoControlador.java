package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.BancoDao;
import iOS.modelo.entidades.EntidadBancaria;
import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.BancoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaProveedorCuenta;
import iOS.vista.ventanas.VentanaBanco;

public class VentanaBancoControlador implements AccionesABM, ActionListener, MouseListener, KeyListener, BancoInterface{
	private VentanaBanco ventanaBanco;
	private BancoDao dao;
	private EntidadBancaria banco;

	private String accion;

	private List<InformacionPago> items = new ArrayList<InformacionPago>();
	private ModeloTablaProveedorCuenta mtProveedorCuenta;

	public VentanaBancoControlador(VentanaBanco ventanaBanco) {
		this.ventanaBanco = ventanaBanco;
		this.ventanaBanco.getMiToolBar().setAcciones(this);
		
		mtProveedorCuenta = new ModeloTablaProveedorCuenta();
		this.ventanaBanco.gettProveedorCuenta().setModel(mtProveedorCuenta);

		dao = new BancoDao();
		nuevo();
		estadoInicial(true);
		setUpEvents();

	

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosCampoPersonalizado(ventanaBanco.gettNombreBanco(), b);
		EventosUtil.limpiarCampoPersonalizado(ventanaBanco.gettNombreBanco());

		EventosUtil.estadosBotones(ventanaBanco.getMiToolBar().getbtModificar(), !b);
		EventosUtil.estadosBotones(ventanaBanco.getMiToolBar().getbtEliminar(), !b);
		EventosUtil.estadosBotones(ventanaBanco.getMiToolBar().getbtCancelar(), !b);
		EventosUtil.estadosBotones(ventanaBanco.getMiToolBar().getbtGuardar(), b);



	}

	private void setUpEvents() {
		//ACTION LISTENER
		

		//MOUSE LISTENER
		this.ventanaBanco.gettProveedorCuenta().addMouseListener(this);
		

		//KEY LISTENER
		this.ventanaBanco.gettNombreBanco().addKeyListener(this);
		this.ventanaBanco.gettProveedorCuenta().addKeyListener(this);

	}
	
	private boolean validarFormulario() {
		if (ventanaBanco.gettNombreBanco().getText().isEmpty()) {
			ventanaBanco.getlMensaje().setText("El nombre de la entidad bancaria está vacío");
			ventanaBanco.getlMensaje().setForeground(Color.RED);
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
		accion = "NUEVO";
		estadoInicial(false);
		ventanaBanco.getlMensaje().setText(accion + " REGISTRO");
		ventanaBanco.gettNombreBanco().requestFocus();
		
	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		estadoInicial(false);
		ventanaBanco.getlMensaje().setText(accion + " REGISTRO");

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
			banco = new EntidadBancaria();
			banco.setColaborador(Sesion.getInstance().getColaborador());
		}
		
		banco.setNombreBanco(ventanaBanco.gettNombreBanco().getText());
		banco.setInformacionesPago(null);
		
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(banco);
				ventanaBanco.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(banco);
				ventanaBanco.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
			estadoInicial(true);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

		
	}

	@Override
	public void cancelar() {
		EventosUtil.limpiarCampoPersonalizado(ventanaBanco.getlMensaje());
		estadoInicial(true);

	}

	@Override
	public void setBanco(EntidadBancaria banco) {
		this.banco = banco;
		gestionarBanco();

	}

	private void gestionarBanco() {
		if (banco == null) {
			return;
		}

		ventanaBanco.gettNombreBanco().setText(banco.getNombreBanco());
		items = dao.recuperarInformacionPagoPorBanco(banco.getId());

		mtProveedorCuenta.setInformacion(items);
		mtProveedorCuenta.fireTableDataChanged();

	}
}
