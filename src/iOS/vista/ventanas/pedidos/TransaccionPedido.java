package iOS.vista.ventanas.pedidos;

import iOS.controlador.ventanas.pedidos.PedidoCarteleriaControlador;
import iOS.controlador.ventanas.pedidos.PedidoConfeccionControlador;
import iOS.vista.componentes.PedidoGenerico;

public class TransaccionPedido extends PedidoGenerico {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -36528829059081172L;

	private PedidoCarteleriaControlador carteleriaControlador;
	
	private PedidoConfeccionControlador confeccionControlador;
	
	public void setUpCarteleriaControlador() {
		carteleriaControlador = new PedidoCarteleriaControlador(this);
		setTitle("PEDIDO DE CARTELERIA");
	}
	public void setUpConfeccionControlador() {
		carteleriaControlador = new PedidoCarteleriaControlador(this);
		setTitle("PEDIDO DE CONFECCION");
	}

	public TransaccionPedido() {
		// TODO Auto-generated constructor stub
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PedidoCarteleriaControlador getCarteleriaControlador() {
		return carteleriaControlador;
	}
	public PedidoConfeccionControlador getConfeccionControlador() {
		return confeccionControlador;
	}
	
}