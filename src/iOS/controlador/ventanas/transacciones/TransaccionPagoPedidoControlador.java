package iOS.controlador.ventanas.transacciones;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.modelotabla.ModeloTablaCliente;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.transacciones.TransaccionPagoPedido;

public class TransaccionPagoPedidoControlador implements MouseListener {
	private TransaccionPagoPedido ventana;

	private ClienteDao dao;

	private Cliente cliente;
	private List<Cliente> clientes;
	private ModeloTablaCliente tablaCliente;

	private CajaMovimiento movimiento;
	private List<CajaMovimiento> movimientos;
	private ModeloTablaCajaMovimiento tablaCajaMovimiento;

	private Pedido pedido;
	private List<Pedido> pedidos;
	private ModeloTablaPedido tablaPedido;

	private CajaMovimiento m;
	private List<CajaMovimiento> ms;
	private ModeloTablaCajaMovimiento tablaCM;

	public TransaccionPagoPedidoControlador(TransaccionPagoPedido ventana) {
		this.ventana = ventana;
		dao = new ClienteDao();

		tablaCliente = new ModeloTablaCliente();
		ventana.getTableCliente().setModel(tablaCliente);

		tablaCajaMovimiento = new ModeloTablaCajaMovimiento();
		ventana.getTablePago().setModel(tablaCajaMovimiento);

		tablaPedido = new ModeloTablaPedido();
		ventana.getTablePedido().setModel(tablaPedido);

		tablaCM = new ModeloTablaCajaMovimiento();
		ventana.getTableRelacion().setModel(tablaCM);

		ventana.getTableCliente().addMouseListener(this);
		ventana.getTablePago().addMouseListener(this);
		ventana.getTablePedido().addMouseListener(this);
		ventana.getTableRelacion().addMouseListener(this);

		recuperarTodos();
	}

	private void recuperarTodos() {
		// TODO Auto-generated method stub
		clientes = dao.recuperarTodoOrdenadoPorNombre();
		tablaCliente.setLista(clientes);
		tablaCliente.fireTableDataChanged();
	}
	
	private void vaciarTablas() {
		movimientos = new ArrayList<CajaMovimiento>();
		tablaCajaMovimiento.setMovimiento(movimientos);
		tablaCajaMovimiento.fireTableDataChanged();
		
		ms = new ArrayList<CajaMovimiento>();
		tablaCM.setMovimiento(ms);
		tablaCM.fireTableDataChanged();
		
		pedidos = new ArrayList<Pedido>();
		tablaPedido.setPedidos(pedidos);
		tablaPedido.fireTableDataChanged();
	}

	private void seleccionarCliente(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		vaciarTablas();
		cliente = clientes.get(posicion);

		movimientos = dao.recuperarPagos(cliente.getId());
		tablaCajaMovimiento.setMovimiento(movimientos);
		tablaCajaMovimiento.fireTableDataChanged();

		pedidos = dao.recuperarPedidos(cliente.getId());
		tablaPedido.setPedidos(pedidos);
		tablaPedido.fireTableDataChanged();
	}

	private void seleccionarPago(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		movimiento = movimientos.get(posicion);
		System.out.println(movimiento);
	}

	private void seleccionarPedido(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		pedido = pedidos.get(posicion);
		
		if (pedido.getPagosPedido() != null) {
			ms = pedido.getPagosPedido();
			tablaCM.setMovimiento(ms);
			tablaCM.fireTableDataChanged();
		}
	}

	private void asociarPago(CajaMovimiento pago, Pedido pedido, Cliente cliente) {
		pago.setPedido(pedido);
		movimientos.add(pago);
		cliente.setCajaMovimientos(movimientos);
		
			
		if (pago.getValorGS() > pedido.getPrecioPagar()) {
			int respuesta = JOptionPane.showConfirmDialog(null, "VALOR DEL PAGO ES SUPERIOR AL VALOR DEL PEDIDO, DESEA ASOCIAR DE TODAS FORMAS?", "ATENCION",
					JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				
			} else {
				return;
			}
		}
		
		String mensaje = "Confirma la asociacion del PAGO "+pago.getId()+" VALOR "+pago.getValorGS()+" con el PEDIDO "+pedido.getId()+ " VALOR PEDIDO "+pedido.getPrecioPagar();
		
		int respuesta = JOptionPane.showConfirmDialog(null, mensaje, "ATENCION",
				JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				dao.modificar(cliente);
				dao.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dao.rollBack();
			}
		}
		
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventana.getTableCliente()) {
			seleccionarCliente(ventana.getTableCliente().getSelectedRow());
		}

		if (e.getSource() == ventana.getTablePago()) {
			seleccionarPago(ventana.getTablePago().getSelectedRow());
		}

		if (e.getSource() == ventana.getTablePedido()) {
			seleccionarPedido(ventana.getTablePedido().getSelectedRow());
		}

		if (e.getSource() == ventana.getTablePago() && e.getClickCount() == 2) {
			asociarPago(movimiento, pedido, cliente);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
