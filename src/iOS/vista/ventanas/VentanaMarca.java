package iOS.vista.ventanas;

import iOS.controlador.ventanas.VentanaMarcaControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaMarca extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8453183359969835615L;
	public String modulo = "Marca";
	private CampoTextoPersonalizado tNombreMarca;
	private VentanaMarcaControlador controlador;

	public void setUpControlador() {
		controlador = new VentanaMarcaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public VentanaMarca() {

		LabelPersonalizado lblprsnlzdNombreDeLa = new LabelPersonalizado(0);
		lblprsnlzdNombreDeLa.setText("Nombre de la marca");
		lblprsnlzdNombreDeLa.setBounds(12, 11, 120, 15);
		getPanelFormulario().add(lblprsnlzdNombreDeLa);

		tNombreMarca = new CampoTextoPersonalizado();
		tNombreMarca.setBounds(12, 25, 450, 25);
		tNombreMarca.mayusculas();
		getPanelFormulario().add(tNombreMarca);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoTextoPersonalizado gettNombreMarca() {
		return tNombreMarca;
	}

	public VentanaMarcaControlador getControlador() {
		return controlador;
	}

}
