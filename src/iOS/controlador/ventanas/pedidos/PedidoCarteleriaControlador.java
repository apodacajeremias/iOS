package iOS.controlador.ventanas.pedidos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.ColaboradorInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.pedidos.PedidoCarteleria;


public class PedidoCarteleriaControlador implements ActionListener, MouseListener, KeyListener, ColaboradorInterface, PedidoInterface, ClienteInterface, ProductoInterface, PropertyChangeListener {
	private PedidoCarteleria transaccion;
	
	private ModeloTablaPedidoDetalle mtPedidoDetalle;
	private PedidoDao dao;
	private String accion;
	private Pedido pedido;
	private Cliente cliente;
	private List<PedidoDetalles> items = new ArrayList<PedidoDetalles>();
	private Producto producto;
	private PedidoDetalles detalle;
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private double sumaMetrosPorCliente;
	private Colaborador colaborador;
	
	public PedidoCarteleriaControlador(PedidoCarteleria transaccionPedido) {
		this.transaccion = transaccionPedido;
		mtPedidoDetalle = new ModeloTablaPedidoDetalle();
		transaccionPedido.getTable().setModel(mtPedidoDetalle);

		dao = new PedidoDao();
		pedido = new Pedido();

		nuevo();
		setUpEvents();
		formatoTabla();
	}

