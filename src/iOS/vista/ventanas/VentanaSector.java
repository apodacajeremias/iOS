package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaSectorControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaSector extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196814908659540277L;
	private VentanaSectorControlador controlador;
	private JTable tableColaboradores;
	private CampoTextoPersonalizado tDescripcion;

	public void setUpControlador() {
		controlador = new VentanaSectorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaSector() {
		LabelPersonalizado lblprsnlzdSectorNombre = new LabelPersonalizado(0);
		lblprsnlzdSectorNombre.setText("Sector: Nombre");
		lblprsnlzdSectorNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdSectorNombre.setBounds(12, 11, 450, 20);
		getPanelFormulario().add(lblprsnlzdSectorNombre);

		tDescripcion = new CampoTextoPersonalizado();
		tDescripcion.setFont(new Font("Tahoma", Font.BOLD, 18));
		tDescripcion.setBounds(12, 33, 450, 25);
		getPanelFormulario().add(tDescripcion);

		LabelPersonalizado lblprsnlzdColaboradoresDelSector = new LabelPersonalizado(0);
		lblprsnlzdColaboradoresDelSector.setBounds(12, 69, 450, 20);
		getPanelFormulario().add(lblprsnlzdColaboradoresDelSector);
		lblprsnlzdColaboradoresDelSector.setText("Colaboradores del sector");
		lblprsnlzdColaboradoresDelSector.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 100, 450, 259);
		getPanelFormulario().add(scrollPane);

		tableColaboradores = new JTable();
		scrollPane.setViewportView(tableColaboradores);
		getContentPane().setEnabled(false);
		getContentPane().setBackground(new Color(255, 255, 255));

		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Sector");
		getContentPane().setLayout(null);
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

	public CampoTextoPersonalizado gettDescripcion() {
		return tDescripcion;
	}
	
	
}
