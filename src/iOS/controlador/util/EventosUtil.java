package iOS.controlador.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import iOS.modelo.dao.CotizacionDao;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Cotizacion;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class EventosUtil {

	public static void estadosCampoPersonalizado(Component component, boolean estado) {
		if (component instanceof CampoNumeroPersonalizado) {
			CampoNumeroPersonalizado campoPersonalizado = ((CampoNumeroPersonalizado) component);
			campoPersonalizado.setEditable(estado);
		}
		if (component instanceof JTextArea) {
			JTextArea campoPersonalizado = ((JTextArea) component);
			campoPersonalizado.setEditable(estado);
		}
		if (component instanceof JTable) {
			JTable campoPersonalizado = ((JTable) component);
			campoPersonalizado.setEnabled(estado);
		}

		if (component instanceof JPanel) {
			JPanel campoPersonalizado = ((JPanel) component);
			campoPersonalizado.setEnabled(estado);
		}

		if (component instanceof JDateChooser) {
			JDateChooser campoPersonalizado = ((JDateChooser) component);
			campoPersonalizado.setEnabled(estado);
		}
		if (component instanceof JRadioButton) {
			JRadioButton campoPersonalizado = ((JRadioButton) component);
			campoPersonalizado.setEnabled(estado);
		}
		if (component instanceof JSpinner) {
			JSpinner campoPersonalizado = ((JSpinner) component);
			campoPersonalizado.setEnabled(estado);
		}
		if (component instanceof JPasswordField) {
			JPasswordField campoPersonalizado = ((JPasswordField) component);
			campoPersonalizado.setEnabled(estado);
		}

		if (component instanceof JComboBox) {
			@SuppressWarnings("rawtypes")
			JComboBox campoPersonalizado = ((JComboBox) component);
			campoPersonalizado.setEnabled(estado);
		}

		if (component instanceof CampoTextoPersonalizado) {
			CampoTextoPersonalizado campoPersonalizado = ((CampoTextoPersonalizado) component);
			campoPersonalizado.setEditable(estado);
		} else {
			if (component instanceof Container) {
				for (Component c : ((Container) component).getComponents()) {
					estadosCampoPersonalizado(c, estado);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void limpiarCampoPersonalizado(Component component) {

		if (component instanceof CampoNumeroPersonalizado) {
			CampoNumeroPersonalizado campoPersonalizado = ((CampoNumeroPersonalizado) component);
			campoPersonalizado.setText("");
		}

		if (component instanceof JPasswordField) {
			JPasswordField campoPersonalizado = ((JPasswordField) component);
			campoPersonalizado.setText(null);
		}

		if (component instanceof JTextArea) {
			JTextArea campoPersonalizado = ((JTextArea) component);
			campoPersonalizado.setText("");
		}
		if (component instanceof LabelPersonalizado) {
			LabelPersonalizado campoPersonalizado = ((LabelPersonalizado) component);
			campoPersonalizado.setText("");
		}
		if (component instanceof JDateChooser) {
			JDateChooser campoPersonalizado = ((JDateChooser) component);
			campoPersonalizado.setDate(null);
		}
		if (component instanceof JRadioButton) {
			JRadioButton campoPersonalizado = ((JRadioButton) component);
			campoPersonalizado.setSelected(false);
			campoPersonalizado.setText("");
		}
		if (component instanceof JComboBox) {
			JComboBox campoPersonalizado = ((JComboBox) component);
			campoPersonalizado.removeAllItems();
		}
		if (component instanceof CampoTextoPersonalizado) {
			CampoTextoPersonalizado campoPersonalizado = ((CampoTextoPersonalizado) component);
			campoPersonalizado.setText("");
		} else {
			if (component instanceof Container) {
				for (Component c : ((Container) component).getComponents()) {
					limpiarCampoPersonalizado(c);
				}
			}
		}
	}

	public static void moverConEnter(KeyEvent e, Component component) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER)
			component.setFocusable(true);
	}

	public static void estadosBotones(Component component, boolean estado) {
		if (component instanceof MiBoton) {
			MiBoton miBoton = ((MiBoton) component);
			miBoton.setEnabled(estado);
		}

	}

	public static String formatException(Throwable thr) {
		thr.printStackTrace();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		thr.printStackTrace(pw);
		return sw.toString();
	}

	public static boolean liberarAcceso(Colaborador colaborador, String modulo, String accion) {
		for (int i = 0; i < colaborador.getRol().getRolesOperaciones().size(); i++) {
			if (colaborador.getRol().getRolesOperaciones().get(i).getOperacion().getNombreOperacion()
					.equalsIgnoreCase(accion)
					&& colaborador.getRol().getRolesOperaciones().get(i).getOperacion().getModulo().getNombreModulo()
							.equalsIgnoreCase(modulo)) {
				return true;
			}
		}
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "PERFIL REQUERIDO: " + accion + " " + modulo);
		return false;

	}

	public static boolean liberarAccesoSegunRol(Colaborador colaborador, String rol) {
		if (colaborador.getRol().getNombreRol().equalsIgnoreCase(rol)) {
			return true;
		}
		Toolkit.getDefaultToolkit().beep();
		return false;

	}

	public static String formatoFecha(Date fecha) {
		SimpleDateFormat objSDF = new SimpleDateFormat("d MMM, yyyy");

		if (fecha == null) {
			return "Sin fecha.";
		}

		return objSDF.format(fecha);
	}

	public static String separadorMiles(Double valor) {
		try {
			DecimalFormat formato = new DecimalFormat("#,###.###;#,###.###");
			Double valorDouble = (valor);
			return formato.format(valorDouble);
		} catch (Exception e) {
			return null;
		}

	}

	public static String separadorDecimales(Double valor) {
		try {
			DecimalFormat formato = new DecimalFormat("#,##0.0##;#,##0.0##");
			Double valorDouble = (valor);
			return formato.format(valorDouble);
		} catch (Exception e) {
			return null;
		}
	}

	public static String verificarEstado(boolean b) {
		if (b) {
			return "VIGENTE";
		} else {
			return "ANULADO";
		}
	}

	public static Cotizacion cotizacion() {
		CotizacionDao dao = new CotizacionDao();
		Cotizacion cotizacion = dao.recuperarUltimaCotizacion();

		return cotizacion;
	}

	// Para insertar imagen en sistema
	public static byte[] entradaImagen(File archivo) {
		byte[] bytesIMG = new byte[1024 * 100];
		FileInputStream entrada;
		try {
			entrada = new FileInputStream(archivo);
//			BufferedImage img = ImageIO.read(entrada);
//			int width = img.getWidth();
//			int height = img.getHeight();
//			if (width != 64 || height != 64) {
//				JOptionPane.showMessageDialog(null, "El ancho y alto recomendado es de 64px");
//				return null;
//			}
			entrada.read(bytesIMG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytesIMG;
	}

	// Para sacar de la base de datos y guardar en la computadora
	public static String salidaImagen(File archivo, byte[] bytesIMG) {
		String respuesta = null;
		FileOutputStream salida;

		try {
			salida = new FileOutputStream(archivo);
			salida.write(bytesIMG);
			respuesta = "La imagen se cargó con éxito.";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuesta;
	}

}
