package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.transacciones.TransaccionProduccionControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionProduccion extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235985227042616775L;
	private JTable tablePedidoDetalle;
	private JTable tableSeguimientoProduccion;
	private MiBoton btnBuscar;
	private CampoNumeroPersonalizado tPedido;
	private LabelPersonalizado lPedido2;
	private LabelPersonalizado lColaborador;
	private LabelPersonalizado lCliente;
	private TransaccionProduccionControlador controlador;
	private LabelPersonalizado lPedido1;
	private LabelPersonalizado lPedido3;


	public void setUpControlador() {
		controlador = new TransaccionProduccionControlador(this);

	}

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
		tPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
		tPedido.setBounds(10, 11, 100, 30);
		panel.add(tPedido);

		btnBuscar = new MiBoton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBuscar.setText("Encontrar");
		btnBuscar.setActionCommand("Buscar");
		btnBuscar.setBounds(136, 11, 100, 30);
		panel.add(btnBuscar);

		lPedido1 = new LabelPersonalizado(0);
		lPedido1.setText("PEDIDO 654 - noviembre 26, 2021");
		lPedido1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lPedido1.setBounds(10, 52, 390, 20);
		panel.add(lPedido1);

		lPedido2 = new LabelPersonalizado(0);
		lPedido2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lPedido2.setText("PEDIDO CARTELERIA, FINALIZADO");
		lPedido2.setBounds(10, 75, 390, 20);
		panel.add(lPedido2);

		LabelPersonalizado lblprsnlzdVendedor = new LabelPersonalizado(0);
		lblprsnlzdVendedor.setText("Vendedor: Nombre");
		lblprsnlzdVendedor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdVendedor.setBounds(10, 159, 390, 20);
		panel.add(lblprsnlzdVendedor);

		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lColaborador.setBounds(10, 181, 390, 20);
		panel.add(lColaborador);

		lPedido3 = new LabelPersonalizado(0);
		lPedido3.setText("Metraje total del pedido: 3,67 mts2");
		lPedido3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lPedido3.setBounds(10, 106, 390, 20);
		panel.add(lPedido3);

		LabelPersonalizado lblprsnlzdClienteNombre = new LabelPersonalizado(0);
		lblprsnlzdClienteNombre.setText("Cliente: Nombre");
		lblprsnlzdClienteNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombre.setBounds(10, 212, 390, 20);
		panel.add(lblprsnlzdClienteNombre);

		lCliente = new LabelPersonalizado(0);
		lCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lCliente.setBounds(10, 235, 390, 20);
		panel.add(lCliente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(430, 42, 834, 263);
		getContentPane().add(scrollPane);

		tablePedidoDetalle = new JTable();
		scrollPane.setViewportView(tablePedidoDetalle);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(430, 350, 834, 250);
		getContentPane().add(scrollPane_1);

		tableSeguimientoProduccion = new JTable();
		scrollPane_1.setViewportView(tableSeguimientoProduccion);

		LabelPersonalizado lblprsnlzdSeguimiento = new LabelPersonalizado(0);
		lblprsnlzdSeguimiento.setText("Seguimiento");
		lblprsnlzdSeguimiento.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdSeguimiento.setBounds(430, 316, 113, 20);
		getContentPane().add(lblprsnlzdSeguimiento);

		LabelPersonalizado lblprsnlzdDetallesDelPedido = new LabelPersonalizado(0);
		lblprsnlzdDetallesDelPedido.setText("Detalles del pedido");
		lblprsnlzdDetallesDelPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdDetallesDelPedido.setBounds(430, 11, 217, 20);
		getContentPane().add(lblprsnlzdDetallesDelPedido);
		setType(Type.NORMAL);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTablePedidoDetalle() {
		return tablePedidoDetalle;
	}

	public JTable getTableSeguimientoProduccion() {
		return tableSeguimientoProduccion;
	}

	public MiBoton getBtnBuscar() {
		return btnBuscar;
	}

	public CampoNumeroPersonalizado gettPedido() {
		return tPedido;
	}

	public LabelPersonalizado getlPedido2() {
		return lPedido2;
	}

	public LabelPersonalizado getlColaborador() {
		return lColaborador;
	}

	public LabelPersonalizado getlCliente() {
		return lCliente;
	}

	public TransaccionProduccionControlador getControlador() {
		return controlador;
	}

	public LabelPersonalizado getlPedido1() {
		return lPedido1;
	}

	public LabelPersonalizado getlPedido3() {
		return lPedido3;
	}

	

}
