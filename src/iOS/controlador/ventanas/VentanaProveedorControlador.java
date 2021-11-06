package iOS.controlador.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ProveedorDao;
import iOS.modelo.entidades.EntidadBancaria;
import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.entidades.Proveedor;
import iOS.modelo.entidades.ProveedorContactos;
import iOS.modelo.entidades.ProveedorCorreos;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.BancoInterface;
import iOS.modelo.interfaces.ProveedorInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaProveedorContacto;
import iOS.vista.modelotabla.ModeloTablaProveedorCorreo;
import iOS.vista.modelotabla.ModeloTablaProveedorCuenta;
import iOS.vista.ventanas.VentanaProveedor;
import iOS.vista.ventanas.buscadores.BuscadorBanco;

public class VentanaProveedorControlador implements AccionesABM, ActionListener, MouseListener, KeyListener, ProveedorInterface, BancoInterface{
	private VentanaProveedor ventana;
	private ProveedorDao dao;
	private Proveedor proveedor;

	private String accion;

	private ProveedorContactos contacto;
	private ProveedorCorreos correo;
	private InformacionPago informacionPago;

	private List<ProveedorContactos> contactos = new ArrayList<ProveedorContactos>();
	private List<ProveedorCorreos> correos = new ArrayList<ProveedorCorreos>();
	private List<InformacionPago> informacionesPagos = new ArrayList<InformacionPago>();


	private ModeloTablaProveedorContacto mtContacto;
	private ModeloTablaProveedorCorreo mtCorreo;
	private ModeloTablaProveedorCuenta mtInformacionPago;
	private EntidadBancaria banco;

	public VentanaProveedorControlador(VentanaProveedor ventana) {
		this.ventana = ventana;
		this.ventana.getMiToolBar().setAcciones(this);

		this.mtContacto = new ModeloTablaProveedorContacto();
		this.mtCorreo = new ModeloTablaProveedorCorreo();
		this.mtInformacionPago = new ModeloTablaProveedorCuenta();

		this.ventana.getTableContactos().setModel(mtContacto);
		this.ventana.getTableCorreos().setModel(mtCorreo);
		this.ventana.getTableInformacionPago().setModel(mtInformacionPago);
		dao = new ProveedorDao();
		nuevo();
		estadoInicial(true);
		setUpEvents();
	}

	private void setUpEvents() {
		//MOUSE LISTENER
		ventana.getTableContactos().addMouseListener(this);
		ventana.getTableCorreos().addMouseListener(this);
		ventana.getTableInformacionPago().addMouseListener(this);
		ventana.getlContactos().addMouseListener(this);
		ventana.getlCorreos().addMouseListener(this);
		ventana.getlInformacionPago().addMouseListener(this);

		//KEY LISTENER
		ventana.getTableContactos().addKeyListener(this);
		ventana.getTableCorreos().addKeyListener(this);
		ventana.getTableInformacionPago().addKeyListener(this);

	}

	private void estadoInicial(boolean b) {
		//LABEL DE MENSAJES EMPIEZA OBLIGATORIAMENTE CON AMARILLO
		


		//LIMPIAR JTEXTFIELD
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreCompleto());


		//BLOQUEAR JTEXTFIELD
		EventosUtil.estadosCampoPersonalizado(ventana.gettDireccion(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettIdentificacion(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.gettNombreCompleto(), b);


		//BLOQUEAR BOTONES
		//SI ACCION NUEVO
		if (accion.equals("NUEVO")) {
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtModificar(), !b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtEliminar(), !b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtCancelar(), b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtGuardar(), b);
		}

		//SI ACCION MODIFICAR
		if (accion.equals("MODIFICAR")) {
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtModificar(), b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtEliminar(), b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtCancelar(), !b);
			EventosUtil.estadosBotones(ventana.getMiToolBar().getbtGuardar(), !b);
		}

