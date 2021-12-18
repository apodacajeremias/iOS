package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Rol;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaColaborador;

public class VentanaColaboradorControlador
		implements AccionesABM, ActionListener, MouseListener, KeyListener, ColaboradorInterface {
	private VentanaColaborador ventana;

	private String accion;

	private Colaborador colaborador;
	private ColaboradorDao dao;

	private List<Sector> sectores = new ArrayList<>();
	private List<Rol> roles = new ArrayList<>();

	public VentanaColaboradorControlador(VentanaColaborador ventana) {
		this.ventana = ventana;
		ventana.getMiToolBar().setAcciones(this);

		dao = new ColaboradorDao();

		setUpEvents();
		cargarRoles();
		recuperarSectores();
	}

	private void setUpEvents() {
		// MOUSE CLICK
		this.ventana.getRdActivarAcceso().addMouseListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettContacto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettPassword());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettUsuario());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorSalario());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFechaDesvinculacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFechaVinculacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());

		accion = null;

	}

	private void cargarRoles() {
		roles = Sesion.getInstance().getRoles();
		ventana.getCbRol().addItem(null);
		for (int i = 0; i < roles.size(); i++) {
			ventana.getCbRol().addItem(roles.get(i));
		}

	}

	private void recuperarSectores() {
		sectores = Sesion.getInstance().getSectores();
		ventana.getCbSector().addItem(null);
		for (int i = 0; i < sectores.size(); i++) {
			ventana.getCbSector().addItem(sectores.get(i));
		}
	}

	private boolean validarFormulario() {
		if (ventana.gettIdentificacion().getText().isEmpty()) {
			ventana.gettIdentificacion().requestFocus();
			ventana.gettIdentificacion().avisar();

		}
		if (ventana.gettNombreCompleto().getText().isEmpty()) {
			ventana.gettNombreCompleto().requestFocus();
			ventana.gettNombreCompleto().avisar();

		}
		if (ventana.getRdActivarAcceso().isSelected() == true) {
			if (ventana.gettUsuario().getText().isEmpty()) {
				ventana.gettUsuario().requestFocus();
				ventana.gettUsuario().avisar();
				return false;
			}
			if (ventana.gettPassword().getPassword() == null) {
				ventana.gettPassword().requestFocus();
				return false;

			}
			if (ventana.getCbRol().getSelectedItem() == null || ventana.getCbRol().getSelectedIndex() < 0) {
				ventana.getCbRol().requestFocus();
				return false;
			}
		}
		if (ventana.getCbSector().getSelectedItem() == null || ventana.getCbSector().getSelectedIndex() < 0) {
			ventana.getCbSector().requestFocus();
			return false;
		}

		if (ventana.getCbTipoSalario().getSelectedItem() == null || ventana.getCbTipoSalario().getSelectedIndex() < 0) {
			ventana.getCbTipoSalario().requestFocus();
			return false;
		}
		if (ventana.gettValorSalario().getText().isEmpty() || ventana.gettValorSalario().getText().equals("0")) {
			ventana.gettValorSalario().requestFocus();
			ventana.gettValorSalario().error();
			return false;
		}
		return true;
	}

	private void sugerirUsuario() {
		if (ventana.gettNombreCompleto().getText().length() <= 0) {
			ventana.getRdActivarAcceso().setSelected(false);
			return;
		}
		EventosUtil.estadosCampoPersonalizado(ventana.getpAcceso(), true);
		String nombre = ventana.gettNombreCompleto().getText();

		String[] split = nombre.split("\\s");

		ArrayList<String> pal = new ArrayList<>();

		String colaborador = "";

		for (int i = 0; i < split.length; i++) {
			pal.add(split[i]);
			colaborador = colaborador + pal.get(i).charAt(0);
		}
		ventana.gettPassword().setText("12345");
		ventana.gettUsuario().setText(colaborador);
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
		if (e.getSource() == ventana.getRdActivarAcceso()) {
			sugerirUsuario();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == ventana.getRdActivarAcceso()) {
			sugerirUsuario();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ventana.getRdActivarAcceso()) {
			sugerirUsuario();
		}

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
		switch (e.getActionCommand()) {
		case "ActivarAcceso":
			sugerirUsuario();
			break;

		default:
			break;
		}

	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		colaborador = null;
		ventana.gettNombreCompleto().requestFocus();
		ventana.getlMensaje().setText(accion + " REGISTRO");
	}

	@Override
	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
		ventana.gettNombreCompleto().requestFocus();
		ventana.getlMensaje().setText(accion + " REGISTRO");

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
			colaborador = new Colaborador();
			colaborador.setColaboradorQueRegistra(Sesion.getInstance().getColaborador());
		}

		// Informacion personal
		colaborador.setContacto(ventana.gettContacto().getText());
		colaborador.setIdentificacion(ventana.gettIdentificacion().getText());
		colaborador.setNombreCompleto(ventana.gettNombreCompleto().getText());
		colaborador.setFueDesvinculado(false);

		// Informacion de acceso
		if (ventana.getRdActivarAcceso().isSelected()) {
			colaborador.setEsActivo(true);
			colaborador.setPassword(String.valueOf(ventana.gettPassword().getPassword()));
			colaborador.setUsuario(ventana.gettUsuario().getText());
			colaborador.setRol((Rol) ventana.getCbRol().getSelectedItem());
		} else {
			colaborador.setEsActivo(false);
			colaborador.setPassword(String.valueOf(ventana.gettPassword().getPassword()));
			colaborador.setUsuario(ventana.gettUsuario().getText());
			colaborador.setRol((Rol) ventana.getCbRol().getSelectedItem());
		}

		// Informacion en empresa
		colaborador.setEsOperador(!ventana.getRdEsEncargado().isSelected());
		colaborador.setFechaIngresoColaborador(new Date());
		colaborador.setSector((Sector) ventana.getCbSector().getSelectedItem());

		// Maneja desvinculacion
		if (ventana.getRdDesvinculado().isSelected() == true) {
			colaborador.setEsActivo(false);
			colaborador.setFueDesvinculado(true);
			colaborador.setFechaDesvinculacionColaborador(new Date());
		}

		// Informacion salarial
		colaborador.setSalario(ventana.gettValorSalario().getValue());
		colaborador.setTipoSalario(ventana.getCbTipoSalario().getSelectedItem().toString());

		try {
			dao = new ColaboradorDao();
			if (accion.equals("NUEVO")) {
				dao.insertar(colaborador);
			} else {
				dao.modificar(colaborador);
			}
			dao.commit();
			modificar();
			setColaborador(colaborador);
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}

	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;

		gestionarColaborador();
	}

	@Override
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}

		System.out.println(colaborador);

		ventana.gettContacto().setText(colaborador.getContacto());
		ventana.gettIdentificacion().setText(colaborador.getIdentificacion());
		ventana.gettNombreCompleto().setText(colaborador.getNombreCompleto());
		ventana.gettPassword().setText(null);
		ventana.gettUsuario().setText(colaborador.getUsuario());
		ventana.gettValorSalario().setValue(colaborador.getSalario());
		ventana.getlFechaDesvinculacion()
				.setText(colaborador.getFechaDesvinculacionColaborador() == null ? "Colaborador activo "
						: "Desvinculado en fecha "
								.concat(EventosUtil.formatoFecha(colaborador.getFechaDesvinculacionColaborador())));
		ventana.getlFechaVinculacion()
				.setText(colaborador.getFechaIngresoColaborador() == null ? "Sin informacion "
						: "Vinculado en fecha "
								.concat(EventosUtil.formatoFecha(colaborador.getFechaDesvinculacionColaborador())));
		ventana.getCbRol().getModel().setSelectedItem(colaborador.getRol());
		ventana.getCbSector().getModel().setSelectedItem(colaborador.getSector());
		ventana.getCbTipoSalario().setSelectedItem(colaborador.getTipoSalario());

		ventana.getRdActivarAcceso().setSelected(colaborador.isEsActivo());
		ventana.getRdDesvinculado().setSelected(!colaborador.isEstado());
		ventana.getRdEsEncargado().setSelected(!colaborador.isEsOperador());

		if (colaborador.isEsActivo()) {
			ventana.getpAcceso().setVisible(true);
		}
	}

}
