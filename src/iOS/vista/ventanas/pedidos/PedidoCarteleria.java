package iOS.vista.ventanas.pedidos;

import iOS.controlador.ventanas.pedidos.PedidoCarteleriaControlador;
import iOS.vista.componentes.PedidoGenerico;

public class PedidoCarteleria extends PedidoGenerico {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PedidoCarteleriaControlador controlador;
	
	public void setUpControlador() {
		controlador = new PedidoCarteleriaControlador(this);

	}

	public PedidoCarteleria() {
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PedidoCarteleriaControlador getControlador() {
		return controlador;
	}
	
}