		//BLOQUEAR TABLAS
		EventosUtil.estadosCampoPersonalizado(ventana.getTableContactos(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.getTableCorreos(), b);
		EventosUtil.estadosCampoPersonalizado(ventana.getTableInformacionPago(), b);

		//INICIAR TABLAS COMO VACIAS
		contactos = new ArrayList<>();
		correos = new ArrayList<>();
		informacionesPagos = new ArrayList<>();
		mtContacto.setContacto(contactos);
		mtContacto.fireTableDataChanged();
		mtCorreo.setCorreo(correos);
		mtCorreo.fireTableDataChanged();
		mtInformacionPago.setInformacion(informacionesPagos);
		mtInformacionPago.fireTableDataChanged();

	}

	private boolean validarFormulario(){
		if (ventana.gettNombreCompleto().getText().isEmpty()) {
			ventana.gettNombreCompleto().setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			ventana.gettNombreCompleto().requestFocus();
			return false;
		}

		if (ventana.gettIdentificacion().getText().isEmpty()) {
			ventana.gettIdentificacion().setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			ventana.gettIdentificacion().requestFocus();
			return false;
		}

		if (!validarIdentificacion()) {
			ventana.gettIdentificacion().setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			ventana.gettIdentificacion().requestFocus();
			return false;
		}

		if (!validarInformacionBancaria()) {
			return false;
		}

		return true;
	}

	private boolean validarIdentificacion() {
		if (ventana.gettIdentificacion().getText().equals("\\s")){
			return false;
		}
		if (ventana.gettIdentificacion().getText().equals("0+")) {
			return false;
		}
		return true;
	}

	private boolean validarInformacionBancaria() {
		for (int i = 0; i < informacionesPagos.size(); i++) {
			try {
				if (informacionesPagos.get(i).getNombreCuenta().isEmpty()) {
					ventana.getlMensaje().setForeground(Color.red);
					ventana.getlMensaje().setText("VERIFIQUE NOMBRE DE LA CUENTA BANCARIA");	
					return false;
				}
			} catch (Exception e) {
				ventana.getlMensaje().setForeground(Color.red);
				ventana.getlMensaje().setText("VERIFIQUE NOMBRE DE LA CUENTA BANCARIA");	
				return false;
			}

			try {
				if (informacionesPagos.get(i).getNumeroCuenta().isEmpty()) {
					ventana.getlMensaje().setForeground(Color.red);
					ventana.getlMensaje().setText("VERIFIQUE NUMERO DE LA CUENTA BANCARIA");	
					return false;
				}
			} catch (Exception e) {
				ventana.getlMensaje().setForeground(Color.red);
				ventana.getlMensaje().setText("VERIFIQUE NUMERO DE LA CUENTA BANCARIA");	
				return false;
			}

		}
		return true;
	}

	private void pedirContacto() {
		String telefono = JOptionPane.showInputDialog("Introduzca el número");	

		try {
			while (telefono.isEmpty()) {
				telefono = JOptionPane.showInputDialog("Corrija el numero");
				if (telefono == null) {
					return;
				}
			}
		} catch (Exception e) {
			EventosUtil.formatException(e);
			return;
		}

		agregarContacto(telefono);
	}
	private void agregarContacto(String telefono) {
		contacto = new ProveedorContactos();
		contacto.setColaborador(Sesion.getInstance().getColaborador());
		contacto.setNumeroTelefono(telefono);
		contactos.add(contacto);
		mtContacto.setContacto(contactos);
		mtContacto.fireTableDataChanged();
	}

	private void pedirCorreo(){
		String correo = JOptionPane.showInputDialog("INTRODUZCA EL CORREO");

		Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

		if (correo == null) {
			return;
		}

		if (correo.length() == 0) {
			return;
		}

		Matcher mather = pattern.matcher(correo);


		try {
			while (mather.find() == false) {
				correo = JOptionPane.showInputDialog("CORREO NO VALIDO");	
				mather = pattern.matcher(correo);
				if (mather.find() == true) {
					agregarCorreo(correo);
					return;
				}
			} 
		} catch (Exception e) {
			EventosUtil.formatException(e);
			return;
		}

		agregarCorreo(correo);

	}
	private void agregarCorreo(String email) {		
		correo = new ProveedorCorreos();
		correo.setColaborador(Sesion.getInstance().getColaborador());
		correo.setCorreoElectronico(email);
		correos.add(correo);
		mtCorreo.setCorreo(correos);
		mtContacto.fireTableDataChanged();
	}


