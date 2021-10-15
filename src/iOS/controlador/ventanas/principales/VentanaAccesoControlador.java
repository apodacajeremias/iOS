package iOS.controlador.ventanas.principales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.vista.ventanas.principales.VentanaAcceso;
import iOS.vista.ventanas.principales.VentanaPrincipal;

public class VentanaAccesoControlador implements ColaboradorInterface, ActionListener {
	private VentanaAcceso ventana;
	private ColaboradorDao dao;
	private List<Colaborador> colaboradors = new ArrayList<Colaborador>();
	private Colaborador colaborador;
	private ColaboradorInterface interfaz;

	public VentanaAccesoControlador(VentanaAcceso ventana) {
		this.ventana = ventana;
		dao = new ColaboradorDao();
		setUpEvents();
	}

	private void setUpEvents() {
		ventana.getBtnIngresar().addActionListener(this);

	}

	public void setInterfaz(ColaboradorInterface interfaz) {
		this.interfaz = interfaz;
	}

	private boolean verificarAcceso(String u,String c){
		try {
			colaborador = dao.verificarAcceso(u,c);
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (colaborador == null) {
			return false;
		}
		return true;
	}

	public void acceder(){
		String colaborador = ventana.gettUsuario().getText();
		String password = String.valueOf(ventana.gettContra().getPassword());
		if (colaborador.isEmpty() || password.length() <= 0) {
			ventana.gettUsuario().requestFocus();
			return;
		}

		try {
			if (verificarAcceso(colaborador, password)) {
				ventana.dispose();
				abrirVentanaPrincipal();
			} else {
			}
		} catch (Exception e) {
			ventana.getlMensaje().setText("Error en conexion");
			EventosUtil.formatException(e);
			dao.rollBack();
		}
	}

	private void abrirVentanaPrincipal() {
		try {
			VentanaPrincipal ventana = new VentanaPrincipal();
			ventana.setColaborador(colaborador);
			ventana.setVisible(true);
		} catch (Exception e) {
			EventosUtil.formatException(e);
		}

	}

	public VentanaAcceso getVentana() {
		return ventana;
	}

	public ColaboradorDao getDao() {
		return dao;
	}

	public List<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public ColaboradorInterface getInterfaz() {
		return interfaz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Acceder":
			acceder();
			break;
		default:
			break;
		}

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

}
