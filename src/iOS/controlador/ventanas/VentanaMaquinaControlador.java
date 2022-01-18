package iOS.controlador.ventanas;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.MaquinaDao;
import iOS.modelo.entidades.Maquina;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.MaquinaInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaMaquina;

public class VentanaMaquinaControlador implements AccionesABM, MaquinaInterface {
	private VentanaMaquina ventana;
	private MaquinaDao dao;
	private Maquina maquina;
	private String accion;

	public VentanaMaquinaControlador(VentanaMaquina ventanaMaquina) {
		this.ventana = ventanaMaquina;
		this.ventana.getMiToolBar().setAcciones(this);
		dao = new MaquinaDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER

		// MOUSE LISTENER

		// KEY LISTENER

	}

	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblEstado());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblFechaRegistro());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblID());

		accion = null;
	}

	private boolean validarFormulario() {
		if (accion == null || accion.isEmpty()) {
			return false;
		}
		if (ventana.gettNombreCompleto().getText().isEmpty()) {
			ventana.gettNombreCompleto().requestFocus();
			JOptionPane.showMessageDialog(ventana,
					"El nombre descriptivo de la máquina es una información importante.");
			return false;
		}
		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial();
		accion = "NUEVO";
		maquina = null;
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreCompleto().requestFocus();

	}

	@Override
	public void modificar() {
		estadoInicial();
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreCompleto().requestFocus();
	}

	@Override
	public void eliminar() {
		accion = "ELIMINAR";
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			maquina = new Maquina();
			maquina.setColaborador(Sesion.getInstance().getColaborador());
		}

		maquina.setNombreMaquina(ventana.gettNombreCompleto().getText().toUpperCase());
		maquina.setTipoMaquina(ventana.getCbTipoMaquina().getSelectedItem().toString().toUpperCase());

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(maquina);
			} else {
				dao.modificar(maquina);
			}
			dao.commit();
			modificar();
			setMaquina(maquina);
			ventana.getlMensaje().setText("REGISTRO GUARDADO CON ÉXITO");
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}

	}

	@Override
	public void cancelar() {
		estadoInicial();
	}

	public void salir() {
		ventana.dispose();
	}

	@Override
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
		if (maquina == null) {
			System.err.println("MAQUINA NULL");
			return;
		} else {
			System.out.println(maquina.getId() + " ID DE MAQUINA");
		}
		ventana.gettNombreCompleto().setText(maquina.getNombreMaquina());
		ventana.getCbTipoMaquina().setSelectedItem(maquina.getTipoMaquina());
		ventana.getLblColaborador().setText("Registrado por " + maquina.getColaborador().toString());
		ventana.getLblEstado().setText(maquina.isEstado() ? "MAQUINA ACTIVA" : "MAQUINA FUERA DE USO");
		ventana.getLblFechaRegistro().setText("Registrado el " + EventosUtil.formatoFecha(maquina.getFechaRegistro()));
		ventana.getLblID().setText("ID " + maquina.getId());

	}
}