	private void pedirInformacionPago(){
		abrirBuscadorBanco();
	}

	@Override
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
		gestionarProveedor();
	}

	private void gestionarProveedor() {
		estadoInicial(false);
		accion = "MODIFICAR";

		//Cargar campos de texto
		ventana.gettDireccion().setText(proveedor.getDireccion());
		ventana.gettIdentificacion().setText(proveedor.getIdentificacion());
		ventana.gettNombreCompleto().setText(proveedor.getNombreCompleto());

		//Cargar listas
		mtContacto.setContacto(proveedor.getListaContactos());
		mtCorreo.setCorreo(proveedor.getListaCorreos());
		mtInformacionPago.setInformacion(proveedor.getInformacionesPago());
		mtContacto.fireTableDataChanged();
		mtCorreo.fireTableDataChanged();
		mtInformacionPago.fireTableDataChanged();
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
		if (e.getSource() == ventana.getlContactos()) {
			pedirContacto();
		}

		if (e.getSource() == ventana.getlCorreos()) {
			pedirCorreo();
		}

		if (e.getSource() == ventana.getlInformacionPago()) {
			pedirInformacionPago();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == ventana.getlContactos()) {
			Font font = ventana.getlContactos().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			ventana.getlContactos().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == ventana.getlCorreos()) {
			Font font = ventana.getlCorreos().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			ventana.getlCorreos().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == ventana.getlInformacionPago()) {
			Font font = ventana.getlInformacionPago().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			ventana.getlInformacionPago().setFont(font.deriveFont(attributes));
		}

	}


	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == ventana.getlContactos()) {
			Font font = ventana.getlContactos().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, -1);
			ventana.getlContactos().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == ventana.getlCorreos()) {
			Font font = ventana.getlCorreos().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, -1);
			ventana.getlCorreos().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == ventana.getlInformacionPago()) {
			Font font = ventana.getlInformacionPago().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, -1);
			ventana.getlInformacionPago().setFont(font.deriveFont(attributes));
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {

	}


	@Override
	public void nuevo() {
		accion = "NUEVO";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion+" REGISTRO");
		ventana.gettNombreCompleto().requestFocus();

	}


	@Override
	public void modificar() {
		accion = "MODIFICAR";
		estadoInicial(true);
		ventana.getlMensaje().setText(accion+" REGISTRO");
		ventana.gettNombreCompleto().requestFocus();

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
			proveedor = new Proveedor();
			proveedor.setColaborador(Sesion.getInstance().getColaborador());
		}

		proveedor.setNombreCompleto(ventana.gettNombreCompleto().getText());
		proveedor.setDireccion(ventana.gettDireccion().getText());
		proveedor.setIdentificacion(ventana.gettIdentificacion().getText());

		for (int i = 0; i < contactos.size(); i++) {
			contactos.get(i).setProveedor(proveedor);
		}

		for (int i = 0; i < correos.size(); i++) {
			correos.get(i).setProveedor(proveedor);
		}

		for (int i = 0; i < informacionesPagos.size(); i++) {
			informacionesPagos.get(i).setProveedor(proveedor);
		}
		proveedor.setListaContactos(contactos);
		proveedor.setListaCorreos(correos);
		proveedor.setInformacionesPago(informacionesPagos);

		ventana.getlMensaje().setForeground(Color.yellow);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(proveedor);
				ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(proveedor);
				ventana.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
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
		estadoInicial(true);

	}

	@Override
	public void setBanco(EntidadBancaria banco) {
		this.banco = banco;

		gestionarBanco();

	}

	private void gestionarBanco() {
		informacionPago = new InformacionPago();
		informacionPago.setEntidadBancaria(banco);
		informacionesPagos.add(informacionPago);
		mtInformacionPago.setInformacion(informacionesPagos);
		mtInformacionPago.fireTableDataChanged();
	}

	private void abrirBuscadorBanco() {
		BuscadorBanco buscador = new BuscadorBanco();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);

	}
}
