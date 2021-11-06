package iOS.controlador.ventanas.transacciones;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.ConexionReporte;
import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.transacciones.TransaccionPedido;
import net.sf.jasperreports.engine.JRException;


public class TransaccionPedidoControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface, ClienteInterface, ProductoInterface, PropertyChangeListener {
	private TransaccionPedido transaccionPedido;
	
	private ModeloTablaPedidoDetalle mtPedidoDetalle;
	private PedidoDao dao;
	private String accion;
	private Pedido pedido;
	private Cliente cliente;
	private List<PedidoDetalles> items = new ArrayList<PedidoDetalles>();
	private Producto producto;
	private PedidoDetalles detalle;

	private DecimalFormat formatter = new DecimalFormat("#,###");

	private List<Pedido> pedidos = new ArrayList<Pedido>();
	private double sumaMetrosPorCliente;
	
	public TransaccionPedidoControlador(TransaccionPedido transaccionPedido) {
		this.transaccionPedido = transaccionPedido;
		mtPedidoDetalle = new ModeloTablaPedidoDetalle();
		transaccionPedido.getTable().setModel(mtPedidoDetalle);

		dao = new PedidoDao();
		pedido = new Pedido();

		nuevo();
		estadoInicial(true);
		setUpEvents();
		formatoTabla();
	}

