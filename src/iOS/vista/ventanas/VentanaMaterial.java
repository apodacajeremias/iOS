package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
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
	private CampoNumeroPersonalizado tMedidaAlto;
	private CampoNumeroPersonalizado tPrecioMaterial;
	private CampoNumeroPersonalizado tMedidaAncho;
	private JRadioButton rdMedidasFijas;
	private JRadioButton rdEsMaterial;
	private JRadioButton rdEsServicio;
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
		getPanelFormulario().setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getPanelFormulario().setBounds(10, 121, 474, 130);
		setTitle("Registro de nueva caracteristica compartida");
		tNombreMaterial = new CampoTextoPersonalizado();
		tNombreMaterial.setBounds(10, 36, 250, 25);
		tNombreMaterial.mayusculas();
		tNombreMaterial.limite(50);
		getPanelFormulario().add(tNombreMaterial);

		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setBounds(10, 93, 100, 25);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Descripci\u00F3n del producto");
		lblprsnlzdNombreCompleto.setBounds(10, 20, 152, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdIdentificacion_1.setText("Código Referencia");
		lblprsnlzdIdentificacion_1.setBounds(10, 77, 116, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		ButtonGroup grupoUno = new ButtonGroup();
		ButtonGroup grupoDos = new ButtonGroup();

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(null, "Precio Sugerido", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 262, 474, 53);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		LabelPersonalizado lblprsnlzdIdentificacion_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1_1.setText("Valor");
		lblprsnlzdIdentificacion_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblprsnlzdIdentificacion_1_1_1.setBounds(10, 24, 33, 15);
		panel_1.add(lblprsnlzdIdentificacion_1_1_1);

		tPrecioMaterial = new CampoNumeroPersonalizado();
		tPrecioMaterial.setBounds(40, 18, 100, 25);
		panel_1.add(tPrecioMaterial);

		rdEsMaterial = new JRadioButton("Registro de producto");
		rdEsMaterial.setBounds(14, 20, 152, 23);
		getContentPane().add(rdEsMaterial);
		rdEsMaterial.setSelected(true);
		rdEsMaterial.setBackground(Color.WHITE);
		rdEsMaterial.setFont(new Font("Tahoma", Font.PLAIN, 12));
		grupoUno.add(rdEsMaterial);

		rdEsServicio = new JRadioButton("Registro de servicio");
		rdEsServicio.setBounds(14, 46, 152, 23);
		getContentPane().add(rdEsServicio);
		rdEsServicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdEsServicio.setBackground(Color.WHITE);
		grupoUno.add(rdEsServicio);

		rdMetroCuadrado = new JRadioButton("METRO CUADRADO");
		rdMetroCuadrado.setBackground(Color.WHITE);
		rdMetroCuadrado.setSelected(true);
		rdMetroCuadrado.setBounds(180, 20, 150, 23);
		getContentPane().add(rdMetroCuadrado);
		grupoDos.add(rdMetroCuadrado);

		rdMetroLineal = new JRadioButton("METRO LINEAL");
		rdMetroLineal.setBackground(Color.WHITE);
		rdMetroLineal.setBounds(180, 46, 150, 23);
		getContentPane().add(rdMetroLineal);
		grupoDos.add(rdMetroLineal);

		rdUnidad = new JRadioButton("UNIDAD");
		rdUnidad.setBackground(Color.WHITE);
		rdUnidad.setBounds(180, 72, 150, 23);
		getContentPane().add(rdUnidad);
		grupoDos.add(rdUnidad);

		rdMedidasFijas = new JRadioButton("Medidas fijas");
		rdMedidasFijas.setBounds(344, 20, 133, 23);
		getContentPane().add(rdMedidasFijas);
		rdMedidasFijas.setBackground(Color.WHITE);
		rdMedidasFijas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (rdMedidasFijas.isSelected() == false) {
					tMedidaAlto.setEnabled(false);
					tMedidaAncho.setEnabled(false);
				}
				if (rdMedidasFijas.isSelected() == true) {
					tMedidaAlto.setEnabled(true);
					tMedidaAncho.setEnabled(true);
				}
			}
		});
		rdMedidasFijas.setFont(new Font("Tahoma", Font.PLAIN, 12));

		tMedidaAlto = new CampoNumeroPersonalizado();
		tMedidaAlto.setBounds(387, 47, 90, 23);
		getContentPane().add(tMedidaAlto);

		tMedidaAlto.setEnabled(false);

		tMedidaAncho = new CampoNumeroPersonalizado();
		tMedidaAncho.setBounds(387, 72, 90, 23);
		getContentPane().add(tMedidaAncho);
		tMedidaAncho.setEnabled(false);

		LabelPersonalizado lblprsnlzdIdentificacion_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1.setBounds(350, 50, 26, 15);
		getContentPane().add(lblprsnlzdIdentificacion_1_1);
		lblprsnlzdIdentificacion_1_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprsnlzdIdentificacion_1_1.setText("Alto");



		LabelPersonalizado lblprsnlzdIdentificacion_1_2 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_2.setBounds(350, 76, 30, 15);
		getContentPane().add(lblprsnlzdIdentificacion_1_2);
		lblprsnlzdIdentificacion_1_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprsnlzdIdentificacion_1_2.setText("Ancho");

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

	public CampoNumeroPersonalizado gettMedidaAlto() {
		return tMedidaAlto;
	}

	public CampoNumeroPersonalizado gettPrecioMaterial() {
		return tPrecioMaterial;
	}

	public CampoNumeroPersonalizado gettMedidaAncho() {
		return tMedidaAncho;
	}

	public JRadioButton getRdMedidasFijas() {
		return rdMedidasFijas;
	}

	public JRadioButton getRdEsMaterial() {
		return rdEsMaterial;
	}

	public JRadioButton getRdEsServicio() {
		return rdEsServicio;
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
