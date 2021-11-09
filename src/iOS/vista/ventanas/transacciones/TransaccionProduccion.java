package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import iOS.vista.componentes.LabelPersonalizado;

public class TransaccionProduccion extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7235985227042616775L;
	private JTable tablePedidoDetalle;
	private JTable tableSeguimientoProduccion;
	private MiBoton btnBuscar;
	private CampoNumeroPersonalizado tPedido;
	private LabelPersonalizado lFechaRegistro;
	private LabelPersonalizado lColaborador;
	private LabelPersonalizado lMetros;
	private LabelPersonalizado lCliente;
	private LabelPersonalizado lEstado;

	public TransaccionProduccion() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Produccion");
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 410, 589);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tPedido = new CampoNumeroPersonalizado();
		tPedido.setBounds(10, 11, 65, 30);
		panel.add(tPedido);
		
		btnBuscar = new MiBoton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBuscar.setText("Encontrar");
		btnBuscar.setActionCommand("Buscar");
		btnBuscar.setBounds(85, 11, 100, 30);
		panel.add(btnBuscar);
		
		LabelPersonalizado lblprsnlzdFechaDeRegistro = new LabelPersonalizado(0);
		lblprsnlzdFechaDeRegistro.setText("Fecha de registro");
		lblprsnlzdFechaDeRegistro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdFechaDeRegistro.setBounds(10, 52, 390, 20);
		panel.add(lblprsnlzdFechaDeRegistro);
		
		lFechaRegistro = new LabelPersonalizado(0);
		lFechaRegistro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lFechaRegistro.setBounds(10, 75, 390, 20);
		panel.add(lFechaRegistro);
		
		LabelPersonalizado lblprsnlzdVendedor = new LabelPersonalizado(0);
		lblprsnlzdVendedor.setText("Vendedor: Nombre");
		lblprsnlzdVendedor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdVendedor.setBounds(10, 106, 390, 20);
		panel.add(lblprsnlzdVendedor);
		
		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.BOLD, 18));
		lColaborador.setBounds(10, 128, 390, 20);
		panel.add(lColaborador);
		
		LabelPersonalizado lblprsnlzdMetrajeTotalDel = new LabelPersonalizado(0);
		lblprsnlzdMetrajeTotalDel.setText("Metraje total del pedido");
		lblprsnlzdMetrajeTotalDel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdMetrajeTotalDel.setBounds(10, 159, 390, 20);
		panel.add(lblprsnlzdMetrajeTotalDel);
		
		lMetros = new LabelPersonalizado(0);
		lMetros.setFont(new Font("Tahoma", Font.BOLD, 18));
		lMetros.setBounds(10, 181, 390, 20);
		panel.add(lMetros);
		
		LabelPersonalizado lblprsnlzdClienteNombre = new LabelPersonalizado(0);
		lblprsnlzdClienteNombre.setText("Cliente: Nombre");
		lblprsnlzdClienteNombre.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdClienteNombre.setBounds(10, 212, 390, 20);
		panel.add(lblprsnlzdClienteNombre);
		
		lCliente = new LabelPersonalizado(0);
		lCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lCliente.setBounds(10, 235, 390, 20);
		panel.add(lCliente);
		
		LabelPersonalizado lblprsnlzdEstadoDelPedido = new LabelPersonalizado(0);
		lblprsnlzdEstadoDelPedido.setText("Estado del pedido");
		lblprsnlzdEstadoDelPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdEstadoDelPedido.setBounds(10, 266, 390, 20);
		panel.add(lblprsnlzdEstadoDelPedido);
		
		lEstado = new LabelPersonalizado(0);
		lEstado.setText("Fecha de registro");
		lEstado.setFont(new Font("Tahoma", Font.BOLD, 18));
		lEstado.setBounds(10, 289, 390, 20);
		panel.add(lEstado);
		
		tablePedidoDetalle = new JTable();
		tablePedidoDetalle.setBounds(430, 42, 834, 263);
		getContentPane().add(tablePedidoDetalle);
		
		tableSeguimientoProduccion = new JTable();
		tableSeguimientoProduccion.setBounds(430, 350, 834, 250);
		getContentPane().add(tableSeguimientoProduccion);
		
		LabelPersonalizado lblprsnlzdSeguimiento = new LabelPersonalizado(0);
		lblprsnlzdSeguimiento.setText("Seguimiento");
		lblprsnlzdSeguimiento.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdSeguimiento.setBounds(430, 316, 217, 20);
		getContentPane().add(lblprsnlzdSeguimiento);
		
		LabelPersonalizado lblprsnlzdDetallesDelPedido = new LabelPersonalizado(0);
		lblprsnlzdDetallesDelPedido.setText("Detalles del pedido");
		lblprsnlzdDetallesDelPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdDetallesDelPedido.setBounds(430, 11, 217, 20);
		getContentPane().add(lblprsnlzdDetallesDelPedido);
		setType(Type.NORMAL);
	}
	
	
}
