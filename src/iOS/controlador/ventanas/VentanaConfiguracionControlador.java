package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ConfiguracionDao;
import iOS.modelo.entidades.Configuracion;
import iOS.modelo.interfaces.AccionesABM;
import iOS.modelo.singleton.Sesion;
import iOS.vista.ventanas.VentanaConfiguracion;

public class VentanaConfiguracionControlador implements ActionListener, AccionesABM {
	private VentanaConfiguracion ventana;
	private String accion;
	public static String modulo = "CONFIGURACION";

	private ConfiguracionDao dao;
	private Configuracion configuracion;

	private byte[] bytesIMG;
	private File archivo;
	private JFileChooser selectorArchivos = new JFileChooser();
	private FileFilter filtro = new FileNameExtensionFilter("Archivos png (.png)", "png");
	public VentanaConfiguracionControlador(VentanaConfiguracion vConfiguracion) {
		this.ventana = vConfiguracion;
		vConfiguracion.getMiToolBar().setAcciones(this);
		dao = new ConfiguracionDao();
		estadoInicial();
		recuperarConfiguracion();
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

	private void recuperarConfiguracion() {
		configuracion = dao.recuperarUltimaConfiguracion();

		setConfiguracion(configuracion);

	}

	private boolean validarFormulario() {
		if (ventana.gettNombreEmpresa().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			ventana.gettNombreEmpresa().requestFocus();
			return false;
		}
		if (ventana.gettDireccion().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			ventana.gettDireccion().requestFocus();
			return false;
		}
		if (ventana.gettRUC().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			ventana.gettRUC().requestFocus();
			return false;
		}

		if (ventana.gettTelefono().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			ventana.gettTelefono().requestFocus();
			return false;
		}
		if (ventana.gettSitioWeb().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Faltan datos", "Verificar", JOptionPane.ERROR_MESSAGE);
			ventana.gettSitioWeb().requestFocus();
			return false;
		} 
		if (!ventana.gettSitioWeb().getText().isEmpty()) {
			try {
				new URL(ventana.gettSitioWeb().getText()).toURI();
				return true;
			} catch (URISyntaxException exception) {
				JOptionPane.showMessageDialog(ventana, "URL de pagina web invalida, verifique.");
				return false;
			} catch (MalformedURLException exception) {
				JOptionPane.showMessageDialog(ventana, "URL de pagina web invalida, verifique.");
				return false;
			}
		}
		return true;
	}

	private void estadoInicial() {
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNombreEmpresa());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettRUC());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettSitioWeb());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettTelefono());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlIcono());
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
		case "AbrirArchivos":
			abrirBuscadorImagenes();
			break;
		default:
			break;
		}
	}

	@Override
	public void guardar() {
		if (!validarFormulario()) {
			return;
		}
		if (accion.equals("NUEVO")) {
			configuracion = new Configuracion();
			configuracion.setColaborador(Sesion.getInstance().getColaborador());

		}
		configuracion.setDireccion(ventana.gettDireccion().getText());
		configuracion.setNombreEmpresa(ventana.gettNombreEmpresa().getText());
		configuracion.setRucEmpresa(ventana.gettRUC().getText());
		configuracion.setSitioWeb(ventana.gettSitioWeb().getText());
		configuracion.setTelefono(ventana.gettTelefono().getText());
		try {
			configuracion.setIcon(EventosUtil.entradaImagen(archivo));
		} catch (Exception e1) {
			configuracion.setIcon(null);
			e1.printStackTrace();
		}

		
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(configuracion);
				ventana.getLblAccion().setText("Guardado");
			} else {
				dao.modificar(configuracion);
				ventana.getLblAccion().setText("Actualizado");
			}
			dao.commit();
			modificar();
			setConfiguracion(configuracion);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

	}

	@Override
	public void cancelar() {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMensaje());
		estadoInicial();
	}

	@Override
	public void nuevo() {
		estadoInicial();
		accion = "NUEVO";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreEmpresa().requestFocus();
	}

	@Override
	public void modificar() {
		estadoInicial();
		accion = "MODIFICAR";
		ventana.getlMensaje().setText(accion + " REGISTRO");
		ventana.gettNombreEmpresa().requestFocus();
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub

	}

	public void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;

		if (configuracion == null) {
			estadoInicial();
			return;
		}

		ventana.gettNombreEmpresa().setText(configuracion.getNombreEmpresa());
		ventana.gettDireccion().setText(configuracion.getDireccion());
		ventana.gettRUC().setText(configuracion.getRucEmpresa());
		ventana.gettSitioWeb().setText(configuracion.getSitioWeb());
		ventana.gettTelefono().setText(configuracion.getTelefono());
		try {
			ventana.getlIcono().setIcon(new ImageIcon(configuracion.getIcon()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
