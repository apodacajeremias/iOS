package iOS.controlador.ventanas.principales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.principales.VentanaAcceso;
import iOS.vista.ventanas.principales.VentanaPrincipal;

public class VentanaAccesoControlador implements ActionListener {
	private VentanaAcceso ventana;
	private ColaboradorDao dao;
	private Colaborador colaborador;

	public VentanaAccesoControlador(VentanaAcceso ventana) {
		this.ventana = ventana;
		dao = new ColaboradorDao();
		setUpEvents();
	}

	private void setUpEvents() {
		ventana.getBtnIngresar().addActionListener(this);
	}
	
	public void comprobarAcceso() {
		String usuario = ventana.gettUsuario().getText();
		String password = String.valueOf(ventana.gettContra().getPassword());
		
		colaborador = dao.verificarAcceso(usuario, password);
		
		if (colaborador == null) {
			ventana.getlMensaje().setText("Verifique sus credenciales.");
			ventana.gettContra().setText(null);
			ventana.gettUsuario().requestFocus();
		} else {
			abrirVentanaPrincipal();
		}
		}
	
//	public void acceder(){
//		String usuario = ventana.gettUsuario().getText();
//		String password = String.valueOf(ventana.gettContra().getPassword());
//		if (usuario.isEmpty() || password.length() <= 0) {
//			ventana.gettUsuario().requestFocus();
//			return;
//		}
//		
//		colaborador = dao.verificarAcceso(usuario, password);
//		
//		if (colaborador != null) {
//			Sesion.getInstance().setColaborador(colaborador);
//			this.ventana.dispose();
//			abrirVentanaPrincipal();
//		}
//		
//		if (colaborador == null) {
//			this.ventana.getlMensaje().setText("ACCESO INCORRECTO");
//		}
//		
//	}

	private void abrirVentanaPrincipal() {
		try {
			Sesion.getInstance().setColaborador(colaborador);
			VentanaPrincipal principal = new VentanaPrincipal();
			principal.getLblPODAC().setText(Sesion.getInstance().getColaborador().toString());
			ventana.dispose();
			principal.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public VentanaAcceso getVentana() {
		return ventana;
	}

	public ColaboradorDao getDao() {
		return dao;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Acceder":
			comprobarAcceso();
			break;
		default:
			break;
		}

	}
}
