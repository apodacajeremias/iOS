package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ProcesoDao;
import iOS.modelo.entidades.Proceso;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.interfaces.ProcesoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaProceso;

public class VentanaProcesoControlador implements AccionesABM, ProcesoInterface, ActionListener {
	private VentanaProceso ventana;
	private ProcesoDao dao;
	private Proceso proceso;
	private String accion;

	private ProcesoInterface interfaz;

	private byte[] bytesIMG;
	private File archivo;
	private JFileChooser selectorArchivos = new JFileChooser();
	private FileFilter filtro = new FileNameExtensionFilter("Archivos png (.png)", "png");
	
	public void setInterfaz(ProcesoInterface interfaz) {
		this.interfaz = interfaz;
	}

	public VentanaProcesoControlador(VentanaProceso ventanaProceso) {
		this.ventana = ventanaProceso;
		this.ventana.getMiToolBar().setAcciones(this);
		dao = new ProcesoDao();
		setUpEvents();
	}

	// Para iniciar
	private void setUpEvents() {
		// ACTION LISTENER
		ventana.getBtnAbrirArchivos().addActionListener(this);
		// MOUSE LISTENER

		// KEY LISTENER

	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblEstado());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblFechaRegistro());
		EventosUtil.limpiarCampoPersonalizado(ventana.getLblID());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlIcono());

		accion = null;
	}

	private void abrirBuscadorImagenes() {
		selectorArchivos.setFileFilter(filtro);
		if (selectorArchivos.showDialog(ventana, "Seleccionar") == JFileChooser.APPROVE_OPTION) {
			archivo = selectorArchivos.getSelectedFile();
			if (archivo.canRead()) {
				if (archivo.getName().endsWith("png")) {
					bytesIMG = EventosUtil.entradaImagen(archivo);
					ventana.getlIcono().setIcon(new ImageIcon(bytesIMG));
				} else {
					JOptionPane.showMessageDialog(ventana, "Seleccione una imagen png");
				}
			}
		}
	}

	private boolean validarFormulario() {
		if (ventana.gettNombreCompleto().getText().isEmpty()) {
			ventana.gettNombreCompleto().requestFocus();
			JOptionPane.showMessageDialog(ventana, "El nombre descriptivo del proceso es una información importante.");
			return false;
		}
		return true;
	}

	@Override
	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		proceso = null;
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
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			proceso = new Proceso();
			proceso.setColaborador(Sesion.getInstance().getColaborador());
		}

		proceso.setDescripcion(ventana.gettNombreCompleto().getText());
		proceso.setIcon(EventosUtil.entradaImagen(archivo));

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(proceso);
			} else {
				dao.modificar(proceso);
			}
			dao.commit();
			try {
				interfaz.setProceso(proceso);
				ventana.dispose();
			} catch (Exception e) {
				modificar();
				setProceso(proceso);
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

	@Override
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
		if (proceso == null) {
			System.err.println("MAQUINA NULL");
			return;
		} else {
			System.out.println(proceso.getId() + " ID DE MAQUINA");
		}

		ventana.gettNombreCompleto().setText(proceso.getDescripcion());
		ventana.getlIcono().setIcon(new ImageIcon(proceso.getIcon()));
		ventana.getLblColaborador().setText("Registrado por " + proceso.getColaborador().toString());
		ventana.getLblEstado().setText(proceso.isEstado() ? "PROCESO ACTIVO" : "PROCESO FUERA DE USO");
		ventana.getLblFechaRegistro().setText("Registrado el " + EventosUtil.formatoFecha(proceso.getFechaRegistro()));
		ventana.getLblID().setText("ID " + proceso.getId());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "AbrirArchivos":
			abrirBuscadorImagenes();
			break;

		default:
			break;
		}

	}
}
