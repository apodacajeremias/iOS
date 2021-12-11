package iOS.controlador.ventanas;

import java.awt.Color;

import javax.swing.JOptionPane;

import org.hibernate.exception.ConstraintViolationException;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaCliente;

public class VentanaClienteControlador implements AccionesABM, ClienteInterface {
	private VentanaCliente ventanaCliente;
	private ClienteDao dao;
	private Cliente cliente;
	private String accion;

	private ClienteInterface interfaz;

	public void setInterfaz(ClienteInterface interfaz) {
		this.interfaz = interfaz;
	}

	public VentanaClienteControlador(VentanaCliente ventanaCliente) {
		this.ventanaCliente = ventanaCliente;
		this.ventanaCliente.getMiToolBar().setAcciones(this);
		dao = new ClienteDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER

		// MOUSE LISTENER

		// KEY LISTENER

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosCampoPersonalizado(ventanaCliente.gettContacto(), b);
		EventosUtil.estadosCampoPersonalizado(ventanaCliente.gettDireccion(), b);
		EventosUtil.estadosCampoPersonalizado(ventanaCliente.gettIdentificacion(), b);
		EventosUtil.estadosCampoPersonalizado(ventanaCliente.gettNombreCompleto(), b);

		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtNuevo(), b);
		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtSalir(), b);

		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtModificar(), b);
		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtEliminar(), b);
		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtCancelar(), b);
		EventosUtil.estadosBotones(ventanaCliente.getMiToolBar().getbtGuardar(), b);

		EventosUtil.limpiarCampoPersonalizado(ventanaCliente.gettContacto());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente.gettDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente.gettIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente.gettNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente.getlMensaje());

		accion = null;
	}

	private boolean validarFormulario() {
		if (ventanaCliente.gettNombreCompleto().getText().isEmpty()) {
			ventanaCliente.getlMensaje().setText("El nombre está vacio");
			ventanaCliente.getlMensaje().setForeground(Color.RED);
			ventanaCliente.gettNombreCompleto().requestFocus();
			return false;
		}

		if (ventanaCliente.gettIdentificacion().getText().equals("0")
				|| ventanaCliente.gettIdentificacion().getText().equals("00")
				|| ventanaCliente.gettIdentificacion().getText().equals("000")
				|| ventanaCliente.gettIdentificacion().getText().equals("0000")
				|| ventanaCliente.gettIdentificacion().getText().equals("00000")
				|| ventanaCliente.gettIdentificacion().getText().equals("000000")
				|| ventanaCliente.gettIdentificacion().getText().equals("0000000")
				|| ventanaCliente.gettIdentificacion().getText().equals("00000000")
				|| ventanaCliente.gettIdentificacion().getText().equals("000000000")
				|| ventanaCliente.gettIdentificacion().getText().equals("0000000000")) {

			ventanaCliente.getlMensaje().setText("Los datos CÉDULA/RUC están incorrectos");
			ventanaCliente.getlMensaje().setForeground(Color.RED);
			ventanaCliente.gettIdentificacion().requestFocus();
			return false;
		}
		if (ventanaCliente.gettContacto().getText().equals("0") || ventanaCliente.gettContacto().getText().equals("00")
				|| ventanaCliente.gettContacto().getText().equals("000")
				|| ventanaCliente.gettContacto().getText().equals("0000")
				|| ventanaCliente.gettContacto().getText().equals("00000")
				|| ventanaCliente.gettContacto().getText().equals("000000")
				|| ventanaCliente.gettContacto().getText().equals("0000000")
				|| ventanaCliente.gettContacto().getText().equals("00000000")
				|| ventanaCliente.gettContacto().getText().equals("000000000")
				|| ventanaCliente.gettContacto().getText().equals("0000000000")) {

			ventanaCliente.getlMensaje().setForeground(Color.RED);
			ventanaCliente.getlMensaje().setText("El contacto es incorrecto");
			ventanaCliente.gettIdentificacion().requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		cliente = null;
		ventanaCliente.getlMensaje().setText(accion + " REGISTRO");
		ventanaCliente.gettNombreCompleto().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventanaCliente.getlMensaje().setText(accion + " REGISTRO");
	}

	@Override
	public void eliminar() {
		accion = "ELIMINAR";

		ventanaCliente.getlMensaje().setText(accion + " REGISTRO");
		ventanaCliente.getlMensaje().setForeground(Color.RED);
		int respuesta = JOptionPane.showConfirmDialog(null, "La eliminación del cliente " + cliente.getNombreCompleto()
				+ " conlleva la pérdida permanente del registro", "ATENCION", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				dao.eliminar(cliente);
				dao.commit();
				ventanaCliente.getlMensaje().setText("REGISTRO ELIMINADO");
				estadoInicial(true);
			} catch (Exception e) {
				if (e.getCause().getClass() == ConstraintViolationException.class) {
					JOptionPane.showMessageDialog(null, "NO ES POSIBLE ELIMINAR");
				}
				dao.rollBack();
				e.printStackTrace();
			}
		}

	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			cliente = new Cliente();
			cliente.setColaborador(Sesion.getInstance().getColaborador());
		}

		cliente.setNombreCompleto(ventanaCliente.gettNombreCompleto().getText());
		cliente.setContacto(ventanaCliente.gettContacto().getText());
		cliente.setDireccion(ventanaCliente.gettDireccion().getText());
		if (ventanaCliente.gettIdentificacion().getText().isEmpty()) {
			cliente.setIdentificacion(ventanaCliente.gettIdentificacion().getText());
		}
		// Si esta vacio el campo, se pasa como nulo
		switch (ventanaCliente.gettIdentificacion().getText()) {
		case "":
			cliente.setIdentificacion(null);
			break;
		default:
			cliente.setIdentificacion(ventanaCliente.gettIdentificacion().getText());
		}

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(cliente);
			} else {
				dao.modificar(cliente);
			}
			dao.commit();
			try {
				interfaz.setCliente(cliente);
				ventanaCliente.dispose();
			} catch (Exception e) {
				modificar();
				setCliente(cliente);
				ventanaCliente.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			}
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}

	}

	@Override
	public void cancelar() {
		estadoInicial(true);
	}

	public void salir() {
		ventanaCliente.dispose();
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if (cliente == null) {
			System.err.println("CLIENTE NULL");
			return;
		} else {
			System.out.println(cliente.getId() + " ID DEL CLIENTE");
		}
		ventanaCliente.gettNombreCompleto().setText(cliente.getNombreCompleto());
		ventanaCliente.gettIdentificacion().setText(cliente.getIdentificacion());
		ventanaCliente.gettContacto().setText(cliente.getContacto());
		ventanaCliente.gettDireccion().setText(cliente.getDireccion());
	}
}
