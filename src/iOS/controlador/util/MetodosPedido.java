package iOS.controlador.util;

import javax.swing.JOptionPane;

import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Pedido;

public class MetodosPedido {
	private static MetodosPedido metodos;
	
	private MetodosPedido() {
		// TODO Auto-generated constructor stub
	}
	
	public synchronized static MetodosPedido getInstance() {
		if (metodos == null) {
			metodos = new MetodosPedido();
		}
		return metodos;
	}
	
	public void anularPedido(Pedido pedido, PedidoDao dao) {
		if (pedido == null) {
			return;
		}		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Anular pedido?",
				"Anular", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			try {
				pedido.setEstado(false);
				dao.modificar(pedido);
				dao.commit();
			} catch (Exception e) {
				e.printStackTrace();
				dao.rollBack();
				return;
			}	
		} else {
			return;
		}	
	}
}
