package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaMaterialControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaMaterial extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901641652725084536L;
	public String modulo = "PRODUCTO";
	private CampoTextoPersonalizado tNombreMaterial;
	private CampoTextoPersonalizado tCodigoReferencia;
	private VentanaMaterialControlador controlador;
	private CampoNumeroPersonalizado tPrecioMaterial;
	private JRadioButton rdMetroCuadrado;
	private JRadioButton rdMetroLineal;
	private JRadioButton rdUnidad;

	public void setUpControlador() {
		controlador = new VentanaMaterialControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaMaterial() {
		getPanelFormulario().setBorder(
				new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getPanelFormulario().setBounds(10, 110, 474, 200);

		tNombreMaterial = new CampoTextoPersonalizado();
		tNombreMaterial.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreMaterial.setBounds(20, 40, 430, 30);
		tNombreMaterial.mayusculas();
		tNombreMaterial.limite(50);
		getPanelFormulario().add(tNombreMaterial);

		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCodigoReferencia.setBounds(20, 100, 100, 30);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreCompleto.setText("Descripcion detallada del material");
		lblprsnlzdNombreCompleto.setBounds(20, 20, 270, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1.setText("C\u00F3digo de Referencia R\u00E1pida");
		lblprsnlzdIdentificacion_1.setBounds(20, 80, 226, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		LabelPersonalizado lblValor = new LabelPersonalizado(0);
		lblValor.setBounds(20, 140, 140, 20);
		getPanelFormulario().add(lblValor);
		lblValor.setText("Valor");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tPrecioMaterial = new CampoNumeroPersonalizado();
		tPrecioMaterial.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tPrecioMaterial.setBounds(20, 160, 100, 30);
		getPanelFormulario().add(tPrecioMaterial);

		ButtonGroup grupoDos = new ButtonGroup();

		rdMetroCuadrado = new JRadioButton("METRO CUADRADO");
		rdMetroCuadrado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdMetroCuadrado.setBackground(Color.WHITE);
		rdMetroCuadrado.setBounds(10, 31, 200, 25);
		getContentPane().add(rdMetroCuadrado);
		grupoDos.add(rdMetroCuadrado);

		rdMetroLineal = new JRadioButton("METRO LINEAL");
		rdMetroLineal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdMetroLineal.setBackground(Color.WHITE);
		rdMetroLineal.setBounds(10, 55, 200, 25);
		getContentPane().add(rdMetroLineal);
		grupoDos.add(rdMetroLineal);

		rdUnidad = new JRadioButton("UNIDAD");
		rdUnidad.setSelected(true);
		rdUnidad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdUnidad.setBackground(Color.WHITE);
		rdUnidad.setBounds(10, 7, 200, 25);
		getContentPane().add(rdUnidad);
		grupoDos.add(rdUnidad);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public CampoTextoPersonalizado gettNombreMaterial() {
		return tNombreMaterial;
	}

	public CampoTextoPersonalizado gettCodigoReferencia() {
		return tCodigoReferencia;
	}

	public VentanaMaterialControlador getControlador() {
		return controlador;
	}

	public CampoNumeroPersonalizado gettPrecioMaterial() {
		return tPrecioMaterial;
	}

	public JRadioButton getRdMetroCuadrado() {
		return rdMetroCuadrado;
	}

	public JRadioButton getRdMetroLineal() {
		return rdMetroLineal;
	}

	public JRadioButton getRdUnidad() {
		return rdUnidad;
	}
	
}
