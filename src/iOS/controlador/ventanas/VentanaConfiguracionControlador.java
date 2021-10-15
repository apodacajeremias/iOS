package iOS.controlador.ventanas;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ConfiguracionDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Configuracion;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.ventanas.VentanaConfiguracion;

public class VentanaConfiguracionControlador implements ActionListener, ColaboradorInterface {
	private VentanaConfiguracion vConfiguracion;
	private String accion;
	public static String modulo = "CONFIGURACION";
	
	private ConfiguracionDao dao;
	private Configuracion configuracion;
	private Colaborador colaborador;

	public VentanaConfiguracionControlador(VentanaConfiguracion vConfiguracion) {
		this.vConfiguracion = vConfiguracion;
		dao = new ConfiguracionDao();
		recuperarConfiguracion();
		vConfiguracion.getBtGuardar().addActionListener(this);
		vConfiguracion.getBtCancelar().addActionListener(this);
		estadoInicial(true);
		setUpEvents();

	}

	private void recuperarConfiguracion() {
		configuracion = dao.recuperarUltimo();
		if (configuracion == null) {
			accion = "NUEVO";
		} else {
			accion = "MODIFICAR";
			vConfiguracion.gettNombreEmpresa().setText(configuracion.getEmpresa());
			vConfiguracion.gettContacto().setText(configuracion.getTelefono());
			vConfiguracion.gettContribuyente().setText(configuracion.getRegistroUnico());
			vConfiguracion.gettTitular().setText(configuracion.getTitular());
			vConfiguracion.gettRegistroP().setText(configuracion.getRegistroProfesional());
			vConfiguracion.gettCedula().setText(configuracion.getCedulaTitular());
			vConfiguracion.gettUbicacion().setText(configuracion.getUbicacion());
		}
	}

	// METODO QUE LEVANTA LOS EVENTOS
	private void setUpEvents() {
		vConfiguracion.getBtCancelar().addActionListener(this);
		vConfiguracion.getBtGuardar().addActionListener(this);

	}

	private boolean validarFormulario() {
		if (vConfiguracion.gettNombreEmpresa().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettNombreEmpresa().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettContacto().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettContacto().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettContribuyente().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettContribuyente().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettTitular().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettTitular().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettRegistroP().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettRegistroP().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettCedula().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettCedula().requestFocus();
			return false;
		}
		
		if (vConfiguracion.gettUbicacion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			vConfiguracion.gettUbicacion().requestFocus();
			return false;
		}
		return true;
	}

	// METODO VACIAR FORMULARIO
	private void vaciarFormulario() {
		EventosUtil.limpiarCampoPersonalizado(vConfiguracion.gettNombreEmpresa());
		EventosUtil.limpiarCampoPersonalizado(vConfiguracion.gettContacto());
		EventosUtil.limpiarCampoPersonalizado(vConfiguracion.gettContribuyente());
		EventosUtil.limpiarCampoPersonalizado(vConfiguracion.gettTitular());
		EventosUtil.limpiarCampoPersonalizado(vConfiguracion.gettRegistroP());

	}

	private void estadoInicial(boolean b) {
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettNombreEmpresa(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettContribuyente(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettContacto(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettTitular(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettRegistroP(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettCedula(), b);
		EventosUtil.estadosCampoPersonalizado(vConfiguracion.gettUbicacion(), b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Guardar":
			guardar();
			break;
		case "Cancelar":
			cancelar();
			break;
		default:
			break;
		}
	}

	private void guardar() {
		if (!validarFormulario()) {
			return;
		}
		if (accion.equals("NUEVO")) {
			configuracion = new Configuracion();
			configuracion.setColaborador(colaborador);
			
		}
		configuracion.setTitular(vConfiguracion.gettTitular().getText());
		configuracion.setRegistroProfesional(vConfiguracion.gettRegistroP().getText());
		configuracion.setEmpresa(vConfiguracion.gettNombreEmpresa().getText());
		configuracion.setTelefono(vConfiguracion.gettContacto().getText());
		configuracion.setRegistroUnico(vConfiguracion.gettContribuyente().getText());
		configuracion.setCedulaTitular(vConfiguracion.gettCedula().getText());
		configuracion.setUbicacion(vConfiguracion.gettUbicacion().getText());

		dao = new ConfiguracionDao();
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(configuracion);
				dao.commit();
				vConfiguracion.dispose();
				vConfiguracion.getLblAccion().setText("Guardado");
			} else {
				dao.modificar(configuracion);
				dao.commit();
				vConfiguracion.dispose();
				vConfiguracion.getLblAccion().setText("Actualizado");
			}
			
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
		
	}

	private void cancelar() {
		vaciarFormulario();
		vConfiguracion.dispose();
	}
	
	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		gestionarColaborador();
	}
	
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}
	}

}
