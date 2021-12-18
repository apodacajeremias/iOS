package iOS.controlador.ventanas.principales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Maquina;
import iOS.modelo.entidades.Rol;
import iOS.modelo.entidades.Sector;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.principales.VentanaAcceso;
import iOS.vista.ventanas.principales.VentanaPrincipal;

public class VentanaAccesoControlador implements ActionListener {
	private VentanaAcceso ventana;
	private ColaboradorDao dao;
	private Colaborador colaborador;
	private List<Colaborador> colaboradores;

	private Sector sector;
	private List<Sector> sectores;

	private Rol rol;
	private List<Rol> roles;
	
	private List<Maquina> maquinas;

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
			colaboradores = dao.recuperarTodoOrdenadoPorNombre();
			sector = colaborador.getSector();
			sectores = dao.recuperarSectoresOrdenadoPorNombre();
			rol = colaborador.getRol();
			roles = dao.recuperarRolesOrdenadoPorNombre();
			maquinas = dao.recuperarMaquinasOrdenadoPorNombre();
			abrirVentanaPrincipal();
		}
	}

	private void abrirVentanaPrincipal() {
		try {
			Sesion.getInstance().setColaborador(colaborador);
			Sesion.getInstance().setColaboradores(colaboradores);
			Sesion.getInstance().setSector(sector);
			Sesion.getInstance().setSectores(sectores);
			Sesion.getInstance().setRol(rol);
			Sesion.getInstance().setRoles(roles);
			Sesion.getInstance().setMaquinas(maquinas);
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
