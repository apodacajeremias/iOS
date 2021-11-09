package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.VentanaCliente2;
import iOS.vista.ventanas.transacciones.TransaccionCaja;
import iOS.vista.ventanas.transacciones.TransaccionPedido;

public class VentanaClienteControlador2 implements ActionListener, MouseListener, KeyListener, ClienteInterface {
	private VentanaCliente2 ventanaCliente2;
	private ClienteDao dao;
	private Cliente cliente;

	private ModeloTablaCajaMovimiento modeloTablaCajaMovimiento;
	private ModeloTablaPedido modeloTablaPedido;

	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public VentanaClienteControlador2(VentanaCliente2 ventanaCliente2) {
		this.ventanaCliente2 = ventanaCliente2;

		modeloTablaCajaMovimiento = new ModeloTablaCajaMovimiento();
		ventanaCliente2.getTablePagos().setModel(modeloTablaCajaMovimiento);

		modeloTablaPedido = new ModeloTablaPedido();
		ventanaCliente2.getTablePedidos().setModel(modeloTablaPedido);

		dao = new ClienteDao();
		estadoInicial(true);
		setUpEvents();
		formatearTabla();
	}

	private void setUpEvents() {
		ventanaCliente2.getTablePagos().addMouseListener(this);
		ventanaCliente2.getTablePedidos().addMouseListener(this);
	}

	private void formatearTabla() {
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		derecha.setHorizontalAlignment(SwingConstants.RIGHT);

	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlColaborador());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlEstado());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlID());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlNombreCompleto());
		EventosUtil.limpiarCampoPersonalizado(ventanaCliente2.getlFechaRegistro());

	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		gestionarCliente();
	}

	private void gestionarCliente() {
		if (cliente == null) {
			return;
		}

		ventanaCliente2.getlColaborador().setText(cliente.getColaborador().toString());
		ventanaCliente2.getlContacto().setText(cliente.getContacto());
		ventanaCliente2.getlDireccion().setText(cliente.getDireccion());
		ventanaCliente2.getlEstado().setText(cliente.isEstado() ? "ACTIVO" : "INACTIVO");
		ventanaCliente2.getlFechaRegistro().setText(EventosUtil.formatoFecha(cliente.getFechaRegistro()));
		ventanaCliente2.getlID().setText(cliente.getId() + "");
		ventanaCliente2.getlIdentificacion().setText(cliente.getIdentificacion());
		ventanaCliente2.getlNombreCompleto().setText(cliente.getNombreCompleto());

//		modeloTablaCajaMovimiento.setMovimiento(cliente.getCajaMovimientos());
//		modeloTablaCajaMovimiento.fireTableDataChanged();
//
//		modeloTablaPedido.setPedidos(cliente.getPedidos());
//		modeloTablaPedido.fireTableDataChanged();

		cargarTablas(cliente);

	}

	private void cargarTablas(Cliente cliente) {
		movimientos = dao.recuperarPagos(cliente.getId());
		modeloTablaCajaMovimiento.setMovimiento(movimientos);
		modeloTablaCajaMovimiento.fireTableDataChanged();

		pedidos = dao.recuperarPedidos(cliente.getId());
		modeloTablaPedido.setPedidos(pedidos);
		modeloTablaPedido.fireTableDataChanged();

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == ventanaCliente2.getTablePagos()) {
			TransaccionCaja ventana = new TransaccionCaja();
			ventana.setUpControlador();
			ventana.getControlador()
					.setCaja(movimientos.get(ventanaCliente2.getTablePagos().getSelectedRow()).getCaja());
			ventana.setVisible(true);
		}

		if (arg0.getSource() == ventanaCliente2.getTablePedidos()) {
			TransaccionPedido ventana = new TransaccionPedido();
			ventana.setUpControlador();
			ventana.getControlador().setPedido(pedidos.get(ventanaCliente2.getTablePedidos().getSelectedRow()));
			ventana.setVisible(true);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
