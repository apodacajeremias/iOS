package iOS.vista.ventanas.reportes;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import iOS.controlador.util.ConnectionHelper;
import iOS.controlador.util.EventosUtil;
import iOS.controlador.ventanas.reportes.ReporteValesControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteVales extends ReporteGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918225936203470477L;
	private ReporteValesControlador controlador;
	
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			ConnectionHelper.setUp();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ReporteVales dialog = new ReporteVales();
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					EventosUtil.formatException(ex);
				}
			}

		});
	}

	public void setUpControlador() {
		controlador = new ReporteValesControlador(this);
		setTitle("Reporte de Vales");
	}

	/**
	 * Create the dialog.
	 */
	public ReporteVales() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteValesControlador getControlador() {
		return controlador;
	}
	
	

}
