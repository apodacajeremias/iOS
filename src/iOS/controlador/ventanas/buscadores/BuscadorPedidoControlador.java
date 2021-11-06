package iOS.controlador.ventanas.buscadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.ConfiguracionDao;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Configuracion;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.vista.modelotabla.ModeloTablaPedido;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorPedido;
import iOS.vista.ventanas.transacciones.TransaccionPedido;

public class BuscadorPedidoControlador implements ActionListener, ClienteInterface, MouseListener, PedidoInterface {
	private List<Pedido> pedidos;
	private ModeloTablaPedido mtPedido;
	private ModeloTablaPedidoDetalle mtPedidoDetalle;
	private PedidoDao dao;
	private BuscadorPedido bPedido;
	@SuppressWarnings("unused")
	private PedidoInterface interfaz;
	private Configuracion configuracion;
	private Cliente cliente;
	private Pedido pedido;

	public BuscadorPedidoControlador(BuscadorPedido buscador) {
		this.bPedido = buscador;
		estadoInicial(true);
		
		dao = new PedidoDao();


		mtPedido = new ModeloTablaPedido();
		bPedido.getTable().setModel(mtPedido);

		DefaultTableCellRenderer centro = new DefaultTableCellRenderer(); // ALINEAMIENTO DE LAS COLUMNAS
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer(); // ALINEAMIENTO DE LAS COLUMNAS
		centro.setHorizontalAlignment(SwingConstants.CENTER);// .LEFT .RIGHT .CENTER
		derecha.setHorizontalAlignment(SwingConstants.RIGHT);// .LEFT .RIGHT .CENTER

		bPedido.getTable().getColumnModel().getColumn(0).setPreferredWidth(40);
		bPedido.getTable().getColumnModel().getColumn(1).setPreferredWidth(40);
		bPedido.getTable().getColumnModel().getColumn(2).setPreferredWidth(40);

		bPedido.getTable().getColumnModel().getColumn(0).setCellRenderer(derecha);
		bPedido.getTable().getColumnModel().getColumn(1).setCellRenderer(centro);
		bPedido.getTable().getColumnModel().getColumn(2).setCellRenderer(centro);
		


		mtPedidoDetalle = new ModeloTablaPedidoDetalle();
		this.bPedido.getTableDetalle().setModel(mtPedidoDetalle);

		bPedido.getTableDetalle().getColumnModel().getColumn(0).setPreferredWidth(50);
		bPedido.getTableDetalle().getColumnModel().getColumn(1).setPreferredWidth(200);
		bPedido.getTableDetalle().getColumnModel().getColumn(2).setPreferredWidth(50);

		bPedido.getTableDetalle().getColumnModel().getColumn(1).setCellRenderer(centro);
		bPedido.getTableDetalle().getColumnModel().getColumn(2).setCellRenderer(derecha);

		cargarConfiguracion();
		setUpEvents();
		recuperarTodo();
	}
	public void setInterfaz(PedidoInterface interfaz) {
		this.interfaz = interfaz;
	}

	private void setUpEvents() {
		bPedido.getBtnFiltrar().addActionListener(this);
		bPedido.getBtnPagar().addActionListener(this);
		this.bPedido.getBtnBuscarCliente().addActionListener(this);
		this.bPedido.getBtnFiltrar().addActionListener(this);
		this.bPedido.getBtnLimpiar().addActionListener(this);
		this.bPedido.getBtnCancelar().addActionListener(this);
		this.bPedido.getTable().addMouseListener(this);

	}
	private void cargarConfiguracion() {
		ConfiguracionDao daoConfig = new ConfiguracionDao();
		configuracion = daoConfig.recuperarPorId(0);
		if (configuracion == null) {
			JOptionPane.showMessageDialog(null,
					"Verifique que la configuración esté cargada");
		}
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		bPedido.gettCliente().setText(cliente.getNombreCompleto());
	}
	
	@Override
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	
	}
	
	private void estadoInicial(boolean b){
		EventosUtil.limpiarCampoPersonalizado(bPedido.getLblTotalRegistro());
		EventosUtil.limpiarCampoPersonalizado(bPedido.gettCliente());	
		
		EventosUtil.estadosBotones(bPedido.getBtnFiltrar(), b);
		EventosUtil.estadosBotones(bPedido.getBtnPagar(), false);
		EventosUtil.estadosBotones(bPedido.getBtnBuscarCliente(), b);
		EventosUtil.estadosBotones(bPedido.getBtnCancelar(), !b);
		EventosUtil.estadosBotones(bPedido.getBtnLimpiar(), !b);
		
	}
	
	private void vaciarFormulario(){
		
	}
	
	private void vaciarFormularioDetalle(){
		
	}
	
	private void filtrar() {
		if (bPedido.gettCliente().getText().length() == 0) {
			pedidos = dao.recuperarTodo();
		} else {
			pedidos = dao.recuperarPorCliente(cliente.getId());			
			mtPedido.setPedidos(pedidos);
			mtPedido.fireTableDataChanged();
		}

		bPedido.getLblTotalRegistro().setText("Se han encontrado "+pedidos.size()+" registros");
		EventosUtil.estadosBotones(bPedido.getBtnLimpiar(), true);
	}
	
	private void limpiar() {
		estadoInicial(true);
		pedidos = dao.recuperarTodo();
		
		mtPedido.setPedidos(pedidos);
		bPedido.getLblTotalRegistro().setText("Se han encontrado "+pedidos.size()+" registros");
	}
	

	private void cancelar(){
		estadoInicial(true);
		vaciarFormulario();
		vaciarFormularioDetalle();
	}
	
	
	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}
	
	private void abrirTransaccionPedido() {
		TransaccionPedido tPres = new TransaccionPedido();
		tPres.setUpControlador();
		tPres.getControlador().setPedido(pedido);
		tPres.setVisible(true);
		bPedido.dispose();
	}

	private void visualizarDetalles(int posicion) {
		if (posicion < 0) {
			return;
		}
			
		pedido = pedidos.get(posicion);
//		mtPedidoDetalle.setDetalle(pedidos.get(posicion).getPedidoDetalle());
		mtPedidoDetalle.setDetalle(pedido.getPedidoDetalles());
		mtPedidoDetalle.fireTableDataChanged();
		System.out.println(posicion + " / visualizar detalles");
		EventosUtil.estadosBotones(bPedido.getBtnCancelar(), true);
	}
	private void recuperarTodo() {
		pedidos = dao.recuperarTodo();
		mtPedido.setPedidos(pedidos);
		mtPedido.fireTableDataChanged();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == bPedido.getTable() && e.getClickCount() == 1) {
			visualizarDetalles(bPedido.getTable().getSelectedRow());
			
		}
		
		if (e.getSource() == bPedido.getTable() && e.getClickCount() == 2) {
			abrirTransaccionPedido();
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
		case "BuscarCliente":
			abrirBuscadorCliente();
			break;
		case "Filtrar":
			filtrar();
			break;
		case "Limpiar":
			limpiar();
			break;
		case "Cancelar":
			cancelar();
			break;
		default:
			break;
		}
	}
	

}
