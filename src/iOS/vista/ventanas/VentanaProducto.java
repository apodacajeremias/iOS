package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaProductoControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaProducto extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901641652725084536L;
	public String modulo = "PRODUCTO";
	private CampoTextoPersonalizado tNombreProducto;
	private CampoTextoPersonalizado tCodigoReferencia;
	private VentanaProductoControlador controlador;
	private CampoNumeroPersonalizado tMedidaAlto;
	private CampoNumeroPersonalizado tPrecioProducto;
	private CampoNumeroPersonalizado tMedidaAncho;
	private JRadioButton rdMedidasFijas;
	private JRadioButton rdMetroCuadrado;
	private JRadioButton rdMetroLineal;
	private JRadioButton rdUnidad;

	public void setUpControlador() {
		controlador = new VentanaProductoControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaProducto() {
		getPanelFormulario().setBorder(
				new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getPanelFormulario().setBounds(10, 110, 474, 200);

		tNombreProducto = new CampoTextoPersonalizado();
		tNombreProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreProducto.setBounds(20, 40, 430, 30);
		tNombreProducto.mayusculas();
		tNombreProducto.limite(50);
		getPanelFormulario().add(tNombreProducto);

		tCodigoReferencia = new CampoTextoPersonalizado();
		tCodigoReferencia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tCodigoReferencia.setBounds(20, 100, 100, 30);
		tCodigoReferencia.mayusculas();
		tCodigoReferencia.limite(4);
		tCodigoReferencia.soloLetras();
		getPanelFormulario().add(tCodigoReferencia);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreCompleto.setText("Descripcion detallada del producto");
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

		tPrecioProducto = new CampoNumeroPersonalizado();
		tPrecioProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tPrecioProducto.setBounds(20, 160, 100, 30);
		getPanelFormulario().add(tPrecioProducto);

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

		rdMedidasFijas = new JRadioButton("Medidas fijas");
		rdMedidasFijas.setBounds(212, 7, 150, 25);
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
		rdMedidasFijas.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tMedidaAlto = new CampoNumeroPersonalizado();
		tMedidaAlto.setBounds(253, 33, 90, 20);
		getContentPane().add(tMedidaAlto);

		tMedidaAlto.setEnabled(false);

		tMedidaAncho = new CampoNumeroPersonalizado();
		tMedidaAncho.setBounds(253, 60, 90, 20);
		getContentPane().add(tMedidaAncho);
		tMedidaAncho.setEnabled(false);

		LabelPersonalizado lblprsnlzdIdentificacion_1_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_1.setBounds(212, 31, 26, 25);
		getContentPane().add(lblprsnlzdIdentificacion_1_1);
		lblprsnlzdIdentificacion_1_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprsnlzdIdentificacion_1_1.setText("Alto");

		LabelPersonalizado lblprsnlzdIdentificacion_1_2 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1_2.setBounds(212, 55, 30, 25);
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

	public CampoTextoPersonalizado gettNombreProducto() {
		return tNombreProducto;
	}

	public CampoTextoPersonalizado gettCodigoReferencia() {
		return tCodigoReferencia;
	}

	public VentanaProductoControlador getControlador() {
		return controlador;
	}

	public CampoNumeroPersonalizado gettMedidaAlto() {
		return tMedidaAlto;
	}

	public CampoNumeroPersonalizado gettPrecioProducto() {
		return tPrecioProducto;
	}

	public CampoNumeroPersonalizado gettMedidaAncho() {
		return tMedidaAncho;
	}

	public JRadioButton getRdMedidasFijas() {
		return rdMedidasFijas;
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
