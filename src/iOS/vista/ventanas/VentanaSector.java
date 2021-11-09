package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaSectorControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaSector extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196814908659540277L;
	private VentanaSectorControlador controlador;
	private JTable tableColaboradores;
	private JTable tableProcesos;
	private MiBoton btnAgregar;
	private CampoTextoPersonalizado tDescripcion;
	private LabelPersonalizado lColaborador;
	private LabelPersonalizado lID;
	private LabelPersonalizado lFechaRegistro;
	private LabelPersonalizado lEstado;

	public void setUpControlador() {
		controlador = new VentanaSectorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaSector() {
		getlMensaje().setBounds(20, 532, 402, 20);
		getMiToolBar().setBounds(10, 560, 412, 40);
		getPanelFormulario().setBounds(10, 10, 412, 511);

		LabelPersonalizado lblprsnlzdSectorNombre = new LabelPersonalizado(0);
		lblprsnlzdSectorNombre.setText("Sector: Nombre");
		lblprsnlzdSectorNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdSectorNombre.setBounds(10, 11, 390, 20);
		getPanelFormulario().add(lblprsnlzdSectorNombre);

		tDescripcion = new CampoTextoPersonalizado();
		tDescripcion.setFont(new Font("Tahoma", Font.BOLD, 18));
		tDescripcion.setBounds(10, 33, 390, 25);
		getPanelFormulario().add(tDescripcion);

		LabelPersonalizado lblprsnlzdColaboradorQueRegistro = new LabelPersonalizado(0);
		lblprsnlzdColaboradorQueRegistro.setText("Colaborador que registro");
		lblprsnlzdColaboradorQueRegistro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdColaboradorQueRegistro.setBounds(10, 64, 390, 20);
		getPanelFormulario().add(lblprsnlzdColaboradorQueRegistro);

		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.BOLD, 18));
		lColaborador.setBounds(10, 87, 390, 20);
		getPanelFormulario().add(lColaborador);

		LabelPersonalizado lblprsnlzdCodigoDeSector = new LabelPersonalizado(0);
		lblprsnlzdCodigoDeSector.setText("Codigo de sector");
		lblprsnlzdCodigoDeSector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdCodigoDeSector.setBounds(10, 118, 390, 20);
		getPanelFormulario().add(lblprsnlzdCodigoDeSector);

		lID = new LabelPersonalizado(0);
		lID.setFont(new Font("Tahoma", Font.BOLD, 18));
		lID.setBounds(10, 140, 390, 20);
		getPanelFormulario().add(lID);

		LabelPersonalizado lblprsnlzdFechaRegistro = new LabelPersonalizado(0);
		lblprsnlzdFechaRegistro.setText("Fecha de registro");
		lblprsnlzdFechaRegistro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdFechaRegistro.setBounds(10, 171, 390, 20);
		getPanelFormulario().add(lblprsnlzdFechaRegistro);

		lFechaRegistro = new LabelPersonalizado(0);
		lFechaRegistro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lFechaRegistro.setBounds(10, 195, 390, 20);
		getPanelFormulario().add(lFechaRegistro);

		LabelPersonalizado lblprsnlzdEstadoDeRegistro = new LabelPersonalizado(0);
		lblprsnlzdEstadoDeRegistro.setText("Estado de registro");
		lblprsnlzdEstadoDeRegistro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdEstadoDeRegistro.setBounds(10, 226, 390, 20);
		getPanelFormulario().add(lblprsnlzdEstadoDeRegistro);

		lEstado = new LabelPersonalizado(0);
		lEstado.setFont(new Font("Tahoma", Font.BOLD, 18));
		lEstado.setBounds(10, 251, 390, 20);
		getPanelFormulario().add(lEstado);
		getContentPane().setEnabled(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Pedidos");
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(432, 61, 410, 539);
		getContentPane().add(scrollPane);

		tableColaboradores = new JTable();
		scrollPane.setViewportView(tableColaboradores);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(854, 61, 410, 539);
		getContentPane().add(scrollPane_1);

		tableProcesos = new JTable();
		scrollPane_1.setViewportView(tableProcesos);

		LabelPersonalizado lblprsnlzdColaboradoresDelSector = new LabelPersonalizado(0);
		lblprsnlzdColaboradoresDelSector.setText("Colaboradores del sector");
		lblprsnlzdColaboradoresDelSector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdColaboradoresDelSector.setBounds(432, 20, 390, 30);
		getContentPane().add(lblprsnlzdColaboradoresDelSector);

		LabelPersonalizado lblprsnlzdEstadosDeLos = new LabelPersonalizado(0);
		lblprsnlzdEstadosDeLos.setText("Estados de los procesos de produccion");
		lblprsnlzdEstadosDeLos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdEstadosDeLos.setBounds(854, 20, 306, 30);
		getContentPane().add(lblprsnlzdEstadosDeLos);

		btnAgregar = new MiBoton("Nuevo");
		btnAgregar.setText("Agregar");
		btnAgregar.setActionCommand("Agregar");
		btnAgregar.setBounds(1164, 20, 100, 30);
		getContentPane().add(btnAgregar);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public VentanaSectorControlador getControlador() {
		return controlador;
	}

	public JTable getTableColaboradores() {
		return tableColaboradores;
	}

	public JTable getTableProcesos() {
		return tableProcesos;
	}

	public MiBoton getBtnAgregar() {
		return btnAgregar;
	}

	public CampoTextoPersonalizado gettDescripcion() {
		return tDescripcion;
	}

	public LabelPersonalizado getlColaborador() {
		return lColaborador;
	}

	public LabelPersonalizado getlID() {
		return lID;
	}

	public LabelPersonalizado getlFechaRegistro() {
		return lFechaRegistro;
	}

	public LabelPersonalizado getlEstado() {
		return lEstado;
	}

}
