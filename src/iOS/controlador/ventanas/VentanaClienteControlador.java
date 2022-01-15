package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.hibernate.exception.ConstraintViolationException;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Representante;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaRepresentante;
import iOS.vista.ventanas.VentanaCliente;
import iOS.vista.ventanas.buscadores.BuscadorCliente;

public class VentanaClienteControlador implements AccionesABM, ClienteInterface, ActionListener {
	private VentanaCliente ventana;
	private ModeloTablaRepresentante modeloTablaRepresentante;
	private ClienteDao dao;
	private Cliente cliente;
	private String accion;

	private ClienteInterface interfaz;

	private Representante representante;
	private List<Representante> representantes = new ArrayList<Representante>();

	public void setInterfaz(ClienteInterface interfaz) {
		this.interfaz = interfaz;
	}

	public VentanaClienteControlador(VentanaCliente ventanaCliente) {
		this.ventana = ventanaCliente;
		this.ventana.getMiToolBar().setAcciones(this);

		modeloTablaRepresentante = new ModeloTablaRepresentante();
		ventana.getListaRepresentantes().setModel(modeloTablaRepresentante);
		tableMenu(ventana.getListaRepresentantes());
		dao = new ClienteDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER
		ventana.getBtnBuscarRepresentante().addActionListener(this);
		// MOUSE LISTENER

		// KEY LISTENER

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosCampoPersonalizado(ventana.gettContacto(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettDireccion(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettIdentificacion(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettNombreCompleto(), b);

		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtNuevo(), b);
		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtSalir(), b);

		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtModificar(), b);
		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtEliminar(), b);
		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtCancelar(), b);
		EventosUtil.estadosBotones(ventana.getMiToolBar().getbtGuardar(), b);

		EventosUtil.limpiarCampoPersonalizado(ventana.gettContacto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());

		accion = null;
		vaciarListaRepresentantes();
	}

	private void vaciarListaRepresentantes() {
		representantes = new ArrayList<Representante>();
		modeloTablaRepresentante.setLista(representantes);
	}
	
	private void agregarRepresentante(Cliente r) {
		if (r == null) {
			return;
		}
		if (r.isEsB2B()) {
			JOptionPane.showMessageDialog(ventana, "El representante debe ser una persona fisica");
			return;
		}
		for (int i = 0; i < representantes.size(); i++) {
			if (representantes.get(i).getId() == r.getId()) {
				return;
			}
		}
		
		representante = new Representante();
		representante.setColaborador(Sesion.getInstance().getColaborador());
		representante.setRepresentante(r);
		representantes.add(representante);
		modeloTablaRepresentante.setLista(representantes);
	}

	private void quitarRepresentante(int posicion) {
		if (posicion < 0) {
			representante = null;
			return;
		}
		int opcion = JOptionPane.showConfirmDialog(null, "¿Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			try {
				representantes.remove(posicion);
				modeloTablaRepresentante.setLista(representantes);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private boolean validarFormulario() {
		if (!ventana.getRdB2B().isSelected() && !ventana.getRdB2C().isSelected()) {
			JOptionPane.showMessageDialog(ventana, "Debe indicar si es una persona fisica o juridica");
			return false;
		}
		if (ventana.getRdB2B().isSelected() && ventana.getRdB2C().isSelected()) {
			JOptionPane.showMessageDialog(ventana, "Debe indicar si es una persona fisica o juridica");
			return false;
		}
		if (ventana.gettNombreCompleto().getText().isEmpty()) {
			ventana.getlMensaje().setText("El nombre está vacio");
			ventana.getlMensaje().setForeground(Color.RED);
			ventana.gettNombreCompleto().requestFocus();
			return false;
		} else {
			String palabras[] = ventana.gettNombreCompleto().getText().split("\\s");
			if (palabras.length <= 1) {
				JOptionPane.showMessageDialog(ventana,
						"El nombre debe ser compuesto por: Primer Nombre + Nombre Paterno minimamente");
				return false;
			}
		}

		if (ventana.gettIdentificacion().getText().equals("0") || ventana.gettIdentificacion().getText().equals("00")
				|| ventana.gettIdentificacion().getText().equals("000")
				|| ventana.gettIdentificacion().getText().equals("0000")
				|| ventana.gettIdentificacion().getText().equals("00000")
				|| ventana.gettIdentificacion().getText().equals("000000")
				|| ventana.gettIdentificacion().getText().equals("0000000")
				|| ventana.gettIdentificacion().getText().equals("00000000")
				|| ventana.gettIdentificacion().getText().equals("000000000")
				|| ventana.gettIdentificacion().getText().equals("0000000000")) {

			ventana.getlMensaje().setText("Los datos CÉDULA/RUC están incorrectos");
			ventana.getlMensaje().setForeground(Color.RED);
			ventana.gettIdentificacion().requestFocus();
			return false;
		}
		if (ventana.gettContacto().getText().equals("0") || ventana.gettContacto().getText().equals("00")
				|| ventana.gettContacto().getText().equals("000") || ventana.gettContacto().getText().equals("0000")
				|| ventana.gettContacto().getText().equals("00000") || ventana.gettContacto().getText().equals("000000")
				|| ventana.gettContacto().getText().equals("0000000")
				|| ventana.gettContacto().getText().equals("00000000")
				|| ventana.gettContacto().getText().equals("000000000")
				|| ventana.gettContacto().getText().equals("0000000000")) {

			ventana.getlMensaje().setForeground(Color.RED);
			ventana.getlMensaje().setText("El contacto es incorrecto");
			ventana.gettIdentificacion().requestFocus();
			return false;
		}
		if (ventana.gettIdentificacion().getText().isEmpty() && ventana.gettContacto().getText().isEmpty()) {
			JOptionPane.showMessageDialog(ventana,
					"Debe existir un modo de identificar al cliente, por documento o por contacto");
			return false;
		}
		if (ventana.getRdB2B().isSelected() && ventana.gettIdentificacion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(ventana, "Si es una persona juridica, se debe indicar el RUC");
			return false;
		}
		if (ventana.getRdB2B().isSelected() && representantes.size() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Si es una persona juridica, debe tener representantes");
			return false;
		}
		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		cliente = null;
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreCompleto().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
	}

	@Override
	public void eliminar() {
		accion = "ELIMINAR";

		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.getlMensaje().setForeground(Color.RED);
		int respuesta = JOptionPane.showConfirmDialog(null, "La eliminación del cliente " + cliente.getNombreCompleto()
				+ " conlleva la pérdida permanente del registro", "ATENCION", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				dao.eliminar(cliente);
				dao.commit();
				ventana.getlMensaje().setText("REGISTRO ELIMINADO");
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
		if (ventana.getRdB2C().isSelected()) {
			agregarRepresentante(cliente);
		}

		cliente.setNombreCompleto(ventana.gettNombreCompleto().getText());
		cliente.setContacto(ventana.gettContacto().getText());
		cliente.setDireccion(ventana.gettDireccion().getText());
		cliente.setEsB2B(ventana.getRdB2B().isSelected());
		cliente.setEsB2C(ventana.getRdB2C().isSelected());
		if (ventana.gettIdentificacion().getText().isEmpty()) {
			cliente.setIdentificacion(ventana.gettIdentificacion().getText());
		}
		// Si esta vacio el campo, se pasa como nulo
		switch (ventana.gettIdentificacion().getText()) {
		case "":
			cliente.setIdentificacion(null);
			break;
		default:
			cliente.setIdentificacion(ventana.gettIdentificacion().getText());
		}

		for (int i = 0; i < representantes.size(); i++) {
			representantes.get(i).setCliente(cliente);
		}
		cliente.setRepresentantes(representantes);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(cliente);
			} else {
				dao.modificar(cliente);
			}
			dao.commit();
//			Metodos.getInstance().registrar(cliente, accion, cliente.registrar());
			try {
				interfaz.setCliente(cliente);
				ventana.dispose();
			} catch (Exception e) {
				modificar();
				setCliente(cliente);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
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
		ventana.dispose();
	}

	private void abrirBuscadorRepresentante() {
		BuscadorCliente ventana = new BuscadorCliente();
		ventana.setUpControlador();
		ventana.getControlador().setRepresentante(true);
		ventana.getControlador().setInterfaz(this);
		ventana.setVisible(true);
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
		ventana.gettNombreCompleto().setText(cliente.getNombreCompleto());
		ventana.gettIdentificacion().setText(cliente.getIdentificacion());
		ventana.gettContacto().setText(cliente.getContacto());
		ventana.gettDireccion().setText(cliente.getDireccion());
		ventana.getRdB2B().setSelected(cliente.isEsB2B());
		ventana.getRdB2C().setSelected(cliente.isEsB2C());

		representantes = cliente.getRepresentantes();
		modeloTablaRepresentante.setLista(representantes);
		ventana.getPanel().setVisible(cliente.isEsB2B());
	}

	@Override
	public void setRepresentante(Cliente r) {
		agregarRepresentante(r);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Buscar":
			abrirBuscadorRepresentante();
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
		JMenuItem quitarItem = new JMenuItem("Quitar representante");
		quitarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarRepresentante(row);

			}
		});
		popup.add(quitarItem);
		return popup;
	}
}
