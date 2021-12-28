package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaClienteControlador2;
import iOS.vista.componentes.LabelPersonalizado;

public class VentanaCliente2 extends JDialog {



	/**
	 * 
	 */
	private static final long serialVersionUID = -6710485952821378132L;
	private JTable tablePedidos;
	private JTable tablePagos;
	private LabelPersonalizado lNombreCompleto;
	private LabelPersonalizado lIdentificacion;
	private LabelPersonalizado lContacto;
	private LabelPersonalizado lDireccion;
	private LabelPersonalizado lFechaRegistro;
	private LabelPersonalizado lColaborador;
	private LabelPersonalizado lID;
	private LabelPersonalizado lEstado;
	private VentanaClienteControlador2 controlador;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private LabelPersonalizado lDeuda;
	private LabelPersonalizado lPagos;
	private LabelPersonalizado lDiferencia;
	
	public void setUpControlador() {
		controlador = new VentanaClienteControlador2(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCliente2() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Perfil de Cliente");
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 305, 589);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto.setText("Cliente: Nombre Completo");
		lblprsnlzdClienteNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto.setBounds(10, 11, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto);
		
		lNombreCompleto = new LabelPersonalizado(0);
		lNombreCompleto.setText(" ");
		lNombreCompleto.setFont(new Font("Dialog", Font.BOLD, 18));
		lNombreCompleto.setBounds(10, 32, 285, 20);
		panel.add(lNombreCompleto);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_1.setText("Documento de Identidad");
		lblprsnlzdClienteNombreCompleto_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_1.setBounds(10, 63, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_1);
		
		lIdentificacion = new LabelPersonalizado(0);
		lIdentificacion.setText(" ");
		lIdentificacion.setFont(new Font("Dialog", Font.BOLD, 18));
		lIdentificacion.setBounds(10, 85, 285, 20);
		panel.add(lIdentificacion);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3.setText("Contacto Telef\u00F3nico");
		lblprsnlzdClienteNombreCompleto_1_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3.setBounds(10, 116, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3);
		
		lContacto = new LabelPersonalizado(0);
		lContacto.setText(" ");
		lContacto.setFont(new Font("Dialog", Font.BOLD, 18));
		lContacto.setBounds(10, 137, 285, 20);
		panel.add(lContacto);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_1.setText("Direccion Domicilio");
		lblprsnlzdClienteNombreCompleto_1_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_1.setBounds(10, 168, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_1);
		
		lDireccion = new LabelPersonalizado(0);
		lDireccion.setText(" ");
		lDireccion.setFont(new Font("Dialog", Font.BOLD, 18));
		lDireccion.setBounds(10, 190, 285, 20);
		panel.add(lDireccion);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1.setText("Fecha Registro");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1.setBounds(10, 221, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1);
		
		lFechaRegistro = new LabelPersonalizado(0);
		lFechaRegistro.setText(" ");
		lFechaRegistro.setFont(new Font("Dialog", Font.BOLD, 18));
		lFechaRegistro.setBounds(10, 243, 285, 20);
		panel.add(lFechaRegistro);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2.setText("Colaborador que registr\u00F3");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2.setBounds(10, 274, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2);
		
		lColaborador = new LabelPersonalizado(0);
		lColaborador.setText(" ");
		lColaborador.setFont(new Font("Dialog", Font.BOLD, 18));
		lColaborador.setBounds(10, 296, 285, 20);
		panel.add(lColaborador);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1.setText("C\u00F3digo de cliente");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1.setBounds(10, 327, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1);
		
		lID = new LabelPersonalizado(0);
		lID.setText(" ");
		lID.setFont(new Font("Dialog", Font.BOLD, 18));
		lID.setBounds(10, 350, 285, 20);
		panel.add(lID);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1.setText("Estado del registro");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1.setBounds(10, 381, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1);
		
		lEstado = new LabelPersonalizado(0);
		lEstado.setText(" ");
		lEstado.setFont(new Font("Dialog", Font.BOLD, 18));
		lEstado.setBounds(10, 403, 285, 20);
		panel.add(lEstado);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1.setText("Sumatoria Deuda");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1.setBounds(10, 434, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1);
		
		lDeuda = new LabelPersonalizado(0);
		lDeuda.setText(" ");
		lDeuda.setFont(new Font("Dialog", Font.BOLD, 18));
		lDeuda.setBounds(10, 456, 285, 20);
		panel.add(lDeuda);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1.setText("Sumatoria de Pagos");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1.setBounds(10, 487, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1);
		
		lPagos = new LabelPersonalizado(0);
		lPagos.setText(" ");
		lPagos.setFont(new Font("Dialog", Font.BOLD, 18));
		lPagos.setBounds(10, 510, 285, 20);
		panel.add(lPagos);
		
		LabelPersonalizado lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1_1.setText("Diferencia: Deuda - Pagos");
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1_1.setBounds(10, 541, 285, 20);
		panel.add(lblprsnlzdClienteNombreCompleto_1_3_1_2_1_2_1_1_1_1_1);
		
		lDiferencia = new LabelPersonalizado(0);
		lDiferencia.setText(" ");
		lDiferencia.setFont(new Font("Dialog", Font.BOLD, 18));
		lDiferencia.setBounds(10, 563, 285, 20);
		panel.add(lDiferencia);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(325, 11, 939, 290);
		getContentPane().add(scrollPane);
		
		tablePedidos = new JTable();
		scrollPane.setViewportView(tablePedidos);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(325, 310, 939, 290);
		getContentPane().add(scrollPane_1);
		
		tablePagos = new JTable();
		scrollPane_1.setViewportView(tablePagos);
		setType(Type.NORMAL);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTablePedidos() {
		return tablePedidos;
	}

	public JTable getTablePagos() {
		return tablePagos;
	}

	public LabelPersonalizado getlNombreCompleto() {
		return lNombreCompleto;
	}

	public LabelPersonalizado getlIdentificacion() {
		return lIdentificacion;
	}

	public LabelPersonalizado getlContacto() {
		return lContacto;
	}

	public LabelPersonalizado getlDireccion() {
		return lDireccion;
	}

	public LabelPersonalizado getlFechaRegistro() {
		return lFechaRegistro;
	}

	public LabelPersonalizado getlColaborador() {
		return lColaborador;
	}

	public LabelPersonalizado getlID() {
		return lID;
	}

	public LabelPersonalizado getlEstado() {
		return lEstado;
	}

	public VentanaClienteControlador2 getControlador() {
		return controlador;
	}

	public LabelPersonalizado getlDeuda() {
		return lDeuda;
	}

	public LabelPersonalizado getlPagos() {
		return lPagos;
	}

	public LabelPersonalizado getlDiferencia() {
		return lDiferencia;
	}
	
	
}
