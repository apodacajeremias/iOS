package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.BancoDao;
import iOS.modelo.entidades.EntidadBancaria;
import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.BancoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaProveedorCuenta;
import iOS.vista.ventanas.VentanaBanco;

public class VentanaBancoControlador implements AccionesABM, ActionListener, BancoInterface {
	private VentanaBanco ventana;
	private BancoDao dao;
	private EntidadBancaria banco;

	private String accion;

	private List<InformacionPago> items = new ArrayList<InformacionPago>();
	private ModeloTablaProveedorCuenta mtProveedorCuenta;

	public VentanaBancoControlador(VentanaBanco ventanaBanco) {
		this.ventana = ventanaBanco;
		this.ventana.getMiToolBar().setAcciones(this);

		mtProveedorCuenta = new ModeloTablaProveedorCuenta();
		this.ventana.gettProveedorCuenta().setModel(mtProveedorCuenta);

		dao = new BancoDao();
		setUpEvents();
	}

	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreBanco());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNumeroAtencion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSitioWeb());

		accion = null;
		vaciarTabla();
	}

	private void vaciarTabla() {
		items = new ArrayList<InformacionPago>();
		mtProveedorCuenta.setInformacion(items);
	}

	private void setUpEvents() {
		// ACTION LISTENER

		// MOUSE LISTENER

		// KEY LISTENER

	}

	private boolean validarFormulario() {
		if (ventana.gettNombreBanco().getText().isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Nombre de la entidad bancaria es requerida.");
			return false;
		}
		if (!ventana.gettSitioWeb().getText().isEmpty()) {
			try {
				new URL(ventana.gettSitioWeb().getText()).toURI();
				return true;
			} catch (URISyntaxException exception) {
				JOptionPane.showMessageDialog(ventana, "URL de pagina web invalida, verifique.");
				return false;
			} catch (MalformedURLException exception) {
				JOptionPane.showMessageDialog(ventana, "URL de pagina web invalida, verifique.");
				return false;
			}
		}
		return true;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevo() {
		estadoInicial();
		accion = "NUEVO";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreBanco().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial();
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreBanco().requestFocus();

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

		banco.setNombreBanco(ventana.gettNombreBanco().getText());
		banco.setNumeroAtencion(ventana.gettNumeroAtencion().getText());
		banco.setPaginaWeb(ventana.gettSitioWeb().getText());
		banco.setInformacionesPago(null);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(banco);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(banco);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}
			dao.commit();
			modificar();
			setBanco(banco);
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}

	}

	@Override
	public void cancelar() {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		estadoInicial();

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

		ventana.gettNombreBanco().setText(banco.getNombreBanco());
		ventana.gettSitioWeb().setText(banco.getPaginaWeb());
		ventana.gettNumeroAtencion().setText(banco.getNumeroAtencion());

		try {
			items = banco.getInformacionesPago();
			mtProveedorCuenta.setInformacion(items);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
