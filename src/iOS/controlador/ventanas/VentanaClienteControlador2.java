package iOS.controlador.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ClienteDao;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.singleton.Metodos;
import iOS.vista.modelotabla.ModeloTablaCajaMovimiento;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.VentanaCliente2;
import iOS.vista.ventanas.pedidos.TransaccionPedido;
import iOS.vista.ventanas.transacciones.TransaccionCaja;

public class VentanaClienteControlador2 implements ActionListener, MouseListener, KeyListener, ClienteInterface {
	private VentanaCliente2 ventanaCliente2;
	private ClienteDao dao;
	private Cliente cliente;

	private ModeloTablaCajaMovimiento modeloTablaCajaMovimiento;
	private ModeloTablaPedido modeloTablaPedido;

	private Caja caja;
	private List<CajaMovimiento> movimientos = new ArrayList<CajaMovimiento>();
	private Pedido pedido;
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public VentanaClienteControlador2(VentanaCliente2 ventanaCliente2) {
		this.ventanaCliente2 = ventanaCliente2;
		
		tableMenu(ventanaCliente2.getTablePedidos());

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
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventanaCliente2.getTablePagos() && e.getClickCount() == 2) {
			caja = movimientos.get(ventanaCliente2.getTablePagos().getSelectedRow()).getCaja();
			abrirTransaccionCaja(caja);
		}

		if (e.getSource() == ventanaCliente2.getTablePedidos() && e.getClickCount() == 2) {
			pedido = pedidos.get(ventanaCliente2.getTablePedidos().getSelectedRow());
			if (pedido.getPedidoCarteleria() != (null)) {
				if (pedido.getPedidoCarteleria()) {
					abrirPedidoCarteleria(pedido);
					return;
				}
			}

			if (pedido.getPedidoCostura() != (null)) {
				if (pedido.getPedidoCostura()) {
					abrirPedidoCostura(pedido);
					return;
				}
			}
			if (pedido.getPedidoCarteleria() == null && pedido.getPedidoCostura() == null) {
				JOptionPane.showMessageDialog(ventanaCliente2, "Error al consultar, comuníquese con el desarrollador.");
			}
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

	private void abrirTransaccionCaja(Caja c) {
		TransaccionCaja ventana = new TransaccionCaja();
		ventana.setUpControlador();
		ventana.getControlador().setCaja(c);
		ventana.setVisible(true);
	}

	private void abrirPedidoCarteleria(Pedido p) {
		TransaccionPedido ventana = new TransaccionPedido();
		ventana.setUpCarteleriaControlador();
		ventana.getCarteleriaControlador().setPedido(p);
		ventana.setVisible(true);
	}

	private void abrirPedidoCostura(Pedido p) {
		TransaccionPedido ventana = new TransaccionPedido();
		ventana.setUpConfeccionControlador();
		ventana.getConfeccionControlador().setPedido(p);
		ventana.setVisible(true);
	}
	
	
	private void tableMenu(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopup(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopup(final JTable table, final int row) {
		pedido = pedidos.get(row);
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem imprimirItem = new JMenuItem("Imprimir pedido");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (pedido.getPedidoCarteleria() != pedido.getPedidoCarteleria() == true) {
					Metodos.getInstance().imprimirPedidoCarteleriaIndividual(pedido, ventanaCliente2);
				}

			}
		});
		JMenuItem anularPedido = new JMenuItem("Anular pedido");
		anularPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Metodos.getInstance().anularRegistro(pedidos.get(row));

			}
		});
		popup.add(imprimirItem);
		popup.add(anularPedido);
		return popup;
	}
}