	private void setUpEvents() {
		this.transaccionPedido.getBtnAgregar().addActionListener(this);
		this.transaccionPedido.getBtnAnular().addActionListener(this);
		this.transaccionPedido.getBtnBuscarCliente().addActionListener(this);
		this.transaccionPedido.getBtnBuscarProducto().addActionListener(this);
		this.transaccionPedido.getBtnSalir().addActionListener(this);
		
		this.transaccionPedido.getBtnGuardar().addActionListener(this);
		

		this.transaccionPedido.getTable().addMouseListener(this);
		this.transaccionPedido.getTable().addPropertyChangeListener(this);
				this.transaccionPedido.getRbConsiderarMetraje().addPropertyChangeListener(this);
		this.transaccionPedido.getRbGenerarPresupuesto().addPropertyChangeListener(this);
		this.transaccionPedido.getRbConsiderarMetraje().addMouseListener(this);
		this.transaccionPedido.getRbGenerarPresupuesto().addMouseListener(this);

		this.transaccionPedido.gettDescuentoPedido().addKeyListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlCliente());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlEstadoPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlFechaHora());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlGanancia());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlProducto());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlMetrosFechaEmision());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlMetrosPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlNroPedido());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlSumatoria());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.getlTotalPagar());
		EventosUtil.limpiarCampoPersonalizado(transaccionPedido.gettDescuentoPedido());
		EventosUtil.estadosCampoPersonalizado(transaccionPedido.gettDescuentoPedido(), !b);

		transaccionPedido.getRbConsiderarMetraje().setSelected(false);
		transaccionPedido.getlMetrosFechaEmision().setForeground(Color.RED);
		transaccionPedido.getRbGenerarPresupuesto().setSelected(false);

		//Botones que se mantienen disponibles si o si
		EventosUtil.estadosBotones(transaccionPedido.getBtnBuscarCliente(), true);
		EventosUtil.estadosBotones(transaccionPedido.getBtnSalir(), true);

		//Cuando setCliente, se activa
		EventosUtil.estadosBotones(transaccionPedido.getBtnBuscarProducto(), !b);

		//Cuando setProducto, se activa
		EventosUtil.estadosBotones(transaccionPedido.getBtnAgregar(), false);

		//Si se selecciona un detalle
		EventosUtil.estadosBotones(transaccionPedido.getBtnAnular(), !b);

		EventosUtil.estadosBotones(transaccionPedido.getBtnGuardar(), false);
		
		vaciarTabla();

	}

	private void formatoTabla() {
		transaccionPedido.getTable().getColumnModel().getColumn(0).setPreferredWidth(250);
		transaccionPedido.getTable().getColumnModel().getColumn(1).setPreferredWidth(100);

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
		detalle = new PedidoDetalles();

		//Medidas
		if (producto.isTieneMedidaFija()) {
			detalle.setMedidaAlto(producto.getAltoProducto());
			detalle.setMedidaAncho(producto.getAnchoProducto());
		} else {
			detalle.setMedidaAlto(100);
			detalle.setMedidaAncho(100);
		}
		
		
		//El estado inicial TRUE porque está disponible, si es false entonces el detalle está cancelado y no debe ir a produccion
		

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
		transaccionPedido.getBtnBuscarProducto().requestFocus();

		//Metodos referentes
		calcularMedidaDetalle();
		calcularSubtotalDetalle();
		sumarMedidaDetalle();
	}
	
	private void quitarItem() {
		if (transaccionPedido.getTable().getSelectedRow() < 0) {
			return;
		}
		
		int opcion = JOptionPane.showConfirmDialog(null,"¿Retirar item?",
				"Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			items.remove(transaccionPedido.getTable().getSelectedRow());
			mtPedidoDetalle.setDetalle(items);
		} else {
			return;
		}
		mtPedidoDetalle.fireTableDataChanged();
		realizarCalculos();
	}

	private boolean considerarMetraje() {
		boolean considerarMetraje = transaccionPedido.getRbConsiderarMetraje().isSelected();
		if (considerarMetraje == true) {
			transaccionPedido.getlMetrosFechaEmision().setForeground(Color.YELLOW);
		} else {
			transaccionPedido.getlMetrosFechaEmision().setForeground(Color.RED);
		}
		System.out.println(considerarMetraje+ " considerarMetraje");
		return considerarMetraje;

	}

	private boolean generarPresupuesto() {
		boolean esPresupuesto = transaccionPedido.getRbGenerarPresupuesto().isSelected();
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
			transaccionPedido.getlMetrosFechaEmision().setText(
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
		transaccionPedido.getlMetrosPedido().setText(String.valueOf(total));
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
		double descuento = transaccionPedido.gettDescuentoPedido().getValue();
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

		transaccionPedido.getlSumatoria().setText(formatter.format(suma)+" Gs.");
		transaccionPedido.getlTotalPagar().setText(formatter.format(diferencia)+ " Gs.");

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
			JOptionPane.showMessageDialog(transaccionPedido, "Indique el cliente");
			return false;
		}
		if (items.size() <= 0) {
			JOptionPane.showMessageDialog(transaccionPedido, "Cargue productos al pedido");
			return false;
		}
		
		return true;
	}
	
	private void imprimir(Pedido p) {
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
//		parametros.put("titularEmpresaM", configuracion.getTitular().toUpperCase());
//		parametros.put("nombreEmpresa", configuracion.getEmpresa());
//		parametros.put("registroProfesional",configuracion.getRegistroProfesional());
//		parametros.put("registroUnico", configuracion.getRegistroUnico());
//		parametros.put("contactoEmpresa", configuracion.getTelefono());
//		parametros.put("cedulaTitular", configuracion.getCedulaTitular());
//		parametros.put("ubicacion", configuracion.getUbicacion());
		
		pedidos.clear();
		
		pedidos.add(p);

		// Creando reportes
		ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
		try {
			conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso");
			conexionReporte.ventanaReporte.setLocationRelativeTo(transaccionPedido);
			conexionReporte.ventanaReporte.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
		}

		pedido.setCliente(cliente);
		pedido.setColaborador(Sesion.getInstance().getColaborador());
		pedido.setConsiderarMetraje(transaccionPedido.getRbConsiderarMetraje().isSelected());
		pedido.setDescuentoTotal(Integer.parseInt(transaccionPedido.gettDescuentoPedido().getText()));

		if (transaccionPedido.getRbGenerarPresupuesto().isSelected() == false) { //es pedido entonces
			pedido.setEsPresupuesto(false);
		} else {
			pedido.setEsPresupuesto(true); //es presupuesto entonces la variable pasa con true
		}
		pedido.setCostoTotal(sumarCosto());
		pedido.setMetrosFechaEmision(Double.parseDouble(transaccionPedido.getlMetrosFechaEmision().getText()));
		pedido.setMetrosTotal(sumarMedidaDetalle());
		pedido.setTipoPagoPedido(transaccionPedido.getLstTipoPago().getSelectedValue().toString());


		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));


		pedido.setPedidoDetalles(items);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setPedido(pedido);
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(pedido);
			} else {
				dao.modificar(pedido);
			}
			dao.commit();
			estadoInicial(true);
			
			int opcion = JOptionPane.showConfirmDialog(null,"¿Desea imprimir?",
					"Impresion", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (opcion == JOptionPane.OK_OPTION) {
				imprimir(pedido);
			} else {
				return;
			}
					
		} catch (Exception e) {
			dao.rollBack();
			System.out.println(formatException(e));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == transaccionPedido.gettDescuentoPedido()
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
		if (e.getSource() == transaccionPedido.getTable()) {
			transaccionPedido.getTable().isCellEditable(transaccionPedido.getTable().getSelectedRow(), transaccionPedido.getTable().getSelectedColumn());

		}
		if (e.getSource() == transaccionPedido.getRbConsiderarMetraje()) {
			considerarMetraje();
		}

		if (e.getSource() == transaccionPedido.getRbGenerarPresupuesto()) {
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
		if (e.getSource() == transaccionPedido.getTable()) {
			realizarCalculos();
		}

		if (e.getSource() == transaccionPedido.getRbConsiderarMetraje()) {
			considerarMetraje();
		}
		if (e.getSource() == transaccionPedido.getRbGenerarPresupuesto()) {
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
		transaccionPedido.getlProducto().setText(producto.getDescripcion());
		agregarItem();
		EventosUtil.estadosBotones(transaccionPedido.getBtnAgregar(), true);
		EventosUtil.estadosBotones(transaccionPedido.getBtnGuardar(), true);
	}

	private void abrirBuscadorProducto() {
		BuscadorProducto buscador = new BuscadorProducto();
		buscador.setUpControlador(false);
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
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
		estadoInicial(false);
		transaccionPedido.getlCliente().setText(cliente.getNombreCompleto());
		transaccionPedido.getlIdentificacion().setText(cliente.getIdentificacion());
		transaccionPedido.getlContacto().setText(cliente.getContacto());
		transaccionPedido.getlMetrosFechaEmision().setText(String.valueOf(calcularHistoricoMetros(cliente.getId()+"", fechaHoy(), restarFechas())));
	}

	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), buscador.modulo, "BUSCAR")) {
			buscador.setUpControlador();
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
		estadoInicial(false);

		//No se puede modificar el cliente de un pedido generado
		EventosUtil.estadosBotones(transaccionPedido.getBtnBuscarCliente(), false);


		//Datos del cliente
		transaccionPedido.getlCliente().setText(pedido.getCliente().getNombreCompleto());
		transaccionPedido.getlContacto().setText(pedido.getCliente().getContacto());
		transaccionPedido.getlIdentificacion().setText(pedido.getCliente().getIdentificacion());
		transaccionPedido.getlMetrosFechaEmision().setText(String.valueOf(pedido.getMetrosFechaEmision()));

		//Datos del pedido
		transaccionPedido.getlEstadoPedido().setText(String.valueOf(pedido.getMetrosFechaEmision()));
		//		transaccionPedido.getlFechaHora().setText(pedido.getFechaPedido()+" "+pedido.getHoraPedido());
		transaccionPedido.getlGanancia().setText(String.valueOf(pedido.getGananciaTotal()));
		transaccionPedido.getlMetrosPedido().setText(String.valueOf(pedido.getMetrosTotal()));
		transaccionPedido.getlNroPedido().setText(String.valueOf(pedido.getId()));
		transaccionPedido.getlSumatoria().setText(String.valueOf(pedido.getSumatoriaPrecio()));
		transaccionPedido.getLstTipoPago().setSelectedValue(pedido.getTipoPagoPedido(), true);
		transaccionPedido.getlTotalPagar().setText(String.valueOf(pedido.getPrecioPagar()));
		transaccionPedido.gettDescuentoPedido().setText(String.valueOf(pedido.getDescuentoTotal()));
		transaccionPedido.getRbConsiderarMetraje().setEnabled(pedido.isConsiderarMetraje());
		transaccionPedido.getRbGenerarPresupuesto().setEnabled(pedido.isEsPresupuesto());

		//Detalles del pedido
		//		items = pedido.getPedidoDetalle();
		mtPedidoDetalle.setDetalle(pedido.getPedidoDetalles());
		mtPedidoDetalle.fireTableDataChanged();

		accion = "MODIFICAR";
	}
	
	public static String formatException (Throwable thr) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter (sw);
		thr.printStackTrace (pw);
		return sw.toString();
	}
}