	private void setUpEvents() {
		this.transaccion.getBtnAgregar().addActionListener(this);
		this.transaccion.getBtnAnular().addActionListener(this);
		this.transaccion.getBtnBuscarCliente().addActionListener(this);
		this.transaccion.getBtnBuscarProducto().addActionListener(this);
		this.transaccion.getBtnSalir().addActionListener(this);
		
		this.transaccion.getBtnGuardar().addActionListener(this);
		

		this.transaccion.getTable().addMouseListener(this);
		this.transaccion.getTable().addPropertyChangeListener(this);
				this.transaccion.getRbConsiderarMetraje().addPropertyChangeListener(this);
		this.transaccion.getRbGenerarPresupuesto().addPropertyChangeListener(this);
		this.transaccion.getRbConsiderarMetraje().addMouseListener(this);
		this.transaccion.getRbGenerarPresupuesto().addMouseListener(this);

		this.transaccion.gettDescuentoPedido().addKeyListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlCliente());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlEstadoPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlFechaHora());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlGanancia());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlProducto());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlMetrosFechaEmision());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlMetrosPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlNroPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlSumatoria());
		EventosUtil.limpiarCampoPersonalizado(transaccion.getlTotalPagar());
		EventosUtil.limpiarCampoPersonalizado(transaccion.gettDescuentoPedido());
		EventosUtil.estadosCampoPersonalizado(transaccion.gettDescuentoPedido(), !b);

		transaccion.getRbConsiderarMetraje().setSelected(false);
		transaccion.getlMetrosFechaEmision().setForeground(Color.RED);
		transaccion.getRbGenerarPresupuesto().setSelected(false);

		//Botones que se mantienen disponibles si o si
		EventosUtil.estadosBotones(transaccion.getBtnBuscarCliente(), true);
		EventosUtil.estadosBotones(transaccion.getBtnSalir(), true);

		//Cuando setCliente, se activa
		EventosUtil.estadosBotones(transaccion.getBtnBuscarProducto(), !b);

		//Cuando setProducto, se activa
		EventosUtil.estadosBotones(transaccion.getBtnAgregar(), false);

		//Si se selecciona un detalle
		EventosUtil.estadosBotones(transaccion.getBtnAnular(), !b);

		EventosUtil.estadosBotones(transaccion.getBtnGuardar(), false);
		
		vaciarTabla();

	}

	private void formatoTabla() {
		transaccion.getTable().getColumnModel().getColumn(0).setPreferredWidth(250);
		transaccion.getTable().getColumnModel().getColumn(1).setPreferredWidth(100);

	}
	
	private void vaciarTabla() {
		items = new ArrayList<PedidoDetalles>();
		mtPedidoDetalle.setDetalle(items);
		mtPedidoDetalle.fireTableDataChanged();

	}

	private void agregarItem() {
		if (producto == null) {
			return;
		}	
		
		if (!limitarItems(items)) {
			return;
		}
		detalle = new PedidoDetalles();
		detalle.setColaborador(colaborador);

		//Medidas
		if (producto.isTieneMedidaFija()) {
			detalle.setMedidaAlto(producto.getAltoProducto());
			detalle.setMedidaAncho(producto.getAnchoProducto());
		} else {
			detalle.setMedidaAlto(0);
			detalle.setMedidaAncho(0);
		}

		//Calcula el descuento del producto si la opcion está activa o si el cliente aplica
		calcularDescuentoProducto(considerarMetraje());

		//Cantidad
		detalle.setCantidadDetalle(1);

		//Herencias
		detalle.setProducto(producto);
		
		//Precio sugerido que se puede cambiar
		detalle.setPrecioProducto((int) producto.getPrecioMinimo());


		//Para la tabla que se muestra
		items.add(detalle);
		mtPedidoDetalle.setDetalle(items);
		mtPedidoDetalle.fireTableDataChanged();
		transaccion.getBtnBuscarProducto().requestFocus();

		//Metodos referentes
		realizarCalculos();
	}
	
	private boolean limitarItems(List<PedidoDetalles> lista) {
		if (lista.size() < 10) {
			return true;
		}
		return false;

	}
	
	private void quitarItem() {
		if (transaccion.getTable().getSelectedRow() < 0) {
			return;
		}
		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Retirar item?",
				"Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			items.remove(transaccion.getTable().getSelectedRow());
			mtPedidoDetalle.setDetalle(items);
		} else {
			return;
		}
		mtPedidoDetalle.fireTableDataChanged();
		realizarCalculos();
	}

	private boolean considerarMetraje() {
		boolean considerarMetraje = transaccion.getRbConsiderarMetraje().isSelected();
		if (considerarMetraje == true) {
			transaccion.getlMetrosFechaEmision().setForeground(Color.YELLOW);
		} else {
			transaccion.getlMetrosFechaEmision().setForeground(Color.RED);
		}
		System.out.println(considerarMetraje+ " considerarMetraje");
		return considerarMetraje;

	}

	private boolean generarPresupuesto() {
		boolean esPresupuesto = transaccion.getRbGenerarPresupuesto().isSelected();
		System.out.println(esPresupuesto+ " esPresupuesto");
		return esPresupuesto;

	}

	private void calcularDescuentoProducto(boolean estado) {
		if (producto == null) {
			return;
		}
		if (estado == false) {
			sumaMetrosPorCliente = 0;
		} else {
			transaccion.getlMetrosFechaEmision().setText(
					String.valueOf(calcularHistoricoMetros(cliente.getId()+"", fechaHoy(), restarFechas())));
		}

		//		if (sumaMetrosPorCliente < 5) {
		//			detalle.setPrecioProducto(producto.getPrecio_5Menos());
		//			detalle.setPrecioPagar(producto.getPrecio_5Menos());
		//			detalle.setGananciaDetalle(producto.getPrecio_5Menos()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 5) {
		//			detalle.setPrecioProducto(producto.getPrecio_5Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_5Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_5Mas()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 10) {
		//			detalle.setPrecioProducto(producto.getPrecio_10Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_10Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_10Mas()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 50) {
		//			detalle.setPrecioProducto(producto.getPrecio_50Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_50Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_50Mas()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 100) {
		//			detalle.setPrecioProducto(producto.getPrecio_100Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_100Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_100Mas()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 200) {
		//			detalle.setPrecioProducto(producto.getPrecio_200Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_200Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_200Mas()-producto.getCosto());
		//		}
		//		if (sumaMetrosPorCliente >= 300) {
		//			detalle.setPrecioProducto(producto.getPrecio_300Mas());
		//			detalle.setPrecioPagar(producto.getPrecio_300Mas());
		//			detalle.setGananciaDetalle(producto.getPrecio_300Mas()-producto.getCosto());
		//		}
	}

	//Se calculan las compras de los ultimos 30 días 
	private double calcularHistoricoMetros(String id, java.sql.Date hoy, java.sql.Date hace){
		pedidos = dao.recuperarHistoricoCliente(id, hoy, hace);
		sumaMetrosPorCliente = 0;
		for (int i = 0; i < pedidos.size(); i++) {
			sumaMetrosPorCliente += pedidos.get(i).getMetrosTotal();
			System.out.println(pedidos.get(i).getMetrosTotal()+" "+i+" posicion");
		}
		System.out.println(pedidos.size()+" registros");
		System.out.println(hace+" fecha antes");
		System.out.println(hoy+" fecha hoy");
		System.out.println(sumaMetrosPorCliente);
		return sumaMetrosPorCliente;
	}

	private void calcularMedidaDetalle() {
		for (int i = 0; i < items.size(); i++) {

			//Se obttienen las medidas para calcular el area
			double alto = items.get(i).getMedidaAlto();
			double ancho = items.get(i).getMedidaAncho();			

			//calculo de area
			double area = alto*ancho;		

			//Como las medidas son en cm pasamos a M2			
			double metros = (area/10000);

			items.get(i).setMedidaDetalle(metros);
			mtPedidoDetalle.setDetalle(items);
			mtPedidoDetalle.fireTableDataChanged();
		}
	}

	private double sumarMedidaDetalle() {
		double total = 0;
		for (int i = 0; i < items.size(); i++) {
			total = total + (items.get(i).getMedidaDetalle());
		}
		transaccion.getlMetrosPedido().setText(String.valueOf(total));
		return total;
	}

	private void calcularSubtotalDetalle(){
		double metros = 0;
		int precio = 0;
		int cantidad = 0;
		int total = 0;
		
		for (int i = 0; i < items.size(); i++) {
			metros = items.get(i).getMedidaDetalle();
			precio = items.get(i).getPrecioProducto();
			cantidad = items.get(i).getCantidadDetalle();
			switch (items.get(i).getProducto().getTipoCobro()) {
			case "METRO CUADRADO":
				total = (int) ((precio * metros)*cantidad);
				items.get(i).setPrecioDetalle(total);
				mtPedidoDetalle.setDetalle(items);
				mtPedidoDetalle.fireTableDataChanged();
				break;
			case "METRO LINEAL":
			break;
			case "UNIDAD":
				total = (int) ((precio * cantidad));
				items.get(i).setPrecioDetalle(total);
				mtPedidoDetalle.setDetalle(items);
				mtPedidoDetalle.fireTableDataChanged();
			break;
			default:
				break;
			}
		}
	}

	private List<Integer> valorarPedido() {
		int suma = 0;
		int diferencia = 0;
		List<Integer> numeros = new ArrayList<>();
		double descuento = transaccion.gettDescuentoPedido().getValue();
		for (int i = 0; i < items.size(); i++) {
			suma += items.get(i).getPrecioDetalle();
		}
		if (suma > descuento) {
			diferencia = (int) (suma - descuento);
		} else {
			return null;
		}

		numeros.add(suma);
		numeros.add(diferencia);

		transaccion.getlSumatoria().setText(EventosUtil.separadorMiles((double) suma)+" Gs.");
		transaccion.getlTotalPagar().setText(EventosUtil.separadorMiles((double) diferencia)+ " Gs.");

		return numeros;
	}

	private int sumarCosto() {
		int total = 0;
		for (int i = 0; i < items.size(); i++) {
			//			total = total + (items.get(i).getProducto().getCosto());
		}
		return total;
	} 

	private void realizarCalculos() { //Si mi items está vacio entonces no ejecuta
		calcularMedidaDetalle();
		calcularSubtotalDetalle();
		sumarMedidaDetalle();
		valorarPedido();

	}

	private java.sql.Date restarFechas(){
		//Se busca la fecha de HOY
		Calendar c = Calendar.getInstance();
		//Date now = c.getTime();

		//A la fecha de HOY se restan 30 días
		c.add(Calendar.DATE, -30);

		//Obtenemos la fecha de hace 30 días, casteamos a long y paseamos a SQL DATE
		Date daysAgo = c.getTime();
		long d = daysAgo.getTime();
		java.sql.Date fecha = new java.sql.Date(d);
		return fecha;
	}

	private java.sql.Date fechaHoy() {
		//Fecha de hoy tipo CALENDAR
		Calendar c = Calendar.getInstance();

		//Fecha de hoy casteamos a DATE
		Date now = c.getTime();

		//Fecha de hoy en LONG
		long d = now.getTime();


		//Fecha LONG pasamos a SQL DATE
		java.sql.Date fecha = new java.sql.Date(d);
		return fecha;
	}
	
	private boolean validarFormulario() {
		if (cliente == null) {
			JOptionPane.showMessageDialog(transaccion, "Indique el cliente");
			return false;
		}
		if (colaborador == null) {
			JOptionPane.showMessageDialog(transaccion, "Inicie sesión");
			return false;
		}
		if (items.size() <= 0) {
			JOptionPane.showMessageDialog(transaccion, "Cargue productos al pedido");
			return false;
		}
		
		return true;
	}
	
	

	public void nuevo() {
		accion = "NUEVO";
		System.out.println(accion);
		estadoInicial(false);

	}

	private void cancelar() {
		estadoInicial(true);
	}

	private void guardar() {
		if (!validarFormulario()) {
			return;
		}
		
		if (accion.equals("NUEVO")) {
			pedido = new Pedido();
			pedido.setColaborador(colaborador);
			pedido.setMetrosFechaEmision(Double.parseDouble(transaccion.getlMetrosFechaEmision().getText()));
			pedido.setPedidoCarteleria(true);
		}

		pedido.setCliente(cliente);
		pedido.setConsiderarMetraje(transaccion.getRbConsiderarMetraje().isSelected());
		pedido.setDescuentoTotal(Integer.parseInt(transaccion.gettDescuentoPedido().getText()));
		pedido.setEsPresupuesto(transaccion.getRbGenerarPresupuesto().isSelected());
		pedido.setCostoTotal(sumarCosto());
		pedido.setMetrosTotal(sumarMedidaDetalle());
		pedido.setTipoPagoPedido(transaccion.getLstTipoPago().getSelectedValue().toString());


		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));


		pedido.setPedidoDetalles(items);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setPedido(pedido);
			items.get(i).setFechaModificado(new Date());
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(pedido);
			} else {
				dao.modificar(pedido);
			}
			dao.commit();			
			transaccion.dispose();
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == transaccion.gettDescuentoPedido()
				&& e.getKeyCode() == KeyEvent.VK_ENTER) {
			realizarCalculos();
			System.out.println("keyPressed");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == transaccion.getTable()) {
			transaccion.getTable().isCellEditable(transaccion.getTable().getSelectedRow(), transaccion.getTable().getSelectedColumn());

		}
		if (e.getSource() == transaccion.getRbConsiderarMetraje()) {
			considerarMetraje();
		}

		if (e.getSource() == transaccion.getRbGenerarPresupuesto()) {
			generarPresupuesto();
		}
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == transaccion.getTable()) {
			realizarCalculos();
		}

		if (e.getSource() == transaccion.getRbConsiderarMetraje()) {
			considerarMetraje();
		}
		if (e.getSource() == transaccion.getRbGenerarPresupuesto()) {
			generarPresupuesto();
		}
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "BuscarCliente":
			abrirBuscadorCliente();
			break;
		case "BuscarProducto":
			abrirBuscadorProducto();
			break;
		case "Nuevo":
			nuevo();
			break;
		case "Cancelar":
			cancelar();
			break;
		case "Guardar":
			guardar();
			break;
		case "Agregar":
			agregarItem();
			break;
		case "Quitar":
			quitarItem();
			break;
		default:
			break;
		}
	}

	@Override
	public void setProducto(Producto producto) {
		this.producto = producto;
		gestionarProducto();
	}

	private void gestionarProducto() {
		transaccion.getlProducto().setText(producto.getDescripcion());
		agregarItem();
		EventosUtil.estadosBotones(transaccion.getBtnAgregar(), true);
		EventosUtil.estadosBotones(transaccion.getBtnGuardar(), true);
	}

	private void abrirBuscadorProducto() {
		BuscadorProducto buscador = new BuscadorProducto();
		buscador.setUpControlador(false);
		buscador.getControlador().setInterfaz(this);
		buscador.getControlador().setColaborador(colaborador);
		buscador.setVisible(true);
		
		System.out.println(colaborador.getNombreCompleto());
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
		transaccion.getlCliente().setText(cliente.getNombreCompleto());
		transaccion.getlIdentificacion().setText(cliente.getIdentificacion());
		transaccion.getlContacto().setText(cliente.getContacto());
		transaccion.getlMetrosFechaEmision().setText(String.valueOf(calcularHistoricoMetros(cliente.getId()+"", fechaHoy(), restarFechas())));
	}

	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		if (EventosUtil.liberarAcceso(colaborador, buscador.modulo, "BUSCAR")) {
			buscador.setUpControlador();
			buscador.getControlador().setColaborador(colaborador);
			buscador.getControlador().setInterfaz(this);
			buscador.setVisible(true);
		}
	}

	@Override
	public void setPedido(Pedido pedido){
		estadoInicial(false);
		this.pedido = pedido;
		gestionarPedido();

	}

	private void gestionarPedido() {
		if (pedido == null) {
			return;
		}

		//No se puede modificar el cliente de un pedido generado
		EventosUtil.estadosBotones(transaccion.getBtnBuscarCliente(), false);
		EventosUtil.estadosBotones(transaccion.getBtnAgregar(), true);
		EventosUtil.estadosBotones(transaccion.getBtnAnular(), true);
		EventosUtil.estadosBotones(transaccion.getBtnBuscarProducto(), true);
		EventosUtil.estadosBotones(transaccion.getBtnGuardar(), true);


		//Datos del cliente
		cliente = pedido.getCliente();
		transaccion.getlCliente().setText(cliente.getNombreCompleto());
		transaccion.getlContacto().setText(cliente.getContacto());
		transaccion.getlIdentificacion().setText(cliente.getIdentificacion());
		transaccion.getlMetrosFechaEmision().setText(EventosUtil.separadorDecimales(pedido.getMetrosFechaEmision()));

		//Datos del pedido
		transaccion.getlEstadoPedido().setText(EventosUtil.verificarEstado(pedido.isEstado()));
		transaccion.getlGanancia().setText(EventosUtil.separadorMiles((double) pedido.getGananciaTotal()));
		transaccion.getlMetrosPedido().setText(EventosUtil.separadorDecimales(pedido.getMetrosTotal()));
		transaccion.getlNroPedido().setText((EventosUtil.separadorMiles((double) pedido.getId())));
		transaccion.getlSumatoria().setText((EventosUtil.separadorMiles((double) pedido.getSumatoriaPrecio())));
		transaccion.getLstTipoPago().setSelectedValue(pedido.getTipoPagoPedido(), true);
		transaccion.getlTotalPagar().setText((EventosUtil.separadorMiles((double) pedido.getPrecioPagar())));
		transaccion.gettDescuentoPedido().setValue((double) pedido.getDescuentoTotal());
		transaccion.getRbConsiderarMetraje().setSelected(pedido.isConsiderarMetraje());
		transaccion.getRbGenerarPresupuesto().setSelected(pedido.isEsPresupuesto());

		//Detalles del pedido
		items = pedido.getPedidoDetalles(); 
		mtPedidoDetalle.setDetalle(items);
		mtPedidoDetalle.fireTableDataChanged();

		accion = "MODIFICAR";
	}
	
	@Override
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		
		gestionarColaborador();
	}
	
	public void gestionarColaborador() {
		if (colaborador == null) {
			return;
		}
	}
}


