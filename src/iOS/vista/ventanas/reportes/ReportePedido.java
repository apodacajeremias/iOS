package iOS.vista.ventanas.reportes;

import java.awt.EventQueue;

import javax.swing.JDialog;

import iOS.controlador.ventanas.reportes.ReportePedidoCarteleriaControlador;
import iOS.controlador.ventanas.reportes.ReportePedidoConfeccionControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReportePedido extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReportePedidoCarteleriaControlador controladorCarteleria;
	public String modulo = "PEDIDO";
	private ReportePedidoConfeccionControlador controladoConfeccion;


	/**
	 * Create the dialog.
	 */

	public void setUpControladorCarteleria() {
		controladorCarteleria = new ReportePedidoCarteleriaControlador(this);
	}
	
	public void setUpControladorConfeccion() {
		controladoConfeccion = new ReportePedidoConfeccionControlador(this);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportePedido dialog = new ReportePedido();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ReportePedido() {
		getRb2().setText("Incluir presupuestos");
		getRb1().setText("Incluir anulados");
		getBtnImprimir().setText("Reporte Hoy");
		setTitle("REPORTE DE "+modulo);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReportePedidoCarteleriaControlador getControladorCarteleria() {
		return controladorCarteleria;
	}

	public String getModulo() {
		return modulo;
	}

	public ReportePedidoConfeccionControlador getControladoConfeccion() {
		return controladoConfeccion;
	}

	
	

}
