package iOS.vista.ventanas.buscadores;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import iOS.controlador.ventanas.buscadores.BuscadorPedidoControlador;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class BuscadorPedido extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private BuscadorPedidoControlador controlador;
	private JTable table;
	private LabelPersonalizado lTotalRegistro;
	private MiBoton btnFiltrar;
	private MiBoton btnBuscarCliente;
	private MiBoton btnCancelar;
	private LabelPersonalizado lDatos1;
	private LabelPersonalizado lDatos2;

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new BuscadorPedidoControlador(this);
	}

	public BuscadorPedido() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("Informe de Pedidos");
		setBounds(100, 100, 600, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);

		lTotalRegistro = new LabelPersonalizado(10);
		lTotalRegistro.setFont(new Font("Tahoma", Font.BOLD, 10));
		lTotalRegistro.setBounds(99, 123, 200, 15);
		getContentPane().add(lTotalRegistro);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 564, 405);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 11, 1, 68);
		getContentPane().add(separator_2);

		LabelPersonalizado lblPedido = new LabelPersonalizado(20);
		lblPedido.setText("Pedidos");
		lblPedido.setBounds(10, 118, 90, 20);
		getContentPane().add(lblPedido);

		btnBuscarCliente = new MiBoton("Buscar");
		btnBuscarCliente.setActionCommand("Buscar");
		btnBuscarCliente.setBounds(474, 7, 100, 30);
		getContentPane().add(btnBuscarCliente);

		btnCancelar = new MiBoton("Cancelar");
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setBounds(474, 44, 100, 30);
		getContentPane().add(btnCancelar);

		btnFiltrar = new MiBoton("Filtrar");
		btnFiltrar.setActionCommand("Filtrar");
		btnFiltrar.setBounds(474, 81, 100, 30);
		getContentPane().add(btnFiltrar);

		lDatos1 = new LabelPersonalizado(0);
		lDatos1.setText("Seleccione el cliente para empezar");
		lDatos1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lDatos1.setBounds(10, 17, 400, 20);
		getContentPane().add(lDatos1);

		lDatos2 = new LabelPersonalizado(0);
		lDatos2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lDatos2.setBounds(10, 44, 400, 20);
		getContentPane().add(lDatos2);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BuscadorPedidoControlador getControlador() {
		return controlador;
	}

	public JTable getTable() {
		return table;
	}

	public LabelPersonalizado getlTotalRegistro() {
		return lTotalRegistro;
	}

	public MiBoton getBtnFiltrar() {
		return btnFiltrar;
	}

	public MiBoton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public MiBoton getBtnCancelar() {
		return btnCancelar;
	}

	public LabelPersonalizado getlDatos1() {
		return lDatos1;
	}

	public LabelPersonalizado getlDatos2() {
		return lDatos2;
	}

}
