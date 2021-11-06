package iOS.vista.ventanas.buscadores;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import iOS.controlador.ventanas.buscadores.BuscadorPedidoControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class BuscadorPedido extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private BuscadorPedidoControlador controller;
	private JTable table;
	private JTable tableDetalle;
	private LabelPersonalizado lblTotalRegistro;
	private MiBoton btnFiltrar;
	private CampoTextoPersonalizado tCliente;
	private MiBoton btnBuscarCliente;
	private MiBoton btnLimpiar;
	private MiBoton btnCancelar;
	private MiBoton btnPagar;

	

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controller = new BuscadorPedidoControlador(this);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscadorPedido dialog = new BuscadorPedido();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BuscadorPedido() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("Informe de Pedidos");
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);

		lblTotalRegistro = new LabelPersonalizado(10);
		lblTotalRegistro.setBounds(294, 54, 200, 30);
		getContentPane().add(lblTotalRegistro);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 138, 764, 175);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		LabelPersonalizado lblDetalleDePedido = new LabelPersonalizado(14);
		lblDetalleDePedido.setText("Detalles del pedido");
		lblDetalleDePedido.setBounds(267, 324, 250, 27);
		getContentPane().add(lblDetalleDePedido);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 350, 764, 200);
		getContentPane().add(scrollPane_1);

		tableDetalle = new JTable();
		scrollPane_1.setViewportView(tableDetalle);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(12, 52, 170, 2);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(4, 95, 764, 2);
		getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 11, 1, 68);
		getContentPane().add(separator_2);

		LabelPersonalizado lblPedido = new LabelPersonalizado( 20);
		lblPedido.setText("Pedidos");
		lblPedido.setBounds(267, 107, 250, 27);
		getContentPane().add(lblPedido);

		LabelPersonalizado lblCliente = new LabelPersonalizado( 15);
		lblCliente.setText("Encontrar por cliente");
		lblCliente.setBounds(12, 13, 170, 30);
		getContentPane().add(lblCliente);

		tCliente = new CampoTextoPersonalizado();
		tCliente.setEditable(false);
		tCliente.setText(null);
		tCliente.setToolTipText("Nombre del Cliente");
		tCliente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tCliente.setColumns(10);
		tCliente.setBounds(206, 13, 300, 30);
		getContentPane().add(tCliente);

		btnBuscarCliente = new MiBoton("Buscar");
		btnBuscarCliente.setActionCommand("BuscarCliente");
		btnBuscarCliente.setBounds(512, 13, 100, 30);
		getContentPane().add(btnBuscarCliente);

		btnLimpiar = new MiBoton("Limpiar");
		btnLimpiar.setActionCommand("Limpiar");
		btnLimpiar.setBounds(668, 54, 100, 30);
		getContentPane().add(btnLimpiar);

		btnCancelar = new MiBoton("Cancelar");
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setBounds(512, 54, 100, 30);
		getContentPane().add(btnCancelar);
		
		btnFiltrar = new MiBoton("Filtrar");
		btnFiltrar.setActionCommand("Filtrar");
		btnFiltrar.setBounds(668, 10, 100, 30);
		getContentPane().add(btnFiltrar);
		
		JLabel lblDobleClickSobre = new JLabel("Doble click sobre pedido para abrir");
		lblDobleClickSobre.setForeground(Color.LIGHT_GRAY);
		lblDobleClickSobre.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblDobleClickSobre.setBounds(589, 314, 185, 14);
		getContentPane().add(lblDobleClickSobre);
		
		btnPagar = new MiBoton(("Nuevo"));
		btnPagar.setEnabled(false);
		btnPagar.setVisible(false);
		btnPagar.setText("Pagar");
		btnPagar.setActionCommand("Pagar");
		btnPagar.setBounds(668, 105, 100, 30);
		getContentPane().add(btnPagar);

	}

	public BuscadorPedidoControlador getControlador() {
		return controller;
	}

	public JTable getTable() {
		return table;
	}

	public JTable getTableDetalle() {
		return tableDetalle;
	}

	public LabelPersonalizado getLblTotalRegistro() {
		return lblTotalRegistro;
	}

	public MiBoton getBtnFiltrar() {
		return btnFiltrar;
	}

	public MiBoton getBtnCancelar() {
		return btnCancelar;
	}

	public CampoTextoPersonalizado gettCliente() {
		return tCliente;
	}

	public MiBoton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public MiBoton getBtnLimpiar() {
		return btnLimpiar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MiBoton getBtnPagar() {
		return btnPagar;
	}
	
	
}
