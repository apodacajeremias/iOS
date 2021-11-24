package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteDeudasPagosControlador;
import iOS.vista.componentes.ReporteGenerico;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ReporteDeudasPagos extends ReporteGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918225936203470477L;
	private ReporteDeudasPagosControlador controlador;
	private JTable tablePago;

	public void setUpControlador() {
		controlador = new ReporteDeudasPagosControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public ReporteDeudasPagos() {
		getScrollPane().setBounds(10, 198, 610, 402);
		getRb4().setText("Pedido sin pago");
		getRb3().setText("Pago sin pedido");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(630, 198, 634, 402);
		getContentPane().add(scrollPane);
		
		tablePago = new JTable();
		scrollPane.setViewportView(tablePago);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteDeudasPagosControlador getControlador() {
		return controlador;
	}

	public void setControlador(ReporteDeudasPagosControlador controlador) {
		this.controlador = controlador;
	}

	public JTable getTablePago() {
		return tablePago;
	}
	
	
}
