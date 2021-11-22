package iOS.controlador.ventanas.buscadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorPedido;

public class BuscadorPedidoControlador implements ActionListener, ClienteInterface, MouseListener, PedidoInterface {
	
	private List<Pedido> pedidos;
	private ModeloTablaPedido mtPedido;
	private PedidoDao dao;
	private BuscadorPedido bPedido;
	private PedidoInterface interfaz;
	private Cliente cliente;
	private Pedido pedido;

	public BuscadorPedidoControlador(BuscadorPedido buscador) {
		this.bPedido = buscador;
		
		dao = new PedidoDao();
		mtPedido = new ModeloTablaPedido();
		bPedido.getTable().setModel(mtPedido);

		setUpEvents();
		formatoTabla();
		estadoInicial(true);
	}
	public void setInterfaz(PedidoInterface interfaz) {
		this.interfaz = interfaz;
	}
	
	private void formatoTabla() {
		bPedido.getTable().getColumnModel().getColumn(1).setPreferredWidth(0);
		bPedido.getTable().getColumnModel().getColumn(1).setMinWidth(0);
		bPedido.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
	}

	private void setUpEvents() {
		bPedido.getBtnFiltrar().addActionListener(this);
		this.bPedido.getBtnBuscarCliente().addActionListener(this);
		this.bPedido.getBtnFiltrar().addActionListener(this);
		this.bPedido.getBtnCancelar().addActionListener(this);
		this.bPedido.getTable().addMouseListener(this);
	}
	
	private void seleccionarPedido(Integer posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		
		pedido = pedidos.get(posicion);
		interfaz.setPedido(pedido);
		bPedido.dispose();
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		while (cliente == null) {
			abrirBuscadorCliente();
			JOptionPane.showMessageDialog(bPedido, "Seleccione el cliente");
		}
		
		pedidos = cliente.getPedidos().stream().filter(o -> o.isEsPresupuesto() == false && o.isEstado() == true).collect(Collectors.toList());
		mtPedido.setPedidos(pedidos);
		mtPedido.fireTableDataChanged();
		
		bPedido.getlDatos1().setText(cliente.getNombreCompleto());
		bPedido.getlDatos2().setText("DOCUMENTO: " + cliente.getIdentificacion() + " TELEFONO " + cliente.getContacto());
	}
	
	@Override
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	private void estadoInicial(boolean b){
		EventosUtil.limpiarCampoPersonalizado(bPedido.getlTotalRegistro());
		EventosUtil.limpiarCampoPersonalizado(bPedido.getlDatos1());	
		EventosUtil.limpiarCampoPersonalizado(bPedido.getlDatos2());
		
		EventosUtil.estadosBotones(bPedido.getBtnFiltrar(), b);
		EventosUtil.estadosBotones(bPedido.getBtnBuscarCliente(), b);
		EventosUtil.estadosBotones(bPedido.getBtnCancelar(), b);
		
	}
	
	private void vaciarFormulario(){
		pedidos = new ArrayList<>();
		mtPedido.setPedidos(pedidos);
		mtPedido.fireTableDataChanged();
	}
	
	private void filtrar() {
		if (cliente == null) {
			return;
		}
		try {
			pedidos = dao.recuperarPorCliente(cliente.getId());
			mtPedido.setPedidos(pedidos);
			mtPedido.fireTableDataChanged();
			bPedido.getlTotalRegistro().setText("Se han encontrado "+EventosUtil.separadorMiles((double) pedidos.size())+" registros en total.");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	private void cancelar(){
		estadoInicial(true);
		vaciarFormulario();
	}
	
	
	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}
	
//	private void abrirTransaccionPedido() {
//		TransaccionPedido tPres = new TransaccionPedido();
//		tPres.setUpControlador();
//		tPres.getControlador().setPedido(pedido);
//		tPres.setVisible(true);
//	}

	@Override
	public void mouseClicked(MouseEvent e) {		
		if (e.getSource() == bPedido.getTable() && e.getClickCount() == 2) {
			seleccionarPedido(bPedido.getTable().getSelectedRow());
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Buscar":
			abrirBuscadorCliente();
			break;
		case "Filtrar":
			filtrar();
			break;
		case "Cancelar":
			cancelar();
			break;
		default:
			break;
		}
	}
	

}
