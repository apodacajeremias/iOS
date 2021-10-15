package iOS.vista.ventanas.pedidos;

import iOS.controlador.ventanas.pedidos.PedidoConfeccionControlador;
import iOS.vista.componentes.PedidoGenerico;

public class PedidoConfeccion extends PedidoGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6747029491614937552L;
	private PedidoConfeccionControlador controlador;

	/**
	 * Launch the application.
	 */
	public void setUpControlador() {
		controlador = new PedidoConfeccionControlador(this);

	}
	/**
	 * Create the dialog.
	 */
	public PedidoConfeccion() {
		getLblprsnlzdMetrosDelPedido().setVisible(false);
		getlMetrosPedido().setVisible(false);
		getRbConsiderarMetraje().setVisible(false);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PedidoConfeccionControlador getControlador() {
		return controlador;
	}

	
}
