package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteValesControlador;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteVales extends ReporteGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918225936203470477L;
	private LabelPersonalizado lCliente;
	private MiBoton btnBuscarCliente;
	private ReporteValesControlador controlador;

	public void setUpControlador() {
		controlador = new ReporteValesControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public ReporteVales() {
		getL1().setBounds(10, 122, 420, 25);
		getCbColaborador().setVisible(false);
		getLblNewLabel().setLocation(10, 36);
		getLblPorPeriodos().setLocation(299, 36);
		getRb1().setLocation(490, 25);

		getLblNewLabel().setText("Cliente");
//		getL3().setFont(new Font("Tahoma", Font.BOLD, 20));
//		getL3().setSize(420, 25);
		getScrollPane().setLocation(10, 148);
//		getScrollPane_1().setLocation(454, 148);
//		getL3().setLocation(454, 122);
//		getL3().setText("Pagos");
		getL2().setVisible(false);
		getL1().setText("Deudas");
//		getScrollPane_1().setSize(420, 402);
		getScrollPane().setSize(420, 402);
		getRb2().setVisible(false);
		getRb1().setText("Incluir anulados");

		btnBuscarCliente = new MiBoton("Buscar");
		btnBuscarCliente.setText("");
		btnBuscarCliente.setActionCommand("Buscar");
		btnBuscarCliente.setBounds(210, 20, 50, 30);
		getPanel().add(btnBuscarCliente);

		lCliente = new LabelPersonalizado(0);
		lCliente.setBounds(10, 61, 250, 20);
		getPanel().add(lCliente);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LabelPersonalizado getlCliente() {
		return lCliente;
	}

	public MiBoton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public ReporteValesControlador getControlador() {
		return controlador;
	}

}
