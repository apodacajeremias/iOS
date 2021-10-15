package iOS.controlador.ventanas.transacciones;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.RolDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Operacion;
import iOS.modelo.entidades.Rol;
import iOS.modelo.entidades.RolOperacion;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.OperacionInterface;
import iOS.modelo.interfaces.RolInterface;
import iOS.vista.modelotabla.ModeloTablaRolOperacion;
import iOS.vista.ventanas.buscadores.BuscadorOperacion;
import iOS.vista.ventanas.transacciones.TransaccionRol;

public class TransaccionRolControlador implements KeyListener, MouseListener, AccionesABM, OperacionInterface, RolInterface, ColaboradorInterface {
	private TransaccionRol transaccion;
	private ModeloTablaRolOperacion modeloTabla;
	private RolDao dao;
	private Rol rol;
	private RolOperacion rolOperacion;

	private String accion;
	private List<RolOperacion> rolesOperaciones = new ArrayList<>();
	private Operacion operacion;
	private Colaborador colaborador;
	public TransaccionRolControlador(TransaccionRol transaccion) {
		this.transaccion = transaccion;
		this.transaccion.getMiToolBar().setAcciones(this);
		modeloTabla = new ModeloTablaRolOperacion();
		this.transaccion.getTableOperaciones().setModel(modeloTabla);

		dao = new RolDao();

		setUpEvents();
		estadoInicial(true);
		nuevo();
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		transaccion.getTableOperaciones().addMouseListener(this);

		transaccion.getlOperacion().addMouseListener(this);
	}

	private void estadoInicial(boolean b) {
		//		EventosUtil.limpiarCampoPersonalizado(transaccion.getPanelFormulario());

	}

	private boolean validarFormulario(){
		if (transaccion.gettNombreCompleto().getText().isEmpty()) {
			transaccion.gettNombreCompleto().error();
			transaccion.gettNombreCompleto().requestFocus();
			return false;
		}
		if (rolesOperaciones.size() <= 0) {
			transaccion.getlOperacion().requestFocus();
			return false;
		}

		return true;
	}

	@Override
	public void nuevo() {
		accion = "NUEVO";
	}

	@Override
	public void modificar() {
		accion = "MODIFICAR";
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			transaccion.getlMensaje().setText("VERIFIQUE LA INFORMACION NUEVAMENTE");			
			return;
		}

		if (accion.equals("NUEVO")) {
			rol = new Rol();	
			rol.setColaborador(colaborador);
		}

		rol.setNombreRol(transaccion.gettNombreCompleto().getText());
		rol.setRolesOperaciones(rolesOperaciones);
		
		for (int i = 0; i < rolesOperaciones.size(); i++) {
			rolesOperaciones.get(i).setRol(rol);
		}

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(rol);
				dao.commit();
				estadoInicial(true);
				transaccion.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
			} else {
				dao.modificar(rol);
				dao.commit();
				estadoInicial(true);
				transaccion.getlMensaje().setText("REGISTRO MODIFICADO CON ÉXITO");
			}


		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		if (e.getSource() == transaccion.getlOperacion()) {
			abrirBuscadorOperacion();
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
		if (e.getSource() == transaccion.getlOperacion()) {
			Font font = transaccion.getlOperacion().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			transaccion.getlOperacion().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == transaccion.getlOperacion()) {
			Font font = transaccion.getlOperacion().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			transaccion.getlOperacion().setFont(font.deriveFont(attributes));
		}

		if (e.getSource() == transaccion.getlOperacion()) {
			Font font = transaccion.getlOperacion().getFont();
			HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			transaccion.getlOperacion().setFont(font.deriveFont(attributes));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void abrirBuscadorOperacion() {
		BuscadorOperacion buscador = new BuscadorOperacion();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}


	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;

		gestionarColaborador();
	}

	public void gestionarColaborador() {
		if(colaborador == null) {
			return;
		}
	}

	@Override
	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;

		gestionarOperacion();
	}

	private void gestionarOperacion() {
		rolOperacion = new RolOperacion();
		
		for (int i = 0; i < rolesOperaciones.size(); i++) {
			if (rolesOperaciones.get(i).getOperacion().getId() == operacion.getId()) {
				return;
			}
		}
		
		rolOperacion.setOperacion(operacion);
		rolesOperaciones.add(rolOperacion);
		modeloTabla.setOperaciones(rolesOperaciones);
		modeloTabla.fireTableDataChanged();

	}

	@Override
	public void setRol(Rol rol) {
		this.rol = rol;
		gestionarRol();
	}

	private void gestionarRol() {
		if (rol == null) {
			return;
		}
		rolesOperaciones = rol.getRolesOperaciones();
		transaccion.gettNombreCompleto().setText(rol.getNombreRol());
		modeloTabla.setOperaciones(rol.getRolesOperaciones());
		modeloTabla.fireTableDataChanged();

		accion = "MODIFICAR";
	}



}
